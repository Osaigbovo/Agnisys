/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.backannotation;

import static com.agnisys.idsnextgen.backannotation.BackAnnotation.SetFieldErrorMsg;
import static com.agnisys.idsnextgen.backannotation.BackAnnotation.fillSpecData;
import static com.agnisys.idsnextgen.backannotation.BackAnnotation.headerMap;
import static com.agnisys.idsnextgen.backannotation.BackAnnotation.jsonMsgStr;
import com.agnisys.idsnextgen.global.IDSUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import static us.agnisys.commons.AgnisysUtils.convertToIDSNumericType;
import static us.agnisys.html.HtmlParser.html2Xhtml;
import us.agnisys.idsbatch.Transformer;

/**
 *
 * @author Himanshu yadav
 */
public class MultiBusDomainToc {

    public static ArrayList<AnnotationHeader> busArrayList = new ArrayList<AnnotationHeader>();
    static String tAddr = "";
    static List<Node> busDomailsList = null;

    public static String MultiBusDomainToc(org.dom4j.Element xmlNode, org.jsoup.nodes.Document doc) {
        String str = "";
        try {
            File tocFile = IDSUtils.loadSystemResource("ids_templates/toc.html");
            String htmlContents = Transformer.readFileAsString(tocFile.getAbsolutePath());
            String xhtmlContents = html2Xhtml(htmlContents);
            Document tocDoc = DocumentHelper.parseText(xhtmlContents);
            Node config = xmlNode.selectSingleNode("config");
            Node busDomails = config.selectSingleNode("busdomains");
            busDomailsList = busDomails.selectNodes("busdomain");
            for (Node busDomain : busDomailsList) {
                String busName = busDomain.valueOf("@name");
                setData(xmlNode, busName);
                str = str.concat(replacePlaceholder(tocDoc, busName));
                fillSpecData(doc, busArrayList);

                busArrayList.clear();
            }

        } catch (Exception e) {
            System.out.println("error- " + e);
        }
        return str;
    }

    private static String replacePlaceholder(Document tocDoc, String busName) {
        String tocStr = "";
        String placeHolder = "";
        try {
            Node clone = tocDoc.getRootElement().selectSingleNode("table").selectSingleNode("./tbody/tr[@id='replace']");//selectSingleNode("table");//getElementById("replace").clone();
            String strClone = clone.asXML();
            placeHolder = strClone;
//
            for (int i = 0; i < busArrayList.size(); i++) {
                if (placeHolder.contains("$$heading")) {
                    placeHolder = placeHolder.replace("$$heading", busArrayList.get(i).getHeading());
                }

                if (placeHolder.contains("$$component")) {
                    String space = "";
                    String name = busArrayList.get(i).getName();
                    String heading = busArrayList.get(i).getHeading();
                    int length = heading.length();
                    if (length != 1) {
                        for (int l = 0; l < length - 2; l++) {
                            space = space.concat("&nbsp;&nbsp;");
                            l = l + 1;
                        }
                    }
                    placeHolder = placeHolder.replace("$$component", space.concat("<a href=#" + busArrayList.get(i).getHeading() + ">" + name + "</a>"));
                }
                if (placeHolder.contains("$$default")) {
                    placeHolder = placeHolder.replace("$$default", busArrayList.get(i).getDefault_F());
                }
                if (placeHolder.contains("$$address")) {
                    String endaddr = busArrayList.get(i).getEndAddress();
                    if ("".equals(endaddr)) {
                        placeHolder = placeHolder.replace("$$address", busArrayList.get(i).getAddress());
                    } else {
                        placeHolder = placeHolder.replace("$$address", busArrayList.get(i).getAddress() + " - " + endaddr);
                    }
                }
                if (busArrayList.size() > i + 1) {
                    placeHolder = placeHolder.concat(strClone);
                }
            }

            String str = tocDoc.getRootElement().selectSingleNode("table").asXML();//.selectSingleNode("./tbody/tr[@id='replace']").asXML();
            str = str.replace(strClone, placeHolder);
//            System.out.println("print table--" + str);
//            tocDoc.select("tr").get(2).remove();
            String headings = tocDoc.getRootElement().selectSingleNode("table").selectSingleNode("./tbody/tr/th[@id='busname']").getText();//.asafter(str);
            String headings1 = headings.concat(" (" + busName + ")");
            str = str.replace(headings, headings1);
//            System.out.println("print aa=" + headings);
            tocStr = str.concat("</br>");
//            tocStr = str;
//            System.out.println("prin-" + tocStr);
            // tocDoc.remove();
        } catch (Exception ex) {
            jsonMsgStr("default", "Error Occure in Creating Toc : " + ex);
//            System.err.println("Error Occure in Creating Toc : " + ex);
        }
        return tocStr;
    }

    public static void setData(Node xmlNode, String busName) throws Exception {
        try {

            org.dom4j.Element elem = (org.dom4j.Element) xmlNode.selectNodes("config").get(0);
            org.dom4j.Element addrUnitEle = (org.dom4j.Element) elem.selectNodes("addressunit").get(0);
            String addrUnit = addrUnitEle.getText();
            //calculate address
            String address = "@" + busName + ".address";
            address = xmlNode.valueOf(address);
            String addr = convertToIDSNumericType(address);
            long finalAddr = Long.parseLong(addr) / (Integer.parseInt(addrUnit) / 8);
            String finaladdr = "0x" + Long.toHexString(finalAddr);

            //calculate endAddress
            String endAddress = "@" + busName + ".endaddress";
            endAddress = xmlNode.valueOf(endAddress);
            String endAddr = convertToIDSNumericType(endAddress);
            long finalEndAddr = Long.parseLong(endAddr) / (Integer.parseInt(addrUnit) / 8);
            String finalEndaddr = "0x" + Long.toHexString(finalEndAddr);
            String msg = xmlNode.valueOf("@msg");
            if (msg.isEmpty()) {
                msg = "";
            }
            headerMap.setHeading(xmlNode.valueOf("@heading").trim());
            headerMap.setName(xmlNode.valueOf("@name").trim());
            headerMap.setDefault_F(xmlNode.valueOf("@default").trim());

            headerMap.setEndAddress(finalEndaddr);
            headerMap.setTid(xmlNode.valueOf("@id").trim());
            headerMap.setMsg(msg);
            headerMap.setAddress(finaladdr);
            String allAddress = addAllAddr(xmlNode, addrUnit);
            headerMap.setAllAddresses(allAddress);
            busArrayList.add(headerMap);

            List<Node> childNodes = xmlNode.selectNodes("child::node()");
            setChildData(childNodes, addrUnit, busName);
        } catch (Exception ex) {
            jsonMsgStr("default", "exception occure- " + ex);
        }
    }

    public static void setChildData(List<Node> listNode, String addrUnit, String busName) {
        try {
            for (Node node : listNode) {
                if ("emptyspace".equalsIgnoreCase(node.getName()) || "reg".equalsIgnoreCase(node.getName()) || "mem".equalsIgnoreCase(node.getName()) || "section".equals(node.getName()) || "block".equals(node.getName()) || "chip".equals(node.getName()) || "system".equals(node.getName())) {

                    String msg = node.valueOf("@msg");
                    if (msg.equals("")) {
                        msg = "";
                    }
                    String address = "@" + busName + ".address";
                    address = node.valueOf(address);
                    if (!"".equals(address)) {
                        String allAddr = "";
                        String addr = convertToIDSNumericType(address);
                        int finalAddr = Integer.parseInt(addr) / (Integer.parseInt(addrUnit) / 8);
                        String finaladdr = "0x" + Integer.toHexString(finalAddr);

                        String endAddress = "@" + busName + ".endaddress";
                        endAddress = node.valueOf(endAddress);
                        String endAddr = convertToIDSNumericType(endAddress);
                        long finalEndAddr = Long.parseLong(endAddr) / (Integer.parseInt(addrUnit) / 8);
                        String finalEndaddr = "0x" + Long.toHexString(finalEndAddr);
                        if (!"reg".equals(node.getName()) && !"emptyspace".equalsIgnoreCase(node.getName())) {

                            AnnotationHeader headerMap = new AnnotationHeader();
                            headerMap.setHeading(node.valueOf("@heading"));
                            headerMap.setName(node.valueOf("@name").trim());
                            headerMap.setDefault_F(node.valueOf("@default").trim());
                            String allAddress = addAllAddr(node, addrUnit);
                            headerMap.setAllAddresses(allAddress);
                            headerMap.setAddress(finaladdr);
                            headerMap.setTid(node.valueOf("@id"));
                            headerMap.setMsg(msg);
                            headerMap.setEndAddress(finalEndaddr);

                            List<Node> childNodes = node.selectNodes("child::node()");
                            busArrayList.add(headerMap);

                            setChildData(childNodes, addrUnit, busName);
                        }
                        String emptyspace = null;

                        if ("reg".equalsIgnoreCase(node.getName()) && !"mem".equalsIgnoreCase(node.valueOf("@type"))) {
                            AnnotationHeader headerMap = new AnnotationHeader();
                            headerMap.setHeading(node.valueOf("@heading"));
                            headerMap.setName(node.valueOf("@name").trim());
                            headerMap.setDefault_F(node.valueOf("@default").trim());
                            String allAddress = addAllAddr(node, addrUnit);
                            headerMap.setAllAddresses(allAddress);
                            headerMap.setAddress(finaladdr);
                            headerMap.setTid(node.valueOf("@id"));
                            headerMap.setEndAddress("");

                            List<Node> fldNodes = node.selectNodes("child::node()");
                            String fldMsg = SetFieldErrorMsg(fldNodes);
                            String finalMsg = "";
                            if (!msg.equals("") || !fldMsg.equals("")) {
                                msg = msg.concat(fldMsg);
                                for (String str : msg.split(";")) {
                                    str = str.concat("</br>");
                                    finalMsg = finalMsg.concat(str);
                                }
                            }
                            headerMap.setMsg(finalMsg);
                            busArrayList.add(headerMap);
                        }

                    }
                    if ("emptyspace".equalsIgnoreCase(node.getName())) {
//                            System.out.println("true");
                        String tempAddr = node.selectSingleNode("config").selectSingleNode("busdomains").selectSingleNode("busdomain").valueOf("@address");//valueOf(node.valueOf("@address"));
                        String tempEndAddr = node.selectSingleNode("config").selectSingleNode("busdomains").selectSingleNode("busdomain").valueOf("@endaddress");
                        String addr = convertToIDSNumericType(tempAddr);
                        int finalAddr = Integer.parseInt(addr) / (Integer.parseInt(addrUnit) / 8);
                        String finaladdr = "0x" + Integer.toHexString(finalAddr);

                        String endAddr = convertToIDSNumericType(tempEndAddr);
                        long finalEndAddr = Long.parseLong(endAddr) / (Integer.parseInt(addrUnit) / 8);
                        String finalEndaddr = "0x" + Long.toHexString(finalEndAddr);
//                        System.out.println("addr- " + finaladdr + " end address- " + finalEndaddr);
                        AnnotationHeader headerMap = new AnnotationHeader();
                        headerMap.setHeading("");
                        headerMap.setName("");
                        headerMap.setDefault_F("");
                        headerMap.setTid("");
                        headerMap.setMsg("");
                        headerMap.setAddress(finaladdr);
                        headerMap.setEndAddress(finalEndaddr);

//                        emptyspace = finaladdr.concat(" - ");
//                        emptyspace = emptyspace.concat(finalEndaddr);
//                        headerMap.setAddress(emptyspace);
                        busArrayList.add(headerMap);
                    }
                    String tempAddr = "";
                    if (!"emptyspace".equalsIgnoreCase(node.getName())) {

//                        for (Node busDomain : busDomailsList) {
//                            String bName = busDomain.valueOf("@name");
//                            String tAddress = "@" + bName + ".address";
//                            address = node.valueOf(address);
//                            // String bName = busDomain.valueOf("@name");
//                            String addr1 = convertToIDSNumericType(address);
//                            int finalAddr1 = Integer.parseInt(addr1) / (Integer.parseInt(addrUnit) / 8);
//                            String finaladdr1 = "0x" + Integer.toHexString(finalAddr1);
//                            //  allAddr1 = allAddr1.concat(finaladdr);
//                            tempAddr = addr1.concat(finaladdr1);
//                        }
                    }
                }
            }
        } catch (Exception ex) {
            jsonMsgStr("default", "Error Occure in Set  multiple Toc Data : " + ex);

        }
    }

    public static String addAllAddr(Node node, String addrUnit) {
        String allAddr = "";
        boolean flag = false;
        if (!"emptyspace".equalsIgnoreCase(node.getName())) {

            for (Node busDomain : busDomailsList) {
                String bName = busDomain.valueOf("@name");
                String addr = "@" + bName + ".address";
                String address = node.valueOf(addr);
                // String bName = busDomain.valueOf("@name");
                String tempTddr = convertToIDSNumericType(address);
                if ("".equals(tempTddr)) {
//                    System.out.println("to be break" + tempTddr);
                } else {
                    int tempFinalAddr = Integer.parseInt(tempTddr) / (Integer.parseInt(addrUnit) / 8);
                    String finalAddr = "0x" + Integer.toHexString(tempFinalAddr);

//  allAddr1 = allAddr1.concat(finaladdr);
                    if (flag == false) {
                        allAddr = allAddr.concat(bName + ":" + finalAddr);
                        flag = true;
                    } else {

                        allAddr = allAddr.concat("<br>" + "<pre style=\"display:inline;\">     </pre>" + bName + ":" + finalAddr);
                    }
                }
            }
        }
//        System.out.println("print all address -: " + allAddr);
        return allAddr;
    }
}
