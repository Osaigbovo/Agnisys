/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.controllers.config.properties.General;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class ConfigGeneralController implements Initializable {

    @FXML
    Label lblCompony;
    @FXML
    Label lblGeneral;
    @FXML
    Label lblCopyRight;
    @FXML
    Label lblSave;

    @FXML
    TextField txtCompony;
    @FXML
    TextArea txtAreaCopyRight;
    @FXML
    CheckBox chkSave;
    @FXML
    CheckBox chklog;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        bindStrings();
    }

    public void loadPropIntoConfig() {
        txtCompony.setText(General.getGeneralIns().getCompany());
        txtAreaCopyRight.setText(General.getGeneralIns().getCopyright());
        chklog.setSelected(General.getGeneralIns().isLog());
    }

    public void saveIntoProp() {
        General.getGeneralIns().setCompany(txtCompony.getText());
        General.getGeneralIns().setCopyright(txtAreaCopyRight.getText());
        General.getGeneralIns().setSavedoc(chkSave.isSelected());
        General.getGeneralIns().setLog(chklog.isSelected());
        System.out.println("-newlog=" + General.getGeneralIns().isLog());
    }

    private void bindStrings() {
        lblCompony.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_COMPANY());
        lblGeneral.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_GENERAL());
        lblCopyRight.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_COPYRIGHT());
        lblSave.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_SAVE_DOCUMENTL());
    }

}
