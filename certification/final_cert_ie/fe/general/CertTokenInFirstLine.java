package com.certification.final_cert_ie.fe.general;

import com.workfusion.vds.sdk.api.nlp.annotation.OnDocumentStart;
import com.workfusion.vds.sdk.api.nlp.fe.Feature;
import com.workfusion.vds.sdk.api.nlp.fe.FeatureExtractor;
import com.workfusion.vds.sdk.api.nlp.model.Cell;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.Line;
import com.workfusion.vds.sdk.api.nlp.model.NamedEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Create a feature if data is covered with particular Ner.
 *
 * @param <T>
 */
public class CertTokenInFirstLine<T extends Element> implements FeatureExtractor<T> {

   private static final String FEATURE_NAME = "TOKEN_IN_FIRST_LINE";
// private final String nerType;
       private List<Line> allLines;
       private Line firstLine;
       private static final String PREV_LINE_TEXT = "remitpaymentto";
       
  

    
	   @OnDocumentStart
	    public void onDocumentStart(Document document) {  
		   allLines = new ArrayList<>(document.findAll(Line.class));
		   firstLine = allLines.get(0);
		   }
	    

    @Override
    public Collection<Feature> extract(Document document, T element) {
    	List<Feature> result = new ArrayList<>();
    	document.findCovering(Line.class,element);	
    	List<Line> prevLine = document.findPrevious(Line.class,element,1);	
    	
    	if(prevLine.size() != 0) {
    		String prevText = prevLine.get(0).getText().trim().toLowerCase().replaceAll(" ","").replaceAll(":","");
    		
    		if(prevText.contains(PREV_LINE_TEXT)) {
    			result.add(new Feature(FEATURE_NAME,1.0));
    		}
    	}
    	return result;
    }
}
