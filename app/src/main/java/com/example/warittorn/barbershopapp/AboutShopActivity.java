package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AboutShopActivity extends AppCompatActivity {
    TextView nametv,ownertv,emailtv,contacttv,timetv,addresstv,statustv;
    ImageView Imagev;
    String name,owner,email,contact,start,end,address,status,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_shop);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">About This Shop</font>"));
        name = getIntent().getStringExtra("NAME");
        owner = getIntent().getStringExtra("OWNER");
        email = getIntent().getStringExtra("EMAIL");
        contact = getIntent().getStringExtra("CONTACT");
        start = getIntent().getStringExtra("START");
        end = getIntent().getStringExtra("END");
        address = getIntent().getStringExtra("ADDRESS");
        status = getIntent().getStringExtra("STATUS");
        url = "http://192.168.1.7/mobileApp/img/"+name+".jpg";
        nametv = findViewById(R.id.shopname);
        ownertv = findViewById(R.id.shopowner);
        emailtv = findViewById(R.id.shopemail);
        contacttv = findViewById(R.id.shopcontact);
        timetv = findViewById(R.id.shopopen);
        addresstv = findViewById(R.id.shopaddress);
        statustv = findViewById(R.id.status2);
        Imagev = findViewById(R.id.shopImage);
        nametv.setText(name);
        ownertv.setText(owner);
        emailtv.setText(email);
        contacttv.setText(contact);
        timetv.setText(start+" : "+end);
        addresstv.setText(address);
        statustv.setText(status);
        if(status.equals("Open")){
            statustv.setTextColor(Color.parseColor("#0eab32"));
        }else if(status.equals("Close")){
            statustv.setTextColor(Color.parseColor("#FF0000"));
        }
        loadImage(url);
    }

    private void loadImage(String url) {
        Picasso.get()
                .load(url)
                .placeholder(R.mipmap.ic_launcher).fit()
                .error(R.mipmap.ic_launcher)
                .into(Imagev);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main3,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Handle item selection

        switch (item.getItemId()) {
            case R.id.booking:
                Intent itn = new Intent(this,ServiceActivity.class);
                itn.putExtra("NAME",name);
                itn.putExtra("EMAIL",email);
                itn.putExtra("CONTACT",contact);
                itn.putExtra("START",start);
                itn.putExtra("END",end);
                itn.putExtra("ADDRESS",address);
                startActivity(itn);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}