package com.agnisys.idsnextgen.backannotation;

import static com.agnisys.idsnextgen.backannotation.MultiBusDomainToc.MultiBusDomainToc;
import com.agnisys.idsnextgen.global.IDSUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Node;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import static us.agnisys.commons.AgnisysUtils.convertToIDSNumericType;
import us.agnisys.idsbatch.Transformer;
import us.agnisys.socedatasheet.SoceDatasheetGenerator;

/**
 *
 * @author Himanshu yadav
 */
public class BackAnnotation {

    public static void main(String[] args) {

//         File inpfile = new File("C:\\Users\\Agnisys43\\Documents\\project3\\proj1\\mbd\\t1.html");
//         File annotFile = new File("C:\\Users\\Agnisys43\\Documents\\project3\\proj1\\mbd\\.ids\\t1_annotate.xrsl");
        File inpfile = new File("C:\\Users\\Agnisys43\\Documents\\project3\\proj1\\mbd\\mbdtest1.idsng");
        File annotFile = new File("C:\\Users\\Agnisys43\\Documents\\project3\\proj1\\mbd\\.ids\\mbdtest1_annotate.xrsl");

        try {
            backAnnotation(inpfile, annotFile);
        } catch (Exception ex) {
            throw ex;
        }

    }
    public static AnnotationHeader headerMap = new AnnotationHeader();
    public static ArrayList<AnnotationHeader> arrayList = new ArrayList<AnnotationHeader>();
    public static boolean toc = true;
    public static boolean multiToc = true;
    public static int comma = 0;
    public static String jsonStr = "0";
    final static boolean debug = false;

    public static String backAnnotation(File specFile, File annotFile) {
        String docStr = "";
        try {
            if (debug) {
                System.out.println("Start backAnnotation...");
            }

            org.dom4j.Document xrslDoc = SoceDatasheetGenerator.loadIPXACT(annotFile);
            org.dom4j.Element xmlNode = xrslDoc.getRootElement();
            List< Node> checkBusDomain = xmlNode.selectNodes("//@bus.domain");
            // createErrorLink();
            String htmlContents = Transformer.readFileAsString(specFile.getAbsolutePath());
            // String xhtmlContents = HtmlParser.html2Xhtml(htmlContents);
            if (debug) {
                System.out.println("parse html to xhtml");
            }
            org.jsoup.nodes.Document doc = Jsoup.parse(htmlContents);
            if (checkBusDomain.size() > 0) {
                multiToc = false;
                docStr = MultiBusDomainToc(xmlNode, doc);

            }
            if (debug) {
                System.out.println("parse xhtml to Document " + doc.toString());
                System.out.println("start reading xml and set data in arrayList");
            }
            if (multiToc) {
                setData(annotFile);
            }
            if (debug) {
                System.out.println("stop reading xml and set data in arrayList");
                System.out.println("start fill data in header,address and default");
            }
            fillSpecData(doc, arrayList);
            if (debug) {
                System.out.println("stop fill data in header,address and default");
                System.out.println("start adding TOC ");
            }
            if (!multiToc) {
                doc.getElementsByClass("tocTable").remove();
                doc.select("table").get(0).before(docStr).html();
// doc.getElementsByClass("tocTable").get(0).html(str);
//                int tocSize = doc.getElementsByClass("tocTable").size();
//                if (doc.select("table").hasClass("tocTable")) {
//                    if (tocSize >= 1) {
//                        doc.getElementsByClass("tocTable").get(0).html(docStr);
//                        if (tocSize > 1) {
//                            for (int i = 1; i < tocSize; i++) {
//                                doc.getElementsByClass("tocTable").get(i).remove();
//                            }
//                        }
//                    }
////                    doc.select("table").get(0).before(str);
////                    doc.getElementsByTag("br").get(0).remove();
//                } else {
//                    doc.select("table").get(0).before(docStr);
//                }
                doc.getElementsByTag("br").get(0).remove();
                doc.getElementsByTag("br").get(0).remove();
//                doc.getElementsByClass("tocClass").html(docStr);
                docStr = doc.toString();
            }

            if (multiToc) {
                docStr = addTocTemplate(doc);
            }
            save(specFile, docStr);
            if (comma == 1) {
                jsonStr = jsonStr.concat("]\n }");
            } else {
                jsonStr = "0";
            }
            comma = 0;
            arrayList.clear();
            toc = true;
            multiToc = true;
            if (debug) {
                System.out.println("End BackAnnotation........");
            }
        } catch (Exception ex) {
            jsonMsgStr("default", "Error Occure  in Back Annotation :" + ex);
        }
//        System.out.println("print json string =" + jsonStr);
        return jsonStr;
    }

    public static String fillSpecData(org.jsoup.nodes.Document doc, ArrayList<AnnotationHeader> arrayList) {
        List tableList = doc.select("table");
        int errorNumber = 0;
        for (int i = 0; i < tableList.size(); i++) {

            String htmlId = doc.select("table").get(i).attr("id");
            for (int j = 0; j < arrayList.size(); j++) {
                if (htmlId.equalsIgnoreCase(arrayList.get(j).getTid())) {
                    // doc.select("table").get(i).attr("id");

                    Element tableEle = (Element) tableList.get(i);
                    List trList = tableEle.select("tr");

                    for (Object tr : trList) {
                        Element trElement = (Element) tr;
                        if (trElement.hasClass("idscheck")) {
                            trElement.remove();
                        }
                        List tdList = trElement.select("td");
                        for (Object td : tdList) {
                            Element tdElement = (Element) td;
                            if (tdElement.hasClass("header") == true) {
                                tdElement.html("<a name=" + arrayList.get(j).getHeading() + "></a>" + "<font size=\"3\" color=\"blue\">" + arrayList.get(j).getHeading() + "</font>");
                            }
                            String classname = tdElement.className();
                            if ("desc descclass".equals(classname) && !arrayList.get(j).getMsg().equals("")) {
                                toc = false;
                                if (comma == 0) {

                                }
                                String enumber = String.valueOf(errorNumber++);
                                String eid = "target00".concat(enumber);
                                String msg = arrayList.get(j).getMsg();
                                jsonMsgStr(eid, msg);
                                trElement.after("<tr class=\"idscheck\" style=\"border:2px solid #f00;\"><td colspan=\"6\"><a name=" + eid + ">" + msg + "</a></td></tr>");
                            }
                            if (tdElement.hasClass("address") == true) {
                                List addrlableList = tdElement.select("label");
                                for (Object tlable : addrlableList) {
                                    Element tdivElement = (Element) tlable;
                                    if (tdivElement.hasClass("addrvalue") == true) {

                                        if (!multiToc) {

                                            // System.out.println("print setaddress all value-" + arrayList.get(j).getAllAddresses());
                                            tdivElement.html(arrayList.get(j).getAllAddresses());
                                        } else {
                                            tdivElement.text(arrayList.get(j).getAddress());
                                        }
//                                        System.out.println("true 4");
                                    }
                                    if (tdivElement.hasClass("defvalue") == true) {
                                        tdivElement.text(arrayList.get(j).getDefault_F());
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return null;
    }

    public static String addTocTemplate(org.jsoup.nodes.Document doc) {
        String docStr = "";
        try {
//            if (doc.getAllElements().hasClass("tocTable")) {
//                doc.getElementsByClass("tocTable").remove();
//                doc.getElementsByTag("br").get(0).remove();
//            }
            if (debug) {
                //System.out.println("delete Existing table(if exist)........");
            }
//File tocFile = new File("E:\\Files\\IDSNextGen\\toc.html");
            if (toc) {
                if (debug) {
                    System.out.println("adding Toc template........");
                }
                File tocFile = IDSUtils.loadSystemResource("ids_templates/toc.html");
                String tocHtmlContents = Transformer.readFileAsString(tocFile.getAbsolutePath());
                org.jsoup.nodes.Document tocDoc = Jsoup.parse(tocHtmlContents);
                String str = replacePlaceholderInHtml(tocDoc);
                if (doc.select("table").hasClass("tocTable")) {
                    doc.getElementsByClass("tocTable").get(0).html(str);
//                    doc.select("table").get(0).before(str);
                    doc.getElementsByClass("tocTable").get(0).getElementsByTag("br").get(0).remove();
                } else {
                    doc.select("table").get(0).before(str);
                }// doc.getElementsByClass("tocClass").html(str);
                if (debug) {
                    System.out.println("added Toc template with data........");
                }
            }
            docStr = doc.toString();
        } catch (Exception ex) {
            jsonMsgStr("default", "Error Occure in adding Toc Template : " + ex);
//            System.err.println("Error Occure in adding Toc Template : " + ex);
        }
        return docStr;
    }

    public static void setData(File annofile) throws Exception {
        try {
            org.dom4j.Document xrslDoc = SoceDatasheetGenerator.loadIPXACT(annofile);
            org.dom4j.Element xmlNode = xrslDoc.getRootElement();
            List< Node> check = xmlNode.selectNodes("//@msg");

            if (check.size() > 0) {
                String defMsg = "";
                toc = true;
                for (Node item : check) {
                    defMsg = defMsg.concat(item.asXML());
                }
                jsonMsgStr("default", defMsg);
            }

            String address = xmlNode.valueOf("@address");
            org.dom4j.Element elem = (org.dom4j.Element) xmlNode.selectNodes("config").get(0);
            org.dom4j.Element addrUnitEle = (org.dom4j.Element) elem.selectNodes("addressunit").get(0);

            String addrUnit = addrUnitEle.getText();
            String addr = convertToIDSNumericType(address);
            long finalAddr = Long.parseLong(addr) / (Integer.parseInt(addrUnit) / 8);
            String fullAddr = "0x" + Long.toHexString(finalAddr);

            //calculate endAddress
            String endAddress = xmlNode.valueOf("@endaddress");
            String endAddr = convertToIDSNumericType(endAddress);
            long finalEndAddr = Long.parseLong(endAddr) / (Integer.parseInt(addrUnit) / 8);
            String fullEndaddr = "0x" + Long.toHexString(finalEndAddr);

            String msg = xmlNode.valueOf("@msg");
            if (msg.isEmpty()) {
                msg = "";
            }
            headerMap.setHeading(xmlNode.valueOf("@heading").trim());
            headerMap.setName(xmlNode.valueOf("@name").trim());
            headerMap.setDefault_F(xmlNode.valueOf("@default").trim());
            headerMap.setAddress(fullAddr);
            headerMap.setEndAddress(fullEndaddr);
            headerMap.setTid(xmlNode.valueOf("@id").trim());
            headerMap.setMsg(msg);
            arrayList.add(headerMap);
            List<Node> childNodes = xmlNode.selectNodes("child::node()");
            setChildData(childNodes, addrUnit);

        } catch (Exception ex) {
            jsonMsgStr("default", "exception occure- " + ex);
//            System.err.println("exception occure- " + ex);
        }
    }

    public static void setChildData(List<Node> listNode, String addrUnit) {
        try {
            for (Node node : listNode) {
                if ("emptyspace".equalsIgnoreCase(node.getName()) || "reg".equalsIgnoreCase(node.getName()) || "mem".equalsIgnoreCase(node.getName()) || "section".equals(node.getName()) || "block".equals(node.getName()) || "chip".equals(node.getName()) || "system".equals(node.getName())) {

                    String msg = node.valueOf("@msg");
                    if (msg.equals("")) {
                        msg = "";
                    }
                    //calculate Address
                    String address = node.valueOf("@address");
                    String addr = convertToIDSNumericType(address);
                    long finalAddr = Long.parseLong(addr) / (Integer.parseInt(addrUnit) / 8);
                    String finaladdr = "0x" + Long.toHexString(finalAddr);
                    //calculate endAddress
                    String endAddress = node.valueOf("@endaddress");
                    String endAddr = convertToIDSNumericType(endAddress);
                    long finalEndAddr = Long.parseLong(endAddr) / (Integer.parseInt(addrUnit) / 8);
                    String finalEndaddr = "0x" + Long.toHexString(finalEndAddr);
                    if (!"reg".equals(node.getName()) && !"emptyspace".equalsIgnoreCase(node.getName())) {

                        AnnotationHeader headerMap = new AnnotationHeader();
                        headerMap.setHeading(node.valueOf("@heading"));
                        headerMap.setName(node.valueOf("@name").trim());
                        headerMap.setDefault_F(node.valueOf("@default").trim());
                        headerMap.setAddress(finaladdr);
                        headerMap.setTid(node.valueOf("@id"));
                        headerMap.setMsg(msg);
                        headerMap.setEndAddress(finalEndaddr);

                        List<Node> childNodes = node.selectNodes("child::node()");
                        arrayList.add(headerMap);
                        setChildData(childNodes, addrUnit);
                    }
                    if ("emptyspace".equalsIgnoreCase(node.getName())) {

                        AnnotationHeader headerMap = new AnnotationHeader();
                        headerMap.setHeading("");
                        headerMap.setName("");
                        headerMap.setDefault_F("");
                        headerMap.setTid("");
                        headerMap.setMsg("");
                        headerMap.setEndAddress("");
                        String emptyspace = "";
                        emptyspace = finaladdr.concat(" - ");
                        emptyspace = emptyspace.concat(finalEndaddr);
                        //  emptyspace = "  <span style=\"background-color: #C0C0C0\">" + emptyspace + "</span>";
                        headerMap.setAddress(emptyspace);
                        arrayList.add(headerMap);
                    }
                    if ("reg".equalsIgnoreCase(node.getName()) && !"mem".equalsIgnoreCase(node.valueOf("@type"))) {
                        AnnotationHeader headerMap = new AnnotationHeader();
                        headerMap.setHeading(node.valueOf("@heading"));
                        headerMap.setName(node.valueOf("@name").trim());
                        headerMap.setDefault_F(node.valueOf("@default").trim());
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
                        arrayList.add(headerMap);
                    }

                }
            }

        } catch (Exception ex) {
            jsonMsgStr("default", "Error Occure in Set Toc Data : " + ex);
//            System.err.println("Error Occure in Set Toc Data : " + ex);
        }
    }

    public static String SetFieldErrorMsg(List<Node> fldNodes) {
        String finalMsg = "";
        try {
            for (Node node : fldNodes) {
                if ("field".equals(node.getName())) {
                    String msg = node.valueOf("@msg");
                    if (msg.equals("")) {
                        msg = "";
                    }
                    //  System.out.println("print message " + msg);
                    finalMsg = finalMsg.concat(msg);
                    // System.out.println("print message final " + finalMsg);
                }
            }
        } catch (Exception ex) {
//todo
        }
        return finalMsg;
    }

    private static String replacePlaceholderInHtml(org.jsoup.nodes.Document tocDoc) {
        String tocStr = "";
        String placeHolder = "";
        try {
            org.jsoup.nodes.Element clone = tocDoc.select("table").get(0).getElementById("replace").clone();

            String strClone = clone.toString();
            placeHolder = strClone;

            for (int i = 0; i < arrayList.size(); i++) {
                if (placeHolder.contains("$$heading")) {
                    placeHolder = placeHolder.replace("$$heading", arrayList.get(i).getHeading());
                }
                if (placeHolder.contains("$$component")) {
                    String space = "";
                    String name = arrayList.get(i).getName();
                    String heading = arrayList.get(i).getHeading();
                    int length = heading.length();
                    if (length != 1) {
                        for (int l = 0; l < length - 2; l++) {
                            space = space.concat("&nbsp;&nbsp;");
                            l = l + 1;
                        }
                    }
                    placeHolder = placeHolder.replace("$$component", space.concat("<a href=#" + arrayList.get(i).getHeading() + ">" + name + "</a>"));
                }
                if (placeHolder.contains("$$default")) {
                    placeHolder = placeHolder.replace("$$default", arrayList.get(i).getDefault_F());
                }
                if (placeHolder.contains("$$address")) {
                    String endaddr = arrayList.get(i).getEndAddress();
                    if ("".equals(endaddr)) {
                        placeHolder = placeHolder.replace("$$address", arrayList.get(i).getAddress());
                    } else {
                        placeHolder = placeHolder.replace("$$address", arrayList.get(i).getAddress() + " - " + endaddr);
                    }
                }
                if (arrayList.size() > i + 1) {
                    placeHolder = placeHolder.concat(strClone);
                }
            }
            String str = tocDoc.select("table").get(0).getElementById("replace").toString();
            str = str.replace(strClone, placeHolder);
            tocDoc.select("tr").get(2).remove();
            tocDoc.select("tr").get(1).after(str);
            tocStr = tocDoc.select("table").get(0).toString().concat("</br>");
            // tocDoc.remove();
        } catch (Exception ex) {
            jsonMsgStr("default", "Error Occure in Creating Toc : " + ex);
//            System.err.println("Error Occure in Creating Toc : " + ex);
        }
        return tocStr;
    }

    public static void save(File specFile, String docSrt) {

        String path = specFile.getAbsolutePath();
        File file = new File(path);

        try {
            FileWriter fstream = new FileWriter(file, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(docSrt);
            out.close();
        } catch (Exception ex) {
            jsonMsgStr("default", "Error occure in save the file " + ex);
//            System.err.println("Error occure in save the file " + ex);
        }
    }

    public static String jsonMsgStr(String id, String msg) {
        String str = null;
        try {
            msg = URLEncoder.encode("Error-B : " + msg);
            //msg = msg.replaceAll(":", "-");
            msg = msg.replaceAll(":", "-");
            if (comma == 0) {
                jsonStr = "{\n" + "	error: [";
                str = "\n{\n" + "    id:" + id + ",\n" + "   msg:" + msg + "}";
                comma = 1;
            } else {
                str = ",\n{\n" + "    id:" + id + ",\n" + "   msg:" + msg + "}";
            }
            {
                jsonStr = jsonStr.concat(str);
            }
        } catch (Exception ex) {
            jsonMsgStr("default", "Error occure in creating json msg String- :" + ex);
//            System.out.println("Error occure :" + ex);
        }
        return str;
    }
}
