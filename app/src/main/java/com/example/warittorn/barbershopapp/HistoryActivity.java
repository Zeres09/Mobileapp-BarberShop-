package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ISharedPref sharedPreferences;
    private List<HistoryDataModel> datas = new ArrayList<>();
    private RequestQueue mQueue;
    String result,user_id;
    String url1 = "http://192.168.1.7/mobileApp/user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">History</font>"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        sharedPreferences = new SecureSharePref(this,"secrets1");
        getdata();
        String url = "http://192.168.1.7/mobileapp/history.php?user_id="+user_id;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson =new Gson();
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("history");
                    for(int i = 0;i < arr.length();i++){
                        JSONObject jsObj = arr.getJSONObject(i);
                        HistoryDataModel d =gson.fromJson(String.valueOf(jsObj), HistoryDataModel.class);
                        datas.add(d);
                    }
                    System.out.println(arr);
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
        HIstoryAdapter adapter = new HIstoryAdapter(this,datas);
        ListView lv = findViewById(R.id.historyview);
        lv.setAdapter(adapter);
    }
    public void getdata(){
        String[] field = new String[1];
        field[0] = "email";
        String[] data = new String[1];
        data[0] = sharedPreferences.get(field[0]);
        PutData putData = new PutData(url1, "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                result = putData.getResult();
                System.out.println("PutData "+result);
            }
        }
        try {
            JSONObject obj = new JSONObject(result);
            JSONArray arr = obj.getJSONArray("user");
            for(int i = 0;i < arr.length();i++){
                JSONObject jsObj = arr.getJSONObject(i);
                user_id = jsObj.get("user_id").toString();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}