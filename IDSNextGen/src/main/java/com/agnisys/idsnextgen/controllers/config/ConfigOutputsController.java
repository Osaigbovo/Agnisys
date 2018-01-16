/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config;

import com.agnisys.idsnextgen.controllers.config.properties.Outputs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class ConfigOutputsController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fxml value init">
    @FXML
    TextField txtOutDir;
    @FXML
    CheckBox chkVerilog;
    @FXML
    RadioButton rbVerilog1995;
    @FXML
    RadioButton rbVerilog2001;
    @FXML
    CheckBox chkRTLWire;
    @FXML
    CheckBox chkVHDL;
    @FXML
    RadioButton rbVHDLAlt1;
    @FXML
    RadioButton rbVHDLAlt2;
    @FXML
    CheckBox chkVHDLMultiOutFile;
    @FXML
    CheckBox chkSystemVerilog;
    @FXML
    CheckBox chkSystemC;
    @FXML
    RadioButton rbSyscAlt1;
    @FXML
    RadioButton rbSyscAlt2;
    @FXML
    CheckBox chkUVM;
    @FXML
    CheckBox chkUVMMultiOutFile;
    @FXML
    CheckBox chkOVM;
    @FXML
    CheckBox chkVMM;
    @FXML
    CheckBox chkERm;
    @FXML
    CheckBox chkIVSExcel;
    @FXML
    CheckBox chkARVFormal;
    @FXML
    CheckBox chkC;
    @FXML
    RadioButton rbCAlt1;
    @FXML
    RadioButton rbCAlt2;
    @FXML
    RadioButton rbCMisrac;
    @FXML
    CheckBox chkSVHeader;
    @FXML
    CheckBox chkCSharp;
    @FXML
    CheckBox chkPerl;
    @FXML
    CheckBox chkPython;
    @FXML
    CheckBox chkCPP;
    @FXML
    CheckBox chkVHeader;
    @FXML
    CheckBox chkVHDHeader;
    @FXML
    CheckBox chkHTML;
    @FXML
    RadioButton rbHTMLAlt1;
    @FXML
    RadioButton rbHTMLAlt2;
    @FXML
    CheckBox chkPDF;
    @FXML
    CheckBox chkXML;
    @FXML
    CheckBox chkSVG;
    @FXML
    CheckBox chkDatasheet;
    @FXML
    CheckBox chkCustomCSV;
    @FXML
    CheckBox chkCustomXML;
    @FXML
    CheckBox chkIPXact;
    @FXML
    RadioButton rbIPXact15;
    @FXML
    RadioButton rbIPXact14;
    @FXML
    CheckBox chkSystemRDL;
    @FXML
    CheckBox chkCMSIS;
    @FXML
    CheckBox chkFirmware;
    @FXML
    CheckBox chkSeqUVM;
    @FXML
    CheckBox chkARVSim;
    @FXML
    CheckBox chkCHeaderMultioutfile;
    //</editor-fold>

    public static ConfigOutputsController outputs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click_selectAll() {
        selectUnselectBtns(true);
    }

    @FXML
    private void click_clearAll() {
        selectUnselectBtns(false);
    }

    @FXML
    private void click_rbVerilog() {
        /*
         if (chkVerilog.isSelected()) {
         rbVerilog1995.setDisable(false);
         rbVerilog2001.setDisable(false);
         } else {
         rbVerilog1995.setDisable(true);
         rbVerilog2001.setDisable(true);
         }
         */
        rbVerilog1995.setDisable(!chkVerilog.isSelected());
        rbVerilog2001.setDisable(!chkVerilog.isSelected());
        if (chkVHDL.isSelected() || chkVerilog.isSelected()) {
            chkVHDLMultiOutFile.setDisable(false);
        } else {
            chkVHDLMultiOutFile.setDisable(true);
        }
    }

    @FXML
    private void click_ChkVHDL() {
        /*
         if (chkVHDL.isSelected()) {
         rbVHDLAlt1.setDisable(false);
         rbVHDLAlt2.setDisable(false);
         } else {
         rbVHDLAlt1.setDisable(true);
         rbVHDLAlt2.setDisable(true);
         }
         */
        rbVHDLAlt1.setDisable(!chkVHDL.isSelected());
        rbVHDLAlt2.setDisable(!chkVHDL.isSelected());
        if (chkVHDL.isSelected() || chkVerilog.isSelected()) {
            chkVHDLMultiOutFile.setDisable(false);
        } else {
            chkVHDLMultiOutFile.setDisable(true);
        }
    }

    @FXML
    private void click_chkC() {
        /*
         if (chkC.isSelected()) {
         rbCAlt1.setDisable(false);
         rbCAlt2.setDisable(false);
         rbCMisrac.setDisable(false);
         } else {
         rbCAlt1.setDisable(true);
         rbCAlt2.setDisable(true);
         rbCMisrac.setDisable(true);
         }
         */
        rbCAlt1.setDisable(!chkC.isSelected());
        rbCAlt2.setDisable(!chkC.isSelected());
        rbCMisrac.setDisable(!chkC.isSelected());
        chkCHeaderMultioutfile.setDisable(!chkC.isSelected());
    }

    @FXML
    private void click_systemc() {
        if (chkSystemC.isSelected()) {
            rbSyscAlt1.setDisable(false);
            //rbSyscAlt2.setDisable(false);
        } else {
            rbSyscAlt1.setDisable(true);
            //rbSyscAlt2.setDisable(true);
        }
    }

    @FXML
    private void click_chkHTML() {
        if (chkHTML.isSelected()) {
            rbHTMLAlt1.setDisable(false);
            rbHTMLAlt2.setDisable(false);
        } else {
            rbHTMLAlt1.setDisable(true);
            rbHTMLAlt2.setDisable(true);
        }
    }

    @FXML
    private void click_chkIPXact() {
        if (chkIPXact.isSelected()) {
            rbIPXact14.setDisable(false);
            rbIPXact15.setDisable(false);
        } else {
            rbIPXact14.setDisable(true);
            rbIPXact15.setDisable(true);
        }
    }

    private void selectUnselectBtns(boolean isSelect) {
        chkARVFormal.setSelected(isSelect);
        chkARVSim.setSelected(isSelect);

        chkC.setSelected(isSelect);
        rbCAlt1.setDisable(!isSelect);
        rbCAlt2.setDisable(!isSelect);
        rbCMisrac.setDisable(!isSelect);
        chkCHeaderMultioutfile.setDisable(!isSelect);

        chkCMSIS.setSelected(isSelect);
        chkCPP.setSelected(isSelect);
        chkCSharp.setSelected(isSelect);
        chkCustomCSV.setSelected(isSelect);
        chkCustomXML.setSelected(isSelect);
        chkDatasheet.setSelected(isSelect);
        chkERm.setSelected(isSelect);
        chkFirmware.setSelected(isSelect);

        chkHTML.setSelected(isSelect);
        rbHTMLAlt1.setDisable(!isSelect);
        rbHTMLAlt2.setDisable(!isSelect);

        chkIPXact.setSelected(isSelect);
        rbIPXact14.setDisable(!isSelect);
        rbHTMLAlt2.setDisable(!isSelect);

        chkIVSExcel.setSelected(isSelect);
        chkOVM.setSelected(isSelect);
        chkPDF.setSelected(isSelect);
        chkPerl.setSelected(isSelect);
        chkPython.setSelected(isSelect);
        chkRTLWire.setSelected(isSelect);
        chkSVG.setSelected(isSelect);
        chkSVHeader.setSelected(isSelect);
        chkSeqUVM.setSelected(isSelect);
        chkSystemC.setSelected(isSelect);
        chkSystemRDL.setSelected(isSelect);
        chkSystemVerilog.setSelected(isSelect);

        chkUVM.setSelected(isSelect);
        chkUVMMultiOutFile.setDisable(!isSelect);

        chkUVMMultiOutFile.setSelected(isSelect);
        chkVHDHeader.setSelected(isSelect);

        chkVHDL.setSelected(isSelect);
        rbVHDLAlt1.setDisable(!isSelect);
        rbVHDLAlt2.setDisable(!isSelect);

        chkCHeaderMultioutfile.setSelected(isSelect);
        chkVHeader.setSelected(isSelect);
        chkVMM.setSelected(isSelect);

        chkVerilog.setSelected(isSelect);
        rbVerilog1995.setDisable(!isSelect);
        rbVerilog2001.setDisable(!isSelect);
        chkVHDLMultiOutFile.setDisable(!isSelect);

        chkXML.setSelected(isSelect);
        chkVHDLMultiOutFile.setSelected(isSelect);
    }

    @FXML
    private void click_chkUVM() {
        chkUVMMultiOutFile.setDisable(!chkUVM.isSelected());
    }

    public void loadPropIntoConfig() {
        txtOutDir.setText(Outputs.getOutputsIns().getOutputdir());
        chkARVFormal.setSelected(Outputs.getOutputsIns().isArvformal());
        chkARVSim.setSelected(Outputs.getOutputsIns().isArvsim());
        chkC.setSelected(Outputs.getOutputsIns().isC());
        rbCAlt1.setSelected(Outputs.getOutputsIns().isCalt1());
        rbCAlt2.setSelected(Outputs.getOutputsIns().isCalt2());
        rbCMisrac.setSelected(Outputs.getOutputsIns().isCmisrac());
        chkCHeaderMultioutfile.setDisable(!chkC.isSelected());

        if (Outputs.getOutputsIns().isCalt1() || Outputs.getOutputsIns().isCalt2() || Outputs.getOutputsIns().isCmisrac()) {
            rbCAlt1.setDisable(false);
            rbCAlt2.setDisable(false);
            rbCMisrac.setDisable(false);
        } else {
            rbCAlt1.setDisable(true);
            rbCAlt2.setDisable(true);
            rbCMisrac.setDisable(true);
        }

        chkCMSIS.setSelected(Outputs.getOutputsIns().isCmsissvd());
        chkCPP.setSelected(Outputs.getOutputsIns().isCpp());
        chkCSharp.setSelected(Outputs.getOutputsIns().isCsharp());
        chkCustomCSV.setSelected(Outputs.getOutputsIns().isCustomcsv());
        chkCustomXML.setSelected(Outputs.getOutputsIns().isCustomxml());
        chkDatasheet.setSelected(Outputs.getOutputsIns().isDatasheet());
        chkERm.setSelected(Outputs.getOutputsIns().isErm());
        chkFirmware.setSelected(Outputs.getOutputsIns().isFirmwawre());

        chkHTML.setSelected(Outputs.getOutputsIns().isHtml());
        rbHTMLAlt1.setSelected(Outputs.getOutputsIns().isHtmlalt1());
        rbHTMLAlt2.setSelected(Outputs.getOutputsIns().isHtmlalt2());
        if (Outputs.getOutputsIns().isHtmlalt1() || Outputs.getOutputsIns().isHtmlalt2()) {
            rbHTMLAlt1.setDisable(false);
            rbHTMLAlt2.setDisable(false);
        } else {
            rbHTMLAlt1.setDisable(true);
            rbHTMLAlt2.setDisable(true);
        }

        chkIPXact.setSelected(Outputs.getOutputsIns().isIpxact());
        rbIPXact14.setSelected(Outputs.getOutputsIns().isIpxactv14());
        rbIPXact15.setSelected(Outputs.getOutputsIns().isIpxactv15());
        if (Outputs.getOutputsIns().isIpxactv14() || Outputs.getOutputsIns().isIpxactv15()) {
            rbIPXact14.setDisable(false);
            rbIPXact15.setDisable(false);
        } else {
            rbIPXact14.setDisable(true);
            rbIPXact15.setDisable(true);
        }

        chkIVSExcel.setSelected(Outputs.getOutputsIns().isIvsexcel());
        chkOVM.setSelected(Outputs.getOutputsIns().isOvm());
        chkPDF.setSelected(Outputs.getOutputsIns().isPdf());
        chkPerl.setSelected(Outputs.getOutputsIns().isPerl());
        chkPython.setSelected(Outputs.getOutputsIns().isPython());
        chkRTLWire.setSelected(Outputs.getOutputsIns().isRtlwire());
        chkSeqUVM.setSelected(Outputs.getOutputsIns().isSeqUVM());
        chkSVG.setSelected(Outputs.getOutputsIns().isSvg());
        chkSVHeader.setSelected(Outputs.getOutputsIns().isSvheader());
        //chkSystemC.setSelected(Outputs.getOutputsIns().isSystemc());
        if (Outputs.getOutputsIns().isSystemc()) {
            chkSystemC.setSelected(true);
            rbSyscAlt1.setSelected(true);
            rbSyscAlt1.setDisable(false);
            //rbSyscAlt2.setDisable(false);
        } else if (Outputs.getOutputsIns().isSystemcalt2()) {
            chkSystemC.setSelected(true);
            rbSyscAlt2.setSelected(true);
            rbSyscAlt1.setDisable(false);
            //rbSyscAlt2.setDisable(false);
        } else {
            chkSystemC.setSelected(false);
            rbSyscAlt1.setDisable(true);
            //rbSyscAlt2.setDisable(true);
        }

        chkSystemRDL.setSelected(Outputs.getOutputsIns().isSystemrdl());
        chkSystemVerilog.setSelected(Outputs.getOutputsIns().isSystemverilog());

        chkUVM.setSelected(Outputs.getOutputsIns().isUvm());
        chkUVMMultiOutFile.setSelected(Outputs.getOutputsIns().isUvmmultioutfile());
        chkUVMMultiOutFile.setDisable(!chkUVM.isSelected());

        if (Outputs.getOutputsIns().isVerilog()) {
            chkVerilog.setSelected(true);
            rbVerilog1995.setSelected(true);
            rbVerilog2001.setSelected(false);
        } else if (Outputs.getOutputsIns().isVerilog2001()) {
            chkVerilog.setSelected(true);
            rbVerilog1995.setSelected(false);
            rbVerilog2001.setSelected(true);

        } else {
            chkVerilog.setSelected(false);
        }

        if (Outputs.getOutputsIns().isVerilog() || Outputs.getOutputsIns().isVerilog2001()) {
            rbVerilog1995.setDisable(false);
            rbVerilog2001.setDisable(false);
        } else {
            rbVerilog1995.setDisable(true);
            rbVerilog2001.setDisable(true);
        }

        chkVHDHeader.setSelected(Outputs.getOutputsIns().isVhdheader());

        if (Outputs.getOutputsIns().isVhdl()) {
            chkVHDL.setSelected(true);
            rbVHDLAlt1.setSelected(true);
            rbVHDLAlt2.setSelected(false);
        } else if (Outputs.getOutputsIns().isVhdlalt2()) {
            chkVHDL.setSelected(true);
            rbVHDLAlt1.setSelected(false);
            rbVHDLAlt2.setSelected(true);
        } else {
            chkVHDL.setSelected(false);
        }

        if (Outputs.getOutputsIns().isVhdl() || Outputs.getOutputsIns().isVhdlalt2()) {
            rbVHDLAlt1.setDisable(false);
            rbVHDLAlt2.setDisable(false);
        } else {
            rbVHDLAlt1.setDisable(true);
            rbVHDLAlt2.setDisable(true);
        }

        if (chkVHDL.isSelected() || chkVerilog.isSelected()) {
            chkVHDLMultiOutFile.setDisable(false);
        } else {
            chkVHDLMultiOutFile.setDisable(true);
        }
        chkVHDLMultiOutFile.setSelected(Outputs.getOutputsIns().isVhdlmultioutfile());
        chkCHeaderMultioutfile.setSelected(Outputs.getOutputsIns().isCmultiout());
        chkVHeader.setSelected(Outputs.getOutputsIns().isVheader());
        chkVMM.setSelected(Outputs.getOutputsIns().isVmm());
        chkXML.setSelected(Outputs.getOutputsIns().isXml());
    }

    public void saveOutputsIntoProp() {

        Outputs.getOutputsIns().setArvformal(chkARVFormal.isSelected());
        Outputs.getOutputsIns().setArvsim(chkARVSim.isSelected());
        Outputs.getOutputsIns().setC(chkC.isSelected());
        Outputs.getOutputsIns().setCalt1(rbCAlt1.isSelected());
        Outputs.getOutputsIns().setCalt2(rbCAlt2.isSelected());
        Outputs.getOutputsIns().setCmisrac(rbCMisrac.isSelected());
        Outputs.getOutputsIns().setCmsissvd(chkCMSIS.isSelected());
        Outputs.getOutputsIns().setCpp(chkCPP.isSelected());
        Outputs.getOutputsIns().setCsharp(chkCSharp.isSelected());
        Outputs.getOutputsIns().setCustomcsv(chkCustomCSV.isSelected());
        Outputs.getOutputsIns().setCustomxml(chkCustomXML.isSelected());
        Outputs.getOutputsIns().setDatasheet(chkDatasheet.isSelected());
        Outputs.getOutputsIns().setErm(chkERm.isSelected());
        Outputs.getOutputsIns().setFirmwawre(chkFirmware.isSelected());
        Outputs.getOutputsIns().setHtml(chkHTML.isSelected());
        Outputs.getOutputsIns().setHtmlalt1(rbHTMLAlt1.isSelected());
        Outputs.getOutputsIns().setHtmlalt2(rbHTMLAlt2.isSelected());
        Outputs.getOutputsIns().setIpxact(chkIPXact.isSelected());
        Outputs.getOutputsIns().setIpxactv14(rbIPXact14.isSelected());
        Outputs.getOutputsIns().setIpxactv15(rbIPXact15.isSelected());

        Outputs.getOutputsIns().setIvsexcel(chkIVSExcel.isSelected());
        Outputs.getOutputsIns().setOutputdir(txtOutDir.getText());
        Outputs.getOutputsIns().setOvm(chkOVM.isSelected());
        Outputs.getOutputsIns().setPdf(chkPDF.isSelected());
        Outputs.getOutputsIns().setPerl(chkPerl.isSelected());
        Outputs.getOutputsIns().setPython(chkPython.isSelected());
        Outputs.getOutputsIns().setRtlwire(chkRTLWire.isSelected());
        Outputs.getOutputsIns().setSvg(chkSVG.isSelected());
        Outputs.getOutputsIns().setSvheader(chkSVHeader.isSelected());
        Outputs.getOutputsIns().setSeqUVM(chkSeqUVM.isSelected());
        //Outputs.getOutputsIns().setSystemc(chkSystemC.isSelected());
        if (chkSystemC.isSelected()) {
            if (rbSyscAlt1.isSelected()) {
                Outputs.getOutputsIns().setSystemc(true);
                Outputs.getOutputsIns().setSystemcalt2(false);
            } else if (rbSyscAlt2.isSelected()) {
                Outputs.getOutputsIns().setSystemc(false);
                Outputs.getOutputsIns().setSystemcalt2(true);
            }
        } else {
            Outputs.getOutputsIns().setSystemc(false);
            Outputs.getOutputsIns().setSystemcalt2(false);
        }

        Outputs.getOutputsIns().setSystemrdl(chkSystemRDL.isSelected());
        Outputs.getOutputsIns().setSystemverilog(chkSystemVerilog.isSelected());
        Outputs.getOutputsIns().setUvm(chkUVM.isSelected());
        Outputs.getOutputsIns().setUvmmultioutfile(chkUVMMultiOutFile.isSelected());
        Outputs.getOutputsIns().setVerilog(chkVerilog.isSelected());
        Outputs.getOutputsIns().setVerilog1995(rbVerilog1995.isSelected());
        Outputs.getOutputsIns().setVerilog2001(rbVerilog2001.isSelected());
        Outputs.getOutputsIns().setVhdheader(chkVHDHeader.isSelected());
        Outputs.getOutputsIns().setVhdl(chkVHDL.isSelected());
        Outputs.getOutputsIns().setVhdlalt1(rbVHDLAlt1.isSelected());
        Outputs.getOutputsIns().setVhdlalt2(rbVHDLAlt2.isSelected());
        Outputs.getOutputsIns().setVhdlmultioutfile(chkVHDLMultiOutFile.isSelected());
        Outputs.getOutputsIns().setCmultiout(chkCHeaderMultioutfile.isSelected());
        Outputs.getOutputsIns().setVheader(chkVHeader.isSelected());
        Outputs.getOutputsIns().setVmm(chkVMM.isSelected());
        Outputs.getOutputsIns().setXml(chkXML.isSelected());

    }
}
