/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author avdhesh
 */
public class IDSNGLocale {

    private ResourceBundle bundle;

    public IDSNGLocale() {
        bundle = ResourceBundle.getBundle("properties/MessageBundle", Locale.US);
    }

    public IDSNGLocale(Locale l) {
        Locale.setDefault(l);
        bundle = ResourceBundle.getBundle("MessageBundle");

    }

    public ResourceBundle getBundle() {
        return bundle;
    }

}
