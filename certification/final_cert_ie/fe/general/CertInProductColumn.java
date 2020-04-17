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
public class CertInProductColumn<T extends Element> implements FeatureExtractor<T> {

   // private final String nerType;
       private List<Cell> allCells;
       private int product_column;
       private static final String PRODUCT_COLUMN = "product";
       private static final String SERVICE_COLUMN = "service";
       private static final String DESCRIPTION_COLUMN = "description";
       private static final String FEATURE_NAME = "PRODUCT_COLUMN";
  

    
	   @OnDocumentStart
	    public void onDocumentStart(Document document) {  
		   allCells = new ArrayList<>(document.findAll(Cell.class));
		   for(Cell cell : allCells) {
			   String cellText = cell.getText();
			   cellText = cellText.trim().toLowerCase();
			   if(cellText.equals(PRODUCT_COLUMN)) {
				   product_column = cell.getColumnIndex();
			   }else if(cellText.equals(SERVICE_COLUMN)) {
				   product_column = cell.getColumnIndex();
			   }else if(cellText.equals(DESCRIPTION_COLUMN)) {
				   product_column = cell.getColumnIndex();
			   }else {}
		   }
	    }

    @Override
    public Collection<Feature> extract(Document document, T element) {
    	List<Feature> result = new ArrayList<>();
    	List<Cell> currentCell = document.findCovering(Cell.class,element);	
    	if(currentCell.size() != 0) {
    		if(currentCell.get(0).getColumnIndex() == product_column ) {
    			result.add(new Feature(FEATURE_NAME,1.0));
    		}
    	}
    	return result;
    }
}
