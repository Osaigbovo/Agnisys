/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.transformation;

import com.agnisys.idsmanager.IDSManager;
import com.agnisys.idsnextgen.backannotation.BackAnnotation;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.controllers.config.ConfigMainController;
import com.agnisys.idsnextgen.editorutils.IDSNGEditorFileHandler;
import com.agnisys.idsnextgen.global.IDSUtils;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import us.agnisys.idsbatch.Transformer;

/**
 *
 * @author Sumeet
 */
public class Transformation {

    private static Transformation transformation;
    String outDir = null;
    IDSManager idsmanager = new IDSManager();
    public ConfigMainController configMainObj = new ConfigMainController();
    public final String OUTDIR_NAME = "output";
    public final String UNEXPECTED_ERROR = "Unexpected Error ! This is becouse of spec's language error or parsing error. Please review your document";

    public String getOutDir() {
        String filePath = IDSUtils.getActiveFilePath();
        outDir = (new File(filePath)).getParent() + File.separator + OUTDIR_NAME;
        return outDir;
    }

    private Transformation() {
    }

    public static Transformation getTransformer() {
        if (transformation == null) {
            transformation = new Transformation();
        }
        return transformation;
    }

    public String runProcess(String[] cmd) throws IOException, InterruptedException {

        //String jarPath = IDSUtils.loadSystemResource("supportfiles")
//        URL jarPath = getClass().getResource("supportfiles/IDSBatch.jar");
//        URL jarPath2 = getClass().getResource("IMPORT_26.png");
//        URL jarPath3 = getClass().getResource("supportfiles/IDSBatch.jar");
//        URL jarPath = getClass().getResource("supportfiles/IDSBatch.jar");
//        URL jarPath = getClass().getResource("supportfiles/IDSBatch.jar");
//        System.out.println("--JarPath=" + jarPath.getPath());
        String jarPath = (new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath())).getParent() + File.separator + "src/IDSBatch.jar";
        System.out.println("--Batch running : " + IDSUtils.getCurrentTimeStamp());
        Process ps = Runtime.getRuntime().exec(cmd);
        ps.waitFor();
        System.out.println("--Batch running end : " + IDSUtils.getCurrentTimeStamp());
        java.io.InputStream is = ps.getInputStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "UTF-8");
        String theString = writer.toString();

        return theString;
    }

    public void runCheck() {
        //System.out.println("--Check Start : " + IDSUtils.getCurrentTimeStamp());

        String filePath = IDSUtils.getActiveOrgFilePath();
        checkCode(filePath);
    }

    public void checkCode(String filePath) {
        IDSUtils.initilizeLogFile(filePath);
        IDSUtils.traceLog("--Check starts at : " + IDSUtils.getCurrentTime());

        String messFromBatch = validateSpec(true, filePath);
        IDSUtils.traceLog("--return from batch : " + messFromBatch);
        /*
         if (isErrorFound(messFromBatch)) {
         IDSUtils.showErrorMessage(getErrorMessage(messFromBatch));
         } else {
         IDSUtils.showInfoMessage("Document Checked Successfully !");
         }
         */
        System.out.println("--messFromBatch=" + messFromBatch);
        IDSUtils.print("--Check End : " + IDSUtils.getCurrentTime());
        IDSUtils.traceLog("--Check End : " + IDSUtils.getCurrentTime());

        //IDSUtils.showInfoMessage("--msgfrombatch=" + messFromBatch);
        if (messFromBatch != null && !messFromBatch.equals(GUIBATCHERROR) && !messFromBatch.equals("0")) {
            IDSUtils.showErrorMessage(getErrorMessage(messFromBatch));
            ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
        } else if (messFromBatch != null && messFromBatch.equals(GUIBATCHERROR)) {

        } else if (messFromBatch != null && messFromBatch.equals("0")) {
            IDSUtils.showInfoMessage("Document Checked Successfully !");
            ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
        } else {
            IDSUtils.showInfoMessage(UNEXPECTED_ERROR + " : " + messFromBatch);
        }
        ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
    }

    private String getErrorMessage(String msg) {
        String[] msgs = msg.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String str : msgs) {
            if (str.startsWith("<CMD")) {

            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static final String GUIBATCHERROR = "GUI_Batch ERROR";

    public void runGenerate() {
        System.out.println("--Generate Start : " + IDSUtils.getCurrentTimeStamp());
        String filePath = IDSUtils.getActiveOrgFilePath();
        generateCode(filePath);
    }

    public void generateCode(String filePath) {
        String messFromBatch = validateSpec(false, filePath);
        System.out.println("--messFromBatch=" + messFromBatch);

        if (messFromBatch != null && !messFromBatch.equals(GUIBATCHERROR) && !messFromBatch.equals("0")) {
            IDSUtils.showErrorMessage(getErrorMessage(messFromBatch));
            ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
        } else if (messFromBatch != null && messFromBatch.equals(GUIBATCHERROR)) {

        } else if (messFromBatch != null && messFromBatch.equals("0")) {
            IDSUtils.showInfoMessage("Output Generated !");
            ApplicationMainGUIController.APPLICATION_OBJECT.refreshProject();
        } else {
            IDSUtils.showInfoMessage(UNEXPECTED_ERROR);
        }

        System.out.println("--Generate End : " + IDSUtils.getCurrentTimeStamp());
    }

    boolean isErrorFound(String message) {
        return message.toLowerCase().contains("error");
    }

    /**
     * @return return null if no GUI check error found else return GUIBATCHERROR
     *
     */
    private String runGUIChecks() {
        System.out.println("--Running GUI checks...");
        Boolean guicheck = (Boolean) ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("runGUICheck()");

//        IDSUtils.saveCurrentEditedFile();
        if (guicheck) {
            return GUIBATCHERROR;
        }
        return null;
    }

    public String validateSpec(boolean isCheck, String filePath) {

        System.out.println("--run validation on file : " + filePath);
        String output = null;
        String configPath = configMainObj.getConfigFilePath();
        outDir = (new File(filePath)).getParent();
        System.out.println("--Config file path=" + configPath);

        if (filePath.endsWith(".idsng")) {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setTabID()");
            String guicheck = runGUIChecks();
            IDSNGEditorFileHandler.getObj().copyCurrentHtmlContentToIDSNGOrgFile();
            /*
             For GUI check javafx call runGUICheck() function on javascript and this function alert GUI error json string list if error
             occures, else it does not call alert and return null. there is javascript alert listner call in ApplicationMainGUIController
             which override javascript alert. so when javascript call alert it override here in javafx and show GUI error. Thanks
             */
            if (guicheck != null) {
                return guicheck;
            }

            filePath = IDSUtils.getActiveOrgFilePath();
//            outDir = (new File(filePath)).getParent();
            System.out.println("--Active file path=" + filePath);

            if (configPath.equals("") || !(new File(configPath)).exists()) {
                configMainObj.saveConfigIntoXMLFile();
            } else {
                editConfigForCheck(isCheck);
            }

            String[] cmd;
            if (isCheck) {
                //cmd = new String[]{filePath, "-config", configPath, "-out", "xrsl", "-dir", outDir + File.separator + ".idsng", "-reset"};
                outDir = outDir + File.separator + ".idsng";
                cmd = new String[]{"-reset", filePath, "-config", configPath, "-dir", outDir};
            } else {
                outDir += File.separator + OUTDIR_NAME;
                cmd = new String[]{"-reset", filePath, "-config", configPath, "-dir", outDir};
            }

            System.out.println("--cmd : " + Arrays.toString(cmd));
            output = IDSManager.ngBatchWrapper(cmd);
            System.out.println("--IDSBATCH OUTPUT : " + output);
            //IDSUtils.showInfoMessage("--b=" + output);
            if (output != null && (output.equals("0") || output.toLowerCase().contains("error-a"))) {
                output = callBackAnnotation(IDSUtils.getActiveFilePath(), outDir);
                if (output != null && !output.equals("0")) {
                    ApplicationMainGUIController.APPLICATION_OBJECT.showIDSGUIError(output);
//                IDSNGEditorFileHandler.getObj().copyHtmlContentToIDSNGOrgFile(filePath, IDSUtils.getActiveOrgFilePath());
                    return GUIBATCHERROR;
                }
            }
            return output;
        } else if (filePath.endsWith(".docx")) {
            String[] cmd;
            if (isCheck) {
                outDir = outDir + File.separator + ".idsng";
                cmd = new String[]{"-reset", filePath, "-nocache", "-config", configPath, "-dir", outDir};
            } else {
                outDir += File.separator + OUTDIR_NAME;
                cmd = new String[]{"-reset", filePath, "-nocache", "-config", configPath, "-dir", outDir};
            }

            System.out.println("--cmd : " + Arrays.toString(cmd));
            output = IDSManager.ngBatchWrapper(cmd);
        } else if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx")
                || filePath.endsWith(".csv")) {
            String sheetname = IDSUtils.getSheetName();
//            System.out.println("--sheetName=" + sheetname);
            if (sheetname != null) {
                String[] cmd;
                if (isCheck) {
                    outDir = outDir + File.separator + ".idsng";
                    cmd = new String[]{"-reset", filePath, "-config", configPath, "-dir", outDir, "-sheet_name", sheetname};
                } else {
                    outDir += File.separator + OUTDIR_NAME;
                    cmd = new String[]{"-reset", filePath, "-config", configPath, "-dir", outDir, "-sheet_name", sheetname};
                }

                System.out.println("--cmd : " + Arrays.toString(cmd));
                output = IDSManager.ngBatchWrapper(cmd);
            }

        } else if (filePath.endsWith(".xml") || filePath.endsWith(".xrsl") || filePath.endsWith(".rdl") || filePath.endsWith(".odt") || filePath.endsWith(".ralf") || filePath.endsWith(".ods")) {
            String[] cmd;
            if (isCheck) {
                outDir = outDir + File.separator + ".idsng";
                cmd = new String[]{"-reset", filePath, "-config", configPath, "-dir", outDir};
            } else {
                outDir += File.separator + OUTDIR_NAME;
                cmd = new String[]{"-reset", filePath, "-config", configPath, "-dir", outDir};
            }

            System.out.println("--cmd : " + Arrays.toString(cmd));
            output = IDSManager.ngBatchWrapper(cmd);
        }
        return output;
    }

    private void reloadCurrentPage(String filePath) {
        String str = Transformer.readFileAsString(filePath);
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().loadContent(str);
    }

    private String callBackAnnotation(String currDocPath, String outDir) {
        try {
            File currentFile = new File(currDocPath);
            String fileName = FilenameUtils.removeExtension(currentFile.getName()).replace(".~$", "");
            //String annotatedFilePath = outDir + File.separator + fileName + "_annotate.xrsl";

            String annotatedFilePath = currentFile.getParent() + File.separator + ".ids" + File.separator + fileName + "_annotate.xrsl";
            System.out.println("--Annotated file path=" + annotatedFilePath);
            File annotatedFile = new File(annotatedFilePath);
            String backannotation = null;
            if (annotatedFile.exists()) {
                System.out.println("--annotated file path : " + currDocPath);
                backannotation = BackAnnotation.backAnnotation(new File(currDocPath), annotatedFile);
                IDSUtils.print("--backannotation=" + backannotation);
            } else {
                System.err.println("Error annotated file not found");
            }
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("reloadPage()");
            return backannotation;
        } catch (Exception e) {
            System.err.println("Error callBackAnnotation : " + e.getMessage());
        }
        return null;
    }

    private void editConfigForCheck(boolean isCheck) {
        configMainObj.isCheck = isCheck;
        configMainObj.addCheckOnlyIntoXML(String.valueOf(isCheck));
    }

    private String getActiveFileHTMLPath(String activeFilePath) {
        try {
            File activeFile = new File(activeFilePath);
            String parentPath = activeFile.getParent();
            String destPath = parentPath + File.separator + FilenameUtils.removeExtension(activeFile.getName()) + ".html";
            File DescFile = new File(destPath);
            FileUtils.copyFile(activeFile, DescFile);
            if (DescFile.exists()) {
                return DescFile.getAbsolutePath();
            } else {
                System.err.println("Error : IDSNG, HTML copy cannot be created!");
            }
        } catch (Exception e) {
            System.err.println("Err getActiveFileHTMLPath : " + e.getMessage());
        }
        return null;
    }

    /**
     * description dummy function
     */
    private String runbatchCheck(String filepath, String configPath, boolean isCheck) {
        return "";
    }
}
