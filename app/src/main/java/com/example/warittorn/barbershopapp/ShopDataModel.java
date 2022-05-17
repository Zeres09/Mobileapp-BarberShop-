package com.example.warittorn.barbershopapp;

public class ShopDataModel {
    private int id;
    private String username,phone,time,service,order_id;
    public ShopDataModel() {
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
