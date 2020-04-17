package com.certification.final_cert_ie.fe.general;

import com.workfusion.vds.sdk.api.nlp.fe.Feature;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.Line;
import com.workfusion.vds.sdk.api.nlp.model.NamedEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create a feature if data is covered with particular Ner.
 *
 * @param <T>
 */
public class CertSameLineInvoiceNumberFE<T extends Element> implements FeatureExtractor<T> {

   // private final String nerType;
    private List<NamedEntity> filteredNamedEntities;
    
    private final static String INVOICE_NO_REGEX = "invoice(s|#|number|no)?([A-Za-z]+)?[0-9]+";
    private final static String FEATURE_NAME = "SAME_LINE_INVOICE_NO_FEATURE";

    


    @Override
    public Collection<Feature> extract(Document document, T element) {
    	List<Feature> result = new ArrayList<>();
    	List<Line> currentLine = document.findCovering(Line.class,element);
    
    	if(currentLine.size() != 0) {
    		String currentLineText = currentLine.get(0).getText();
    		 currentLineText = currentLineText.trim().replaceAll("-","").replaceAll("^","").replaceAll(",","").toLowerCase().replaceAll(" ","").replaceAll(":","").replaceAll(".","");
    		
    		   Pattern pattern = Pattern.compile(INVOICE_NO_REGEX); 
    	       Matcher matcher = pattern.matcher(currentLineText); 
    	       while(matcher.find()) {  
    	    	   result.add(new Feature(FEATURE_NAME,1.0));
    	       }
    		
    	}
    	return result;
    }
}
