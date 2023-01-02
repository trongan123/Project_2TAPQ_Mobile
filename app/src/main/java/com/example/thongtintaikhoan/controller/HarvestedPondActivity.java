package com.example.thongtintaikhoan.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Detail_Receipts_payments;
import com.example.thongtintaikhoan.model.Pond;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HarvestedPondActivity extends AppCompatActivity {
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Intent intent;
    private Cursor cursor;
    private ImageView btn_QuayLai;
    private String PondID;
    private String ID_acc;
    private String ID_role;
    private String id_rolestaff;
    private String ID_account;
    List<Pond> userlist = new ArrayList<>();
    private ArrayAdapter<Pond> adapter;
    private ListView ListPondHarvest;
    String id_role = String.valueOf(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvested_pond);

        ID_acc = clientID();
        ID_role = clientRole();
        id_rolestaff = clientRolestaff();
        getID_StaffToAccount(ID_role);
        getListPond(ID_acc);
        Collections.reverse(userlist);
        btn_QuayLai = findViewById(R.id.img_QuayLai_Harvested_Pond);
        btn_QuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage();
            }
        });


        ListPondHarvest = findViewById(R.id.lv_ListPondHarvest);
        adapter = new ArrayAdapter<Pond>(this, 0, userlist) {

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                convertView = inflater.inflate(R.layout.item_harvested_pond, null);
                TextView tvName = convertView.findViewById(R.id.tv_TenAo_DaThuHoach);
                TextView tvPhone = convertView.findViewById(R.id.tv_DienTichAo_DaThuHoach);
                ImageView tvImage = convertView.findViewById(R.id.img_fishHarvestPond);
//                ImageView tvThuHoach = convertView.findViewById(R.id.img_Thu_Hoach_ao);
                ImageView btnDelete = convertView.findViewById(R.id.img_Thu_Hoach_ao);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Pond pond = userlist.get(position);
                        PondID = pond.getId_pond();
                        openDialogDeleteHarvestPond(Gravity.CENTER);
                    }
                });
                Pond pond = userlist.get(position);
                tvName.setText("Tên ao: " + pond.getName());
                tvPhone.setText("Diện tích: " + pond.getPond_area() + " m²");

                byte[] recordImage =  pond.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
                if(bitmap != null){
                    tvImage.setImageBitmap(bitmap);
                }

                return convertView;
            }
        };

        ListPondHarvest.setAdapter(adapter);

        ListPondHarvest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Pond pond = userlist.get(position);
                PondID = pond.getId_pond();
                intent = new Intent(getApplicationContext(), InformationHarvestPondActivity.class);
                intent.putExtra("POND_ID",PondID);
                startActivity(intent);
            }
        });
    }

    public void getListPond(String ID_acc) {
//        List<Pond> list = new ArrayList<>();
        userlist.clear();
        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.selectPondOfHarvest(db, ID_acc);
            byte[] image = new byte[0];

            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "Chưa có ao thu hoạch", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);


                    String id_pond = cursor.getString(0);
                    String id_acc = cursor.getString(1);
                    String name = cursor.getString(3);
                    String pond_area = cursor.getString(4);
                    image = cursor.getBlob(5);

                    if(image != null){
                        Pond pond = new Pond(id_pond,id_acc, name, pond_area,image);
                        pond.setId_pond(id_pond);
                        pond.setName(name);
                        pond.setPond_area(pond_area);


                        userlist.add(pond);
                        cursor.moveToNext();
                    }

                }
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database không hoạt động", Toast.LENGTH_SHORT).show();
        }
    }
    private void openDialogDeleteHarvestPond(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_harvested_pond);

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

        Button btnHuy = dialog.findViewById(R.id.btn_Huy_Delete_ThuHoachAo);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnLuu = dialog.findViewById(R.id.btn_DongY_Delete_ThuHoachAo);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteHarvestPond(PondID);
                getListPond(ID_acc);
                Collections.reverse(userlist);
                recreate();

            }
        });
        dialog.show();
    }
    public void DeleteHarvestPond(String PondID){
        try{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.DeleteDiaryPondHarvest(db,PondID);
            cursor.moveToNext();
            cursor = DatabaseHelper.getIDPondforDelete(db, PondID);
            cursor.moveToNext();
            DatabaseHelper.insertNotifyDeleteHarvest(db,ID_acc);
            cursor.moveToNext();

        }catch (SQLiteException ex){
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    public void getID_StaffToAccount(String ID_role){
        if(ID_role == id_role){

        }else{
            try {
                databaseHelper = new DatabaseHelper(getApplicationContext());
                db = databaseHelper.getReadableDatabase();
                cursor = DatabaseHelper.getID_accountforstaff(db, ID_role);


                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Không có ao", Toast.LENGTH_LONG).show();
                } else {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        ID_account = cursor.getString(0);
                        cursor.moveToNext();
                        ID_acc = ID_account;
                    }
                }
            } catch (SQLiteException ex) {
                Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String clientID() {
        LoginActivity.sharedPreferences = this.getApplicationContext().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }
    public String clientRole(){
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_role = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLE, String.valueOf(0));
        return ID_role;
    }
    public String clientRolestaff(){
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        id_rolestaff = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLESTAFF, String.valueOf(0));
        return id_rolestaff;
    }

    public void HomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}