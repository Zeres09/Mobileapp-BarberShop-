package com.example.warittorn.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText textInputEditTextUsername,textInputEditTextEmail,textInputEditTextNumber,textInputEditTextPassword,textInputEditTextCPassword;
    Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle(Html.fromHtml("<font color =\"black\">"+"Register"+"</font>"));
        textInputEditTextUsername = findViewById(R.id.UsernameS);
        textInputEditTextEmail = findViewById(R.id.Email);
        textInputEditTextNumber = findViewById(R.id.Number);
        textInputEditTextPassword = findViewById(R.id.Password);
        textInputEditTextCPassword = findViewById(R.id.CPassword);
        buttonRegister = findViewById(R.id.Register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username,email,number,password,cpassword;
                username = String.valueOf(textInputEditTextUsername.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                number = String.valueOf(textInputEditTextNumber.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                cpassword = String.valueOf(textInputEditTextCPassword.getText());
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
                                field[4] = "status";
                                String[] data = new String[5];
                                data[0] = username;
                                data[1] = email;
                                data[2] = number;
                                data[3] = password;
                                data[4] = "user";
                                PutData putData = new PutData("http://192.168.1.7/mobileApp/register.php", "POST", field, data);
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
    }

    public void loginpage(View v){
        this.finish();
    }

}