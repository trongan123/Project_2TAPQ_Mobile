package com.example.thongtintaikhoan.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    public static final String MY_PREFERENCES = "MY_PREFS";
    public static final String EMAIL = "email";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CLIENT_ROLE = "CLIENT_ROLE";
    public static final String CLIENT_ROLESTAFF = "CLIENT_ROLESTAFF";
    public static final String LOGIN_STATUS = "LOGGED_IN";
    public static final String NAME_CLIENT = "NAME_CLIENT";
    public static SharedPreferences sharedPreferences;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextView txtLoginError,tvForgotPassWord_Login;
    private boolean isValid;
    private SQLiteOpenHelper databaseHelper;

    private String ID_acc;
    private String ID_role;
    private String id_roles;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ImageView img,btn_Login;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        sharedPreferences = getSharedPreferences(MY_PREFERENCES, 0);
        Boolean loggedIn = sharedPreferences.getBoolean(LOGIN_STATUS, false);//login status

        if (loggedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }


        img = findViewById(R.id.img_back_Login2);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intro();
            }
        });
        Button btnLogin = (Button) findViewById(R.id.btn_DangNhap);
        inputEmail = (EditText) findViewById(R.id.edt_login);
        inputPassword = (EditText) findViewById(R.id.edt_password);
        txtLoginError = (TextView) findViewById(R.id.tv_BaoLoi);
        tvForgotPassWord_Login = (TextView) findViewById(R.id.tv_ForgotPassWord_Login);
        tvForgotPassWord_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePassWordWithMailActivity.class);
                startActivity(intent);

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });
    }
    public void attemptLogin() {

        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            isValid = isValidUserInput();

            String filteredEmail = HelperUtilities.filter(inputEmail.getText().toString());
            String filteredPassword = HelperUtilities.filter(inputPassword.getText().toString());
            filteredPassword = getMd5Hash(filteredPassword);

            if (isValid) {

                cursor = DatabaseHelper.login(db, filteredEmail, filteredPassword);

                if (cursor != null && cursor.getCount() == 1) {
                    cursor.moveToFirst();
//
                    String email = cursor.getString(4);
                    ID_acc = cursor.getString(0);
                   String name_client = cursor.getString(1);
                    ID_role = cursor.getString(9);

                    loadID_rolestaff(ID_role);
                    sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(CLIENT_ID, ID_acc);
                    editor.putString(CLIENT_ROLE,ID_role);
                    editor.putString(CLIENT_ROLESTAFF,id_roles);
                    editor.putString(NAME_CLIENT, name_client);
                    editor.putString(EMAIL, email);
                    editor.putBoolean(LOGIN_STATUS, true);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    txtLoginError.setText("Invalid email or password");
                }
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValidUserInput() {
        if (HelperUtilities.isEmptyOrNull(inputEmail.getText().toString())) {
            txtLoginError.setText("Invalid email or password");
            return false;
        }
        if (HelperUtilities.isEmptyOrNull(inputPassword.getText().toString())) {
            txtLoginError.setText("Invalid email or wrong");
            return false;
        }
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error closing database or cursor", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
    }


    public void intro(){
        Intent intent = new Intent(this, IntroAcitvity.class);
        startActivity(intent);
        finish();
    }

    public void MainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
    public void loadID_rolestaff(String ID_role){
        if(ID_role == null){

        }else{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.getID_role1(db,ID_role);
            cursor.moveToFirst();
            id_roles = cursor.getString(0);

        }
    }

}