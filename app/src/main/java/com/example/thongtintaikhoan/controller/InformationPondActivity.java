package com.example.thongtintaikhoan.controller;

import static android.provider.MediaStore.Images.Media.getBitmap;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Fish_category;
import com.example.thongtintaikhoan.model.Pond;
import com.example.thongtintaikhoan.model.Pond_diary;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformationPondActivity extends AppCompatActivity {
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String fPondID;
    private Intent intent;

    private String ID_acc;
    private String PondID;
    private String ID_fcategory;
    private TextView tv_FishCate;
    private TextView tv_StartDay;
    private TextView DtQuantity;
    private TextView tv_EndDay;
    private TextView DtPond_area;
    private ImageView img_QuayLaiHomePage;
    private CircleImageView circleImageView;
    final int REQUEST_CODE_GALLERY = 999;
    private TextView edit_HistoryPond;
    private TextView tv_Show_HistoryPond;
    private Button btn_deltepond;
    private static final int REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinao);

        intent = getIntent();
        PondID = String.valueOf(intent.getStringExtra("POND_ID"));


        ID_acc =clientID();

        tv_FishCate = (TextView)findViewById(R.id.tv_LoaiCa_ThongTinAo);
        DtQuantity = (TextView)findViewById(R.id.tv_SanLuong_ThongTinAo);
        DtPond_area = (TextView)findViewById(R.id.tv_DienTich_ThongTinAo);
        btn_deltepond = findViewById(R.id.btn_deltepond);
        btn_deltepond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogInformationPond(Gravity.CENTER);
            }
        });
        tv_Show_HistoryPond = (TextView) findViewById(R.id.tv_Show_HistoryPond);
        tv_Show_HistoryPond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), ShowHistoryPondActivity.class);
                intent.putExtra("POND_ID",PondID);
                startActivity(intent);
            }
        });
        edit_HistoryPond = (TextView) findViewById(R.id.edit_HistoryPond);
        edit_HistoryPond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), EditHistoryPondActivity.class);
                intent.putExtra("POND_ID",PondID);
                intent.putExtra("POND_IDFCATEGORY",ID_fcategory);
                startActivity(intent);
            }
        });

        circleImageView = findViewById(R.id.img_AnhAo_ImformationAo);
        circleImageView.setOnClickListener(new View.OnClickListener() {
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
                HomePage();
            }
        });

        tv_StartDay = (TextView) findViewById(R.id.tv_StartDay_ThongTinAo);
        tv_EndDay = (TextView) findViewById(R.id.tv_EndDay_ThongTinAo);

        displaySelectedFlightInfo(PondID);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case REQUEST_CODE:
                    if (resultCode == Activity.RESULT_OK && data != null) {

                        //data gives you the image uri. Try to convert that to bitmap
                        Uri selectedImage = data.getData();

                        //uploadImage.setImageURI(selectedImage);
                        Bitmap bitmap = getBitmap(this.getContentResolver(), selectedImage);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                        // Create a byte array from ByteArrayOutputStream
                        byte[] byteArray = stream.toByteArray();


                        try {
                            databaseHelper = new DatabaseHelper(getApplicationContext());
                            db = databaseHelper.getWritableDatabase();

                            DatabaseHelper.updateClientImage(db,
                                    byteArray,
                                    String.valueOf(PondID));

                            db = databaseHelper.getReadableDatabase();

                            cursor = DatabaseHelper.selectImage(db, PondID);

                            if (cursor.moveToFirst()) {
                                // Create a bitmap from the byte array
                                byte[] image = cursor.getBlob(0);

                                circleImageView.setImageBitmap(HelperUtilities.decodeSampledBitmapFromByteArray(image, 300, 300));

                            }


                        } catch (SQLiteException ex) {
                            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
                        }


                        break;
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Log.e(TAG, "Selecting picture cancelled");
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in onActivityResult : " + e.getMessage());
        }
    }



    public void displaySelectedFlightInfo(String PondID ) {
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
                String quantity_of_fingerlings = cursor.getString(7);
                image= cursor.getBlob(5);
                String start_day = cursor.getString(9);
                String end_day = cursor.getString(10);

                if(image != null){

                    Pond pond = new Pond(id_pond, id_acc,name, pond_area,session, quantity_of_fingerlings, image);
                    Pond pond1 = new Pond(start_day, end_day);
                    Fish_category fish_category = new Fish_category(id_fcategory, category_name);

                    tv_FishCate.setText(fish_category.getCategory_name() + "");
                    DtPond_area.setText(pond.getPond_area() + "   m²");
                    DtQuantity.setText(pond.getQuantity_of_fingerlings() + "   Tấn");
                    tv_StartDay.setText(pond1.getStar_day() + "");
                    tv_EndDay.setText(pond1.getEnd_day() + "");
                    byte[] getImg = pond.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(getImg, 0, getImg.length);
                    circleImageView.setImageBitmap(bitmap);
                    ID_fcategory = id_fcategory;
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


    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }

    private void HomePage(){

        finish();
    }
    private void EditHistoryPond(){
        Intent intent = new Intent(this, EditHistoryPondActivity.class);
        startActivity(intent);
    }

    private void openDialogInformationPond(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_information_pond);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }


        Button btnHuy = dialog.findViewById(R.id.btn_Huy_Delete_ThongTinAo);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        Button btnLuu = dialog.findViewById(R.id.btn_DongY_Delete_ThongTinAo);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeletePond(PondID);
                HomePage();
                finish();
            }
        });



        dialog.show();

    }

    private void DeletePond(String PondID){
        try{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getIDPondforDelete(db, PondID);
            cursor.moveToNext();
            cursor = DatabaseHelper.DeleteDiaryPond(db,PondID);
            cursor.moveToNext();
        }catch (SQLiteException ex){
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

}
