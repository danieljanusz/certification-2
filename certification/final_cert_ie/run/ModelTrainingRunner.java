package com.certification.final_cert_ie.run;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.certification.final_cert_ie.config.Fields;
import com.certification.final_cert_ie.model.CertificationModel;
import com.workfusion.vds.sdk.api.nlp.configuration.FieldInfo;
import com.workfusion.vds.sdk.api.nlp.configuration.FieldType;
import com.workfusion.vds.sdk.run.ModelRunner;
import com.workfusion.vds.sdk.run.config.LocalTrainingConfiguration;

/**
 * This runner allows you to start model training on your local machine.
 * Paths to training set and output folders, fields configuration are required for the lauch.
 */
public class ModelTrainingRunner {
	public final static String OUTPUT_DIR_PATH = "results";
    private final static String INPUT_DIR_PATH = "data/train";
    
    public static void main(String[] args) throws Exception {
        System.setProperty("WORKFLOW_LOG_FOLDER", "./logs/");

        //TODO Configure input/output
        Path inputDirPath = Paths.get(INPUT_DIR_PATH);
        Path outputDirPath = Paths.get(OUTPUT_DIR_PATH);

        //TODO Configure fields according to your use-case
        List<FieldInfo> fields = new ArrayList<>();
        fields.add(new FieldInfo.Builder("invoice")
                .type(FieldType.INFO_EXTRACTION)
                .required(true)
                .child(new FieldInfo.Builder(Fields.FIELD_NAME_SUPPLIER_NAME)
                        .type(FieldType.FREE_TEXT)
                        .multiValue(false)
                        .required(false)
                        .build())
                .child(new FieldInfo.Builder(Fields.FIELD_NAME_EMAIL)
                        .type(FieldType.EMAIL)
                        .multiValue(false)
                        .required(false)
                        .build())
                .child(new FieldInfo.Builder(Fields.FIELD_NAME_INVOICE_DATE)
                        .type(FieldType.DATE)
                        .multiValue(false)
                        .required(true)
                        .build())
                .child(new FieldInfo.Builder(Fields.FIELD_NAME_INVOICE_NUMBER)
                        .type(FieldType.FREE_TEXT)
                        .multiValue(false)
                        .required(true)
                        .build())
                .child(new FieldInfo.Builder(Fields.FIELD_NAME_PRICE)
                        .type(FieldType.NUMBER)
                        .multiValue(true)
                        .required(true)
                        .build())
                .child(new FieldInfo.Builder(Fields.FIELD_NAME_QUANTITY)
                        .type(FieldType.NUMBER)
                        .multiValue(true)
                        .required(true)
                        .build())
                .child(new FieldInfo.Builder(Fields.FIELD_NAME_PRODUCT)
                        .type(FieldType.FREE_TEXT)
                        .multiValue(true)
                        .required(true)
                        .build())
               
                .child(new FieldInfo.Builder(Fields.FIELD_NAME_TOTAL_AMOUNT)
                        .type(FieldType.NUMBER)
                        .multiValue(false)
                        .required(true)
                        .build())
                .build());
      
        
    
      

        //TODO add parameters if needed.
        Map<String, Object> parameters = new HashMap<>();
       

        LocalTrainingConfiguration configuration = LocalTrainingConfiguration.builder()
                .inputDir(inputDirPath)
                .outputDir(outputDirPath)
                .fields(fields)
                .parameters(parameters)
                .build();

        ModelRunner.run(CertificationModel.class, configuration);
    }
}