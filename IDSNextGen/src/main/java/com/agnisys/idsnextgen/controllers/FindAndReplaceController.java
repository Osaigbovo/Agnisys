/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class FindAndReplaceController implements Initializable {

    @FXML
    TextField txtSeach;
    @FXML
    TextField txtReplace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void findText() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("findString('" + txtSeach.getText() + "')");
    }

    @FXML
    private void replaceText() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("findReplaceString('" + txtSeach.getText() + "','" + txtReplace.getText() + "')");
    }

}
