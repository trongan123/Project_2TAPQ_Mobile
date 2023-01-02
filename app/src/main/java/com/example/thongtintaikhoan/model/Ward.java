package com.example.thongtintaikhoan.model;

public class Ward {
    String id_ward;
    String name;
    String type;
    String id_district;
    String status;

    public Ward(String id_ward, String name) {
        this.id_ward = id_ward;
        this.name = name;
    }

    public String getId_ward() {
        return id_ward;
    }

    public void setId_ward(String id_ward) {
        this.id_ward = id_ward;
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

    public String getId_district() {
        return id_district;
    }

    public void setId_district(String id_district) {
        this.id_district = id_district;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
