/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

//<editor-fold defaultstate="collapsed" desc="imports">
import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.backannotation.BackAnnotation;
import com.agnisys.idsnextgen.editorutils.HTMLEditorCustomizationSample;
import com.agnisys.idsnextgen.editorutils.HTMLParser;
import com.agnisys.idsnextgen.classes.IDSTerminal;
import com.agnisys.idsnextgen.classes.SimpleFileTreeItem;
import com.agnisys.idsnextgen.classes.WebController;
import com.agnisys.idsnextgen.editorutils.IDSNGEditorFileHandler;
import com.agnisys.idsnextgen.global.HTMLEditorInstances;
import com.agnisys.idsnextgen.global.IDSUtils;
import com.agnisys.idsnextgen.licensevalidation.IDSLicenseManager;
import com.agnisys.idsnextgen.param.ParamWriter;
import com.agnisys.idsnextgen.transformation.Transformation;
import com.sun.javafx.tk.Toolkit;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import us.agnisys.idsbatch.Transformer;
//</editor-fold>

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class ApplicationMainGUIController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fxml var init">
    @FXML
    public VBox vboxhelp;

    @FXML
    public Pane pnhelp;
    @FXML
    BorderPane borderPaneMain;
    @FXML
    Pane paneRightSideBtns;
    @FXML
    TextArea txtAreaCmd;
    @FXML
    TextArea textAreaConsole;
    @FXML
    AnchorPane anchPnConsoleContainer;
    @FXML
    ScrollPane scrPnLeftSide;
    @FXML
    ScrollPane pnl3;
    @FXML
    SplitPane splitView;
    @FXML
    Label lblProjectExplorer;
    @FXML
    Pane pnLeftSide;
    @FXML
    BorderPane borderPn;
    @FXML
    SplitPane splitPnEditorView;
    @FXML
    Button btnToolBarOpenProject;
    @FXML
    Pane pnlCentralEditor;
    @FXML
    TabPane tabPnCentralEditor;
//    @FXML
//    ToolBar toolbarEditor;
    @FXML
    Pane paneMainMenu;
    @FXML
    ScrollPane srollPnRightPane;
    @FXML
    SplitPane splitPaneRight;
    @FXML
    BorderPane brdPnRightLower;
    @FXML
    BorderPane brdPnRightUpper;
    @FXML
    Pane pnlIDSToolBar;
    @FXML
    Pane pnlEditorToolbar;
    @FXML
    Label lblrootPath;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="class variables init">
    boolean isProjExpShown = true;
    boolean isConsoleShown = true;
    boolean isRightPaneShown = true;
    boolean isGraphicalShown = true;
    boolean isHierShown = true;
    TreeView<File> fileView;
    SingleSelectionModel<Tab> selectionModel;
    public static ApplicationMainGUIController APPLICATION_OBJECT;
    private String CURRENT_PROJECT_ROOT = "";
    HTMLEditorCustomizationSample IDSHtmlEditor;
    final double MAIN_FRAME_HEIGHT = 595;
    File cutCopyFilePath = null;
    static int selectedIndex = 0;
    public File SELECTED_FILE = null;
    final public KeyCombination kb = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    final public KeyCombination kbFind = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);

    boolean isCutSelected = false;

    private String JQUERY_LIB_PATH;
    private String IDS_CSS;
    private String IDS_JS;
    private String PARAM_JS;
    IDSToolbarController idsToolbarController;
    EditorToolbarController editorToolbarController;
    public IDSTerminal idsterminal;
    int ScreenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    int ScreenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    IDSLicenseManager licensemanager = new IDSLicenseManager();
    public String licenseStatus = "";
    //public IDSNGEditorFileHandler idsngFileHandler = new IDSNGEditorFileHandler();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="tree right click menu items declare">
    final Menu addFileMenu = new Menu("Create New");
    final MenuItem open = new MenuItem("Open");
    final MenuItem openWith = new MenuItem("Open with OS");
    final MenuItem deleteFile = new MenuItem("Delete");
    final MenuItem refresh = new MenuItem("Refresh");
    final MenuItem cut = new MenuItem("Cut");
    final MenuItem copy = new MenuItem("Copy");
    final MenuItem paste = new MenuItem("Paste");
    final MenuItem rename = new MenuItem("Rename");
    final MenuItem check = new MenuItem("Check");
    final MenuItem generate = new MenuItem("Generate");
    final MenuItem openInGUI = new MenuItem("Open in GUI");
    final MenuItem openInBrowser = new MenuItem("Open Location in file System");
    final MenuItem properties = new MenuItem("Properties");
    final MenuItem addIP = new MenuItem("Add IP");
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="return instance of current active editor">
    /**
     *
     * @return instance of current active editor
     */
    public HTMLEditorCustomizationSample get_ActiveHTMLEditor() {
        try {
            Object ob = ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
            if (ob instanceof Tab) {
                String fileContent = "";

                Tab tab = (Tab) ob;
                Object obHTML = tab.getContent();
                if (obHTML instanceof HTMLEditor) {
                    HTMLEditor htmlEditor = (HTMLEditor) obHTML;
                    for (HTMLEditorCustomizationSample customHtmlEditor : HTMLEditorInstances.getHTMLInstances().getHTMLEditorList()) {
                        if (customHtmlEditor.getHTMLEditor().equals(htmlEditor)) {
                            return customHtmlEditor;
                        }
                    }
                }

            }

        } catch (Exception e) {
            System.out.println("Err save file " + e.getMessage());
        }
        return null;
    }

    public WebEngine getActiveWebEngine() {
        try {
            Object ob = ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
            if (ob instanceof Tab) {
                String fileContent = "";

                Tab tab = (Tab) ob;
                Object obHTML = tab.getContent();
                if (obHTML instanceof WebView) {
                    WebEngine eng = ((WebView) obHTML).getEngine();
                    return eng;
                }

            }

        } catch (Exception e) {
            System.out.println("Err save file " + e.getMessage());
        }
        return null;
    }
    //</editor-fold>

    public TabPane getEditorTab() {
        return tabPnCentralEditor;
    }

    public String getCURRENT_PROJECT_ROOT() {
        return CURRENT_PROJECT_ROOT;
    }

    public void setCURRENT_PROJECT_ROOT(String CURRENT_PROJECT_ROOT) {
        this.CURRENT_PROJECT_ROOT = CURRENT_PROJECT_ROOT;
    }

    //<editor-fold defaultstate="collapsed" desc="initialize project, load class">
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println("--Call ApplicationMainGUIController");
        String defaultProj = IDSNextGen.APPLICATION_OBJECT.defaultProjPath;
        //System.out.println("--default proj=" + defaultProj);
        //initilization("C:\\Users\\Agnisys\\Desktop\\GUI demo");

        System.out.println("--Default Project : " + defaultProj);
        initilization(defaultProj);
        System.out.println("--ApplicationGUIController initilization Successfully");
        setResourcePaths();
        System.out.println("--Resuouce Path successfully");
        loadWindowView();
        System.out.println("--Window View loaded Successfully");
        loadOpenedFiles();
        System.out.println("--Open previous files successfully");
        idsterminal = new IDSTerminal(textAreaConsole, txtAreaCmd);

        setLicense();
        System.out.println("--Check licence successfully pass");
    }
    //</editor-fold>

    void setLicense() {
        ArrayList<Object> listController = new ArrayList<>();
        listController.add(idsToolbarController);
        listController.add(editorToolbarController);
        licensemanager.checklicense(listController);
        licenseStatus = licensemanager.getLicenseStatus();
    }

    void setResourcePaths() {
        JQUERY_LIB_PATH = IDSUtils.loadSystemResource("ids_templates/jquery-3.2.1.min.js").getAbsolutePath();
        System.err.println("--JQUERYLIBPATH : " + JQUERY_LIB_PATH);
        IDS_CSS = IDSUtils.loadSystemResource("ids_templates/ids_template.css").getAbsolutePath();
        IDS_JS = IDSUtils.loadSystemResource("ids_templates/ids_template.js").getAbsolutePath();
        PARAM_JS = IDSUtils.loadSystemResource("ids_templates/ids_param.js").getAbsolutePath();
    }

    public static void main(String[] arg) {
        ApplicationMainGUIController ob = new ApplicationMainGUIController();
        ob.setResourcePaths();
    }

    public String getJQUERY_LIB_PATH() {
        return JQUERY_LIB_PATH;
    }

    public String getIDS_CSS() {
        return IDS_CSS;
    }

    public String getIDS_JS() {
        return IDS_JS;
    }

    public String getParam_JS() {
        return PARAM_JS;
    }

    //<editor-fold defaultstate="collapsed" desc="load window view layout">
    private void loadWindowView() {
        /* int wid = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
         //LAYOUTTEST int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 35;
         int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 60;
         System.out.println("--Screen Height="+java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
         */
//        borderPaneMain.setPrefSize(wid, height-360);
        //paneRightSideBtns.setPrefWidth(ScreenWidth);
        splitPnEditorView.setDividerPosition(0, IDSNextGen.APPLICATION_OBJECT.applicationProps.getCONSOLE_DIVIDER_POS());
        //System.out.println("--proj size=" + IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
        splitView.setDividerPosition(0, IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
        //System.out.println("--editor size=" + IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR00());
        splitView.setDividerPosition(1, IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR00());
        splitPaneRight.setDividerPosition(0, IDSNextGen.APPLICATION_OBJECT.applicationProps.getEDITOR_LEFT_UP_DOWN_POS());

        //System.out.println("00-" + IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR00() + " /n 01=" + IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="functions not in use">
    public static final String RESET = "RESET";
    public static final String HIDEPROP = "HIDEPROP";
    public static final String SHOWPROP = "SHOWPROP";
    public String callstatus = RESET;

    private void resetFileViews(WebEngine engine, String callStatus) {
        switch (callStatus) {
            case RESET:
                engine.executeScript("resetview()");
                break;
            case HIDEPROP:
                engine.executeScript("IDS_PROPHIDE()");
                break;
            case SHOWPROP:
                engine.executeScript("IDS_PROPSHOW()");
                break;
        }
    }

    public void setViewForAllTabs(String status) {
        try {
            for (Tab t : tabPnCentralEditor.getTabs()) {
                Object ob = t.getContent();
                if (ob instanceof WebView) {
                    System.out.println("-HTML=" + getStringFromWebView(((WebView) ob).getEngine()));

                    if (!t.getText().startsWith("DashBoard")) {
                        System.out.println("-FILEName=" + t.getId());
                        resetFileViews(((WebView) ob).getEngine(), status);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Err save file " + e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="load opened files, open file on program starts">
    public void loadOpenedFiles() {
        List<String> files = IDSNextGen.APPLICATION_OBJECT.applicationProps.getOPENED_FILE_PATHS();
        if (files != null) {

            try {
                for (String myfile : files) {
                    if (myfile != null && !myfile.isEmpty()) {
                        openTextInNewTab(new File(myfile));
                    }
                }
                //setViewForAllTabs(SHOWPROP);
            } catch (Exception e) {
                System.err.println("Err (loadOpenFiles) : " + e.getMessage());
            }
        }
    }
    //</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="initilization, load application other module views">
    public void initilization(String filePath) {

        bindFileTree(filePath);

        //project navigator width change
        pnLeftSide.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (fileView != null) {
                    fileView.setPrefWidth((double) newValue);
                }
                //lblProjectExplorer.setPrefWidth((double) newValue);
                scrPnLeftSide.setPrefWidth((double) newValue);
                borderPn.setPrefWidth((double) newValue);
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setMAIN_EDITOR00(splitView.getDividerPositions()[0]);
//                System.out.println("--proj exp size=" + splitView.getDividerPositions()[0]);
            }
        });

        //console height change
        splitView.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (fileView != null) {
                    fileView.setPrefHeight((double) newValue);
                }
                scrPnLeftSide.setPrefHeight((double) newValue - 30);

                splitPaneRight.setPrefHeight((double) newValue);
                textAreaConsole.setPrefHeight(MAIN_FRAME_HEIGHT - (double) newValue);
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setCONSOLE_DIVIDER_POS(splitPnEditorView.getDividerPositions()[0]);
                //System.out.println("--console size=" + splitPnEditorView.getDividerPositions()[0]);
            }
        });

        textAreaConsole.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                    Object newValue) {
                textAreaConsole.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                //use Double.MIN_VALUE to scroll to the top
            }
        });

        //testing
        //System.out.println("--newValue=" + newValue);
        if (ScreenWidth > 1900) {
            paneRightSideBtns.setPrefWidth(ScreenWidth * 0.34375);
        } else if (ScreenWidth < 1900) {
            paneRightSideBtns.setPrefWidth(ScreenWidth * 0.015373352);
        }
        //System.out.println("--Editor width=" + paneRightSideBtns.getPrefWidth());
        if (ScreenHeight > 1000) {
            tabPnCentralEditor.setPrefHeight(ScreenHeight * 0.49074074);
            scrPnLeftSide.setPrefHeight(ScreenHeight * 0.49074074);
            borderPaneMain.setPrefSize(ScreenWidth, ScreenHeight - 420);
        } else if (ScreenHeight < 1000) {
            tabPnCentralEditor.setPrefHeight(ScreenHeight * 0.729166667);
            scrPnLeftSide.setPrefHeight(ScreenHeight * 0.929166667);
            borderPaneMain.setPrefSize(ScreenWidth, ScreenHeight - 60);
            //scrPnLeftSide.setPrefHeight(ScreenHeight * 0.2);
        }
//        System.out.println("--pnlHeight=" + scrPnLeftSide.getPrefHeight());
        System.out.println("--screenHeight=" + ScreenHeight);
        //System.out.println("--Editor height=" + tabPnCentralEditor.getPrefHeight());
//        tabPnCentralEditor.setPrefHeight(560);
//        paneRightSideBtns.setPrefWidth(21);
        //testing end

        //central editor width change
        pnlCentralEditor.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                tabPnCentralEditor.setPrefWidth((double) newValue);
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setMAIN_EDITOR01(splitView.getDividerPositions()[1]);
//                System.out.println("--central size=" + splitView.getDividerPositions()[1]);
            }
        });

        //tabPnCentralEditor.setPrefHeight(200);
        //right side pane size change
        srollPnRightPane.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                splitPaneRight.setPrefWidth((double) newValue);
                brdPnRightUpper.setPrefWidth((double) newValue);
                brdPnRightLower.setPrefWidth((double) newValue);
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setEDITOR_LEFT_UP_DOWN_POS(splitPaneRight.getDividerPositions()[0]);
                //System.out.println("--right size==" + splitPaneRight.getDividerPositions()[0]);
            }
        });
        borderPn.setPrefWidth(500);
        //scrPnLeftSide.setMaxWidth(IDSConstants.LEFT_PANE_PREF_VAL);

        Label lbl = new Label("Project Navigator");
        Pane pane = new Pane();
        pane.getChildren().add(lbl);
        //pane.getChildren().add(fileView);
        //scrPnLeftSide.setContent(pane);
//        scrPnLeftSide.setContent(fileView);
//        menuRegSpec.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                System.out.println("you have click on menu");
//            }
//
//        });
        try {
            MenuBar mainMenuBar;
            mainMenuBar = FXMLLoader.load(IDSNextGen.APPLICATION_OBJECT.getClass().getResource("/designs/MainMenu.fxml"));
            paneMainMenu.getChildren().add(mainMenuBar);
        } catch (Exception ex) {
            System.out.println("Error load " + ex.getMessage());
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            AnchorPane IDSToolBar;
            FXMLLoader loader;
            //loader = FXMLLoader.load(IDSNextGen.APPLICATION_OBJECT.getClass().getResource("/designs/IDSToolbar.fxml"));
            loader = new FXMLLoader(getClass().getResource("/designs/IDSToolbar.fxml"));
            IDSToolBar = loader.load();
            idsToolbarController = loader.getController();

            pnlIDSToolBar.getChildren().add(IDSToolBar);
        } catch (Exception ex) {
            System.out.println("Error load " + ex.getMessage());
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Pane EditorToolbar;
            FXMLLoader loader;
            //loader = FXMLLoader.load(IDSNextGen.APPLICATION_OBJECT.getClass().getResource("/designs/IDSToolbar.fxml"));
            loader = new FXMLLoader(getClass().getResource("/designs/EditorToolbar.fxml"));
            EditorToolbar = loader.load();
            editorToolbarController = loader.getController();

            pnlEditorToolbar.getChildren().add(EditorToolbar);
        } catch (Exception ex) {
            System.out.println("Error load " + ex.getMessage());
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabPnCentralEditor.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
                        //System.out.println("Tab Selection changed=" + getTabFilePath(newTab));
                        if (getTabFilePath(newTab).endsWith(".idsng")) {
                            // disableButtonOnTabSelection(true);
                        } else {
                            //disableButtonOnTabSelection(true);
                        }
                    }

                }
        );

//        idsterminal = new IDSTerminal(textAreaConsole, txtAreaCmd);
        //disableButtonOnTabSelection(true); //rough code, delete later
        APPLICATION_OBJECT = this;
    }
    //</editor-fold>

    /**
     * @param tab
     * @return file path from given tab
     */
    public String getTabFilePath(Tab tab) {
        return tab.getTooltip().getText();
    }

    //<editor-fold defaultstate="collapsed" desc="disableButtons according to file selection">
    public void disableButtonOnTabSelection(boolean isEnable) {
        idsToolbarController.btnBlock.setDisable(isEnable);
//        idsToolbarController.btnBoard.setDisable(isEnable);
        idsToolbarController.btnBusDomain.setDisable(isEnable);
        idsToolbarController.btnChip.setDisable(isEnable);
        idsToolbarController.btnDefine.setDisable(isEnable);
        idsToolbarController.btnEnum.setDisable(isEnable);
        idsToolbarController.btnMemory.setDisable(isEnable);
        idsToolbarController.btnRef.setDisable(isEnable);
        idsToolbarController.btnRegGrp.setDisable(isEnable);
        idsToolbarController.btnRegister.setDisable(isEnable);
//        idsToolbarController.btnSequ.setDisable(isEnable);
        idsToolbarController.btnSignal.setDisable(isEnable);
//        idsToolbarController.btnSystem.setDisable(isEnable);
        idsToolbarController.btnVarient.setDisable(isEnable);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Open Project. Project Explorer bind. Bind project files in tree and add tree menus">
    public void bindFileTree(String str) {
        if (str == null) {
            return;
        }
        File file = new File(str);
        lblrootPath.setText(str + "$");

        com.sun.javafx.tk.FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        /*Label label = new Label("My name is Warren. I love Java.");
         label.setFont(Font.font("Consolas", FontWeight.THIN, FontPosture.REGULAR, 16));*/
        float lblwidth = fontLoader.computeStringWidth(lblrootPath.getText(), lblrootPath.getFont());
        //System.out.println("The label's width is: " + lblwidth);
        //System.out.println("Screen width is: " + ScreenWidth);
        //System.out.println("--workspace width=" + lblwidth);
        if (ScreenWidth > 1900) {
            txtAreaCmd.setPrefWidth(ScreenWidth - lblwidth - 700);
        } else {
            txtAreaCmd.setPrefWidth(ScreenWidth - lblwidth - 50);
        }
        //

        CURRENT_PROJECT_ROOT = file.getAbsolutePath();
        //Node img = new Image(IDSNextGen.class.getResourceAsStream("/images/ids32.png"));
//        Node img1 = new ImageView(new Image(IDSNextGen.class.getResourceAsStream("/images/ids16.png")));

        fileView = new TreeView<>(new SimpleFileTreeItem(file));
//        fileView = new TreeView<>(new SimpleFileTreeItem(file, img1));

        fileView.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {

            @Override
            public TreeCell<File> call(TreeView<File> tv) {
                return new TreeCell<File>() {

                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);

                        //setText((empty || item == null) ? "" : item.getName().replace(".html", ""));
                        setText((empty || item == null) ? "" : item.getName());

                        //code to set icon on tree node
                        if (item != null && empty != true) {
                            Node img;
                            if (item.isDirectory()) {
                                img = new ImageView(new Image(IDSNextGen.class.getResourceAsStream("/images/dir.png")));
                            } else {
                                if (item.getName().endsWith(".idsng")) {
                                    img = new ImageView(new Image(IDSNextGen.class.getResourceAsStream("/images/ids10.png")));
                                } else {
                                    img = new ImageView(new Image(IDSNextGen.class.getResourceAsStream("/images/file.png")));
                                }
                            }
                            setGraphic(img);

                        } else {
                            setGraphic(null);
                        }

                    }

                };
            }
        });

        fileView.getStylesheets().add(getClass().getResource("/styles/treeView.css").toExternalForm());
        fileView.getTreeItem(0).setExpanded(true);
        fileView.setContextMenu(addTreeRightClickMenuItems());
        fileView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try {
                    /*
                     Object ob = event.getSource();
                     ((TreeView<>(File,Node)) event.getSource()).getSelectionModel();
                     */
//                    Object ob = ((TreeView<File>) event.getSource()).getSelectionModel();
//                    Object ob2 = ((TreeView<File>) event.getSource()).getSelectionModel().getSelectedItem();
                    SELECTED_FILE = ((TreeView<File>) event.getSource()).getSelectionModel().getSelectedItem().getValue();
                    if (event.getClickCount() == 2) {
                        mouseClickOnTree(SELECTED_FILE);
                    }
                } catch (Exception e) {
                    System.err.println("Error ApplicationMainGUIController file bind on tree : " + e.getMessage());
                }
            }
        });

        fileView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<TreeItem>() {
            @Override
            public void onChanged(Change<? extends TreeItem> change) {
                selectedIndex = fileView.getSelectionModel().getSelectedIndex();
//                System.out.println("--selectedIndex=" + selectedIndex);
                enableDisableProjRGMenus(fileView.getTreeItem(selectedIndex).isLeaf());
            }
        });

        //System.out.println("--selectedInex22=" + selectedIndex);
        try {
            if (selectedIndex > 0) {
                if (fileView.getTreeItem(selectedIndex).isLeaf()) {
                    fileView.getTreeItem(selectedIndex).getParent().setExpanded(true);
                    // System.out.println("--parent leaf=" + fileView.getTreeItem(selectedIndex).getParent().isLeaf());
//            System.out.println("--parent index=" + fileView.getTreeItem(selectedIndex).getParent().C);

                } else {
                    fileView.getTreeItem(selectedIndex).setExpanded(true);
                }
            }
        } catch (Exception e) {
            System.err.println("Warning in expanding project explorer tree! " + e.getMessage());
        }

        scrPnLeftSide.setContent(fileView);
    }
    //</editor-fold>

    void expandTree(TreeItem<File> item) {
        if (item.isLeaf()) {
            //expandTree(item.get);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="project right click menus enable disable ">
    void enableDisableProjRGMenus(boolean isFile) {
        if (!isFile) {
            addIP.setVisible(true);
            addFileMenu.setVisible(true);
            open.setVisible(false);
            openWith.setVisible(false);
            check.setVisible(false);
            generate.setVisible(false);
            openInGUI.setVisible(false);
            paste.setVisible(true);
        } else {
            addIP.setVisible(false);
            addFileMenu.setVisible(false);
            open.setVisible(true);
            openWith.setVisible(true);
            check.setVisible(true);
            generate.setVisible(true);
            openInGUI.setVisible(true);
            paste.setVisible(false);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="tree right click menus bind">
    ContextMenu addTreeRightClickMenuItems() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem newIP = new MenuItem("IP");
        newIP.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (SELECTED_FILE == null) {
                    System.out.println("Warning : Please select file from project explorer");
                } else {
                    showFXMLWindow("/designs/IPWizard.fxml");
                }
            }
        });

        MenuItem regSpec = new MenuItem("Register Spec");
        regSpec.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (SELECTED_FILE == null) {
                    System.out.println("Warning : Please select file from project explorer");
                } else {
                    showFXMLWindow("/designs/FileWizard.fxml");
                }
            }
        });

        MenuItem paramSpec = new MenuItem("Parameters");
        paramSpec.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (SELECTED_FILE == null) {
                    IDSUtils.showErrorMessage("Warning : Please select file from project explorer");
                } else {
                    showFXMLWindow("/designs/Parameters.fxml");
                }
            }
        });

        MenuItem Text = new MenuItem("Text");
        Text.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (SELECTED_FILE == null) {
                    System.out.println("Warning : Please select file from project explorer");
                } else {
                    showFXMLWindow("/designs/TextWizard.fxml");
                }
            }
        });

        MenuItem Canvas = new MenuItem("Canvas");

        MenuItem Folder = new MenuItem("Folder");
        Folder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (SELECTED_FILE == null) {
                    System.out.println("Warning : Please select file from project explorer");
                } else {
                    showFXMLWindow("/designs/FolderWizard.fxml");
                }
            }
        });

        addFileMenu.getItems().clear();
        addFileMenu.getItems().addAll(newIP, regSpec, paramSpec, Canvas, new SeparatorMenuItem(), Text, Folder);

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mouseClickOnTree(SELECTED_FILE);
            }
        });

        openWith.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWithOS(SELECTED_FILE);
            }
        });

        deleteFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteFile(SELECTED_FILE);
                refreshProject();
            }
        });

        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshProject();
            }
        });

        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cutCopyFilePath = SELECTED_FILE;
                paste.setDisable(false);
                isCutSelected = true;
            }
        });

        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cutCopyFilePath = SELECTED_FILE;
                paste.setDisable(false);
                isCutSelected = false;
            }
        });

        paste.setDisable(true);
        paste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (cutCopyFilePath != null) {
                        if (cutCopyFilePath.isFile()) {
                            IDSUtils.copyFileToDir(cutCopyFilePath, SELECTED_FILE);
                            if (isCutSelected) {
                                deleteFile(cutCopyFilePath);
                            }
                        } else {
                            if (isCutSelected) {
                                IDSUtils.cutFolder(cutCopyFilePath, SELECTED_FILE);
                            } else {
                                IDSUtils.copyFolder(cutCopyFilePath, SELECTED_FILE);
                            }
                        }

                        refreshProjectWithIndex();
                        //refreshProject();
                        paste.setDisable(true);
                        cutCopyFilePath = null;
                    }
                } catch (Exception ex) {
                    System.err.println("Err in copying file " + ex.getMessage());
                    Logger.getLogger(ApplicationMainGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        openInBrowser.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                IDSUtils.openInExplorer(SELECTED_FILE.getAbsolutePath());
                //IDSUtils.openInExplorer("/home/sumeet/IDSNG/IDSNexGen-linux-1.1-beta");//delete this code
            }
        });

        rename.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showFXMLWindow("/designs/Rename.fxml");
            }
        });

        Canvas.setDisable(true);
        openInGUI.setDisable(true);
        properties.setDisable(false);
        //rename.setDisable(true);
        properties.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showFXMLWindow("/designs/PropertyWindow.fxml");
            }
        });

        addIP.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showFXMLWindow("/designs/AddIP.fxml");
            }
        });

        check.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (SELECTED_FILE.getName().endsWith(".idsng")) {
                    IDSUtils.showErrorMessage("ERROR : idsng file only check from editor, Please open this file first and then check");
                } else {
                    Transformation.getTransformer().checkCode(SELECTED_FILE.getAbsolutePath());
                }
            }
        });

        generate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (SELECTED_FILE.getName().endsWith(".idsng")) {
                    IDSUtils.showErrorMessage("ERROR : idsng file only Run from editor, Please open this file first and then Run");
                } else {
                    Transformation.getTransformer().generateCode(SELECTED_FILE.getAbsolutePath());
                }
            }
        });

        contextMenu.getItems().addAll(addFileMenu, addIP, new SeparatorMenuItem(), open, openWith, openInBrowser, new SeparatorMenuItem(), cut, copy, paste, new SeparatorMenuItem(), deleteFile, rename, refresh,
                new SeparatorMenuItem(), check, generate, openInGUI, new SeparatorMenuItem(), properties
        );
        return contextMenu;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="open file with OS">
    private void openWithOS(File file) {
        try {
            if (IDSUtils.isWindow()) {
                Desktop.getDesktop().open(file);
            } else if (IDSUtils.isLinux()) {
                Runtime.getRuntime().exec("xdg-open " + file.getAbsolutePath());
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationMainGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="delete file or folder">
    public void deleteFile(File file) {
        try {
            if (IDSUtils.deleteConfirmation()) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    IDSUtils.deleteFolder(file);
                }
                System.out.println("----fileDeleted");
                if (selectedIndex > 0) {
                    // selectedIndex--;
                }
            }
        } catch (Exception e) {
            System.err.println("Err deleting file : " + e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="refresh project">
    public void refreshProject() {
        selectedIndex = 0;
        bindFileTree(CURRENT_PROJECT_ROOT);
    }

    void refreshProjectWithIndex() {
        bindFileTree(CURRENT_PROJECT_ROOT);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update editor tab name/id">
    public void updateTabName(File file, File oldFile) {
        List<Tab> tabs = tabPnCentralEditor.getTabs();
        for (Tab t : tabs) {
            if (t.getId().equals(oldFile.getAbsolutePath())) {
                t.setId(file.getAbsolutePath());
                t.setText(file.getName());
                break;
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="open IDSNG file in webview">
    private Tab openIDSNGFileInWebView(Tab tab, String filePath) throws Exception {
        //LineManipulator ob = new LineManipulator();
        webview = new WebView();
        webengine = webview.getEngine();
        webengine.setJavaScriptEnabled(true);
        String modifiedPath;
        if (!(new File(filePath)).getName().equals("DashBoard.html")) {
            modifiedPath = IDSNGEditorFileHandler.getObj().getHtmlPath(filePath);
            String fileContent = HTMLParser.addIDSStyleAndCss(filePath);
            BackAnnotation.save(new File(filePath), fileContent);
        } else {
            modifiedPath = filePath;
        }

        tab.setId(modifiedPath);
        modifiedPath = "file:///" + modifiedPath;
//        System.out.println("-you are opening : " + modifiedPath);
        webengine.load(modifiedPath);

//        webengine.load("file:///C:\\Users\\Agnisys\\Documents\\NewProject100\\IP2\\propCheck.idsng");
        //file:///C:\Users\Agnisys\Documents\NewProject100\IP2\propCheck.idsng
       /* String str = Transformer.readFileAsString(filePath);
         webengine.loadContent(str, "text/html");*/
//        System.out.println("--file content=" + fileContent);
        /*if (fileContent != null || !"".equals(fileContent)) {
         webengine.loadContent(fileContent);
         ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("reloadPage()");
         } else {
         webengine.load(modifiedPath);
         }*/
        //Pane pane = ob.loadGraph();
        //testing code
        webengine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
                //System.out.println("--web engine changed event call");
                if (newState == State.SUCCEEDED) {
                    window = (JSObject) webengine.executeScript("window");
                    window.setMember("clickController", new WebController());
                }
            }
        }
        );
        window = (JSObject) webengine.executeScript("window");
        window.setMember("clickController", new WebController());

        tab.setContent(webview);
        webview.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {

            @Override
            public void handle(WebEvent<String> event) {
                System.out.println("HTML Console : " + event.getData());

                if (event.getData().startsWith("{error")) {
                    showIDSGUIError(event.getData());
                } else {
                    showAlert(event.getData());
                }
            }
        });

        webview.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (ApplicationMainGUIController.APPLICATION_OBJECT.kb.match(event)) {
                    //IDSUtils.saveCurrentEditedFile(CURRENT_PROJECT_ROOT, CURRENT_PROJECT_ROOT);
                    //System.out.println("save match from htmlE editor");
                    IDSUtils.saveCurrentEditedFile();
                    WebController.setSaveSymbol();
                } else if (ApplicationMainGUIController.APPLICATION_OBJECT.kbFind.match(event)) {
                    WebController.openFind();
                } else {
                    WebController.setUnsaveSymbol();
                }

            }
        });
        /*
         Timer timer = new Timer(3000, new ActionListener() {

         @Override
         public void actionPerformed(java.awt.event.ActionEvent e) {
         System.out.println("run after 3 sec");
         resetFileViews(webengine, callstatus);
         }
         });
         timer.setRepeats(false); // Only execute once
         timer.start();
         */

        return tab;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="open file in webview">
    private Tab openfileInWebView(Tab tab, String filePath) throws Exception {
        //LineManipulator ob = new LineManipulator();
        webview = new WebView();
        webengine = webview.getEngine();
        webengine.setJavaScriptEnabled(true);
        String modifiedPath;
        tab.setId(filePath);
        modifiedPath = "file:///" + filePath;

        webengine.load(modifiedPath);

        tab.setContent(webview);
        webview.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {

            @Override
            public void handle(WebEvent<String> event) {
                System.out.println("HTML Console : " + event.getData());

                if (event.getData().startsWith("{error")) {
                    showIDSGUIError(event.getData());
                } else {
                    showAlert(event.getData());
                }
            }
        });

        webview.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {

                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (ApplicationMainGUIController.APPLICATION_OBJECT.kb.match(event)) {
                    //IDSUtils.saveCurrentEditedFile(CURRENT_PROJECT_ROOT, CURRENT_PROJECT_ROOT);
                    //System.out.println("save match from htmlE editor");
                    IDSUtils.saveCurrentWebViewFile();
                    //WebController.setSaveSymbol();
                } else if (ApplicationMainGUIController.APPLICATION_OBJECT.kbFind.match(event)) {
                    WebController.openFind();
                } else {
                    //WebController.setUnsaveSymbol();
                }

            }
        });

        return tab;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="open file in new tab">
    public void openTextInNewTab(File file) {
        if (file != null && file.exists() && file.isFile()) {
            try {
                count++;
                Tab tab = null;
                List<Tab> tabs = tabPnCentralEditor.getTabs();
                for (Tab t : tabs) {
                    if (t.getTooltip().getText().equals(file.getAbsolutePath())) {
                        tab = t;
                        break;
                    }
                }
                if (tab == null) {
                    tab = new Tab();
//                    tab.setText(FilenameUtils.removeExtension(file.getName()));
//                    tab.setTooltip(new Tooltip(file.getAbsolutePath()));
//                    tab.setId(file.getAbsolutePath());
//                    tab.setClosable(true);

                    String str = readFile(file.getAbsolutePath(), StandardCharsets.UTF_8);
                    //if (file.getName().endsWith(".idsng") || file.getName().endsWith(".htm") || file.getName().endsWith(".html")) {
                    if (file.getName().endsWith(".idsng") || file.getName().endsWith(".htm")
                            || file.getName().endsWith(".h") || file.getName().endsWith(".htmids")) {
                        /*
                         IDSHtmlEditor = new HTMLEditorCustomizationSample(str);
                         //                        IDSHtmlEditor = new HTMLEditorCustomizationSample(file);
                         //printConsole(IDSHtmlEditor.getHTMLText());
                         tab.setContent(IDSHtmlEditor.getHTMLEditor());
                         HTMLEditorInstances.getHTMLInstances().addHTMLEditor(IDSHtmlEditor);
                         */
                        tab.setText(FilenameUtils.removeExtension(file.getName()));
                        openIDSNGFileInWebView(tab, file.getAbsolutePath());
                        //resetFileViews((((WebView) tab.getContent())).getEngine(), SHOWPROP);
                        //(tab, "C:/Users/Agnisys/Desktop/GUI demo/test/wysiwyg-editor-master/html/events/image_removed.html");

                    } else if (file.getName().endsWith(".html")) {
                        tab.setText(FilenameUtils.removeExtension(file.getName()));
                        openfileInWebView(tab, file.getAbsolutePath());
                    } /*else if (file.getName().endsWith(".idsng") || file.getName().endsWith(".htm") || file.getName().endsWith(".html")) {

                     IDSHtmlEditor = new HTMLEditorCustomizationSample(str);
                     //                        IDSHtmlEditor = new HTMLEditorCustomizationSample(file);
                     //printConsole(IDSHtmlEditor.getHTMLText());
                     tab.setContent(IDSHtmlEditor.getHTMLEditor());
                     HTMLEditorInstances.getHTMLInstances().addHTMLEditor(IDSHtmlEditor);

                     }*/ /*else if (file.getName().endsWith(".v")) {
                     openIDSNGFileInWebView(tab);
                     }*/ else {
                        tab.setText(file.getName());
                        TextArea txt = new TextArea(str);
                        txt.setOnKeyPressed(new EventHandler<KeyEvent>() {

                            @Override
                            public void handle(KeyEvent event) {
                                txtEditorKeyPressEvent(event);
                            }
                        });
                        tab.setContent(txt);
                        tab.setId(file.getAbsolutePath());
                    }

                    tab.setTooltip(new Tooltip(file.getAbsolutePath()));
//                    tab.setId(file.getAbsolutePath());
                    tab.setClosable(true);

                    tab.setOnCloseRequest(new EventHandler<Event>() {

                        @Override
                        public void handle(Event event) {
                            tabCloseEvent(event);
                        }

                    });

                    tabPnCentralEditor.getTabs().add(tab);
                    selectionModel = tabPnCentralEditor.getSelectionModel();
                }

                selectionModel.select(tab);

            } catch (Exception e) {
                System.err.println("Err Opentextintotab : " + e.getMessage());
            }
        }
    }
    //</editor-fold>

    private void updateJSAndCSSFile(String filePath) {
        String htmlContents = Transformer.readFileAsString(filePath);
        org.jsoup.nodes.Document doc = Jsoup.parse(htmlContents);

    }

    //<editor-fold defaultstate="collapsed" desc="Save file. keyPress event on text Area">
    private void txtEditorKeyPressEvent(KeyEvent event) {
//        ChangeManager manager = new ChangeManager();

        if (kb.match(event)) {
//            System.out.println("save this file");
            IDSUtils.saveCurrentEditedFile();
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="close all tabs from editor">
    public void closeAllTabs() {
        tabPnCentralEditor.getTabs().clear();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="tab Close event, fire when  editor tab close">
    void tabCloseEvent(Event event) {
        IDSUtils.onTabClose(((Tab) event.getSource()));
        IDSNGEditorFileHandler.getObj().deleteCacheHTMLFile(((Tab) event.getSource()).getId());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="open project explorer pane">
    @FXML
    public void click_btnOpnProjecExp(ActionEvent event) {
        System.out.println("webEngine=" + webengine);
        System.out.println("window=" + window);

        if (isProjExpShown) {
            splitView.setDividerPosition(0, 0.0);
            isProjExpShown = false;
        } else {
            splitView.setDividerPosition(0, 0.2);
            pnLeftSide.setPrefWidth(500);
            pnLeftSide.setFocusTraversable(true);
            isProjExpShown = true;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="close project explorer">
    @FXML
    public void click_btnClsProjectExp(ActionEvent event) {
        splitView.setDividerPosition(0, 0.0);
        isProjExpShown = false;
    }
    //</editor-fold>

    @FXML
    private void onclick_paramview() {
        ParamWriter paramwriter = new ParamWriter();
        paramwriter.onParamView();
    }

    @FXML
    private void onclick_regview() {
        ParamWriter paramwriter = new ParamWriter();
        paramwriter.onRegisterView();
        //String str = "<h2>Helllo world</h2>";
        //getActiveWebEngine().executeScript("regviewupdate('" + str + "')");
    }

    //<editor-fold defaultstate="collapsed" desc="click_graphicalViewBtn">
    @FXML
    private void click_btnGraphicalView(ActionEvent event) {
        double temp = 0;
        //splitPnEditorView.setDividerPosition(1, 0.20);
        splitPaneRight.setDividerPosition(0, 1.0);
        double va = splitView.getDividerPositions()[1];
        if (splitView.getDividerPositions()[1] < 0.98) {
            splitView.setDividerPosition(1, 1.0);
        } else {
            splitView.setDividerPosition(1, 0.85);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="click_graphicalViewBtn">
    @FXML
    private void click_btnGraphicalView2(ActionEvent event) {
        double temp = 0;
        if (splitView.getDividerPositions()[1] < 0.98) {
            //right pane is visible
            if (splitPaneRight.getDividerPositions()[0] > 0.1) {
                //graphical pane is visible
                temp = splitPaneRight.getDividerPositions()[0];
                splitPaneRight.setDividerPosition(0, 0.0);

                IDSNextGen.APPLICATION_OBJECT.applicationProps.setEDITOR_LEFT_UP_DOWN_POS(temp);

                if (temp > 0.95) {
                    temp = splitView.getDividerPositions()[1];
                    splitView.setDividerPosition(1, 1.0);
                    IDSNextGen.APPLICATION_OBJECT.applicationProps.setMAIN_EDITOR01(temp);
                }
            } else {
                //graphical pane is not visible
                temp = IDSNextGen.APPLICATION_OBJECT.applicationProps.getEDITOR_LEFT_UP_DOWN_POS();
//                System.out.println("--right height=" + temp);
//                if (temp < 0.2) {
//                    temp = 0.5;
//                }
                splitPaneRight.setDividerPosition(0, temp);
            }
            //System.out.println("-value2222=" + IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
        } else {
            //splitView is not visible
            System.out.println("-value=" + IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
            if (IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01() > 0.98) {
                splitView.setDividerPosition(1, 0.75);
            } else {
                splitView.setDividerPosition(1, IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
            }
            splitPaneRight.setDividerPosition(0, IDSNextGen.APPLICATION_OBJECT.applicationProps.getEDITOR_LEFT_UP_DOWN_POS());
        }

        //System.out.println("--getDividerPos=" + splitPaneRight.getDividerPositions()[0]);
//        if (isGraphicalShown && !isHierShown) {
//            splitView.setDividerPosition(1, 1.0);
//            isGraphicalShown = false;
//
//        } else if (!isGraphicalShown && !isHierShown) {
//            isGraphicalShown = true;
//            splitView.setDividerPosition(1, 0.80);
//            splitPaneRight.setDividerPosition(0, 1.0);
//
//        } else if (!isGraphicalShown && isHierShown) {
//            isGraphicalShown = true;
//            splitView.setDividerPosition(1, 0.80);
//            splitPaneRight.setDividerPosition(0, 0.5);
//
//        } else if (isGraphicalShown && isHierShown) {
//            isGraphicalShown = false;
//            splitView.setDividerPosition(1, 0.80);
//            splitPaneRight.setDividerPosition(0, 0.0);
//        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="click_btnHierarchView">
    @FXML
    private void click_btnHieraricalView(ActionEvent event) {

        double temp = 0;
        if (splitView.getDividerPositions()[1] < 0.98) {
            //right pane is visible
            System.out.println("right size=" + splitPaneRight.getDividerPositions()[0]);

            if (splitPaneRight.getDividerPositions()[0] < 0.98) {
                //graphical pane is visible
                temp = splitPaneRight.getDividerPositions()[0];
                splitPaneRight.setDividerPosition(0, 1.0);
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setEDITOR_LEFT_UP_DOWN_POS(temp);

                if (temp < 0.1) {
                    temp = splitView.getDividerPositions()[1];
                    splitView.setDividerPosition(1, 1.0);
                    IDSNextGen.APPLICATION_OBJECT.applicationProps.setMAIN_EDITOR01(temp);
                }

            } else {
                //graphical pane is not visible
                //splitPaneRight.setDividerPosition(0, IDSNextGen.APPLICATION_OBJECT.applicationProps.getEDITOR_LEFT_UP_DOWN_POS());
                temp = IDSNextGen.APPLICATION_OBJECT.applicationProps.getEDITOR_LEFT_UP_DOWN_POS();
                System.out.println("--right height=" + temp);
//                if (temp > 0.98) {
//                    temp = 0.5;
//                }
                splitPaneRight.setDividerPosition(0, temp);
            }
        } else {
            //splitView is not visible
            System.out.println("-value=" + IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
            if (IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01() > 0.98) {
                splitView.setDividerPosition(1, 0.75);
            } else {
                splitView.setDividerPosition(1, IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
            }

            //splitView.setDividerPosition(1, IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01());
            splitPaneRight.setDividerPosition(0, IDSNextGen.APPLICATION_OBJECT.applicationProps.getEDITOR_LEFT_UP_DOWN_POS());
        }

//        if (isHierShown && !isGraphicalShown) {
//            splitView.setDividerPosition(1, 1.0);
//            isHierShown = false;
//
//        } else if (!isGraphicalShown && !isHierShown) {
//            isHierShown = true;
//            splitView.setDividerPosition(1, 0.80);
//            splitPaneRight.setDividerPosition(0, 0.0);
//
//        } else if (!isHierShown && isGraphicalShown) {
//            isHierShown = true;
//            splitView.setDividerPosition(1, 0.80);
//            splitPaneRight.setDividerPosition(0, 0.5);
//
//        } else if (isGraphicalShown && isHierShown) {
//            isHierShown = false;
//            splitView.setDividerPosition(1, 0.80);
//            splitPaneRight.setDividerPosition(0, 1.0);
//        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="file chooser">
    public File fileChooser(File defaultLocation) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("All Files", "*.*"));
        fileChooser.setInitialDirectory(defaultLocation);
        File selectedFile = fileChooser.showOpenDialog(com.agnisys.idsnextgen.IDSNextGen.STAGE);
        return selectedFile;
    }

    public File fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(com.agnisys.idsnextgen.IDSNextGen.STAGE);
        return selectedFile;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="image chooser">
    public File imageChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(com.agnisys.idsnextgen.IDSNextGen.STAGE);
        return selectedFile;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="folder chooser">
    public File folderChoose() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory
                = directoryChooser.showDialog(com.agnisys.idsnextgen.IDSNextGen.STAGE);

        return selectedDirectory;
    }

    public File folderChoose(File defaultLoc) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(defaultLoc);
        File selectedDirectory
                = directoryChooser.showDialog(com.agnisys.idsnextgen.IDSNextGen.STAGE);

        return selectedDirectory;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="open new fxml window with title param">
    public void showFXMLWindow(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(IDSNextGen.APPLICATION_OBJECT.getClass().getResource(fxmlPath));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initOwner(com.agnisys.idsnextgen.IDSNextGen.STAGE.getScene().getWindow());
            stage.setTitle(title);
            stage.show();
            //Alert.show("hello this is just a test");
        } catch (Exception e) {
            System.out.println("Error opening new project wizard : " + e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="open new fxml window">
    public void showFXMLWindow(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(IDSNextGen.APPLICATION_OBJECT.getClass().getResource(fxmlPath));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initOwner(com.agnisys.idsnextgen.IDSNextGen.STAGE.getScene().getWindow());
            stage.setTitle(IDSNextGen.APPLICATION_OBJECT.idsString.get_APPLICATION_TITLE());
            stage.show();
            //Alert.show("hello this is just a test");
        } catch (Exception e) {
            System.out.println("Error showFXMLWindow : " + e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="read string from file">
    public static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        //System.out.println("--readAllBits=" + new String(encoded, encoding));
        return new String(encoded, encoding);
    }
    //</editor-fold >

    //<editor-fold defaultstate="collapsed" desc="double click on tree node">
    private void mouseClickOnTree(File file) {
        System.out.println("--file=" + file.getAbsolutePath());
        try {
            if (file.isFile()) {
//                IDSUtils.addIntoOpenedFileArry(file.getAbsolutePath());
//                IDSNextGen.APPLICATION_OBJECT.applicationProps.getOPENED_FILE_PATHS().add(file.getAbsolutePath());
                openTextInNewTab(file);
                //System.out.println("--double click call");
            } /*else {
             System.out.println("--incorrect file format");
             }*/

        } catch (Exception e) {
            System.err.println("Err mouseClickOnTree : " + e.getMessage());
        }
    }
    //</editor-fold>

    public void addNewFileInProjExp(File file) {
        bindFileTree(getCURRENT_PROJECT_ROOT());
        openTextInNewTab(file);
//        IDSNextGen.APPLICATION_OBJECT.applicationProps.getOPENED_FILE_PATHS().add(file.getAbsolutePath());
    }

    /**
     * add new file into project explorer without opening it into new tab
     */
    public void addNewFileInProjExp() {
        bindFileTree(getCURRENT_PROJECT_ROOT());
    }

    public void printConsole(String msg) {
        IDSUtils.showErrorMessage(msg);
        //textAreaConsole.setText(textAreaConsole.getText() + "\n" + msg);
    }

    @FXML
    public void click_btnConsoleClose(ActionEvent event) {
        closeConsole();
    }

    @FXML
    public void click_btnCOnsoleOpen(ActionEvent event) {
        if (isConsoleShown) {
            closeConsole();
        } else {
            splitPnEditorView.setDividerPosition(0, 0.8);
            isConsoleShown = true;
        }
    }

    private void closeConsole() {
        splitPnEditorView.setDividerPosition(0, 1.0);
        isConsoleShown = false;
    }

    @FXML
    private void click_btnHierachyHide(ActionEvent event) {
        System.out.println("btnHierarchy call");
        splitPaneRight.setDividerPosition(0, 1.0);
        //brdPnRightLower.setDividerPosition(0, 1.0);
    }

    private void addNewFile(File selectedFile) {
        if (selectedFile.isDirectory()) {

        }
    }

    private void initExplorerMenuItem() {

    }

//    @FXML
//    TextArea txtAreaGetHtmlText;
    @FXML
    private void click_btnGetHtmlText(ActionEvent event) {
        //txtAreaGetHtmlText.setText(htm.getHtmlText());
    }

    static public WebEngine webengine;
    private WebView webview;
    public static JSObject window;

    private void showAlert(String message) {
        Dialog<Void> alert = new Dialog<>();
        alert.getDialogPane().setContentText(message);
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    public JSONObject jsonObj;

    public void showIDSGUIError(String data) {
        try {
            //if (data.toLowerCase().startsWith("error"))
            {
                //System.out.println("--returned data=" + data);
                //data = data.split(" ")[0];

                //JSONObject jsonObj = new JSONObject("{interests : [{interestKey:Dogs}, {interestKey:Cats}]}");
                //JSONObject jsonObj = new JSONObject("{error : [{msg:GUI error, id:6}, {msg:GUI-G error,id:7}]}");
                jsonObj = new JSONObject(data);

                JSONArray array = jsonObj.getJSONArray("error");
                /*
                 for (int i = 0; i < array.length(); i++) {
                 System.out.println("--id=" + array.getJSONObject(i).getString("id"));
                 }
                 */

                showErrorWindow("/designs/ErrorWindow.fxml");

                // ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("jump('target1')");
            }
        } catch (Exception e) {
            System.err.println("Error showIDSGUIError : " + e.getMessage());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="open new fxml window">
    public void showErrorWindow(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(IDSNextGen.APPLICATION_OBJECT.getClass().getResource(fxmlPath));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initOwner(com.agnisys.idsnextgen.IDSNextGen.STAGE.getScene().getWindow());
            stage.setTitle("Validation Error!");
            stage.setX(ScreenWidth * 0.7);
            stage.setY(ScreenHeight * 0.2);
            stage.show();
            //Alert.show("hello this is just a test");
        } catch (Exception e) {
            System.out.println("Error showFXMLWindow : " + e.getMessage());
        }
    }
    //</editor-fold>

//    public String getStringFromWebView() {
//        String html = (String) webengine.executeScript("document.documentElement.outerHTML");
//        return html;
//    }
    public String getStringFromWebView(WebEngine _webengine) {
        String html = (String) _webengine.executeScript("document.documentElement.outerHTML");
        return html;
    }

    HTMLEditor htm;
    int count = 1;

    @FXML
    private void onDragDetected(MouseEvent event) {
        /* allow any transfer mode */
        Dragboard db = ((Button) event.getSource()).startDragAndDrop(TransferMode.MOVE);

        /* put a string on dragboard */
        ClipboardContent content = new ClipboardContent();

        content.putString("Drag Me");
        db.setContent(content);

        event.consume();
    }

    @FXML
    private void onDragOver(DragEvent event) {
        /* data is dragged over the target */
        //System.out.println("onDragOver");

        /* accept it only if it is  not dragged from the same node
         * and if it has a string data */
        // if (event.getGestureSource() != txtArea) {
            /* allow for both copying and moving, whatever user chooses */
        event.acceptTransferModes(TransferMode.MOVE);
        //}

        event.consume();
    }

    @FXML
    private void onDragEntered(DragEvent event) {
        /* the drag-and-drop gesture entered the target */
        System.out.println("onDragEntered");
        /* show to the user that it is an actual gesture target */
//        if (event.getGestureSource() != pnlCanvas) {
//
//        }

        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        System.out.println("mouse dropped");
//        Dragboard db = event.getDragboard();
//
//        boolean success = false;
//        if (db.hasString()) {
//            //target.setText(db.getString());
//            success = true;
//
//        }
//
//        Button src = (Button) event.getGestureSource();
//        if (src.getId().equals("btnAction")) {
//            System.out.println("this is src buttn");
//            src = new Button(db.getString());
//            src.setId("btn" + Math.random());
//        } else {
//            System.out.println("this is new button");
//
//        }
//        System.out.println("event.getSceneX()-" + event.getSceneX() + " event.getSceney()-" + event.getSceneY());
//        double x = event.getSceneX() - 67;
//        double y = event.getSceneY() - 55;
//        src.setLayoutX(x);
//        src.setLayoutY(y);
//        src.setOnDragDetected(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("new btn event");
//                onDragDetected(event);
//            }
//
//        });
//
//        src.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                onMouseClicked(event);
//            }
//
//        });
//
//        pnlCanvas.getChildren().add(src);
//
//        event.acceptTransferModes(TransferMode.MOVE);
//        /* let the source know whether the string was successfully
//         * transferred and used */
//        event.setDropCompleted(success);
//
//        event.consume();

    }

    @FXML
    private void onMouseClicked(MouseEvent event) {
        System.out.println("mouse event call=" + event.getButton());
        if (event.getButton().toString().equals("SECONDARY")) {
            System.out.println("you have pressed secendary button");

            final ContextMenu contextMenu = new ContextMenu();
            MenuItem cut = new MenuItem("Cut");
            cut.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    System.out.println("--you have click cut button");
                }

            });
            MenuItem copy = new MenuItem("Copy");
            MenuItem paste = new MenuItem("Paste");
            contextMenu.getItems().addAll(cut, copy, paste);
            contextMenu.show(((Button) event.getSource()), event.getScreenX(), event.getScreenY());
            /*
             try {
             Pane pane = FXMLLoader.load(getClass().getResource("FXMLTest.fxml"));

             Parent root = FXMLLoader.load(getClass().getResource("FXMLTest.fxml"));

             Scene scene = new Scene(root);

             Pane p = (Pane) event.getSource();
             //stage.setScene(scene);
             //stage.show();

             } catch (Exception e) {
             }
             */
        }
    }

}
