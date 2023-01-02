package com.example.thongtintaikhoan.controller;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;

public class EditProfileActivity extends AppCompatActivity {

    private Button btnUpdateProfile;
    private EditText clientFirstName;
    private EditText userFullName;
    private EditText userEmail;
    private EditText userPhone;
    private EditText userAddress;
    private ImageView ImageView;
    private Intent intent;


    private String email_user;
    private String ID_acc;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        intent = getIntent();
        email_user = String.valueOf(intent.getStringExtra("EMAIL_USER"));


        ImageView = findViewById(R.id.imgV_ChinhSuaThongTIn);
        ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuayLaiProfile();
            }
        });

        btnUpdateProfile = (Button) findViewById(R.id.btn_LuuThongTin);

        clientFirstName = (EditText) findViewById(R.id.edit_Ten);
        userFullName = (EditText) findViewById(R.id.edit_Ho);
        userEmail = (EditText) findViewById(R.id.edit_Email);
        userPhone = (EditText) findViewById(R.id.edit_SoDienThoai);
        userAddress = (EditText) findViewById(R.id.edit_DiaChi);


        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();

            }
        });

    }
    public void updateProfile() {
        String email =  userEmail.getText().toString();
        if(email.equals(email_user)){
            try {

                databaseHelper = new DatabaseHelper(getApplicationContext());
                db = databaseHelper.getWritableDatabase();

                ID_acc = clientID();
                isValid = isValidUserInput();

                if (isValid) {
                    DatabaseHelper.updateClient(db,
                            clientFirstName.getText().toString(),
                            userFullName.getText().toString(),
                            userEmail.getText().toString(),
                            userPhone.getText().toString(),
                            userAddress.getText().toString(),ID_acc);
                    updateProfileDialog().show();
                }
            } catch (SQLiteException ex) {
                Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            }

        }else{
            try {

                databaseHelper = new DatabaseHelper(getApplicationContext());
                db = databaseHelper.getWritableDatabase();
                cursor = DatabaseHelper.selectAccount(db, HelperUtilities.filter(userEmail.getText().toString()));
                ID_acc = clientID();
                isValid = isValidUserInput();
                if (cursor != null && cursor.getCount() > 0) {
                    accountExistsAlert().show();
                } else {
                    if (isValid) {
                                DatabaseHelper.updateClient(db,
                                clientFirstName.getText().toString(),
                                userFullName.getText().toString(),
                                userEmail.getText().toString(),
                                userPhone.getText().toString(),
                                userAddress.getText().toString(), ID_acc);

                        updateProfileDialog().show();
                    }
                }
            } catch (SQLiteException ex) {
                Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Dialog accountExistsAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Email đã tồn tại.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }
    public Dialog updateProfileDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cập nhật thành công! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }
    public boolean isValidUserInput() {
        if (HelperUtilities.isEmptyOrNull(clientFirstName.getText().toString())) {
            clientFirstName.setError("Vui lòng nhập tên");
            return false;
        } else if (HelperUtilities.isName(clientFirstName.getText().toString())) {
            clientFirstName.setError("Vui lòng nhập đúng định dạng tên");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(userFullName.getText().toString())){
            userFullName.setError("Vui lòng nhập đầy đủ tên");
            return false;
        }else if(HelperUtilities.isName(userFullName.getText().toString())){
            userFullName.setError("Vui lòng nhập đúng định dạng tên");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(userAddress.getText().toString())){
            userAddress.setError("Vui lòng nhập địa chỉ");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(userPhone.getText().toString())){
            userPhone.setError("Vui lòng nhập số điện thoại");
            return false;
        }else if(!HelperUtilities.isValidPhone(userPhone.getText().toString())){
            userPhone.setError("Vui lòng nhập đúng định dạng số điện thoại");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(userEmail.getText().toString())){
            userEmail.setError("Vui lòng nhập mail");
            return false;
        }else if(!HelperUtilities.isValidEmail(userEmail.getText().toString())){
            userEmail.setError("Vui lòng nhập đúng định dạng mail");
            return false;
        }

        return true;

    }


    private void QuayLaiProfile(){
        finish();
    }



}
