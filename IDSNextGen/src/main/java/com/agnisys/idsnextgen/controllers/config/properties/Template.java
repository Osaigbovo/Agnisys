/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config.properties;

/**
 *
 * @author Agnisys
 */
public class Template {

    boolean hideprop;
    boolean hidedesc;

    private static Template template;

    private Template() {
    }

    public static Template getTemplateIns() {
        if (template == null) {
            template = new Template();
        }
        return template;
    }

    public boolean isHideprop() {
        return hideprop;
    }

    public void setHideprop(boolean hideprop) {
        this.hideprop = hideprop;
    }

    public boolean isHidedesc() {
        return hidedesc;
    }

    public void setHidedesc(boolean hidedesc) {
        this.hidedesc = hidedesc;
    }

}
