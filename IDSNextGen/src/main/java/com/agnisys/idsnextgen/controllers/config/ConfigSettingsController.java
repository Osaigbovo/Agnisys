/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config;

import com.agnisys.idsnextgen.controllers.config.properties.Settings;
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
public class ConfigSettingsController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="fxml initilization">
    @FXML
    RadioButton rbBus8;
    @FXML
    RadioButton rbBus16;
    @FXML
    RadioButton rbBus32;
    @FXML
    RadioButton rbBus64;
    @FXML
    RadioButton rbBus128;
    @FXML
    RadioButton rbBus256;

    @FXML
    TextField txtAddr256;
    @FXML
    TextField txtReg256;
    @FXML
    TextField txtBus256;

    @FXML
    RadioButton rbAddr8;
    @FXML
    RadioButton rbAddr16;
    @FXML
    RadioButton rbAddr32;
    @FXML
    RadioButton rbAddr64;
    @FXML
    RadioButton rbAddr128;
    @FXML
    RadioButton rbAddr256;

    @FXML
    RadioButton rbReg8;
    @FXML
    RadioButton rbReg16;
    @FXML
    RadioButton rbReg32;
    @FXML
    RadioButton rbReg64;
    @FXML
    RadioButton rbReg128;
    @FXML
    RadioButton rbReg256;

    @FXML
    RadioButton rbAHB;
    @FXML
    RadioButton rbAVALON;
    @FXML
    RadioButton rbProprietary;
    @FXML
    RadioButton rbAPB;
    @FXML
    RadioButton rbAHBLit;
    @FXML
    RadioButton rbOCP;
    @FXML
    RadioButton rbWishbone;
    @FXML
    RadioButton rbAXI;
    @FXML
    TextField txtBlockSize;
    @FXML
    TextField txtChipSize;
    @FXML
    TextField txtBoardSize;
    @FXML
    TextField txtCType;
    @FXML
    RadioButton rbAXI4Full;
    @FXML
    RadioButton rbSPI;
    @FXML
    RadioButton rbI2C;
    @FXML
    CheckBox chkLitlleEndian;
    @FXML
    CheckBox chkBigEndian;
    //</editor-fold>

    final String AHB = "amba";
    final String AVALON = "avalon";
    final String PROPRIETARY = "proprietary";
    final String APB = "apb";
    final String AHB_LITE = "amba3ahblite";
    final String OCP = "ocp";
    final String WISHBONE = "wishbone";
    final String AXI = "axi4lite";
    final String AXI4full = "axi4full";
    final String SPI = "spi";
    final String I2C = "i2c";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void loadPropIntoConfig() {
        String addrUnit = Settings.getSettingIns().getAddressunit();
        if (addrUnit != null) {
            switch (addrUnit) {
                case "8":
                    rbAddr8.setSelected(true);
                    break;
                case "16":
                    rbAddr16.setSelected(true);
                    break;
                case "32":
                    rbAddr32.setSelected(true);
                    break;
                case "64":
                    rbAddr64.setSelected(true);
                    break;
                case "128":
                    rbAddr128.setSelected(true);
                    break;
                default:
                    rbAddr256.setSelected(true);
                    txtAddr256.setText(addrUnit);
                    break;
            }
        }

        String regWidth = Settings.getSettingIns().getRegbuswidth();
        switch (regWidth) {
            case "8":
                rbReg8.setSelected(true);
                break;
            case "16":
                rbReg16.setSelected(true);
                break;
            case "32":
                rbReg32.setSelected(true);
                break;
            case "64":
                rbReg64.setSelected(true);
                break;
            case "128":
                rbReg128.setSelected(true);
                break;
            default:
                rbReg256.setSelected(true);
                txtReg256.setText(regWidth);
                break;
        }

        String busWidth = Settings.getSettingIns().getbuswidth();
        switch (busWidth) {
            case "8":
                rbBus8.setSelected(true);
                break;
            case "16":
                rbBus16.setSelected(true);
                break;
            case "32":
                rbBus32.setSelected(true);
                break;
            case "64":
                rbBus64.setSelected(true);
                break;
            case "128":
                rbBus128.setSelected(true);
                break;
            default:
                rbBus256.setSelected(true);
                txtBus256.setText(busWidth);
                break;
        }

        String bus = Settings.getSettingIns().getBus();
        switch (bus) {
            case AHB:
                rbAHB.setSelected(true);
                break;
            case AVALON:
                rbAVALON.setSelected(true);
                break;
            case PROPRIETARY:
                rbProprietary.setSelected(true);
                break;
            case APB:
                rbAPB.setSelected(true);
                break;
            case AHB_LITE:
                rbAHBLit.setSelected(true);
                break;
            case WISHBONE:
                rbWishbone.setSelected(true);
                break;
            case AXI:
                rbAXI.setSelected(true);
                break;
            case AXI4full:
                rbAXI4Full.setSelected(true);
                break;
            case SPI:
                rbSPI.setSelected(true);
                break;
            case I2C:
                rbI2C.setSelected(true);
                break;
        }

        txtBlockSize.setText(Settings.getSettingIns().getBlocksize());
        txtBoardSize.setText(Settings.getSettingIns().getBoardsize());
        txtCType.setText(Settings.getSettingIns().getCtype());
        txtChipSize.setText(Settings.getSettingIns().getChipsize());

        chkBigEndian.setSelected(Settings.getSettingIns().isBigendian());
        chkLitlleEndian.setSelected(Settings.getSettingIns().isLittleendian());
    }

    public void saveSettingIntoProp() {
        if (rbAddr8.isSelected()) {
            Settings.getSettingIns().setAddressunit("8");
        } else if (rbAddr16.isSelected()) {
            Settings.getSettingIns().setAddressunit("16");
        } else if (rbAddr32.isSelected()) {
            Settings.getSettingIns().setAddressunit("32");
        } else if (rbAddr64.isSelected()) {
            Settings.getSettingIns().setAddressunit("64");
        } else if (rbAddr128.isSelected()) {
            Settings.getSettingIns().setAddressunit("128");
        } else if (rbAddr256.isSelected()) {
            Settings.getSettingIns().setAddressunit(txtAddr256.getText());
        }

        if (rbReg8.isSelected()) {
            Settings.getSettingIns().setRegbuswidth("8");
        } else if (rbReg16.isSelected()) {
            Settings.getSettingIns().setRegbuswidth("16");
        } else if (rbReg32.isSelected()) {
            Settings.getSettingIns().setRegbuswidth("32");
        } else if (rbReg64.isSelected()) {
            Settings.getSettingIns().setRegbuswidth("64");
        } else if (rbReg128.isSelected()) {
            Settings.getSettingIns().setRegbuswidth("128");
        } else if (rbReg256.isSelected()) {
            Settings.getSettingIns().setRegbuswidth(txtReg256.getText());
        }

        if (rbBus8.isSelected()) {
            Settings.getSettingIns().setbuswidth("8");
        } else if (rbBus16.isSelected()) {
            Settings.getSettingIns().setbuswidth("16");
        } else if (rbBus32.isSelected()) {
            Settings.getSettingIns().setbuswidth("32");
        } else if (rbBus64.isSelected()) {
            Settings.getSettingIns().setbuswidth("64");
        } else if (rbBus128.isSelected()) {
            Settings.getSettingIns().setbuswidth("128");
        } else if (rbBus256.isSelected()) {
            Settings.getSettingIns().setbuswidth(txtBus256.getText());
        }

        if (rbAHB.isSelected()) {
            Settings.getSettingIns().setBus(AHB);
        } else if (rbAVALON.isSelected()) {
            Settings.getSettingIns().setBus(AVALON);
        } else if (rbProprietary.isSelected()) {
            Settings.getSettingIns().setBus(PROPRIETARY);
        } else if (rbAPB.isSelected()) {
            Settings.getSettingIns().setBus(APB);
        } else if (rbAHBLit.isSelected()) {
            Settings.getSettingIns().setBus(AHB_LITE);
        } else if (rbOCP.isSelected()) {
            Settings.getSettingIns().setBus(OCP);
        } else if (rbWishbone.isSelected()) {
            Settings.getSettingIns().setBus(WISHBONE);
        } else if (rbAXI.isSelected()) {
            Settings.getSettingIns().setBus(AXI);
        } else if (rbAXI4Full.isSelected()) {
            Settings.getSettingIns().setBus(AXI4full);
        } else if (rbSPI.isSelected()) {
            Settings.getSettingIns().setBus(SPI);
        } else if (rbI2C.isSelected()) {
            Settings.getSettingIns().setBus(I2C);
        }

        Settings.getSettingIns().setBlocksize(txtBlockSize.getText());
        Settings.getSettingIns().setBoardsize(txtBoardSize.getText());

        Settings.getSettingIns().setChipsize(txtChipSize.getText());
        Settings.getSettingIns().setCtype(txtCType.getText());

        Settings.getSettingIns().setBigendian(chkBigEndian.isSelected());
        Settings.getSettingIns().setLittleendian(chkLitlleEndian.isSelected());
    }

}
