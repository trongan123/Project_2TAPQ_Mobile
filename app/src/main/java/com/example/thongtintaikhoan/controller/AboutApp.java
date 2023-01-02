package com.example.thongtintaikhoan.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;

public class AboutApp extends AppCompatActivity {
    private ImageView img_BackInInformationofApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        img_BackInInformationofApp = (ImageView) findViewById(R.id.img_BackInInformationofApp);
        img_BackInInformationofApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Homepage();
            }
        });

    }

    private void Homepage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}
