/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config.properties;

/**
 *
 * @author Sumeet
 */
public class Settings {

    String addressunit = "";
    String regbuswidth = "";
    String buswidth = "";
    String bus = "";
    String blocksize = "";
    String chipsize = "";
    String boardsize = "";
    String ctype = "";
    boolean bigendian = false;
    boolean littleendian = false;

    private static Settings settings;

    private Settings() {

    }

    public static Settings getSettingIns() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public String getAddressunit() {
        return addressunit;
    }

    public void setAddressunit(String addressunit) {
        this.addressunit = addressunit;
    }

    public String getRegbuswidth() {
        return regbuswidth;
    }

    public void setRegbuswidth(String regbuswidth) {
        this.regbuswidth = regbuswidth;
    }

    public String getbuswidth() {
        return buswidth;
    }

    public void setbuswidth(String buswidth) {
        this.buswidth = buswidth;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getBlocksize() {
        return blocksize;
    }

    public void setBlocksize(String blocksize) {
        this.blocksize = blocksize;
    }

    public String getChipsize() {
        return chipsize;
    }

    public void setChipsize(String chipsize) {
        this.chipsize = chipsize;
    }

    public String getBoardsize() {
        return boardsize;
    }

    public void setBoardsize(String boardsize) {
        this.boardsize = boardsize;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public boolean isBigendian() {
        return bigendian;
    }

    public void setBigendian(boolean bigendian) {
        this.bigendian = bigendian;
    }

    public boolean isLittleendian() {
        return littleendian;
    }

    public void setLittleendian(boolean littleendian) {
        this.littleendian = littleendian;
    }

}
