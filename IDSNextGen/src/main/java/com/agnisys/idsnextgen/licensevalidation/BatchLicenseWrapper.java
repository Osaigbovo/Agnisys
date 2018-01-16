/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.licensevalidation;

import com.agnisys.commons.AgnisysToolsSecure;

/**
 *
 * @author Sumeet
 */
public class BatchLicenseWrapper {

    AgnisysToolsSecure LICENSE_INSTANCE = null;
    public final String PRODUCT_NAME = "idsword";

    public BatchLicenseWrapper() {
        LICENSE_INSTANCE = new AgnisysToolsSecure();
    }

    public void initilizeLicense() {
        boolean liceAvail = LICENSE_INSTANCE.isAvailable();
        boolean isf = LICENSE_INSTANCE.isFeatureActive(PRODUCT_NAME, "verilog");
        System.out.println("--isLicAvail=" + liceAvail);
        System.out.println("--isF=" + isf);
    }
}
