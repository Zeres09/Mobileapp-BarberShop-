package com.example.warittorn.barbershopapp;

public class UserDataModel {
    private int id;
    private String username;
    private String phone;
    public UserDataModel() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
