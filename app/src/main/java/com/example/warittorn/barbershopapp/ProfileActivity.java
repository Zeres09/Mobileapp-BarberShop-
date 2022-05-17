package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    ISharedPref sharedPreferences;
    TextInputEditText itvname , itvemail , itvphone , itvpassword , itvconpass;
    Button conbt;
    String user_id;
    String url = "http://192.168.1.7/mobileApp/user.php";
    String result;
    SharedPreferences preferences3;
    SharedPreferences.Editor editor3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">Profile</font>"));
        itvname = findViewById(R.id.username);
        itvemail = findViewById(R.id.email);
        itvphone = findViewById(R.id.number);
        itvpassword = findViewById(R.id.password);
        itvconpass = findViewById(R.id.CPassword2);
        conbt = findViewById(R.id.confirm2);
        sharedPreferences = new SecureSharePref(this,"secrets1");
        itvemail.setText(sharedPreferences.get("email"));
        itvpassword.setText(sharedPreferences.get("password"));
        preferences3 = getSharedPreferences("secrets1", Context.MODE_PRIVATE);
        editor3 = preferences3.edit();
        getdata();
        conbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username,email,number,password,cpassword;
                username = String.valueOf(itvname.getText());
                email = String.valueOf(itvemail.getText());
                number = String.valueOf(itvphone.getText());
                password = String.valueOf(itvpassword.getText());
                cpassword = String.valueOf(itvconpass.getText());
                if(password.equals(cpassword)){
                    if(!username.equals("") && !email.equals("") && !number.equals("") && !password.equals("")){
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[5];
                                field[0] = "username";
                                field[1] = "email";
                                field[2] = "number";
                                field[3] = "password";
                                field[4] = "user_id";
                                String[] data = new String[5];
                                data[0] = username;
                                data[1] = email;
                                data[2] = number;
                                data[3] = password;
                                data[4] = user_id;
                                PutData putData = new PutData("http://192.168.1.7/mobileApp/editprofile.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if(result.equals("complete")){
                                            editor3.clear();
                                            sharedPreferences.put(field[1],email);
                                            sharedPreferences.put(field[3],password);
                                            sharedPreferences.put("status","user");
                                            Toast.makeText(getApplicationContext(),"แก้ไขโปรไฟล์เสร้จสิ้น",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
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
                }else{
                    Toast.makeText(getApplicationContext(),"รหัสผ่านไม่ตรงกัน",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void mainpage(View v){
        finish();
    }
    public void getdata(){
        String[] field = new String[1];
        field[0] = "email";
        String[] data = new String[1];
        data[0] = sharedPreferences.get(field[0]);
        PutData putData = new PutData(url, "POST", field, data);
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
                itvphone.setText(jsObj.get("phone").toString());
                itvname.setText(jsObj.get("username").toString());
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}