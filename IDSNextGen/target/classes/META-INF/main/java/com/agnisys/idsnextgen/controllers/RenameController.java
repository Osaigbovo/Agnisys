/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.global.IDSUtils;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class RenameController implements Initializable {

    @FXML
    Button btnCancel;
    @FXML
    Button btnOk;
    @FXML
    Label lblChangeName;
    @FXML
    TextField txtChangeName;
    @FXML
    Label lblWizardTitle;
    @FXML
    Label lblFileType;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE;
        if (f != null) {
            txtChangeName.setText(f.getName());
        }
        bindStrings();
    }

    void bindStrings() {
        try {
            lblWizardTitle.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_RENAME());
            lblFileType.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CHANGE_NAME());
            btnOk.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_OK());
            btnCancel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CANCEL());
        } catch (Exception e) {
            System.err.println("Err bindingString Rename : " + e.getMessage());
        }
    }

    @FXML
    public void click_ok() {
        if (!txtChangeName.getText().isEmpty()) {
            if (IDSUtils.rename(ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE, txtChangeName.getText())) {
                String destPath = ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.getParent() + File.separator + txtChangeName.getText();
                File tempFile = ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE;
                File destFile = new File(destPath);
                ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE = destFile;
                ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
                if (destFile.isFile()) {
                    ApplicationMainGUIController.APPLICATION_OBJECT.updateTabName(destFile, tempFile);
                }
                closeButtonAction();
            }

        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please provide new file name");
        }
    }

    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
