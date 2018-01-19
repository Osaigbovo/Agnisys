/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.classes;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.global.ApplicationProperties;
import com.agnisys.idsnextgen.global.IDSUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class ProjectConfiguration {

    public static void writeIntoProjConfig(ApplicationProperties prop, String appConfigPath) {
        try {
            System.out.println("--prop file path new " + appConfigPath);
            File xmlFile = new File(appConfigPath);
            Document document;
            Element root;
            if (xmlFile.exists() && xmlFile.isFile()) {
                System.out.println("--proj file do exits");
                SAXReader saxReader = new SAXReader();
                document = saxReader.read(xmlFile);
                root = document.getRootElement();
                Element ele = (Element) root.selectSingleNode("files");
                if (ele != null) {
                    ele.detach();
                }
            } else {
                document = DocumentHelper.createDocument();
                root = document.addElement("idsng");
            }

            List<String> opendfiles = IDSUtils.getFileListFromTabEditor(ApplicationMainGUIController.APPLICATION_OBJECT.getEditorTab());

//            if (prop.getOPENED_FILE_PATHS() != null && prop.getOPENED_FILE_PATHS().size() > 0)
            if (opendfiles != null && opendfiles.size() > 0) {
                Element childNode = root.addElement("files");
                for (String file : opendfiles) {
                    childNode.addElement("file").setText(file);
                }
            }

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;

            FileOutputStream fl = new FileOutputStream(xmlFile);

            writer = new XMLWriter(fl, format);
            writer.write(document);
            writer.flush();
            System.out.println("--project file created " + appConfigPath);
        } catch (DocumentException | IOException e) {
            System.err.println("Error reading file=" + e.getMessage());
        }
    }

    /**
     * bind project configuration into properties
     *
     * @param projConfig
     */
    public static void loadProjConfig(File projConfig) {
        List<String> file = new ArrayList<>();
        try {
            System.out.println("--PROJECT CONFIG : " + projConfig);

            if (projConfig.exists() && projConfig.isFile()) {
                Document document;
                SAXReader saxReader = new SAXReader();
                document = saxReader.read(projConfig);
                Element root = document.getRootElement();
                Element files = (Element) root.selectSingleNode("files");
                if (files != null) {
                    List<Element> fileArr = files.selectNodes("file");
                    if (fileArr != null) {
                        for (Element ele : fileArr) {
                            file.add(ele.getText());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Err readProjConfig : " + e.getMessage());
        }
        /*set IDS-NextGen Dashboard page here for the first time when project load*/
        String dashboardPath = IDSUtils.loadSystemResource("ids_templates/DashBoard.html").getAbsolutePath();
        file.add(dashboardPath);
        IDSNextGen.APPLICATION_OBJECT.applicationProps.setOPENED_FILE_PATHS(file);
    }
}
