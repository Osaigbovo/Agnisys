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
public class AdvanceDesign {

    boolean memorytech;
    boolean optimize;

    private static AdvanceDesign advanceDesign;

    public static AdvanceDesign getAdvanceDesignIns() {
        if (advanceDesign == null) {
            advanceDesign = new AdvanceDesign();
        }
        return advanceDesign;
    }

    public boolean isMemorytech() {
        return memorytech;
    }

    public void setMemorytech(boolean memorytech) {
        this.memorytech = memorytech;
    }

    public boolean isOptimize() {
        return optimize;
    }

    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }

}
