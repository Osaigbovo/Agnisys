/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.backannotation;

import java.util.HashMap;

/**
 *
 * @author Himanshu yadav
 */
public class AnnotationHeader {

    private String heading;
    private String name;
    private String address;
    private String endAddress;
    private String default_F;
    private String allAddresses;
    private String tid;
    private String msg;
    HashMap<String, String> data = new HashMap<String, String>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAllAddresses() {
        return allAddresses;
    }

    public void setAllAddresses(String allAddresses) {
        this.allAddresses = allAddresses;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDefault_F(String default_F) {
        this.default_F = default_F;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getHeading() {
        return heading;
    }

    public String getAddress() {
        return address;
    }

    public String getDefault_F() {
        return default_F;
    }

    public String getTid() {
        return tid;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

}
