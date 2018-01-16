/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.editorutils;

import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import java.io.File;
import java.nio.file.Files;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Sumeet
 * @desc Handle path for IDSNG to HTML extension file
 */
public class IDSNGEditorFileHandler {

    private static IDSNGEditorFileHandler ob = null;

    private IDSNGEditorFileHandler() {
    }

    public static IDSNGEditorFileHandler getObj() {
        if (ob == null) {
            ob = new IDSNGEditorFileHandler();
        }
        return ob;
    }

    public void copyCurrentHtmlContentToIDSNGOrgFile() {
        try {

            Object ob = ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab().getSelectionModel().getSelectedItem();
            if (ob instanceof Tab) {
                String fileContent = "";
                Tab tab = (Tab) ob;
                Object obHTML = tab.getContent();
                WebEngine webEng;
                if (obHTML instanceof WebView) {
                    webEng = ((WebView) obHTML).getEngine();
                    fileContent = ApplicationMainGUIController.APPLICATION_OBJECT.getStringFromWebView(webEng);
                } else {
                    System.out.println("Error Editor not Supported");
                }

                // fileContent = ApplicationMainGUIController.APPLICATION_OBJECT.getStringFromWebView();
                if (!fileContent.isEmpty()) {

                    FileUtils.writeStringToFile(new File(tab.getId()), fileContent);
                    FileUtils.writeStringToFile(new File(tab.getTooltip().getText()), fileContent);

                }
            }

        } catch (Exception e) {
            System.err.println("Error copyHtmlContentToIDSNGOrgFile : " + e.getMessage());
        }
    }

    public void copyCurrentHtmlContentToIDSNGOrgFile(Tab tab) {
        try {
            String fileContent = "";

            Object obHTML = tab.getContent();
            WebEngine webEng = null;
            if (obHTML instanceof WebView) {
                webEng = ((WebView) obHTML).getEngine();
                fileContent = ApplicationMainGUIController.APPLICATION_OBJECT.getStringFromWebView(webEng);
            } else {
                System.out.println("Error Editor not Supported");
            }

            // fileContent = ApplicationMainGUIController.APPLICATION_OBJECT.getStringFromWebView();
            if (!fileContent.isEmpty()) {

                FileUtils.writeStringToFile(new File(tab.getId()), fileContent);
                FileUtils.writeStringToFile(new File(tab.getTooltip().getText()), fileContent);

            }

        } catch (Exception e) {
            System.err.println("Error copyHtmlContentToIDSNGOrgFile : " + e.getMessage());
        }
    }

    public void deleteCacheHTMLFile(String filePath) {
        try {
            File f = new File(filePath);
            if (f.getName().startsWith(".~$")) {
                (new File(filePath)).delete();
            }
        } catch (Exception e) {
            System.err.println("Error in deleteCacheHTMLFile : " + e.getMessage());
        }
    }

    /**
     * create copy of idsng file in HTML format and return its path
     *
     * @param idsngPath
     * @return String
     */
    public String getHtmlPath(String idsngPath) {
        try {
            if (idsngPath.endsWith(".idsng")) {
                File src = new File(idsngPath);
                File desc = new File(src.getParent() + File.separator + ".~$" + FilenameUtils.removeExtension(src.getName()) + ".htm");
                if (desc.exists()) {
                    if (src.lastModified() < desc.lastModified()) {
                        return desc.getAbsolutePath();
                    } else {
                        desc.delete();
                    }
                }

                Files.copy(src.toPath(), desc.toPath());
//                try {
//                    Files.setAttribute(desc.toPath(), "dos:hidden", false);
//                } catch (Exception e) {
//                }
                return desc.getAbsolutePath();
            } else {
                return idsngPath;
            }
        } catch (Exception e) {
            System.err.println("Error getHTMLPath : " + e.getMessage());
        }
        return null;
    }
}
