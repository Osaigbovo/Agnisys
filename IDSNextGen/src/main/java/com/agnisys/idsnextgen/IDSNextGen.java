/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen;

//<editor-fold defaultstate="collapsed" desc="imports">
import com.agnisys.idsnextgen.classes.ApplicationConfiguration;
import com.agnisys.idsnextgen.classes.ProjectConfiguration;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.controllers.config.ConfigMainController;
import com.agnisys.idsnextgen.global.ApplicationProperties;
import com.agnisys.idsnextgen.licensevalidation.IDSLicenseManager;
import com.agnisys.idsnextgen.strings.IDSString;
import java.io.File;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
//</editor-fold>

/**
 * Project entry class
 *
 * @author Sumeet
 */
public class IDSNextGen extends Application {

    //<editor-fold defaultstate="collapsed" desc="class variables">
    final String APP_CONFIG_NAME = "idsng.appconfig";
    final String PROJ_CONFIG_NAME = ".projconfig";

    public static Stage STAGE;
    public static IDSNextGen APPLICATION_OBJECT;
    public String defaultProjPath = "";
    String projectPath = "";
    String tempFilePath = "";
    public ApplicationProperties applicationProps = new ApplicationProperties();
    public final IDSString idsString = new IDSString();
    //</editor-fold>

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("--Program Start : " + System.currentTimeMillis());
        APPLICATION_OBJECT = this;
        defaultProjPath = getdefaultProjectAndLoadProjSetting();
        loadProjConfig(defaultProjPath);
        System.out.println("--Project Loaded Successfully");
        Parent root = FXMLLoader.load(getClass().getResource("/designs/ApplicationMainGUI.fxml"));
        
        //Parent root = FXMLLoader.load(getClass().getResource("/designs/ApplicationMainGUI_2.fxml"));

        //Scene scene = new Scene(root);
        int wid = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int hi = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
//        System.out.println("--Screen Width=" + ScreenWidth);
//        System.out.println("--Screen Height=" + ScreenHeight);

        Scene scene = new Scene(root, wid, hi);
        stage.setScene(scene);
        stage.setTitle(idsString.get_APPLICATION_TITLE());
        //stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        /*stage.setWidth(bounds.getWidth());
         stage.setHeight(bounds.getHeight());

         */
        stage.setMaximized(true);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                System.out.println("you are closing event");
                saveProjSettings();
            }
        });
//        IDSLicenseManager licensemanager = new IDSLicenseManager();
//        licensemanager.checklicense();

        stage.getIcons().add(new Image(IDSNextGen.class.getResourceAsStream("/images/ids32.png")));
        STAGE = stage;
        stage.show();
    }

    public String getProjConfigPath(String projPath) {
        File file = new File(projPath);
        (new File(projPath + File.separator + ".idsng")).mkdirs();

        String projFileName = file.getName() + PROJ_CONFIG_NAME;
        String projSettingPath = projPath + File.separator + ".idsng";
        File f = new File(projSettingPath);

        if (!f.exists()) {
            f.mkdirs();
        }

        return projSettingPath + File.separator + projFileName;
        //return projPath + File.separator + file.getName() + PROJ_CONFIG_NAME;
    }

    /**
     * read project configuration like last open file and load into project
     * properties
     *
     * @param projPath
     */
    public void loadProjConfig(String projPath) {
        if (projPath != null && !projPath.isEmpty()) {
            File file = new File(projPath);
            String configPath = projPath + File.separator + "idsng/idsngconfig.xml";
            if ((new File(configPath)).exists()) {
                ConfigMainController.CONFIGXMLPATH = configPath;
            }
            //projectPath = projPath + File.separator + file.getName() + PROJ_CONFIG_NAME;
            projectPath = getProjConfigPath(projPath);
            //System.out.println("--Project Path : " + projectPath);
            if (file.exists() && file.isDirectory()) {
                file = new File(projectPath);
                ProjectConfiguration.loadProjConfig(file);
            }
        }
    }

    public void saveProjSettings() {
        ApplicationConfiguration.writeIntoAppConfig(ApplicationMainGUIController.APPLICATION_OBJECT, tempFilePath);
        ProjectConfiguration.writeIntoProjConfig(applicationProps, projectPath);

    }

    /**
     *
     * @return String path of the last opened project
     */
    private String getdefaultProjectAndLoadProjSetting() {
        tempFilePath = System.getProperty("user.home") + File.separator + APP_CONFIG_NAME;
        return ApplicationConfiguration.loadAppConfig(new File(tempFilePath));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
