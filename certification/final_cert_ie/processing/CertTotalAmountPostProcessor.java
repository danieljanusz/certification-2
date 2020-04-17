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

public class CertTotalAmountPostProcessor implements Processor<IeDocument> {
	
   private static final int ZEROAFTERDECIMAL = 3;

    @Override
    public void process(IeDocument document) {

        
    	Optional<Field> fieldOptional = document.findField(Fields.FIELD_NAME_TOTAL_AMOUNT) ;
    	
         if (fieldOptional.isPresent()) {
             Field field = fieldOptional.get();
             String fieldString = field.getValue();
             fieldString = fieldString.trim().replaceAll(",","");
             fieldString = fieldString.replace("(","").replace(")","").replace("S","").replace("$","");
             int pos = fieldString.indexOf('.');
             if(pos == 0) {
            	 fieldString="0"+fieldString;
             }
             pos = pos+ZEROAFTERDECIMAL;
             
             if(pos > fieldString.length()) {
            	 int index = pos - fieldString.length();
            	 while(index != 0) {
            		 fieldString =fieldString+"0";
            		 index --;
            	 }
             }
             field.setValue(fieldString);   

             }

         }
     
   
    	}
    
    

