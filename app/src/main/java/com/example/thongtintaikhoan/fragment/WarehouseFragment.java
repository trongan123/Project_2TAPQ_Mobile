package com.example.thongtintaikhoan.fragment;

import android.annotation.SuppressLint;
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
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.controller.HistoryWareHouseActivity;
import com.example.thongtintaikhoan.controller.LoginActivity;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.model.Account;
import com.example.thongtintaikhoan.model.Item_category;
import com.example.thongtintaikhoan.model.Item_store_house;
import com.example.thongtintaikhoan.model.Pond;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarehouseFragment extends Fragment {
    private static final int FRAGMENT_HOME = 1;

    private static final int FRAGMENT_SETTING = 0;

    private static final int FRAGMENT_WAREHOUSE = 2;
    private BottomNavigationView mbottomNavigationView;
    private int mCurrentFragment = FRAGMENT_WAREHOUSE;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String StoreHouseID;
    private Intent intent;
    private ListView ListStoreHouse;
    private String id_user;
    private String ID_role;
    private String id_rolestaff;
    private String ID_account;
    private Spinner edt_Add_Loai_Kho;
    private ArrayAdapter<Item_store_house> adapter;
    List<Item_store_house> userlist = new ArrayList<>();
    private LinearLayout icon_HistoryWareHouse, icon_ThemSanPhamKho;
    List<Item_category> userlist1 = new ArrayList<>();
    private ImageButton  icon_LichKho, icon_Add_LichSuKho;
    private EditText edt_Add_TenSanPham_Kho,edt_Add_SoLuong_Kho,
            edt_Add_MoTa_Kho;
    private EditText edt_TenSanPham_WareHouse, edt_SoLuong_WareHouse,edt_MoTa_WareHouse;
    private Spinner edt_Loai_WareHouse;
    private String arruser[];
    private boolean isValid;
    private String ID_WareHouse;
    private String id_nv;
    private String NameForDelete;
    private String QuantilyForDelete;
    private String CategoryWareHouseForDelete;
    String id_role = String.valueOf(0);



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_warehouse, viewGroup, false);
        ListStoreHouse = view.findViewById(R.id.Lv_ListStoreHouse);

        String Staff_Pond = "RL00000002";
        id_user = clientID();
         id_nv = id_user;
        ID_role = clientRole();
        id_rolestaff = clientRolestaff();
        getID_StaffToAccount(ID_role);

        getListWarehouse(id_user);
        Collections.reverse(userlist);
        Collections.reverse(userlist1);

        icon_ThemSanPhamKho = view.findViewById(R.id.icon_ThemSanPhamKho);
        icon_ThemSanPhamKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddWareHouse(Gravity.CENTER);

            }
        });

        icon_Add_LichSuKho = view.findViewById(R.id.icon_Add_LichSuKho);
        icon_Add_LichSuKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddWareHouse(Gravity.CENTER);
            }
        });

        icon_LichKho = view.findViewById(R.id.icon_LichKho);
        icon_LichKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryWareHouseActivity.class);
                startActivity(intent);
            }
        });
        icon_HistoryWareHouse = view.findViewById(R.id.icon_HistoryWareHouse);
        icon_HistoryWareHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryWareHouseActivity.class);
                startActivity(intent);
            }
        });
        if(ID_role == id_role){
            adapter = new ArrayAdapter<Item_store_house>(getContext(), 0, userlist) {

                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                    convertView = inflater.inflate(R.layout.activity_item_history_house, null);
                    TextView tvName = convertView.findViewById(R.id.tv_NameItem_WareHouse);
                    TextView tvLoai = convertView.findViewById(R.id.tv_Loai_WareHouse);
                    TextView tvQuanlity = convertView.findViewById(R.id.tv_QUanlity_Warehouse);
                    TextView tvNote = convertView.findViewById(R.id.tv_LuuYWareHouse);

                    Button btn_edit_WareHouse = convertView.findViewById(R.id.btn_edit_WareHouse);
                    btn_edit_WareHouse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Item_store_house item_store_house = userlist.get(position);
                            ID_WareHouse = item_store_house.getId_item_store_house();
                            openDialogEditWareHouse(Gravity.CENTER);
                        }
                    });

                    Button btn_Delete_WareHouse = convertView.findViewById(R.id.btn_Delete_WareHouse);
                    btn_Delete_WareHouse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Item_store_house item_store_house = userlist.get(position);
                            Item_category item_category = userlist1.get(position);
                            ID_WareHouse = item_store_house.getId_item_store_house();
                            NameForDelete = item_store_house.getName();
                            QuantilyForDelete = item_store_house.getQuanlity();
                            CategoryWareHouseForDelete = item_category.getName();
                            openDialogDeleteWareHouse(Gravity.CENTER);
                        }
                    });

                    Item_store_house item_store_house = userlist.get(position);
                    Item_category item_category = userlist1.get(position);

                    tvName.setText(item_store_house.getName());
                    tvLoai.setText(item_category.getName());
                    tvQuanlity.setText(item_store_house.getQuanlity());
                    tvNote.setText(item_store_house.getNote());

                    return convertView;
                }
            };

        }else if(id_rolestaff.equals(Staff_Pond)){
            adapter = new ArrayAdapter<Item_store_house>(getContext(), 0, userlist) {

                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                    convertView = inflater.inflate(R.layout.activity_item_history_house, null);
                    TextView tvName = convertView.findViewById(R.id.tv_NameItem_WareHouse);
                    TextView tvLoai = convertView.findViewById(R.id.tv_Loai_WareHouse);
                    TextView tvQuanlity = convertView.findViewById(R.id.tv_QUanlity_Warehouse);
                    TextView tvNote = convertView.findViewById(R.id.tv_LuuYWareHouse);

                    Button btn_edit_WareHouse = convertView.findViewById(R.id.btn_edit_WareHouse);
                    btn_edit_WareHouse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Item_store_house item_store_house = userlist.get(position);
                            ID_WareHouse = item_store_house.getId_item_store_house();
                            openDialogEditWareHouse(Gravity.CENTER);
                        }
                    });

                    Button btn_Delete_WareHouse = convertView.findViewById(R.id.btn_Delete_WareHouse);
                    btn_Delete_WareHouse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Item_store_house item_store_house = userlist.get(position);
                            Item_category item_category = userlist1.get(position);
                            ID_WareHouse = item_store_house.getId_item_store_house();



                            NameForDelete = item_store_house.getName();
                            QuantilyForDelete = item_store_house.getQuanlity();
                            CategoryWareHouseForDelete = item_category.getName();
                            openDialogDeleteWareHouse(Gravity.CENTER);
                        }
                    });

                    Item_store_house item_store_house = userlist.get(position);
                    Item_category item_category = userlist1.get(position);

                    tvName.setText(item_store_house.getName());
                    tvLoai.setText(item_category.getName());
                    tvQuanlity.setText(item_store_house.getQuanlity());
                    tvNote.setText(item_store_house.getNote());

                    return convertView;
                }
            };

        }else{
            DialogArlet().show();

        }
        ListStoreHouse.setAdapter(adapter);


        return view;
    }

    public void getListWarehouse(String id_user) {
//        List<Pond> list = new ArrayList<>();
        userlist.clear();
        try {
            databaseHelper = new DatabaseHelper(getContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.selectItemWareHouse(db, id_user);


            if (cursor.getCount() == 0) {
                Toast.makeText(getContext(), "Kho trống", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);

                    String id_item_store_house = cursor.getString(0);
                    String name = cursor.getString(2);
                    String category = cursor.getString(7);
                    String quanlity = cursor.getString(3);
                    String note = cursor.getString(5);

                    Item_store_house item_store_house = new Item_store_house(id_item_store_house, name, quanlity, note);
                    Item_category item_category = new Item_category(category);
                    item_store_house.setName(name);
                    item_category.setName(category);
                    item_store_house.setQuanlity(quanlity);
                    item_store_house.setNote(note);

                    userlist.add(item_store_house);
                    userlist1.add(item_category);
                    cursor.moveToNext();


                }
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getContext(), "Database 4444unahggfgfvailable", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDialogAddWareHouse(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_history_warehouse);

        edt_Add_TenSanPham_Kho = dialog.findViewById(R.id.edt_Add_TenSanPham_Kho);
        edt_Add_SoLuong_Kho = dialog.findViewById(R.id.edt_Add_SoLuong_Kho);
        edt_Add_Loai_Kho = (Spinner) dialog.findViewById(R.id.edt_Add_Loai_Kho);
        edt_Add_MoTa_Kho = dialog.findViewById(R.id.edt_Add_MoTa_Kho);
        LoadItem_StoreHouse();

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

        Button btnHuy = dialog.findViewById(R.id.btn_HuyAdd_Kho);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        Button btnLuu = dialog.findViewById(R.id.btn_LuuAdd_Kho);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = isValidUserInput();
                if(isValid){
                    Add_History_WareHouse(id_user);
                    if(mCurrentFragment == FRAGMENT_WAREHOUSE){
                        replaceFragment(new WarehouseFragment());
                        mCurrentFragment = FRAGMENT_WAREHOUSE;
                    }
                    Collections.reverse(userlist);
                    Collections.reverse(userlist1);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void Add_History_WareHouse(String id_user) {
        String Staff_Pond = "RL00000002";
        try {

            databaseHelper = new DatabaseHelper(getContext());
            db = databaseHelper.getReadableDatabase();
            cursor.moveToFirst();

            DatabaseHelper.insertItem_StoreHouse(db,id_user,

                    edt_Add_TenSanPham_Kho.getText().toString(),
                    edt_Add_SoLuong_Kho.getText().toString(),
                    edt_Add_Loai_Kho.getSelectedItem().toString(),
                    edt_Add_MoTa_Kho.getText().toString());
            cursor.moveToNext();
            DatabaseHelper.insertItem_History_House(db,id_user,
                    edt_Add_TenSanPham_Kho.getText().toString(),
                    edt_Add_SoLuong_Kho.getText().toString(),
                    edt_Add_Loai_Kho.getSelectedItem().toString());
            cursor.moveToNext();
            DatabaseHelper.insertWareHouseAddPond(db,id_user);
            cursor.moveToNext();

            AddHistoryWareHouseSuccessDialog().show();
        } catch (SQLiteException ex) {
            Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    public void LoadItem_StoreHouse(){

        databaseHelper = new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        cursor = DatabaseHelper.LoadItemcategory(db);
        arruser =  new String[cursor.getCount()];
        cursor.moveToFirst();

        for(int i=0;i<arruser.length;i++){

            arruser[i]=cursor.getString(0);
            cursor.moveToNext();
        }
        ArrayAdapter<String> test1 = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arruser);
        edt_Add_Loai_Kho.setAdapter(test1);

    }

    public Dialog AddHistoryWareHouseSuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Thêm thành công")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ListStoreHouse.invalidateViews();
                    }
                });
        return builder.create();
    }

    private void openDialogEditWareHouse(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edt_warehouse);

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

        edt_TenSanPham_WareHouse = dialog.findViewById(R.id.edt_TenSanPham_WareHouse);
        edt_Loai_WareHouse = (Spinner) dialog.findViewById(R.id.edt_Loai_WareHouse);
        edt_SoLuong_WareHouse = dialog.findViewById(R.id.edt_SoLuong_WareHouse);
        edt_MoTa_WareHouse = dialog.findViewById(R.id.edt_MoTa_WareHouse);
        LoadEditItem_StoreHouse();

        Button btnHuy = dialog.findViewById(R.id.btn_HuyEdit_WareHouse);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnLuu = dialog.findViewById(R.id.btn_LuuEdit_WareHouse);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = isValidinputPond();
                if(isValid){
                    updateWareHouse(ID_WareHouse);
                    if(mCurrentFragment == FRAGMENT_WAREHOUSE){
                        replaceFragment(new WarehouseFragment());
                        mCurrentFragment = FRAGMENT_WAREHOUSE;
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void updateWareHouse(String ID_WareHouse) {
        try {

            databaseHelper = new DatabaseHelper(getContext());
            db = databaseHelper.getReadableDatabase();
            cursor.moveToFirst();
            DatabaseHelper.UpdateItem_StoreHouse(db,ID_WareHouse,

                    edt_TenSanPham_WareHouse.getText().toString(),
                    edt_SoLuong_WareHouse.getText().toString(),
                    edt_Loai_WareHouse.getSelectedItem().toString(),
                    edt_MoTa_WareHouse.getText().toString());
            DatabaseHelper.updateItem_History_House(db,id_user,
                    edt_TenSanPham_WareHouse.getText().toString(),
                    edt_SoLuong_WareHouse.getText().toString(),
                    edt_Loai_WareHouse.getSelectedItem().toString());
            cursor.moveToNext();
            AddHistoryWareHouseSuccessDialog().show();

        } catch (SQLiteException ex) {
            Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    public void getID_StaffToAccount(String ID_role){
        if(ID_role == id_role){

        }else{
            try {
                databaseHelper = new DatabaseHelper(getContext());
                db = databaseHelper.getReadableDatabase();
                cursor = DatabaseHelper.getID_accountforstaff(db, ID_role);


                if (cursor.getCount() == 0) {
                    Toast.makeText(getContext(), "Kho trống", Toast.LENGTH_LONG).show();
                } else {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);

                        ID_account = cursor.getString(0);
                        cursor.moveToNext();
                        id_user = ID_account;

                    }
                }

            } catch (SQLiteException ex) {
                Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void LoadEditItem_StoreHouse(){

        databaseHelper = new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
        cursor = DatabaseHelper.LoadItemcategory(db);
//       String fishname = cursor.getString(0);
        arruser =  new String[cursor.getCount()];
        cursor.moveToFirst();
//
        for(int i=0;i<arruser.length;i++){

            arruser[i]=cursor.getString(0);
            cursor.moveToNext();
        }
        ArrayAdapter<String> test1 = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arruser);
        edt_Loai_WareHouse.setAdapter(test1);
    }

    private void openDialogDeleteWareHouse(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_warehouse);
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

        Button btnHuy = dialog.findViewById(R.id.btn_Huy_Delete_WareHouse);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnLuu = dialog.findViewById(R.id.btn_DongY_Delete_WareHouse);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteWareHouse(ID_WareHouse);
                if(mCurrentFragment == FRAGMENT_WAREHOUSE){
                    replaceFragment(new WarehouseFragment());
                    mCurrentFragment = FRAGMENT_WAREHOUSE;
                }
                Collections.reverse(userlist);
                Collections.reverse(userlist1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public Dialog DialogArlet() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Không có quyền truy cập! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(mCurrentFragment != FRAGMENT_SETTING){
                            replaceFragment(new SettingFragment());
                            mCurrentFragment = FRAGMENT_SETTING;
                        }

//                        FragmentTransaction fr = getFragmentManager().beginTransaction();
//                        fr.replace(R.id.content_frame, new SettingFragment());
//                        fr.commit();

                    }
                });

        return builder.create();
    }

    private void DeleteWareHouse(String ID_WareHouse){
        try{
            databaseHelper = new DatabaseHelper(getContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.getIDWareHouseforDelete(db, ID_WareHouse);
            cursor.moveToFirst();
            DatabaseHelper.insertDeleteItem_History_House(db,id_user,
                    NameForDelete, QuantilyForDelete,
                    CategoryWareHouseForDelete);
            cursor.moveToNext();
            DatabaseHelper.insertNotifyDeleteReceipts(db,id_user);
            cursor.moveToNext();

        }catch (SQLiteException ex){
            Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
    public boolean isValidUserInput() {

        if (HelperUtilities.isEmptyOrNull(edt_Add_TenSanPham_Kho.getText().toString())) {
            edt_Add_TenSanPham_Kho.setError("Vui lòng nhập tên sản phẩm");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(edt_Add_SoLuong_Kho.getText().toString())) {
            edt_Add_SoLuong_Kho.setError("Vui lòng nhập số lượng");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(edt_Add_MoTa_Kho.getText().toString())) {
            edt_Add_MoTa_Kho.setError("Vui lòng nhập lượng cá ao");
            return false;
        }
        return  true;
    }
    public boolean isValidinputPond(){
        if(HelperUtilities.isEmptyOrNull(edt_TenSanPham_WareHouse.getText().toString())){
            edt_TenSanPham_WareHouse.setError("Vui lòng nhập tên sản phẩm");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edt_SoLuong_WareHouse.getText().toString())){
            edt_SoLuong_WareHouse.setError("Vui lòng nhập số lượng");
            return false;
        }else if(HelperUtilities.isEmptyOrNull(edt_MoTa_WareHouse.getText().toString())){
            edt_MoTa_WareHouse.setError("Vui lòng nhập mô tả");
            return false;
        }
        return true;

    }

    public String clientID() {
        LoginActivity.sharedPreferences = this.getActivity().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        id_user = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return id_user;
    }
    public String clientRole(){
        LoginActivity.sharedPreferences = this.getActivity().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_role = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLE, String.valueOf(0));
        return ID_role;
    }
    public String clientRolestaff(){
        LoginActivity.sharedPreferences = this.getActivity().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        id_rolestaff = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLESTAFF, String.valueOf(0));
        return id_rolestaff;
    }


}
