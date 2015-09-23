package com.example.zy.stry.lib;

import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.entity.CartEntity.Cart;
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
import java.util.List;

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

    public static final String QUERY_USER_ALL = "SELECT * FROM courses";



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
                + Sell.KEY_IS_SELLING + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_IS_SOLD + TEXT_TYPE +  COMMA_SEP
                + Sell.KEY_ADD_TIME + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_UPDATE_TIME + TEXT_TYPE + COMMA_SEP
                + Sell.KEY_IS_DEL + TEXT_TYPE +  COMMA_SEP
                + Sell.KEY_BID  + INT_TYPE //+ COMMA_SEP
                + ")";

        StringBuffer tableCreate = new StringBuffer();
        tableCreate.append("CREATE TABLE ")
                .append(" courses ")
                .append(" (")
                .append(" book TEXT , ")
                .append(" isSelected INTEGER , ")
                .append(" isTaking INTEGER , ")
                .append(" courseid INTEGER , ")
                .append(" origprice TEXT , ")//书后定价
                .append(" price TEXT , ")
                .append(" author TEXT , ")
                .append(" publisher TEXT , ")
                .append(" pages TEXT , " )
                .append(" image TEXT , ")
                .append(" remarks TEXT , ")//备注
                .append(" damage TEXT  ")//damage
                .append(" )");
        //System.out.println(tableCreate.toString());

        String CREATE_CART_TABLE = "CREATE TABLE " + Cart.TABLE_NAME + "("
                + Cart.KEY_ID + " INTEGER PRIMARY KEY,"
                + Cart.KEY_SELLID + INT_TYPE //+ COMMA_SEP
//                + Sell.KEY_BOOKNAME + TEXT_TYPE + COMMA_SEP
//                + Sell.KEY_COURSEID + INT_TYPE + COMMA_SEP
//                + Sell.KEY_COURSENAME + TEXT_TYPE + COMMA_SEP
//                + Sell.KEY_PRICE + INT_TYPE + COMMA_SEP
//                + Sell.KEY_PRESSS + TEXT_TYPE + COMMA_SEP
//                + Sell.KEY_IS_SELLING + TEXT_TYPE + COMMA_SEP
//                + Sell.KEY_IS_SOLD + TEXT_TYPE +  COMMA_SEP
//                + Sell.KEY_ADD_TIME + TEXT_TYPE + COMMA_SEP
//                + Sell.KEY_UPDATE_TIME + TEXT_TYPE + COMMA_SEP
//                + Sell.KEY_IS_DEL + TEXT_TYPE +  COMMA_SEP
//                + Sell.KEY_BID  + INT_TYPE //+ COMMA_SEP
                + ")";


        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_SELL_TABLE);
        db.execSQL(tableCreate.toString());
        db.execSQL(CREATE_CART_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Sell.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BookEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Cart.TABLE_NAME);

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
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<SellBook> getShopData(){
        ArrayList<SellBook> list = new ArrayList<SellBook>();
//        String selectQuery = "SELECT  * FROM " + Sell.TABLE_NAME + " WHERE " + Sell.KEY_IS_SELLING + " = 1";
        String selectQuery = "SELECT  * FROM " + Sell.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor  cursor = db.query(Sell.TABLE_NAME, columns, null, null, null, null, null);

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SellBook book = new SellBook();
                book._id = cursor.getInt(0);
                book.username = cursor.getString(1);
                book.bookname = cursor.getString(2);
                book.courseid = cursor.getInt(3);
                book.coursename = cursor.getString(4);
                book.price = cursor.getInt(5);
                book.press = cursor.getString(6);
                book.is_selling =  (cursor.getString(7) == "true" )? true : false;
                book.is_sold = (cursor.getString(8) == "true" )? true : false;
                book.add_time = cursor.getString(9);
                book.update_time = cursor.getString(10);
                book.is_del = (cursor.getString(11) == "true" )? true : false;
                book.bid = cursor.getInt(12);
                list.add(book);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public void deleteShopData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Sell.TABLE_NAME, null, null);
        db.close();
    }

    public void changeSelling(int id ) {
        SQLiteDatabase db = this.getWritableDatabase();
//        String update_str = "UPDATE " + Sell.TABLE_NAME + " SET " + Sell.KEY_IS_SELLING + " = false WHERE "
//                + Sell.KEY_ID + "=" + Integer.toString(id);
//        db.execSQL(update_str);
        ContentValues cv = new ContentValues();
        cv.put(Sell.KEY_IS_SELLING, false);
        db.update(Sell.TABLE_NAME, cv, Sell.KEY_ID + "=" + Integer.toString(id), null);
    }

    public void changeSold(int id ) {
        SQLiteDatabase db = this.getWritableDatabase();
//        String update_str = "UPDATE " + Sell.TABLE_NAME + " SET " + Sell.KEY_IS_SOLD + " = true WHERE "
//                + Sell.KEY_ID + "=" + Integer.toString(id);
//        db.execSQL(update_str);
        ContentValues cv = new ContentValues();
        cv.put(Sell.KEY_IS_SOLD, true);
        db.update(Sell.TABLE_NAME,cv, Sell.KEY_ID + "=" + Integer.toString(id), null);
    }

    /**
     * add in cart
     * @param _id
     */

    public void addInCart(int _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(Cart.KEY_SELLID, _id);
        db.insert(Cart.TABLE_NAME,null,cv);
    }

    public long addData(List<BookEntity> be){
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS books");
        long param=0;
        //db.beginTransaction();
        if(be!=null)
            for(BookEntity se :be){
                ContentValues cv =new ContentValues();
                cv.put("book",se.getBook());
                cv.put("isSelected" ,se.isSelected());
                cv.put("isTaking" ,se.isTaking());
                cv.put("courseid", se.courseid);
                param=db.insert("courses",null,cv);
            }
        db.close();
        //db.setTransactionSuccessful();
        //db.endTransaction();
        return param;
    }


    public List<BookEntity> getCoursesAll(){
        List<BookEntity> It=new ArrayList<BookEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        BookEntity use=null;
        String selectQuery = "SELECT  * FROM " + BookEntity.TABLE_NAME;
        Cursor cr=db.rawQuery(selectQuery,null);
        if (cr.moveToFirst()) {
            do {
                use = new BookEntity();
                use.setBook(cr.getString(0));
                use.isSelected(cr.getInt(1));
                use.isTaking(cr.getInt(2));
                use.courseid(cr.getInt(3));
                use.origprice=(cr.getString(4));
                use.price=(cr.getString(5));
                use.author=(cr.getString(6));
                use.publisher=(cr.getString(7));
                use.pages=(cr.getString(8));
                use.image=(cr.getString(9));
                use.remarks=(cr.getString(10));
                use.damage=(cr.getString(11));
                It.add(use);
            } while (cr.moveToNext());
        }
        return It;
    }

    public List<BookEntity> getUserBooks(String username){
        List<BookEntity> It=new ArrayList<BookEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        BookEntity use = null;
        String selectQuery = "SELECT  * FROM " + Sell.TABLE_NAME + " WHERE " + Sell.KEY_USERNAME + "=?";
        Cursor cr=db.rawQuery(selectQuery,new String[]{username});
        if (cr.moveToFirst()) {
            do {
                use = new BookEntity();
                use.setBook(cr.getString(2));
                use.courseid(cr.getInt(3));
                It.add(use);
            } while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return It;
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
    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + User.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(User.TABLE_NAME, null, null);
        db.close();
    }


    public void deleteCourses() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BookEntity.TABLE_NAME, null, null);
        db.close();
    }

    public List search(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<SellBook> list = new ArrayList<>();
        SellBook tmp;
        String searchQuery = "SELECT * FROM " + Sell.TABLE_NAME + " WHERE " + Sell.KEY_BOOKNAME;
        int len = query.length();
        for (int i = 0; i < len; i++)  {
            if(i > 0) {
                searchQuery += " AND " + Sell.KEY_BOOKNAME;
            }
            searchQuery += " LIKE " + "'%" + query.charAt(i) +"%'";
        }
        Cursor cursor = db.rawQuery(searchQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tmp = new SellBook();
//                tmp.setData();
                tmp._id = cursor.getInt(0);
                tmp.username = cursor.getString(1);
                tmp.bookname = cursor.getString(2);
                tmp.courseid = cursor.getInt(3);
                tmp.coursename = cursor.getString(4);
                tmp.price = cursor.getInt(5);
                tmp.press = cursor.getString(6);
                tmp.is_selling =  (cursor.getString(7) == "true" )? true : false;
                tmp.is_sold = (cursor.getString(8) == "true" )? true : false;
                tmp.add_time = cursor.getString(9);
                tmp.update_time = cursor.getString(10);
                tmp.is_del = (cursor.getString(11) == "true" )? true : false;
                tmp.bid = cursor.getInt(12);
                list.add(tmp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;

    }

    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void deleteTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(User.TABLE_NAME, null, null);
        db.delete(Sell.TABLE_NAME, null, null);
        db.delete(BookEntity.TABLE_NAME, null, null);
        db.close();
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
