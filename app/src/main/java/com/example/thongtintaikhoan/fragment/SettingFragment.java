package com.example.thongtintaikhoan.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.thongtintaikhoan.MainActivity;
import com.example.thongtintaikhoan.R;
import com.example.thongtintaikhoan.controller.HistoryWareHouseActivity;
import com.example.thongtintaikhoan.controller.HomepageActivity;
import com.example.thongtintaikhoan.controller.ListNotifyActivity;
import com.example.thongtintaikhoan.controller.LoginActivity;
import com.example.thongtintaikhoan.controller.ReceiptsPaymentsActivity;
import com.example.thongtintaikhoan.controller.ReceiptsPaymentsPayActivity;
import com.example.thongtintaikhoan.controller.ShowHistoryPondActivity;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class SettingFragment extends Fragment {
    private static final int FRAGMENT_HOME = 0;
    //1
    private static final int FRAGMENT_ACCOUNT = 1;
    //3
    private static final int FRAGMENT_SETTING = 0;
    //4
    private static final int FRAGMENT_WAREHOUSE = 3;
    private int mCurrentFragment = FRAGMENT_SETTING;
    private ImageView img_Ao;
    private View mView;
    private MainActivity mainActivity;
    private ImageView img_button_receipt;
    private Intent intent;
    private ImageView img_button_receipt_pay;
    private ImageView img_WareHouse;
    private ImageView img_backgound_Inbox;
    private ImageView img_backgound_map;
    private int status;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String ID_acc;
    private String ID_role;
    private String id_rolestaff;
    public String name_client;
    public TextView tv_ThongTinTenDangNhapVaos;
    private BottomNavigationView mbottomNavigationView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_homepage, container, false);
        mainActivity = (MainActivity) getActivity();

        ID_acc = clientID();
        ID_role = clientRole();
        name_client = clientName();
        id_rolestaff = clientRolestaff();

        tv_ThongTinTenDangNhapVaos = mView.findViewById(R.id.tv_ThongTinTenDangNhapVao);
        tv_ThongTinTenDangNhapVaos.setText("Hi "+name_client);
//        getListImage(ID_acc);
        String Staff_Pond = "RL00000003";
        String Staff_PondWareHouse = "RL00000002";
        String id_role = String.valueOf(0);

        img_backgound_Inbox = mView.findViewById(R.id.img_backgound_Inbox);
        img_backgound_Inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), ListNotifyActivity.class);
                updateStatus();
                startActivity(getActivity().getIntent());
                startActivity(intent);
            }
        });

        img_WareHouse = mView.findViewById(R.id.img_WareHouse);
        img_WareHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID_role == id_role){
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.content_frame, new WarehouseFragment());
                    fr.commit();
                }else if(id_rolestaff.equals(Staff_PondWareHouse)){
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.content_frame, new WarehouseFragment());
                    fr.commit();
                } else{
                    DialogArlet().show();
                }


            }
        });

        img_Ao = mView.findViewById(R.id.img_Ao);
        img_Ao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.content_frame, new HomeFragment());
                fr.commit();
            }
        });
        img_backgound_map = mView.findViewById(R.id.img_backgound_maps);
        img_backgound_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID_role == id_role){
                    intent = new Intent(getContext(), HistoryWareHouseActivity.class);
                    startActivity(intent);
                }else{
                    DialogArlet().show();
                }
            }
        });

        img_button_receipt_pay = mView.findViewById(R.id.img_backgound_Logout);
        img_button_receipt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ID_role == id_role){
                    intent = new Intent(getContext(), ReceiptsPaymentsPayActivity.class);
                    startActivity(intent);
                }else if(id_rolestaff.equals(Staff_Pond)){
                    intent = new Intent(getContext(), ReceiptsPaymentsPayActivity.class);
                    startActivity(intent);
                } else{
                    DialogArlet().show();
                }

            }
        });

        img_button_receipt = mView.findViewById(R.id.img_backgound_settings);
        img_button_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID_role == id_role){
                    intent = new Intent(getContext(), ReceiptsPaymentsActivity.class);
                    startActivity(intent);
                }else if(id_rolestaff.equals(Staff_Pond)){
                    intent = new Intent(getContext(), ReceiptsPaymentsActivity.class);
                    startActivity(intent);
                } else{
                    DialogArlet().show();
                }
            }
        });
        return mView;
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    public void updateStatus() {
        try {

            databaseHelper = new DatabaseHelper(getContext());
            db = databaseHelper.getWritableDatabase();

            ID_acc = clientID();

            DatabaseHelper.updateStatus(db,
                    getStatus(),ID_acc);

        } catch (SQLiteException ex) {
            Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

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
    public String clientName(){
        LoginActivity.sharedPreferences = this.getActivity().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        name_client = LoginActivity.sharedPreferences.getString(LoginActivity.NAME_CLIENT, String.valueOf(0));
        return name_client;
    }

    private static String getStatus(){
        String status1 = "2";
        return status1;
    }
    public Dialog DialogArlet() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Không có quyền truy cập! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}
