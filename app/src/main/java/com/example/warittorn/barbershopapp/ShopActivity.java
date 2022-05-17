package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ShopActivity extends AppCompatActivity {
    ISharedPref sharedPreferences;
    SharedPreferences preferences2;
    SharedPreferences.Editor editor2;
    ListView lv;
    private List<ShopDataModel> datas = new ArrayList<>();
    private RequestQueue mQueue;
    String result,username,order_id;
    String url1 = "http://192.168.1.7/mobileApp/shop.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">"+getString(R.string.app_name)+"</font>"));
        preferences2 = getSharedPreferences("secrets1", Context.MODE_PRIVATE);
        sharedPreferences = new SecureSharePref(this,"secrets1");
        editor2 = preferences2.edit();
        getdata();
        String url = "http://192.168.1.7/mobileapp/shopbook.php?shopname="+username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson =new Gson();
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("shopbooking");
                    for(int i = 0;i < arr.length();i++){
                        JSONObject jsObj = arr.getJSONObject(i);
                        ShopDataModel s =gson.fromJson(String.valueOf(jsObj), ShopDataModel.class);
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
        Button ref;
        ref = findViewById(R.id.refresh);
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Handle item selection

        switch (item.getItemId()) {
            case R.id.logout :
                Toast.makeText(this,"ออกจากระบบเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                Intent itn = new Intent(this,LoginPageActivity.class);
                startActivity(itn);
                editor2.clear();
                editor2.commit();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void displayListview(){
        ShopAdapter adapter = new ShopAdapter(this,datas);
        lv = findViewById(R.id.shoplist);
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
            JSONArray arr = obj.getJSONArray("shop");
            for(int i = 0;i < arr.length();i++){
                JSONObject jsObj = arr.getJSONObject(i);
                username = jsObj.get("s_name").toString();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}