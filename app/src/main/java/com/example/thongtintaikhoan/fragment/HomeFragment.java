package com.example.thongtintaikhoan.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.thongtintaikhoan.Adapter.PondAdapter;
import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;
import com.example.thongtintaikhoan.controller.AddPondActivity;
import com.example.thongtintaikhoan.controller.InformationPondActivity;
import com.example.thongtintaikhoan.controller.LoginActivity;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.layoutdetail.DetailActivity;
import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.layoutdetail.User;
import com.example.thongtintaikhoan.model.Detail_Receipts_payments;
import com.example.thongtintaikhoan.model.Item_category;
import com.example.thongtintaikhoan.model.Item_store_house;
import com.example.thongtintaikhoan.model.Pond;
import com.example.thongtintaikhoan.myinterface.IClickItemPondListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private static final int FRAGMENT_HOME = 1;

    private static final int FRAGMENT_SETTING = 0;

    private static final int FRAGMENT_WAREHOUSE = 2;

    private int mCurrentFragment = FRAGMENT_HOME;

    private BottomNavigationView mbottomNavigationView;

    private RecyclerView rcvInfo;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String PondID;
    private boolean isValid;

    private  ListView test;
//    private  ArrayList<Pond> adapter;


    private Intent intent;
    private TextView test1;
    private ListView pondlist;
    private TextView itineraryMessage;
    private String ID_acc;
    private String ID_role;
    private String id_rolestaff;
    private String ID_account;
    String id_role = String.valueOf(0);
    RecyclerView rvPrograms;
    private ArrayAdapter<Pond> adapter;
    List<Pond> userlist = new ArrayList<>();
    private ImageView tvImage;
    private ImageView btn_ThemAo,btn_Reload;
    private EditText edt_SanLuong,edt_PondHarvest;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, viewGroup, false);

     pondlist = view.findViewById(R.id.lv_ListPond);
        ID_acc = clientID();
        ID_role = clientRole();
        id_rolestaff = clientRolestaff();
        getID_StaffToAccount(ID_role);



        getListPond(ID_acc);
        Collections.reverse(userlist);
//      getListImage(ID_acc);
        String Staff_Pond = "RL00000001";
        btn_ThemAo = view.findViewById(R.id.btn_ThemAo);
        btn_ThemAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID_role == id_role){
                    AddPond();
                }else if(Staff_Pond.equals(id_rolestaff)){
                    AddPond();
                } else{
                    DialogArlet().show();
                }
            }
        });
        btn_Reload = view.findViewById(R.id.btn_Reload);
        btn_Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListPond(ID_acc);
                Collections.reverse(userlist);
                adapter.notifyDataSetChanged();
            }
        });

        if(ID_role == id_role){
            adapter = new ArrayAdapter<Pond>(getContext(), 0, userlist) {


                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                    convertView = inflater.inflate(R.layout.item_fishpond, null);
                    TextView tvName = convertView.findViewById(R.id.tv_namepond1);
                    TextView tvPhone = convertView.findViewById(R.id.tv_description);
                    ImageView tvImage = convertView.findViewById(R.id.img_fishpond1);

                    ImageView tvThuHoach = convertView.findViewById(R.id.img_Thu_Hoach_ao);
                    tvThuHoach.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(ID_role == id_role){
                                Pond pond = userlist.get(position);
                                PondID = pond.getId_pond();
                                openDialogThuHoachPond(Gravity.CENTER);
                            }else if(id_rolestaff.equals(Staff_Pond)){
                                Pond pond = userlist.get(position);
                                PondID = pond.getId_pond();
                                openDialogThuHoachPond(Gravity.CENTER);
                            } else{
                                DialogArlet().show();
                            }


                        }
                    });

                    Pond pond = userlist.get(position);
                    tvName.setText("Tên ao: " + pond.getName());
                    tvPhone.setText("Diện tích: " + pond.getPond_area() + " m²");

//                Pond pondimage = userlist.get(position);
                    byte[] recordImage =  pond.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
                    if(bitmap != null){
                        tvImage.setImageBitmap(bitmap);
                    }
                    return convertView;
                }
            };
        }else if(id_rolestaff.equals(Staff_Pond)){
            adapter = new ArrayAdapter<Pond>(getContext(), 0, userlist) {


                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.data_item, null);
                    convertView = inflater.inflate(R.layout.item_fishpond, null);
                    TextView tvName = convertView.findViewById(R.id.tv_namepond1);
                    TextView tvPhone = convertView.findViewById(R.id.tv_description);
                    ImageView tvImage = convertView.findViewById(R.id.img_fishpond1);

                    ImageView tvThuHoach = convertView.findViewById(R.id.img_Thu_Hoach_ao);
                    tvThuHoach.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(ID_role == id_role){
                                Pond pond = userlist.get(position);
                                PondID = pond.getId_pond();
                                openDialogThuHoachPond(Gravity.CENTER);
                            }else if(id_rolestaff.equals(Staff_Pond)){
                                Pond pond = userlist.get(position);
                                PondID = pond.getId_pond();
                                openDialogThuHoachPond(Gravity.CENTER);
                            } else{
                                DialogArlet().show();
                            }
                        }
                    });

                    Pond pond = userlist.get(position);
                    tvName.setText("Tên ao: " + pond.getName());
                    tvPhone.setText("Diện tích: " + pond.getPond_area() + " m²");

//                Pond pondimage = userlist.get(position);
                    byte[] recordImage =  pond.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
                    if(bitmap != null){
                        tvImage.setImageBitmap(bitmap);
                    }
                    return convertView;
                }
            };
        } else{
            DialogArlet().show();
        }


        pondlist.setAdapter(adapter);
        pondlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Pond pond = userlist.get(position);
                PondID = pond.getId_pond();
                intent = new Intent(getContext(), InformationPondActivity.class);
                intent.putExtra("POND_ID",PondID);
                startActivity(intent);
            }
        });


     return view;


    }

    private void openDialogThuHoachPond(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thu_hoach_pond);

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

        edt_SanLuong = dialog.findViewById(R.id.edt_SanLuong_ThuHoachPond);
        edt_PondHarvest = dialog.findViewById(R.id.edt_SanLuong_ThuHoachPond);
        Button btnHuy = dialog.findViewById(R.id.btn_Huy_ThuHoach);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        Button btnPondHarvest = dialog.findViewById(R.id.btn_DongY_ThuHoach);
        btnPondHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = isValidUserInput();
                if(isValid){
                    Update_HarvestPond(PondID);
                    getListPond(ID_acc);
                    Collections.reverse(userlist);
                    dialog.dismiss();
                }
//                DeletePay(ID_Buy);
//                recreate();
            }
        });

        dialog.show();
    }


    public void getListPond(String ID_acc) {
        userlist.clear();

        try {

            databaseHelper = new DatabaseHelper(getContext());
            db = databaseHelper.getReadableDatabase();
            cursor = DatabaseHelper.selectClientJoinAccount(db, ID_acc);
            byte[] image = new byte[0];

            if (cursor.getCount() == 0) {
                Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_LONG).show();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
//            test = (ListView) findViewById(R.id.listitem);

                    String id_pond = cursor.getString(0);
                    String id_acc = cursor.getString(1);
                    String name = cursor.getString(3);
                    String pond_area = cursor.getString(4);
                    image = cursor.getBlob(5);

                    if(image != null){
                        Pond pond = new Pond(id_pond,id_acc, name, pond_area,image);
                        pond.setId_pond(id_pond);
                        pond.setName(name);
                        pond.setPond_area(pond_area);

                        userlist.add(pond);
                        cursor.moveToNext();
                    }
                }
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public void Update_HarvestPond(String PondID) {
        try {
            databaseHelper = new DatabaseHelper(getContext());
            db = databaseHelper.getWritableDatabase();
            cursor.moveToNext();
            DatabaseHelper.update_HarvestPond(db,PondID,
                    edt_PondHarvest.getText().toString()
              );

            DatabaseHelper.insertHarvestNotify(db,ID_acc);
            cursor.moveToNext();
        } catch (SQLiteException ex) {
            Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

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
                        ID_acc = ID_account;
                    }
                }

            } catch (SQLiteException ex) {
                Toast.makeText(getContext(), "co", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
    public boolean isValidUserInput() {

        if (HelperUtilities.isEmptyOrNull(edt_PondHarvest.getText().toString())) {
            edt_PondHarvest.setError("Vui lòng nhập sản lượng");
            return false;
        }
        return  true;
    }
    public void AddPond(){
        Intent intent = new Intent(getContext(), AddPondActivity.class);
        startActivity(intent);
    }
    public String clientID() {
        LoginActivity.sharedPreferences = this.getActivity().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        ID_acc = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ID, String.valueOf(0));
        return ID_acc;
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

