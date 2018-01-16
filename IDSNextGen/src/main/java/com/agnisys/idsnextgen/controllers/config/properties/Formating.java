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
public class Formating {

    boolean index;
    String startingindex;
    boolean heading;
    String startingheading;
    boolean toc;
    boolean limittochir;
    boolean updatepage;
    boolean showemptyspace;
    boolean showvariant;
    boolean indirectregarr;
    boolean preservename;
    boolean addresssorting;

    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public String getStartingindex() {
        return startingindex;
    }

    public void setStartingindex(String startingindex) {
        this.startingindex = startingindex;
    }

    public boolean isHeading() {
        return heading;
    }

    public void setHeading(boolean heading) {
        this.heading = heading;
    }

    public String getStartingheading() {
        return startingheading;
    }

    public void setStartingheading(String startingheading) {
        this.startingheading = startingheading;
    }

    public boolean isToc() {
        return toc;
    }

    public void setToc(boolean toc) {
        this.toc = toc;
    }

    public boolean isLimittochir() {
        return limittochir;
    }

    public void setLimittochir(boolean limittochir) {
        this.limittochir = limittochir;
    }

    public boolean isUpdatepage() {
        return updatepage;
    }

    public void setUpdatepage(boolean updatepage) {
        this.updatepage = updatepage;
    }

    public boolean isShowemptyspace() {
        return showemptyspace;
    }

    public void setShowemptyspace(boolean showemptyspace) {
        this.showemptyspace = showemptyspace;
    }

    public boolean isShowvariant() {
        return showvariant;
    }

    public void setShowvariant(boolean showvariant) {
        this.showvariant = showvariant;
    }

    public boolean isIndirectregarr() {
        return indirectregarr;
    }

    public void setIndirectregarr(boolean indirectregarr) {
        this.indirectregarr = indirectregarr;
    }

    public boolean isPreservename() {
        return preservename;
    }

    public void setPreservename(boolean preservename) {
        this.preservename = preservename;
    }

    public boolean isAddresssorting() {
        return addresssorting;
    }

    public void setAddresssorting(boolean addresssorting) {
        this.addresssorting = addresssorting;
    }

}
