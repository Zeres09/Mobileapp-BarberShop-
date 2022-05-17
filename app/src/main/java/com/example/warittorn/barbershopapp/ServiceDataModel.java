package com.example.warittorn.barbershopapp;

public class ServiceDataModel {
    private int id;
    private String ser_name;
    private String ser_detail;
    private String ser_price;

    public ServiceDataModel() {
    }
//    private String img;

//    public DataModel(int id, String title, String description, String img) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.img = img;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSer_name() {
        return ser_name;
    }

    public void setSer_name(String ser_name) {
        this.ser_name = ser_name;
    }

    public String getSer_detail() {
        return ser_detail;
    }

    public void setSer_detail(String ser_detail) {
        this.ser_detail = ser_detail;
    }

    public String getSer_price() {
        return ser_price;
    }

    public void setSer_price(String ser_price) {
        this.ser_price = ser_price;
    }
}
