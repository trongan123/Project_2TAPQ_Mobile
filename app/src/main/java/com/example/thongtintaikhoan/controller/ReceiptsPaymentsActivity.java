package com.example.thongtintaikhoan.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Detail_Receipts_payments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceiptsPaymentsActivity extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String ID_acc;
    private String ID_role;
    private String id_rolestaff;
    private String ID_account;
    private boolean isValid;
    String id_role = String.valueOf(0);
    private ImageView img_QuayLai_QuanLyThu;
    private String ID_Buy;
    private ImageView btn_Them_QuanLyThu;
    private EditText edt_AddTenSanPham_QuanLyThu, edt_AddGia_QuanLyThu, edt_AddSoLuong_QuanLyThu,
            edt_AddNguoiNhap_QuanLyThu, editTenSanPham,editGia,editSoLuong,editNguoiNhap;


    private ArrayAdapter<Detail_Receipts_payments> adapter;
    private ListView ReceiptPayment;
    List<Detail_Receipts_payments> userlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts_payments);

        ID_acc = clientID();
        ID_role = clientRole();
        id_rolestaff = clientRolestaff();
        getID_StaffToAccount(ID_role);
        getReceiptPayment(ID_acc);
        Collections.reverse(userlist);



        btn_Them_QuanLyThu = findViewById(R.id.btn_Them_QuanLyThu);
        btn_Them_QuanLyThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddBuy(Gravity.CENTER);
            }
        });

        img_QuayLai_QuanLyThu = findViewById(R.id.img_QuayLai_QuanLyThu);
        img_QuayLai_QuanLyThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomePage();
            }
        });

        ReceiptPayment = findViewById(R.id.list_Receipt_payment);
        adapter = new ArrayAdapter<Detail_Receipts_payments>(this, 0, userlist) {

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                convertView = inflater.inflate(R.layout.item_receipt_payments, null);
                TextView tvName = convertView.findViewById(R.id.tv_rp_nameproduct);
                TextView tvPrice = convertView.findViewById(R.id.tv_rp_price);
                TextView tvTotal = convertView.findViewById(R.id.tv_rp_total);

                TextView tvQuanlity = convertView.findViewById(R.id.tv_rp_quantily);
                TextView tvNameStaff = convertView.findViewById(R.id.tv_rp_namestaff);
                TextView tvDate = convertView.findViewById(R.id.tv_rp_date);

                Button btnEdit = convertView.findViewById(R.id.btn_edit_Buy_QuanLyThu);

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Detail_Receipts_payments detail_receipts_payments = userlist.get(position);
                        ID_Buy = detail_receipts_payments.getId_detail_receipt_payment();
                        openDialogEditBuy(Gravity.CENTER);
                    }
                });

                Button btnDelete = convertView.findViewById(R.id.btn_Delete_Buy_QuanLyThu);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Detail_Receipts_payments detail_receipts_payments = userlist.get(position);
                        ID_Buy = detail_receipts_payments.getId_detail_receipt_payment();
                        openDialogDeleteBuy(Gravity.CENTER);
                    }
                });
                Detail_Receipts_payments detail_receipts_payments = userlist.get(position);
                int id = detail_receipts_payments.getPrice();
                int abc = detail_receipts_payments.getQuanlity();
                tvName.setText(detail_receipts_payments.getName());
                tvPrice.setText(String.valueOf(detail_receipts_payments.getPrice())+"k VND");
                tvQuanlity.setText(String.valueOf(detail_receipts_payments.getQuanlity()));
                tvTotal.setText(id*abc+"k VND");
                tvNameStaff.setText(detail_receipts_payments.getNamestaff());
                tvDate.setText(detail_receipts_payments.getDate());
                return convertView;
            }
        };
        ReceiptPayment.setAdapter(adapter);
    }

    public void getReceiptPayment(String ID_acc) {
            userlist.clear();
            try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getReceiptPayments(db, ID_acc);


            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);


                    String id_detail_receipt_payment = cursor.getString(0);
                    String name = cursor.getString(5);
                    int price = cursor.getInt(4);
                    int quantily = cursor.getInt(3);
                    String namestaff = cursor.getString(2);
                    String date = cursor.getString(8);
                    Detail_Receipts_payments detail_receipts_payments = new Detail_Receipts_payments(id_detail_receipt_payment, name, price,quantily,namestaff,date);
                    detail_receipts_payments.setId_detail_receipt_payment(id_detail_receipt_payment);
                    detail_receipts_payments.setName(name);
                    detail_receipts_payments.setPrice(price);
                    detail_receipts_payments.setQuanlity(quantily);
                    detail_receipts_payments.setNamestaff(namestaff);
                    detail_receipts_payments.setDate(date);

                    userlist.add(detail_receipts_payments);

                    cursor.moveToNext();
                }


//            return list;

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
    public String clientRole(){
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_role = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLE, String.valueOf(0));
        return ID_role;
    }
    public String clientRolestaff(){
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        id_rolestaff = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLESTAFF, String.valueOf(0));
        return id_rolestaff;
    }

    private void HomePage(){
        finish();
    }

    private void openDialogAddBuy(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_receipts_payments_buy);

        edt_AddNguoiNhap_QuanLyThu = dialog.findViewById(R.id.edt_AddNguoiNhap_QuanLyThu);
        edt_AddTenSanPham_QuanLyThu = dialog.findViewById(R.id.edt_AddTenSanPham_QuanLyThu);
        edt_AddGia_QuanLyThu = dialog.findViewById(R.id.edt_AddGia_QuanLyThu);
        edt_AddSoLuong_QuanLyThu = dialog.findViewById(R.id.edt_AddSoLuong_QuanLyThu);


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

        Button btnHuy = dialog.findViewById(R.id.btn_HuyAdd_QuanLyThu);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button btnLuu = dialog.findViewById(R.id.btn_LuuAdd_QuanLyThu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = isValidUserInput();
                if(isValid){
                    Add_Buy_Payments(ID_acc);
                    getReceiptPayment(ID_acc);
                    Collections.reverse(userlist);
                    dialog.dismiss();

                }

            }
        });
        dialog.show();

    }



    private void openDialogEditBuy(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_receipts_payments_buy);

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

         editTenSanPham = dialog.findViewById(R.id.edt_TenSanPham_QuanLyThu);
         editGia = dialog.findViewById(R.id.edt_Gia_QuanLyThu);
         editSoLuong = dialog.findViewById(R.id.edt_SoLuong_QuanLyThu);
         editNguoiNhap = dialog.findViewById(R.id.edt_NguoiNhap_QuanLyThu);

        Button btnHuy = dialog.findViewById(R.id.btn_Huy_QuanLyThu);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        Button btnLuu = dialog.findViewById(R.id.btn_Luu_QuanLyThu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = isValidinputPond();
                if(isValid){
                    updateBuy_Payments(ID_Buy);
                    getReceiptPayment(ID_acc);
                    Collections.reverse(userlist);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    private void openDialogDeleteBuy(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_receipts_payments_buy);

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
        Button btnHuy = dialog.findViewById(R.id.btn_Huy_Delete_QuanLyThu);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        Button btnLuu = dialog.findViewById(R.id.btn_DongY_Delete_QuanLyThu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeletePay(ID_Buy);
                dialog.dismiss();
                Collections.reverse(userlist);
                recreate();

            }
        });
        dialog.show();
    }

    private void DeletePay(String ID_Buy){
        try{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            cursor.moveToFirst();
            cursor = DatabaseHelper.getIDBuyforDelete(db, ID_Buy);
            cursor.moveToNext();
            DatabaseHelper.insertNotifyDeleteReceipts(db,ID_acc);
            cursor.moveToNext();

        }catch (SQLiteException ex){
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    public void Add_Buy_Payments(String ID_acc) {

        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();


            cursor.moveToFirst();
            cursor = DatabaseHelper.getTest(db, ID_acc);
            if(cursor.getCount()==0){
                DatabaseHelper.getReceiptsPayments(db,ID_acc);
            }
            DatabaseHelper.insertBuy_Payments(db,ID_acc,

                    edt_AddNguoiNhap_QuanLyThu.getText().toString(),
                    edt_AddSoLuong_QuanLyThu.getText().toString(),
                    edt_AddGia_QuanLyThu.getText().toString(),
                    edt_AddTenSanPham_QuanLyThu.getText().toString());
            DatabaseHelper.insertReceiptsPayNotify(db,ID_acc);
            cursor.moveToNext();
            AddBuySuccessDialog().show();

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public Dialog AddBuySuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thêm thành công")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        recreate();
                    }
                });

        return builder.create();
    }

    public void updateBuy_Payments(String ID_Buy) {
        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getWritableDatabase();
            cursor.moveToFirst();

                 DatabaseHelper.updateBuy_Payments(db,ID_Buy,
                        editNguoiNhap.getText().toString(),
                        editSoLuong.getText().toString(),
                        editGia.getText().toString(),
                        editTenSanPham.getText().toString());
            DatabaseHelper.updateReceiptsPayNotify(db,ID_acc);
            cursor.moveToNext();
                 updateBuyDialogPayments().show();

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    public Dialog updateBuyDialogPayments() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cập nhật thành công! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                        startActivity(intent);
//                        recreate();
                    }
                });

        return builder.create();
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

    public boolean isValidUserInput() {
        if (HelperUtilities.isEmptyOrNull(edt_AddTenSanPham_QuanLyThu.getText().toString())) {
            edt_AddTenSanPham_QuanLyThu.setError("Vui lòng nhập tên sản phẩm");
            return false;
        }else if(!HelperUtilities.isString(edt_AddTenSanPham_QuanLyThu.getText().toString())){
            edt_AddTenSanPham_QuanLyThu.setError("Vui lòng nhập đúng định dạng tên");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(edt_AddGia_QuanLyThu.getText().toString())) {
            edt_AddGia_QuanLyThu.setError("Vui lòng nhập giá");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(edt_AddSoLuong_QuanLyThu.getText().toString())) {
            edt_AddSoLuong_QuanLyThu.setError("Vui lòng nhập số lượng");
            return false;
        } else if(HelperUtilities.isEmptyOrNull(edt_AddNguoiNhap_QuanLyThu.getText().toString())) {
            edt_AddNguoiNhap_QuanLyThu.setError("Vui lòng nhập tên người nhập");
            return false;
        }else if(!HelperUtilities.isString(edt_AddNguoiNhap_QuanLyThu.getText().toString())){
            edt_AddNguoiNhap_QuanLyThu.setError("Vui lòng nhập đúng định dạng tên");
            return false;
        }
        return  true;
    }
    public boolean isValidinputPond(){

        if(HelperUtilities.isEmptyOrNull(editTenSanPham.getText().toString())){
            editTenSanPham.setError("Vui lòng nhập tên sản phẩm");
            return false;
        }else if (!HelperUtilities.isString(editTenSanPham.getText().toString())){
            editTenSanPham.setError("Vui lòng nhập đúng định dạng tên");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(editGia.getText().toString())){
            editGia.setError("Vui lòng nhập giá");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(editSoLuong.getText().toString())){
            editSoLuong.setError("Vui lòng nhập số lượng");
            return false;
        }else if (HelperUtilities.isEmptyOrNull(editNguoiNhap.getText().toString())){
            editNguoiNhap.setError("Vui lòng nhập tên sản phẩm");
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
}