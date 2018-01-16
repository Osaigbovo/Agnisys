/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers;

import com.agnisys.idsnextgen.IDSNextGen;
import com.agnisys.idsnextgen.global.IDSUtils;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.Double.min;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Platform.runLater;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 * FXML Controller class
 *
 * @author Sumeet
 */
public class EditorToolbarController implements Initializable {

    @FXML
    TextField txtSearch;
    @FXML
    ComboBox ddFontSize;
    @FXML
    ComboBox ddFontName;
    @FXML
    ColorPicker colorPickerFont;

    @FXML
    ComboBox ddFormating;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initilization();
    }

    private void initilization() {
        ddFontSize.getItems().addAll(
                "1",
                "2",
                "3",
                "4", "5", "6", "7"
        );
        ddFontSize.getSelectionModel().select(3);
        ddFontSize.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                /*System.out.println(ov);
                 System.out.println(oldValue);
                 System.out.println(newValue);*/
                setFontSize(newValue);
            }
        });

        ddFontName.getItems().addAll("Arial", "Calibri", "Comic Sans MS");
        ddFontName.getSelectionModel().selectFirst();
        ddFontName.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                /*System.out.println(ov);
                 System.out.println(oldValue);
                 System.out.println(newValue);*/
                setFontName(newValue);
            }
        });

        ddFormating.getItems().addAll("Heading 1", "Heading 2", "Heading 3", "Heading 4", "Paragraph");
        ddFormating.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                /*System.out.println(ov);
                 System.out.println(oldValue);
                 System.out.println(newValue);*/
                setFormats(newValue);
            }
        });
    }

    @FXML
    private void click_colorPicker() {
        System.out.println("-selected color=" + colorPickerFont.getValue());
        Color c = colorPickerFont.getValue();
        System.out.println("--color=" + toRGBCode(c));
        String color = toRGBCode(colorPickerFont.getValue());
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("fontColorChange('" + color + "')");
    }

    public String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @FXML
    private void click_printBtn() {
        // ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("printDoc()");
        //printCurrentWebpage();
        // print("mytest", false);
        printMe2();
    }

    private void printMe2() {
        System.out.println("print me2 call");
        String filename = "C:\\Users\\Agnisys\\Documents\\newProj2\\IP2\\IP2.html";

        File f = new File(filename);

        if (f.exists() && !f.isDirectory()) {

            System.out.println("Valid File = " + filename);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.PRINT)) {
                    try {
                        desktop.print(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("--print done");
            }
        } else {
            System.out.println("File does exist = " + filename);
        }
    }

    private void printME() {
        System.out.println("print me call");
        String filename = "C:\\Users\\Agnisys\\Documents\\newProj2\\IP2\\IP2.html";
        try {
            System.out.println(filename);
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            DocFlavor flavor = DocFlavor.INPUT_STREAM.TEXT_HTML_HOST;
            PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = ServiceUI.printDialog(null, 200, 200, printService, defaultService, flavor, pras);
            if (service != null) {
                DocPrintJob job = service.createPrintJob();
                FileInputStream fis = new FileInputStream(filename);
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, das);
                job.print(doc, pras);
                //Thread.sleep(10000);
                System.out.println("--print donie");
            }
        } catch (Exception e) {
            System.err.println("Error in printMe : " + e.getMessage());
        }
    }

    public void printCurrentWebpage() {
        WebView webview;// = IDSUtils.getCurrentWebview();
        String filePath = IDSUtils.getActiveFilePath();
        webview = new WebView();
        WebEngine webengine = webview.getEngine();
        webengine.setJavaScriptEnabled(true);
        String modifiedPath = "file:///" + filePath;
//        System.out.println("-you are opening : " + modifiedPath);
        webengine.load(modifiedPath);

        Tab tab = new Tab();
        tab.setContent(webview);
        //ApplicationMainGUIController.APPLICATION_OBJECT.tabPnCentralEditor.getTabs().add(tab);
        print(webview);
    }

    public void print(final String title, boolean withDirections) {
        if (withDirections) {
            throw new UnsupportedOperationException("Printing with directions not supported");
        }

        runLater(new Runnable() {
            public void run() {
                PrinterJob job = PrinterJob.createPrinterJob();
                if (job != null && job.showPrintDialog(null)) {
                    WebView webView = IDSUtils.getCurrentWebview();
                    PageLayout pageLayout = job.getPrinter().getDefaultPageLayout();
                    double scaleX = pageLayout.getPrintableWidth() / webView.getBoundsInParent().getWidth();
                    double scaleY = pageLayout.getPrintableHeight() / webView.getBoundsInParent().getHeight();
                    double minimumScale = min(scaleX, scaleY);
                    Scale scale = new Scale(minimumScale, minimumScale);

                    try {
                        webView.getTransforms().add(scale);

                        boolean success = job.printPage(webView);
                        if (success) {
                            job.endJob();
                        }

                    } finally {
                        webView.getTransforms().remove(scale);

                        Group group = new Group();
                        group.getChildren().add(webView);
                        //getPanel().setScene(new Scene(group));
                    }
                }
            }
        });
    }

    /**
     * Scales the node based on the standard letter, portrait paper to be
     * printed.
     *
     * @param node The scene node to be printed.
     */
    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            System.out.println("print job created");
            // Show the print setup dialog
            boolean proceed = job.showPrintDialog(IDSNextGen.STAGE);
            if (proceed) {

                boolean success = job.printPage(node);
                if (success) {
                    System.out.println("sucess");
                }
            }

            /*
             //            boolean success = job.printPage(node);
             if (success) {
             System.out.println("print job success");
             job.endJob();
             System.out.println("print job end");
             }
             */
        }
    }

    @FXML
    private void click_deleteIDSTab() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("deleteElement()");
    }

    @FXML
    private void click_copyIDSTab() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("copyIDSTab()");
    }

    @FXML
    private void click_cutIDSTab() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("cutIDSTab()");
    }

    @FXML
    private void click_pasteIDSTab() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("pasteIDSTab()");
    }

    @FXML
    private void click_imageChooser() {
        File f = ApplicationMainGUIController.APPLICATION_OBJECT.imageChooser();
        if (f != null) {
            setImage(f.toURI());
        }
    }

    private void setImage(URI imgPath) {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("insertHTML('" + imgPath + "')");
    }

    private void setFontName(String fontName) {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setFontName('" + fontName + "')");
    }

    private void setFormats(String formatName) {
        String temp = "";
        switch (formatName) {
            case "Heading 1":
                temp = "<h1>";
                break;
            case "Heading 2":
                temp = "<h2>";
                break;
            case "Heading 3":
                temp = "<h3>";
                break;
            case "Heading 4":
                temp = "<h4>";
                break;
            case "Paragraph":
                temp = "<p>";
                break;
        }
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setFormatsName('" + temp + "')");
    }

    private void setFontSize(String fontSize) {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setFontSize('" + fontSize + "')");
    }

    @FXML
    private void click_increaseSize() {
        //System.out.println("increase");
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setIncreaseSize()");
    }

    @FXML
    private void click_decreaseSize() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setDecreaseSize()");
    }

    @FXML
    private void click_searchText() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("findString('" + txtSearch.getText() + "')");
    }

    @FXML
    private void click_setStrikeThrough() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setStrikeThrough()");
    }

    @FXML
    private void click_setSubscript() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setSubscript()");
    }

    @FXML
    private void click_setSuperscript() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setSuperscript()");
    }

    @FXML
    private void click_setOrderedList() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setOrderedList()");
    }

    @FXML
    private void click_setUnOrderedList() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setUnOrderedList()");
    }

    @FXML
    private void click_setAlignLeft() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setAlignLeft()");
    }

    @FXML
    private void click_setAlignCenter() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setAlignCenter()");
    }

    @FXML
    private void click_setAlignRight() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setAlignRight()");
    }

    @FXML
    private void click_setAlignFull() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setAlignFull()");
    }

    @FXML
    private void click_bold() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setBold()");
    }

    @FXML
    private void click_Italic() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setItalic()");
    }

    @FXML
    private void click_underline() {
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("setUnderline()");
    }
}
