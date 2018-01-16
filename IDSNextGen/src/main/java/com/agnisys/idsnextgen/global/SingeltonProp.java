/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.global;

/**
 *
 * @author Agnisys
 */
public class SingeltonProp {

    private static SingeltonProp single;
    private String prevFolderLoc = null;
    private String prevFileLoc = null;

    private SingeltonProp() {
    }

    public static SingeltonProp getInstance() {
        if (single == null) {
            single = new SingeltonProp();
        }
        return single;
    }

    public String getPrevFolderLoc() {
        return prevFolderLoc;
    }

    public void setPrevFolderLoc(String prevFolderLoc) {
        this.prevFolderLoc = prevFolderLoc;
    }

    public String getPrevFileLoc() {
        return prevFileLoc;
    }

    public void setPrevFileLoc(String prevFileLoc) {
        this.prevFileLoc = prevFileLoc;
    }

}
