package com.example.thongtintaikhoan.model;

public class History_store_house {
    String id_history_store_house;
    String id_staff;
    String id_s_house;
    String name;
    int quanlity;
    String id_item_category;
    String note;
    String date;
    int status;

    public History_store_house(String name, int quanlity, String note, String date) {
        this.name = name;
        this.quanlity = quanlity;
        this.note = note;
        this.date = date;
    }

    public String getId_history_store_house() {
        return id_history_store_house;
    }

    public void setId_history_store_house(String id_history_store_house) {
        this.id_history_store_house = id_history_store_house;
    }

    public String getId_staff() {
        return id_staff;
    }

    public void setId_staff(String id_staff) {
        this.id_staff = id_staff;
    }

    public String getId_s_house() {
        return id_s_house;
    }

    public void setId_s_house(String id_s_house) {
        this.id_s_house = id_s_house;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public String getId_item_category() {
        return id_item_category;
    }

    public void setId_item_category(String id_item_category) {
        this.id_item_category = id_item_category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
