package com.example.thongtintaikhoan.model;

public class Item_category {
    String id_item_category;
    String name;
    int status;

    public Item_category(String name) {
        this.name = name;
    }

    public String getId_item_category() {
        return id_item_category;
    }

    public void setId_item_category(String id_item_category) {
        this.id_item_category = id_item_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
