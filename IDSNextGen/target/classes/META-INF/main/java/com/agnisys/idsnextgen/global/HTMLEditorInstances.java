/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.global;

import com.agnisys.idsnextgen.editorutils.HTMLEditorCustomizationSample;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sumeet
 */
public class HTMLEditorInstances {

    private static HTMLEditorInstances CustomHtmlEditor;
    List<HTMLEditorCustomizationSample> listHTMLEditorCustom = new ArrayList();

    private HTMLEditorInstances() {

    }

    public static HTMLEditorInstances getHTMLInstances() {
        if (CustomHtmlEditor == null) {
            CustomHtmlEditor = new HTMLEditorInstances();
        }
        return CustomHtmlEditor;
    }

    public List<HTMLEditorCustomizationSample> getHTMLEditorList() {
        return listHTMLEditorCustom;
    }

    public void addHTMLEditor(HTMLEditorCustomizationSample htmlEditor) {
        listHTMLEditorCustom.add(htmlEditor);
    }
}
