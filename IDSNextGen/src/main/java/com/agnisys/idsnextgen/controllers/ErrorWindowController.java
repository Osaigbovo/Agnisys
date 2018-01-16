/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class ErrorWindowController implements Initializable {

    @FXML
    Hyperlink linkTest;
    @FXML
    AnchorPane anchrPaneContainer;
    @FXML
    ScrollPane scrollpaneContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initilization();
    }

    private void initilization() {
        bindErrors();

    }

    @FXML
    private void click_linkTest() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("jump('target1')");
    }

    private void bindErrors() {
        Hyperlink link;
        Pane paneErrorlink = new Pane();
        JSONObject jsonObj = ApplicationMainGUIController.APPLICATION_OBJECT.jsonObj;
        String msg = "";
        try {
            int layout = 10;
            JSONArray array = jsonObj.getJSONArray("error");
            for (int i = 0; i < array.length(); i++) {
                final String lineID = array.getJSONObject(i).getString("id");
                msg = URLDecoder.decode(array.getJSONObject(i).getString("msg"));
                link = new Hyperlink(msg);
                link.setTooltip(new Tooltip(msg));
                paneErrorlink.getChildren().add(link);

                link.setLayoutY(layout);
                link.setLayoutX(10);
                link.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        callJump(lineID);
                    }

                });

                layout = layout + 20;
            }
            scrollpaneContainer.setContent(paneErrorlink);
        } catch (Exception e) {
            System.err.println("Error bindErrors : " + e.getMessage());
        }
    }

    private void callJump(String jumpID) {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("jump('" + jumpID + "')");
    }

}
