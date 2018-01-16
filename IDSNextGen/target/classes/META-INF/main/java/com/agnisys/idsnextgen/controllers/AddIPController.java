/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import com.agnisys.idsnextgen.global.IDSUtils;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class AddIPController implements Initializable {

    @FXML
    private TextField txtStoreTo;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtAddFrom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.isDirectory()) {
            txtStoreTo.setText(ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.getAbsolutePath());
        } else {
            txtStoreTo.setText(ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.getParentFile().getAbsolutePath());
        }
        txtStoreTo.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    addIP();
                }
            }

        });
    }

    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void click_AddFile(ActionEvent event) {
        addIP();
    }

    @FXML
    private void browseFileLoc() {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.fileChooser(new File(txtStoreTo.getText()));
        txtAddFrom.setText(f.getAbsolutePath());
    }

    @FXML
    private void browseDescLoc() {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.folderChoose();
        txtStoreTo.setText(f.getAbsolutePath());
    }

    private void addIP() {
        File Src = new File(txtAddFrom.getText());
        File Desc = new File(txtStoreTo.getText() + File.separator + Src.getName());
        //       System.out.println("--src=" + Src.getAbsolutePath());
        //      System.out.println("--desc=" + Desc.getAbsolutePath());

        if (txtAddFrom.getText().isEmpty() || !Src.exists()) {
            //ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error Please insert File name");
            //System.err.println("Error Please insert File name");
            IDSUtils.showErrorMessage("Source field cannot be blank or invalid");
            return;
        }

        if (txtStoreTo.getText().isEmpty() || !(new File(txtStoreTo.getText())).exists()) {
            IDSUtils.showErrorMessage("Destination field cannot be blank or invalid");
            return;
        }

        try {
            /*
             try (FileWriter writer = new FileWriter(Desc)) {
             writer.write(str);
             writer.close();
             }
             */
            IDSUtils.copyFileUsingStream(Src, Desc);
            ApplicationMainGUIController.APPLICATION_OBJECT.addNewFileInProjExp();
            closeButtonAction();
        } catch (Exception ex) {
            Logger.getLogger(FileWizardController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Err click_createFile : " + ex.getMessage());
        }
    }

}
