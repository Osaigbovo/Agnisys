/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config;

import com.agnisys.idsnextgen.controllers.config.properties.AdvanceDesign;
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
public class ConfigAdvanceDesignController implements Initializable {

    @FXML
    CheckBox chkMemoryMapping;
    @FXML
    CheckBox chkOptimizeLow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void loadPropIntoConfig() {
        chkMemoryMapping.setSelected(AdvanceDesign.getAdvanceDesignIns().isMemorytech());
        chkOptimizeLow.setSelected(AdvanceDesign.getAdvanceDesignIns().isOptimize());
        //enableDisablePropDesc();
    }

    public void saveTemplateIntoProp() {
        AdvanceDesign.getAdvanceDesignIns().setMemorytech(chkMemoryMapping.isSelected());
        AdvanceDesign.getAdvanceDesignIns().setOptimize(chkOptimizeLow.isSelected());
    }

}
