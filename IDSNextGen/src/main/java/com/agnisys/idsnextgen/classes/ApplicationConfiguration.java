/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.classes;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.global.IDSUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Sumeet
 */
public class ApplicationConfiguration {

    /**
     * save application configuration into user home directory
     *
     * @param mainIns Instance of ApplicationMainGUIController class
     * @param path string path of user home directory
     */
    public static void writeIntoAppConfig(ApplicationMainGUIController mainIns, String path) {
        try {
//            System.out.println("--file path " + path);
            File xmlFile = new File(path);
            Document document = null;
            if (!xmlFile.exists() || (xmlFile.exists() && !xmlFile.isFile())) {
//                System.out.println("--proj file not exits");
                document = DocumentHelper.createDocument();
                Element root = document.addElement("idsng");

                Element childNode = root.addElement("default_proj");
                childNode.setText(mainIns.getCURRENT_PROJECT_ROOT());

                root.addElement("console_size").setText(String.valueOf(IDSNextGen.APPLICATION_OBJECT.applicationProps.getCONSOLE_DIVIDER_POS()));
                root.addElement("mainEditor_size00").setText(String.valueOf(IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR00()));
                root.addElement("mainEditor_size01").setText(String.valueOf(IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01()));
                root.addElement("rightview_size").setText(String.valueOf(IDSNextGen.APPLICATION_OBJECT.applicationProps.getEDITOR_LEFT_UP_DOWN_POS()));

                // Pretty print the document to System.out
            } else if (xmlFile.exists() && xmlFile.isFile()) {
//                System.out.println("--proj file do exits");
                SAXReader saxReader = new SAXReader();
                document = saxReader.read(xmlFile);
                Element root = document.getRootElement();
                root.element("default_proj").setText(mainIns.getCURRENT_PROJECT_ROOT());
                root.element("console_size").setText(String.valueOf(IDSNextGen.APPLICATION_OBJECT.applicationProps.getCONSOLE_DIVIDER_POS()));
                root.element("mainEditor_size00").setText(String.valueOf(IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR00()));
                root.element("mainEditor_size01").setText(String.valueOf(IDSNextGen.APPLICATION_OBJECT.applicationProps.getMAIN_EDITOR01()));
                root.element("rightview_size").setText(String.valueOf(IDSNextGen.APPLICATION_OBJECT.applicationProps.getEDITOR_LEFT_UP_DOWN_POS()));
            }

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;

            FileOutputStream fl = new FileOutputStream(xmlFile);

            writer = new XMLWriter(fl, format);
            writer.write(document);
            writer.flush();
            System.out.println("--project file created " + path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (DocumentException ex) {
            Logger.getLogger(IDSUtils.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.err.println("Error reading file=" + e.getMessage());
        }

    }

    /**
     * this function return last opened project path and set application layout
     * like console, project explorer size and width
     *
     * @param appConfig
     * @return
     */
    public static String loadAppConfig(File appConfig) {
        System.out.println("--APPLICATION CONFIG : " + appConfig.getAbsolutePath());
        if (appConfig.exists() && appConfig.isFile()) {
            Document document;
            SAXReader saxReader = new SAXReader();
            try {
                document = saxReader.read(appConfig);
                Element root = document.getRootElement();
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setCONSOLE_DIVIDER_POS(Double.parseDouble(root.element("console_size").getText()));
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setMAIN_EDITOR00(Double.parseDouble(root.element("mainEditor_size01").getText()));
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setEDITOR_LEFT_UP_DOWN_POS(Double.parseDouble(root.element("rightview_size").getText()));
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setMAIN_EDITOR01(Double.parseDouble(root.element("mainEditor_size00").getText()));
                IDSNextGen.APPLICATION_OBJECT.applicationProps.setDEFAULT_PATH(root.element("default_proj").getText());
                return root.element("default_proj").getText();
            } catch (Exception e) {
                System.err.println("Err (read project config) : " + e.getMessage());
            }
        }

        return null;
    }
}
