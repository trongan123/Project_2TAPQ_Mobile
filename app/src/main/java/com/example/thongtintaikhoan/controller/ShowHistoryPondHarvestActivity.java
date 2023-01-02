package com.example.thongtintaikhoan.controller;

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

import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Pond_diary;

import java.util.ArrayList;
import java.util.List;


public class ShowHistoryPondHarvestActivity extends AppCompatActivity {
    private ImageView img_QuayLaiNhatKiAo;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String PondID;
    private Intent intent;

    private ArrayAdapter<Pond_diary> adapter;
    private ListView HistoryPond;
    List<Pond_diary> userlist = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history_pond);

        intent = getIntent();
        PondID = String.valueOf(intent.getStringExtra("POND_ID"));

        getHistoryPondHarvest(PondID);

        HistoryPond = findViewById(R.id.Lv_ListHistoryPond);
        adapter = new ArrayAdapter<Pond_diary>(this, 0, userlist) {

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                convertView = inflater.inflate(R.layout.item_history_pond, null);
                TextView tvDate = convertView.findViewById(R.id.Date_HistoryPond);
                TextView tvMatDoNuoc = convertView.findViewById(R.id.tv_MatDoNuoc_Of_HistoryPond);
                TextView tvNhietDo = convertView.findViewById(R.id.tv_NhietDo_HistoryPond);
                TextView tvpH = convertView.findViewById(R.id.tv_pH_HistoryPond);

                TextView tvDoMan = convertView.findViewById(R.id.tv_DoMan_HistoryPond);
                TextView tvTrangThai = convertView.findViewById(R.id.tv_TrangThai_HistoryPond);

                Pond_diary pond_diary = userlist.get(position);
                final int abc = position;

                tvDoMan.setText(String.valueOf(pond_diary.getSanility())+"‰");
                tvpH.setText(String.valueOf(pond_diary.getPh())+" pH");
                tvNhietDo.setText(String.valueOf(pond_diary.getTemperature())+"°C");
                tvMatDoNuoc.setText(String.valueOf(pond_diary.getWater_level())+" m");
                tvTrangThai.setText(checkStatus(abc));
                tvDate.setText(String.valueOf(pond_diary.getDate()));

                return convertView;
            }
        };
        HistoryPond.setAdapter(adapter);

        img_QuayLaiNhatKiAo = (ImageView) findViewById(R.id.img_QuayLaiNhatKiAo);
        img_QuayLaiNhatKiAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 finish();
            }
        });
    }
    public void getHistoryPondHarvest(String PondID) {
//        List<Pond> list = new ArrayList<>();
        userlist.clear();


        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getHistoryPond(db, PondID);


            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Không có thông tin", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);

                    String abc = cursor.getString(0);
                    double sanility = cursor.getInt(2);
                    double ph = cursor.getInt(3);
                    double temperature = cursor.getInt(4);
                    double water_level = cursor.getInt(5);
                    int fish_status = cursor.getInt(6);
                    String date = cursor.getString(7);
                    Pond_diary pond_diary = new Pond_diary(abc,sanility,ph,temperature,water_level,fish_status,date);
                    pond_diary.setSanility(sanility);
                    pond_diary.setPh(ph);
                    pond_diary.setTemperature(temperature);
                    pond_diary.setWater_level(water_level);
                    pond_diary.setFish_status(fish_status);
                    pond_diary.setDate(date);

                    userlist.add(pond_diary);
                    cursor.moveToNext();

//               pondArrayList.add(cursor.getString(3));
//                pondArrayList.add(cursor.getString(4));

                }
//            return list;
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    private CharSequence checkStatus(int abc){
        Pond_diary pond_diary = userlist.get(abc);
        if(pond_diary.getFish_status() == 1){

            String string1 = "Tốt";
            return string1;
        }else if(pond_diary.getFish_status() == 2){

            String string2 = "Nguy hiểm";
            return string2;
        }


        return null;
    }

}
