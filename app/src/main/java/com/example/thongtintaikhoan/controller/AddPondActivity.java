package com.example.thongtintaikhoan.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.fragment.HomeFragment;
import com.example.thongtintaikhoan.fragment.WarehouseFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPondActivity extends AppCompatActivity {
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ImageView img_BackInAddPond;
    private TextView checkboxCa;
    private TextView checkboxQui;
    private String ID_role;
    private String ID_account;
    private EditText  edit_TenAo_ThemAo;
    private EditText edit_DienTichAo_ThemAo;
    private EditText edit_LuongCa_ThemAo;
    private Button btn_DongY_ThemAo;
    private boolean isValid;
    private Spinner test;
    private String arruser[];
    private String ID_acc;
    private CircleImageView circleImageView;
    String id_role = String.valueOf(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinhsuathongtinao);

        edit_TenAo_ThemAo = findViewById(R.id.edit_TenAo_ThemAo);
        edit_DienTichAo_ThemAo = findViewById(R.id.edit_DienTichAo_ThemAo);
        edit_LuongCa_ThemAo = findViewById(R.id.edit_LuongCa_ThemAo);
        test = findViewById(R.id.spinner);
        btn_DongY_ThemAo = findViewById(R.id.btn_DongY_ThemAo);

        LoadFishCategory();
        ID_acc = clientID();
        ID_role = clientRole();
        getID_StaffToAccount(ID_role);
        circleImageView = findViewById(R.id.img_Add_AnhAo);

        checkboxQui = findViewById(R.id.checkbox_Qui_ThemAo);
        checkboxQui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMenuQui();
            }
        });

        img_BackInAddPond = (ImageView) findViewById(R.id.img_BackInAddPond);
        img_BackInAddPond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomePage();
            }
        });

        btn_DongY_ThemAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPond(ID_acc);
            }
        });

    }
    private void HomePage(){
      finish();
    }

    private void ShowMenuQui(){
        PopupMenu popupMenu = new PopupMenu(this, checkboxQui);
        popupMenu.getMenuInflater().inflate(R.menu.menu_checkbox_qui, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item_Qui1: checkboxQui.setText("1");
                        break;
                    case R.id.item_Qui2: checkboxQui.setText("2");
                        break;
                    case R.id.item_Qui3: checkboxQui.setText("3");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }



    public void AddPond(String ID_acc) {

        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            isValid = isValidUserInput();
            if (isValid) {
                byte[] image = new byte[0];
                cursor.moveToFirst();

                DatabaseHelper.insertPond(db, ID_acc,

                        checkboxQui.getText().toString(),
                        edit_TenAo_ThemAo.getText().toString(),
                        edit_DienTichAo_ThemAo.getText().toString(), image,
                        edit_LuongCa_ThemAo.getText().toString(),
                        test.getSelectedItem().toString());

                DatabaseHelper.insertNotifyAddPond(db, ID_acc);
                cursor.moveToNext();
                registrationSuccessDialog().show();
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }
    public boolean isValidUserInput() {

        if (HelperUtilities.isEmptyOrNull(edit_TenAo_ThemAo.getText().toString())) {
            edit_TenAo_ThemAo.setError("Vui lòng nhập tên ao");
            return false;
        }else if(!HelperUtilities.isString(edit_TenAo_ThemAo.getText().toString())){
            edit_TenAo_ThemAo.setError("Vui lòng nhập đúng ký tự");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(edit_DienTichAo_ThemAo.getText().toString())) {
            edit_DienTichAo_ThemAo.setError("Vui lòng nhập diện tích ao");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(edit_LuongCa_ThemAo.getText().toString())) {
            edit_LuongCa_ThemAo.setError("Vui lòng nhập lượng cá ao");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(checkboxQui.getText().toString())){
            checkboxQui.setError("Vui lòng chọn quý");
            return false;
        }

        return true;

    }

    public Dialog registrationSuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn đã tạo ao thành công!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        return builder.create();
    }

    public void LoadFishCategory(){
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        cursor = DatabaseHelper.Loadfishcategory(db);
//       String fishname = cursor.getString(0);
        arruser =  new String[cursor.getCount()];
        cursor.moveToFirst();

        for(int i=0;i<arruser.length;i++){

            arruser[i]=cursor.getString(0);
            cursor.moveToNext();


        }
        ArrayAdapter<String> test1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arruser);
        test.setAdapter(test1);

    }
    public void getID_StaffToAccount(String ID_role){
        if(ID_role == id_role){

        }else{
            try {
                databaseHelper = new DatabaseHelper(getApplicationContext());
                db = databaseHelper.getReadableDatabase();
                cursor = DatabaseHelper.getID_accountforstaff(db, ID_role);


                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Không có hóa đơn", Toast.LENGTH_LONG).show();
                } else {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        ID_account = cursor.getString(0);
                        cursor.moveToNext();
                        ID_acc = ID_account;
                    }
                }
            } catch (SQLiteException ex) {
                Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }
    public String clientRole(){
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_role = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLE, String.valueOf(0));
        return ID_role;
    }

}
