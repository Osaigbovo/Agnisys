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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class TextWizardController implements Initializable {

    @FXML
    TextField txtProjLoc;
    @FXML
    Button btnCancel;
    @FXML
    TextField txtProjName;

    @FXML
    Label lblNewProj;
    @FXML
    Label lblProjName;
    @FXML
    Label lblProjLoc;
    @FXML
    Button btnBrowse;
    @FXML
    Button btnCreateProj;

    String defaultProjLocation = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.isDirectory()) {
                txtProjLoc.setText(ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.getAbsolutePath());
            } else {
                txtProjLoc.setText(ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.getParentFile().getAbsolutePath());
            }
//            defaultProjLocation = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
//            txtProjLoc.setText(defaultProjLocation);
            bindStrings();
            txtProjName.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        createFile();
                    }
                }

            });
        } catch (Exception e) {
            System.err.println("Err TextWizard initiliazation : " + e.getMessage());
        }
    }

    void bindStrings() {
        lblNewProj.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_WIZARD_TITLE());
        lblProjName.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_NAME());
        lblProjLoc.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_LOCATION());
        btnBrowse.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_BROWSE());
        btnCreateProj.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_CREATE());
        btnCancel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CANCEL());
    }

    @FXML
    private void browseProjLoc() {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.folderChoose(new File(txtProjLoc.getText()));
        txtProjLoc.setText(f.getAbsolutePath());
    }

    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void click_createTextFile() {
        createFile();
    }

    private void createFile() {
        File Src = null;
        File Desc;
        String fileName = txtProjName.getText();
        if (fileName.isEmpty()) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error Please insert File name");
            return;
        }

        if (txtProjLoc.getText().isEmpty()) {
            IDSUtils.showErrorMessage("Please provide File location");
            return;
        }

        try {
            Src = IDSUtils.loadSystemResource("ids_templates/textFileIndex.txt");
        } catch (Exception ex) {
            System.err.println("Error click_createTextFile get resouce file : " + ex.getMessage());
        }

        if (!fileName.contains(".")) {
            fileName = fileName + ".txt";
        }

        Desc = new File(txtProjLoc.getText() + File.separator + fileName);
        try {
            FileUtils.copyFile(Src, Desc);
//            ApplicationMainGUIController.APPLICATION_OBJECT.bindFileTree(ApplicationMainGUIController.APPLICATION_OBJECT.getCURRENT_PROJECT_ROOT());
//            ApplicationMainGUIController.APPLICATION_OBJECT.openTextInNewTab(Desc);
            ApplicationMainGUIController.APPLICATION_OBJECT.addNewFileInProjExp(Desc);
            closeButtonAction();
        } catch (Exception ex) {
            Logger.getLogger(FileWizardController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Err " + ex.getMessage());
        }
    }
}
