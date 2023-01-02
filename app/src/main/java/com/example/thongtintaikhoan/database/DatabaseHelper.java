package com.example.thongtintaikhoan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.thongtintaikhoan.HelperUtils.HelperUtilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.time.LocalDate;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "dbluanvan";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "account");
        db.execSQL("DROP TABLE IF EXISTS " + "cooperative_room");
        db.execSQL("DROP TABLE IF EXISTS " + "detail_receipts_payments");
        db.execSQL("DROP TABLE IF EXISTS " + "district");
        db.execSQL("DROP TABLE IF EXISTS " + "fish_category");
        db.execSQL("DROP TABLE IF EXISTS " + "history_store_house");
        db.execSQL("DROP TABLE IF EXISTS " + "item_category");
        db.execSQL("DROP TABLE IF EXISTS " + "item_store_house");
        db.execSQL("DROP TABLE IF EXISTS " + "member");
        db.execSQL("DROP TABLE IF EXISTS " + "notify");
        db.execSQL("DROP TABLE IF EXISTS " + "pond");
        db.execSQL("DROP TABLE IF EXISTS " + "pond_diary");
        db.execSQL("DROP TABLE IF EXISTS " + "province");
        db.execSQL("DROP TABLE IF EXISTS " + "quantity_house");
        db.execSQL("DROP TABLE IF EXISTS " + "receipts_payments");
        db.execSQL("DROP TABLE IF EXISTS " + "role");
        db.execSQL("DROP TABLE IF EXISTS " + "role_staff");
        db.execSQL("DROP TABLE IF EXISTS " + "store_house");
        db.execSQL("DROP TABLE IF EXISTS " + "ward");

        updateDatabase(db, oldVersion, newVersion);

    }
    private void updateDatabase(SQLiteDatabase db, int oldVersion, int dbVersion) {

        if (oldVersion < 1) {

            db.execSQL(createaccount());
            db.execSQL(createcooperative_room());
            db.execSQL(createdetail_receipts_payments());
            db.execSQL(createdistrict());
            db.execSQL(createfish_category());
            db.execSQL(createhistory_store_house());
            db.execSQL(createitem_category());
            db.execSQL(createitem_store_house());
            db.execSQL(createmember());
            db.execSQL(createnotify());
            db.execSQL(createpond());
            db.execSQL(createpond_diary());
            db.execSQL(createprovince());
            db.execSQL(createquantity_house());
            db.execSQL(createreceipts_payments());
            db.execSQL(createrole());
            db.execSQL(createrole_staff());
            db.execSQL(createstore_house());
            db.execSQL(createward());


        }
    }





    public String createaccount(){
        return "CREATE TABLE account (" +
                "id_acc TEXT NOT NULL," +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "fullname TEXT NOT NULL, " +
                "email TEXT, " +
                "phone TEXT, " +
                "birthday TEXT, " +
                "address TEXT NOT NULL, " +
                "role INTEGER NOT NULL, " +
                "id_role_staff TEXT, " +
                "id_ward TEXT NOT NULL, " +
                "date_join TEXT NOT NULL, " +
                "image TEXT, " +
                "status INTEGER, " +

                "FOREIGN KEY(id_role_staff) REFERENCES role_staff(id_role_staff), "+
                "FOREIGN KEY(id_ward) REFERENCES ward(id_ward), "+
                "PRIMARY KEY(id_acc));" ;


    }
    public String createcooperative_room(){
        return "CREATE TABLE cooperative_room (" +
                "id_room TEXT NOT NULL," +
                "id_coo TEXT NOT NULL, " +
                "join_code TEXT NOT NULL, " +
                "pond_area DOUBLE, " +
                "status INTEGER, " +

                "FOREIGN KEY(id_coo) REFERENCES account(id_acc), "+
                "PRIMARY KEY(id_room));" ;


    }
    public String createdetail_receipts_payments(){
        return "CREATE TABLE detail_receipts_payments (" +
                "id_detail_receipts_payments TEXT NOT NULL," +
                "id_invoice TEXT NOT NULL, " +
                "namestaff TEXT, " +
                "quanlity DOUBLE, " +
                "price DOUBLE, " +
                "name TEXT, " +
                "description TEXT, " +
                "status INTEGER, " +
                "date datetime, " +

                "FOREIGN KEY(id_invoice) REFERENCES receipts_payments(id_invoice), "+
                "PRIMARY KEY(id_detail_receipts_payments));" ;
    }
    public String createdistrict(){
        return "CREATE TABLE district (" +
                "id_district TEXT NOT NULL," +
                "name TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "id_province TEXT NOT NULL, " +
                "status INTEGER, " +

                "FOREIGN KEY(id_province) REFERENCES province(id_province), "+
                "PRIMARY KEY(id_district));" ;
    }
    public String createfish_category(){
        return "CREATE TABLE fish_category (" +
                "id_fcategory TEXT NOT NULL," +
                "category_name TEXT NOT NULL, " +
                "image TEXT, " +
                "harvest_time INTEGER NOT NULL, " +
                "description TEXT, " +
                "status INTEGER, " +
                "ph DOUBLE, " +
                "temperature DOUBLE, " +
                "water_level DOUBLE, " +
                "sanility DOUBLE, " +

                "PRIMARY KEY(id_fcategory));" ;
    }
    public String createhistory_store_house(){
        return "CREATE TABLE history_store_house (" +
                "id_history_store_house TEXT NOT NULL," +
                "id_staff TEXT NOT NULL, " +
                "id_s_house TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "quanlity DOUBLE, " +
                "id_item_category TEXT NOT NULL, " +
                "note TEXT, " +
                "date DOUBLE, " +
                "status INTEGER NOT NULL, " +

                "FOREIGN KEY(id_staff) REFERENCES account(id_acc), "+
                "FOREIGN KEY(id_item_category) REFERENCES item_category(id_item_category), "+
                "FOREIGN KEY(id_s_house) REFERENCES store_house(id_s_house), "+
                "PRIMARY KEY(id_history_store_house));" ;
    }
    public String createitem_category(){
        return "CREATE TABLE item_category (" +
                "id_item_category TEXT NOT NULL," +
                "name TEXT NOT NULL, " +
                "status INTEGER, " +

                "PRIMARY KEY(id_item_category));" ;
    }
    public String createitem_store_house(){
        return "CREATE TABLE item_store_house (" +
                "id_item_store_house TEXT NOT NULL," +
                "id_s_house TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "quanlity DOUBLE NOT NULL, " +
                "id_item_category TEXT NOT NULL, " +
                "note TEXT, " +

                "FOREIGN KEY(id_item_category) REFERENCES item_category(id_item_category), "+
                "FOREIGN KEY(id_s_house) REFERENCES store_house(id_s_house), "+
                "PRIMARY KEY(id_item_store_house));" ;
    }
    public String createmember(){
        return "CREATE TABLE member (" +
                "id_member TEXT NOT NULL," +
                "id_user TEXT NOT NULL, " +
                "id_room TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "status INTEGER , " +


                "FOREIGN KEY(id_room) REFERENCES cooperative_room(id_room), "+
                "PRIMARY KEY(id_member));" ;
    }
    public String createnotify(){
        return "CREATE TABLE notify (" +
                "id_notify TEXT NOT NULL," +
                "id_acc TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "description TEXT, " +
                "date TEXT , " +
                "status INTEGER, " +

                "FOREIGN KEY(id_acc) REFERENCES account(id_acc), "+
                "PRIMARY KEY(id_notify));" ;
    }
    public String createpond(){
        return "CREATE TABLE pond (" +
                "id_pond TEXT NOT NULL," +
                "id_acc TEXT NOT NULL, " +
                "id_fcategory TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "pond_area DOUBLE, " +
                "image TEXT, " +
                "session TEXT, " +
                "quantity_of_fingerlings DOUBLE, " +
                "quanlity_of_end DOUBLE, " +
                "start_day TEXT, " +
                "end_day TEXT, " +
                "status INTEGER, " +

                "FOREIGN KEY(id_acc) REFERENCES account(id_acc), "+
                "FOREIGN KEY(id_fcategory) REFERENCES fish_category(id_fcategory), "+
                "PRIMARY KEY(id_pond));" ;
    }
    public String createpond_diary(){
        return "CREATE TABLE pond_diary (" +
                "id_diary TEXT NOT NULL," +
                "id_pond TEXT NOT NULL, " +
                "sanility DOUBLE, " +
                "ph DOUBLE, " +
                "temperature DOUBLE, " +
                "water_level DOUBLE, " +
                "fish_status TEXT, " +
                "date TEXT, " +

                "FOREIGN KEY(id_pond) REFERENCES pond(id_pond), "+
                "PRIMARY KEY(id_diary));" ;
    }
    public String createprovince(){
        return "CREATE TABLE province (" +
                "id_province TEXT NOT NULL," +
                "name TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "status INTEGER, " +

                "PRIMARY KEY(id_province));" ;
    }
    public String createquantity_house(){
        return "CREATE TABLE quantity_house (" +
                "id_quantity TEXT NOT NULL," +
                "id_acc TEXT NOT NULL, " +
                "quanlity DOUBLE NOT NULL, " +
                "added_date TEXT, " +

                "FOREIGN KEY(id_acc) REFERENCES account(id_acc), "+
                "PRIMARY KEY(id_quantity));" ;
    }
    public String createreceipts_payments(){
        return "CREATE TABLE receipts_payments (" +
                "id_invoice TEXT NOT NULL," +
                "id_user TEXT NOT NULL, " +
                "total DOUBLE, " +
                "added_date TEXT NOT NULL, " +
                "status INTEGER NOT NULL, " +

                "FOREIGN KEY(id_user) REFERENCES account(id_acc), "+
                "PRIMARY KEY(id_invoice));" ;
    }
    public String createrole(){
        return "CREATE TABLE role (" +
                "id_role TEXT NOT NULL," +
                "type TEXT NOT NULL, " +
                "status INTEGER, " +

                "PRIMARY KEY(id_role));" ;
    }
    public String createrole_staff(){
        return "CREATE TABLE role_staff (" +
                "id_role_staff TEXT NOT NULL," +
                "id_acc TEXT NOT NULL, " +
                "id_role TEXT NOT NULL, " +
                "salary DOUBLE, " +
                "status INTEGER, " +

                "FOREIGN KEY(id_role) REFERENCES role(id_role), "+
                "PRIMARY KEY(id_role_staff));" ;
    }
    public String createstore_house(){
        return "CREATE TABLE store_house (" +
                "id_s_house TEXT NOT NULL," +
                "id_user TEXT NOT NULL, " +
                "status INTEGER, " +

                "FOREIGN KEY(id_user) REFERENCES account(id_acc), "+
                "PRIMARY KEY(id_s_house));" ;
    }
    public String createward(){
        return "CREATE TABLE ward (" +
                "id_ward TEXT NOT NULL," +
                "name TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "id_district TEXT NOT NULL, " +
                "status INTEGER, " +

                "FOREIGN KEY(id_district) REFERENCES district(id_district), "+
                "PRIMARY KEY(id_ward));" ;
    }

    public static void insertaccount(SQLiteDatabase db, String email, String password  ) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("email", email);
        accountValues.put("password", password);

        db.insert("account", null, accountValues);

    }
    public static Cursor login(SQLiteDatabase db, String email, String password) {
        return db.query("account", new String[]{"id_acc", "username", "password", "fullname", "email", "phone", "birthday", "address", "role", "id_role_staff",
                        "id_ward", "date_join", "image", "status"},
                "email = ? AND password = ? ", new String[]{email, password},
                null, null, null, null);
    }
    public static Cursor selectClientJoinAccount(SQLiteDatabase db, String ID_acc) {
        return db.rawQuery("SELECT * FROM pond JOIN account " +
                "ON pond.id_acc = account.id_acc " +
                "WHERE pond.status = 1 AND pond.id_acc = '" + ID_acc + "'", null);
    }
    public static Cursor selectPondOfHarvest(SQLiteDatabase db, String ID_acc) {
        return db.rawQuery("SELECT * FROM pond JOIN account " +
                "ON pond.id_acc = account.id_acc " +
                "WHERE pond.status = 2 AND pond.id_acc = '" + ID_acc + "'", null);
    }

    public static Cursor selectImagePond(SQLiteDatabase db, String ID_acc) {
        return db.rawQuery("SELECT * FROM pond " +
                "WHERE " +
                "id_acc = '" + ID_acc + "'", null);
    }


    public static Cursor selectItemWareHouse(SQLiteDatabase db, String id_user){
        return db.rawQuery("SELECT * FROM item_store_house JOIN item_category ON item_store_house.id_item_category =  item_category.id_item_category " +
                "JOIN store_house ON item_store_house.id_s_house = store_house.id_s_house " +
                "WHERE store_house.id_user = '" + id_user + "'", null);
    }
    public static Cursor selectItinerary(SQLiteDatabase db, String ID_acc) {
        return db.rawQuery(" SELECT name, pond_area " +
                "FROM pond " +
                "JOIN account " +
                "ON account.id_acc = pond.id_acc " +
                "WHERE " +
                "pond.id_acc = " + ID_acc, null);


//        id_pond, id_acc, id_fcategory, name, pond_area, image, session, quantity_of_fingerlings, quanlity_of_end, start_day, end_day, status


    }

    public static Cursor selectProfile(SQLiteDatabase db, String ID_acc, byte[] image) {
        return db.query("account", new String[]{"username","fullname","email","phone","address","image"}, "id_acc = ? ",
                new String[]{(ID_acc)}, null, null,
                null, null);
    }
    public static void updateClient(SQLiteDatabase db, String username, String fullname,  String email,String phone,String address, String ID_acc) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("username", HelperUtilities.capitalize(username.toLowerCase()));
        clientValues.put("fullname", HelperUtilities.capitalize(fullname.toLowerCase()));
        clientValues.put("phone",phone);
        clientValues.put("email", email);
        clientValues.put("address", address);
        db.update("account", clientValues, "id_acc = ?", new String[]{String.valueOf(ID_acc)});
    }

    public static void updateBuy_Payments(SQLiteDatabase db,String ID_Buy, String namestaff, String quanlity,  String price,String name) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("namestaff", namestaff);
        clientValues.put("quanlity", quanlity);
        clientValues.put("price",price);
        clientValues.put("name", name);
        db.update("detail_receipts_payments", clientValues, "id_detail_receipts_payments = ?", new String[]{String.valueOf(ID_Buy)});
    }

    public static void update_HarvestPond(SQLiteDatabase db,String PondID, String quanlity_of_end) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("quanlity_of_end", quanlity_of_end);
        clientValues.put("status",getStatus2());
        db.update("pond", clientValues, "id_pond = ?", new String[]{String.valueOf(PondID)});
    }


    public static void updatePay_Payments(SQLiteDatabase db,String ID_Pay, String namestaff, String quanlity,  String price,String name) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("namestaff", namestaff);
        clientValues.put("quanlity", quanlity);
        clientValues.put("price",price);
        clientValues.put("name", name);
        db.update("detail_receipts_payments", clientValues, "id_detail_receipts_payments = ?", new String[]{String.valueOf(ID_Pay)});
    }
    public static void updateRoll_Staff(SQLiteDatabase db,String ID_acc,String role_staff,String salary) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_role",getid_category_staff(db,role_staff));
        clientValues.put("salary",salary);
        db.update("role_staff", clientValues, "id_role_staff = ?", new String[]{String.valueOf(ID_acc)});
    }
    public static void updateHistoryPond(SQLiteDatabase db,String ID_diary,String matdonuoc,String doman,String pH, String nhietdo) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("water_level",matdonuoc);
        clientValues.put("sanility",doman);
        clientValues.put("ph",pH);
        clientValues.put("temperature",nhietdo);
        db.update("pond_diary", clientValues, "id_diary = ?", new String[]{String.valueOf(ID_diary)});
    }
    public static void updateAccountStaff(SQLiteDatabase db,String ID_acc,String username,String name,String email,String phone,String birthday) {
        ContentValues clientValues = new ContentValues();

        clientValues.put("username",username);
        clientValues.put("fullname",name);
        clientValues.put("email", email);
        clientValues.put("phone", phone);
        clientValues.put("birthday", birthday);

        db.update("account", clientValues, "id_acc = ?", new String[]{String.valueOf(ID_acc)});
    }


    public static Cursor selectClientID(SQLiteDatabase db,String id_acc, String username, String password, String fullname, String email, String phone,
                                        String birthday, String address, String role, String id_ward, String date_join, String status) {
        return db.query("account", null,
                "id_acc = ? AND username = ? AND password = ? AND fullname = ? AND email = ? AND phone = ? AND birthday = ? AND address = ? AND role = ? AND id_ward = ? AND date_join = ? AND status = ?",
                new String[]{id_acc, username, password, fullname, email, phone, birthday, address, role, id_ward ,date_join, status},
                null, null, null, null);
    }

    public static void insertClient(SQLiteDatabase db, String username, String password, String fullname, String email, String phone,
                                    String birthday, String address,String ID_ward) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_acc",getID_acc(db));
        clientValues.put("username",username);
        clientValues.put("password",password);
        clientValues.put("fullname",fullname);
        clientValues.put("email", email);
        clientValues.put("phone", phone);
        clientValues.put("birthday", birthday);
        clientValues.put("address", address);
        clientValues.put("role", getRole());
        clientValues.put("id_ward", ID_ward);
        clientValues.put("date_join",getDateTime());
//        clientValues.put("status",status);
        clientValues.put("status",getStatus());
        db.insert("account", null, clientValues);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void insertPond(SQLiteDatabase db, String ID_acc, String session, String name
            , String pond_area, byte[] image, String quantity_of_fingerlings, String id_fcategory ) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_pond",getID_Pond(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("id_fcategory",getFishCategory(db,id_fcategory));
        clientValues.put("name",name);
        clientValues.put("pond_area",pond_area);
        clientValues.put("image",image);
        clientValues.put("session",session);
        clientValues.put("quantity_of_fingerlings",quantity_of_fingerlings);
        clientValues.put("start_day",getDateTime());
        clientValues.put("end_day",getEndDateTime(db,id_fcategory));
        clientValues.put("status", getStatus());
        db.insert("pond", null, clientValues);
    }

    public static void insertBuy_Payments(SQLiteDatabase db,String ID_acc,String namestaff,String quanlity, String price,String name) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_detail_receipts_payments",getID_Buy_Payments(db));
        clientValues.put("id_invoice",getID_invoice(db,ID_acc));
        clientValues.put("namestaff",namestaff);
        clientValues.put("quanlity",quanlity);
        clientValues.put("price",price);
        clientValues.put("name",name);
        clientValues.put("status", getStatus());
        clientValues.put("date", getDateTime());
        db.insert("detail_receipts_payments", null, clientValues);
    }

    public static void AddHistoryPond(SQLiteDatabase db,String PondID,String ID_fcategory,String sanility,String ph, String temperature,String water_level) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_diary",getID_History_Pond(db));
        clientValues.put("id_pond",getID_Pond(db,PondID));
        clientValues.put("sanility",sanility);
        clientValues.put("ph",ph);
        clientValues.put("temperature",temperature);
        clientValues.put("water_level",water_level);
        clientValues.put("fish_status", getStatusPond_diary(db,ID_fcategory,Double.parseDouble(temperature),Double.parseDouble(water_level),Double.parseDouble(ph),Double.parseDouble(sanility)));
        clientValues.put("date", getDateTime());
        db.insert("pond_diary", null, clientValues);
    }
    public static void insertAccount_Staff(SQLiteDatabase db,String username,String password,String name,String email,String phone,String birthday) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_acc",getID_acc(db));
        clientValues.put("username",username);
        clientValues.put("password",password);
        clientValues.put("fullname",name);
        clientValues.put("email", email);
        clientValues.put("phone", phone);
        clientValues.put("birthday", birthday);
        clientValues.put("address", getAddress());
        clientValues.put("role", getRole());
        clientValues.put("id_role_staff", getID_Role_Staffs(db));
        clientValues.put("id_ward", getId_ward());
        clientValues.put("date_join", getDateTime());
        clientValues.put("status",getStatus());

        db.insert("account", null, clientValues);
    }

    public static void insertItem_StoreHouse(SQLiteDatabase db,String id_user,String name,String quanlity,String id_item_category, String note) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_item_store_house",getID_id_item_storeHouse(db));
        clientValues.put("id_s_house",getID_id_s_house(db,id_user));
        clientValues.put("name",name);
        clientValues.put("quanlity",quanlity);
        clientValues.put("id_item_category", getid_item_category(db,id_item_category));
        clientValues.put("note",note);
        db.insert("item_store_house", null, clientValues);
    }
    public static void insertItem_History_House(SQLiteDatabase db,String id_user,String name,String quanlity,String id_item_category) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_history_store_house",getID_id_item_History_Store_House(db));
        clientValues.put("id_staff",id_user);
        clientValues.put("id_s_house",getID_id_s_house(db,id_user));
        clientValues.put("name",name);
        clientValues.put("quanlity",quanlity);
        clientValues.put("id_item_category",getid_item_category(db,id_item_category));
        clientValues.put("note",getNote_History_Add_Store_House());
        clientValues.put("date",getDateTime());
        clientValues.put("status",getStatus());
        db.insert("history_store_house ", null, clientValues);
    }
    public static void updateItem_History_House(SQLiteDatabase db,String id_user,String name,String quanlity,String id_item_category) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_history_store_house",getID_id_item_History_Store_House(db));
        clientValues.put("id_staff",id_user);
        clientValues.put("id_s_house",getID_id_s_house(db,id_user));
        clientValues.put("name",name);
        clientValues.put("quanlity",quanlity);
        clientValues.put("id_item_category",getid_item_category(db,id_item_category));
        clientValues.put("note",getNote_History_update_Store_House());
        clientValues.put("date",getDateTime());
        clientValues.put("status",getStatus());
        db.insert("history_store_house ", null, clientValues);
    }
    public static void insertDeleteItem_History_House(SQLiteDatabase db,String id_user,String name,String quanlity,String id_item_category) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_history_store_house",getID_id_item_History_Store_House(db));
        clientValues.put("id_staff",id_user);
        clientValues.put("id_s_house",getID_id_s_house(db,id_user));
        clientValues.put("name",name);
        clientValues.put("quanlity",quanlity);
        clientValues.put("id_item_category",getid_item_category(db,id_item_category));
        clientValues.put("note",getNote_History_Delete_Store_House());
        clientValues.put("date",getDateTime());
        clientValues.put("status",getStatus());
        db.insert("history_store_house ", null, clientValues);
    }

    public static void UpdateItem_StoreHouse(SQLiteDatabase db,String ID_WareHouse,String name,String quanlity,String id_item_category, String note) {
        ContentValues clientValues = new ContentValues();

        clientValues.put("name",name);
        clientValues.put("quanlity",quanlity);
        clientValues.put("id_item_category", getid_item_category(db,id_item_category));
        clientValues.put("note",note);
        db.update("item_store_house" , clientValues, "id_item_store_house = ?", new String[]{String.valueOf(ID_WareHouse)});
    }

    public static void insertPay_Payments(SQLiteDatabase db,String ID_acc,String namestaff,String quanlity, String price,String name) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_detail_receipts_payments",getID_Buy_Payments(db));
        clientValues.put("id_invoice",getID_invoice(db,ID_acc));
        clientValues.put("namestaff",namestaff);
        clientValues.put("quanlity",quanlity);
        clientValues.put("price",price);
        clientValues.put("name",name);
        clientValues.put("status", getStatusPay());
        clientValues.put("date", getDateTime());
        db.insert("detail_receipts_payments", null, clientValues);
    }
    public static void getReceiptsPayments(SQLiteDatabase db,String ID_acc) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_invoice",getID_invoicePayment(db));
        clientValues.put("id_user",ID_acc);
        clientValues.put("added_date",getDateTime());
        clientValues.put("status",getStatus());


        db.insert("receipts_payments", null, clientValues);
    }
    public static void insertNotifyAddPond(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getTypeAddPond());
        clientValues.put("description",getDescription());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void insertReceiptsPayNotify(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getReceiptAddPond());
        clientValues.put("description",getDescription());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void updateReceiptsPaymentPayNotify(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getReceiptPaymentPay());
        clientValues.put("description",getDescription());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void updateReceiptsPayNotify(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getReceiptUpdateNotify());
        clientValues.put("description",getDescription());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void insertNotifyDeleteReceipts(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getReceiptDeleteItemWareHouse());
        clientValues.put("description",getDescription1());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void insertNotifyDeleteItemWareHouse(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getReceiptDelete());
        clientValues.put("description",getDescription1());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void insertNotifyDeleteHarvest(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getHarvestDelete());
        clientValues.put("description",getDescription1());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void insertNotifyDeleteReceiptsPay(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getReceiptDeletePay());
        clientValues.put("description",getDescription1());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void insertReceiptsPaymentPayAddPond(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getReceiptAddPond());
        clientValues.put("description",getDescription());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void insertHarvestNotify(SQLiteDatabase db,String ID_acc){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("type",getDescribeHarvest());
        clientValues.put("description",getDescription());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void insertWareHouseAddPond(SQLiteDatabase db,String id_user){
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_notify",getID_notify(db));
        clientValues.put("id_acc",id_user);
        clientValues.put("type",getWareHouseAddPond());
        clientValues.put("description",getDescription());
        clientValues.put("date",getDateTime1());
        clientValues.put("status",getStatus());

        db.insert("notify ", null, clientValues);
    }
    public static void updateClientImage(SQLiteDatabase db, byte[] image, String PondID) {
        ContentValues employeeValues = new ContentValues();
        employeeValues.put("image", image);
        db.update("pond", employeeValues, " id_pond = ? ", new String[]{PondID});
    }
    public static void updateImageAccount(SQLiteDatabase db, byte[] image, String ID_acc) {
        ContentValues employeeValues = new ContentValues();
        employeeValues.put("image", image);
        db.update("account", employeeValues, " id_acc = ? ", new String[]{ID_acc});
    }
    public static Cursor selectImage(SQLiteDatabase db, String PondID) {
        return db.query("pond", new String[]{"image"}, "id_pond = ? ",
                new String[]{PondID}, null, null,
                null, null);
    }
    public static Cursor selectImageAccount(SQLiteDatabase db, String ID_acc) {
        return db.query("account", new String[]{"image"}, "id_acc = ? ",
                new String[]{ID_acc}, null, null,
                null, null);
    }

//    public static void insertPond(SQLiteDatabase db, String session, String name
//            , String pond_area, String quantity_of_fingerlings, String id_fcategory, String ID_acc) {
//        ContentValues clientValues = new ContentValues();
//        clientValues.put("id_pond",username);
//        clientValues.put("id_acc",getID_acc(db));
//        clientValues.put("id_fcategory",username);
//        clientValues.put("name",password);
//        clientValues.put("pond_area",fullname);
//        clientValues.put("image",username);
//        clientValues.put("session", email);
//        clientValues.put("quantity_of_fingerlings", phone);
//        clientValues.put("start_day", birthday);
//        clientValues.put("end_day", address);
//        clientValues.put("status", getRole());
//        db.insert("pond", null, clientValues);
//    }
    public static Cursor selectClientPassword(SQLiteDatabase db, String ID_acc) {
        return db.query("account", new String[]{"password"}, "id_acc = ? ",
                new String[]{ID_acc}, null, null,
                null, null);
    }
    public static void ChangePassword(SQLiteDatabase db, String password){
        ContentValues clientValues = new ContentValues();
        clientValues.put("password",password);
        db.insert("account", null, clientValues);
    }
    public static void updatePassword(SQLiteDatabase db, String password, String ID_acc) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("password", password);
        db.update("account", clientValues, " id_acc = ? ", new String[]{ID_acc});
    }
    public static void updatePasswordFromEmail(SQLiteDatabase db, String password, String email) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("password", password);
        db.update("account", clientValues, " email = ? ", new String[]{email});
    }
    public static void updateStatus(SQLiteDatabase db, String status, String ID_acc) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("status",status);
        db.update("notify", clientValues, "id_acc = ?", new String[]{String.valueOf(ID_acc)});
    }
    public static Cursor getPondDetail(SQLiteDatabase db, String PondID) {
        return db.rawQuery("SELECT * FROM pond " +
                "JOIN fish_category " +
                "ON pond.id_fcategory = fish_category.id_fcategory " +
                "WHERE " +
                "pond.id_pond = '" + PondID + "'", null);
    }
    public static Cursor getStatusNotify(SQLiteDatabase db, String ID_acc){
        return db.rawQuery("SELECT status FROM notify WHERE status = 1 AND id_acc = '" + ID_acc + "'", null);
    }
    public static Cursor getStaff(SQLiteDatabase db, String ID_acc){
        return db.rawQuery("SELECT account.username,email,phone, role.type ,role_staff.id_role_staff,salary FROM account JOIN role_staff ON account.id_role_staff = role_staff.id_role_staff JOIN role ON role.id_role = role_staff.id_role WHERE role_staff.id_acc = '" + ID_acc + "'", null);
    }
    public static Cursor getStaffDialog(SQLiteDatabase db, String ID_acc){
        return db.rawQuery("SELECT account.id_acc,username,email,password,fullname,phone,birthday,address, role.type ,role_staff.id_role_staff,salary FROM account JOIN role_staff ON account.id_role_staff = role_staff.id_role_staff JOIN role ON role.id_role = role_staff.id_role WHERE account.email = '" + ID_acc + "'", null);
    }
    public static Cursor getDiaryPondDialog(SQLiteDatabase db, String ID_diary){
        return db.rawQuery("SELECT * FROM pond_diary WHERE id_diary = '" + ID_diary + "'", null);
    }
    public static Cursor getIDPondforDelete(SQLiteDatabase db, String PondID){
        return db.rawQuery("DELETE FROM pond WHERE id_pond = '" + PondID + "'", null);
    }
    public static Cursor DeleteDiaryPond(SQLiteDatabase db, String PondID){
        return db.rawQuery("DELETE FROM pond_diary WHERE id_pond = '" + PondID + "'", null);
    }
    public static Cursor DeleteDiaryPondHarvest(SQLiteDatabase db, String PondID){
        return db.rawQuery("DELETE FROM pond_diary WHERE id_pond = '" + PondID + "'", null);
    }
    public static Cursor getIDPayforDelete(SQLiteDatabase db, String ID_Pay){
        return db.rawQuery("DELETE FROM detail_receipts_payments WHERE status = 2 AND id_detail_receipts_payments = '" + ID_Pay + "'", null);
    }
    public static Cursor getIDBuyforDelete(SQLiteDatabase db, String ID_Buy){
        return db.rawQuery("DELETE FROM detail_receipts_payments WHERE status = 1 AND id_detail_receipts_payments = '" + ID_Buy + "'", null);
    }
    public static Cursor getIDforDeleteNotifyAfterDay(SQLiteDatabase db, String ID_acc){
        return db.rawQuery("DELETE FROM notify WHERE date < DATETIME('NOW','-30 days')  AND id_acc = '" + ID_acc + "'", null);
    }
    public static Cursor getIDRoleStaffforDelete(SQLiteDatabase db, String id_role_staff){
        return db.rawQuery("DELETE FROM role_staff WHERE id_role_staff =  '" + id_role_staff + "'", null);
    }
    public static Cursor DeleteStaffAccount(SQLiteDatabase db, String id_role_staff){
        return db.rawQuery("DELETE FROM account WHERE id_role_staff =  '" + id_role_staff + "'", null);
    }
    public static Cursor getIDWareHouseforDelete(SQLiteDatabase db, String ID_WareHouse){
        return db.rawQuery("DELETE FROM item_store_house WHERE id_item_store_house = '" + ID_WareHouse + "'", null);
    }
    public static Cursor getReceiptPayments(SQLiteDatabase db, String ID_acc){
        return db.rawQuery("SELECT * FROM detail_receipts_payments JOIN receipts_payments ON detail_receipts_payments.id_invoice = receipts_payments.id_invoice WHERE detail_receipts_payments.status = 1 AND id_user  = '" + ID_acc + "'", null);
    }
    public static Cursor getHistoryPond(SQLiteDatabase db, String PondID){
        return db.rawQuery("SELECT * FROM pond_diary WHERE id_pond = '" + PondID + "'", null);
    }
    public static Cursor getTest(SQLiteDatabase db,String ID_acc){
        return db.rawQuery("SELECT id_user FROM receipts_payments WHERE id_user = '" + ID_acc + "'", null);
    }
    public static Cursor getHistoryWareHouse(SQLiteDatabase db, String ID_acc){
        return db.rawQuery("SELECT * FROM history_store_house JOIN account ON history_store_house.id_staff = account.id_acc WHERE id_acc = '" + ID_acc + "'", null);
    }
    public static Cursor LoadCategoryStaff(SQLiteDatabase db){
        return db.rawQuery("SELECT type FROM role", null);
    }
    public static Cursor Loadfishcategory(SQLiteDatabase db){
        return db.rawQuery("SELECT category_name FROM fish_category", null);
    }
    public static Cursor LoadItemcategory(SQLiteDatabase db){
        return db.rawQuery("SELECT name FROM item_category", null);
    }
    public static Cursor getReceiptPaymentsPay(SQLiteDatabase db, String ID_acc){
        return db.rawQuery("SELECT * FROM detail_receipts_payments JOIN receipts_payments ON detail_receipts_payments.id_invoice = receipts_payments.id_invoice WHERE detail_receipts_payments.status = 2 AND id_user  = '" + ID_acc + "'", null);
    }
    public static Cursor getNotify(SQLiteDatabase db, String ID_acc){
        return db.rawQuery("SELECT * FROM notify JOIN account ON account.id_acc = notify.id_acc WHERE notify.id_acc = '" + ID_acc + "'", null);
    }
    public static Cursor LoadProvince(SQLiteDatabase db){
        return db.rawQuery("SELECT * FROM province", null);
    }

    public static Cursor LoadDistrict(SQLiteDatabase db,String id_province){
        return db.rawQuery("SELECT * FROM district WHERE id_province ='" + id_province + "'" , null);
    }
    public static Cursor LoadWard(SQLiteDatabase db,String id_district){
        return db.rawQuery("SELECT * FROM ward WHERE id_district ='" + id_district + "'" , null);
    }
    private static String getFishCategory(SQLiteDatabase db,String input){
        String sql = "SELECT id_fcategory FROM fish_category WHERE category_name ='"+ input +"'";
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToNext();
        input =cursor.getString(0);
        return input;
    }
    private static String getid_item_category(SQLiteDatabase db,String input){
        String sql = "SELECT id_item_category FROM item_category WHERE name ='"+ input +"'";
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToFirst();
        input =cursor.getString(0);
        return input;
    }
    public static void insertRoles_Staff(SQLiteDatabase db,String ID_acc,String role_staff,String salary) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_role_staff",getID_Role_Staff(db));
        clientValues.put("id_acc",ID_acc);
        clientValues.put("id_role",getid_category_staff(db,role_staff));
        clientValues.put("salary",salary);
        clientValues.put("status", getStatus());

        db.insert("role_staff", null, clientValues);
    }

    public static Cursor selectAccount(SQLiteDatabase db, String email) {
        return db.query("account", null, " email = ? ",
                new String[]{email}, null, null, null, null);
    }
    public static Cursor getID_role1(SQLiteDatabase db, String ID_role){
        return db.rawQuery("SELECT id_role FROM role_staff WHERE id_role_staff = '" + ID_role + "'", null);

    }
    public static Cursor getID_accountforstaff(SQLiteDatabase db, String ID_role){
        return db.rawQuery("SELECT id_acc FROM role_staff WHERE id_role_staff = '" + ID_role + "'", null);

    }



    public static void insertStoreHouse(SQLiteDatabase db) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("id_s_house",getID_s_house(db));
        clientValues.put("id_user",getID_accfsth(db));
        clientValues.put("status",getStatus());

        db.insert("store_house", null, clientValues);
    }

    private static String getID_acc(SQLiteDatabase db){
        String sql = "SELECT id_acc FROM account WHERE id_acc = (SELECT MAX(id_acc) FROM account)";
        Cursor cursor= db.rawQuery(sql,null);

        if(cursor.getCount()==0 ){
            String Id = "A000000001";
            String IDcuoi = Id;
            return IDcuoi;
        }else{
            cursor.moveToNext();
            String abc =cursor.getString(0);



            String Id = abc;
            int c=Integer.parseInt(Id.substring(1));

            String IDcuoi=String.format("A%09d",c+1);

            return IDcuoi;

        }



    }

    private static String getID_notify(SQLiteDatabase db){
        String sql = "SELECT id_notify FROM notify WHERE id_notify = (SELECT MAX(id_notify) FROM notify)";
        Cursor cursor= db.rawQuery(sql,null);

        if(cursor.getCount()==0){
            String Id = "N000000001";
            String IDcuoi = Id;
            return IDcuoi;
        }else{    cursor.moveToNext();
            String abc =cursor.getString(0);

            String Id = abc;
            int c=Integer.parseInt(Id.substring(1));

            String IDcuoi=String.format("N%09d",c+1);

            return IDcuoi;
        }
    }
    private static String getID_Role_Staff(SQLiteDatabase db){
        String sql = "SELECT id_role_staff FROM role_staff WHERE id_role_staff = (SELECT MAX(id_role_staff) FROM role_staff)";
        Cursor cursor= db.rawQuery(sql,null);

        if(cursor.getCount()==0){

            String Id = "RS00000001";
            String IDcuoi = Id;

            return IDcuoi;
        }else{    cursor.moveToNext();
            String abc =cursor.getString(0);

            String Id = abc;
            int c=Integer.parseInt(Id.substring(2));

            String IDcuoi=String.format("RS%08d",c+1);

            return IDcuoi;
        }
    }

    private static String getID_invoice(SQLiteDatabase db, String ID_acc){
        String sql = "SELECT id_invoice FROM receipts_payments WHERE id_user ='"+ ID_acc +"'";
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToNext();
        String abc =cursor.getString(0);
        return abc;
    }
    private static String getid_category_staff(SQLiteDatabase db,String input){
        String sql = "SELECT id_role FROM role WHERE type ='"+ input +"'";
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToNext();
        input =cursor.getString(0);
        return input;
    }

    private static String getID_Pond(SQLiteDatabase db, String PondID){
        String sql = "SELECT id_pond FROM pond WHERE id_pond = '"+ PondID +"'";
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToNext();
        String abc =cursor.getString(0);
        return abc;
    }

    private static String getID_id_s_house(SQLiteDatabase db, String ID_acc){
        String sql = "SELECT id_s_house FROM store_house WHERE id_user ='"+ ID_acc +"'";
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToNext();
        String abc =cursor.getString(0);

        return abc;


    }

    private static String getID_accfsth(SQLiteDatabase db){
        String sql = "SELECT id_acc FROM account WHERE id_acc = (SELECT MAX(id_acc) FROM account)";
        Cursor cursor= db.rawQuery(sql,null);

        cursor.moveToNext();
        String abc =cursor.getString(0);
        String Id = abc;
        int c=Integer.parseInt(Id.substring(1));
        String IDcuoi=String.format("A%09d",c);
        return IDcuoi;

    }

    private static String getID_Pond(SQLiteDatabase db){
        String sql = "SELECT id_pond FROM pond WHERE id_pond = (SELECT MAX(id_pond) FROM pond)";
        Cursor cursor= db.rawQuery(sql,null);

        if(cursor.getCount()==0){

            String Id = "P000000001";
            String IDcuoi = Id;

            return IDcuoi;
        }else{
            cursor.moveToNext();
            String abc =cursor.getString(0);
            String Id = abc;
            int c=Integer.parseInt(Id.substring(1));
            String IDcuoi=String.format("P%09d",c+1);
            return IDcuoi;
        }
    }

    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private static String getDateTime1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh-mm-ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private static String getStatus(){
        String status = "1";
        return status;
    }
    private static String getStatus2(){
        String status = "2";
        return status;
    }
    private static String getStatusPond_diary(SQLiteDatabase db,String ID_fcategory,double temperature
            ,double water_level,double ph,double sanility){

        String sql = "SELECT * FROM fish_category WHERE id_fcategory = '"+ ID_fcategory +"'";
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToNext();

        double nd = cursor.getDouble(7);
        double mdn = cursor.getDouble(8);
        double dp = cursor.getDouble(6);
        double dm = cursor.getDouble(9);

        double NhietDo = temperature;
        double MatDoNuoc = water_level;
        double DopH = ph;
        double DoMan = sanility;

        if(NhietDo >= (nd +5) || NhietDo <= (nd -5) ){
            String status = "2";
            return status;
        }else if(MatDoNuoc >= (mdn +1) || MatDoNuoc <= (mdn -1)){
            String status = "2";
            return status;
        }else if(DopH >= (dp + 1) || DopH <= (dp - 1)){
            String status = "2";
            return status;
        }else if(DoMan >= (dm + 1)|| DoMan <= (dm -1)){
            String status = "2";
            return status;
        }else {
            String status = "1";
            return status;
        }
    }

    private static String getStatusPay(){
        String status = "2";
        return status;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getEndDateTime(SQLiteDatabase db,String id_fcategory) {
        String sql = "SELECT * FROM fish_category WHERE category_name = '"+ id_fcategory +"'";
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToNext();
        String time_harvest = cursor.getString(3);
        int abc = Integer.parseInt(time_harvest);
        LocalDate date = LocalDate.now();
        // Displaying date

        LocalDate newDate = date.plusDays(abc);
        String avc = String.valueOf(newDate);
        return avc;
    }
    private static String getAddress(){
        String address = "can tho";
        return address;
    }

    private static String getId_ward(){
        String id_ward = "000001";
        return id_ward;
    }
    private static String getTypeAddPond(){
        String type = "Đã tạo thêm ao thành công";
        return type;
    }
    private static String getReceiptAddPond(){
        String type = "Đã tạo hóa đơn mua thành công";
        return type;
    }
    private static String getReceiptPaymentPay(){
        String type = "Đã cập nhật hóa đơn mua thành công";
        return type;
    }


    private static String getReceiptUpdateNotify(){
        String type = "Đã cập nhật hóa đơn mua thành công";
        return type;
    }
    private static String getReceiptDelete(){
        String type = "Đã xóa hóa đơn thành công";
        return type;
    }
    private static String getReceiptDeleteItemWareHouse(){
        String type = "Đã xóa hóa sản phẩm kho";
        return type;
    }
    private static String getHarvestDelete(){
        String type = "Đã xóa ao thu hoạch thành công";
        return type;
    }
    private static String getReceiptDeletePay(){
        String type = "Đã xóa hóa đơn mua thành công";
        return type;
    }
    private static String getDescribeHarvest(){
        String type = "Đã thu hoạch ao thành công";
        return type;
    }
    private static String getWareHouseAddPond(){
        String type = "Đã thêm sản phẩm thành công";
        return type;
    }
    private static String getDescription(){
        String description = "Đang hoạt động";
        return description;
    }
    private static String getDescription1(){
        String description = "Thành công";
        return description;
    }
    private static String getRole(){
        String id_role = "2";
        return id_role;
    }
    private static String getNote_History_Add_Store_House(){
        String Note = "Thêm kho thành công";
        return Note;
    }
    private static String getNote_History_update_Store_House(){
        String Note = "Cập nhật kho thành công";
        return Note;
    }
    private static String getNote_History_Delete_Store_House(){
        String Note = "Xóa Kho thành công";
        return Note;
    }

    private static String getID_s_house(SQLiteDatabase db){
        String sql = "SELECT id_s_house FROM store_house WHERE id_s_house = (SELECT MAX(id_s_house) FROM store_house)";
        Cursor cursor= db.rawQuery(sql,null);

        if(cursor.getCount()==0){
            String Id = "SH00000001";
            String IDcuoi = Id;
            return IDcuoi;
        }else{
            cursor.moveToNext();
            String abc =cursor.getString(0);
            String Id = abc;
            int c=Integer.parseInt(Id.substring(2));
            String IDcuoi=String.format("SH%08d",c+1);
            return IDcuoi;
        }

    }

    private static String getID_Buy_Payments(SQLiteDatabase db){
        String sql = "SELECT id_detail_receipts_payments FROM detail_receipts_payments WHERE id_detail_receipts_payments = (SELECT MAX(id_detail_receipts_payments) FROM detail_receipts_payments)";
        Cursor cursor= db.rawQuery(sql,null);
        if(cursor.getCount()==0){

            String Id = "DRP0000001";
            String IDcuoi = Id;
            return IDcuoi;

        }else{

            cursor.moveToNext();
            String abc =cursor.getString(0);
            String Id = abc;
            int c=Integer.parseInt(Id.substring(3));
            String IDcuoi=String.format("DRP%07d",c+1);
            return IDcuoi;

        }

    }
    private static String getID_invoicePayment(SQLiteDatabase db){
        String sql = "SELECT id_invoice FROM receipts_payments WHERE id_invoice = (SELECT MAX(id_invoice) FROM receipts_payments)";
        Cursor cursor= db.rawQuery(sql,null);

        if(cursor.getCount()==0){

            String Id = "RP00000001";
            String IDcuoi = Id;

            return IDcuoi;
        }else{
            cursor.moveToNext();
            String abc =cursor.getString(0);

            String Id = abc;
            int c=Integer.parseInt(Id.substring(2));

            String IDcuoi=String.format("RP%08d",c+1);

            return IDcuoi;
        }
    }

    private static String getID_History_Pond(SQLiteDatabase db){
        String sql = "SELECT id_diary FROM pond_diary WHERE id_diary = (SELECT MAX(id_diary) FROM pond_diary)";
        Cursor cursor= db.rawQuery(sql,null);
        if(cursor.getCount()==0){
            String Id = "V000000001";
            String IDcuoi = Id;
            return IDcuoi;
        }else {
            cursor.moveToNext();
            String abc =cursor.getString(0);

            String Id = abc;
            int c=Integer.parseInt(Id.substring(1));

            String IDcuoi=String.format("V%09d",c+1);

            return IDcuoi;
        }

    }
    private static String getID_Role_Staffs(SQLiteDatabase db){
        String sql = "SELECT id_role_staff FROM account WHERE id_role_staff = (SELECT MAX(id_role_staff) FROM account)";
        Cursor cursor= db.rawQuery(sql,null);

        if(cursor.getCount()==0){


            String Id = "RS00000001";
            String IDcuoi = Id;

            return IDcuoi;

        }else{    cursor.moveToNext();
            String abc =cursor.getString(0);

            String Id = abc;
            int c=Integer.parseInt(Id.substring(2));

            String IDcuoi=String.format("RS%08d",c+1);

            return IDcuoi;
        }


    }

    private static String getID_id_item_storeHouse(SQLiteDatabase db){
        String sql = "SELECT id_item_store_house FROM item_store_house " +
                "WHERE id_item_store_house = (SELECT MAX(id_item_store_house) FROM item_store_house)";
        Cursor cursor= db.rawQuery(sql,null);

        if(cursor.getCount()==0){

            String Id = "ITH0000001";
            String IDcuoi = Id;
            return IDcuoi;

        }else{
            cursor.moveToNext();
            String abc =cursor.getString(0);

            String Id = abc;
            int c=Integer.parseInt(Id.substring(3));

            String IDcuoi=String.format("ITH%07d",c+1);

            return IDcuoi;
        }


    }
    private static String getID_id_item_History_Store_House(SQLiteDatabase db){
        String sql = "SELECT id_history_store_house FROM history_store_house " +
                "WHERE id_history_store_house = (SELECT MAX(id_history_store_house) FROM history_store_house)";
        Cursor cursor= db.rawQuery(sql,null);
        if(cursor.getCount()==0){

            String Id = "HSH0000001";
            String IDcuoi = Id;

            return IDcuoi;

        }else{

            cursor.moveToNext();
            String abc =cursor.getString(0);

            String Id = abc;
            int c=Integer.parseInt(Id.substring(3));

            String IDcuoi=String.format("HSH%07d",c+1);

            return IDcuoi;

        }

    }
}
