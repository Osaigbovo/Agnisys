/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.param;

import com.agnisys.idsnextgen.controllers.ApplicationMainGUIController;
import com.agnisys.matlabparameter.LocationOptimizer;
import com.agnisys.matlabparameter.model.Parameters;
import com.agnisys.matlabparameter.model.Registers;
import com.agnisys.matlabparameter.yamlparser.YamlParser;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Sumeet
 */
public class ParamWriter {

    public void writeParam() {
        YamlParser parser = new YamlParser();
        ArrayList<Parameters> params = parser.readYamlData(new File("D:\\AgnisysProjects\\java\\MatlabParameter\\src\\main\\java\\com\\agnisys\\matlabparameter\\yamlparser\\Final_Allagro_Data.yml"));
        System.out.println("--yaml length=" + params.size());

        LocationOptimizer ob = new LocationOptimizer();
        ArrayList<Registers> registers = ob.optimizeLocation(params);
        System.out.println("--registers length=" + registers.size());
        String temp;
        String str = "";
        String row1;
        String row2;
        int count = 0;
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
                    System.out.println("----field name : " + par.getField());
                    System.out.println("----field offset : " + par.getOffset());
                    System.out.println("----field type : " + par.getType());
                    System.out.println("----field desc : " + par.getDesc());

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

            temp = "<table class=\"regcontainer\"><tr><td class=\"regname\" contenteditable=\"true\">" + regg.getRegname() + "</td></tr><tr><td ><table class=\"regtab\"><tbody>" + row1 + "" + row2 + "</tbody></table></td></tr></table><br>";

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
    }
}
