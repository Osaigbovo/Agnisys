/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class PropertyWindowController implements Initializable {

    @FXML
    TextField lblFileLocation;
    @FXML
    Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadStrings();
    }

    private void loadStrings() {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE;
        if (f.isFile()) {
            lblFileLocation.setText(f.getParent());
        } else {
            lblFileLocation.setText(f.getAbsolutePath());
        }
    }

    @FXML
    private void click_btnClose() {
// get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
