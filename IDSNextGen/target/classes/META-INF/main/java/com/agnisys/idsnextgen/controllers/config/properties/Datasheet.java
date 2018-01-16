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
public class Datasheet {

    boolean removedefinevarianttable;
    boolean properties;
    boolean expandreggrp;
    boolean flattenref;
    boolean replacetemp;
    String datasheetoutfilename;

    public boolean isRemovedefinevarianttable() {
        return removedefinevarianttable;
    }

    public void setRemovedefinevarianttable(boolean removedefinevarianttable) {
        this.removedefinevarianttable = removedefinevarianttable;
    }

    public boolean isProperties() {
        return properties;
    }

    public void setProperties(boolean properties) {
        this.properties = properties;
    }

    public boolean isExpandreggrp() {
        return expandreggrp;
    }

    public void setExpandreggrp(boolean expandreggrp) {
        this.expandreggrp = expandreggrp;
    }

    public boolean isFlattenref() {
        return flattenref;
    }

    public void setFlattenref(boolean flattenref) {
        this.flattenref = flattenref;
    }

    public boolean isReplacetemp() {
        return replacetemp;
    }

    public void setReplacetemp(boolean replacetemp) {
        this.replacetemp = replacetemp;
    }

    public String getDatasheetoutfilename() {
        return datasheetoutfilename;
    }

    public void setDatasheetoutfilename(String datasheetoutfilename) {
        this.datasheetoutfilename = datasheetoutfilename;
    }

}
