/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config;

import com.agnisys.idsnextgen.controllers.config.properties.AdvanceDesign;
import com.agnisys.idsnextgen.controllers.config.properties.AdvanceVerification;
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
public class ConfigAdvanceVerificationController implements Initializable {

    @FXML
    CheckBox chkConstraint;
    @FXML
    CheckBox chkIllegal;
    @FXML
    CheckBox chkUVMCovergae;
    @FXML
    CheckBox chkUVMConstraint;
    @FXML
    CheckBox chkHDLPath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void loadPropIntoConfig() {
        chkConstraint.setSelected(AdvanceVerification.getAdvanceVerificationInst().isGenerateconstraints());
        chkHDLPath.setSelected(AdvanceVerification.getAdvanceVerificationInst().isGenerateuvmhdl());
        chkIllegal.setSelected(AdvanceVerification.getAdvanceVerificationInst().isGenerateIlligalBeans());
        chkUVMConstraint.setSelected(AdvanceVerification.getAdvanceVerificationInst().isGenerateuvmcov());
        chkUVMCovergae.setSelected(AdvanceVerification.getAdvanceVerificationInst().isGenerateuvmcov());

        //enableDisablePropDesc();
    }

    public void saveTemplateIntoProp() {
        AdvanceVerification.getAdvanceVerificationInst().setGenerateIlligalBeans(chkIllegal.isSelected());
        AdvanceVerification.getAdvanceVerificationInst().setGenerateconstraints(chkConstraint.isSelected());
        AdvanceVerification.getAdvanceVerificationInst().setGeneratecoverage(chkUVMCovergae.isSelected());
        AdvanceVerification.getAdvanceVerificationInst().setGenerateuvmcov(chkUVMConstraint.isSelected());
        AdvanceVerification.getAdvanceVerificationInst().setGenerateuvmhdl(chkHDLPath.isSelected());

    }
}
