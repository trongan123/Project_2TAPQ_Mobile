package com.example.thongtintaikhoan.model;

public class Notify {
    String id_notify;
    String id_acc;
    String type;
    String description;
    String date;
    int status;


    public Notify(String type, String description, String date) {
        this.type = type;
        this.description = description;
        this.date = date;
    }

    public Notify(int status) {
        this.status = status;
    }

    public String getId_notify() {
        return id_notify;
    }

    public void setId_notify(String id_notify) {
        this.id_notify = id_notify;
    }

    public String getId_acc() {
        return id_acc;
    }

    public void setId_acc(String id_acc) {
        this.id_acc = id_acc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
