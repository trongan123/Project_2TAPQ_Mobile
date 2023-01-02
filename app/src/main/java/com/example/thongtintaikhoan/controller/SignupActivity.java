package com.example.thongtintaikhoan.controller;

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
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.District;
import com.example.thongtintaikhoan.model.Province;
import com.example.thongtintaikhoan.model.Ward;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private SQLiteOpenHelper hospitalDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
//    private EditText inputEmail;
    private EditText inputusername;
    private EditText inputfullname;
    private String ID_acc;
    private EditText inputpassword;
    private EditText inputphone;
    private EditText inputbirthday;
    private EditText inputaddress;
    private EditText inputid;
    private EditText inputrole;
    private EditText inputidward;
    private EditText inputdatetime;
    private EditText inputConformPassword;
    private EditText inputstatus;
    private TextView alreadyHaveAccounts;
    private TextView inputEmail;

    private Button btn_provinces;
    private Button btn_districts;
    private Button btn_wards;

    DatePickerDialog.OnDateSetListener setListener;

    private boolean isValid;
    private boolean isValidUser;
    private SQLiteOpenHelper databaseHelper;
    private DatabaseHelper dbHelper;
    private ImageView imgv_Register;
    private Intent intent;
    private String mailOTP;
    private String id_province;
    private String id_district;
    private String id_wards;

    private ArrayAdapter<Province> adapter;
    private ArrayAdapter<District> adapter1;
    private ArrayAdapter<Ward> adapter2;
    private ListView provincelist;
    private ListView Districtlist;
    private ListView Wardlist;
    List<Province> userlist = new ArrayList<>();
    List<District> userlist1 = new ArrayList<>();
    List<Ward> userlist2 = new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button register = (Button) findViewById(R.id.btn_register);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        btn_provinces =(Button) findViewById(R.id.province);
        btn_districts =(Button) findViewById(R.id.district);
        btn_wards =(Button) findViewById(R.id.ward);

        intent = getIntent();
        mailOTP = String.valueOf(intent.getStringExtra("MAIL_CHECKOTP"));

        databaseHelper = new DatabaseHelper(SignupActivity.this);

        inputusername = (EditText) findViewById(R.id.edt_username);
        inputpassword = (EditText) findViewById(R.id.edt_password);
        inputfullname = (EditText)  findViewById(R.id.edt_fullname);
        inputEmail = (TextView) findViewById(R.id.edt_emailregister);
        inputphone = (EditText) findViewById(R.id.edt_phone);
        inputbirthday = (EditText) findViewById(R.id.edt_birthday);
        inputaddress = (EditText) findViewById(R.id.edt_address);
        inputConformPassword = (EditText) findViewById(R.id.inputConformPassword);

        inputEmail.setText(mailOTP);

        alreadyHaveAccounts = (TextView) findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
        imgv_Register = (ImageView) findViewById(R.id.img_toolbar_SignUp);
        imgv_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intro();
            }
        });

        inputbirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
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
                inputbirthday.setText(date);
            }
        };

        final DatabaseHelper db = new DatabaseHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEmployee();

            }
        });
        getListProvince();
        btn_provinces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_wards.setText("XÃ/PHƯỜNG");
                btn_districts.setText("QUẬN/HUYỆN");
                openDialogProvince(Gravity.CENTER);

            }
        });
        btn_districts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_wards.setText("XÃ/PHƯỜNG");
                getListDistrict(id_province);
                openDialogDistrict(Gravity.CENTER);

            }
        });
        btn_wards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListWard(id_district);
                openDialogWard(Gravity.CENTER);
            }
        });
    }
    private void openDialogProvince(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_province);


        provincelist = dialog.findViewById(R.id.listProvince);
        adapter = new ArrayAdapter<Province>(this, 0, userlist) {
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cardview_province, null);
                TextView tvName = convertView.findViewById(R.id.name_province);


                Province province = userlist.get(position);
                tvName.setText(province.getName());
//                tvPhone.setText("Diện tích: " + pond.getPond_area() + " m²");

//                Pond pondimage = userlist.get(position);
                return convertView;
            }
        };
        provincelist.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        provincelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Province province = userlist.get(position);
                id_province = province.getId_province();
                String abc = id_province;
                btn_provinces.setText(province.getName());
                dialog.dismiss();

            }
        });



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
//        getListProvince();

//        provincelist.setAdapter(adapter);
        ImageView img_toolbar_backProvince = dialog.findViewById(R.id.img_toolbar_backProvince);
        img_toolbar_backProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void openDialogDistrict(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_district);

        Districtlist = dialog.findViewById(R.id.listDistrict);
        adapter1 = new ArrayAdapter<District>(this, 0, userlist1) {
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cardview_district, null);
                TextView tvName = convertView.findViewById(R.id.name_district);


                District district = userlist1.get(position);
                tvName.setText(district.getName());
//                tvPhone.setText("Diện tích: " + pond.getPond_area() + " m²");

//                Pond pondimage = userlist.get(position);
                return convertView;
            }
        };
        Districtlist.setAdapter(adapter1);
//        adapter.notifyDataSetChanged();
        Districtlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                District district = userlist1.get(position);
//                id_province = province.getId_province();
//                String abc = id_province;
//                getListDistrict(id_province);
                id_district = district.getId_district();
                btn_districts.setText(district.getName());
                dialog.dismiss();
            }
        });

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
        ImageView img_toolbar_backDisTricts = dialog.findViewById(R.id.img_toolbar_backDistrict);
        img_toolbar_backDisTricts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

//        getListProvince();

//        provincelist.setAdapter(adapter);
        dialog.show();

    }

    private void openDialogWard(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ward);

        Wardlist = dialog.findViewById(R.id.listWard);
        adapter2 = new ArrayAdapter<Ward>(this, 0, userlist2) {
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cardview_ward, null);
                TextView tvName = convertView.findViewById(R.id.name_ward);

                Ward ward = userlist2.get(position);
                tvName.setText(ward.getName());
//                tvPhone.setText("Diện tích: " + pond.getPond_area() + " m²");

//                Pond pondimage = userlist.get(position);
                return convertView;
            }
        };
        Wardlist.setAdapter(adapter2);
//        adapter.notifyDataSetChanged();
        Wardlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ward ward = userlist2.get(position);
//                id_province = province.getId_province();
//                String abc = id_province;
//                getListDistrict(id_province);
//                id_district = ward.getId_district();
                id_wards = ward.getId_ward();
                btn_wards.setText(ward.getName());
                dialog.dismiss();
            }
        });



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
//        getListProvince();

//        provincelist.setAdapter(adapter);
        ImageView img_toolbar_backWards = dialog.findViewById(R.id.img_toolbar_backWard);
        img_toolbar_backWards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public void getListProvince() {
        userlist.clear();

        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.LoadProvince(db);

            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_LONG).show();

            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);

                    String id_province = cursor.getString(0);
                    String name = cursor.getString(1);
                    Province province = new Province(id_province,name);
                    province.setId_province(id_province);
                    province.setName(name);
                    userlist.add(province);
                    cursor.moveToNext();
                }
            }
        }

        catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    public void getListDistrict(String id_province) {
        userlist1.clear();

        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.LoadDistrict(db,id_province);

            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//                  test = (ListView) findViewById(R.id.listitem);
                    String IDforDistrict = cursor.getString(0);
                    String name1 = cursor.getString(1);

                    District district = new District(IDforDistrict,name1);
                    district.setId_district(IDforDistrict);
                    district.setName(name1);

                    userlist1.add(district);
                    cursor.moveToNext();
                }
            }
        }

        catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    public void getListWard(String id_district) {
        userlist2.clear();

        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.LoadWard(db,id_district);

            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//                  test = (ListView) findViewById(R.id.listitem);
                    String IDforWard = cursor.getString(0);
                    String name = cursor.getString(1);

                    Ward ward = new Ward(IDforWard,name);
                    ward.setId_district(IDforWard);
                    ward.setName(name);

                    userlist2.add(ward);
                    cursor.moveToNext();
                }
            }
        }

        catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    public void registerEmployee() {

        try {
            hospitalDatabaseHelper = new DatabaseHelper(getApplicationContext());
            db = hospitalDatabaseHelper.getWritableDatabase();
            cursor = DatabaseHelper.selectAccount(db, HelperUtilities.filter(inputEmail.getText().toString()));
            isValid = isValidUserInput();

            if (isValid) {

                if (cursor != null && cursor.getCount() > 0) {

                    accountExistsAlert().show();

                } else {
                    cursor.moveToFirst();

                    DatabaseHelper.insertClient(db,
                            inputusername.getText().toString(),
                            getMd5Hash(inputpassword.getText().toString()),
                            inputfullname.getText().toString(),
                            inputEmail.getText().toString(),
                            inputphone.getText().toString(),
                            inputbirthday.getText().toString(),
                            inputaddress.getText().toString(),
                            id_wards);
                    DatabaseHelper.insertStoreHouse(db);
                    cursor.moveToNext();
                    registrationSuccessDialog().show();
                }

            }


        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean isValidUserInput() {
        if(HelperUtilities.isEmptyOrNull(inputusername.getText().toString())){
            inputusername.setError("Vui lòng nhập tên");
            return false;
        }else if(HelperUtilities.isName(inputusername.getText().toString())){
            inputusername.setError("Vui lòng nhập đúng định dạng tên");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(inputfullname.getText().toString())) {
            inputfullname.setError("Vui lòng nhập họ tên");
            return false;
        }else if (HelperUtilities.isName(inputfullname.getText().toString())) {
            inputfullname.setError("Vui lòng nhập họ tên đúng định dạng");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(inputEmail.getText().toString())) {
            inputEmail.setError("Vui lòng nhập email");
            return false;
        } else if (!HelperUtilities.isValidEmail(inputEmail.getText().toString())) {
            inputEmail.setError("Vui lòng nhập đúng định dạng email");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(inputphone.getText().toString())){
            inputphone.setError("Vui lòng nhập số điện thoại");
            return false;
        }else if(!HelperUtilities.isValidPhone(inputphone.getText().toString())){
            inputphone.setError("Vui lòng nhập đúng định dạng số điện thoại");
            return false;
        }else if (HelperUtilities.isShortPassword(inputpassword.getText().toString())) {
            inputpassword.setError("Vui lòng nhập mật khẩu nhiều hơn 8 ký tự và phải có số và chữ");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(inputConformPassword.getText().toString())) {
            inputConformPassword.setError("Vui lòng xác nhận mật khẩu mới của bạn");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(inputpassword.getText().toString())) {
            inputpassword.setError("Vui lòng nhập mật khẩu mới của bạn");
            return false;
        }else if (!inputpassword.getText().toString().equals(inputConformPassword.getText().toString())) {
            inputConformPassword.setError("Mật khẩu không trùng khớp");
            return false;
        }
        return true;

    }

    public Dialog accountExistsAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Email đã tồn tại. Vui lòng nhập lại Email!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), CheckMailOTPActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        return builder.create();
    }

    public Dialog registrationSuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thêm tài khoản thành công! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
    public String clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
    }

    private void Intro(){
        Intent intent = new Intent(this, IntroAcitvity.class);
        startActivity(intent);
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
