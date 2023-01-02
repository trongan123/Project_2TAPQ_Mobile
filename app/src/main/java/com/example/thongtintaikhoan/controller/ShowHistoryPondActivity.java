package com.example.thongtintaikhoan.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Account;
import com.example.thongtintaikhoan.model.Pond_diary;
import com.example.thongtintaikhoan.model.Role_staff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowHistoryPondActivity extends AppCompatActivity {
    private ImageView img_QuayLaiNhatKiAo;

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String ID_acc;
    private String PondID;
    private Intent intent;
    private boolean isValid;
    private String ID_diary;
    private EditText edt_matdonuoc,edt_doman,edt_dopH,edt_nhietdo;



    private ArrayAdapter<Pond_diary> adapter;
    private ListView HistoryPond;
    List<Pond_diary> userlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history_pond);

        intent = getIntent();
        PondID = String.valueOf(intent.getStringExtra("POND_ID"));
        ID_acc = clientID();
        getHistoryPond(PondID);
        Collections.reverse(userlist);


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

                Button btn_Edit_DiaryPonds = convertView.findViewById(R.id.btn_Edit_DiaryPond);
                btn_Edit_DiaryPonds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pond_diary pond_diary = userlist.get(position);
                        ID_diary=pond_diary.getId_diary();
                        String test = ID_diary;
                        openDialogEditBuy(Gravity.CENTER);

                    }
                });

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
                HomePage();
            }
        });
    }
    private void openDialogEditBuy(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_historypond);

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

        edt_matdonuoc = dialog.findViewById(R.id.edt_matdonuoc_historyPond);
        edt_doman = dialog.findViewById(R.id.edt_doman_historyPond);
        edt_dopH = dialog.findViewById(R.id.edt_doph_historypond);
        edt_nhietdo = dialog.findViewById(R.id.edt_nhietdo_historypond);
        getInfoHistoryPondDialog(ID_diary);
        Button btnHuy = dialog.findViewById(R.id.btn_HuyEdit_HistoryWareHouse);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        Button btnLuu = dialog.findViewById(R.id.btn_LuuEdit_HistoryWareHouse);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Update_HistoryPond(ID_diary);
                  adapter.notifyDataSetChanged();
                   getHistoryPond(PondID);
                    Collections.reverse(userlist);
                    dialog.dismiss();


            }
        });
        dialog.show();

    }

    public void getHistoryPond(String PondID) {
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
    public void getInfoHistoryPondDialog(String ID_diary) {
//
        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getDiaryPondDialog(db,ID_diary);

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Không có thông tin lịch sử sao", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                String id_diary = cursor.getString(0);
                double matdonuoc = cursor.getInt(5);
                double doman = cursor.getInt(2);
                double pH = cursor.getInt(3);
                double nhietdo = cursor.getInt(4);
                cursor.moveToNext();

                Pond_diary pond_diary = new Pond_diary(id_diary,doman,pH,nhietdo,matdonuoc);

                edt_matdonuoc.setText(String.valueOf(pond_diary.getWater_level()));
                edt_doman.setText(String.valueOf(pond_diary.getSanility()));
                edt_dopH.setText(String.valueOf(pond_diary.getPh()));
                edt_nhietdo.setText(String.valueOf(pond_diary.getTemperature()));

            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    public void Update_HistoryPond(String ID_diary) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getWritableDatabase();
            isValid = isValidUserInput();
            if (isValid) {
                cursor.moveToFirst();
                DatabaseHelper.updateHistoryPond(db, ID_diary,
                        edt_matdonuoc.getText().toString(),
                        edt_doman.getText().toString(),
                        edt_dopH.getText().toString(),
                        edt_nhietdo.getText().toString());

                UpdatePondHistorySuccess().show();
            }


        }catch(SQLiteException ex) {
                Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }
    public boolean isValidUserInput() {

        if (HelperUtilities.isEmptyOrNull(edt_matdonuoc.getText().toString())) {
            edt_matdonuoc.setError("Vui lòng nhập thông tin");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(edt_doman.getText().toString())) {
            edt_doman.setError("Vui lòng nhập thông tin");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(edt_dopH.getText().toString())) {
            edt_dopH.setError("Vui lòng nhập thông tin");
            return false;
        }
        else if (HelperUtilities.isEmptyOrNull(edt_nhietdo.getText().toString())) {
            edt_nhietdo.setError("Vui lòng nhập thông tin");
            return false;
        }
        return true;
    }
    public Dialog UpdatePondHistorySuccess() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sửa thành công")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    private void HomePage(){
        finish();
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