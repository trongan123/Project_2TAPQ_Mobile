package com.example.thongtintaikhoan.controller;

import static android.provider.MediaStore.Images.Media.getBitmap;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Account;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Account;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private String ID_acc;
    private ImageButton uploadImage;
    private ImageView profileImage;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private TextView clientFirstname;
    private TextView clientLastName;
    private TextView clientEmail;
    private TextView clientPhone;
    private TextView tv_UserName;
    private Intent intent;
    private TextView tv_FullName;
    private TextView tv_Email;
    private TextView fullName;
    private TextView clientCreditCard;
    private Button editProfile;
    private TextView tv_Phone;
    private TextView tv_Address;
    private CircleImageView circleImageView;
    private String email_user;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        tv_UserName = (TextView) findViewById(R.id.tv_Ten);
        tv_FullName = (TextView) findViewById(R.id.tv_Ho);
        tv_Email = (TextView) findViewById(R.id.tv_email);
        tv_Phone = (TextView) findViewById(R.id.tv_SoDienThoai);
        tv_Address = (TextView) findViewById(R.id.tv_Diachi);


        circleImageView = (CircleImageView) findViewById(R.id.img_AnhDaiDien);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(upload, REQUEST_CODE);
            }
        });

        profileImage = findViewById(R.id.imgV_ThongTinTaiKhoan);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomePage();
            }
        });

        ID_acc = clientID();
//        getProfileInformation(ID_acc);
        loadProfile(ID_acc);

        Button btn_editThongTin = (Button) findViewById(R.id.btn_EditThongTin);

//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });
        btn_editThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.putExtra("EMAIL_USER",email_user);
                startActivity(intent);
            }
        });

    }
    public void getProfileInformation(String ID_acc) {
        try {
//
            clientFirstname = (TextView) findViewById(R.id.tv_Ten);
            clientLastName = (TextView) findViewById(R.id.tv_Ho);
            clientEmail = (TextView) findViewById(R.id.tv_email);

//
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case REQUEST_CODE:
                    if (resultCode == Activity.RESULT_OK && data != null) {

                        //data gives you the image uri. Try to convert that to bitmap
                        Uri selectedImage = data.getData();

                        //uploadImage.setImageURI(selectedImage);
                        Bitmap bitmap = getBitmap(this.getContentResolver(), selectedImage);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                        // Create a byte array from ByteArrayOutputStream
                        byte[] byteArray = stream.toByteArray();


                        try {
                            databaseHelper = new DatabaseHelper(getApplicationContext());
                            db = databaseHelper.getWritableDatabase();

                            DatabaseHelper.updateImageAccount(db,
                                    byteArray,
                                    String.valueOf(ID_acc));

                            db = databaseHelper.getReadableDatabase();

                            cursor = DatabaseHelper.selectImageAccount(db, ID_acc);

                            if (cursor.moveToFirst()) {
                                // Create a bitmap from the byte array
                                byte[] image = cursor.getBlob(0);

                                circleImageView.setImageBitmap(HelperUtilities.decodeSampledBitmapFromByteArray(image, 300, 300));

                            }

                        } catch (SQLiteException ex) {
                            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Log.e(TAG, "Selecting picture cancelled");
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in onActivityResult : " + e.getMessage());
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            cursor.close();
            db.close();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error closing database or cursor", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadProfile(String ID_acc) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            byte[] image = new byte[0];
            cursor = DatabaseHelper.selectProfile(db, ID_acc, image);

            if (cursor.moveToFirst()) {

                String username = cursor.getString(0);
                String fullname = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                String address = cursor.getString(4);
                image = cursor.getBlob(5);
                if(image != null){
                    Account account = new Account(username, fullname, email, phone, address, image);
                    tv_UserName.setText(account.getUsername());
                    tv_FullName.setText(account.getFullname());
                    tv_Email.setText(account.getEmail());
                    tv_Address.setText(account.getAddress());
                    tv_Phone.setText(account.getPhone());
                    byte[] getImg = account.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(getImg, 0, getImg.length);
                    circleImageView.setImageBitmap(bitmap);
                }
                Account account = new Account(username, fullname, email, phone, address);
                tv_UserName.setText(account.getUsername());
                tv_FullName.setText(account.getFullname());
                tv_Email.setText(account.getEmail());
                tv_Address.setText(account.getAddress());
                tv_Phone.setText(account.getPhone());
                email_user = email;
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
    private void HomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}