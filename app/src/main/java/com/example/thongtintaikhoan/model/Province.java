package com.example.thongtintaikhoan.model;

public class Province {
     String id_province;
     String name;
     String type;
     String status;

    public Province(String id_province, String name, String type) {
        this.id_province = id_province;
        this.name = name;
        this.type = type;
    }

    public Province(String id_province, String name) {
        this.id_province = id_province;
        this.name = name;
    }

    public String getId_province() {
        return id_province;
    }

    public void setId_province(String id_province) {
        this.id_province = id_province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
