package com.example.thongtintaikhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thongtintaikhoan.controller.AboutApp;
import com.example.thongtintaikhoan.controller.ChangePassword;
import com.example.thongtintaikhoan.controller.HarvestedPondActivity;
import com.example.thongtintaikhoan.controller.ListNotifyActivity;
import com.example.thongtintaikhoan.controller.LoginActivity;
import com.example.thongtintaikhoan.controller.ManageStaffActivity;
import com.example.thongtintaikhoan.controller.ProfileActivity;
import com.example.thongtintaikhoan.database.DatabaseHelper;
import com.example.thongtintaikhoan.fragment.HomeFragment;
import com.example.thongtintaikhoan.fragment.SettingFragment;
import com.example.thongtintaikhoan.fragment.WarehouseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;


//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_store_house);
//    }
//}


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final int FRAGMENT_HOME = 1;

    private static final int FRAGMENT_SETTING = 0;

    private static final int FRAGMENT_WAREHOUSE = 2;

    private int mCurrentFragment = FRAGMENT_SETTING;

    private BottomNavigationView mbottomNavigationView;

    private ImageView image;
    private int status;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String ID_acc;
    private String ID_role;
    private ImageView btn_notify;
    private Intent intent;
    public TextView tv_NameAccount_Mains;
    public TextView tv_EmailAccount_Mains;
    public String name_client;
    public String email_client;
    String Staff_PondWareHouse = "RL00000001";
    private String id_rolestaff;

    private RecyclerView rcvData;

    private DrawerLayout mDrawerLayout;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, new SettingFragment());
        fragmentTransaction.commit();

        NavigationView navigationViews = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigationViews.getHeaderView(0);
        mbottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close );
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new SettingFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        mbottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);

        image =  findViewById(R.id.widget_title_icon);
        btn_notify = findViewById(R.id.btn_ThongBao);
        ID_acc = clientID();
        ID_role = clientRole();
        id_rolestaff = clientRolestaff();
        name_client = client_name();
        email_client = client_email();
        tv_NameAccount_Mains = (TextView) headerView.findViewById(R.id.tv_NameAccount_Main);
        tv_NameAccount_Mains.setText(name_client);
        tv_EmailAccount_Mains = (TextView) headerView.findViewById(R.id.tv_EmailAccount_Main);
        tv_EmailAccount_Mains.setText(email_client);



        getStatusNotify(ID_acc);

        if(status == 1){
            image.setVisibility(View.VISIBLE);}
        else if(status == 2){
            image.setVisibility(View.INVISIBLE);
        }

        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), ListNotifyActivity.class);
                updateStatus();
                startActivity(getIntent());
                startActivity(intent);

            }
        });

        mbottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_ao){

                    openHomeFragment();
//                    navigationView.getMenu().findItem(R.id.bottom_ao).setChecked(true);

                }
//                else if(id == R.id.bottom_account){
////                    openAccountFragment();
////                    navigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
//                }
                else if(id == R.id.bottom_home){
//                    navigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                    openSettingFragment();


                }else if(id == R.id.bottom_warehouse){
//                    navigationView.getMenu().findItem(R.id.bottom_warehouse).setChecked(true);
                    openWarehouseFragment();


                }
                return true;
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            openHomeFragment();
            mbottomNavigationView.getMenu().findItem(R.id.bottom_ao).setChecked(true);
        }else if(id == R.id.nav_account){
//            openAccountFragment();
//            mbottomNavigationView.getMenu().findItem(R.id.bottom_account).setChecked(true);
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_changepassword){
            String id_role = String.valueOf(0);
            if(ID_role == id_role){
                Intent intent = new Intent(this, ChangePassword.class);
                startActivity(intent);
                finish();
            }else{
                ArletSuccessDialog().show();
            }
        }else if(id == R.id.nav_AoDaThuHoach){
            String id_role = String.valueOf(0);
            if(ID_role == id_role){
                Intent intent = new Intent(this, HarvestedPondActivity.class);
                startActivity(intent);
                finish();
            }else if(id_rolestaff.equals(Staff_PondWareHouse)){
                Intent intent = new Intent(this, HarvestedPondActivity.class);
                startActivity(intent);
                finish();
            } else{
                ArletSuccessDialog().show();
            }


        }
        else if(id == R.id.nav_QuanLyNhanVien){
            String id_role = String.valueOf(0);
            if(ID_role == id_role){
                Intent intent = new Intent(this, ManageStaffActivity.class);
                startActivity(intent);
                finish();
            }else{
                ArletSuccessDialog().show();
            }
        }

        else if(id == R.id.nav_information){
            Intent intent = new Intent(this, AboutApp.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_dang_xuat){
            getApplicationContext().getSharedPreferences(LoginActivity.MY_PREFERENCES, 0).edit().clear().commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openHomeFragment(){
        if(mCurrentFragment != FRAGMENT_HOME){
            replaceFragment(new HomeFragment());
            mCurrentFragment = FRAGMENT_HOME;
            mbottomNavigationView.getMenu().findItem(R.id.bottom_ao).setChecked(true);

        }
    }
    private void openWarehouseFragment(){
        if(mCurrentFragment != FRAGMENT_WAREHOUSE){
            replaceFragment(new WarehouseFragment());
            mCurrentFragment = FRAGMENT_WAREHOUSE;
            mbottomNavigationView.getMenu().findItem(R.id.bottom_warehouse).setChecked(true);
        }
    }
    private void openSettingFragment(){
        if(mCurrentFragment != FRAGMENT_SETTING){
            replaceFragment(new SettingFragment());
            mCurrentFragment = FRAGMENT_SETTING;
            mbottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
        }
    }

//    private void openAccountFragment(){
//        if(mCurrentFragment != FRAGMENT_ACCOUNT){
//            replaceFragment(new AccountFragment());
//            mCurrentFragment = FRAGMENT_ACCOUNT;
//        }
//    }


    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    public void getStatusNotify(String ID_acc){
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        cursor = DatabaseHelper.getStatusNotify(db, ID_acc);

        if(cursor.getCount()==0){
            status = 2;
        }else {
            cursor.moveToFirst();
            int getstatus = cursor.getInt(0);

            status = getstatus;

        }
    }
    public Dialog ArletSuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Không có quyền truy cập! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }


    public void updateStatus() {
        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getWritableDatabase();

            ID_acc = clientID();

            DatabaseHelper.updateStatus(db,
                    getStatus(),ID_acc);

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    private static String getStatus(){
        String status1 = "2";
        return status1;
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
    public String client_name(){
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        name_client = LoginActivity.sharedPreferences.getString(LoginActivity.NAME_CLIENT, String.valueOf(0));
        return name_client;
    }
    public String client_email(){
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        email_client = LoginActivity.sharedPreferences.getString(LoginActivity.EMAIL, String.valueOf(0));
        return email_client;
    }
    public String clientRolestaff(){
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        id_rolestaff = LoginActivity.sharedPreferences.getString(LoginActivity.CLIENT_ROLESTAFF, String.valueOf(0));
        return id_rolestaff;
    }



}
