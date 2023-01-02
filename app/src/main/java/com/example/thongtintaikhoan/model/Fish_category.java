package com.example.thongtintaikhoan.model;

public class Fish_category {
    String id_fcategory;
    String category_name;

    public Fish_category(String id_fcategory, String category_name) {
        this.id_fcategory = id_fcategory;
        this.category_name = category_name;
    }

    public Fish_category(){

    }
    public String getId_fcategory() {
        return id_fcategory;
    }

    public void setId_fcategory(String id_fcategory) {
        this.id_fcategory = id_fcategory;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
