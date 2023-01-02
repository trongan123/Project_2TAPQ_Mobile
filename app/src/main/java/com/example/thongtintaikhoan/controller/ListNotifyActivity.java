package com.example.thongtintaikhoan.controller;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Account;
import com.example.thongtintaikhoan.model.Notify;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListNotifyActivity extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String PondID;


    private  ListView test;
    private Intent intent;
    private ListView listNotify;
    private String ID_acc;
    private ImageView img_QuayLaiHomePage;
    private ArrayAdapter<Notify> adapter;
    private ArrayAdapter<Account> adapter1;
    List<Account> userlist1 = new ArrayList<>();
    List<Notify> userlist = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notify);

        listNotify = findViewById(R.id.lv_notify);

        img_QuayLaiHomePage = (ImageView) findViewById(R.id.img_QuayLaiHomePage);
        img_QuayLaiHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Homepage();
            }
        });

        ID_acc = clientID();
        getListNotify(ID_acc);
        DeleteNotifyAfterDay(ID_acc);
        Collections.reverse(userlist);
        Collections.reverse(userlist1);
        adapter = new ArrayAdapter<Notify>(this, 0, userlist) {
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_thongbao, null);
                TextView tvType = convertView.findViewById(R.id.tv_TinhTrang);
                TextView tvDescription = convertView.findViewById(R.id.tv_TrangThai);
                TextView tvDate = convertView.findViewById(R.id.tv_ThoiGian);
                TextView tvName = convertView.findViewById(R.id.tv_tennguoinhap);

                Notify notify = userlist.get(position);
                Account account = userlist1.get(position);
                tvType.setText(notify.getType());
                tvDescription.setText(notify.getDescription());
                tvDate.setText(notify.getDate());
                tvName.setText(account.getUsername());

                return convertView;
            }
        };
        listNotify.setAdapter(adapter);
    }
    public void DeleteNotifyAfterDay(String ID_acc){

        try{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getIDforDeleteNotifyAfterDay(db, ID_acc);
            cursor.moveToFirst();
        }catch (SQLiteException ex){
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public void getListNotify(String ID_acc) {
        userlist.clear();
        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getNotify(db, ID_acc);

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String type = cursor.getString(2);
                    String description = cursor.getString(3);
                    String date = cursor.getString(4);
                    String name = cursor.getString(7);

                    Notify notify = new Notify(type, description, date);
                    Account account = new Account(name);
                    notify.setType(type);
                    notify.setDescription(description);
                    notify.setDate(date);
                    account.setUsername(name);
                    userlist.add(notify);
                    userlist1.add(account);
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }
    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
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

    private void Homepage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}