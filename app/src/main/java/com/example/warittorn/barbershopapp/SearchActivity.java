package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
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
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements RecyclerViewInterface {
    private SearchView searchView ;
    private List<SearchDataModel> datas = new ArrayList<>();
    private RequestQueue mQueue;
    private TextView STtv;
    private SearchAdapter adapter ;
    String url = "http://192.168.1.7/mobileapp/search.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">Search BarberShop</font>"));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson =new Gson();
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("shop");
                    for(int i = 0;i < arr.length();i++){
                        JSONObject jsObj = arr.getJSONObject(i);
                        SearchDataModel s = gson.fromJson(String.valueOf(jsObj), SearchDataModel.class);
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
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

    }
    public void displayListview(){
        adapter = new SearchAdapter(this,datas,this);
        RecyclerView Rv = findViewById(R.id.showShop);
        Rv.setAdapter(adapter);
        Rv.setLayoutManager(new LinearLayoutManager(this));

    }
    private void filterList(String newtext){
        List<SearchDataModel> filteredList = new ArrayList<>();
        for(SearchDataModel item : datas){
            if(item.getS_name().toLowerCase().contains(newtext.toLowerCase())){
                filteredList.add(item);
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show();
            }else{
                adapter.setFilteredList(filteredList);
            }
        }
    }

    @Override
    public void OnitemCilck(int position) {
        Intent itn = new Intent(SearchActivity.this,AboutShopActivity.class);
        itn.putExtra("ID",datas.get(position).getId());
        itn.putExtra("NAME",datas.get(position).getS_name());
        itn.putExtra("OWNER",datas.get(position).getS_owner());
        itn.putExtra("EMAIL",datas.get(position).getS_email());
        itn.putExtra("CONTACT",datas.get(position).getS_contact());
        itn.putExtra("START",datas.get(position).getS_start());
        itn.putExtra("END",datas.get(position).getS_end());
        itn.putExtra("ADDRESS",datas.get(position).getS_address());
        itn.putExtra("STATUS",datas.get(position).getS_status());
        startActivity(itn);
    }
}