package com.example.warittorn.barbershopapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginPageActivity extends AppCompatActivity {
    TextInputEditText textInputEditTextEmail,textInputEditTextPassword;
    String Status;
    ISharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button buttonLogin;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">" + getString(R.string.app_name) + "</font>"));
        TextView tv = findViewById(R.id.textView);
        tv.setTextSize(24);
        sharedPref = new SecureSharePref(this,"secrets1");
        textInputEditTextEmail = findViewById(R.id.Email1);
        textInputEditTextPassword = findViewById(R.id.Pass1);
        autologin();
        buttonLogin = findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email,password;
                    email = String.valueOf(textInputEditTextEmail.getText());
                    password = String.valueOf(textInputEditTextPassword.getText());
                    if(!email.equals("") && !password.equals("")) {
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[2];
                                field[0] = "email";
                                field[1] = "password";
                                String[] data = new String[2];
                                data[0] = email;
                                data[1] = password;
                                PutData putData = new PutData("http://192.168.1.7/mobileApp/login.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("user")) {
                                            sharedPref.put(field[0],email);
                                            sharedPref.put(field[1],password);
                                            sharedPref.put("status",result);
                                            Toast.makeText(getApplicationContext(),"เข้าสู่ระบบเรียบร้อย", Toast.LENGTH_SHORT).show();
                                            Intent itn = new Intent(getApplicationContext(),UserActivity.class);
                                            startActivity(itn);
                                            finish();
                                        }else if(result.equals("shop")){
                                            sharedPref.put(field[0],email);
                                            sharedPref.put(field[1],password);
                                            sharedPref.put("status",result);
                                            Toast.makeText(getApplicationContext(), "เข้าสู่ระบบเรียบร้อย", Toast.LENGTH_SHORT).show();
                                            Intent itn = new Intent(getApplicationContext(),ShopActivity.class);
                                            startActivity(itn);
                                            finish();
                                        }else if(result.equals("ADMIN")){
                                            Intent itn = new Intent(getApplicationContext(),RegisterShopActivity.class);
                                            startActivity(itn);
                                            finish();
                                        }
                                        //End ProgressBar (Set visibility to GONE)
                                        Log.i("PutData", result);
                                    }
                                }
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
    public void autologin(){
        System.out.println(sharedPref.get("email"));
        if(!(sharedPref.get("email").isEmpty())){
            Status = sharedPref.get("status");
            if(Status.equals("user")){
                Intent itn = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(itn);
                finish();
            }else if(Status.equals("shop")){
                Intent itn = new Intent(getApplicationContext(),ShopActivity.class);
                startActivity(itn);
                finish();
            }
        }
    }
    public void registerpage(View v){
        Intent itn = new Intent(this,RegisterActivity.class);
        startActivity(itn);
    }
}