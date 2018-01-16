/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

//<editor-fold defaultstate="collapsed" desc="imports">
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
import org.apache.commons.io.FileUtils;
//</editor-fold>

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class FileWizardController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fxml variables">
    @FXML
    Button btnCancel;
    @FXML
    TextField txtLocation;
    @FXML
    TextField txtFileName;
    @FXML
    RadioButton rbWord;
    @FXML
    RadioButton rbExcel;
    @FXML
    RadioButton rbText;

    @FXML
    Label lblWizardTitle;
    @FXML
    Label lblFileType;

    @FXML
    Label lblFileName;
    @FXML
    Label lblFileLocation;
    @FXML
    Button btnBrowse;
    @FXML
    Button btnCreateFile;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="initiale class">
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
        rbWord.setSelected(true);
        rbExcel.setDisable(true);
        bindStrings();
        txtFileName.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    createSpecFile();
                }
            }

        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="bind strings">
    void bindStrings() {
        lblWizardTitle.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_WIZARD_TITLE());
        lblFileType.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_TYPE());
        rbWord.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_WORD());
        rbExcel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_EXCEL());
        rbText.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_TEXT());
        lblFileName.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_NAME());
        lblFileLocation.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_LOCATION());
        btnBrowse.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_BROWSE());
        btnCreateFile.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_CREATE());
        btnCancel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CANCEL());
    }
    //</editor-fold>

    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void click_createFile(ActionEvent event) {
        createSpecFile();
    }

    private void createSpecFile() {
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
            Src = IDSUtils.loadSystemResource("ids_templates/index.idsng");
            str = HTMLParser.addIDSStyleAndCss(Src.getAbsolutePath());
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
        } catch (Exception ex) {
            Logger.getLogger(FileWizardController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error click_createFile : " + ex.getMessage());
        }
    }

    @FXML
    public void browseProjLoc(ActionEvent event) {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.folderChoose(new File(txtLocation.getText()));
        txtLocation.setText(f.getAbsolutePath());
    }
}
