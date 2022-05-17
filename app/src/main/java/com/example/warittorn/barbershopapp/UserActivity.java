package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {
    SharedPreferences preferences3;
    SharedPreferences.Editor editor3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">"+getString(R.string.app_name)+"</font>"));
        preferences3 = getSharedPreferences("secrets1", Context.MODE_PRIVATE);
        editor3 = preferences3.edit();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main2,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Handle item selection

        switch (item.getItemId()) {
            case R.id.logout :
                Toast.makeText(this,"ออกจากระบบเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                Intent itn = new Intent(this,LoginPageActivity.class);
                startActivity(itn);
                editor3.clear();
                editor3.commit();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void profile(View v){
        Intent itn = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(itn);
    }
    public  void history(View v){
        Intent itn = new Intent(getApplicationContext(),HistoryActivity.class);
        startActivity(itn);
    }
    public  void  search(View v){
        Intent itn = new Intent(getApplicationContext(),SearchActivity.class);
        startActivity(itn);
    }
}