package com.example.thongtintaikhoan.model;

public class District {
    String id_district;
    String name;
    String type;
    String id_province;
    String status;


    public District(String id_district, String name) {
        this.id_district = id_district;
        this.name = name;
    }

    public String getId_district() {
        return id_district;
    }

    public void setId_district(String id_district) {
        this.id_district = id_district;
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

    public String getId_province() {
        return id_province;
    }

    public void setId_province(String id_province) {
        this.id_province = id_province;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
