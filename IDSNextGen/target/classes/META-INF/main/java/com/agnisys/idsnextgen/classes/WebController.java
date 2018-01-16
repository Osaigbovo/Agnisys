/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.classes;

import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import javafx.scene.control.Tab;

/**
 *
 * @author Sumeet
 */
public class WebController {

    public void printId(Object object) {
        System.out.println("webcontroller run successfull");
        /*
         if (org.w3c.dom.html.HTMLElement.class.isAssignableFrom(object.getClass())) {
         org.w3c.dom.html.HTMLElement it = (org.w3c.dom.html.HTMLElement) object;
         System.out.println("Id is " + it.getId());
         }*/
    }

    public static void callJump() {
        System.out.println("--call jump call");
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("jump('1')");
    }

    public static void openFind() {
        //System.out.println("webcontroller 2run successfull");
        //System.out.println("java web controller openFind window");
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/FindAndReplace.fxml");//
    }

    public static void setUnsaveSymbol() {
        //System.out.println("set unsaved symbole call");//
        Tab tab = (Tab) ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
        if (!tab.getText().endsWith("*")) {
            tab.setText(tab.getText() + "*");
        }
    }

    public static void setSaveSymbol() {
        //System.out.println("set saved symbole call");
        Tab tab = (Tab) ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
        tab.setText(tab.getText().replace("*", ""));
    }
}
