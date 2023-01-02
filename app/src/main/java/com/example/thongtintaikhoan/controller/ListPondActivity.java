package com.example.thongtintaikhoan.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thongtintaikhoan.Adapter.PondAdapter;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Pond;

import java.util.ArrayList;
import java.util.List;

public class ListPondActivity extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String PondID;


    private ListView test;
//    private  ArrayList<Pond> adapter;


    private Intent intent;
    private TextView test1;
    private ListView pondlist;
    private TextView itineraryMessage;
    private String ID_acc;
    RecyclerView rvPrograms;

    private RecyclerView rcvData;
    private PondAdapter pondAdapter;


    RecyclerView.LayoutManager layoutManager;
    private ArrayAdapter<Pond> adapter;
    //    List<Pond> contactsList = new ArrayList<>();
    List<Pond> userlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pond);

        pondlist = findViewById(R.id.testabc);

        ID_acc = clientID();
//        getListUser(ID_acc);
        adapter = new ArrayAdapter<Pond>(this, 0, userlist) {

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                convertView = inflater.inflate(R.layout.item_fishpond, null);
                TextView tvName = convertView.findViewById(R.id.tv_namepond1);
                TextView tvPhone = convertView.findViewById(R.id.tv_description);

                Pond pond = userlist.get(position);
                tvName.setText(pond.getName());
                tvPhone.setText(pond.getPond_area());

                return convertView;
            }
        };

        pondlist.setAdapter(adapter);
        pondlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

//                PondID = (int) id;
                Pond pond = userlist.get(position);

                PondID = pond.getId_pond();


                //Toast.makeText(getApplicationContext(), String.valueOf(flightID), Toast.LENGTH_SHORT).show();

                intent = new Intent(getApplicationContext(), InformationPondActivity.class);
                intent.putExtra("POND_ID",PondID);
                startActivity(intent);
            }
        });



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
}