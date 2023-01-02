package com.example.thongtintaikhoan.model;

public class Item_store_house {
    String id_item_store_house;
    String id_s_house;
    String name;
    String quanlity;
    String note;

    public Item_store_house(String id_item_store_house,String name, String quanlity, String note) {
        this.id_item_store_house = id_item_store_house;
        this.name = name;
        this.quanlity = quanlity;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(String quanlity) {
        this.quanlity = quanlity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId_item_store_house() {
        return id_item_store_house;
    }

    public void setId_item_store_house(String id_item_store_house) {
        this.id_item_store_house = id_item_store_house;
    }
}
