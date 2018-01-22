/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

//<editor-fold defaultstate="collapsed" desc="imports">
import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.global.IDSUtils;
import com.agnisys.idsnextgen.strings.IDSString;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import org.dom4j.Document;
//</editor-fold>

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class MainMenuController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="declare fxml variables">
    @FXML
    MenuItem menuItmSelectAll;
//    @FXML
//    MenuItem menuItmShowDesc;
//    @FXML
//    MenuItem menuItmShowProp;
    @FXML
    MenuBar MainMenuBar;
    @FXML
    RadioButton rbWord;
    @FXML
    RadioButton rbExcel;
    @FXML
    RadioButton rbText;

    @FXML
    Menu menuFile;
    @FXML
    Menu menuEdit;
//    @FXML
//    Menu menuView;
//    @FXML
//    Menu menuNavigator;
//    @FXML
//    Menu menuRun;
//    @FXML
//    Menu menuTools;
    @FXML
    Menu menuHelp;
    @FXML

    Menu menuFileNew;
    @FXML
    Menu menuFileNewProj;
    @FXML
    MenuItem menuItmCreateProj;
    @FXML
    MenuItem menuFileNewFile;
    @FXML
    MenuItem menuItmNewIP;
    @FXML
    MenuItem menuItmNewReg;
    @FXML
    MenuItem menuItmNewText;
//    @FXML
//    MenuItem menuItmNewCanvas;

    @FXML
    Menu menuOpen;
    @FXML
    MenuItem menuItmOpenProj;
    @FXML
    MenuItem menuItmOpenFile;
//    @FXML
//    MenuItem menuItmCloseProj;
//    @FXML
//    MenuItem menuItmImportProj;
//    @FXML
//    MenuItem menuItmExportProj;
//    @FXML
//    MenuItem menuItmRestartIDE;
    @FXML
    MenuItem menuItmSave;
//    @FXML
//    MenuItem menuItmSaveAll;
    @FXML
    MenuItem menuItmExit;

    @FXML
    MenuItem menuItmUndo;
    @FXML
    MenuItem menuItmRedo;
//    @FXML
//    MenuItem menuItmCopy;
//    @FXML
//    MenuItem menuItmCut;
//    @FXML
//    MenuItem menuItmPaste;
    @FXML
    MenuItem menuItmDelete;
    @FXML
    MenuItem menuItmFind;

//    @FXML
//    Menu menuCustToolBar;
//    @FXML
//    MenuItem menuItmIDSEditor;
//    @FXML
//    MenuItem menuItmGraphicalWindow;
//    @FXML
//    MenuItem menuItmProjExp;
//    @FXML
//    MenuItem menuItmPropWindow;
//    @FXML
//    MenuItem menuItmHierView;
//    @FXML
//    MenuItem menuItmConsole;
//
//    @FXML
//    MenuItem menuItmGotoFile;
//    @FXML
//    MenuItem menuItmGotoLine;
//
//    @FXML
//    MenuItem menuItmRunProj;
//    @FXML
//    MenuItem menuItmSettings;
//    @FXML
//    MenuItem menuItmConfiguration;
    @FXML
    MenuItem menuItmFolder;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="class variables">
    public Document document = null;
    private final String SHOWDESC = " Show description";
    private final String HIDEDESC = "\u2714Show description"; //unicode characters
    private final String SHOWPROP = " Show properties";
    private final String HIDEPROP = "\u2714Show properties";
    //</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="initilize class functions">
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*      ToggleGroup tg = new ToggleGroup();
         rbWord.setToggleGroup(tg);
         rbExcel.setToggleGroup(tg);
         rbText.setToggleGroup(tg);
         */
        //document = DocumentHelper.createDocument();
        bindStrings();
        initializeControls();
    }
    //</editor-fold >

    @FXML
    private void click_parameters() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please Select location from project explorer first");
            return;
        }
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/Parameters.fxml");
    }

    @FXML
    private void click_addIP() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please Select location from project explorer first");
            return;
        }
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/AddIP.fxml");
    }

    @FXML
    private void selectAll() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("selectAllStr()");
    }

    private void initializeControls() {
        MainMenuBar.setPrefHeight(28);
    }

    @FXML
    private void click_menuItmAbout() {
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/About.fxml", "About");
    }

    @FXML
    private void click_menuItmContactUs() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.agnisys.com/contact-us/"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void click_menuItmUndo() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("undo()");
    }

    @FXML
    private void click_menuItmRedo() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("redo()");
    }

    @FXML
    private void click_menuItmCopy() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("copy()");
    }

    @FXML
    private void click_menuItmCut() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("cut()");
    }

    @FXML
    private void click_menuItmPaste() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("paste()");
    }

    @FXML
    private void click_menuItmDelete() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("deleteHtml()");
    }

    @FXML
    private void click_menuItmExit() {
        Platform.exit();
    }

//    @FXML
//    private void click_menuItmShowDesc() {
//        switch (menuItmShowDesc.getText()) {
//            case SHOWDESC:
//                ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("IDS_DESCSHOW()");
//                menuItmShowDesc.setText(HIDEDESC);
//                break;
//            case HIDEDESC:
//                ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("IDS_DESCHIDE()");
//                menuItmShowDesc.setText(SHOWDESC);
//                break;
//        }
//    }
//
//    @FXML
//    private void click_menuItmShowProp() {
//        switch (menuItmShowProp.getText()) {
//            case SHOWPROP:
//                ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("IDS_PROPSHOW()");
//                //ApplicationMainGUIController.APPLICATION_OBJECT.setViewForAllTabs(ApplicationMainGUIController.SHOWPROP);
//                //ApplicationMainGUIController.APPLICATION_OBJECT.callstatus = ApplicationMainGUIController.SHOWPROP;
//                menuItmShowProp.setText(HIDEPROP);
//                break;
//            case HIDEPROP:
//                ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("IDS_PROPHIDE()");
//                //ApplicationMainGUIController.APPLICATION_OBJECT.setViewForAllTabs(ApplicationMainGUIController.HIDEPROP);
//                //ApplicationMainGUIController.APPLICATION_OBJECT.callstatus = ApplicationMainGUIController.HIDEPROP;
//                menuItmShowProp.setText(SHOWPROP);
//                break;
//        }
//    }
    public void click_SaveFile() {
        System.out.println("call save");
        IDSUtils.saveCurrentEditedFile();
    }

    @FXML
    private void click_findAndReplace() {
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/FindAndReplace.fxml");
    }

    //<editor-fold defaultstate="collapsed" desc="click_OpenFile">
    @FXML
    public void click_OpenFile() {
//        History.getInstance().undo();
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.fileChooser();

        if (f != null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.openTextInNewTab(f);
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="click_NEWIPWizard">
    @FXML
    public void click_IPWizard() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please first select location from project explorer");
            return;
        }
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/IPWizard.fxml");
    }
    //</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="binding strings">
    private void bindStrings() {
//        menuItmShowProp.setText(HIDEPROP);
//        menuItmShowDesc.setText(HIDEDESC);

        menuFile.setText(IDSString.MENU_FILE);
        menuEdit.setText(IDSString.MENU_EDIT);
//        menuNavigator.setText(IDSString.MENU_NAVIGATOR);
//        menuRun.setText(IDSString.MENU_RUN);
//        menuTools.setText(IDSString.MENU_TOOL);
//        menuView.setText(IDSString.MENU_VIEW);

        menuFileNew.setText(IDSString.MENU_FILE_NEW);
        menuFileNewProj.setText(IDSString.MENU_FILE_NEW_PROJ);
        menuItmCreateProj.setText(IDSString.MENU_CREATE_PROJ);
        menuFileNewFile.setText(IDSString.MENU_NEW_FILE);
        menuItmNewIP.setText(IDSString.MENU_NEW_IP);
        menuItmNewReg.setText(IDSString.MENU_NEW_REG);
        menuItmNewText.setText(IDSString.MENU_NEW_TEXT);
//        menuItmNewCanvas.setText(IDSString.MENU_NEW_CANVAS);

        menuOpen.setText(IDSString.MENU_OPEN);
        menuItmOpenProj.setText(IDSString.MENU_OPEN_PROJ);
        menuItmOpenFile.setText(IDSString.MENU_OPEN_FILE);
//        menuItmCloseProj.setText(IDSString.MENU_CLOSE_PROJ);
//        menuItmImportProj.setText(IDSString.MENU_IMPORT_PROJ);
//        menuItmExportProj.setText(IDSString.MENU_EXPORT_PROJ);
//        menuItmRestartIDE.setText(IDSString.MENU_RESTART_IDS);
        menuItmSave.setText(IDSString.MENU_SAVE);
//        menuItmSaveAll.setText(IDSString.MENU_SAVE_ALL);
        menuItmExit.setText(IDSString.MENU_EXIT);

        menuItmUndo.setText(IDSString.MENU_UNDO + "    Ctrl+Z");
        menuItmRedo.setText(IDSString.MENU_REDO + "    Ctrl+Y");
//        menuItmCopy.setText(IDSString.MENU_COPY);
//        menuItmCut.setText(IDSString.MENU_CUT);
//        menuItmPaste.setText(IDSString.MENU_PASTE);
        menuItmDelete.setText(IDSString.MENU_DELETE);
        menuItmFind.setText("Find and Replace    Ctrl+F");
        menuItmSelectAll.setText("Select All    Ctrl+A");
        menuHelp.setText(IDSString.MENU_HELP);

//        menuCustToolBar.setText(IDSString.MENU_CUST_TOOLBAR);
//        menuItmIDSEditor.setText(IDSString.MENU_IDS_EDITOR);
//        menuItmGraphicalWindow.setText(IDSString.MENU_GRAPHICAL_VIEW);
//        menuItmProjExp.setText(IDSString.MENU_PROJ_EXP);
//        menuItmPropWindow.setText(IDSString.MENU_PROJ_EXP);
//        menuItmHierView.setText(IDSString.MENU_HIER_VIEW);
//        menuItmConsole.setText(IDSString.MENU_CONSOLE);
//
//        menuItmGotoFile.setText(IDSString.MENU_GOTO_FILE);
//        menuItmGotoLine.setText(IDSString.MENU_GOTO_LINE);
//
//        menuItmRunProj.setText(IDSString.MENU_RUN_PROJ);
//        menuItmSettings.setText(IDSString.MENU_SETTING);
//        menuItmConfiguration.setText(IDSString.MENU_CONFIG);
        menuItmFolder.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_MENU_FOLDER());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="click_NewTextFileWizard">
    @FXML
    public void click_NewTextFileWizard() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please first select location from project explorer");
            return;
        }
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/TextWizard.fxml");
    }
    //</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="click_NewFolderizard">
    @FXML
    public void click_NewFolderWizard() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please first select location from project explorer");
            return;
        }
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/FolderWizard.fxml");
    }
    //</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="click_NewRegFileWizard">
    @FXML
    public void click_NewRegFileWizard() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please first select location from project explorer");
            return;
        }
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/FileWizard.fxml");
    }
    //</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="click_CreateNewProject">
    @FXML
    public void click_CreateNewProject() {
        ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/NewProjectWizard.fxml");
    }
    //</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="open project click">
    @FXML
    private void click_openProject(ActionEvent event) {
        //File f = IDSUtils.folderChoose();
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        File f = directoryChooser.showDialog(com.agnisys.idsnextgen.IDSNextGen.STAGE);
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.folderChoose();
        if (f != null) {
            //saved current project settings before open new project
            IDSNextGen.APPLICATION_OBJECT.saveProjSettings();
            ApplicationMainGUIController.APPLICATION_OBJECT.bindFileTree(f.getAbsolutePath());
            ApplicationMainGUIController.APPLICATION_OBJECT.closeAllTabs();
            IDSNextGen.APPLICATION_OBJECT.loadProjConfig(f.getAbsolutePath());
            ApplicationMainGUIController.APPLICATION_OBJECT.loadOpenedFiles();
        }
    }
    //</editor-fold>

    @FXML
    private void click_createFileBtn(ActionEvent event) {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.SELECTED_FILE == null) {
            System.out.println("Warning : Please select file from project explorer");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/FileWizard.fxml");
        }
    }
}
