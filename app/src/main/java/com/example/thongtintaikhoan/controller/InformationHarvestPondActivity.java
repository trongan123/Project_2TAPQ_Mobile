package com.example.thongtintaikhoan.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Fish_category;
import com.example.thongtintaikhoan.model.Pond;

import org.w3c.dom.Text;


import de.hdodenhof.circleimageview.CircleImageView;

public class InformationHarvestPondActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private SQLiteOpenHelper databaseHelper;
    private CircleImageView circleImageViewPondHarvest;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String fPondID;
    private Intent intent;
    private ImageView img_QuayLaiHomePage;
    private Button tv_Show_HistoryPondHarvests;
    private String ID_acc;
    private String PondID;
    TextView tv_FishCate,DtQuantity,DtPond_area,tv_StartDay,tv_EndDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_harvest_pond);

        ID_acc = clientID();
        intent = getIntent();
        PondID = String.valueOf(intent.getStringExtra("POND_ID"));


        tv_FishCate = findViewById(R.id.tv_LoaiCa_HarvestPond);
        DtQuantity = findViewById(R.id.tv_SanLuong_HarvestPond);
        DtPond_area = findViewById(R.id.tv_DienTich_HarvestPond);
        tv_StartDay =  findViewById(R.id.tv_StartDay_HarvestPond);
        tv_EndDay = findViewById(R.id.tv_EndDay_HarvestPond);


        circleImageViewPondHarvest = findViewById(R.id.img_AnhAo_ImformationHarvestPond);
        circleImageViewPondHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(upload, REQUEST_CODE);
            }
        });
        img_QuayLaiHomePage = (ImageView)findViewById(R.id.img_QuayLaiHomePage);
        img_QuayLaiHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               PreviousPage();
            }
        });
        tv_Show_HistoryPondHarvests = findViewById(R.id.tv_Show_HistoryPondHarvest);
        tv_Show_HistoryPondHarvests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ShowHistoryPondHarvestActivity.class);
                intent.putExtra("POND_ID",PondID);
                startActivity(intent);
            }
        });
        displayPondHarvest(PondID);
    }
    public void displayPondHarvest(String PondID) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            byte[] image = new byte[0];
            cursor = DatabaseHelper.getPondDetail(db, PondID);

            if(cursor.moveToNext()){
                String id_pond = cursor.getString(0);
                String id_acc = cursor.getString(1);
                String name = cursor.getString(3);
                String session = cursor.getString(6);
                String id_fcategory = cursor.getString(2);
                String category_name = cursor.getString(13);
                String pond_area = cursor.getString(4);
                String quanlity_of_end = cursor.getString(8);
                image= cursor.getBlob(5);
                String start_day = cursor.getString(9);
                String end_day = cursor.getString(10);

                if(image != null){

                    Pond pond = new Pond(id_pond, id_acc,name, pond_area,session, quanlity_of_end,start_day,end_day, image);

                    Fish_category fish_category = new Fish_category(id_fcategory, category_name);

//                    Pond pond = new Pond();
//                    Fish_category fish_category = new Fish_category();

                    tv_FishCate.setText(fish_category.getCategory_name() + "");
                    DtPond_area.setText(pond.getPond_area() + "   m²");
                    DtQuantity.setText(pond.getQuanlity_of_end() + "   Tấn");
                    tv_StartDay.setText(pond.getStar_day() + "");
                    tv_EndDay.setText(pond.getEnd_day() + "");
                    byte[] getImg = pond.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(getImg, 0, getImg.length);
                    circleImageViewPondHarvest.setImageBitmap(bitmap);

                }
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            cursor.close();
            db.close();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error closing database or cursor", Toast.LENGTH_SHORT).show();
        }
    }
    public void PreviousPage(){
        Intent intent = new Intent(this, HarvestedPondActivity.class);
        startActivity(intent);
        finish();
    }

    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }

}