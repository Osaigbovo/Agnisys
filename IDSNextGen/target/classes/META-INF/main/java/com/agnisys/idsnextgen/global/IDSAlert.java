/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.global;

import com.agnisys.idsnextgen.controllers.AlertMessageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Agnisys
 */
public class IDSAlert {

    public static void show(String str) {
        IDSAlert ob = new IDSAlert();
        ob.print(str);
    }

    private void print(String str) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/AlertMessage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            AlertMessageController controller = fxmlLoader.<AlertMessageController>getController();
            controller.setMessage(str);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initOwner(com.agnisys.idsnextgen.IDSNextGen.STAGE.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            System.out.println("Erron on print: " + e.getMessage());
        }
    }
}
