package com.example.warittorn.barbershopapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref implements ISharedPref{
    private final SharedPreferences sharedPreferences;

    public SharedPref(Context context,String name) {
        sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    @Override
    public String get(String name) {
        return sharedPreferences.getString(name,"null");
    }

    @Override
    public void put(String name, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name,value);
        editor.apply();
    }
}
