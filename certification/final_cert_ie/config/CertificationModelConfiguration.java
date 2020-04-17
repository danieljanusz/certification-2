package com.certification.final_cert_ie.config;

import com.workfusion.vds.nlp.hypermodel.ie.generic.config.GenericIeHypermodelConfiguration;
import com.workfusion.vds.sdk.api.hpo.HpoConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.ConfigurationContext;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Filter;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Import;
import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.Named;
import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.configuration.IeConfigurationContext;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import com.workfusion.vds.sdk.nlp.component.annotator.EntityBoundaryAnnotator;
import com.workfusion.vds.sdk.nlp.component.annotator.ner.BaseRegexNerAnnotator;
import com.workfusion.vds.sdk.nlp.component.annotator.tokenizer.MatcherTokenAnnotator;
import com.certification.final_cert_ie.fe.general.CertEmailNerFE;
import com.certification.final_cert_ie.fe.general.CertInProductColumn;
import com.certification.final_cert_ie.fe.general.CertInvoiceNumberFE;
import com.certification.final_cert_ie.fe.general.CertNextLineInvoiceNumberFE;
import com.certification.final_cert_ie.fe.general.CertSameLineInvoiceNumberFE;
import com.certification.final_cert_ie.fe.general.CertTokenInFirstLine;
import com.certification.final_cert_ie.fe.general.CertTotalAmountFE;
import com.certification.final_cert_ie.processing.CerEmailPostProcessor;
import com.certification.final_cert_ie.processing.CertPricePostProcessor;
import com.certification.final_cert_ie.processing.CertQuantityPostProcessor;
import com.certification.final_cert_ie.processing.CertTotalAmountPostProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The model configuration class.
 * Here you can configure set of Feature Extractors, Annotators and Post-Processors.
 * Also you can import configuration with set of predefined components or your own configuration
 */
@ModelConfiguration
@Import(
		configurations = {
               @Import.Configuration(value = GenericIeHypermodelConfiguration.class)
        }, 
		resources = {
                @Import.Resource(value="/parameters/email/parameters.json",
                        condition = @Filter(expression = "field.code eq 'email'")),
                @Import.Resource(value="/parameters/invoice_date/parameters.json",
                		condition = @Filter(expression = "field.code eq 'invoice_date'")),
                @Import.Resource(value="/parameters/price/parameters.json",
                		condition = @Filter(expression = "field.code eq 'price'")),
                @Import.Resource(value="/parameters/quantity/parameters.json",
                		condition = @Filter(expression = "field.code eq 'quantity'")),
                @Import.Resource(value="/parameters/product/parameters.json",
        				condition = @Filter(expression = "field.code eq 'product'")),
                @Import.Resource(value="/parameters/supplier_name/parameters.json",
                		condition = @Filter(expression = "field.code eq 'supplier_name'")),
                @Import.Resource(value="/parameters/total_amount/parameters.json",
        			condition = @Filter(expression = "field.code eq 'total_amount'")),
                @Import.Resource(value="/parameters/invoice_number/parameters.json",
    			condition = @Filter(expression = "field.code eq 'invoice_number'"))
}  
)
public class CertificationModelConfiguration {

    /**
     * Configure set of annotators.
     * DictionaryNerAnnotator - use some dictionary as an input, will annotate all words provided if they are found in text.
     * RegexNerAnnotator - annotate token ruled by some regular expression.
     *
     * @param context Execution context
     * @return List of specific annotators that will be applied for every document.
     */
	private final static String TOKEN_REGEX = "[\\w\\/@.,$%’-]+";
	private static final String EMAIL_REGEXP = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
	
	  @Named("Annotator")
	  public List<Annotator> getAnnotators(IeConfigurationContext context){
		  List<Annotator> annotators = new ArrayList<>();
		  annotators.add(new MatcherTokenAnnotator(TOKEN_REGEX));
	      annotators.add(new EntityBoundaryAnnotator());
	       
		  String type = context.getField().getCode();
		  switch(type) {
	        case Fields.FIELD_NAME_EMAIL : {
        		annotators.add(BaseRegexNerAnnotator.getJavaPatternRegexNerAnnotator(Fields.FIELD_NAME_EMAIL,EMAIL_REGEXP));
	        	break;
	        }
	        case Fields.FIELD_NAME_INVOICE_DATE : {
	        	
	        	break;
	        }
		  }
		  return annotators;
		  
	  }
	  @Named("featureExtractors")
	    public List<FeatureExtractor<Element>> getFeatureExtractors(IeConfigurationContext context) {
	        List<FeatureExtractor<Element>> featuresExtractors = new ArrayList<>();
	        String type = context.getField().getCode();
	        switch(type) {
	        case Fields.FIELD_NAME_INVOICE_NUMBER : {
	        	 featuresExtractors.add(new CertInvoiceNumberFE());
	        	 featuresExtractors.add(new CertSameLineInvoiceNumberFE());
	        	 featuresExtractors.add(new CertNextLineInvoiceNumberFE());
	        	 break;
	        }
	        case Fields.FIELD_NAME_EMAIL : {
	        	  featuresExtractors.add(new CertEmailNerFE());
	        	break;
	        }
	        case Fields.FIELD_NAME_TOTAL_AMOUNT :{
	           featuresExtractors.add(new CertTotalAmountFE());
	        	break;
	        }
	        case Fields.FIELD_NAME_SUPPLIER_NAME :{
	        	featuresExtractors.add(new CertTokenInFirstLine());
		        	break;
		        }
	        case Fields.FIELD_NAME_PRODUCT :{
	        	featuresExtractors.add(new CertInProductColumn());
	        	break;
	        }
	        }
	        return featuresExtractors;
	    }
	  
	  @Named("hpoConfiguration")
	    public HpoConfiguration hpoConfiguration(ConfigurationContext context) {
	        return new HpoConfiguration.Builder()
	                .timeLimit(600, TimeUnit.SECONDS)
	                .maxExperimentsWithSameScore(5)
	                .build();
	    }
	  
	  @Named("processors")
	    public List<Processor<IeDocument>> getProcessors() {
	    	List<Processor<IeDocument>> processors = new ArrayList<>();
	    	  processors.add(new CerEmailPostProcessor()); 
	          processors.add(new CertTotalAmountPostProcessor());
	    	  processors.add(new CertPricePostProcessor());
	    	  processors.add(new CertQuantityPostProcessor());
	        return processors;
	    }
  
}