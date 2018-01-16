/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.global;

import java.util.List;

/**
 *
 * @author Agnisys
 */
public class ApplicationProperties {

    private double CONSOLE_DIVIDER_POS = 1.0;
    private double MAIN_EDITOR00 = 0.2;
    private double MAIN_EDITOR01 = 1.0;
    private double EDITOR_LEFT_UP_DOWN_POS = 0.5;
    private String DEFAULT_PATH = "";
    private List<String> OPENED_FILE_PATHS = null;

    public List<String> getOPENED_FILE_PATHS() {
        return OPENED_FILE_PATHS;
    }

    public void setOPENED_FILE_PATHS(List<String> OPENED_FILE_PATH) {
        this.OPENED_FILE_PATHS = OPENED_FILE_PATH;
    }

    public double getCONSOLE_DIVIDER_POS() {
        return CONSOLE_DIVIDER_POS;
    }

    public void setCONSOLE_DIVIDER_POS(double CONSOLE_DIVIDER_POS) {
        this.CONSOLE_DIVIDER_POS = CONSOLE_DIVIDER_POS;
    }

    public double getMAIN_EDITOR01() {
        return MAIN_EDITOR01;
    }

    public void setMAIN_EDITOR01(double EDITOR_LEFT_PANE_POS) {
        this.MAIN_EDITOR01 = EDITOR_LEFT_PANE_POS;
    }

    public double getEDITOR_LEFT_UP_DOWN_POS() {
        return EDITOR_LEFT_UP_DOWN_POS;
    }

    public void setEDITOR_LEFT_UP_DOWN_POS(double EDITOR_LEFT_UP_DOWN_POS) {
        this.EDITOR_LEFT_UP_DOWN_POS = EDITOR_LEFT_UP_DOWN_POS;
    }

    public double getMAIN_EDITOR00() {
        return MAIN_EDITOR00;
    }

    public void setMAIN_EDITOR00(double EDITOR_RIGHT_PROJ_EXP) {
        this.MAIN_EDITOR00 = EDITOR_RIGHT_PROJ_EXP;
    }

    public String getDEFAULT_PATH() {
        return DEFAULT_PATH;
    }

    public void setDEFAULT_PATH(String DEFAULT_PATH) {
        this.DEFAULT_PATH = DEFAULT_PATH;
    }

}
