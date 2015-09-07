package com.example.zy.stry.lib;

import com.example.zy.stry.entity.UserEntity.User;
import com.example.zy.stry.entity.SellEntity.SellBook;
import com.example.zy.stry.entity.SellEntity.Sell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wendy on 15-7-6.
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "App";

    private static final String TEXT_TYPE = " TEXT ";
    private static final String INT_TYPE = " INTEGER ";
    private static final String BOOL_TYPE = " BLOB ";
    private static final String COMMA_SEP = " , ";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + User.TABLE_NAME + " ("
                + User.KEY_UID + " INTEGER PRIMARY KEY,"
                + User.KEY_NAME + " TEXT UNIQUE" + COMMA_SEP
                + User.KEY_PASS + TEXT_TYPE //+ COMMA_SEP
                + ")";

        String CREATE_SELL_TABLE = "CREATE TABLE " + Sell.TABLE_NAME + "("
                + Sell.KEY_ID + " INTEGER PRIMARY KEY,"
                + Sell.KEY_USERNAME + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_BOOKNAME + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_COURSEID + INT_TYPE + COMMA_SEP
                + Sell.KEY_COURSENAME + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_PRICE + INT_TYPE + COMMA_SEP
                + Sell.KEY_PRESSS + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_IS_SELLING + BOOL_TYPE + COMMA_SEP
                + Sell.KEY_IS_SOLD + BOOL_TYPE + COMMA_SEP
                + Sell.KEY_ADD_TIME + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_UPDATE_TIME + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_IS_DEL + BOOL_TYPE + COMMA_SEP
                + Sell.KEY_BID  + INT_TYPE //+ COMMA_SEP
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_SELL_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Sell.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.KEY_NAME, username); // Name
        values.put(User.KEY_PASS, password); // password
        // Inserting Row
        db.insert(User.TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUsersData(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + User.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("username", cursor.getString(1));
            user.put("password", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
    /**
     * Storing shop details in database
     * */
    public void addSell(String username, String bookname, int courseid, String coursename,
                        int price, String press, boolean is_selling, boolean is_sold,
                        String add_time, String update_time, boolean is_del, int anInt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Sell.KEY_USERNAME, username);
        values.put(Sell.KEY_BOOKNAME, bookname);
        values.put(Sell.KEY_COURSEID, courseid);
        values.put(Sell.KEY_COURSENAME, coursename);
        values.put(Sell.KEY_PRICE, price);
        values.put(Sell.KEY_PRESSS, press);
        values.put(Sell.KEY_IS_SELLING, is_selling);
        values.put(Sell.KEY_IS_SOLD, is_sold);
        values.put(Sell.KEY_ADD_TIME, add_time);
        values.put(Sell.KEY_UPDATE_TIME, update_time);
        values.put(Sell.KEY_IS_DEL, is_del);
        values.put(Sell.KEY_BID, 0);
        db.insert(Sell.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<SellBook> getShopData(){
        ArrayList<SellBook> list = new ArrayList<SellBook>();
        SellBook book = new SellBook();
//        String selectQuery = "SELECT  * FROM " + Sell.TABLE_NAME + " WHERE " + Sell.KEY_IS_SELLING + " = 1";
        String selectQuery = "SELECT  * FROM " + Sell.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns={"bookname"};
//        Cursor  cursor = db.query(Sell.TABLE_NAME, columns, null, null, null, null, null);

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
//            book.username = cursor.getString(1);
//            book.bookname = cursor.getString(2);
//            book.courseid = cursor.getInt(3);
//            book.coursename = cursor.getString(4);
//            book.price = cursor.getInt(5);
//            book.press = cursor.getString(6);
//            book.is_selling = cursor.getBlob(7);
//            book.is_sold = cursor.getBlob(8);
//            book.add_time = cursor.getString(9);
//            book.update_time = cursor.getString(10);
//            book.is_del = cursor.getBlob(11);
//            book.bid = cursor.getInt(12);
            book.bookname = cursor.getString(0);
            list.add(book);
        }
        cursor.close();
        db.close();
        return list;
    }



    /**
     * Storing sell details in database
     * */
//    public int getSell() {
//        String countQuery = "SELECT  * FROM " + user.TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        int rowCount = cursor.getCount();
//        db.close();
//        cursor.close();
//
//        // return row count
//        return rowCount;
//    }

    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + User.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void deleteTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(User.TABLE_NAME, null, null);
        db.delete(Sell.TABLE_NAME, null, null);

        db.close();
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
