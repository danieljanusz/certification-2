/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.certification.final_cert_ie.processing;

import com.certification.final_cert_ie.config.Fields;
import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.processing.Processor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CerEmailPostProcessor implements Processor<IeDocument> {
	


    @Override
    public void process(IeDocument document) {

        
    	Optional<Field> fieldOptional = document.findField(Fields.FIELD_NAME_EMAIL) ;
    	
         if (fieldOptional.isPresent()) {
             Field field = fieldOptional.get();
             String fieldString = field.getValue();
             fieldString = fieldString.trim().replaceAll(" ","");
             if(! fieldString.contains("@")) {
            	 fieldString = fieldString.replaceAll("S","@");
            	 fieldString = fieldString.replaceAll("5","@");
            	 	if(! fieldString.contains("@")) {
            	 		 fieldString = fieldString.replace("(","@");
            	 		 	if(! fieldString.contains("@")) {
            	 		 		 fieldString = fieldString.replace("^","@");
            	 		 	}else {
                   	 		 fieldString = fieldString.replace("(","").replace(")","").replace(">","").replace("<","").replace("^","");
               	 		 }
            	 	}
            	 	else {
            	 		 fieldString = fieldString.replace("(","").replace(")","").replace(">","").replace("<","").replace("^","");
            	 		 }	 
            	 	}else {
           	 		 fieldString = fieldString.replace("(","").replace(")","").replace(">","").replace("<","").replace("^","");
           	 		 }
                    int pos = fieldString.indexOf('@');
                    char nectChar = fieldString.charAt(pos+1);
                    if(nectChar == '.' || nectChar == ',' || nectChar == ' ' ) {
                    	String subString1 = fieldString.substring(0,pos+1);
                    	String subString2 = fieldString.substring(pos+2);
                    	fieldString = subString1+subString2;
                    }
            	 
             field.setValue(fieldString);   

             }

         }
     
   
    	}
    
    

