/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.global;

import com.agnisys.idsmanager.IDSManager;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.controllers.config.properties.General;
import com.agnisys.idsnextgen.editorutils.IDSNGEditorFileHandler;
import com.agnisys.idsnextgen.transformation.Transformation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Sumeet
 */
public class IDSUtils {

    public void readLogFromConfig() {

        String CONFIGXMLPATH = Transformation.getTransformer().configMainObj.getConfigFilePath();
        File configFile = new File(CONFIGXMLPATH);
        if (configFile.exists()) {
            Document document;
            SAXReader saxReader = new SAXReader();
            try {
                document = saxReader.read(configFile);
                Element root = document.getRootElement();
                System.out.println("--is log true=" + Boolean.parseBoolean(root.elementText("log")));
                General.getGeneralIns().setLog(Boolean.parseBoolean(root.elementText("log")));

            } catch (Exception e) {
                System.err.println("Err (loadConfigIntoProp) : " + e.getMessage());
            }
        }
    }

    public static boolean deleteConfirmation() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("IDS-NG Confirmation");
        alert.setHeaderText("Delete this file");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void traceLog(String msg) {
        if (General.getGeneralIns().isLog()) {
            IDSManager.printNgLog(msg);
        }
    }

    public static void initilizeLogFile(String filePath) {
        if (General.getGeneralIns().isLog()) {
            File file = new File(filePath);
            if (file.isFile()) {
                file = new File(file.getParent() + File.separator + ".ids" + File.separator + file.getName() + ".txt");
            } else {
                file = new File(file.getAbsolutePath() + File.separator + ".ids" + File.separator + file.getName() + ".txt");
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            System.out.println("--log file location : " + file.getAbsolutePath());
            IDSManager.initializeLog(file);
            printOnTerminal("--log file location : " + file.getAbsolutePath());
        }
    }

    public static void printOnTerminal(String msg) {
        ApplicationMainGUIController.APPLICATION_OBJECT.idsterminal.setIDSTerminalText(msg);
    }

    public static LocalDateTime getCurrentTime() {
        return java.time.LocalDateTime.now();
    }

    public static void print(String printMsg) {
        if (General.getGeneralIns().isLog()) {
            //IDSManager function call
        } else {
            System.out.println(printMsg);
        }
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    public static void showInfoMessage(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("IDS-Next Generation Info");
//        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void showErrorMessage(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("IDS Error");
//        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static String getStringFromFile(File file) throws FileNotFoundException {
        String content = new Scanner(file).useDelimiter("\\Z").next();
        return content;
    }

    public static List<String> getFileListFromTabEditor(TabPane tabPane) {
        List<String> files = null;
        if (tabPane != null) {
            List<Tab> tabs = tabPane.getTabs();
            files = new ArrayList<>();
            for (Tab tab : tabs) {
                //files.add(tab.getId());
                files.add(tab.getTooltip().getText());
                onTabClose(tab);
            }
        }
        return files;
    }

    public static void onTabClose(Tab tab) {
        IDSNGEditorFileHandler.getObj().deleteCacheHTMLFile(tab.getId());
        IDSNGEditorFileHandler.getObj().copyCurrentHtmlContentToIDSNGOrgFile(tab);
    }

    public static void addIntoOpenedFileArry(String value) {
//        if (IDSNextGen.APPLICATION_OBJECT.applicationProps.getOPENED_FILE_PATHS() != null) {
//            for (String str : IDSNextGen.APPLICATION_OBJECT.applicationProps.getOPENED_FILE_PATHS()) {
//                if (str.equals(value)) {
//                    return;
//                }
//            }
//            IDSNextGen.APPLICATION_OBJECT.applicationProps.getOPENED_FILE_PATHS().add(value);
//        }
    }

    public static boolean rename(File src, String newName) {
        File dest = new File(src.getParent() + File.separator + newName);
        return src.renameTo(dest);
    }

    public static void deleteFolder(File file) throws IOException {
        FileUtils.deleteDirectory(file);
    }

    public static void copyFolder(File src, File dest)
            throws IOException {
        dest = new File(dest.getAbsolutePath() + File.separator + src.getName());
        FileUtils.copyDirectory(src, dest);
    }

    public static void cutFolder(File src, File dest) throws IOException {
        copyFolder(src, dest);
        deleteFolder(src);
    }

    public static void openInExplorer(String path) {
        try {
            if (isWindow()) {
                Runtime.getRuntime().exec("explorer.exe /select," + path);
            } else if (isLinux()) {
                Runtime.getRuntime().exec("xdg-open " + path);
            } else {
                showErrorMessage("Error : Unknown");
            }

            //Desktop.getDesktop().desktop.open(new File("///home/user/mypath"));
            //Desktop.getDesktop().browse(new URI("file:///" + path));
            /*System.out.println("--requested path : " + path);
             if (Desktop.isDesktopSupported()) {
             //Desktop.getDesktop().open(new File(path));

             File file = new File(path);
             if (file.isDirectory()) {
             java.awt.Desktop.getDesktop().open(file);
             } else {
             java.awt.Desktop.getDesktop().open(file.getParentFile());
             }
             } else {
             System.out.println("not supported");
             }*/
        } catch (Exception ex) {
            System.err.println("Error openInExplorer : " + ex.getMessage());
            //Logger.getLogger(IDSUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isWindow() {
        String OS = System.getProperty("os.name");
        return OS.toLowerCase().contains("win");
    }

    public static boolean isLinux() {
        String OS = System.getProperty("os.name");
        return OS.toLowerCase().contains("nux");
    }

    public static WebView getCurrentWebview() {
        //System.out.println("Save this file");
        try {
            Object ob = ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
            if (ob instanceof Tab) {

                Tab tab = (Tab) ob;
                Object obHTML = tab.getContent();
                if (obHTML instanceof WebView) {
                    return (WebView) obHTML;
                } else {
                    System.out.println("Error Editor not Supported");
                }
            }
        } catch (Exception e) {
            System.out.println("Err save file " + e.getMessage());
        }
        return null;
    }

    public static void saveCurrentWebViewFile() {
        try {
            Object ob = ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
            if (ob instanceof Tab) {
                String fileContent = "";

                Tab tab = (Tab) ob;
                Object obHTML = tab.getContent();
                WebEngine webEng;
                if (obHTML instanceof HTMLEditor) {
                    fileContent = ((HTMLEditor) obHTML).getHtmlText();
                } else if (obHTML instanceof TextArea) {
                    fileContent = ((TextArea) obHTML).getText();
                } else if (obHTML instanceof WebView) {
                    webEng = ((WebView) obHTML).getEngine();
                    fileContent = ApplicationMainGUIController.APPLICATION_OBJECT.getStringFromWebView(webEng);
                } else {
                    System.out.println("Error Editor not Supported");
                }

                // fileContent = ApplicationMainGUIController.APPLICATION_OBJECT.getStringFromWebView();
                if (!fileContent.isEmpty()) {
                    FileUtils.writeStringToFile(new File(tab.getId()), fileContent);
                    /*
                     if (webEng != null) {
                     IDSFileUtils.saveToIDSNG(new File(tab.getTooltip().getText()), fileContent);
                     }
                     */
                }
            }

        } catch (Exception e) {
            System.out.println("Err save file " + e.getMessage());
        }
    }

    public static void saveCurrentEditedFile() {
        //System.out.println("Save this file");
        try {
            Object ob = ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
            if (ob instanceof Tab) {
                String fileContent = "";

                Tab tab = (Tab) ob;
                Object obHTML = tab.getContent();
                WebEngine webEng = null;
                if (obHTML instanceof HTMLEditor) {
                    fileContent = ((HTMLEditor) obHTML).getHtmlText();
                } else if (obHTML instanceof TextArea) {
                    fileContent = ((TextArea) obHTML).getText();
                } else if (obHTML instanceof WebView) {
                    webEng = ((WebView) obHTML).getEngine();
                    fileContent = ApplicationMainGUIController.APPLICATION_OBJECT.getStringFromWebView(webEng);
                } else {
                    System.out.println("Error Editor not Supported");
                }

                // fileContent = ApplicationMainGUIController.APPLICATION_OBJECT.getStringFromWebView();
                if (!fileContent.isEmpty()) {
                    FileUtils.writeStringToFile(new File(tab.getId()), fileContent);
                    if (webEng != null) {
                        IDSFileUtils.saveToIDSNG(new File(tab.getTooltip().getText()), fileContent);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Err save file " + e.getMessage());
        }
    }

    public static String getSheetName() {
        TextInputDialog dialog = new TextInputDialog("Sheet1");
        dialog.setTitle("IDS Next-Gen");
        dialog.setHeaderText("Excel specification");
        dialog.setContentText("Please enter sheet name :");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your name: " + result.get());
            return result.get();
        }
        return null;
    }

    public static String getActiveFilePath() {
        String path = null;
        Object ob = ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
        if (ob instanceof Tab) {

            //tab id store html file path and tab tooltip store idsng original filepath
            Tab tab = (Tab) ob;
            //path = tab.getTooltip().getText();
            path = tab.getId();
        }

        return path;
    }

    public static String getActiveOrgFilePath() {
        String path = null;
        Object ob = ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
        if (ob instanceof Tab) {

            Tab tab = (Tab) ob;
            path = tab.getTooltip().getText();
            //path = tab.getId();
        }

        return path;
    }

    public static List<String> removeFromList(List<String> list, Object key) {
        for (String ob : list) {
            if (ob.equals(key)) {
                list.remove(ob);
                break;
            }
        }
        return list;
    }

    public static void copyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static void cutPasteFileToDir(File source, File dir) throws IOException {
        copyFile(source, dir);
        ApplicationMainGUIController.APPLICATION_OBJECT.deleteFile(source);
    }

    public static void copyFileToDir(File source, File dir) throws IOException {
        File dirFile = new File(dir.getAbsoluteFile() + File.separator + source.getName());
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dirFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static File loadSystemResource(String path) {
        try {
            System.out.println("--Resource Path : " + path);
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
            if (inputStream == null) {
                inputStream = ApplicationMainGUIController.APPLICATION_OBJECT.getClass().getResourceAsStream(path);
                //InputStream is = ClassLoader.getSystemResourceAsStream(res);
                //System.out.println("--Updated input stream : " + inputStream);
            }
            String tarPath = System.getProperty("user.dir") + File.separator + "idsng";

            //File targetFile = new File(System.getProperty("user.dir") + File.separator + "idsng" + File.separator + (new File(path)).getName());
            File targetFile = new File(tarPath);
            targetFile.mkdirs();
            targetFile = new File(tarPath + File.separator + (new File(path)).getName());
            //System.out.println("--Target File : " + targetFile.getAbsolutePath());
            //System.out.println("--Source : " + inputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            FileWriter fw = new FileWriter(targetFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            bw.write(sb.toString());

            bw.close();
            fw.close();

            return targetFile;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Err loadSystemResource : " + e.getMessage());
        }
        return null;
    }
}
