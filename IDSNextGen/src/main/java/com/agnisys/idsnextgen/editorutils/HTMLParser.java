/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.editorutils;

import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Sumeet
 */
public class HTMLParser {

    private final static String IDS_CSS_ID = "ids_style_template";
    private final static String IDS_JS_ID = "ids_js";
    private final static String JQUERY_TAG_ID = "jqueryLib";
    //private final static String JQUERY_LIB_PATH = "C:\\Users\\Agnisys\\Desktop\\GUI demo\\workspace\\myFirstProj\\sumeet\\resource\\jquery-3.2.1.min.js";

    public static String addIDSStyleAndCss(String filePath) {
        try {

            //File cssFile = IDSUtils.loadSystemResource("ids_templates/ids_template.css");
            //File jsFile = IDSUtils.loadSystemResource("ids_templates/ids_template.js");
            File file = new File(filePath);
            Document doc = removeJSAndCSS(file);
            if (doc != null) {
                Element header = doc.select("head").first();

                //add css
                String css = "<style id='" + IDS_CSS_ID + "'>" + readFile(ApplicationMainGUIController.APPLICATION_OBJECT.getIDS_CSS()) + "</style>";
                header.append(css);

                //add juery path
                String script = "<script id='" + JQUERY_TAG_ID + "'>" + readFile(ApplicationMainGUIController.APPLICATION_OBJECT.getJQUERY_LIB_PATH()) + "</script>";
                header.append(script);

                //add js
                String jsString = "<script id='" + IDS_JS_ID + "'>" + readFile(ApplicationMainGUIController.APPLICATION_OBJECT.getIDS_JS()) + "</script>";
                header.append(jsString);

                //FileUtils.writeStringToFile(file, doc.outerHtml(), "UTF-8");
                return doc.outerHtml();
            }
        } catch (Exception e) {
            System.err.println("Err (addIDSStyleAndCss) : " + e.getMessage());
        }
        return null;
    }

    public static void saveHtmlBody(String htmlContent, String filePath) {
        Document doc = null;
        try {
            File file = new File(filePath);
            doc = Jsoup.parse(file, "UTF-8");
            Elements elem;

            //remove body
            elem = doc.select("body#idsBody");

            if (elem != null) {
                elem.remove();
            }

            Document srcDoc = Jsoup.parse(htmlContent);
            Element eleBody = srcDoc.select("body#idsBody").get(0);

            doc.select("html").add(eleBody);

        } catch (Exception e) {
            System.err.println("Error (addIDSStyleSheet) : " + e.getMessage());
        }
    }

    public static String addIDSStyleAndCss(String filePath, File cssPath, File jsPath) {
        try {
            File file = new File(filePath);
            Document doc = removeJSAndCSS(file);
            if (doc != null) {
                Element header = doc.select("head").first();

                //add css
                String css = "<style id='" + IDS_CSS_ID + "'>" + readFile(cssPath) + "</style>";
                header.append(css);

                //add juery path
                String script = "<script id='" + JQUERY_TAG_ID + "'>" + readFile(ApplicationMainGUIController.APPLICATION_OBJECT.getJQUERY_LIB_PATH()) + "</script>";
                header.append(script);

                //add js
                String jsString = "<script id='" + IDS_JS_ID + "'>" + readFile(jsPath) + "</script>";
                header.append(jsString);

                //FileUtils.writeStringToFile(file, doc.outerHtml(), "UTF-8");
                return doc.outerHtml();
            }
        } catch (Exception e) {
            System.err.println("Err (addIDSStyleAndCss) : " + e.getMessage());
        }
        return null;
    }

    public static String addIDSStyleAndCss(String filePath, String cssPath, String jsPath) {
        try {
            File file = new File(filePath);
            Document doc = removeJSAndCSS(file);
            if (doc != null) {
                Element header = doc.select("head").first();

                //add css
                String css = "<style id='" + IDS_CSS_ID + "'>" + readFile(cssPath) + "</style>";
                header.append(css);

                //add juery path
                String script = "<script id='" + JQUERY_TAG_ID + "'>" + readFile(ApplicationMainGUIController.APPLICATION_OBJECT.getJQUERY_LIB_PATH()) + "</script>";
                header.append(script);

                //add js
                String jsString = "<script id='" + IDS_JS_ID + "'>" + readFile(jsPath) + "</script>";
                header.append(jsString);

                //FileUtils.writeStringToFile(file, doc.outerHtml(), "UTF-8");
                return doc.outerHtml();
            }
        } catch (Exception e) {
            System.err.println("Err (addIDSStyleAndCss) : " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] arg) {
        addIDSStyleAndCss("C:\\Users\\Agnisys\\Desktop\\GUI demo\\workspace\\myFirstProj\\sumeet\\Sample.html",
                "C:\\Users\\Agnisys\\Desktop\\GUI demo\\workspace\\myFirstProj\\sumeet\\resource\\ids_template.css",
                "C:\\Users\\Agnisys\\Desktop\\GUI demo\\workspace\\myFirstProj\\sumeet\\resource\\ids_template.js");
    }

    private static Document removeJSAndCSS(File file) {
        Document doc = null;
        try {
            doc = Jsoup.parse(file, "UTF-8");
            Elements elem;

            //remove css
            elem = doc.select("style#" + IDS_CSS_ID);

            if (elem != null) {
                elem.remove();
            }
            elem = doc.select("script#" + IDS_JS_ID);
            if (elem != null) {
                elem.remove();
            }

            elem = doc.select("script#" + JQUERY_TAG_ID);
            if (elem != null) {
                elem.remove();
            }

            return doc;

        } catch (Exception e) {
            System.err.println("Error (addIDSStyleSheet) : " + e.getMessage());
        }
        return doc;
    }

    private static String readFile(String path)
            throws IOException {
        return ApplicationMainGUIController.readFile(path, StandardCharsets.UTF_8).replace("/&amp;", "&");
    }

    private static String readFile(File file) {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException ex) {
            Logger.getLogger(HTMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
