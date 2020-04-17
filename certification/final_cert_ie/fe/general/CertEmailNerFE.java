package com.certification.final_cert_ie.fe.general;


import com.workfusion.vds.sdk.api.nlp.annotation.OnDocumentStart;
import com.workfusion.vds.sdk.api.nlp.fe.Feature;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.NamedEntity;
import com.workfusion.vds.sdk.api.nlp.model.Token;

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
public class CertEmailNerFE<T extends Element> implements FeatureExtractor<T> {

  
 
   
    
   
    @OnDocumentStart
    public void onStart(Document document, Class<T> focusClass) {
       /*List<NamedEntity> nes = new ArrayList<>(document.findAll(NamedEntity.class));
       System.out.println("Size : "+nes.size());
       for(NamedEntity qp : nes) {
    	   System.out.println("****"+qp);
    	   if(qp.getType().toString().contains("date")) {
    		   System.out.println("#$%##%$$#%$#$%#%$#$%#%$#%$#%$#$%  FOUND"+qp);
    	   }
       }*/
    }

    @Override
    public Collection<Feature> extract(Document document, T element) {
    	List<Feature> result = new ArrayList<>();
    	List<NamedEntity> namedEntity = document.findCovering(NamedEntity.class,element);
        	if(namedEntity.size() != 0) 
        	    if(namedEntity.get(0).getType().toString().equals("email")) {
        	    	result.add(new Feature("email_feature",1.0));	
        	    } 		
    	return result;
     }
    	
    	
                
    
}
