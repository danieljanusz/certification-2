package com.certification.final_cert_ie.run;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.workfusion.vds.sdk.run.ModelRunner;
import com.workfusion.vds.sdk.run.config.LocalExecutionConfiguration;
import com.certification.final_cert_ie.model.CertificationModel;


/**
 *  Runner class for local model execution.
 *  Could be used for model tuning and post-processing development.
 *  Paths to input documents, trained model and output folders are required for the lauch.
 */
public class ModelExecutionRunner {
	 private final static String MODEL_DIR_PATH = "results/training/output/model";

	    private final static String INPUT_DIR_PATH = "data/validation";

	    public final static String OUTPUT_DIR_PATH = "results/extract";
    public static void main(String[] args) throws Exception {
    	System.setProperty("WORKFLOW_LOG_FOLDER", "./logs/");

        //TODO put correct values for the paths
        Path trainedModelPath = Paths.get(MODEL_DIR_PATH);
        Path inputDirPath = Paths.get(INPUT_DIR_PATH);
        Path outputDirPath = Paths.get(OUTPUT_DIR_PATH);

        //TODO add parameters if needed.
        Map<String, Object> parameters = new HashMap<>();

        LocalExecutionConfiguration configuration = LocalExecutionConfiguration.builder()
                .inputDir(inputDirPath)
                .outputDir(outputDirPath)
                .trainedModelDir(trainedModelPath)
                .parameters(parameters)
                .build();

        ModelRunner.run(CertificationModel.class, configuration);
    }
}
