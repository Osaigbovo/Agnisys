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
public class AdvanceVerification {

    boolean generateconstraints;
    boolean generatecoverage;
    boolean generateuvmhdl;
    boolean generateuvmcov;
    boolean generateIlligalBeans;

    private static AdvanceVerification advanceVerification;

    public static AdvanceVerification getAdvanceVerificationInst() {
        if (advanceVerification == null) {
            advanceVerification = new AdvanceVerification();
        }
        return advanceVerification;
    }

    public boolean isGenerateIlligalBeans() {
        return generateIlligalBeans;
    }

    public void setGenerateIlligalBeans(boolean val) {
        generateIlligalBeans = val;
    }

    public boolean isGenerateconstraints() {
        return generateconstraints;
    }

    public void setGenerateconstraints(boolean generateconstraints) {
        this.generateconstraints = generateconstraints;
    }

    public boolean isGeneratecoverage() {
        return generatecoverage;
    }

    public void setGeneratecoverage(boolean generatecoverage) {
        this.generatecoverage = generatecoverage;
    }

    public boolean isGenerateuvmhdl() {
        return generateuvmhdl;
    }

    public void setGenerateuvmhdl(boolean generateuvmhdl) {
        this.generateuvmhdl = generateuvmhdl;
    }

    public boolean isGenerateuvmcov() {
        return generateuvmcov;
    }

    public void setGenerateuvmcov(boolean generateuvmcov) {
        this.generateuvmcov = generateuvmcov;
    }

}
