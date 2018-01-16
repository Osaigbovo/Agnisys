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
public class General {

    private static General general;

    private General() {
    }

    public static General getGeneralIns() {
        if (general == null) {
            general = new General();
        }
        return general;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public boolean isSavedoc() {
        return savedoc;
    }

    public void setSavedoc(boolean savedoc) {
        this.savedoc = savedoc;
    }

    public boolean isLog() {
        return log;
    }

    public void setLog(boolean log) {
        this.log = log;
    }

    private String company = "";
    private String copyright = "";
    private boolean savedoc = false;
    private boolean log = false;
}
