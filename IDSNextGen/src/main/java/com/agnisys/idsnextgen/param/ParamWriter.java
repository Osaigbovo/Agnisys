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
import com.agnisys.matlabparameter.model.ParamCategory;
import com.agnisys.matlabparameter.model.Parameters;
import com.agnisys.matlabparameter.model.Registers;
import com.agnisys.matlabparameter.yamlparser.YamlDiff;
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
import us.agnisys.idsbatch.Transformer;

/**
 *
 * @author Sumeet
 */
public class ParamWriter {

    HashMap<Parameters, Parameters> paramDiff = null;
    Document htmldoc = null;
    public static String currYAMLFile;

    public void onParamView() {
        String htmlContents = Transformer.readFileAsString(IDSUtils.getActiveOrgFilePath());
        htmldoc = Jsoup.parse(htmlContents);

        List idsTemps = htmldoc.getElementsByClass("idsTemp");
        Element ParamDiv = htmldoc.getElementById("paramcontainer");

        for (Object idstemp : idsTemps) {
            Element idstab = (Element) idstemp;
            String cls = idstab.attr("class").split(" ")[0];
            switch (cls) {
                case "section":
                    updateRegGroup(idstab, ParamDiv);
                    break;

                case "reg":
                    updateReg(idstab, ParamDiv);
                    break;
            }
        }
        hideregview();

        //save in org file. should find better solution
        save(new File(IDSUtils.getActiveOrgFilePath()), htmldoc.toString());
        //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("hideregview()");
        //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().load(IDSUtils.getActiveOrgFilePath());//.reload();//.executeScript("reloadPage()");
        save(new File(IDSUtils.getActiveFilePath()), htmldoc.toString());
//        System.out.println(htmldoc.toString());
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("reloadPage()");

        // System.out.println("--param=" + ParamDiv.toString());
        //ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("updateParamView('" + ParamDiv.toString() + "')");
    }

    private void hideregview() {
        htmldoc.getElementById("regdivcontainer").attr("style", "display:none");
        htmldoc.getElementById("paramcontainer").attr("style", "display:block");

    }

    private void updateRegGroup(Element ele, Element paramdiv) {
        String id = ele.attr("id");
        String prop = ele.getElementsByClass("propclass").get(0).text();
        String desc = ele.getElementsByClass("descclass").get(0).text();
        String offset = ele.getElementsByClass("offset").get(0).text();

        Element param = paramdiv.getElementsByAttributeValue("data-name", id).get(0);
        param.attr("data-prop", prop);
        param.attr("data-desc", desc);
        param.attr("data-offset", offset);
    }

    private void updateReg(Element ele, Element paramdiv) {
        //read register values from register view
        String id = ele.attr("id");
        //String id = ele.getElementsByClass("name").get(0).text();
        String prop = ele.getElementsByClass("propclass").get(0).text();
        String desc = ele.getElementsByClass("descclass").get(0).text();
        String offset = ele.getElementsByClass("offset").get(0).text();
        String regName = ele.getElementsByClass("name").get(0).text();

        //update register value in parameters
        Element param = paramdiv.getElementsByAttributeValue("data-name", id).get(0);
        param.attr("data-prop", prop);
        param.attr("data-desc", desc);
        param.attr("data-offset", offset);
        param.getElementsByClass("regname").get(0).text(regName);

        //read field values from register view
        List fields = ele.getElementsByClass("field");

        for (Object field : fields) {
            Element f = (Element) field;
            String fieldname = f.getElementsByClass("fieldname").get(0).text().trim();
            String bits = f.getElementsByClass("bits").get(0).text().trim();
            String sw = f.getElementsByClass("sw").get(0).text().trim();
            String hw = f.getElementsByClass("hw").get(0).text().trim();
            String defaul = f.getElementsByClass("default").get(0).text().trim();
            String fdesc = f.getElementsByClass("desc").get(0).text().trim();
            String propcls = "";
            if (f.getElementsByClass("propclass").size() > 0) {
                propcls = f.getElementsByClass("propclass").get(0).text().trim();
            }
            //update field values in parameters
            Element paramfield = paramdiv.getElementsByAttributeValue("data-name", fieldname).get(0);
            paramfield.attr("data-bits", bits);
            paramfield.attr("data-sw", sw);
            paramfield.attr("data-hw", hw);
            paramfield.attr("data-default", defaul);
            paramfield.attr("data-desc", fdesc);
            paramfield.attr("data-prop", propcls);

        }
    }

    public void onRegisterView() {
        String str = "";
        String htmlContents = Transformer.readFileAsString(IDSUtils.getActiveOrgFilePath());
        htmldoc = Jsoup.parse(htmlContents);

        Element paramdiv = htmldoc.getElementById("paramcontainer");
        List groups = paramdiv.getElementsByTag("table");

        for (Object group : groups) {

            Element tab = ((Element) group);
            //System.out.println("--class name=" + tab.attr("class"));
            String tabcls = tab.attr("class");
            switch (tabcls) {
                case "reggroup":
                    if (str.equals("")) {
                        str += insertreggroup(tab);
                    } else {
                        str += "<table class=\"endreggroup idsTemp\" id=\"tab1\"><tbody><tr class=\"label\"><td>End RegGroup</td></tbody></table> <br>" + insertreggroup(tab);
                    }
                    break;

                case "regcontainer":
                    str += insertReg(tab);
                    break;
            }
        }
        str += "<table class=\"endreggroup idsTemp\" id=\"tab1\"><tbody><tr class=\"label\"><td>End RegGroup</td></tbody></table> <br>";
//        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("regviewupdate('" + str + "')");
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("regviewupdate('" + str + "')");

    }

    private String insertReg(Element ele) {
        //read reg here
        String name = ele.attr("data-name");
        String offset = ele.attr("data-offset");
        String desc = ele.attr("data-desc");
        String prop = ele.attr("data-prop");
        String regName = ele.getElementsByClass("regname").get(0).text();

        //read filed here
        String fieldstr;

        List fieldsele = ele.getElementsByClass("droptarget");
        String row = "";

        //for (Object field : fieldsele)
        String foffset;
        int counter = 0;
        int size;
        for (int i = fieldsele.size() - 1; i >= 0; i--) {
            Element f = (Element) fieldsele.get(i);

            List ptaglist = f.getElementsByTag("p");

            if (ptaglist.size() > 0) {
                Element ptag = (Element) ptaglist.get(0);
                foffset = ptag.attr("data-size");
                size = Integer.parseInt(foffset);
                if (size > 1) {
                    foffset = counter + ":" + (counter + size - 1);
                    counter += size;
                } else {
                    foffset = String.valueOf(counter);
                    counter++;
                }
                row += "<tr onkeyup=\"setCurrRow(this);\" onclick=\"setCurrRow(this);\" class=\"field edited\"><td class=\"bits disablecell\">" + foffset + "</td><td class=\"fieldname disablecell\" >" + ptag.attr("data-name") + "</td><td class=\"sw\">" + ptag.attr("data-sw") + "</td><td class=\"hw\">" + ptag.attr("data-hw") + "</td><td class=\"default\">" + ptag.attr("data-default") + "</td><td class=\"desc fielddesc\" onkeydown=\"insertNewRow(event,this);\">" + ptag.attr("data-desc") + "</td></tr>";
            } else {
                counter++;
            }
        }

        fieldstr = "<table class=\"fields idsTemp\" id=\"field" + name + "\"><tr class=\"label\"><td class=\"ddregbits\">bits</td><td class=\"lblfieldname\">name</td><td class=\"lblsw\">s/w</td><td class=\"lblhw\">h/w</td><td class=\"lbldefault\">default</td><td class=\"lbldesc\">description</td></tr>" + row + "</table>";

        String imgPath = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAFWSURBVHja1JNdUoMwEMc3gM744EX0CF6iXsZXPY9Tq97Gj1ptRynfpKVQCCHbTWhLeebJzCSzf0h+5L+7MESEIcOCgWMwwLm+fRjm4WZ0h49fJY6nGU6mKV6N7vH1k+PzR4yT98Dopzef4ohmYvTL9xrHs62JHQ3JgwRUnYNqKgMNeQyIDSAooyOeAGM2RbbR8eIP7PPL1oJeZpEHFlaUEGkeLn7XdIAOs9ad6xWknT2EdJZADXkHWLqCvl6DUi1gHnDazI42f4IcdLkPJZ8tKrBtuwNEYQxKCsCmBQgvA3aSJ+FnPUDsx8CcbQdIVyVZaGi2nuc5gU4abL6p4bThio0EaVUdIOQFWErRbDeJlderVL1a9gBhWoJg0tzSAGRVwJn2vPe9SeCYA71mnB0vpJ2jEiZfOnZSuABn7YJ+Lw/tWfv9di2XPS25uy+oLtS//5l2AgwAgHLTw4hIPJYAAAAASUVORK5CYII";
        String reg = "<table contenteditable=\"false\" onclick=\"tabClick(this);\" class=\"reg idsTemp\" id=\"" + name + "\"><tbody><tr><td class=\"header readOnly\"></td><td title=\"reg name\" class=\"name \">" + regName + "</td><td title=\"offset\" class=\"offset disablecell\" colspan=\"2\">" + offset + "</td><td class=\"specImage\"><img title=\"Register\" alt=\"Register\" src=" + imgPath + "></td><td class=\"address addCell readOnly\" ><div class=\"splitVer setBorder\" title=\"address\"><label class=\"label\">address|</label><label class=\"addrvalue\"></label></div><div class=\"splitVer\" title=\"Default\"><label class=\"label\">default |</label><label class=\"defvalue\"></label></div> </td> <td class=\"regwidth hideWidth\">32</td></tr><tr><td colspan=\"6\" title=\"add properties\" class=\"propclass\" contenteditable=\"true\">" + prop + "</td></tr><tr><td colspan=\"6\" title=\"add description here\" class=\"desc descclass\">" + desc + "</td></tr><tr><td colspan=\"6\" class=\"border\"></td></tr><tr><td colspan=\"6\" class=\"fieldtd\">" + fieldstr + "</td></tr></tbody></table><br>";
        return reg;
    }

    private String insertreggroup(Element ele) {
        String name = ele.attr("data-name");
        String offset = ele.attr("data-offset");
        String desc = ele.attr("data-desc");
        String prop = ele.attr("data-prop");
        String imgPath = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAABkSURBVHjaYvz//z8DJYBFOqSVIhNYQMTkugwUwdymGShiyHx0NhMDhYBRKriFci+snFjO8PX7T4akiglYnYrPa2AvsLCwMLAwMw+gF3CF+BCKBUKhjc87FHth4MOAkdLsDBBgAAAIP+g8PM01AAAAAElFTkSuQmCC";
        String reggroup = "<br><table contenteditable=\"false\" class=\"section idsTemp\" onclick=\"tabClick(this)\" id=\"" + name + "\"><tbody><tr><td class=\"header readOnly\"></td><td class=\"name\" title=\"reg group name\">" + name + "</td><td title=\"offset\" class=\"offset\">" + offset + "</td><td class=\"specImage\"><img title=\"RegGroup\" alt=\"RegGrou[]\" src=" + imgPath + "></td><td class=\"address addCell readOnly\"><label class=\"label\">address|</label><label class=\"addrvalue\" title=\"address\"></label></td></tr><tr><td colspan=\"5\" title=\"add properties\"  class=\"propclass\">" + prop + "</td></tr><tr><td colspan=\"5\" title=\"add description here\" class=\"desc descclass\">" + desc + "</td></tr></tbody> </table><br>";
        return reggroup;
    }

    public void updateParam(File yamlfile) {
        ApplicationMainGUIController.APPLICATION_OBJECT.vboxhelp.getChildren().clear();

        YamlDiff parser = new YamlDiff();
        paramDiff = parser.getYamlDiff(new File(currYAMLFile), yamlfile);
        System.out.println("--diff length=" + paramDiff.size());
        //paramDiff = getparadiff();
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
                String fik = p.getField();
                String eleee = ele.attr("title");
                if (p.getField().trim().equals(ele.attr("title").trim())) {

                    //System.out.println("--matching attr=" + ele.attr("title"));
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
        save(new File(IDSUtils.getActiveFilePath()), htmldoc.toString());
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
                                            insertField.html("<p data-size=\"" + currparam.getOffset() + "\" data-name=\"" + currparam.getField() + "\" data-desc=\"" + currparam.getDesc() + "\"  draggable=\"true\" class=\"dragtarget\" id=\"dragtarget" + count + "\" title=\"" + currparam.getField() + "\">" + currparam.getField() + "</p>");
                                            insertField.attr("style", "background-color: #d4e0e2;");
                                            insertField.attr("colspan", String.valueOf(size));

                                            for (int k = 1; k < deletedcell.size(); k++) {
                                                ((Element) fieldcells.get((int) deletedcell.get(k))).remove();
                                            }

                                            isFound = false;
                                            needinsertreg = false;
                                            save(new File(IDSUtils.getActiveOrgFilePath()), htmldoc2.toString());
                                            save(new File(IDSUtils.getActiveFilePath()), htmldoc2.toString());
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

                                    field.html("<p data-size=\"" + currparam.getOffset() + "\" data-name=\"" + currparam.getField() + "\" data-desc=\"" + currparam.getDesc() + "\" draggable=\"true\" class=\"dragtarget\" id=\"dragtarget" + count + "\" title=\"" + currparam.getField() + "\">" + currparam.getField() + "</p>");
                                    field.attr("style", "background-color: #d4e0e2;");
                                    isFound = false;
                                    needinsertreg = false;
                                    save(new File(IDSUtils.getActiveOrgFilePath()), htmldoc2.toString());
                                    save(new File(IDSUtils.getActiveFilePath()), htmldoc2.toString());
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
                            ele3 = htmldoc2.getElementById("paramcontainer").append("<br><br><table data-name=\"" + currparam.getType() + "\" class=\"reggroup\"><tr><td>" + currparam.getType() + "</td></tr></table>");
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

                        row2 += "<td class=\"droptarget\" colspan=\"" + currparam.getOffset() + "\" style=\"background-color: #d4e0e2;\"><p data-size=\"" + currparam.getOffset() + "\" data-name=\"" + currparam.getField() + "\" data-desc=\"" + currparam.getDesc() + "\" draggable=\"true\" class=\"dragtarget\" id=\"dragtarget" + count + "\" title=\"" + currparam.getField() + "\">" + currparam.getField() + "</p></td>";
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

                        temp = "<table data-offset=\"" + LocationOptimizer.REG_SIZE + "\" data-name=\"reg" + count + "\" class=\"regcontainer\"><tr><td class=\"regname\" contenteditable=\"true\">reg" + count + "</td></tr><tr><td ><table data-reg-type=\"" + currparam.getType() + "\" class=\"regtab\"><tbody>" + row1 + "" + row2 + "</tbody></table></td></tr></table><br>";

                        count++;
                        //ele3.after("<br>" + temp);
                        /*
                         } else {

                         }*/
                        if (isgroupneed) {
                            //ele3.append("<br>" + temp);
                            ele3.append("<br>" + temp + "<br><table class=\"endreggroupparam\"><tr><td></td></tr></table>");
                        } else {
                            ele3.after("<br>" + temp);
                        }

                        save(new File(IDSUtils.getActiveOrgFilePath()), htmldoc2.toString());
                        save(new File(IDSUtils.getActiveFilePath()), htmldoc2.toString());
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

    private ArrayList<ParamCategory> getCategoryList() {
        ArrayList<ParamCategory> ob = new ArrayList<>();
        ParamCategory parm;

        parm = new ParamCategory("volatile", "rw", "ro", "0", "");
        ob.add(parm);

        parm = new ParamCategory("shadow", "rw", "rw", "0", "");
        ob.add(parm);

        parm = new ParamCategory("eeprom", "rw", "ro", "0", "");
        ob.add(parm);

        return ob;
    }

    static int count = 0;

    private ParamCategory getaccess(String categoryName, ArrayList<ParamCategory> paramcats) {
        for (ParamCategory parm : paramcats) {
            if (categoryName.equals(parm.getCategory())) {
                return parm;
            }
        }
        return null;
    }

    public void writeParam(File yamlFile) {
        currYAMLFile = yamlFile.getAbsolutePath();

        YamlParser parser = new YamlParser();
        ArrayList<Parameters> params = parser.readYamlData(yamlFile);
        System.out.println("--param length=" + params.size());

        LocationOptimizer ob = new LocationOptimizer();
        ArrayList<Registers> registers = ob.optimizeLocation(params);
        ArrayList<ParamCategory> paramCat = /*parser.getAttributes();*/ getCategoryList();
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
                    ParamCategory cat = getaccess(par.getType(), paramCat);
                    String sw = "";
                    String hw = "";
                    String def = "";

                    if (cat != null) {
                        sw = cat.getSw();
                        hw = cat.getHw();
                        def = cat.getDef();
                    }
                    row2 += "<td class=\"droptarget\" colspan=\"" + par.getOffset() + "\" style=\"background-color: #d4e0e2;\"><p data-default=\"" + def + "\" data-sw=\"" + sw + "\" data-hw=\"" + hw + "\" data-size=\"" + par.getOffset() + "\" data-name=\"" + par.getField() + "\" data-desc=\"" + par.getDesc() + "\" draggable=\"true\" class=\"dragtarget\" id=\"dragtarget" + count + "\" title=\"" + par.getField() + " : " + par.getOffset() + "\">" + par.getField() + "</p></td>";
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
//            System.out.println("--remain=" + remainsize + "--totalBits=" + totalbits);
            for (int i = 0; i < remainsize; i++) {
//                row2 += "<td class=\"droptarget\"></td>";
                row2 = "<td class=\"droptarget\"></td>" + row2;
            }

            for (int i = 0; i < LocationOptimizer.REG_SIZE; i++) {
                row1 += "<td></td>";
            }
            row1 = "<tr class=\"regheader\">" + row1 + "</tr>";
            row2 = "<tr>" + row2 + "</tr>";

            temp = "<table data-name=\"" + regg.getRegname() + "\" class=\"regcontainer\"><tr><td class=\"regname\" contenteditable=\"true\">" + regg.getRegname() + "</td></tr><tr><td ><table data-reg-type=\"" + regg.getRegType() + "\" class=\"regtab\"><tbody>" + row1 + "" + row2 + "</tbody></table></td></tr></table><br>";

            if (reggroup.equals("")) {
                temp = "<br><br><table data-name=\"" + regg.getRegType() + "\" class=\"reggroup\"><tr><td>" + regg.getRegType() + "</td></tr></table><br>" + temp;
                reggroup = regg.getRegType();
            } else if (reggroup.equals(regg.getRegType())) {

            } else if (!reggroup.equals(regg.getRegType())) {
                temp = "<table class=\"endreggroupparam\"><tr><td></td></tr></table><br><br><table data-name=\"" + regg.getRegType() + "\" class=\"reggroup\"><tr><td>" + regg.getRegType() + "</td></tr></table><br>" + temp;
                reggroup = regg.getRegType();
            }

            str += temp;
//            System.out.println("--------------------------------------");
//            System.out.println("");
        }

        str = str + "<table class=\"endreggroupparam\"><tr><td></td></tr></table>";
        //str = "<table class=\"regtab\"><tbody><tr class=\"regheader\"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr><tr><td class=\"droptarget\" colspan=\"16\" style=\"background-color: cadetblue;\"><p draggable=\"true\" class=\"dragtarget\" id=\"dragtarget1\">16</p></td></tr></tbody></table>";
        // System.out.println("--str=" + str);
        ApplicationMainGUIController.APPLICATION_OBJECT.getActiveWebEngine().executeScript("writeparam('" + str + "')");

        ApplicationMainGUIController.APPLICATION_OBJECT.vboxhelp.getChildren().clear();

    }
}
