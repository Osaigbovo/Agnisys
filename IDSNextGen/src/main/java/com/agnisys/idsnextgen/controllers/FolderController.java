/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

//<editor-fold defaultstate="collapsed" desc="import">
import com.agnisys.idsnextgen.IDSNextGen;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//</editor-fold>

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class FolderController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fxml variables">
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

        } catch (Exception e) {
            System.err.println("Err FolderWizard initiliazation : " + e.getMessage());
        }
    }

    public void click_createFolder() {
        File Desc;
        String fileName = txtProjName.getText();
        if (fileName.isEmpty()) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error Please insert File name");
            return;
        }

        try {
            new File(txtProjLoc.getText() + File.separator + fileName).mkdirs();

            ApplicationMainGUIController.APPLICATION_OBJECT.bindFileTree(ApplicationMainGUIController.APPLICATION_OBJECT.getCURRENT_PROJECT_ROOT());
            closeButtonAction();
        } catch (Exception ex) {
            Logger.getLogger(FileWizardController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Err " + ex.getMessage());
        }
    }

    void bindStrings() {
        lblNewProj.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FOLDER_WIZARD_TITLE());
        lblProjName.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FOLDER_NAME());
        lblProjLoc.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FOLDER_LOCATION());
        btnBrowse.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_BROWSE());
        btnCreateProj.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FILE_CREATE());
        btnCancel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CANCEL());
    }

    @FXML
    private void browseProjLoc() {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.folderChoose(new File(txtProjName.getText()));
        txtProjLoc.setText(f.getAbsolutePath());
    }

    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
