package com.example.thongtintaikhoan.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePassword extends AppCompatActivity {
    private SQLiteOpenHelper hospitalDatabaseHelper;
    private String ID_acc;

    private SQLiteDatabase db;
    private ImageView img;
    private EditText oldPassword, newPassword,confirmPassword;
    private Button btnChangePassword;
    private TextView txtMatchError;
    private boolean isValid;
    private boolean isValidUser;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        oldPassword = findViewById(R.id.edt_PasswordHienTai);
        newPassword = findViewById(R.id.edt_MatKhauMoi);
        confirmPassword = findViewById(R.id.edt_XacNhanMatKhau);

        img = findViewById(R.id.imgV_SetPassWord);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity();
            }
        });

        txtMatchError = (TextView) findViewById(R.id.txtMatchError);
        Button btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        ID_acc = clientID();



        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }





    public Dialog changePasswordDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Mật khẩu đã thay đổi thành công! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }

    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }

    public boolean isValidUser() {
        try {

            hospitalDatabaseHelper = new DatabaseHelper(getApplicationContext());
            db = hospitalDatabaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectClientPassword(db, ID_acc);

            String oldPassword1 = getMd5Hash(oldPassword.getText().toString());
            if (cursor.moveToFirst()) {

                String password = cursor.getString(0);

                if (!oldPassword1.equals(password)) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public boolean isValidInput() {
        isValidUser = isValidUser();
        if (HelperUtilities.isEmptyOrNull(oldPassword.getText().toString())) {
            oldPassword.setError("Vui lòng nhập mật khẩu của bạn");
            return false;
        } else if (!isValidUser) {
            oldPassword.setError("Mật khẩu không đúng");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(newPassword.getText().toString())) {
            newPassword.setError("Vui lòng nhập mật khẩu mới của bạn");
            return false;
        }else if (HelperUtilities.isShortPassword(newPassword.getText().toString())) {
            newPassword.setError("Mật khẩu của bạn phải có ít nhất 8 ký tự, có chữ và số.");
            return false;
        }  else if (HelperUtilities.isEmptyOrNull(confirmPassword.getText().toString())) {
            confirmPassword.setError("Vui lòng xác nhận mật khẩu mới của bạn");
            return false;
        } else if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            txtMatchError.setText("Mật khẩu không khớp");
            return false;
        }
        return true;
    }

    public void changePassword() {
        try {

            hospitalDatabaseHelper = new DatabaseHelper(getApplicationContext());
            db = hospitalDatabaseHelper.getWritableDatabase();

            ID_acc = clientID();
            isValid = isValidInput();
            isValidUser = isValidUser();
            String newPassword1 = getMd5Hash(newPassword.getText().toString());

            if (isValid && isValidUser) {
                DatabaseHelper.updatePassword(db,
                        newPassword1,
                        String.valueOf(ID_acc));

                changePasswordDialog().show();
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }
    public void MainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
