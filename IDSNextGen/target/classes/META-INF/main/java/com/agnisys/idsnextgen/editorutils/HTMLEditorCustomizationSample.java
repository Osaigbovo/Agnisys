/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.editorutils;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.global.IDSUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSException;

public class HTMLEditorCustomizationSample extends Application {

    HTMLEditor htmlEditor = null;
    WebEngine engine = null;
    String jsCodeInsertHtml = "function insertHtmlAtCursor(html) {\n"
            + "    var range, node;\n"
            + "    if (window.getSelection && window.getSelection().getRangeAt) {\n"
            + "        range = window.getSelection().getRangeAt(0);\n"
            + "        node = range.createContextualFragment(html);\n"
            + "        range.insertNode(node);\n"
            + "    } else if (document.selection && document.selection.createRange) {\n"
            + "        document.selection.createRange().pasteHTML(html);\n"
            + "    }\n"
            + "}insertHtmlAtCursor('####html####')";

    public static void main(String[] args) {
        launch(args);
    }

    public HTMLEditor getHTMLEditor() {
        return htmlEditor;
    }

    //int to string
    private static String hex(int i) {
        return Integer.toHexString(i);
    }

    void keyPressEvent(KeyEvent event) {

        /*
         if (event.getCode() == KeyCode.CONTROL && event.isControlDown()) {
         if (event.getCode() == KeyCode.S) {
         System.out.println("--cotrol + s click");
         }
         }
         */
        if (ApplicationMainGUIController.APPLICATION_OBJECT.kb.match(event)) {
            //IDSUtils.saveCurrentEditedFile(CURRENT_PROJECT_ROOT, CURRENT_PROJECT_ROOT);
            //System.out.println("save match from htmlE editor");
            IDSUtils.saveCurrentEditedFile();
        }
    }

//    private String appendStyleSheet(File inputFile) {
//        SAXReader saxReader = new SAXReader();
//        try {
//
//            DOMParser parser = new DOMParser();
//
//            parser.parse(new InputSource(inputFile.getAbsolutePath()));
//            org.w3c.dom.Document doc = parser.getDocument();
//
//            DOMReader reader = new DOMReader();
//            Document document = reader.read(doc);
//
//            //Document document = saxReader.read(new InputSource(new StringReader(htmlStr)));
//            Element root = document.getRootElement();
//            Element header = (Element) root.selectSingleNode("header");
//            if (header != null) {
//                Element style = (Element) header.selectSingleNode("style");
//
//                if (style != null) {
//                    Attribute attrId = style.attribute("id");
//                    if (attrId != null) {
//                        if (attrId.getValue().equals("ids_style_template")) {
//                            style.detach();
//                        }
//                    }
//                }
//
//                style = header.addElement("style");
//                style.addAttribute("id", "ids_style_template");
//                File f = IDSUtils.loadSystemResource("ids_templates/ids_template.css");
//                String str = IDSUtils.getStringFromFile(f);
//                //System.out.println("-str=" + str);
//                style.setText(str);
//
//                return document.asXML();
//            }
//        } catch (Exception e) {
//            System.err.println("Error appendStyleSheet : " + e.getMessage());
//        }
//        return "";
//    }
    public String getHTMLText() {
        return htmlEditor.getHtmlText();
    }

    public HTMLEditorCustomizationSample(String fileStr) {
        htmlEditor = new HTMLEditor();
        htmlEditor.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                keyPressEvent(event);
            }
        });

//        String finalString = appendStyleSheet(new File(fileStr));
        //String finalString = HTMLParser.addIDSStyleAndCss(fileStr, ApplicationMainGUIController.APPLICATION_OBJECT.getIDS_CSS(), ApplicationMainGUIController.APPLICATION_OBJECT.getIDS_JS());
        String finalString = fileStr;
        htmlEditor.setHtmlText(finalString);

// add a custom button to the top toolbar.
        //Node toolNode = htmlEditor.lookup(".top-toolbar");
        Node webNode = htmlEditor.lookup(".web-view");
        if (webNode instanceof WebView) {
            //ToolBar bar = (ToolBar) toolNode;
            WebView webView = (WebView) webNode;
            engine = webView.getEngine();

            engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
                @Override
                public void changed(ObservableValue<? extends Throwable> ov, Throwable t, Throwable t1) {
                    System.out.println("Received exception: " + t1.getMessage());
                }
            });

            webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> event) {
                    System.err.println("Message from html " + event.getData());
                    Stage popup = new Stage();
                    popup.initOwner(IDSNextGen.STAGE);
                    popup.initStyle(StageStyle.UTILITY);
                    popup.initModality(Modality.WINDOW_MODAL);

                    StackPane content = new StackPane();
                    content.getChildren().setAll(
                            new Label(event.getData())
                    );
                    content.setPrefSize(200, 100);

                    popup.setScene(new Scene(content));
                    popup.showAndWait();
                }
            });

//            Button btnCaretAddImage = new Button("add an image");
//            btnCaretAddImage.setMinSize(100.0, 24.0);
//            btnCaretAddImage.setMaxSize(100.0, 24.0);
            //bar.getItems().addAll(btnCaretAddImage);
            //htmlEditor.setHtmlText(fileStr);
            htmlEditor.setHtmlText(finalString);
            //data uri image
//            String img
//                    = "<img alt=\"Embedded Image\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAEeSURBVDhPbZFba8JAEIXz//9BoeCD4GNQAqVQBUVKm97UeMFaUmvF1mqUEJW0NXV6mtlL3PRwILuz52NnJxax5iGdt8l2TaP4shCZVBI4ezCjypVbkUklASN07NftlzMNh2GMYA6Iv0UFksWiH5w8Lk5HS9QkgHv5GAtuGq+SAKIAYJQl4E3UMTyYBLFzp7at1Q7p5scGQQlAszVdeJwYV7sqTa2xCKTKAET+dK1zysfSAEZReFqaadv1GsNi/53fAFv8KT2vkMYiKd8YADty7ptXvgaU3cuREc26Xx+YQPUtQnud3kznapkB2K4JJIcDgOBzr3uDkh+6Fjdb9XmUBf6OU3Fv6EHsIcy91hVTygP5CksA6uejGa78DxD9ApzMoGHun6uuAAAAAElFTkSuQmCC\" />";
//            //http://stackoverflow.com/questions/2213376/how-to-find-cursor-position-in-a-contenteditable-div

//            btnCaretAddImage.setOnAction((ActionEvent event) -> {
//                try {
//                    engine.executeScript(jsCodeInsertHtml.replace("####html####", escapeJavaStyleString(img, true, true)));
//
//                } catch (JSException e) {
//                    // A JavaScript Exception Occured
//                }
//            });
        }
    }

    public void executeScript(String script) {
        engine.executeScript(script);
    }

    public void appendText(String str) {
        try {
            engine.executeScript(jsCodeInsertHtml.replace("####html####", escapeJavaStyleString(str, true, true)));
            // htmlEditor.setHtmlText("<font face='Comic Sans MS' color='blue'>Smurfs are having fun :-)</font>");
        } catch (JSException e) {
            // A JavaScript Exception Occured
        }
    }

    //a method to convert to a javas/js style string
    //https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/src-html/org/apache/commons/lang/StringEscapeUtils.html
    private static String escapeJavaStyleString(String str,
            boolean escapeSingleQuote, boolean escapeForwardSlash) {
        StringBuilder out = new StringBuilder("");
        if (str == null) {
            return null;
        }
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                out.append("\\u").append(hex(ch));
            } else if (ch > 0xff) {
                out.append("\\u0").append(hex(ch));
            } else if (ch > 0x7f) {
                out.append("\\u00").append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;
                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;
                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;
                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;
                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            out.append("\\u00").append(hex(ch));
                        } else {
                            out.append("\\u000").append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        if (escapeSingleQuote) {
                            out.append('\\');
                        }
                        out.append('\'');
                        break;
                    case '"':
                        out.append('\\');
                        out.append('"');
                        break;
                    case '\\':
                        out.append('\\');
                        out.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            out.append('\\');
                        }
                        out.append('/');
                        break;
                    default:
                        out.append(ch);
                        break;
                }
            }
        }
        return out.toString();
    }

    @Override
    public void start(Stage stage) {
//// create a new html editor and show it before we start modifying it.
//        htmlEditor = new HTMLEditor();
//        stage.setScene(new Scene(htmlEditor));
//        stage.show();
//
//// add a custom button to the top toolbar.
//        Node toolNode = htmlEditor.lookup(".top-toolbar");
//        Node webNode = htmlEditor.lookup(".web-view");
//        if (toolNode instanceof ToolBar && webNode instanceof WebView) {
//            ToolBar bar = (ToolBar) toolNode;
//            WebView webView = (WebView) webNode;
//            WebEngine engine = webView.getEngine();
//
//            Button btnCaretAddImage = new Button("add an image");
//            btnCaretAddImage.setMinSize(100.0, 24.0);
//            btnCaretAddImage.setMaxSize(100.0, 24.0);
//
//            bar.getItems().addAll(btnCaretAddImage);
//            htmlEditor.setHtmlText("Hello World");
//            //data uri image
//            String img
//                    = "<img alt=\"Embedded Image\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAEeSURBVDhPbZFba8JAEIXz//9BoeCD4GNQAqVQBUVKm97UeMFaUmvF1mqUEJW0NXV6mtlL3PRwILuz52NnJxax5iGdt8l2TaP4shCZVBI4ezCjypVbkUklASN07NftlzMNh2GMYA6Iv0UFksWiH5w8Lk5HS9QkgHv5GAtuGq+SAKIAYJQl4E3UMTyYBLFzp7at1Q7p5scGQQlAszVdeJwYV7sqTa2xCKTKAET+dK1zysfSAEZReFqaadv1GsNi/53fAFv8KT2vkMYiKd8YADty7ptXvgaU3cuREc26Xx+YQPUtQnud3kznapkB2K4JJIcDgOBzr3uDkh+6Fjdb9XmUBf6OU3Fv6EHsIcy91hVTygP5CksA6uejGa78DxD9ApzMoGHun6uuAAAAAElFTkSuQmCC\" />";
//            //http://stackoverflow.com/questions/2213376/how-to-find-cursor-position-in-a-contenteditable-div
//            String jsCodeInsertHtml = "function insertHtmlAtCursor(html) {\n"
//                    + "    var range, node;\n"
//                    + "    if (window.getSelection && window.getSelection().getRangeAt) {\n"
//                    + "        range = window.getSelection().getRangeAt(0);\n"
//                    + "        node = range.createContextualFragment(html);\n"
//                    + "        range.insertNode(node);\n"
//                    + "    } else if (document.selection && document.selection.createRange) {\n"
//                    + "        document.selection.createRange().pasteHTML(html);\n"
//                    + "    }\n"
//                    + "}insertHtmlAtCursor('####html####')";
//            btnCaretAddImage.setOnAction((ActionEvent event) -> {
//                try {
//                    engine.executeScript(jsCodeInsertHtml.replace("####html####", escapeJavaStyleString(img, true, true)));
//                } catch (JSException e) {
//                    // A JavaScript Exception Occured
//                }
//            });
//        }
//        System.out.println("start called");
    }
}
