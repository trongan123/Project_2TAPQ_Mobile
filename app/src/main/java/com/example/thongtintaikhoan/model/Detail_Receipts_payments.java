package com.example.thongtintaikhoan.model;

public class Detail_Receipts_payments {
    String id_detail_receipt_payment;
    String id_invoice;
    String namestaff;
    int  quanlity;
    int price;
    String name;
    String description;
    int status;
    String date;

    public Detail_Receipts_payments(String id_detail_receipt_payment, String namestaff, int quanlity, int price, String name, String date) {
        this.id_detail_receipt_payment = id_detail_receipt_payment;
        this.namestaff = namestaff;
        this.quanlity = quanlity;
        this.price = price;
        this.name = name;
        this.date = date;
    }
    public Detail_Receipts_payments(){

    }


    public String getId_detail_receipt_payment() {
        return id_detail_receipt_payment;
    }

    public void setId_detail_receipt_payment(String id_detail_receipt_payment) {
        this.id_detail_receipt_payment = id_detail_receipt_payment;
    }

    public String getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(String id_invoice) {
        this.id_invoice = id_invoice;
    }

    public String getNamestaff() {
        return namestaff;
    }

    public void setNamestaff(String namestaff) {
        this.namestaff = namestaff;
    }

    public int getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
