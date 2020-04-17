package com.certification.final_cert_ie.model;

import com.workfusion.automl.hypermodel.ie.generic.IeGenericSe20Hypermodel;
import com.workfusion.vds.sdk.api.hypermodel.ModelType;
import com.workfusion.vds.sdk.api.hypermodel.annotation.HypermodelConfiguration;
import com.workfusion.vds.sdk.api.hypermodel.annotation.ModelDescription;
import com.certification.final_cert_ie.config.CertificationModelConfiguration;

/**
 * The model class. Define here your model details like code, version etc.
 */
@ModelDescription(
        code = "final_cert_ie-ml-sdk",
        title = "Generic Information Extraction Customization",
        description = "Custom Quickstart Information Extraction model",
        version = "0.0.1-SNAPSHOT",
        type = ModelType.IE
)
@HypermodelConfiguration(CertificationModelConfiguration.class)
public class CertificationModel extends IeGenericSe20Hypermodel {
	public CertificationModel() {
		
	}
}