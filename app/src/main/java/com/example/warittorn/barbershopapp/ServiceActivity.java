package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.DataFormatException;

public class ServiceActivity extends AppCompatActivity implements RecyclerViewInterface {
    private List<ServiceDataModel> datas = new ArrayList<>();
    private RequestQueue mQueue;
    private ServiceAdapter adapter ;
    String url = "http://192.168.1.7/mobileapp/service.php";
    String name,email,contact,start,end,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">Services Offered</font>"));
        name = getIntent().getStringExtra("NAME");
        email = getIntent().getStringExtra("EMAIL");
        contact = getIntent().getStringExtra("CONTACT");
        start = getIntent().getStringExtra("START");
        end = getIntent().getStringExtra("END");
        address = getIntent().getStringExtra("ADDRESS");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson =new Gson();
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("service");
                    for(int i = 0;i < arr.length();i++){
                        JSONObject jsObj = arr.getJSONObject(i);
                        ServiceDataModel s = gson.fromJson(String.valueOf(jsObj), ServiceDataModel.class);
                        datas.add(s);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                if (datas.size() > 0){
                    displayListview();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(stringRequest);
    }


    public void displayListview(){
        adapter = new ServiceAdapter(this,datas,this);
        RecyclerView Rv = findViewById(R.id.showshop2);
        Rv.setAdapter(adapter);
        Rv.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public void OnitemCilck(int position) {
        Intent itn = new Intent(this,BookingActivity.class);
        itn.putExtra("NAME",name);
        itn.putExtra("EMAIL",email);
        itn.putExtra("CONTACT",contact);
        itn.putExtra("START",start);
        itn.putExtra("END",end);
        itn.putExtra("SERVICE",datas.get(position).getSer_name());
        itn.putExtra("PRICE",datas.get(position).getSer_price());
        startActivity(itn);
    }
}