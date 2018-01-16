/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.licensevalidation;

import com.agnisys.commons.AgnisysToolsSecure;
import com.agnisys.idsnextgen.controllers.EditorToolbarController;
import com.agnisys.idsnextgen.controllers.IDSToolbarController;
import com.agnisys.idsnextgen.global.IDSUtils;
import java.util.ArrayList;

/**
 *
 * @author Agnisys
 */
public class IDSLicenseManager {

    AgnisysToolsSecure LICENSE_INSTANCE = null;
    public final String PRODUCT_NAME = "idsng";
    private IDSToolbarController idstoolbar = null;
    private EditorToolbarController editortoolbar = null;

    public IDSLicenseManager() {
        LICENSE_INSTANCE = new AgnisysToolsSecure();
    }

    public void checklicense(ArrayList<Object> controllerlist) {
        for (Object ob : controllerlist) {
            if (ob instanceof IDSToolbarController) {
                idstoolbar = (IDSToolbarController) ob;
            } else if (ob instanceof EditorToolbarController) {
                editortoolbar = (EditorToolbarController) ob;
            }
        }

        /*
         System.out.println("--is expired=" + LICENSE_INSTANCE.isExpired(PRODUCT_NAME));
         System.out.println("--is availble=" + LICENSE_INSTANCE.isAvailable());
         System.out.println("--is expiry date=" + LICENSE_INSTANCE.getExpiryDate(PRODUCT_NAME));
         System.out.println("--is expiry in days=" + LICENSE_INSTANCE.getExpiryInDays(PRODUCT_NAME));
         System.out.println("--is valid lic file=" + LICENSE_INSTANCE.isLicFileValid());
         */
        long expiredays = LICENSE_INSTANCE.getExpiryInDays(PRODUCT_NAME);
        if (expiredays > 0) {
            idstoolbar.anchorPaneToolbarContainer.setDisable(false);
        } else {
            idstoolbar.anchorPaneToolbarContainer.setDisable(true);
            IDSUtils.showInfoMessage("IDS-Next Generation : Your license has been expire or not found. Please contact to https://www.agnisys.com/contact-us/");
        }
//        idstoolbar.anchorPaneToolbarContainer.setDisable(!LICENSE_INSTANCE.);
    }

    public String getLicenseStatus() {
        return Long.toString(LICENSE_INSTANCE.getExpiryInDays(PRODUCT_NAME));
    }

    private void printAgnisysLicenseInfo(AgnisysToolsSecure lic) {

        /*
         String licStatus = "Activated";
         String notActReason = "";
         if (lic != null) {
         boolean licActive = true;
         if (lic.isAvailable()) {
         if (lic.isLicFileValid()) {
         if (lic.isUUIDValid()) {
         if (!lic.isProductAvailable(PRODUCT_NAME)) {
         licActive = false;
         notActReason = "Product is not available in the license file.";

         }
         } else {
         licActive = false;
         notActReason = "License file not valid for this machine.";
         }
         } else {
         licActive = false;
         notActReason = "License file is not valid.";
         }

         } else {
         notActReason = "License file not available.";
         licActive = false;
         }
         System.out.println("");
         System.out.println("############################# AGNISYS LICENSE INFO #############################");
         System.out.println("");
         if (licActive) {
         licStatus = "Activated";

         if (lic.isExpired(PRODUCT_NAME)) {
         licStatus = licStatus.concat(" [ Expired ]");
         } else {
         long expD = lic.getExpiryInDays(PRODUCT_NAME);
         licStatus = licStatus.concat(" [ Expires in " + expD + " day(s) ]");

         }
         System.out.println("\tLicense Status : " + licStatus);
         System.out.println("");
         } else {
         licStatus = "Not Activated";
         System.out.println("\tLicense Status : " + licStatus + " ( " + notActReason + " )");
         System.out.println("");

         }

         if (licActive) {
         String licText = Transformer.readFileAsString(lic.getLicenseLocation());
         if (licText != null) {
         String[] licLine = licText.split("\r\n");
         for (String line : licLine) {
         System.out.println(String.format("%-23s", line));
         }
         }
         } else {
         String data = "\tIf you do not have a license file you may obtain it by sending \n"
         + "\tan email to 'support@agnisys.com' specifying the 'System UUID'.";
         System.out.println(data);
         System.out.println("");
         String data2 = "\tIf you already have a license file make sure that the 'AGNI_LICENSE_FILE' \n"
         + "\tenvironment variable points to the license file.";
         System.out.println(data2);
         }
         System.out.println("");
         System.out.println("############################# AGNISYS LICENSE INFO #############################");
         } else {
         String uuid = MachineUUID.getSystemUUIDData();
         System.out.println("\n\t\t[System UUID : " + uuid + " ]\n");
         System.out.println("");
         }
         */
    }
}
