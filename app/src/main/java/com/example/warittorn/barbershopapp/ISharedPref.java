package com.example.warittorn.barbershopapp;

public interface ISharedPref {
    String get(String name);
    void put(String name,String value);
}
