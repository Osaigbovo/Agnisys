/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import com.agnisys.idsnextgen.IDSNextGen;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class NewProjectWizardController extends Stage implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fxml initilization">
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
    //</editor-fold>

    String defaultProjLocation = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        defaultProjLocation = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        txtProjLoc.setText(defaultProjLocation);
        bindStrings();
        lblProjName.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    createProj();
                }
            }

        });
    }

    void bindStrings() {
        lblNewProj.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_NewProj());
        lblProjName.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_PROJ_NAME());
        lblProjLoc.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_PROJ_LOC());
        btnBrowse.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_BROWSE());
        btnCreateProj.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CREATE_PROJ());
        btnCancel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CANCEL());
    }

    @FXML
    private void browseProjLoc() {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.folderChoose();
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
    private void click_createProject() {
        createProj();
    }

    private void createProj() {
        String fullPath = txtProjLoc.getText().concat(File.separator).concat(txtProjName.getText());
        File f = new File(fullPath);
        try {
            f.mkdirs();

            ApplicationMainGUIController.APPLICATION_OBJECT.bindFileTree(f.getAbsolutePath());
            ApplicationMainGUIController.APPLICATION_OBJECT.closeAllTabs();
            IDSNextGen.APPLICATION_OBJECT.loadProjConfig(f.getAbsolutePath());
            ApplicationMainGUIController.APPLICATION_OBJECT.loadOpenedFiles();
            closeButtonAction();

        } catch (Exception e) {
            System.out.println("Error creating project : " + e.getMessage());
        }
    }

}
