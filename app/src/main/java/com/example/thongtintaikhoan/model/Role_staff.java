package com.example.thongtintaikhoan.model;

public class Role_staff {
    String id_role_staff;
    String id_acc;
    String id_role;
    int salary;
    int status;

    public Role_staff(String id_role_staff, String id_acc, String id_role, int salary) {
        this.id_role_staff = id_role_staff;
        this.id_acc = id_acc;
        this.id_role = id_role;
        this.salary = salary;
    }
    public Role_staff(String id_role_staff, int salary) {
        this.id_role_staff = id_role_staff;
        this.salary = salary;
    }

    public String getId_role_staff() {
        return id_role_staff;
    }

    public void setId_role_staff(String id_role_staff) {
        this.id_role_staff = id_role_staff;
    }

    public String getId_acc() {
        return id_acc;
    }

    public void setId_acc(String id_acc) {
        this.id_acc = id_acc;
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
