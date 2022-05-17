package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterShopActivity extends AppCompatActivity {
    TextInputEditText textInputEditTextname,textInputEditTextUsername,textInputEditTextEmail,textInputEditTextNumber,textInputEditTextPassword,textInputEditTextCPassword,textInputEditTextAddress;
    TextView Date1,Date2;
    Button buttonRegister;
    int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">"+"Register"+"</font>"));
        textInputEditTextname = findViewById(R.id.shopnameS);
        textInputEditTextUsername = findViewById(R.id.UsernameS);
        textInputEditTextEmail = findViewById(R.id.EmailS);
        textInputEditTextNumber = findViewById(R.id.NumberS);
        textInputEditTextPassword = findViewById(R.id.PasswordS);
        textInputEditTextCPassword = findViewById(R.id.CPasswordS);
        textInputEditTextAddress = findViewById(R.id.AddressrS);

        buttonRegister = findViewById(R.id.RegisterS);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username,email,number,password,start,end,address,cpassword,shopname;
                username = String.valueOf(textInputEditTextUsername.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                number = String.valueOf(textInputEditTextNumber.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                start = String.valueOf(Date1.getText());
                end = String.valueOf(Date2.getText());
                address = String.valueOf(textInputEditTextAddress.getText());
                shopname = String.valueOf(textInputEditTextname.getText());
                cpassword = String.valueOf(textInputEditTextCPassword.getText());
                if(password.equals(cpassword)){
                    if(!username.equals("") && !email.equals("") && !number.equals("") && !password.equals("")){
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[9];
                                field[0] = "username";
                                field[1] = "email";
                                field[2] = "number";
                                field[3] = "password";
                                field[4] = "status";
                                field[5] = "start";
                                field[6] = "end";
                                field[7] = "address";
                                field[8] = "shop";
                                String[] data = new String[9];
                                data[0] = username;
                                data[1] = email;
                                data[2] = number;
                                data[3] = password;
                                data[4] = "shop";
                                data[5] = start;
                                data[6] = end;
                                data[7] = address;
                                data[8] = shopname;
                                PutData putData = new PutData("http://192.168.1.7/mobileApp/registershop.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if(result.equals("Sign Up Success")){
                                            Toast.makeText(getApplicationContext(),"สมัครสมาชิกเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                                            Intent itn = new Intent(getApplicationContext(),LoginPageActivity.class);
                                            startActivity(itn);
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
        Date1 = findViewById(R.id.time1);
        Date1.setOnClickListener(new View.OnClickListener() {
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
                        Date1.setText(s1+":"+s2+":00");
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterShopActivity.this,onTimeSetListener,hour,minute,true);
                timePickerDialog.show();
            }
        });
        Date2 = findViewById(R.id.time2);
        Date2.setOnClickListener(new View.OnClickListener() {
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
                        Date2.setText(s1+":"+s2+":00");
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterShopActivity.this,onTimeSetListener,hour,minute,true);
                timePickerDialog.show();
            }
        });
    }
    public void loginpage(View v){
        Intent itn = new Intent(this,LoginPageActivity.class);
        startActivity(itn);
        this.finish();
    }
}