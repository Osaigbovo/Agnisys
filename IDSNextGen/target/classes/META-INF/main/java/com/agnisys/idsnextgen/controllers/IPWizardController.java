/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.editorutils.HTMLParser;
import com.agnisys.idsnextgen.global.IDSUtils;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class IPWizardController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fxml variables">
    @FXML
    Label lblFileName;
    @FXML
    Label lblFileLocation;
    @FXML
    Button btnBrowse;
    @FXML
    Button btnCreateFile;
    @FXML
    Button btnCancel;
    @FXML
    Label lblWizardTitle;
    @FXML
    Label lblFileType;
    @FXML
    RadioButton rbWord;
    @FXML
    RadioButton rbExcel;
    @FXML
    RadioButton rbIPXact;
    @FXML
    RadioButton rbSystemRDL;
    @FXML
    RadioButton rbCustomCSV;
    @FXML
    RadioButton rbRalf;
    @FXML
    TextField txtFileName;
    @FXML
    TextField txtLocation;
    //</editor-fold>

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
        bindStrings();
        txtFileName.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    createIP();
                }
            }

        });
    }

    //<editor-fold defaultstate="collapsed" desc="bind strings">
    void bindStrings() {
        try {
            lblWizardTitle.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_IP_WIZARD_TITLE());
            lblFileType.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_TYPE());
            rbWord.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_WORD());
            rbExcel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_EXCEL());
            lblFileName.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_NAME());
            lblFileLocation.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_LOCATION());
            btnBrowse.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_BROWSE());
            btnCreateFile.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_CREATE());
            btnCancel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CANCEL());
            rbIPXact.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_IPXACT());
            rbSystemRDL.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_SYSTEM_RDL());
            rbCustomCSV.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CUSTOM_CSV());
            rbRalf.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_RALF());
        } catch (Exception e) {
            System.err.println("Err bindingStrings in IPWizard : " + e.getMessage());
        }
    }
    //</editor-fold>

    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    //<editor-fold defaultstate="collapsed" desc="create IP button click">
    @FXML
    public void click_createFile(ActionEvent event) {
        createIP();
    }
    //</editor-fold>

    private void createIP() {
        File Src = null;
        File Desc;
        String str = null;
        if (txtFileName.getText().isEmpty()) {
            //ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error Please insert File name");
            //System.err.println("Error Please insert File name");
            IDSUtils.showErrorMessage("Please provide File name");
            return;
        }

        if (txtLocation.getText().isEmpty()) {
            IDSUtils.showErrorMessage("Please provide File location");
            return;
        }

        if (!rbWord.isSelected()) {
            System.err.println("Error : Please select IP file format");
        }

        try {
//            Src = IDSUtils.loadSystemResource("supportfiles/IDSReg.html");
            Src = IDSUtils.loadSystemResource("ids_templates/index.idsng");
            //ClassLoader classLoader = getClass().getClassLoader();
            //File cssFile = IDSUtils.loadSystemResource("ids_templates/ids_template.css");
            //File jsFile = IDSUtils.loadSystemResource("ids_templates/ids_template.js");

            //str = HTMLParser.addIDSStyleAndCss(Src.getAbsolutePath(), cssFile, jsFile);
            str = HTMLParser.addIDSStyleAndCss(Src.getAbsolutePath());
        } catch (Exception ex) {
            //Logger.getLogger(FileWizardController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error click_createFile : " + ex.getMessage());
        }
        String fileName;

        //fileName = txtFileName.getText() + ".html";
        fileName = txtFileName.getText() + ".idsng";
//        System.out.println("--file name=" + fileName);
//        System.out.println("--length=" + fileName.split("\\.").length);

        String dscPath = txtLocation.getText() + File.separator + fileName.split("\\.")[0];
        Desc = new File(dscPath);

        if (Desc.exists()) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : " + fileName + " IP already exist");
            return;
        } else {
            Desc.mkdirs();
        }

        Desc = new File(dscPath + File.separator + fileName);

        try {
            try (FileWriter writer = new FileWriter(Desc)) {
                writer.write(str);
                writer.close();
            }
            ApplicationMainGUIController.APPLICATION_OBJECT.addNewFileInProjExp(Desc);
            closeButtonAction();
        } catch (Exception ex) {
            Logger.getLogger(FileWizardController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Err click_createFile : " + ex.getMessage());
        }
    }

    @FXML
    public void browseProjLoc(ActionEvent event) {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.folderChoose(new File(txtLocation.getText()));
        txtLocation.setText(f.getAbsolutePath());
    }
}
