/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.param;

import static com.agnisys.idsnextgen.backannotation.BackAnnotation.save;
import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.idsnextgen.global.IDSUtils;
import com.agnisys.matlabparameter.LocationOptimizer;
import com.agnisys.matlabparameter.model.Parameters;
import com.agnisys.matlabparameter.model.Registers;
import com.agnisys.matlabparameter.yamlparser.YamlParser;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.agnisys.idsbatch.Transformer;

/**
 *
 * @author Sumeet
 */
public class ParamWriter {

    HashMap<Parameters, Parameters> paramDiff = null;
    Document htmldoc = null;

    public void updateParam() {
        ApplicationMainGUIController.APPLICATION_OBJECT.vboxhelp.getChildren().clear();
        paramDiff = getparadiff();
        Set entryset = paramDiff.entrySet();
        Iterator it;

        String htmlContents = Transformer.readFileAsString(IDSUtils.getActiveOrgFilePath());
        htmldoc = Jsoup.parse(htmlContents);

        List pList = htmldoc.getElementsByClass("dragtarget");

        for (int i = 0; i < pList.size(); i++) {
            Element ele = (Element) pList.get(i);
            it = entryset.iterator();
            while (it.hasNext()) {
                Map.Entry me = (Map.Entry) it.next();
                Parameters p = (Parameters) me.getKey();
                if (p.getField().trim().equals(ele.attr("title").trim())) {

                    System.out.println("--matching attr=" + ele.attr("title"));
                    ele.parent().attr("style", "background-color:white");
                    int colspan = Integer.parseInt(ele.parent().attr("colspan"));
                    for (int j = 1; j < colspan; j++) {
                        ele.parent().before("<td class=\"droptarget\"></td>");
                    }
                    ele.parent().attr("colspan", "1");
                    ele.remove();
                }
            }
            //System.out.println("--atrrs=" + attr);
        }
        //System.out.println("--" + htmldoc.toString());

        it = entryset.iterator();
        String str = "";
        int counter = 0;
        List<CheckBox> chklist = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            Parameters p = (Parameters) me.getKey();
            /*
             str += "<tr><td><input type=\"checkbox\"></td><td>" + p.getField() + "</td></tr>";*/
            CheckBox chk = new CheckBox(p.getField());
            chk.setId(p.getField());
            chk.setOnAction((e) -> {
                clickOnDiff(e);
            });
            chk.setTooltip(new Tooltip("name : " + p.getField() + "\noffset : " + p.getOffset() + "\ntype : " + p.getType() + "\ndesc : " + p.getDesc()));
            ApplicationMainGUIController.APPLICATION_OBJECT.vboxhelp.getChildren().add(chk);
        }
        //ApplicationMainGUIController.APPLICATION_OBJECT.pnhelp.getChildren().addAll(chklist);
        //doc.getElementById("updated").html(str);
        /*System.out.println("--" + htmldoc.getElementById("paramcontainer").html());
         ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("writeparam('" + htmldoc.getElementById("paramcontainer").html() + "')");
         System.out.println("--its new");*/
        save(new File(IDSUtils.getActiveOrgFilePath()), htmldoc.toString());
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("reloadPage()");
    }

    private void clickOnDiff(Event e) {
        CheckBox chk = (CheckBox) e.getSource();
        if (chk.isSelected()) {
            System.out.println("--selected");

            String htmlContents = Transformer.readFileAsString(IDSUtils.getActiveOrgFilePath());
            Document htmldoc2 = Jsoup.parse(htmlContents);

            if (htmldoc2 != null && paramDiff != null) {
                Set entryset = paramDiff.entrySet();
                Iterator it;
                it = entryset.iterator();
                Parameters currparam = null;
                while (it.hasNext()) {
                    Map.Entry me = (Map.Entry) it.next();
                    Parameters p = (Parameters) me.getKey();
                    if (p.getField().equals(chk.getId())) {
                        currparam = p;
                        break;
                    }
                }

                if (currparam != null) {
                    boolean needinsertreg = true;

                    List tabs = htmldoc2.getElementsByAttributeValue("data-reg-type", currparam.getType());
                    int size = Integer.parseInt(currparam.getOffset());
                    for (int i = 0; i < tabs.size(); i++) {
                        Element ele = (Element) tabs.get(i);
                        List fieldcells = ele.getElementsByClass("droptarget");
                        boolean isFound = true;

                        if (size > 1) {
                            boolean isoccupy = false;
                            ArrayList deletedcell = new ArrayList<>();
                            int regcounter = 1;
                            for (int j = 0; j < fieldcells.size(); j++) {
                                Element field = (Element) fieldcells.get(j);
                                if (!field.hasText()) {
                                    //cell is blank
                                    isoccupy = true;
                                    if (isoccupy) {
                                        if (regcounter < size) {
                                            regcounter++;
                                            deletedcell.add(j);
                                        } else if (regcounter == size) {
                                            deletedcell.add(j);
                                            //insert field here
                                            Element insertField = ((Element) fieldcells.get((int) deletedcell.get(0)));
                                            insertField.html("<p draggable=\"true\" class=\"dragtarget\" id=\"dragtarget" + count + "\" title=\"" + currparam.getField() + "\">" + currparam.getOffset() + "</p>");
                                            insertField.attr("style", "background-color: #d4e0e2;");
                                            insertField.attr("colspan", String.valueOf(size));

                                            for (int k = 1; k < deletedcell.size(); k++) {
                                                ((Element) fieldcells.get((int) deletedcell.get(k))).remove();
                                            }

                                            isFound = false;
                                            needinsertreg = false;
                                            save(new File(IDSUtils.getActiveOrgFilePath()), htmldoc2.toString());
                                            ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("reloadPage()");
                                            break;
                                        } else {
                                            isoccupy = false;
                                        }
                                    }

                                } else {
                                    //cell is not blank
                                    isoccupy = false;
                                    regcounter = 1;
                                    deletedcell = new ArrayList<>();
                                }
                            }

                        } else {
                            for (int j = 0; j < fieldcells.size(); j++) {
                                Element field = (Element) fieldcells.get(j);
                                if (!field.hasText()) {

                                    field.html("<p draggable=\"true\" class=\"dragtarget\" id=\"dragtarget" + count + "\" title=\"" + currparam.getField() + "\">" + currparam.getOffset() + "</p>");
                                    field.attr("style", "background-color: #d4e0e2;");
                                    isFound = false;
                                    needinsertreg = false;
                                    save(new File(IDSUtils.getActiveOrgFilePath()), htmldoc2.toString());
                                    ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("reloadPage()");
                                    break;

                                }
                            }
                        }

                        if (!isFound) {
                            break;
                        }
                    }

                    //insert new reg here
                    if (needinsertreg) {
                        boolean isgroupneed = false;
                        Element ele3 = null;
                        if (tabs.size() < 1) {
                            ele3 = htmldoc2.getElementById("paramcontainer").append("<table class=\"reggroup\"><tr><td></td></tr></table><br><br><table class=\"reggroup\"><tr><td>" + currparam.getType() + "</td></tr></table><br>");
                            isgroupneed = true;

                        } else {
                            Element ele = (Element) tabs.get(tabs.size() - 1);
                            ele3 = ele.parent().parent().parent().parent();
                        }

                        //Element ele = (Element) tabs.get(tabs.size() - 1);
                        //if (tabs.size() > 0) {
                        int totalbits = 0;
                        String row1 = "";
                        String row2 = "";
                        String temp = "";

                        row2 += "<td class=\"droptarget\" colspan=\"" + currparam.getOffset() + "\" style=\"background-color: #d4e0e2;\"><p draggable=\"true\" class=\"dragtarget\" id=\"dragtarget" + count + "\" title=\"" + currparam.getField() + "\">" + currparam.getOffset() + "</p></td>";
                        totalbits += Integer.parseInt(currparam.getOffset());

                        int remainsize = LocationOptimizer.REG_SIZE - totalbits;
                        //System.out.println("--remain=" + remainsize + "--totalBits=" + totalbits);
                        for (int j = 0; j < remainsize; j++) {
                            row2 += "<td class=\"droptarget\"></td>";
                        }

                        for (int j = 0; j < LocationOptimizer.REG_SIZE; j++) {
                            row1 += "<td></td>";
                        }
                        row1 = "<tr class=\"regheader\">" + row1 + "</tr>";
                        row2 = "<tr>" + row2 + "</tr>";

                        temp = "<table class=\"regcontainer\"><tr><td class=\"regname\" contenteditable=\"true\">reg" + count + "</td></tr><tr><td ><table data-reg-type=\"" + currparam.getType() + "\" class=\"regtab\"><tbody>" + row1 + "" + row2 + "</tbody></table></td></tr></table><br>";

                        count++;
                        //ele3.after("<br>" + temp);
                        /*
                         } else {

                         }*/
                        if (isgroupneed) {
                            //ele3.append("<br>" + temp);
                            ele3.append("<br>" + temp + "<br><table class=\"reggroup\"><tr><td></td></tr></table>");
                        } else {
                            ele3.after("<br>" + temp);
                        }

                        save(new File(IDSUtils.getActiveOrgFilePath()), htmldoc2.toString());
                        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("reloadPage()");
                    }
                }
            }
        }
    }

    private boolean insertField(Parameters param, Document doc) {

        return false;
    }

    private HashMap getparadiff() {
        HashMap<Parameters, Parameters> paramDiff = new HashMap<>();
        Parameters param = new Parameters();

        param = new Parameters();
        param.setField("allegro");
        param.setType("volatile2");
        param.setOffset("8");
        param.setDesc(null);
        paramDiff.put(param, param);

        param = new Parameters();
        param.setField("adc_zero");
        param.setType("volatile");
        param.setOffset("1");
        param.setDesc(null);
        paramDiff.put(param, param);

        param = new Parameters();
        param.setField("afe_adcchp");
        param.setType("volatile");
        param.setOffset("2");
        param.setDesc(null);
        paramDiff.put(param, param);

        param = new Parameters();
        param.setField("afe_hallph_ext");
        param.setType("volatile");
        param.setOffset("3");
        param.setDesc(null);
        paramDiff.put(param, param);

        return paramDiff;
    }

    static int count = 0;

    public void writeParam() {
        YamlParser parser = new YamlParser();
        ArrayList<Parameters> params = parser.readYamlData(new File("D:\\AgnisysProjects\\java\\MatlabParameter\\src\\main\\java\\com\\agnisys\\matlabparameter\\yamlparser\\Final_Allagro_Data.yml"));
        //System.out.println("--yaml length=" + params.size());

        LocationOptimizer ob = new LocationOptimizer();
        ArrayList<Registers> registers = ob.optimizeLocation(params);
//        System.out.println("--registers length=" + registers.size());
        String temp;
        String str = "";
        String row1;
        String row2;
        count = 0;
        String reggroup = "";
        for (Registers regg : registers) {

            int totalbits = 0;
            row1 = "";
            row2 = "";

            for (Parameters par : regg.getFields()) {
                if (par != null) {

                    row2 += "<td class=\"droptarget\" colspan=\"" + par.getOffset() + "\" style=\"background-color: #d4e0e2;\"><p draggable=\"true\" class=\"dragtarget\" id=\"dragtarget" + count + "\" title=\"" + par.getField() + "\">" + par.getOffset() + "</p></td>";
                    totalbits += Integer.parseInt(par.getOffset());
                    count++;
                    /*
                     System.out.println("----field name : " + par.getField());
                     System.out.println("----field offset : " + par.getOffset());
                     System.out.println("----field type : " + par.getType());
                     System.out.println("----field desc : " + par.getDesc());
                     */
                }
            }

            int remainsize = LocationOptimizer.REG_SIZE - totalbits;
            System.out.println("--remain=" + remainsize + "--totalBits=" + totalbits);
            for (int i = 0; i < remainsize; i++) {
                row2 += "<td class=\"droptarget\"></td>";
            }

            for (int i = 0; i < LocationOptimizer.REG_SIZE; i++) {
                row1 += "<td></td>";
            }
            row1 = "<tr class=\"regheader\">" + row1 + "</tr>";
            row2 = "<tr>" + row2 + "</tr>";

            temp = "<table class=\"regcontainer\"><tr><td class=\"regname\" contenteditable=\"true\">" + regg.getRegname() + "</td></tr><tr><td ><table data-reg-type=\"" + regg.getRegType() + "\" class=\"regtab\"><tbody>" + row1 + "" + row2 + "</tbody></table></td></tr></table><br>";

            if (reggroup.equals("")) {
                temp = "<br><br><table class=\"reggroup\"><tr><td>" + regg.getRegType() + "</td></tr></table><br>" + temp;
                reggroup = regg.getRegType();
            } else if (reggroup.equals(regg.getRegType())) {

            } else if (!reggroup.equals(regg.getRegType())) {
                temp = "<table class=\"reggroup\"><tr><td></td></tr></table><br><br><table class=\"reggroup\"><tr><td>" + regg.getRegType() + "</td></tr></table><br>" + temp;
                reggroup = regg.getRegType();
            }

            str += temp;
//            System.out.println("--------------------------------------");
//            System.out.println("");
        }

        str = str + "<table class=\"reggroup\"><tr><td></td></tr></table>";
        //str = "<table class=\"regtab\"><tbody><tr class=\"regheader\"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr><tr><td class=\"droptarget\" colspan=\"16\" style=\"background-color: cadetblue;\"><p draggable=\"true\" class=\"dragtarget\" id=\"dragtarget1\">16</p></td></tr></tbody></table>";
        // System.out.println("--str=" + str);
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("writeparam('" + str + "')");

        ApplicationMainGUIController.APPLICATION_OBJECT.vboxhelp.getChildren().clear();
    }
}
