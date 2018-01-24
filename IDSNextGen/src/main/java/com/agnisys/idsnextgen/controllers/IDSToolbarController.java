/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.classes.WebController;
import com.agnisys.idsnextgen.controllers.config.ConfigMainController;
import com.agnisys.idsnextgen.global.IDSUtils;
import com.agnisys.idsnextgen.param.ParamWriter;
import com.agnisys.idsnextgen.strings.IDSString;
import com.agnisys.idsnextgen.transformation.Transformation;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class IDSToolbarController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="initiliaze fxml variables">
    @FXML
    ComboBox ddReg;
    @FXML
    Button btnConfig;
    @FXML
    Button btnOutDir;
    /*@FXML
     public Button btnSystem;
     @FXML
     public Button btnBoard;*/
    @FXML
    public Button btnChip;
    @FXML
    public Button btnBlock;
    @FXML
    public Button btnRegGrp;
    @FXML
    public Button btnRegister;
    /*@FXML
     public Button btnTrigBuf;
     @FXML
     public Button btnToC;*/
    @FXML
    public Button btnRef;
    @FXML
    public Button btnMemory;
    @FXML
    public Button btnEnum;
    @FXML
    public Button btnDefine;
    @FXML
    public Button btnVarient;
    /*@FXML
     public Button btnImport;
     @FXML
     public Button btnMarkBit;
     @FXML
     public Button btnHier;
     @FXML
     public Button btnSequ;*/
    @FXML
    public Button btnBusDomain;
    @FXML
    public Button btnSignal;
    @FXML
    Button btnCheck;
    @FXML
    Button btnGenerate;
    /*
     @FXML
     Button btnTurbo;

     @FXML
     Button btnDesc;
     @FXML
     Button btnProp;*/
    //tooltips
    @FXML
    Tooltip toolTipConfig;
    @FXML
    Tooltip toolTipOutDir;
    /*@FXML
     Tooltip toolTipSystem;
     @FXML
     Tooltip toolTipBoard;*/
    @FXML
    Tooltip toolTipChip;
    @FXML
    Tooltip toolTipBlock;
    @FXML
    Tooltip toolTipRegGroup;
    @FXML
    Tooltip toolTipRegister;
    /*@FXML
     Tooltip toolTipTrigBuf;
     @FXML
     Tooltip toolTipToC;*/
    @FXML
    Tooltip toolTipRef;
    @FXML
    Tooltip toolTipMemory;
    @FXML
    Tooltip toolTipEnum;
    @FXML
    Tooltip toolTipDefine;
    @FXML
    Tooltip toolTipVariant;
    /*@FXML
     Tooltip toolTipImport;
     @FXML
     Tooltip toolTipMarkBits;
     @FXML
     Tooltip toolTipHier;
     @FXML
     Tooltip toolTipSeque;*/
    @FXML
    Tooltip toolTipBusDomain;
    @FXML
    Tooltip toolTipSignal;
    @FXML
    Tooltip toolTipCheck;
    @FXML
    Tooltip toolTipGenerate;
    /*@FXML
     Tooltip toolTipTurbo;*/
    @FXML
    public AnchorPane anchorPaneToolbarContainer;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="initiliaze class variables">
    String imageString;
    private final String SHOWDESC = "Show description";
    private final String HIDEDESC = "Hide description";
    private final String SHOWPROP = "Show properties";
    private final String HIDEPROP = "Hide properties";
    private final String REG = "Reg";
    private final String REG8 = "Reg8";
    private final String REG16 = "Reg16";
    private final String REG32 = "Reg32";
    private final String REG64 = "Reg64";
    private final String REG128 = "Reg128";
    public ConfigMainController configmain = null;
    //</editor-fold>

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindString();
        initilization();
    }

    @FXML
    private void click_UpdateParam() {
//        System.out.println("testGit start");

        ParamWriter param = new ParamWriter();
        File yamlFIle = ApplicationMainGUIController.APPLICATION_OBJECT.fileChooser();
        if (yamlFIle != null) {
            param.updateParam(yamlFIle);
        }
    }

    @FXML
    private void click_testGit() {
//        System.out.println("testGit start");

        ParamWriter param = new ParamWriter();
        //File yamlFIle = new File("D:\\AgnisysProjects\\java\\MatlabParameter\\src\\main\\java\\com\\agnisys\\matlabparameter\\yamlparser\\Final_Allagro_Data.yml");
        File yamlFIle = ApplicationMainGUIController.APPLICATION_OBJECT.fileChooser();
        if (yamlFIle != null) {
            param.writeParam(yamlFIle);
        }
//        System.out.println("create repository done");

    }

    private void initilization() {
        ddReg.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                /* System.out.println(ov);
                 System.out.println(t);
                 System.out.println(t1);*/
                btnRegister.setText(t1);
                ddRegChange(t1);
            }
        });
    }

    private void ddRegChange(String value) {
        switch (value) {
            case REG:
                insertReg();
                break;
            case REG8:
                insertReg8();
                break;
            case REG16:
                insertReg16();
                break;
            case REG32:
                insertReg32();
                break;
            case REG64:
                insertReg64();
                break;
            case REG128:
                insertReg128();
                break;
        }
    }

    @FXML
    private void click_outDir() {
        String outDir = Transformation.getTransformer().getOutDir();
        if (outDir == null || !(new File(outDir)).exists()) {
            IDSUtils.showErrorMessage("Output Directory not created. Output Directory creates after generate process.");
        } else {
            IDSUtils.openInExplorer(outDir);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="initiliaze variables">
    private void bindString() {
        ddReg.getItems().addAll(
                REG,
                REG8,
                REG16,
                REG32, REG64, REG128
        );
        btnConfig.setText(IDSString.TOOLBAR_CONFIG);
        btnOutDir.setText(IDSString.TOOLBAR_OUTDIR);
        /*btnSystem.setText(IDSString.TOOLBAR_SYSTEM);
         btnBoard.setText(IDSString.TOOLBAR_BOARD);*/
        btnChip.setText(IDSString.TOOLBAR_CHIP);
        btnBlock.setText(IDSString.TOOLBAR_BLOCK);
        btnRegGrp.setText(IDSString.TOOLBAR_REG_GROUP);
        btnRegister.setText(IDSString.TOOLBAR_REGISTER);
//        btnTrigBuf.setText(IDSString.TOOLBAR_TRIGBUF);
//        btnToC.setText(IDSString.TOOLBAR_TOC);
        btnRef.setText(IDSString.TOOLBAR_Ref);
        btnMemory.setText(IDSString.TOOLBAR_MEMORY);
        btnEnum.setText(IDSString.TOOLBAR_ENUM);
        btnDefine.setText(IDSString.TOOLBAR_DEFINE);
        btnVarient.setText(IDSString.TOOLBAR_VARIENT);
//        btnImport.setText(IDSString.TOOLBAR_IMPORT);
//        btnMarkBit.setText(IDSString.TOOLBAR_MARK_BIT);
//        btnHier.setText(IDSString.TOOLBAR_HIERARCHY);
//        btnSequ.setText(IDSString.TOOLBAR_SEQ);
        btnBusDomain.setText(IDSString.TOOLBAR_BUSDOMAIN);
        btnSignal.setText(IDSString.TOOLBAR_SIGNAL);
        btnCheck.setText(IDSString.TOOLBAR_CHECK);
        btnGenerate.setText(IDSString.TOOLBAR_GENERATE);
        // btnTurbo.setText(IDSString.TOOLBAR_TURBO);

        toolTipConfig.setText(IDSString.TOOLTIP_CONFIG);
        toolTipOutDir.setText(IDSString.TOOLTIP_OUTDIR);
//        toolTipSystem.setText(IDSString.TOOLTIP_SYSTEM);
//        toolTipBoard.setText(IDSString.TOOLTIP_BOARD);
        toolTipChip.setText(IDSString.TOOLTIP_CHIP);
        toolTipBlock.setText(IDSString.TOOLTIP_BLOCK);
        toolTipRegGroup.setText(IDSString.TOOLTIP_REGGROUP);
        toolTipRegister.setText(IDSString.TOOLTIP_REGISTER);
//        toolTipTrigBuf.setText(IDSString.TOOLTIP_TRIGBUFF);
//        toolTipToC.setText(IDSString.TOOLTIP_TOC);
        toolTipRef.setText(IDSString.TOOLTIP_REF);
        toolTipMemory.setText(IDSString.TOOLTIP_MEMORY);
        toolTipEnum.setText(IDSString.TOOLTIP_ENUM);
        toolTipDefine.setText(IDSString.TOOLTIP_DEFINE);
        toolTipVariant.setText(IDSString.TOOLTIP_VARIENT);
//        toolTipImport.setText(IDSString.TOOLTIP_IMPORT);
//        toolTipMarkBits.setText(IDSString.TOOLTIP_MARKBITS);
//        toolTipHier.setText(IDSString.TOOLTIP_HIER);
//        toolTipSeque.setText(IDSString.TOOLTIP_SEQ);
        toolTipBusDomain.setText(IDSString.TOOLTIP_BUSDOMAIN);
        toolTipSignal.setText(IDSString.TOOLTIP_SIGNAL);
        toolTipCheck.setText(IDSString.TOOLTIP_CHECK);
        toolTipGenerate.setText(IDSString.TOOLTIP_GENERATE);
//        toolTipTurbo.setText(IDSString.TOOLTIP_TURBO);
    }
    //</editor-fold>

    @FXML
    private void click_check() {
        Transformation.getTransformer().runCheck();
        //ApplicationMainGUIController.APPLICATION_OBJECT.get_ActiveHTMLEditor().executeScript("getXRSL()");
//        try {
//            Transformation.getTransformer().runProcess();
//        } catch (IOException | InterruptedException e) {
//            System.err.println("Err click_check : " + e.getMessage());
//        }
    }

    @FXML
    private void click_generate() {
        Transformation.getTransformer().runGenerate();
    }

    @FXML
    private void testMe() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("getSelectionText()");
    }

    @FXML
    private void click_insertReg() {
        ddRegChange(btnRegister.getText());
    }

    private void insertReg() {
        //imageString = ImageConvertor.getImageConvertor().getImageString(REG_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFWSURBVHja1JNdUoMwEMc3gM744EX0CF6iXsZXPY9Tq97Gj1ptRynfpKVQCCHbTWhLeebJzCSzf0h+5L+7MESEIcOCgWMwwLm+fRjm4WZ0h49fJY6nGU6mKV6N7vH1k+PzR4yT98Dopzef4ohmYvTL9xrHs62JHQ3JgwRUnYNqKgMNeQyIDSAooyOeAGM2RbbR8eIP7PPL1oJeZpEHFlaUEGkeLn7XdIAOs9ad6xWknT2EdJZADXkHWLqCvl6DUi1gHnDazI42f4IcdLkPJZ8tKrBtuwNEYQxKCsCmBQgvA3aSJ+FnPUDsx8CcbQdIVyVZaGi2nuc5gU4abL6p4bThio0EaVUdIOQFWErRbDeJlderVL1a9gBhWoJg0tzSAGRVwJn2vPe9SeCYA71mnB0vpJ2jEiZfOnZSuABn7YJ+Lw/tWfv9di2XPS25uy+oLtS//5l2AgwAgHLTw4hIPJYAAAAASUVORK5CYII";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
            //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg('data:image/png;base64," + imageString + "')");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    private void insertReg8() {
        //imageString = ImageConvertor.getImageConvertor().getImageString(REG_IMG_PATH);
        //imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFWSURBVHja1JNdUoMwEMc3gM744EX0CF6iXsZXPY9Tq97Gj1ptRynfpKVQCCHbTWhLeebJzCSzf0h+5L+7MESEIcOCgWMwwLm+fRjm4WZ0h49fJY6nGU6mKV6N7vH1k+PzR4yT98Dopzef4ohmYvTL9xrHs62JHQ3JgwRUnYNqKgMNeQyIDSAooyOeAGM2RbbR8eIP7PPL1oJeZpEHFlaUEGkeLn7XdIAOs9ad6xWknT2EdJZADXkHWLqCvl6DUi1gHnDazI42f4IcdLkPJZ8tKrBtuwNEYQxKCsCmBQgvA3aSJ+FnPUDsx8CcbQdIVyVZaGi2nuc5gU4abL6p4bThio0EaVUdIOQFWErRbDeJlderVL1a9gBhWoJg0tzSAGRVwJn2vPe9SeCYA71mnB0vpJ2jEiZfOnZSuABn7YJ+Lw/tWfv9di2XPS25uy+oLtS//5l2AgwAgHLTw4hIPJYAAAAASUVORK5CYII";
        imageString = "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";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
            //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg('data:image/png;base64," + imageString + "')");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg8('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertAutoTest() {
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFWSURBVHja1JNdUoMwEMc3gM744EX0CF6iXsZXPY9Tq97Gj1ptRynfpKVQCCHbTWhLeebJzCSzf0h+5L+7MESEIcOCgWMwwLm+fRjm4WZ0h49fJY6nGU6mKV6N7vH1k+PzR4yT98Dopzef4ohmYvTL9xrHs62JHQ3JgwRUnYNqKgMNeQyIDSAooyOeAGM2RbbR8eIP7PPL1oJeZpEHFlaUEGkeLn7XdIAOs9ad6xWknT2EdJZADXkHWLqCvl6DUi1gHnDazI42f4IcdLkPJZ8tKrBtuwNEYQxKCsCmBQgvA3aSJ+FnPUDsx8CcbQdIVyVZaGi2nuc5gU4abL6p4bThio0EaVUdIOQFWErRbDeJlderVL1a9gBhWoJg0tzSAGRVwJn2vPe9SeCYA71mnB0vpJ2jEiZfOnZSuABn7YJ+Lw/tWfv9di2XPS25uy+oLtS//5l2AgwAgHLTw4hIPJYAAAAASUVORK5CYII";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
            //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg('data:image/png;base64," + imageString + "')");
        } else {
            //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg8('data:image/png;base64," + imageString + "')");
            //WebController.setUnsaveSymbol();

            for (int i = 0; i < 10000; i++) {
                ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("test_Reg('data:image/png;base64," + imageString + "','tab" + i + "')");
            }
        }
    }

    private void insertReg16() {
        //imageString = ImageConvertor.getImageConvertor().getImageString(REG_IMG_PATH);
        //imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFWSURBVHja1JNdUoMwEMc3gM744EX0CF6iXsZXPY9Tq97Gj1ptRynfpKVQCCHbTWhLeebJzCSzf0h+5L+7MESEIcOCgWMwwLm+fRjm4WZ0h49fJY6nGU6mKV6N7vH1k+PzR4yT98Dopzef4ohmYvTL9xrHs62JHQ3JgwRUnYNqKgMNeQyIDSAooyOeAGM2RbbR8eIP7PPL1oJeZpEHFlaUEGkeLn7XdIAOs9ad6xWknT2EdJZADXkHWLqCvl6DUi1gHnDazI42f4IcdLkPJZ8tKrBtuwNEYQxKCsCmBQgvA3aSJ+FnPUDsx8CcbQdIVyVZaGi2nuc5gU4abL6p4bThio0EaVUdIOQFWErRbDeJlderVL1a9gBhWoJg0tzSAGRVwJn2vPe9SeCYA71mnB0vpJ2jEiZfOnZSuABn7YJ+Lw/tWfv9di2XPS25uy+oLtS//5l2AgwAgHLTw4hIPJYAAAAASUVORK5CYII";
        imageString = "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";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
            //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg('data:image/png;base64," + imageString + "')");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg16('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }

    }

    private void insertReg32() {
        //imageString = ImageConvertor.getImageConvertor().getImageString(REG_IMG_PATH);
        //imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFWSURBVHja1JNdUoMwEMc3gM744EX0CF6iXsZXPY9Tq97Gj1ptRynfpKVQCCHbTWhLeebJzCSzf0h+5L+7MESEIcOCgWMwwLm+fRjm4WZ0h49fJY6nGU6mKV6N7vH1k+PzR4yT98Dopzef4ohmYvTL9xrHs62JHQ3JgwRUnYNqKgMNeQyIDSAooyOeAGM2RbbR8eIP7PPL1oJeZpEHFlaUEGkeLn7XdIAOs9ad6xWknT2EdJZADXkHWLqCvl6DUi1gHnDazI42f4IcdLkPJZ8tKrBtuwNEYQxKCsCmBQgvA3aSJ+FnPUDsx8CcbQdIVyVZaGi2nuc5gU4abL6p4bThio0EaVUdIOQFWErRbDeJlderVL1a9gBhWoJg0tzSAGRVwJn2vPe9SeCYA71mnB0vpJ2jEiZfOnZSuABn7YJ+Lw/tWfv9di2XPS25uy+oLtS//5l2AgwAgHLTw4hIPJYAAAAASUVORK5CYII";
        imageString = "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";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
            //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg('data:image/png;base64," + imageString + "')");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg32('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }

    }

    private void insertReg64() {
        //imageString = ImageConvertor.getImageConvertor().getImageString(REG_IMG_PATH);
        //imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFWSURBVHja1JNdUoMwEMc3gM744EX0CF6iXsZXPY9Tq97Gj1ptRynfpKVQCCHbTWhLeebJzCSzf0h+5L+7MESEIcOCgWMwwLm+fRjm4WZ0h49fJY6nGU6mKV6N7vH1k+PzR4yT98Dopzef4ohmYvTL9xrHs62JHQ3JgwRUnYNqKgMNeQyIDSAooyOeAGM2RbbR8eIP7PPL1oJeZpEHFlaUEGkeLn7XdIAOs9ad6xWknT2EdJZADXkHWLqCvl6DUi1gHnDazI42f4IcdLkPJZ8tKrBtuwNEYQxKCsCmBQgvA3aSJ+FnPUDsx8CcbQdIVyVZaGi2nuc5gU4abL6p4bThio0EaVUdIOQFWErRbDeJlderVL1a9gBhWoJg0tzSAGRVwJn2vPe9SeCYA71mnB0vpJ2jEiZfOnZSuABn7YJ+Lw/tWfv9di2XPS25uy+oLtS//5l2AgwAgHLTw4hIPJYAAAAASUVORK5CYII";
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAACXBIWXMAAAsTAAALEwEAmpwYAAAKTWlDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjanVN3WJP3Fj7f92UPVkLY8LGXbIEAIiOsCMgQWaIQkgBhhBASQMWFiApWFBURnEhVxILVCkidiOKgKLhnQYqIWotVXDjuH9yntX167+3t+9f7vOec5/zOec8PgBESJpHmomoAOVKFPDrYH49PSMTJvYACFUjgBCAQ5svCZwXFAADwA3l4fnSwP/wBr28AAgBw1S4kEsfh/4O6UCZXACCRAOAiEucLAZBSAMguVMgUAMgYALBTs2QKAJQAAGx5fEIiAKoNAOz0ST4FANipk9wXANiiHKkIAI0BAJkoRyQCQLsAYFWBUiwCwMIAoKxAIi4EwK4BgFm2MkcCgL0FAHaOWJAPQGAAgJlCLMwAIDgCAEMeE80DIEwDoDDSv+CpX3CFuEgBAMDLlc2XS9IzFLiV0Bp38vDg4iHiwmyxQmEXKRBmCeQinJebIxNI5wNMzgwAABr50cH+OD+Q5+bk4eZm52zv9MWi/mvwbyI+IfHf/ryMAgQAEE7P79pf5eXWA3DHAbB1v2upWwDaVgBo3/ldM9sJoFoK0Hr5i3k4/EAenqFQyDwdHAoLC+0lYqG9MOOLPv8z4W/gi372/EAe/tt68ABxmkCZrcCjg/1xYW52rlKO58sEQjFu9+cj/seFf/2OKdHiNLFcLBWK8ViJuFAiTcd5uVKRRCHJleIS6X8y8R+W/QmTdw0ArIZPwE62B7XLbMB+7gECiw5Y0nYAQH7zLYwaC5EAEGc0Mnn3AACTv/mPQCsBAM2XpOMAALzoGFyolBdMxggAAESggSqwQQcMwRSswA6cwR28wBcCYQZEQAwkwDwQQgbkgBwKoRiWQRlUwDrYBLWwAxqgEZrhELTBMTgN5+ASXIHrcBcGYBiewhi8hgkEQcgIE2EhOogRYo7YIs4IF5mOBCJhSDSSgKQg6YgUUSLFyHKkAqlCapFdSCPyLXIUOY1cQPqQ28ggMor8irxHMZSBslED1AJ1QLmoHxqKxqBz0XQ0D12AlqJr0Rq0Hj2AtqKn0UvodXQAfYqOY4DRMQ5mjNlhXIyHRWCJWBomxxZj5Vg1Vo81Yx1YN3YVG8CeYe8IJAKLgBPsCF6EEMJsgpCQR1hMWEOoJewjtBK6CFcJg4Qxwicik6hPtCV6EvnEeGI6sZBYRqwm7iEeIZ4lXicOE1+TSCQOyZLkTgohJZAySQtJa0jbSC2kU6Q+0hBpnEwm65Btyd7kCLKArCCXkbeQD5BPkvvJw+S3FDrFiOJMCaIkUqSUEko1ZT/lBKWfMkKZoKpRzame1AiqiDqfWkltoHZQL1OHqRM0dZolzZsWQ8ukLaPV0JppZ2n3aC/pdLoJ3YMeRZfQl9Jr6Afp5+mD9HcMDYYNg8dIYigZaxl7GacYtxkvmUymBdOXmchUMNcyG5lnmA+Yb1VYKvYqfBWRyhKVOpVWlX6V56pUVXNVP9V5qgtUq1UPq15WfaZGVbNQ46kJ1Bar1akdVbupNq7OUndSj1DPUV+jvl/9gvpjDbKGhUaghkijVGO3xhmNIRbGMmXxWELWclYD6yxrmE1iW7L57Ex2Bfsbdi97TFNDc6pmrGaRZp3mcc0BDsax4PA52ZxKziHODc57LQMtPy2x1mqtZq1+rTfaetq+2mLtcu0W7eva73VwnUCdLJ31Om0693UJuja6UbqFutt1z+o+02PreekJ9cr1Dund0Uf1bfSj9Rfq79bv0R83MDQINpAZbDE4Y/DMkGPoa5hpuNHwhOGoEctoupHEaKPRSaMnuCbuh2fjNXgXPmasbxxirDTeZdxrPGFiaTLbpMSkxeS+Kc2Ua5pmutG003TMzMgs3KzYrMnsjjnVnGueYb7ZvNv8jYWlRZzFSos2i8eW2pZ8ywWWTZb3rJhWPlZ5VvVW16xJ1lzrLOtt1ldsUBtXmwybOpvLtqitm63Edptt3xTiFI8p0in1U27aMez87ArsmuwG7Tn2YfYl9m32zx3MHBId1jt0O3xydHXMdmxwvOuk4TTDqcSpw+lXZxtnoXOd8zUXpkuQyxKXdpcXU22niqdun3rLleUa7rrStdP1o5u7m9yt2W3U3cw9xX2r+00umxvJXcM970H08PdY4nHM452nm6fC85DnL152Xlle+70eT7OcJp7WMG3I28Rb4L3Le2A6Pj1l+s7pAz7GPgKfep+Hvqa+It89viN+1n6Zfgf8nvs7+sv9j/i/4XnyFvFOBWABwQHlAb2BGoGzA2sDHwSZBKUHNQWNBbsGLww+FUIMCQ1ZH3KTb8AX8hv5YzPcZyya0RXKCJ0VWhv6MMwmTB7WEY6GzwjfEH5vpvlM6cy2CIjgR2yIuB9pGZkX+X0UKSoyqi7qUbRTdHF09yzWrORZ+2e9jvGPqYy5O9tqtnJ2Z6xqbFJsY+ybuIC4qriBeIf4RfGXEnQTJAntieTE2MQ9ieNzAudsmjOc5JpUlnRjruXcorkX5unOy553PFk1WZB8OIWYEpeyP+WDIEJQLxhP5aduTR0T8oSbhU9FvqKNolGxt7hKPJLmnVaV9jjdO31D+miGT0Z1xjMJT1IreZEZkrkj801WRNberM/ZcdktOZSclJyjUg1plrQr1zC3KLdPZisrkw3keeZtyhuTh8r35CP5c/PbFWyFTNGjtFKuUA4WTC+oK3hbGFt4uEi9SFrUM99m/ur5IwuCFny9kLBQuLCz2Lh4WfHgIr9FuxYji1MXdy4xXVK6ZHhp8NJ9y2jLspb9UOJYUlXyannc8o5Sg9KlpUMrglc0lamUycturvRauWMVYZVkVe9ql9VbVn8qF5VfrHCsqK74sEa45uJXTl/VfPV5bdra3kq3yu3rSOuk626s91m/r0q9akHV0IbwDa0b8Y3lG19tSt50oXpq9Y7NtM3KzQM1YTXtW8y2rNvyoTaj9nqdf13LVv2tq7e+2Sba1r/dd3vzDoMdFTve75TsvLUreFdrvUV99W7S7oLdjxpiG7q/5n7duEd3T8Wej3ulewf2Re/ranRvbNyvv7+yCW1SNo0eSDpw5ZuAb9qb7Zp3tXBaKg7CQeXBJ9+mfHvjUOihzsPcw83fmX+39QjrSHkr0jq/dawto22gPaG97+iMo50dXh1Hvrf/fu8x42N1xzWPV56gnSg98fnkgpPjp2Snnp1OPz3Umdx590z8mWtdUV29Z0PPnj8XdO5Mt1/3yfPe549d8Lxw9CL3Ytslt0utPa49R35w/eFIr1tv62X3y+1XPK509E3rO9Hv03/6asDVc9f41y5dn3m978bsG7duJt0cuCW69fh29u0XdwruTNxdeo94r/y+2v3qB/oP6n+0/rFlwG3g+GDAYM/DWQ/vDgmHnv6U/9OH4dJHzEfVI0YjjY+dHx8bDRq98mTOk+GnsqcTz8p+Vv9563Or59/94vtLz1j82PAL+YvPv655qfNy76uprzrHI8cfvM55PfGm/K3O233vuO+638e9H5ko/ED+UPPR+mPHp9BP9z7nfP78L/eE8/sl0p8zAAAABGdBTUEAALGOfPtRkwAAACBjSFJNAAB6JQAAgIMAAPn/AACA6QAAdTAAAOpgAAA6mAAAF2+SX8VGAAADDElEQVR42uyVwW4bVRSGv3PunbFjx46StCGkdRLRIMKaHWIPr4CEhLpmwaKwIKpUCVWABAtgwSvwBix5DkpLYpLYcZo4nrHHnrHnzlwWTsy6i7Ih//JK93znPzr6j3jved1S/gPdQv6nELv30YG/u3Wfl2dtAhw2rJBLwMgZmo01stkFxlRwLqO+tMnF5Rlra03GkyG1Wo1oMKLRWMWaKmSXBD7HzabkWDbe3OWie4q19SmffvYxzboSaE7hBOerPH18wONHnxNUE4wJmc1SppMaP/38A198+QlhBbIsoxI28KXl4KsnfP39t1jJMNaTlwHDccmP3zzFmmRKMZiQJFMoE1yuhMEqwVKNKBkTFDGTcU59OURlmV+fPERlhKhCRVAZoqL8/ssjRPuIKKrCby+GFHkFk0yxweoKx4M+YhKWbI7LDWjJaGy56Dtm5YDCGWpJTpb0+eA9ZX//HQCev/gLFUFUERVUlO3tFt1ul8N+D18sE6yuYNO0SqfTZ6lWIU2UoDbEAdqs8kfnGdYIYRgyu0pwxYB39z/k2Z/PUVXe3nvA4dERqnPQdqsFgKjSv4R00idNq9gQx2Q8Jo5j/MygwytQhx0VFJcTPJ7SGMqypLiOOVVBRDg8OkJU5w5aLTqdLvfubaEi9M5eElhLiMMWRU4UJ8xyT9U0KNIpziXEts5pWqJ4ytJhjFlARJS9B2/Rbv+NyBx4A79xMohSwkAoihyLrTBIpkyygkqpmCDHiKcuKcXonAJwzhGGIWVZAiwAu7s7nJyc0mrdp9vrIaLXMOUiTqlVDdgKOis9UXSFekdZTPFlwWgU412DJFbGAyG5gkmkJPG8SLvdRm66NvO3rc1NNt/YAODO+jrqHVF0xaz02I1GSG/URTNLkVmkOiSbBJjaCtPpEPFzJ6IVXFksxqHXXRuE3vk5KoqocvfOOlEU8f7D7xDn2GisIq96tI6PT/zOzjYA3bMzVARVs9iw9bU14nhIs9mQRay8ag6JKqedzr+FRZBrgIoSx/FiARZ/bs/vLeS16Z8BAA3EZtKDzuxYAAAAAElFTkSuQmCC";

        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
            //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg('data:image/png;base64," + imageString + "')");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg64('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }

    }

    private void insertReg128() {
        //imageString = ImageConvertor.getImageConvertor().getImageString(REG_IMG_PATH);
        //imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFWSURBVHja1JNdUoMwEMc3gM744EX0CF6iXsZXPY9Tq97Gj1ptRynfpKVQCCHbTWhLeebJzCSzf0h+5L+7MESEIcOCgWMwwLm+fRjm4WZ0h49fJY6nGU6mKV6N7vH1k+PzR4yT98Dopzef4ohmYvTL9xrHs62JHQ3JgwRUnYNqKgMNeQyIDSAooyOeAGM2RbbR8eIP7PPL1oJeZpEHFlaUEGkeLn7XdIAOs9ad6xWknT2EdJZADXkHWLqCvl6DUi1gHnDazI42f4IcdLkPJZ8tKrBtuwNEYQxKCsCmBQgvA3aSJ+FnPUDsx8CcbQdIVyVZaGi2nuc5gU4abL6p4bThio0EaVUdIOQFWErRbDeJlderVL1a9gBhWoJg0tzSAGRVwJn2vPe9SeCYA71mnB0vpJ2jEiZfOnZSuABn7YJ+Lw/tWfv9di2XPS25uy+oLtS//5l2AgwAgHLTw4hIPJYAAAAASUVORK5CYII";
        imageString = "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";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
            //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg('data:image/png;base64," + imageString + "')");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertReg128('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }

    }

    @FXML
    private void insertBlock() {
//        imageString = ImageConvertor.getImageConvertor().getImageString(BLOCK_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAACeSURBVHjaYvz//z8DJYCJgULAYpAw6f+/f/8ZmJgYwQJ///1jYGZiYriwII8RWSFIHVYDQERKvAuK4LzFe7HalpaAqm7Wgj0QA7ABXDYSHQYwL4BodO9QNxBBxMJl+xiYGJnggYgLgPyMAUDpABfWj5/4H588CLMQ61S80UgsSIp1xohuFnJtJsoFyNGHyyDqRCOxAFsSZ6Q0OwMEGAB3Z2M39J265AAAAABJRU5ErkJggg";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertBlock('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertSystem() {
//        imageString = ImageConvertor.getImageConvertor().getImageString(SYSTEM_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAALDSURBVHjadFM7bBNBEJ3dvds7+3xnOw6xQ34QkygoipQIQUEIAkQQqQgVKWhSQJ8CQRkJKRKpoIko6GhokKCgo4MiQKQkSBSQH5+A87Nz/pzt++2ytiPFspKR7lY3N+/NvJlZBA/ewLHG+ChgNCTOOXEWGn/z2VsgHQvm0K5QacpQyOBewbE5wJzwuo1h+Eiw51+KafT91OXk2MNrva0Xu2NPhfedqGSwMZTA8ER9VgCf3Wsy1Jf3h5Mn+9sjoKky9MYNCMokuV9yJwqWwwDBAiDkT4/21RFwTiWCZ3sT4Znb50/TkKbCRq4Mfy0X8h6DjhM6dMVCaslno0XHG3E8/8f0jb5N6QDcJd4vBjqbrw+daYF9BvBztwgu48AOdMrYhmiAwshAOxQWNq6ISp4J9wVJgAdUibzu7473tCUisJJzIG/7NTDnVVWoQoIRUMuDva0MK5Y93BE3qv2rVDCICO6RgwpfzTkoXXTA9Tn4B+CqCQYsPqzttKtSiZ3r71LsXKFGgDEOBkVpS9//mTgUNPxAgLg+q8iqZReZxVSAZbKeRGU/3BJVS44HhqrQKgHnPHi2sxl28k5o5ddOGZRyAGsa5qimnQswtyyfyoSHY4YaEYQ68IxjOzNVAoTgo8JYKqEprfmWiJzZzzu2aYIU1ChnQkvZdnUjKOkBRW6mGIhjL69nzMmopixWFwkh9CWVzo8z215r02RIRHUaojL2C3nOikXepAekeIDiVgWDVyq/zeSKY+mCvUhwbQdFcxGYJffz/Pr21ZJV/pBQCSRjWkUaEg8+pSs4RhFs7uWerO2YdwhBKVLpS/0qVxyez/58S5njq7vZV2H5MIAzL7uyk53cMq1HBIllaLDDyyQqEePKpC377qffmWUJo8diEOWvW9mblOB5qMt6NMEhkW+7/nMiYV2Mccn12LxYtGMv7X8BBgD87zQ9A/bkqgAAAABJRU5ErkJggg";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertSystem('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertBoard() {
//        imageString = ImageConvertor.getImageConvertor().getImageString(BOARD_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAACGSURBVHjaYvz//z8DJYBxwA1g0WxeQ5EJLCBiVqoXWZrTZm+DGPDyN4UueP/7P2UGvPn1lzIDPv+h0AA2IF65fi+KRHigMwofXR4GmJAlCyNc+IFYEMSWYmdEwVB5cVDaQcYsExPdNIEMPiAGafwCxAIgxf0r9mDYpsDJ9G/wJeWBNwAgwAAx4jXC9GLiJAAAAABJRU5ErkJggg";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertBoard('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertChip() {
//        imageString = ImageConvertor.getImageConvertor().getImageString(CHIP_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAIySURBVHjazFM7axVBFP7OPHZ2b26Uq43RFPbiE8Ei6B+wsVCLFL4Qo1UKQSsRjBBNpXY2goWgKZSQKlUqsdBGwTRqE4IxCrnG+9zHzPHs3mgvaRx29uw8zvd9881ZMDP+9LFTN/hfYtkVttioRLl0+wkvLi3DgxECYAoPFdfhiz6UtgieYeMauu0NKGIYG+HwyDBePZokKuWomsOVqxdRUEDiIqhASLtNUJKA8hR9sjAIcHEioBkMBzx+No8fn5YHCiZmnvPeIycAFkZZDDnBOIYPKTRppFwgiix6nR5qAmK0wZuF15idPkdm7PR17sQNDO0axTYXkKUZiByUNYAcAaVNxqOdB8TWYWO9CaU0Vr6v4ujJSa4UnL/3ksPO3bDoVgo4WOSZh7WxpBMKlcscy5eMxKNyvvnlI2bvXyA6Nn6L13wD+w8dFKL+wFlh6DOEkcRYA5W1EEii1ihhSAA+v3sPvbHpweWZef7aFudDLstiBRGsS9DttgTNIFK+As2LMiqIGDjZN3f3DNGBs9PcCgZ7RhrCYGCMgveiM3hoGf8tNO8rBWJQ+eDbShtZK9usg6kX/GH1lzBbSRYWls2FqDEORu6dtUTxslwrikJugeS6PRYfTBAdH5/itb6CGa4jFWFemOuxhVMGWZaJeQGSgiLIW6id1QJUgH6uw6duoODanae88HYJqO0Q0+QInSbc0HbovAcvSYGial5zXhWU1Cv2jSaYe3iT8H/8TFtpvwUYANw+W2VPE6rlAAAAAElFTkSuQmCC";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertChip('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertRegGroup() {
//        imageString = ImageConvertor.getImageConvertor().getImageString(REGGROUP_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAABkSURBVHjaYvz//z8DJYBFOqSVIhNYQMTkugwUwdymGShiyHx0NhMDhYBRKriFci+snFjO8PX7T4akiglYnYrPa2AvsLCwMLAwMw+gF3CF+BCKBUKhjc87FHth4MOAkdLsDBBgAAAIP+g8PM01AAAAAElFTkSuQmCC";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertRegGroup('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertRef() {
//        imageString = ImageConvertor.getImageConvertor().getImageString(REF_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAMAAABhq6zVAAAACXBIWXMAAAsTAAALEwEAmpwYAAAABGdBTUEAALGOfPtRkwAAACBjSFJNAAB6JQAAgIMAAPn/AACA6QAAdTAAAOpgAAA6mAAAF2+SX8VGAAADAFBMVEX///8mk7clkrcrouIqoeHk8/vM6PjQ5+/N5u+czt/W7fkhneCl0uGq1eOp1OPY6/BUqcUqouLQ6vguo+Lq9v5YteUvpOJbteUnlLjg8ftMpsOJzPFeueqk0uE8qeY+n8BAq+bX6u84nL0ooOJAoMBDrOan0+L4/P+n2PP9/v/R6fDp9fwPkdDl9PvP6vgvo+I8quU6qeU9quQMlN7S6/mfz99ku+rM6fnP5+6x3fRgueqgz+Dl9Pw8qeSv2/RGosKEyfAcjrXm9PzL6Pg7qeTO6vhGruZFreYOld4hkLZku+k/q+bZ7vqgz99fuenq9vzh8vwOkdCezt7S6O/n9Pzd8PpHpMPJ5e7///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABSmRIoAAAAWXRSTlP/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////AE+FAQ8AAADFSURBVHjaYoxgYGC48/vJRwaG//oAAcQEZDMw39HmY2JS/QgQQMw6QI6gJLPytb/f3gIEEPMjjjfsu94w3fzC4HAfIIBYjO/95lL4cuW37j92BoAAYn70XZz99bPfIR8kP/wACCAmBqkvP81/O118+uHSb4AAYmIQN+X6ycWp8+OzhSxAADExyG/4/83uIqurvqgWQAAxM/57/eiT0KWnNzQYGAACiMn9MweH/Xl2EZDlAAHE/O/R37/Xf/5lE5NkYAAIMAAgfkB2tPG31wAAAABJRU5ErkJggg";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertRef('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertMemory() {
//        imageString = ImageConvertor.getImageConvertor().getImageString(MEM_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAAsTAAALEwEAmpwYAAAABGdBTUEAALGOfPtRkwAAACBjSFJNAAB6JQAAgIMAAPn/AACA6QAAdTAAAOpgAAA6mAAAF2+SX8VGAAAAUklEQVR42mL8//8/AyWARTqklSITWBgYGBgm12WQpTm3aQYDEwOFgFEquIVyL6ycWE6W5vD8TogBLCwsA+yFYRALA+qFgQ8DRkqzMwAAAP//AwBKuhvgGLzsagAAAABJRU5ErkJggg";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertMemory('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertEnum() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertEnum()");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertDefine() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertDefine()");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertVariant() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertVariant()");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertBusDomain() {
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertBusDomain()");
            WebController.setUnsaveSymbol();
        }
    }

    @FXML
    private void insertSignals() {
//        imageString = ImageConvertor.getImageConvertor().getImageString(SIGNAL_IMG_PATH);
        imageString = "iVBORw0KGgoAAAANSUhEUgAAABQAAAAKCAYAAAC0VX7mAAAABGdBTUEAALGOfPtRkwAAACBjSFJNAAB6JQAAgIMAAPn/AACA6QAAdTAAAOpgAAA6mAAAF2+SX8VGAAAACXBIWXMAAA7EAAAOxAGVKw4bAAABsklEQVQ4T6WSvUsCYRzHv2W+kJE0KBkE1lCEa7T2LwQuuQjREEIIjZVt4eLeYGA4GBW0ObgVQqBmDYIv6SChFRroXVpXnfjrnqfr5XBo6AM/7p7vfe/7/J6XAVKASvNZxr3whtnxYeh1g6r6Nyyi2+1Cr9dDE7ifuMPp9SN2l6Yx7xjlmtvtRjab5WaGLMuQJAmpVApWqxWtVgvpdBq5XA4+n+8nUJC6WD0owDA0iIWpUczNTGLDtYjiVYIH/aZaraLT6cBsNiMWiyEcDsPhcCAajfJ2qf70xh50ftOk/H2bv29u77CJNGWxWKhYLJKyPFK65L5CoUDxeJzK5TIfo9l5p2D8lvbOalxgnBwf9YWxSiaTqoNIFEUKBAKkdKcqnwytH5bw8t5TljqACesY8hcxbK0tK/9rsdvtaDQaCIVC0Ol0UDpCJpOB0+nkus1m+zReVkRaOchTWyYSXolMwyN9nQWDQT57vV7nJQgC+f1+8ng8VCqV+Lcv0Ov1KPcgUVts9QWx8nq9qlVLpVKhWu1nm774PuVIJAKj0QiTycSGHHY9XC4XDAaDqvyN5h7+H+AD3W9Zy3A0RZQAAAAASUVORK5CYII=";
        if (ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine() == null) {
            ApplicationMainGUIController.APPLICATION_OBJECT.printConsole("Error : Please select idsng file to insert IDS Specs");
        } else {
            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertSignals('data:image/png;base64," + imageString + "')");
            WebController.setUnsaveSymbol();
        }
    }

    public void click_config() {
        /*
         try {
         ApplicationMainGUIController.APPLICATION_OBJECT.showFXMLWindow("/designs/config/ConfigMain.fxml");
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigMain.fxml"));
         configmain = loader.load();
         configmain = loader.getController();loader.g
         } catch (IOException ex) {
         Logger.getLogger(IDSToolbarController.class.getName()).log(Level.SEVERE, null, ex);
         }
         */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigMain.fxml"));
        try {
            Parent root1 = (Parent) loader.load();
            configmain = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initOwner(com.agnisys.idsnextgen.IDSNextGen.STAGE.getScene().getWindow());
            stage.setTitle(IDSNextGen.APPLICATION_OBJECT.idsString.get_APPLICATION_TITLE());
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IDSToolbarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
