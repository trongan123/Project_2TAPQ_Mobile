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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;

public class EditHistoryPondActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private boolean isValid;
    private String PondID;
    private String ID_fcategory;
    private String ID_acc;
    private Intent intent;
    private ImageView img_toolbar_HistoryPond;
    private EditText edit_MatDoNuoc_EditHistoryPond,edit_DoMan_EditHistoryPond,
            edit_pH_EditHistoryPond,edit_NhietDo_EditHistoryPond;
    private Button btn_DongY_ThemLichSuAo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_history_pond);


        intent = getIntent();
        PondID = String.valueOf(intent.getStringExtra("POND_ID"));
        ID_fcategory = String.valueOf(intent.getStringExtra("POND_IDFCATEGORY"));
        ID_acc = clientID();

        edit_MatDoNuoc_EditHistoryPond = findViewById(R.id.edit_MatDoNuoc_EditHistoryPond);
        edit_DoMan_EditHistoryPond = findViewById(R.id.edit_DoMan_EditHistoryPond);
        edit_pH_EditHistoryPond = findViewById(R.id.edit_pH_EditHistoryPond);
        edit_NhietDo_EditHistoryPond = findViewById(R.id.edit_NhietDo_EditHistoryPond);


        btn_DongY_ThemLichSuAo = findViewById(R.id.btn_DongY_ThemLichSuAo);
        btn_DongY_ThemLichSuAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid = isValidUserInput();
                if(isValid){
                    AddHistoryPond(PondID);
                }
            }
        });

        img_toolbar_HistoryPond = (ImageView) findViewById(R.id.img_toolbar_HistoryPond);
        img_toolbar_HistoryPond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomePage();
            }
        });
    }

    public void AddHistoryPond(String PondID) {
        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getWritableDatabase();

            DatabaseHelper.AddHistoryPond(db,PondID,ID_fcategory,

                    edit_DoMan_EditHistoryPond.getText().toString(),
                    edit_pH_EditHistoryPond.getText().toString(),
                    edit_NhietDo_EditHistoryPond.getText().toString(),
                    edit_MatDoNuoc_EditHistoryPond.getText().toString());

            AddHistoryPondDialog().show();


        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    public Dialog AddHistoryPondDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Nhập thành công! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        return builder.create();
    }
    public boolean isValidUserInput() {

        if (HelperUtilities.isEmptyOrNull(edit_DoMan_EditHistoryPond.getText().toString())) {
            edit_DoMan_EditHistoryPond.setError("Vui lòng nhập thông tin");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(edit_pH_EditHistoryPond.getText().toString())) {
            edit_pH_EditHistoryPond.setError("Vui lòng nhập thông tin");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(edit_NhietDo_EditHistoryPond.getText().toString())) {
            edit_NhietDo_EditHistoryPond.setError("Vui lòng nhập thông tin");
            return false;
        }
        else if (HelperUtilities.isEmptyOrNull(edit_MatDoNuoc_EditHistoryPond.getText().toString())) {
            edit_MatDoNuoc_EditHistoryPond.setError("Vui lòng nhập thông tin");
            return false;
        }
        return true;
    }


    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }

    private void HomePage(){
        finish();
    }
}