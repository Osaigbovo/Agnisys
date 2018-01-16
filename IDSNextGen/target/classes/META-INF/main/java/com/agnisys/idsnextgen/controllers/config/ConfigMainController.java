/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.controllers.config.properties.AdvanceDesign;
import com.agnisys.idsnextgen.controllers.config.properties.AdvanceVerification;
import com.agnisys.idsnextgen.controllers.config.properties.General;
import com.agnisys.idsnextgen.controllers.config.properties.Outputs;
import com.agnisys.idsnextgen.controllers.config.properties.Settings;
import com.agnisys.idsnextgen.controllers.config.properties.Template;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class ConfigMainController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="variables declarations">
    @FXML
    Pane paneMainContainer;

    @FXML
    Hyperlink hypLnkGeneral;
    @FXML
    Hyperlink hypLnkOutPut;
    @FXML
    Hyperlink hypLnkUserDefined;
    @FXML
    Hyperlink hypLnkSettings;
    @FXML
    Hyperlink hypLnkVariants;
    @FXML
    Hyperlink hypLnkFormating;
    @FXML
    Hyperlink hypLnkDataSheet;
    @FXML
    Hyperlink hypLnkCustomCSV;
    @FXML
    Hyperlink hypLnkAdvanceVerification;
    @FXML
    Hyperlink hypLnkAdvanceDesign;
    @FXML
    Label lblConfigMainHeader;
    @FXML
    Button btnOk;
    @FXML
    Button btnCancel;

    AnchorPane ConfigGeneral;
    AnchorPane ConfigOutputs;
    AnchorPane ConfigUserDefinedOutputs;
    AnchorPane ConfigSettings;
    AnchorPane ConfigVariant;
    AnchorPane ConfigFormating;
    AnchorPane ConfigDatasheet;
    AnchorPane ConfigCustomCSV;
    AnchorPane ConfigTemplate;
    AnchorPane ConfigAdvanceVerification;
    AnchorPane ConfigAdvanceDesign;
    ConfigGeneralController generalController;
    public ConfigOutputsController outputsController;
    ConfigAdvanceDesignController advanceDesignController;
    ConfigAdvanceVerificationController advanceVerificationController;
    ConfigCustomCSVController customCSVController;
    ConfigDatasheetController datasheetController;
    ConfigFormatingController formatingController;
    ConfigSettingsController settingsController;
    ConfigTemplateController templateController;
    ConfigUserDefineOutputsController userDefineController;
    ConfigVariantController variantController;

    final String CONFIG_FILE_NAME = "idsngconfig.xml";
    final String CONFIG_DIR_NAME = ".idsng";
    public static String CONFIGXMLPATH = "";
    //</editor-fold>

    public boolean isCheck = false;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        bindStrings();
        initalizeInstances();
        loadConfig();
        showGeneral();
    }

    void initalizeInstances() {
        try {
            FXMLLoader loader;

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigGeneral.fxml"));
            ConfigGeneral = loader.load();
            generalController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigOutputs.fxml"));
            ConfigOutputs = loader.load();
            outputsController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigUserDefineOutputs.fxml"));
            ConfigUserDefinedOutputs = loader.load();
            userDefineController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigSettings.fxml"));
            ConfigSettings = loader.load();
            settingsController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigVariant.fxml"));
            ConfigVariant = loader.load();
            variantController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigFormating.fxml"));
            ConfigFormating = loader.load();
            formatingController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigDatasheet.fxml"));
            ConfigDatasheet = loader.load();
            datasheetController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigCustomCSV.fxml"));
            ConfigCustomCSV = loader.load();
            customCSVController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigTemplate.fxml"));
            ConfigTemplate = loader.load();
            templateController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigAdvanceVerification.fxml"));
            ConfigAdvanceVerification = loader.load();
            advanceVerificationController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/designs/config/ConfigAdvanceDesign.fxml"));
            ConfigAdvanceDesign = loader.load();
            advanceDesignController = loader.getController();

        } catch (Exception e) {
            System.err.println("Err initIns " + e.getMessage());
        }
    }

    @FXML
    void click_saveConifig() {
        saveConfig();
    }

    public void loadConfig() {
        boolean isConfigFound = loadConfigIntoProp();
        if (isConfigFound) {
            loadConfigIntoWindow();
        }
    }

    public boolean loadConfigIntoProp() {
        CONFIGXMLPATH = ApplicationMainGUIController.APPLICATION_OBJECT.getCURRENT_PROJECT_ROOT() + File.separator + CONFIG_DIR_NAME + File.separator + CONFIG_FILE_NAME;
        File configFile = new File(CONFIGXMLPATH);
        if (configFile.exists()) {
            Document document;
            SAXReader saxReader = new SAXReader();
            try {
                document = saxReader.read(configFile);
                Element root = document.getRootElement();
                General.getGeneralIns().setCompany(root.elementText("company"));
                General.getGeneralIns().setCopyright(root.elementText("copyright"));
                General.getGeneralIns().setLog(Boolean.parseBoolean(root.elementText("log")));

                Settings.getSettingIns().setAddressunit(root.elementText("unit"));
                Settings.getSettingIns().setBlocksize(root.elementText("block_size"));
                Settings.getSettingIns().setBoardsize(root.elementText("board_size"));
                Settings.getSettingIns().setBus(root.elementText("bus"));
                Settings.getSettingIns().setChipsize(root.elementText("chip_size"));
                Settings.getSettingIns().setCtype(root.elementText("c_type"));
                Settings.getSettingIns().setBigendian(Boolean.parseBoolean(root.elementText("big_endian")));
                Settings.getSettingIns().setLittleendian(Boolean.parseBoolean(root.elementText("little_endian")));

                if (root.elementText("reg_width") != null) {
                    Settings.getSettingIns().setRegbuswidth(root.elementText("reg_width"));
                }
                if (root.elementText("bus_width") != null) {
                    Settings.getSettingIns().setbuswidth(root.elementText("bus_width"));
                }

                Element output = (Element) root.selectSingleNode("outputs");
                if (output != null) {
                    if (Boolean.parseBoolean(output.elementText("verilog"))) {
                        Outputs.getOutputsIns().setVerilog(true);
                        Outputs.getOutputsIns().setVerilog2001(false);
                    } else if (Boolean.parseBoolean(output.elementText("verilog2001"))) {
                        Outputs.getOutputsIns().setVerilog2001(true);
                        Outputs.getOutputsIns().setVerilog(false);
                    } else {
                        Outputs.getOutputsIns().setVerilog(false);
                        Outputs.getOutputsIns().setVerilog2001(false);
                    }

                    Outputs.getOutputsIns().setVhdl(Boolean.parseBoolean(output.elementText("vhdl")));
                    Outputs.getOutputsIns().setVhdlalt1(Boolean.parseBoolean(output.elementText("vhdlalt1")));
                    Outputs.getOutputsIns().setVhdlalt2(Boolean.parseBoolean(output.elementText("vhdlalt2")));

                    if (Boolean.parseBoolean(output.elementText("vhdl"))) {
                        Outputs.getOutputsIns().setVhdl(true);
                        Outputs.getOutputsIns().setVhdlalt2(false);
                    } else if (Boolean.parseBoolean(output.elementText("vhdlalt2"))) {
                        Outputs.getOutputsIns().setVhdl(false);
                        Outputs.getOutputsIns().setVhdlalt2(true);
                    } else {
                        Outputs.getOutputsIns().setVhdl(false);
                        Outputs.getOutputsIns().setVhdlalt2(false);
                    }

                    //if (root.elementText("rtl_wire") != null) {
                    Outputs.getOutputsIns().setRtlwire(Boolean.parseBoolean(root.elementText("rtl_wire")));
                    //}
                    Boolean alt1;
                    Boolean alt2;
                    // Boolean misrac;
                    Outputs.getOutputsIns().setCustomcsv(Boolean.parseBoolean(output.elementText("customcsv")));

                    Outputs.getOutputsIns().setDatasheet(Boolean.parseBoolean(output.elementText("datasheet")));
                    Outputs.getOutputsIns().setOutputdir(root.elementText("outdir"));
                    Outputs.getOutputsIns().setSystemrdl(Boolean.parseBoolean(output.elementText("rdl")));

                    if (Boolean.parseBoolean(output.elementText("header"))) {
                        Outputs.getOutputsIns().setCalt1(true);
                        Outputs.getOutputsIns().setCalt2(false);
                        Outputs.getOutputsIns().setCmisrac(false);
                    } else if (Boolean.parseBoolean(output.elementText("headeralt2"))) {
                        Outputs.getOutputsIns().setCalt1(false);
                        Outputs.getOutputsIns().setCalt2(true);
                        Outputs.getOutputsIns().setCmisrac(false);
                    } else if (Boolean.parseBoolean(output.elementText("misrac"))) {
                        Outputs.getOutputsIns().setCalt1(false);
                        Outputs.getOutputsIns().setCalt2(false);
                        Outputs.getOutputsIns().setCmisrac(true);
                    } else {
                        Outputs.getOutputsIns().setCalt1(false);
                        Outputs.getOutputsIns().setCalt2(false);
                        Outputs.getOutputsIns().setCmisrac(false);
                    }

                    alt1 = Boolean.parseBoolean(output.elementText("html"));
                    alt2 = Boolean.parseBoolean(output.elementText("htmlalt2"));
                    if (alt1) {
                        Outputs.getOutputsIns().setHtml(true);
                        Outputs.getOutputsIns().setHtmlalt1(true);
                        Outputs.getOutputsIns().setHtmlalt2(false);
                    } else if (alt2) {
                        Outputs.getOutputsIns().setHtml(true);
                        Outputs.getOutputsIns().setHtmlalt1(false);
                        Outputs.getOutputsIns().setHtmlalt2(true);
                    } else {
                        Outputs.getOutputsIns().setHtml(false);
                        Outputs.getOutputsIns().setHtmlalt1(false);
                        Outputs.getOutputsIns().setHtmlalt1(false);
                    }

                    //Outputs.getOutputsIns().setSystemc(Boolean.parseBoolean(output.elementText("systemc")));
                    if (Boolean.parseBoolean(output.elementText("rtl_sysc"))) {
                        Outputs.getOutputsIns().setSystemc(true);
                    } else if (Boolean.parseBoolean(output.elementText("rtl_sysc_alt2"))) {
                        Outputs.getOutputsIns().setSystemcalt2(true);
                    }

                    Outputs.getOutputsIns().setIvsexcel(Boolean.parseBoolean(output.elementText("ivs")));
                    Outputs.getOutputsIns().setCsharp(Boolean.parseBoolean(output.elementText("csharp")));
                    Outputs.getOutputsIns().setSystemverilog(Boolean.parseBoolean(output.elementText("sv")));
                    //Outputs.getOutputsIns().setRtlwire(Boolean.parseBoolean(output.elementText("rtl_wire")));
                    Outputs.getOutputsIns().setArvsim(Boolean.parseBoolean(output.elementText("arv")));
                    Outputs.getOutputsIns().setCmsissvd(Boolean.parseBoolean(output.elementText("cmsis")));
                    //Outputs.getOutputsIns().setSystemc(Boolean.parseBoolean(output.elementText("sysc")));
                    //Outputs.getOutputsIns().setVhdlmultioutfile(Boolean.parseBoolean(output.elementText("multi_out_file")));
                    //Outputs.getOutputsIns().setUvmmultioutfile(Boolean.parseBoolean(output.elementText("multi_out_file_uvm")));
                    if (Boolean.parseBoolean(root.elementText("if_vhdl")) || Boolean.parseBoolean(root.elementText("if_verilog"))) {
                        Outputs.getOutputsIns().setVhdlmultioutfile(true);
                    } else {
                        Outputs.getOutputsIns().setVhdlmultioutfile(false);
                    }
                    Outputs.getOutputsIns().setUvmmultioutfile(Boolean.parseBoolean(root.elementText("if_uvm")));
                    Outputs.getOutputsIns().setCmultiout(Boolean.parseBoolean(root.elementText("if_header")));

                    Outputs.getOutputsIns().setPerl(Boolean.parseBoolean(output.elementText("perl")));
                    Outputs.getOutputsIns().setPython(Boolean.parseBoolean(output.elementText("python")));
                    Outputs.getOutputsIns().setCpp(Boolean.parseBoolean(output.elementText("cpp")));
                    Outputs.getOutputsIns().setVheader(Boolean.parseBoolean(output.elementText("vheader")));
                    Outputs.getOutputsIns().setVhdheader(Boolean.parseBoolean(output.elementText("vhdheader")));
                    Outputs.getOutputsIns().setArvformal(Boolean.parseBoolean(output.elementText("formal")));
                    Outputs.getOutputsIns().setPdf(Boolean.parseBoolean(output.elementText("pdf")));
                    Outputs.getOutputsIns().setCustomxml(Boolean.parseBoolean(output.elementText("customxml")));
                    Outputs.getOutputsIns().setOvm(Boolean.parseBoolean(output.elementText("ovm")));
                    Outputs.getOutputsIns().setVmm(Boolean.parseBoolean(output.elementText("vmm")));
                    Outputs.getOutputsIns().setFirmwawre(Boolean.parseBoolean(output.elementText("iss_firmware")));
                    Outputs.getOutputsIns().setSeqUVM(Boolean.parseBoolean(output.elementText("iss_sequence")));
                    Outputs.getOutputsIns().setXml(Boolean.parseBoolean(output.elementText("xrsl")));

                    alt1 = Boolean.parseBoolean(output.elementText("ipxact1_4"));
                    alt2 = Boolean.parseBoolean(output.elementText("ipxact1_5"));
                    if (alt1) {
                        Outputs.getOutputsIns().setIpxact(true);
                        Outputs.getOutputsIns().setIpxactv14(true);
                        Outputs.getOutputsIns().setIpxactv15(false);
                    } else if (alt2) {
                        Outputs.getOutputsIns().setIpxact(true);
                        Outputs.getOutputsIns().setIpxactv14(false);
                        Outputs.getOutputsIns().setIpxactv15(true);
                    } else {
                        Outputs.getOutputsIns().setIpxact(false);
                        Outputs.getOutputsIns().setIpxactv14(false);
                        Outputs.getOutputsIns().setIpxactv15(false);
                    }

                    Outputs.getOutputsIns().setErm(Boolean.parseBoolean(output.elementText("erm")));
                    Outputs.getOutputsIns().setSvheader(Boolean.parseBoolean(output.elementText("svheader")));
                    Outputs.getOutputsIns().setSvg(Boolean.parseBoolean(output.elementText("svg")));
                    AdvanceDesign.getAdvanceDesignIns().setOptimize(Boolean.parseBoolean(root.elementText("lowpower")));
                    AdvanceDesign.getAdvanceDesignIns().setMemorytech(Boolean.parseBoolean(root.elementText("memtechmapping")));

                    AdvanceVerification.getAdvanceVerificationInst().setGenerateIlligalBeans(Boolean.parseBoolean(root.elementText("illegalbins")));
                    AdvanceVerification.getAdvanceVerificationInst().setGenerateconstraints(Boolean.parseBoolean(root.elementText("arv_constraint")));
                    AdvanceVerification.getAdvanceVerificationInst().setGeneratecoverage(Boolean.parseBoolean(root.elementText("coverage")));
                    AdvanceVerification.getAdvanceVerificationInst().setGenerateuvmcov(Boolean.parseBoolean(root.elementText("arv_coverage")));
                    AdvanceVerification.getAdvanceVerificationInst().setGenerateuvmhdl(Boolean.parseBoolean(root.elementText("hdlpath")));

                }
                return true;
            } catch (Exception e) {
                System.err.println("Err (loadConfigIntoProp) : " + e.getMessage());
            }
        }
        return false;
    }

    private void loadConfigIntoWindow() {
        generalController.loadPropIntoConfig();
        settingsController.loadPropIntoConfig();
        outputsController.loadPropIntoConfig();
        templateController.loadPropIntoConfig();
        advanceDesignController.loadPropIntoConfig();
        advanceVerificationController.loadPropIntoConfig();
    }

    public void saveConfig() {
        saveConfigIntoProp();
        saveConfigIntoXMLFile();

    }

    private void saveConfigIntoProp() {
        generalController.saveIntoProp();
        outputsController.saveOutputsIntoProp();
        settingsController.saveSettingIntoProp();
        //templateController.saveTemplateIntoProp();
        advanceDesignController.saveTemplateIntoProp();
        advanceVerificationController.saveTemplateIntoProp();
    }

    public void addCheckOnlyIntoXML(String checkValue) {
        try {
            File xmlFile = new File(getConfigFilePath());
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);

            Element root = document.getRootElement();
            Element check = (Element) root.selectSingleNode("check_only");
            if (check != null) {
                check.setText(checkValue);
            } else {
                check = document.addElement("check_only");
                check.setText(checkValue);
                root.add(check);
            }

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;

            FileOutputStream fl = new FileOutputStream(xmlFile);

            writer = new XMLWriter(fl, format);
            writer.write(document);
            writer.flush();

        } catch (DocumentException | IOException e) {
            System.err.println("Err addCheckOnlyIntoXML : " + e.getMessage());
        }
    }

    public String getConfigFilePath() {
        String configXMLPath = ApplicationMainGUIController.APPLICATION_OBJECT.getCURRENT_PROJECT_ROOT() + File.separator + CONFIG_DIR_NAME;
        try {
            File xmlFile = new File(configXMLPath);
            if (!xmlFile.exists()) {
                //create config directory
                xmlFile.mkdirs();
            }
            return configXMLPath + File.separator + CONFIG_FILE_NAME;

        } catch (Exception e) {
            System.err.println("Err getConfigFilePath : " + e.getMessage());
        }
        return null;
    }

    public void saveConfigIntoXMLFile() {
        //String configXMLPath = ApplicationMainGUIController.APPLICATION_OBJECT.getCURRENT_PROJECT_ROOT() + File.separator + CONFIG_DIR_NAME;
        try {
            /*File xmlFile = new File(configXMLPath);
             if (!xmlFile.exists()) {
             //create config directory
             xmlFile.mkdirs();
             }

             xmlFile = new File(configXMLPath + File.separator + CONFIG_FILE_NAME);*/
            File xmlFile = new File(getConfigFilePath());
            Document document;

            if (xmlFile.exists()) {
                xmlFile.delete();
            }

            document = DocumentHelper.createDocument();
            Element root = document.addElement("settings");

            //<editor-fold defaultstate="collapsed" desc="General setting write into config">
            root.addElement("company").setText(General.getGeneralIns().getCompany());
            root.addElement("copyright").setText(General.getGeneralIns().getCopyright());
            root.addElement("log").setText(String.valueOf(General.getGeneralIns().isLog()));
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="output setting write in config">
            root.addElement("outdir").setText(Outputs.getOutputsIns().getOutputdir());
            Element outputs = root.addElement("outputs");
            //outputs.addElement("verilog").setText(String.valueOf(Outputs.getOutputsIns().isVerilog()));
            //outputs.addElement("vhdl").setText(String.valueOf(Outputs.getOutputsIns().isVhdl()));

            boolean isVHDL = false;
            if (Outputs.getOutputsIns().isVhdl()) {
                if (Outputs.getOutputsIns().isVhdlalt1()) {
                    outputs.addElement("vhdl").setText(String.valueOf(Outputs.getOutputsIns().isVhdl()));
                    isVHDL = true;
                } else if (Outputs.getOutputsIns().isVhdlalt2()) {
                    outputs.addElement("vhdlalt2").setText(String.valueOf(Outputs.getOutputsIns().isVhdl()));
                    isVHDL = true;
                }
            }

            boolean isVerilog = false;
            if (Outputs.getOutputsIns().isVerilog()) {
                if (Outputs.getOutputsIns().isVerilog1995()) {
                    outputs.addElement("verilog").setText("true");
                    outputs.addElement("verilog2001").setText("false");
                    isVerilog = true;
                } else if (Outputs.getOutputsIns().isVerilog2001()) {
                    outputs.addElement("verilog").setText("false");
                    outputs.addElement("verilog2001").setText("true");
                    isVerilog = true;
                }
            }

            //outputs.addElement("rtl_wire").setText(String.valueOf(Outputs.getOutputsIns().isRtlwire()));
            //outputs.addElement("ocp").setText(String.valueOf(false));
            boolean isC = false;
            if (Outputs.getOutputsIns().isC()) {
                if (Outputs.getOutputsIns().isCalt1()) {
                    outputs.addElement("header").setText("true");
                    outputs.addElement("headeralt2").setText("false");
                    outputs.addElement("misrac").setText("false");
                    isC = true;
                } else if (Outputs.getOutputsIns().isCalt2()) {
                    outputs.addElement("header").setText("false");
                    outputs.addElement("headeralt2").setText("true");
                    outputs.addElement("misrac").setText("false");
                    isC = true;
                } else if (Outputs.getOutputsIns().isCmisrac()) {
                    outputs.addElement("header").setText("false");
                    outputs.addElement("headeralt2").setText("false");
                    outputs.addElement("misrac").setText("true");
                    isC = true;
                }
            }

            if (Outputs.getOutputsIns().isHtml()) {
                if (Outputs.getOutputsIns().isHtmlalt1()) {
                    outputs.addElement("html").setText("true");
                    outputs.addElement("htmlalt2").setText("false");
                } else if (Outputs.getOutputsIns().isHtmlalt2()) {
                    outputs.addElement("html").setText("false");
                    outputs.addElement("htmlalt2").setText("true");
                }
            }

            //outputs.addElement("systemc").setText(String.valueOf(Outputs.getOutputsIns().isSystemc()));
            if (Outputs.getOutputsIns().isSystemc()) {
                outputs.addElement("rtl_sysc").setText("true");
            } else if (Outputs.getOutputsIns().isSystemcalt2()) {
                outputs.addElement("rtl_sysc_alt2").setText("true");
            }

            outputs.addElement("ivs").setText(String.valueOf(Outputs.getOutputsIns().isIvsexcel()));

            outputs.addElement("arv").setText(String.valueOf(Outputs.getOutputsIns().isArvsim()));
            outputs.addElement("special_reg").setText("");
            outputs.addElement("cmsis").setText(String.valueOf(Outputs.getOutputsIns().isCmsissvd()));
            //outputs.addElement("sysc").setText(String.valueOf(Outputs.getOutputsIns().isSystemc()));
            //outputs.addElement("multi_out_file").setText(String.valueOf(Outputs.getOutputsIns().isVhdlmultioutfile()));
            //outputs.addElement("multi_out_file_uvm").setText(String.valueOf(Outputs.getOutputsIns().isUvmmultioutfile()));
            boolean isUVM = Outputs.getOutputsIns().isUvm();
            outputs.addElement("uvm").setText(String.valueOf(isUVM));

            outputs.addElement("perl").setText(String.valueOf(Outputs.getOutputsIns().isPerl()));
            outputs.addElement("python").setText(String.valueOf(Outputs.getOutputsIns().isPython()));
            outputs.addElement("cpp").setText(String.valueOf(Outputs.getOutputsIns().isCpp()));
            outputs.addElement("vheader").setText(String.valueOf(Outputs.getOutputsIns().isVheader()));
            outputs.addElement("vhdheader").setText(String.valueOf(Outputs.getOutputsIns().isVhdheader()));
            outputs.addElement("formal").setText(String.valueOf(Outputs.getOutputsIns().isArvformal()));
            outputs.addElement("pdf").setText(String.valueOf(Outputs.getOutputsIns().isPdf()));
            //outputs.addElement("CsvHeaderChk").setText(String.valueOf(Outputs.getOutputsIns().isCustomcsv()));
            outputs.addElement("Rtl2001").setText(String.valueOf(Outputs.getOutputsIns().isVerilog2001()));
            outputs.addElement("ovm").setText(String.valueOf(Outputs.getOutputsIns().isOvm()));
            outputs.addElement("vmm").setText(String.valueOf(Outputs.getOutputsIns().isVmm()));
            outputs.addElement("iss_firmware").setText(String.valueOf(Outputs.getOutputsIns().isFirmwawre()));
            outputs.addElement("iss_sequence").setText(String.valueOf(Outputs.getOutputsIns().isSeqUVM()));
            outputs.addElement("header").setText(String.valueOf(Outputs.getOutputsIns().isC()));
            outputs.addElement("html").setText(String.valueOf(Outputs.getOutputsIns().isHtml()));
            outputs.addElement("xrsl").setText(String.valueOf(Outputs.getOutputsIns().isXml()));

            /*
             outputs.addElement("ipxact").setText(String.valueOf(Outputs.getOutputsIns().isIpxact()));
             if (Outputs.getOutputsIns().isIpxact() && Outputs.getOutputsIns().isIpxactv14()) {
             outputs.addElement("ipxact1_4").setText("true");
             outputs.addElement("ipxact1_5").setText("false");
             } else if (Outputs.getOutputsIns().isIpxact() && Outputs.getOutputsIns().isIpxactv14()) {
             outputs.addElement("ipxact1_4").setText("false");
             outputs.addElement("ipxact1_5").setText("true");
             } else {
             outputs.addElement("ipxact1_4").setText("false");
             outputs.addElement("ipxact1_5").setText("false");
             }
             */
            if (Outputs.getOutputsIns().isIpxact()) {
                if (Outputs.getOutputsIns().isIpxactv14()) {
                    outputs.addElement("ipxact1_4").setText("true");
                    outputs.addElement("ipxact1_5").setText("false");
                } else if (Outputs.getOutputsIns().isIpxactv15()) {
                    outputs.addElement("ipxact1_4").setText("false");
                    outputs.addElement("ipxact1_5").setText("true");
                }
            }

            outputs.addElement("rdl").setText(String.valueOf(Outputs.getOutputsIns().isSystemrdl()));
            //outputs.addElement("pdf").setText(String.valueOf(Outputs.getOutputsIns().isPdf()));
            outputs.addElement("erm").setText(String.valueOf(Outputs.getOutputsIns().isErm()));
            outputs.addElement("ivsxml").setText(String.valueOf(Outputs.getOutputsIns().isIvsexcel()));
            outputs.addElement("svheader").setText(String.valueOf(Outputs.getOutputsIns().isSvheader()));
            outputs.addElement("svg").setText(String.valueOf(Outputs.getOutputsIns().isSvg()));
            outputs.addElement("datasheet").setText(String.valueOf(Outputs.getOutputsIns().isDatasheet()));
            outputs.addElement("customcsv").setText(String.valueOf(Outputs.getOutputsIns().isCustomcsv()));
            outputs.addElement("customxml").setText(String.valueOf(Outputs.getOutputsIns().isCustomxml()));
            outputs.addElement("csharp").setText(String.valueOf(Outputs.getOutputsIns().isCsharp()));
            outputs.addElement("sv").setText(String.valueOf(Outputs.getOutputsIns().isSystemverilog()));
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Settings config write into config">
            if (isC && Outputs.getOutputsIns().isCmultiout()) {
                root.addElement("if_header").setText("true");
            }

            if (isVHDL && Outputs.getOutputsIns().isVhdlmultioutfile()) {
                root.addElement("if_vhdl").setText("true");
            }

            if (isVerilog && Outputs.getOutputsIns().isVhdlmultioutfile()) {
                root.addElement("if_verilog").setText("true");
            }

            if (isUVM && Outputs.getOutputsIns().isUvmmultioutfile()) {
                root.addElement("if_uvm").setText("true");
            }

            root.addElement("rtl_wire").setText(String.valueOf(Outputs.getOutputsIns().isRtlwire()));
            root.addElement("limit_toc").setText("");
            root.addElement("lowpower").setText(String.valueOf(AdvanceDesign.getAdvanceDesignIns().isOptimize()));
            root.addElement("auto_sequence").setText("");
            root.addElement("interrupt").setText("");
            root.addElement("mbd").setText("");
            root.addElement("arv_assertion").setText("");
            root.addElement("memtechmapping").setText(String.valueOf(AdvanceDesign.getAdvanceDesignIns().isMemorytech()));
            root.addElement("sv_w_intf").setText("");
            root.addElement("sv_wo_intf").setText("");
            root.addElement("uvm_env").setText("");

            root.addElement("hdlpath").setText(String.valueOf(AdvanceVerification.getAdvanceVerificationInst().isGenerateuvmhdl()));
            root.addElement("arv_coverage").setText(String.valueOf(AdvanceVerification.getAdvanceVerificationInst().isGenerateuvmcov()));
            root.addElement("coverage").setText(String.valueOf(AdvanceVerification.getAdvanceVerificationInst().isGeneratecoverage()));
            root.addElement("illegalbins").setText(String.valueOf(AdvanceVerification.getAdvanceVerificationInst().isGenerateIlligalBeans()));
            root.addElement("arv_constraint").setText(String.valueOf(AdvanceVerification.getAdvanceVerificationInst().isGenerateconstraints()));

            root.addElement("bus").setText(Settings.getSettingIns().getBus());
            root.addElement("reg_width").setText(Settings.getSettingIns().getRegbuswidth());
            root.addElement("bus_width").setText(Settings.getSettingIns().getbuswidth());

            root.addElement("unit").setText(Settings.getSettingIns().getAddressunit());

            root.addElement("block_size").setText(Settings.getSettingIns().getBlocksize());
            root.addElement("chip_size").setText(Settings.getSettingIns().getChipsize());
            root.addElement("board_size").setText(Settings.getSettingIns().getBoardsize());
            root.addElement("c_type").setText(Settings.getSettingIns().getCtype());

            root.addElement("big_endian").setText(String.valueOf(Settings.getSettingIns().isBigendian()));
            root.addElement("little_endian").setText(String.valueOf(Settings.getSettingIns().isLittleendian()));

            root.addElement("check_only").setText(String.valueOf(isCheck));

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Template config write into config">
            root.addElement("hide_desc").setText(String.valueOf(Template.getTemplateIns().isHidedesc()));
            root.addElement("hide_prop").setText(String.valueOf(Template.getTemplateIns().isHideprop()));
            //</editor-fold>

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;

            FileOutputStream fl = new FileOutputStream(xmlFile);

            writer = new XMLWriter(fl, format);
            writer.write(document);
            writer.flush();
            System.out.println("--Config Saved in : " + xmlFile.getAbsolutePath());
            if (btnCancel != null) {
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e) {
            System.err.println("--Error saveConfigIntoXMLFile : " + e.getMessage());
        }
    }

    private void bindStrings() {
        hypLnkGeneral.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_GENERAL());
        hypLnkOutPut.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_OUTPUTS());
        hypLnkUserDefined.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_USER_DEFINED_OUTPUTS());
        hypLnkSettings.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_SETTINGSL());
        hypLnkVariants.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_VARIANT());
        hypLnkFormating.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_FORMATING());
        hypLnkDataSheet.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_DATASHEET());
        hypLnkCustomCSV.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CUSTOM_CSV());
        hypLnkAdvanceVerification.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_ADVANCE_VERIFICATION());
        hypLnkAdvanceDesign.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_ADVANCE_DESIGN());
        lblConfigMainHeader.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CONFIG_MAIN_TITLE());
        btnOk.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_OK());
        btnCancel.setText(IDSNextGen.APPLICATION_OBJECT.idsString.get_CANCEL());

    }

    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void click_hypLink1() {
        showGeneral();
    }

    private void showGeneral() {
        if (ConfigGeneral != null) {
            //paneMainContainer.getChildren().setAll(ConfigGeneral);
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigGeneral);
        }
    }

    public void click_hypLink2() {
        if (ConfigOutputs != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigOutputs);
        }
    }

    public void click_hypLnkUserDefine() {
        if (ConfigUserDefinedOutputs != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigUserDefinedOutputs);
        }
    }

    public void click_hypLnkSettings() {
        if (ConfigSettings != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigSettings);
        }
    }

    public void click_hypLnkVariants() {
        if (ConfigVariant != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigVariant);
        }
    }

    public void click_hypLnkFormating() {
        if (ConfigFormating != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigFormating);
        }
    }

    public void click_hypLnkDatasheet() {
        if (ConfigDatasheet != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigDatasheet);
        }
    }

    public void click_hypLnkCustomCSV() {
        if (ConfigCustomCSV != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigCustomCSV);
        }
    }

    public void click_hypLnkTemplate() {
        if (ConfigTemplate != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigTemplate);
        }
    }

    public void click_hypLnkAdvanceVerification() {
        if (ConfigAdvanceVerification != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigAdvanceVerification);
        }
    }

    public void click_hypLnkAdvanceDesign() {
        if (ConfigAdvanceDesign != null) {
            paneMainContainer.getChildren().clear();
            paneMainContainer.getChildren().add(ConfigAdvanceDesign);
        }
    }

}
