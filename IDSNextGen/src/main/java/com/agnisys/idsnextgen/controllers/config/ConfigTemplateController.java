/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config;

import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.controllers.config.properties.Template;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class ConfigTemplateController implements Initializable {

    @FXML
    CheckBox chkHideProp;
    @FXML
    CheckBox chkHideDesc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void loadPropIntoConfig() {
        chkHideDesc.setSelected(Template.getTemplateIns().isHidedesc());
        chkHideProp.setSelected(Template.getTemplateIns().isHideprop());
        //enableDisablePropDesc();
    }

    public void saveTemplateIntoProp() {
        Template.getTemplateIns().setHidedesc(chkHideDesc.isSelected());
        Template.getTemplateIns().setHideprop(chkHideProp.isSelected());
        enableDisablePropDesc();
    }

    private void enableDisablePropDesc() {
        try {
            if (Template.getTemplateIns().isHideprop()) {
                ApplicationMainGUIController.APPLICATION_OBJECT.get_ActiveHTMLEditor().executeScript("IDS_PROPHIDE()");
            } else {
                ApplicationMainGUIController.APPLICATION_OBJECT.get_ActiveHTMLEditor().executeScript("IDS_PROPSHOW()");
            }

            if (Template.getTemplateIns().isHidedesc()) {
                ApplicationMainGUIController.APPLICATION_OBJECT.get_ActiveHTMLEditor().executeScript("IDS_DESCHIDE()");
            } else {
                ApplicationMainGUIController.APPLICATION_OBJECT.get_ActiveHTMLEditor().executeScript("IDS_DESCSHOW()");
            }
        } catch (Exception e) {
            System.err.println("Err enableDisablePropDesc : " + e.getMessage());
        }
    }
}
