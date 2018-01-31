/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class AboutController implements Initializable {

    @FXML
    Label lblAbout;
    @FXML
    Label lblVersion;
    @FXML
    Label lblLicense;
    @FXML
    Label lblLicensePath;

    public final static String VERSION = "1.2.0";
    final String LIC_Status = "Active.";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindString();

    }

    private void bindString() {
        lblLicensePath.setText(System.getenv("AGNI_LICENSE_FILE"));
        lblVersion.setText(VERSION);
        System.out.println("--licensestatuc=" + ApplicationMainGUIController.APPLICATION_OBJECT.licenseStatus);
        lblLicense.setText(ApplicationMainGUIController.APPLICATION_OBJECT.licenseStatus + " days");
        lblAbout.setText("IDesignSpec (IDS)  is an engineering tool that allows a chip or system designer \n"
                + " to create the design specification once and automatically generate all possible \n"
                + " views from it, without re-write or any duplication. Designs created using IDS \n "
                + "are Correct-By-Construction. IDS makes the design process efficient, and cost \n effective.");
    }

}
