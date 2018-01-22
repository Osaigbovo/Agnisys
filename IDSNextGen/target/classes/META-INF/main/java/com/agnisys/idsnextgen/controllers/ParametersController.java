/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import com.agnisys.idsnextgen.editorutils.HTMLParser;
import com.agnisys.idsnextgen.global.IDSUtils;
import com.agnisys.idsnextgen.param.ParamWriter;
import java.io.File;
import java.io.FileWriter;
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
public class ParametersController implements Initializable {

    @FXML
    Button btnCancel;
    @FXML
    TextField txtLocation;
    @FXML
    TextField txtFileName;
    @FXML
    TextField txtimportParam;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.isDirectory()) {
            txtLocation.setText(ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.getAbsolutePath());
        } else {
            txtLocation.setText(ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE.getParentFile().getAbsolutePath());
        }

        txtFileName.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    click_importparam();
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
    public void click_importparam() {
        File Src;
        File Desc;
        if (txtFileName.getText().isEmpty()) {
            //ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error Please insert File name");
            IDSUtils.showErrorMessage("Please provide File name");
            return;
        }
        if (txtLocation.getText().isEmpty()) {
            IDSUtils.showErrorMessage("Please provide File location");
            return;
        }
        String str = null;
        try {
            Src = IDSUtils.loadSystemResource("ids_templates/param.idsng");
            str = HTMLParser.addIDSStyleAndCss(Src.getAbsolutePath());
            str = HTMLParser.addparamcss(str);
        } catch (Exception ex) {
            //Logger.getLogger(FileWizardController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error click_createFile :  " + ex.getMessage());
        }
        String fileName;
        if (txtFileName.getText().contains(".")) {
            fileName = txtFileName.getText();
        } else {
            fileName = txtFileName.getText() + ".idsng";
            //fileName = txtFileName.getText() + ".html";
        }
        Desc = new File(txtLocation.getText() + File.separator + fileName);
        try {
            try (FileWriter writer = new FileWriter(Desc)) {
                writer.write(str);
                writer.close();
            }
            ApplicationMainGUIController.APPLICATION_OBJECT.addNewFileInProjExp(Desc);
            closeButtonAction();
            File importParamFile = new File(txtimportParam.getText());
            if (importParamFile.exists()) {
                importParam(importParamFile);
            }
        } catch (Exception ex) {
            Logger.getLogger(FileWizardController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error click_createFile : " + ex.getMessage());
        }
    }

    private void importParam(File yamlFIle) {
        ParamWriter param = new ParamWriter();
        //File yamlFIle = new File("D:\\AgnisysProjects\\java\\MatlabParameter\\src\\main\\java\\com\\agnisys\\matlabparameter\\yamlparser\\Final_Allagro_Data.yml");
        //File yamlFIle = ApplicationMainGUIController.APPLICATION_OBJECT.fileChooser();
        param.writeParam(yamlFIle);
    }

    @FXML
    public void browseProjLoc(ActionEvent event) {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.folderChoose(new File(txtLocation.getText()));
        txtLocation.setText(f.getAbsolutePath());
    }

    @FXML
    public void browseImport() {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.fileChooser();
        txtimportParam.setText(f.getAbsolutePath());
    }
}
