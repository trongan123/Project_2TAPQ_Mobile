package com.example.thongtintaikhoan.model;

public class Pond {

    String id_pond;
    String id_acc;
    String id_fcategory;
    String name;
    String pond_area;
    private byte[] image;
    String session;
    String quantity_of_fingerlings;
    String quanlity_of_end;
    String star_day;
    String end_day;
    String status;
    String category_name;



    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Pond(String star_day, String end_day) {
        this.star_day = star_day;
        this.end_day = end_day;
    }
    public Pond(){

    }

    public Pond(String id_pond, String id_acc, String name, String pond_area, byte[] image) {
        this.id_pond = id_pond;
        this.id_acc = id_acc;
        this.name = name;
        this.pond_area = pond_area;
        this.image = image;
    }

    public Pond(String id_pond,String id_acc,String name, String pond_area, String session, String quantity_of_fingerlings,byte[] image) {
        this.id_pond = id_pond;
        this.id_acc = id_acc;
        this.name = name;
        this.pond_area = pond_area;
        this.session = session;
        this.quantity_of_fingerlings = quantity_of_fingerlings;
        this.image = image;
    }
    public Pond(String id_pond,String id_acc,String name, String pond_area, String session, String quanlity_of_end,String star_day, String end_day,byte[] image) {
        this.id_pond = id_pond;
        this.id_acc = id_acc;
        this.name = name;
        this.pond_area = pond_area;
        this.session = session;
        this.quanlity_of_end = quanlity_of_end;
        this.star_day = star_day;
        this.end_day = end_day;
        this.image = image;
    }

    public String getId_pond() {
        return id_pond;
    }

    public void setId_pond(String id_pond) {
        this.id_pond = id_pond;
    }

    public String getId_acc() {
        return id_acc;
    }

    public void setId_acc(String id_acc) {
        this.id_acc = id_acc;
    }

    public String getId_fcategory() {
        return id_fcategory;
    }

    public void setId_fcategory(String id_fcategory) {
        this.id_fcategory = id_fcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPond_area() {
        return pond_area;
    }

    public void setPond_area(String pond_area) {
        this.pond_area = pond_area;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getQuantity_of_fingerlings() {
        return quantity_of_fingerlings;
    }

    public void setQuantity_of_fingerlings(String quantity_of_fingerlings) {
        this.quantity_of_fingerlings = quantity_of_fingerlings;
    }

    public String getQuanlity_of_end() {
        return quanlity_of_end;
    }

    public void setQuanlity_of_end(String quanlity_of_end) {
        this.quanlity_of_end = quanlity_of_end;
    }

    public String getStar_day() {
        return star_day;
    }

    public void setStar_day(String star_day) {
        this.star_day = star_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
