package com.example.thongtintaikhoan.controller;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Account;
import com.example.thongtintaikhoan.model.Detail_Receipts_payments;
import com.example.thongtintaikhoan.model.Role_staff;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManageStaffActivity extends AppCompatActivity {
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;

    private Cursor cursor;
    private String ID_acc;
    private String arruser[];
    private String id_role_staff;
    private String ID_AccStaff;
    private boolean isValid;
    private String ID_UpdateStaff;
    private String emailAlready;
    private String ID_Rs;


    private ImageView img_QuayLai_QuanLyNhanVien,btn_Them_QuanLyThu,btn_LuuEdit_NhanVien;
    private EditText edit_AddTen_NhanVien,edit_AddEmail_NhanVien,edit_AddPhone_NhanVien,edit_AddLuong_NhanVien,edt_AddSinhNhat_NhanVien,edt_AddPassword_staff,edt_AddTenTaiKhoan_NhanVien;
    private EditText edt_Ten_NhanVien,edt_Email_NhanVien,edt_Fullname_Nhanvien,edt_Password_Nhanvien,edt_Phone_NhanVien,edt_birtdayss_nhanvien,edt_Luong_NhanVien;
    private ArrayAdapter<Account> adapter;
    DatePickerDialog.OnDateSetListener setListener;



    private Spinner edt_AddChucVu_NhanVien;
    private Spinner edt_ChucVu_NhanVien;

    private ListView ManageStaff;
    List<Account> userlist = new ArrayList<>();
    List<Role_staff> userlist1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);
        ID_acc = clientID();
        getInfoStaff(ID_acc);

        btn_Them_QuanLyThu = findViewById(R.id.btn_Them_QuanLyNhanVien);
        btn_Them_QuanLyThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddNhanVien(Gravity.CENTER);
            }
        });

        img_QuayLai_QuanLyNhanVien = findViewById(R.id.img_QuayLai_QuanLyNhanVien);
        img_QuayLai_QuanLyNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage();
            }
        });

        ManageStaff = findViewById(R.id.list_Manage_Staff);
        adapter = new ArrayAdapter<Account>(this, 0, userlist) {

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                convertView = inflater.inflate(R.layout.item_staff, null);
                TextView tvName = convertView.findViewById(R.id.tv_Ten_Nhan_Vien);
                TextView tvEmail = convertView.findViewById(R.id.tv_Email_Nhan_Vien);
                TextView tvPhone = convertView.findViewById(R.id.tv_Phone_NhanVien);

                TextView tvChucVu = convertView.findViewById(R.id.tv_Chuc_Vu_NhanVien);
                TextView tvLuong = convertView.findViewById(R.id.tv_Luong_NhanVien);

                Button btnEdit = convertView.findViewById(R.id.btn_edit_NhanVien);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Account account = userlist.get(position);
                        ID_AccStaff = account.getEmail();
                        openDialogEditStaff(Gravity.CENTER);

                    }
                });

                Button btnDelete = convertView.findViewById(R.id.btn_Delete_NhanVien);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Role_staff role_staff = userlist1.get(position);
                        id_role_staff = role_staff.getId_role_staff();
                        openDialogDeleteStaff(Gravity.CENTER);
                    }
                });
                Account account = userlist.get(position);
                Role_staff role_staff = userlist1.get(position);

                tvName.setText(account.getUsername());
                tvEmail.setText(account.getEmail());
                tvPhone.setText(account.getPhone());
                tvChucVu.setText(account.getID_Role_Staff());
                tvLuong.setText(String.valueOf(role_staff.getSalary())+" VND");

                return convertView;
            }
        };
        ManageStaff.setAdapter(adapter);
    }
    private void openDialogDeleteStaff(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_staff);

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


        Button btnHuy = dialog.findViewById(R.id.btn_Huy_Delete_NhanVien);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        Button btnLuu = dialog.findViewById(R.id.btn_DongY_Delete_NhanVien);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteStaff(id_role_staff);
                recreate();
            }
        });
        dialog.show();

    }

    private void openDialogAddNhanVien(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_staff);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edit_AddTen_NhanVien = dialog.findViewById(R.id.edt_AddTen_NhanVien);
        edt_AddTenTaiKhoan_NhanVien = dialog.findViewById(R.id.edt_AddTenTaiKhoan_NhanVien);
        edit_AddEmail_NhanVien = dialog.findViewById(R.id.edt_AddEmail_NhanVien);
        edit_AddPhone_NhanVien = dialog.findViewById(R.id.edt_AddPhone_NhanVien);
        edt_AddPassword_staff = dialog.findViewById(R.id.edt_AddPass_NhanVien);
        edt_AddSinhNhat_NhanVien = dialog.findViewById(R.id.edt_AddSinhNhat_NhanVien);
        edt_AddChucVu_NhanVien =(Spinner) dialog.findViewById(R.id.edt_AddChucVu_NhanVien);
        edit_AddLuong_NhanVien = dialog.findViewById(R.id.edt_AddLuong_NhanVien);
        LoadCategoryStaff();


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
        edt_AddSinhNhat_NhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ManageStaffActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMaxDate((cal.getTimeInMillis())+(0*0*0*0*0));
                datePickerDialog.show();

            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"-"+month+"-"+year;
                edt_AddSinhNhat_NhanVien.setText(date);

            }
        };

        Button btnHuy = dialog.findViewById(R.id.btn_HuyAdd_NhanVien);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnLuu = dialog.findViewById(R.id.btn_LuuAdd_NhanVien);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Staff_Account(ID_acc);
                getInfoStaff(ID_acc);
//                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void openDialogEditStaff(int gravity){
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_staff);

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
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_Ten_NhanVien = dialog.findViewById(R.id.edt_Ten_NhanVien);
        edt_Fullname_Nhanvien = dialog.findViewById(R.id.edt_TenTaiKhoan_NhanVien);
        edt_Email_NhanVien = dialog.findViewById(R.id.edt_Email_NhanVien);
        edt_Phone_NhanVien = dialog.findViewById(R.id.edt_Phone_NhanVien);
        edt_Password_Nhanvien = dialog.findViewById(R.id.edt_Pass_NhanVien);
        edt_birtdayss_nhanvien = dialog.findViewById(R.id.edt_SinhNhat_NhanVien);
        edt_ChucVu_NhanVien =(Spinner) dialog.findViewById(R.id.edt_ChucVu_NhanVien);
        edt_Luong_NhanVien = dialog.findViewById(R.id.edt_Luong_NhanVien);
        LoadCategoryEditStaff();

        edt_birtdayss_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ManageStaffActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"-"+month+"-"+year;
                edt_birtdayss_nhanvien.setText(date);

            }
        };
        getInfoStaffDialog(ID_AccStaff);
        Button btnHuy = dialog.findViewById(R.id.btn_HuyEdit_NhanVien);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnLuu = dialog.findViewById(R.id.btn_LuuEdit_NhanVien);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_Staff_Account(ID_UpdateStaff);


            }
        });
        dialog.show();
    }

    public void getInfoStaff(String ID_acc) {
        userlist.clear();
        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getStaff(db, ID_acc);

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Chưa có nhân viên", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    String name = cursor.getString(0);
                    String email = cursor.getString(1);
                    String phone = cursor.getString(2);
                    String rolestaff = cursor.getString(3);
                    String id_role_staff = cursor.getString(4);
                    int salary = cursor.getInt(5);

                    Account account = new Account(name, email, phone,rolestaff);
                    Role_staff role_staff = new Role_staff(id_role_staff,salary);
                    account.setUsername(name);
                    account.setEmail(email);
                    account.setPhone(phone);
                    account.setID_Role_Staff(rolestaff);
                    role_staff.setSalary(salary);
                    role_staff.setId_role_staff(id_role_staff);

                    userlist.add(account);
                    userlist1.add(role_staff);
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    public void Add_Staff_Account(String ID_acc) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getWritableDatabase();
            cursor = DatabaseHelper.selectAccount(db, HelperUtilities.filter(edit_AddEmail_NhanVien.getText().toString()));
            isValid = isValidUserInput();
            if(isValid){
                if (cursor != null && cursor.getCount() > 0) {
                    accountExistsAlert().show();
                } else {
                    DatabaseHelper.insertRoles_Staff(db,ID_acc,
                            edt_AddChucVu_NhanVien.getSelectedItem().toString(),
                            edit_AddLuong_NhanVien.getText().toString());
                    if (cursor != null || cursor.getCount() == 1) {
                        cursor.moveToFirst();
                        DatabaseHelper.insertAccount_Staff(db,
                                edit_AddTen_NhanVien.getText().toString(),
                                getMd5Hash(edt_AddPassword_staff.getText().toString()),
                                edt_AddTenTaiKhoan_NhanVien.getText().toString(),
                                edit_AddEmail_NhanVien.getText().toString(),
                                edit_AddPhone_NhanVien.getText().toString(),
                                edt_AddSinhNhat_NhanVien.getText().toString());
                    }
                    SuccessDialog().show();
                }
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    public void Update_Staff_Account(String ID_UpdateStaff) {
          String abctest =  edt_Email_NhanVien.getText().toString();
        if(abctest.equals(emailAlready)){
            try {
                databaseHelper = new DatabaseHelper(getApplicationContext());
                db = databaseHelper.getWritableDatabase();
                isValid = isValidUserInputUpdate();
                if(isValid){
                        cursor.moveToFirst();
                        DatabaseHelper.updateRoll_Staff(db,ID_Rs,
                                edt_ChucVu_NhanVien.getSelectedItem().toString(),
                                edt_Luong_NhanVien.getText().toString());
                        if (cursor != null || cursor.getCount() == 1) {
                            cursor.moveToFirst();
                            DatabaseHelper.updateAccountStaff(db, ID_UpdateStaff,
                                    edt_Ten_NhanVien.getText().toString(),
                                    edt_Fullname_Nhanvien.getText().toString(),
                                    edt_Email_NhanVien.getText().toString(),
                                    edt_Phone_NhanVien.getText().toString(),
                                    edt_birtdayss_nhanvien.getText().toString());
                            SuccessDialog().show();
                        }
                    }

            } catch (SQLiteException ex) {
                Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            }
        }else{
            try {
                databaseHelper = new DatabaseHelper(getApplicationContext());
                db = databaseHelper.getWritableDatabase();
                cursor = DatabaseHelper.selectAccount(db, HelperUtilities.filter(edt_Email_NhanVien.getText().toString()));
                isValid = isValidUserInputUpdate();
                if(isValid){
                    if (cursor != null && cursor.getCount() > 0) {
                        accountExistsAlert().show();
                    } else {
                        DatabaseHelper.updateRoll_Staff(db,ID_Rs,
                                edt_ChucVu_NhanVien.getSelectedItem().toString(),
                                edt_Luong_NhanVien.getText().toString());
                        if (cursor != null || cursor.getCount() == 1) {
                            cursor.moveToFirst();
                            DatabaseHelper.updateAccountStaff(db,ID_UpdateStaff,
                                    edt_Ten_NhanVien.getText().toString(),
                                    edt_Fullname_Nhanvien.getText().toString(),
                                    edt_Email_NhanVien.getText().toString(),
                                    edt_Phone_NhanVien.getText().toString(),
                                    edt_birtdayss_nhanvien.getText().toString());
                        }

                        SuccessDialog().show();
                    }
                }

            } catch (SQLiteException ex) {
                Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void getInfoStaffDialog(String ID_AccStaff) {
//        userlist.clear();
        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getStaffDialog(db,ID_AccStaff);

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Không có thông tin nhân viên", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();

                    String ID_Accs = cursor.getString(0);
                    String name = cursor.getString(1);
                    String password = cursor.getString(3);
                    String fullname = cursor.getString(4);
                    String email = cursor.getString(2);
                    String phone = cursor.getString(5);
                    String birthday = cursor.getString(6);
                    String address = cursor.getString(7);
                    String rolestaff = cursor.getString(8);
                    String id_role_staff = cursor.getString(9);
                    int salary = cursor.getInt(10);
                    cursor.moveToNext();
                    Account account = new Account(ID_Accs,name,password,fullname,email,phone,birthday,address);
                    Role_staff role_staff = new Role_staff(id_role_staff,salary);
                    ID_UpdateStaff = ID_Accs;
                    emailAlready = email;
                    ID_Rs = id_role_staff;

                    edt_Ten_NhanVien.setText(account.getUsername());
                    edt_Fullname_Nhanvien.setText(account.getFullname());
//                   edt_AddPassword_staff.setText(account.getPassword());
                    edt_Email_NhanVien.setText(account.getEmail());
                    edt_Phone_NhanVien.setText(account.getPhone());
                    edt_birtdayss_nhanvien.setText(account.getBirthday());

                    edt_Luong_NhanVien.setText(String.valueOf(role_staff.getSalary()));

            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    private void DeleteStaff(String id_role_staff){
        try{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
//          cursor.moveToFirst();

            cursor = DatabaseHelper.DeleteStaffAccount(db,id_role_staff);
            cursor.moveToNext();
            cursor = DatabaseHelper.getIDRoleStaffforDelete(db, id_role_staff);
            cursor.moveToNext();
        }catch (SQLiteException ex){
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoadCategoryStaff(){

        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        cursor = DatabaseHelper.LoadCategoryStaff(db);
        arruser =  new String[cursor.getCount()];
        cursor.moveToFirst();
//
        for(int i=0;i<arruser.length;i++){

            arruser[i]=cursor.getString(0);
            cursor.moveToNext();
        }
        ArrayAdapter<String> test1 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arruser);
        edt_AddChucVu_NhanVien.setAdapter(test1);

    }
    public void LoadCategoryEditStaff(){

        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        cursor = DatabaseHelper.LoadCategoryStaff(db);
        arruser =  new String[cursor.getCount()];
        cursor.moveToFirst();
//
        for(int i=0;i<arruser.length;i++){
            arruser[i]=cursor.getString(0);
            cursor.moveToNext();
        }
        ArrayAdapter<String> test1 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arruser);
        edt_ChucVu_NhanVien.setAdapter(test1);
    }
    private void HomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }
    public Dialog DialogArlet() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Chỉ dùng cho người chủ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }
    public Dialog SuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cập nhật nhân viên thành công!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), ManageStaffActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        return builder.create();
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
    public boolean isValidUserInputUpdate(){
        if(HelperUtilities.isEmptyOrNull(edt_Ten_NhanVien.getText().toString())){
            edt_Ten_NhanVien.setError("Vui lòng nhập tên");
            return false;
        }else if(HelperUtilities.isName(edt_Ten_NhanVien.getText().toString())) {
            edt_Ten_NhanVien.setError("Vui lòng nhập tên chữ tiếng việt có cả số");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edt_Fullname_Nhanvien.getText().toString())) {
            edt_Fullname_Nhanvien.setError("Vui lòng nhập họ tên");
            return false;
        }else if(HelperUtilities.isName(edt_Fullname_Nhanvien.getText().toString())) {
            edt_Fullname_Nhanvien.setError("Vui lòng nhập họ tên chữ tiếng việt có cả số");
            return false;
        }else if (HelperUtilities.isShortPassword(edt_Password_Nhanvien.getText().toString())) {
            edt_Password_Nhanvien.setError("Vui lòng nhập mật khẩu nhiều hơn 8 ký tự và phải có số và chữ");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edt_Email_NhanVien.getText().toString())) {
            edt_Email_NhanVien.setError("Vui lòng nhập email");
            return false;
        }else if (!HelperUtilities.isValidEmail(edt_Email_NhanVien.getText().toString())) {
            edt_Email_NhanVien.setError("Vui lòng nhập đúng định dạng email");
            return false;
        }else if(!HelperUtilities.isValidPhone(edt_Phone_NhanVien.getText().toString())){
            edt_Phone_NhanVien.setError("Vui lòng nhập đúng định dạng số điện thoại");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edt_Phone_NhanVien.getText().toString())){
            edt_Phone_NhanVien.setError("Vui lòng nhập số điện thoại");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edt_Luong_NhanVien.getText().toString())){
            edt_Luong_NhanVien.setError("Vui lòng nhập lương của nhân viên");
            return false;
        }
        return true;
    }
    public boolean isValidUserInput() {

        if(HelperUtilities.isEmptyOrNull(edit_AddTen_NhanVien.getText().toString())){
            edit_AddTen_NhanVien.setError("Vui lòng nhập tên");
            return false;
        }else if(HelperUtilities.isName(edit_AddTen_NhanVien.getText().toString())) {
            edit_AddTen_NhanVien.setError("Vui lòng nhập tên chữ tiếng việt có cả số");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edt_AddTenTaiKhoan_NhanVien.getText().toString())) {
            edt_AddTenTaiKhoan_NhanVien.setError("Vui lòng nhập họ tên");
            return false;
        }else if(HelperUtilities.isName(edt_AddTenTaiKhoan_NhanVien.getText().toString())) {
            edt_AddTenTaiKhoan_NhanVien.setError("Vui lòng nhập họ tên chữ tiếng việt có cả số");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edt_AddPassword_staff.getText().toString())){
            edt_AddPassword_staff.setError("Vui lòng nhập mật khẩu");
            return false;
        }else if (HelperUtilities.isShortPassword(edt_AddPassword_staff.getText().toString())) {
            edt_AddPassword_staff.setError("Vui lòng nhập mật khẩu nhiều hơn 8 ký tự và phải có số và chữ");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edit_AddEmail_NhanVien.getText().toString())) {
            edit_AddEmail_NhanVien.setError("Vui lòng nhập email");
            return false;
        } else if (!HelperUtilities.isValidEmail(edit_AddEmail_NhanVien.getText().toString())) {
            edit_AddEmail_NhanVien.setError("Vui lòng nhập đúng định dạng email");
            return false;
        }else if(!HelperUtilities.isValidPhone(edit_AddPhone_NhanVien.getText().toString())){
            edit_AddPhone_NhanVien.setError("Vui lòng nhập đúng định dạng số điện thoại");
            return false;
        } else if(HelperUtilities.isEmptyOrNull(edit_AddPhone_NhanVien.getText().toString())){
            edit_AddPhone_NhanVien.setError("Vui lòng nhập số điện thoại");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edit_AddLuong_NhanVien.getText().toString())){
            edit_AddLuong_NhanVien.setError("Vui lòng nhập lương của nhân viên");
            return false;
        }
        return true;

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
}