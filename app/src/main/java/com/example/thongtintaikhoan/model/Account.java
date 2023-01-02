package com.example.thongtintaikhoan.model;

//import com.google.type.DateTime;

import java.util.Date;

public class Account {
    String ID_acc;
    String Username;
    String Password;
    String Fullname;
    String Email;
    String Phone;
    String Birthday;
    String Address;
    int Role;
    String ID_Role_Staff;
    int ID_ward;
    String Date_join;
    int status;
    private byte[] image;

    public Account(String ID_acc,String username){
        this.Username = username;
        this.ID_acc = ID_acc;
    }

    public Account(String username, String email, String phone, String ID_Role_Staff) {
        Username = username;
        Email = email;
        Phone = phone;
        this.ID_Role_Staff = ID_Role_Staff;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public Account(String username, String fullname, String email, String phone, String address, byte[] image) {
        Username = username;
        Fullname = fullname;
        Email = email;
        Phone = phone;
        Address = address;
        this.image = image;
    }

    public Account(String ID_acc, String username, String password, String fullname, String email, String phone, String birthday, String address) {
        this.ID_acc = ID_acc;
        Username = username;
        Password = password;
        Fullname = fullname;
        Email = email;
        Phone = phone;
        Birthday = birthday;
        Address = address;
    }

    public Account(String username, String fullname, String email, String phone, String address) {
        Username = username;
        Fullname = fullname;
        Email = email;
        Phone = phone;
        Address = address;
    }

    public Account(String username) {
        Username = username;
    }

    public String getID_acc() {
        return ID_acc;
    }

    public void setID_acc(String ID_acc) {
        this.ID_acc = ID_acc;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public String getID_Role_Staff() {
        return ID_Role_Staff;
    }

    public void setID_Role_Staff(String ID_Role_Staff) {
        this.ID_Role_Staff = ID_Role_Staff;
    }

    public int getID_ward() {
        return ID_ward;
    }

    public void setID_ward(int ID_ward) {
        this.ID_ward = ID_ward;
    }

    public String getDate_join() {
        return Date_join;
    }

    public void setDate_join(String date_join) {
        Date_join = date_join;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
