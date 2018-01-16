/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.controllers.config.properties;

/**
 *
 * @author Sumeet
 */
public class Outputs {

    private static Outputs outputs;

    public static Outputs getOutputsIns() {
        if (outputs == null) {
            outputs = new Outputs();
        }
        return outputs;
    }

    String outputdir = "";
    boolean verilog = false;
    boolean verilog1995 = false;
    boolean verilog2001 = false;
    boolean rtlwire = false;
    boolean vhdl = false;
    boolean vhdlalt1 = false;
    boolean vhdlalt2 = false;
    boolean vhdlmultioutfile = false;
    boolean systemverilog = false;
    boolean systemc = false;
    boolean systemcalt2 = false;
    boolean uvm = false;
    boolean uvmmultioutfile = false;
    boolean ovm = false;
    boolean vmm = false;
    boolean erm = false;
    boolean ivsexcel = false;
    boolean arvsim = false;
    boolean arvformal = false;
    boolean c = false;
    boolean calt1 = false;
    boolean calt2 = false;
    boolean cmisrac = false;
    boolean cmultiout = false;
    boolean svheader = false;
    boolean csharp = false;
    boolean perl = false;
    boolean python = false;
    boolean cpp = false;
    boolean vheader = false;
    boolean vhdheader = false;
    boolean html = false;
    boolean htmlalt1 = false;
    boolean htmlalt2 = false;
    boolean pdf = false;
    boolean xml = true;
    boolean svg = false;
    boolean datasheet = false;
    boolean customcsv = false;
    boolean customxml = false;
    boolean ipxact = false;
    boolean ipxactv15 = false;
    boolean ipxactv14 = false;
    boolean systemrdl = false;
    boolean cmsissvd = false;
    boolean firmwawre = false;
    boolean uvmiss = false;

    public String getOutputdir() {
        return outputdir;
    }

    public boolean isCmultiout() {
        return cmultiout;
    }

    public void setCmultiout(boolean cmultiout) {
        this.cmultiout = cmultiout;
    }

    public void setOutputdir(String outputdir) {
        this.outputdir = outputdir;
    }

    public boolean isVerilog() {
        return verilog;
    }

    public void setVerilog(boolean verilog) {
        this.verilog = verilog;
    }

    public boolean isVerilog1995() {
        return verilog1995;
    }

    public void setVerilog1995(boolean verilog1995) {
        this.verilog1995 = verilog1995;
    }

    public boolean isVerilog2001() {
        return verilog2001;
    }

    public void setVerilog2001(boolean verilog2001) {
        this.verilog2001 = verilog2001;
    }

    public boolean isRtlwire() {
        return rtlwire;
    }

    public void setRtlwire(boolean rtlwire) {
        this.rtlwire = rtlwire;
    }

    public boolean isVhdl() {
        return vhdl;
    }

    public void setVhdl(boolean vhdl) {
        this.vhdl = vhdl;
    }

    public boolean isVhdlalt1() {
        return vhdlalt1;
    }

    public void setVhdlalt1(boolean vhdlalt1) {
        this.vhdlalt1 = vhdlalt1;
    }

    public boolean isVhdlalt2() {
        return vhdlalt2;
    }

    public void setVhdlalt2(boolean vhdlalt2) {
        this.vhdlalt2 = vhdlalt2;
    }

    public boolean isVhdlmultioutfile() {
        return vhdlmultioutfile;
    }

    public void setVhdlmultioutfile(boolean vhdlmultioutfile) {
        this.vhdlmultioutfile = vhdlmultioutfile;
    }

    public boolean isSystemverilog() {
        return systemverilog;
    }

    public void setSystemverilog(boolean systemverilog) {
        this.systemverilog = systemverilog;
    }

    public boolean isSystemcalt2() {
        return systemcalt2;
    }

    public void setSystemcalt2(boolean systemcalt2) {
        this.systemcalt2 = systemcalt2;
    }

    public boolean isSystemc() {
        return systemc;
    }

    public void setSystemc(boolean systemc) {
        this.systemc = systemc;
    }

    public boolean isUvm() {
        return uvm;
    }

    public void setUvm(boolean uvm) {
        this.uvm = uvm;
    }

    public boolean isUvmmultioutfile() {
        return uvmmultioutfile;
    }

    public void setUvmmultioutfile(boolean uvmmultioutfile) {
        this.uvmmultioutfile = uvmmultioutfile;
    }

    public boolean isOvm() {
        return ovm;
    }

    public void setOvm(boolean ovm) {
        this.ovm = ovm;
    }

    public boolean isVmm() {
        return vmm;
    }

    public void setVmm(boolean vmm) {
        this.vmm = vmm;
    }

    public boolean isErm() {
        return erm;
    }

    public void setErm(boolean erm) {
        this.erm = erm;
    }

    public boolean isIvsexcel() {
        return ivsexcel;
    }

    public void setIvsexcel(boolean ivsexcel) {
        this.ivsexcel = ivsexcel;
    }

    public boolean isArvsim() {
        return arvsim;
    }

    public void setArvsim(boolean arvsim) {
        this.arvsim = arvsim;
    }

    public boolean isArvformal() {
        return arvformal;
    }

    public void setArvformal(boolean arvformal) {
        this.arvformal = arvformal;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public boolean isCalt1() {
        return calt1;
    }

    public void setCalt1(boolean calt1) {
        this.calt1 = calt1;
    }

    public boolean isCalt2() {
        return calt2;
    }

    public void setCalt2(boolean calt2) {
        this.calt2 = calt2;
    }

    public boolean isCmisrac() {
        return cmisrac;
    }

    public void setCmisrac(boolean cmisrac) {
        this.cmisrac = cmisrac;
    }

    public boolean isSvheader() {
        return svheader;
    }

    public void setSvheader(boolean svheader) {
        this.svheader = svheader;
    }

    public boolean isCsharp() {
        return csharp;
    }

    public void setCsharp(boolean csharp) {
        this.csharp = csharp;
    }

    public boolean isPerl() {
        return perl;
    }

    public void setPerl(boolean perl) {
        this.perl = perl;
    }

    public boolean isPython() {
        return python;
    }

    public void setPython(boolean python) {
        this.python = python;
    }

    public boolean isCpp() {
        return cpp;
    }

    public void setCpp(boolean cpp) {
        this.cpp = cpp;
    }

    public boolean isVheader() {
        return vheader;
    }

    public void setVheader(boolean vheader) {
        this.vheader = vheader;
    }

    public boolean isVhdheader() {
        return vhdheader;
    }

    public void setVhdheader(boolean vhdheader) {
        this.vhdheader = vhdheader;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public boolean isHtmlalt1() {
        return htmlalt1;
    }

    public void setHtmlalt1(boolean htmlalt1) {
        this.htmlalt1 = htmlalt1;
    }

    public boolean isHtmlalt2() {
        return htmlalt2;
    }

    public void setHtmlalt2(boolean htmlalt2) {
        this.htmlalt2 = htmlalt2;
    }

    public boolean isPdf() {
        return pdf;
    }

    public void setPdf(boolean pdf) {
        this.pdf = pdf;
    }

    public boolean isXml() {
        return xml;
    }

    public void setXml(boolean xml) {
        this.xml = xml;
    }

    public boolean isSvg() {
        return svg;
    }

    public void setSvg(boolean svg) {
        this.svg = svg;
    }

    public boolean isDatasheet() {
        return datasheet;
    }

    public void setDatasheet(boolean datasheet) {
        this.datasheet = datasheet;
    }

    public boolean isCustomcsv() {
        return customcsv;
    }

    public void setCustomcsv(boolean customcsv) {
        this.customcsv = customcsv;
    }

    public boolean isCustomxml() {
        return customxml;
    }

    public void setCustomxml(boolean customxml) {
        this.customxml = customxml;
    }

    public boolean isIpxact() {
        return ipxact;
    }

    public void setIpxact(boolean ipxact) {
        this.ipxact = ipxact;
    }

    public boolean isIpxactv15() {
        return ipxactv15;
    }

    public void setIpxactv15(boolean ipxactv15) {
        this.ipxactv15 = ipxactv15;
    }

    public boolean isIpxactv14() {
        return ipxactv14;
    }

    public void setIpxactv14(boolean ipxactv14) {
        this.ipxactv14 = ipxactv14;
    }

    public boolean isSystemrdl() {
        return systemrdl;
    }

    public void setSystemrdl(boolean systemrdl) {
        this.systemrdl = systemrdl;
    }

    public boolean isCmsissvd() {
        return cmsissvd;
    }

    public void setCmsissvd(boolean cmsissvd) {
        this.cmsissvd = cmsissvd;
    }

    public boolean isFirmwawre() {
        return firmwawre;
    }

    public void setFirmwawre(boolean firmwawre) {
        this.firmwawre = firmwawre;
    }

    public boolean isSeqUVM() {
        return uvmiss;
    }

    public void setSeqUVM(boolean uvmiss) {
        this.uvmiss = uvmiss;
    }

}
