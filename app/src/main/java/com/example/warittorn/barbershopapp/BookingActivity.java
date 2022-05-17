package com.example.warittorn.barbershopapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    ISharedPref sharedPreferences;
    public int hour,minute,year,month,day;
    TextView timeTv,dateTv,shopbooktv,contacttv,shopsertv,serpricetv;
    Button CFbt;
    String name,email,contact,start,end,price,ser_name,user_name,user_phone;
    String user_id;
    String result;
    String url = "http://192.168.1.7/mobileApp/user.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        name = getIntent().getStringExtra("NAME");
        email = getIntent().getStringExtra("EMAIL");
        contact = getIntent().getStringExtra("CONTACT");
        start = getIntent().getStringExtra("START");
        end = getIntent().getStringExtra("END");
        price = getIntent().getStringExtra("PRICE");
        ser_name = getIntent().getStringExtra("SERVICE");
        sharedPreferences = new SecureSharePref(this,"secrets1");
        getSupportActionBar().hide();
        shopbooktv = findViewById(R.id.shopbook);
        contacttv = findViewById(R.id.shopphone);
        shopsertv = findViewById(R.id.shopservice);
        serpricetv = findViewById(R.id.price);
        shopbooktv.setText("BarberShop : "+name);
        contacttv.setText("Contact : "+contact);
        serpricetv.setText("Price : "+price+" Baht");
        shopsertv.setText("Service : "+ser_name);
        timeTv = findViewById(R.id.booktime);
        CFbt = findViewById(R.id.cfbt);
        getdata();
        CFbt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(!(String.valueOf(timeTv.getText()).equals(""))&& !(String.valueOf(dateTv.getText())).equals("")) {
                    LocalTime timestart = LocalTime.parse(start);
                    LocalTime timestop = LocalTime.parse(end);
                    LocalTime time = LocalTime.parse(timeTv.getText().toString());
                    if (time.isAfter(timestart) && time.isBefore(timestop)){
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[7];
                                field[0] = "user_id";
                                field[1] = "shopname";
                                field[2] = "service";
                                field[3] = "time";
                                field[4] = "date";
                                field[5] = "user_p";
                                field[6] = "user_n";
                                String[] data = new String[7];
                                data[0] = user_id;
                                data[1] = name;
                                data[2] = ser_name;
                                data[3] = String.valueOf(timeTv.getText());
                                data[4] = String.valueOf(dateTv.getText());
                                data[5] = user_phone;
                                data[6] = user_name;
                                PutData putData = new PutData("http://192.168.1.7/mobileApp/booking.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("complete")) {
                                            Toast.makeText(getApplicationContext(),"ทำการจองเสร็จสิน", Toast.LENGTH_SHORT).show();
                                            Intent itn = new Intent(getApplicationContext(),UserActivity.class);
                                            startActivity(itn);
                                            finish();
                                        } else{
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        }
                                        //End ProgressBar (Set visibility to GONE)
                                        Log.i("PutData", result);
                                    }
                                }
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"การกรุณาใส่เวลาในช่วงเวลาทำการ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        hour = h;
                        minute = m;
                        String s1 = String.valueOf(hour),s2 = String.valueOf(minute);
                        if(h < 10){
                            s1 = "0"+s1;
                        }
                        if(m < 10){
                            s2 = "0"+s2;
                        }
                        timeTv.setText(s1+":"+s2+":00");
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this,onTimeSetListener,hour,minute,true);
                timePickerDialog.show();
            }
        });
        dateTv = findViewById(R.id.bookdate);
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        year = y;
                        month = m;
                        day = d;
                        String s1 = String.valueOf(year),s2 = String.valueOf(month),s3 = String.valueOf(day);
                        if(y < 10){
                            s1 = "0"+s1;
                        }
                        if(m < 10){
                            s2 = "0"+s2;
                        }
                        if(d < 10){
                            s3 = "0"+s3;
                        }
                        dateTv.setText(s1+"-"+s2+"-"+s3);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(BookingActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                dialog.show();
            }
        });
    }
    public void cancel(View v){
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
                user_name = jsObj.get("username").toString();
                user_phone = jsObj.get("phone").toString();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}