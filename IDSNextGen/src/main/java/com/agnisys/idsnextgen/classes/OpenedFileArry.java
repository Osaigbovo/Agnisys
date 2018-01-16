/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.classes;

import java.util.ArrayList;

/**
 *
 * @author Agnisys
 */
class OpenedFileArry extends ArrayList<String> {

    @Override
    public boolean add(String str) {
        for (String s : this) {
            if (s.equals(str)) {
                return false;
            }
        }
        super.add(str);
        return true;
    }
}
