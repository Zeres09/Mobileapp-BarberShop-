package com.example.warittorn.barbershopapp;

public class HistoryDataModel {
    private int id;
    private String shopname;
    private String time;
    private String date;
    private String service;
    private String status;

    public HistoryDataModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return shopname;
    }

    public void setName(String shopname) {
        this.shopname = shopname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
