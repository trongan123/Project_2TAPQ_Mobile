package com.example.thongtintaikhoan.controller;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.fragment.HomeFragment;
import com.example.thongtintaikhoan.fragment.WarehouseFragment;
import com.example.thongtintaikhoan.model.Account;
import com.example.thongtintaikhoan.model.Detail_Receipts_payments;
import com.example.thongtintaikhoan.model.History_store_house;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryWareHouseActivity extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String ID_acc;
    private String ID_role;
    private ImageView img_toolbar_HistoryStoreHouse;
    private String ID_account;
    private String id_user;

    private ArrayAdapter<History_store_house> adapter;
    private ListView HistoryWareHouse;
    String id_role = String.valueOf(0);
    List<History_store_house> userlist = new ArrayList<>();
    List<Account> userlist1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_warehouse);

        ID_acc = clientID();
        ID_role = clientRole();
        getID_StaffToAccount(ID_role);

        getHistoryWareHouse(ID_acc);
        Collections.reverse(userlist);
        img_toolbar_HistoryStoreHouse = findViewById(R.id.img_toolbar_HistoryStoreHouse);
        img_toolbar_HistoryStoreHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareHouse();
            }
        });

        HistoryWareHouse = findViewById(R.id.lv_HistoryWareHouse);
        adapter = new ArrayAdapter<History_store_house>(this,0,userlist){

            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                convertView = inflater.inflate(R.layout.item_history_warehouse, null);
                TextView tvTenSanPham = convertView.findViewById(R.id.tv_item_TenSanPham_HistroyWareHouse);
                TextView tvSoLuong = convertView.findViewById(R.id.tv_item_SoLuong_HistroyWareHouse);
                TextView tvNhanVien = convertView.findViewById(R.id.tv_item_NhanVien_HistroyWareHouse);

                TextView tvNgayNhap = convertView.findViewById(R.id.tv_item_NgayNhap_HistroyWareHouse);
                TextView tvGhiChu = convertView.findViewById(R.id.tv_item_GhiChu_HistroyWareHouse);


                History_store_house history_store_house = userlist.get(position);
                Account account = userlist1.get(position);
                tvTenSanPham.setText(history_store_house.getName());
                tvSoLuong.setText(String.valueOf(history_store_house.getQuanlity()));
                tvNhanVien.setText(account.getUsername());
                tvNgayNhap.setText(history_store_house.getDate());
                tvGhiChu.setText(history_store_house.getNote());

                return convertView;
            }
        };
        HistoryWareHouse.setAdapter(adapter);
    }

    public void getHistoryWareHouse(String ID_acc) {
//        List<Pond> list = new ArrayList<>();
        userlist.clear();


        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getHistoryWareHouse(db, ID_acc);


            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Lịch sử kho trống ", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);


                    String name = cursor.getString(3);
                    int quantily = cursor.getInt(4);
                    String username = cursor.getString(10);
                    String date = cursor.getString(7);
                    String note = cursor.getString(6);
                    History_store_house history_store_house = new History_store_house(name,quantily,date,note);
                    Account account = new Account(ID_acc,username);
                    history_store_house.setName(name);
                    history_store_house.setQuanlity(quantily);
                    account.setUsername(username);
                    history_store_house.setDate(date);
                    history_store_house.setNote(note);

//
                   userlist.add(history_store_house);
                   userlist1.add(account);


                    cursor.moveToNext();
//               pondArrayList.add(cursor.getString(3));
//                pondArrayList.add(cursor.getString(4));
                }
//            return list;
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database warehouse unavailable", Toast.LENGTH_SHORT).show();
        }



    }
    public void getID_StaffToAccount(String ID_role){
        if(ID_role == id_role){

        }else{
            try {
                databaseHelper = new DatabaseHelper(getApplication());
                db = databaseHelper.getReadableDatabase();
                cursor = DatabaseHelper.getID_accountforstaff(db, ID_role);


                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplication(), "Kho trống", Toast.LENGTH_LONG).show();
                } else {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);

                        ID_account = cursor.getString(0);
                        cursor.moveToNext();
                        ID_acc = ID_account;

                    }
                }

            } catch (SQLiteException ex) {
                Toast.makeText(getApplication(), "Database unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }
    public String clientRole(){
        LoginActivity.sharedPreferences = getApplication().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_role = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLE, String.valueOf(0));
        return ID_role;
    }
    private void WareHouse(){
            finish();
    }
}
