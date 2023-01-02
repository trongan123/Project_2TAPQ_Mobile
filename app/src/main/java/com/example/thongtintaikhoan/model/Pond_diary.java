package com.example.thongtintaikhoan.model;

public class Pond_diary {
    String id_diary;
    String id_pond;
    double sanility;
    double ph;
    double temperature;
    double water_level;
    int fish_status;
    String date;

    public Pond_diary(String id_diary,double sanility, double ph, double temperature, double water_level, int fish_status, String date) {
        this.id_diary = id_diary;
        this.sanility = sanility;
        this.ph = ph;
        this.temperature = temperature;
        this.water_level = water_level;
        this.fish_status = fish_status;
        this.date = date;
    }

    public Pond_diary(String id_diary, double sanility, double ph, double temperature, double water_level) {
        this.id_diary = id_diary;
        this.sanility = sanility;
        this.ph = ph;
        this.temperature = temperature;
        this.water_level = water_level;
    }

    public Pond_diary(double ph, double temperature) {

        this.ph = ph;
        this.temperature = temperature;
    }

    public String getId_diary() {
        return id_diary;
    }

    public void setId_diary(String id_diary) {
        this.id_diary = id_diary;
    }

    public String getId_pond() {
        return id_pond;
    }

    public void setId_pond(String id_pond) {
        this.id_pond = id_pond;
    }

    public double getSanility() {
        return sanility;
    }

    public void setSanility(double sanility) {
        this.sanility = sanility;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWater_level() {
        return water_level;
    }

    public void setWater_level(double water_level) {
        this.water_level = water_level;
    }

    public int getFish_status() {
        return fish_status;
    }

    public void setFish_status(int fish_status) {
        this.fish_status = fish_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
