package com.example.thongtintaikhoan.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.thongtintaikhoan.R;

public class IntroAcitvity extends AppCompatActivity {
    private ConstraintLayout cL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cL = findViewById(R.id.cl_SignUp);
        cL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sign();
            }
        });

        cL = findViewById(R.id.cl_Login);
        cL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login2();

            }
        });
    }
    public void Login2(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
    public void Sign(){
        Intent intent = new Intent(this, CheckMailOTPActivity.class);
        startActivity(intent);
    }


}
