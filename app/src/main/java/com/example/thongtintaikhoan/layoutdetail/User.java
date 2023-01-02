package com.example.thongtintaikhoan.layoutdetail;

import java.io.Serializable;

public class User implements Serializable {
    private int resourceId;
    private String name;
    private String address;
    private String ten;

    public User(int resourceId, String name, String address, String ten) {
        this.resourceId = resourceId;
        this.name = name;
        this.address = address;
        this.ten = ten;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
