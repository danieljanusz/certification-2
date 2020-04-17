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

public class CertPricePostProcessor implements Processor<IeDocument> {
	
	private static final int ZEROAFTERDECIMAL = 3;

    @Override
    public void process(IeDocument document) {
    	
    	
    	List<Field> allPriceFields = new ArrayList<>(document.findFields(Fields.FIELD_NAME_PRICE));
    	for(Field fields : allPriceFields) {
    		
    		String fieldString = fields.getValue();
    		fieldString = fieldString.replaceAll(",","");
    		fieldString = fieldString.replace("$","");
    		fieldString = fieldString.replaceAll("£","");
    		fieldString = fieldString.replaceAll("I","1");
    		fieldString = fieldString.replaceAll("[|]","1");
    		fieldString = fieldString.replaceAll("i","1");
    		fieldString = fieldString.replaceAll("l","1");
    		fieldString = fieldString.replaceAll("USD","");
    		
    		fieldString = fieldString.replaceAll("G","6");
    		fieldString = fieldString.replaceAll("b","6");
    		fieldString = fieldString.replaceAll("B","8");
    		fieldString = fieldString.replaceAll("O","0");
    		
    		 if(! fieldString.contains(".")) {
             	fieldString = fieldString+".";
             }
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
             
    		fields.setValue(fieldString);
    	}
    	
    }
   
 }
    
    

