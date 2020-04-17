package com.certification.final_cert_ie.fe.general;

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

/**
 * Create a feature if data is covered with particular Ner.
 *
 * @param <T>
 */
public class CertTotalAmountFE<T extends Element> implements FeatureExtractor<T> {

   // private final String nerType;
    private List<NamedEntity> filteredNamedEntities;
    
    private final static String TOTAL_AMOUNT__REGEX = "[0-9]+.[0-9]+";  
    private final static String FEATURE_NAME = "TOTAL_AMOUNT_FEATURE";

    


    @Override
    public Collection<Feature> extract(Document document, T element) {
    	List<Feature> result = new ArrayList<>();
    	//List<Line> currentLine = document.findCovering(Line.class,element);
    	List<Cell> allCells = new ArrayList<>(document.findAll(Cell.class));
    	List<Cell> currentCell = document.findCovering(Cell.class,element);
    	String elementText = element.getText();
    	elementText = elementText.trim().replaceAll(",","").replaceAll(" ","").replaceAll("$","").replaceAll(" £","").replaceAll("USD","").replaceAll("S","");
    	 Pattern pattern = Pattern.compile(TOTAL_AMOUNT__REGEX); 
	     Matcher matcher = pattern.matcher(elementText); 
	       while(matcher.find()) {  
	    	  // result.add(new Feature(FEATURE_NAME,1.0));
	    	   		if(currentCell.size() != 0) {
	    	   				for(Cell cell : allCells) {
	    	   					   if(cell.getColumnIndex() == currentCell.get(0).getColumnIndex()) {
	    	   						   		String cellText = cell.getText();
	    	   						   		cellText = cellText.trim().replaceAll(" ","").replaceAll(",","").toLowerCase();
	    	   						   		if(cellText.equals("amountdue") || cellText.equals("amount")) {
	    	   						   		 result.add(new Feature(FEATURE_NAME,1.0));
	    	   						   		}
	    	   					   }   
	    	   				}
	    	   		}
	     }
    	
    	return result;
    }
}
