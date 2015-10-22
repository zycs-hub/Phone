package com.example.zy.stry.lib;

import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.entity.CartEntity.Cart;
import com.example.zy.stry.entity.MessageEntity;
import com.example.zy.stry.entity.UserEntity.User;
import com.example.zy.stry.entity.SellBook;
import com.example.zy.stry.entity.Sell.SellEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

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

        String CREATE_SELL_TABLE = "CREATE TABLE " + SellEntity.TABLE_NAME + "("
                + SellEntity.KEY_ID + " INTEGER PRIMARY KEY,"
                + SellEntity.KEY_USERNAME + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_BOOKNAME + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_COURSEID + INT_TYPE + COMMA_SEP
                + SellEntity.KEY_COURSENAME + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_PRICE + INT_TYPE + COMMA_SEP
                + SellEntity.KEY_PRESSS + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_IS_SELLING + INT_TYPE + COMMA_SEP
                + SellEntity.KEY_IS_SOLD + INT_TYPE +  COMMA_SEP
                + SellEntity.KEY_ADD_TIME + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_UPDATE_TIME + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_IS_DEL + INT_TYPE +  COMMA_SEP
                + SellEntity.KEY_BID  + INT_TYPE + COMMA_SEP
                + SellEntity.KEY_BUYER  + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_ORINGIN_PRICE  + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_AUTHOR  + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_PAGES  + TEXT_TYPE + COMMA_SEP
                + SellEntity.KEY_IMG  + TEXT_TYPE //+ COMMA_SEP
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
                + Cart.KEY_SELLID + INT_TYPE + COMMA_SEP
//                + SellEntity.KEY_BOOKNAME + TEXT_TYPE + COMMA_SEP
//                + SellEntity.KEY_COURSEID + INT_TYPE + COMMA_SEP
//                + SellEntity.KEY_COURSENAME + TEXT_TYPE + COMMA_SEP
//                + SellEntity.KEY_PRICE + INT_TYPE + COMMA_SEP
//                + SellEntity.KEY_PRESSS + TEXT_TYPE + COMMA_SEP
//                + SellEntity.KEY_IS_SELLING + TEXT_TYPE + COMMA_SEP
//                + SellEntity.KEY_IS_SOLD + TEXT_TYPE +  COMMA_SEP
                + Cart.KEY_ADD_TIME + TEXT_TYPE //+ COMMA_SEP
//                + SellEntity.KEY_UPDATE_TIME + TEXT_TYPE + COMMA_SEP
//                + SellEntity.KEY_IS_DEL + TEXT_TYPE +  COMMA_SEP
//                + SellEntity.KEY_BID  + INT_TYPE //+ COMMA_SEP
                + ")";

//        StringBuffer messCreate = new StringBuffer();
//        tableCreate.append("CREATE TABLE ")
//                .append(" messages ")
//                .append(" (")
//                .append(" _id INTEGER PRIMARY KEY ,")
//                .append(" bid INTEGER , ")
//                .append(" message TEXT , ")
//                .append(" isRead INTEGER , ")
//                .append(" year TEXT , ")
//                .append(" moon TEXT , ")
//                .append(" day TEXT , ")
//                .append(" hour TEXT , " )
//                .append(" min TEXT  ")//damage
//                .append(" )");

        String CREATE_MESSAGE=" CREATE TABLE messages ( _id INTEGER PRIMARY KEY , bid INTEGER , message TEXT ," +
                " sender TEXT , receiver TEXT ," +
                " isRead INTEGER ,year INTEGER , moon INTEGER , day INTEGER , hour INTEGER , min INTEGER  )";


        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_SELL_TABLE);
        db.execSQL(tableCreate.toString());
        db.execSQL(CREATE_MESSAGE);
        db.execSQL(CREATE_CART_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SellEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BookEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Cart.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + "messages");


        // Create tables again
        onCreate(db);
    }

    public void editTable(String table, String col, int para, String queryKey, String keyArg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col, para);
        db.update(table, cv, queryKey + "=" + keyArg, null);
    }

    public void editTable(String table, String col, String para,String queryKey, String keyArg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col, para);
        db.update(table, cv, queryKey + "=" + keyArg, null);
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

    public void addSell(int _id, String username, String bookname, int courseid, String coursename,
                        int price, String press, int is_selling, int is_sold,
                        String add_time, String update_time, int is_del, int bid, String buyer,
                        String originprice, String author, String pages, String img) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SellEntity.KEY_ID, _id);
        values.put(SellEntity.KEY_USERNAME, username);
        values.put(SellEntity.KEY_BOOKNAME, bookname);
        values.put(SellEntity.KEY_COURSEID, courseid);
        values.put(SellEntity.KEY_COURSENAME, coursename);
        values.put(SellEntity.KEY_PRICE, price);
        values.put(SellEntity.KEY_PRESSS, press);
        values.put(SellEntity.KEY_IS_SELLING, is_selling);
        values.put(SellEntity.KEY_IS_SOLD, is_sold);
        values.put(SellEntity.KEY_ADD_TIME, add_time);
        values.put(SellEntity.KEY_UPDATE_TIME, update_time);
        values.put(SellEntity.KEY_IS_DEL, is_del);
        values.put(SellEntity.KEY_BID, bid);
        values.put(SellEntity.KEY_BUYER, buyer);
        values.put(SellEntity.KEY_ORINGIN_PRICE, originprice);
        values.put(SellEntity.KEY_AUTHOR, author);
        values.put(SellEntity.KEY_PAGES, pages);
        values.put(SellEntity.KEY_IMG, img);
        db.insert(SellEntity.TABLE_NAME, null, values);
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<SellBook> getShopData(){
        ArrayList<SellBook> list = new ArrayList<SellBook>();
        String selectQuery = "SELECT  * FROM " + SellEntity.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor  cursor = db.query(SellEntity.TABLE_NAME, columns, null, null, null, null, null);

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
                book.is_selling =  cursor.getInt(7);
                book.is_sold = cursor.getInt(8);
                book.add_time = cursor.getString(9);
                book.update_time = cursor.getString(10);
                book.is_del = cursor.getInt(11);
                book.bid = cursor.getInt(12);
                book.buyer = cursor.getString(13);
                book.originprice = cursor.getString(14);
                book.author = cursor.getString(15);
                book.pages = cursor.getString(16);
                book.image = cursor.getString(17);
                list.add(book);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteShopData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ SellEntity.TABLE_NAME+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                db.delete(SellEntity.TABLE_NAME, null, null);
            }
            cursor.close();
        }
        db.close();
    }

    public int getLastSellID() {
        SQLiteDatabase db = this.getReadableDatabase();
        String tmp = "SELECT "+ SellEntity.KEY_ID + " FROM sell ORDER BY " + SellEntity.KEY_ID + " DESC limit 1";
        Cursor c = db.rawQuery(tmp, null);
        if (c != null && c.moveToFirst()) {
             return c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return -1;
    }

    public void changeSelling(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        String update_str = "UPDATE " + SellEntity.TABLE_NAME + " SET " + SellEntity.KEY_IS_SELLING + " = false WHERE "
//                + SellEntity.KEY_ID + "=" + Integer.toString(id);
//        db.execSQL(update_str);
        ContentValues cv = new ContentValues();
        cv.put(SellEntity.KEY_IS_SELLING, 0);
        db.update(SellEntity.TABLE_NAME, cv, SellEntity.KEY_ID + "=" + Integer.toString(id), null);
        db.delete(Cart.TABLE_NAME, Cart.KEY_SELLID + "=" + Integer.toString(id), null);
    }

    public void changeSold(int id ) {
        SQLiteDatabase db = this.getWritableDatabase();
//        String update_str = "UPDATE " + SellEntity.TABLE_NAME + " SET " + SellEntity.KEY_IS_SOLD + " = true WHERE "
//                + SellEntity.KEY_ID + "=" + Integer.toString(id);
//        db.execSQL(update_str);
        ContentValues cv = new ContentValues();
        cv.put(SellEntity.KEY_IS_SOLD, true);
        db.update(SellEntity.TABLE_NAME, cv, SellEntity.KEY_ID + "=" + Integer.toString(id), null);
    }



    /**
     *  add in cart
     * @param _id
     * @param curtime
     */

    public void addInCart(int _id, String curtime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(Cart.KEY_SELLID, _id);
        cv.put(Cart.KEY_ADD_TIME, curtime);
        db.insert(Cart.TABLE_NAME, null, cv);
        db.close();
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
        String selectQuery = "SELECT  * FROM " + SellEntity.TABLE_NAME;
        Cursor cr=db.rawQuery(selectQuery,null);
        if (cr.moveToFirst()) {
            do {
                use = new BookEntity();
                use.setBook(cr.getString(0));
                use.setSelected(cr.getInt(1));
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
    public void addMessage(String message, String sender, String receiver, int year, int moon, int day,
                           int hour, int min, int isRead,int bid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("sender", sender);
        values.put("receiver", receiver);
        values.put("year", year);
        values.put("moon", moon);
        values.put("day", day);
        values.put("hour", hour);
        values.put("min", min);
        values.put("isRead", isRead);
        values.put("bid", bid);
        db.insert("messages", null, values);
        db.close();
    }
    public List<MessageEntity> getMessage(int bid){
        List<MessageEntity> lt= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        MessageEntity messageEntity;
        String selectQuery = "SELECT  * FROM " + "messages" + " WHERE " + "bid" + "=?" ;
        Cursor cr=db.rawQuery(selectQuery,new String[]{Integer.toString(bid)});
        if (cr.moveToFirst()) {
            do {
                messageEntity = new MessageEntity();
                messageEntity.message = cr.getString(2);
                messageEntity.isRead = cr.getInt(3);
                messageEntity.year = cr.getInt(4);
                messageEntity.moon = cr.getInt(5);
                messageEntity.day = cr.getInt(6);
                messageEntity.hour = cr.getInt(7);
                messageEntity.min = cr.getInt(8);
                lt.add(messageEntity);
            }while (cr.moveToNext());
        }
        db.close();
        return lt;
    }

    public List<BookEntity> getUserBooks(String username){
        List<BookEntity> It=new ArrayList<BookEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        BookEntity use = null;
        String selectQuery = "SELECT  * FROM " + SellEntity.TABLE_NAME + " WHERE " + SellEntity.KEY_USERNAME + "=?";
        Cursor cr=db.rawQuery(selectQuery,new String[]{username});
        if (cr.moveToFirst()) {
            do {
                use = new BookEntity();
                use._id = cr.getInt(0);
                use.setBook(cr.getString(2));
                use.courseid(cr.getInt(3));
                use.bid = cr.getInt(12);
                String tmpQuery = "SELECT  * FROM course WHERE courseid =?";
                Cursor cursor=db.rawQuery(selectQuery,new String[]{Integer.toString(use.courseid)});
                if (cursor.moveToFirst()) {
                    use.origprice = cursor.getString(4);
                    use.author = cursor.getString(6);
                    use.publisher = cursor.getString(7);
                    use.pages = cursor.getString(8);
                    use.image = cursor.getString(9);
                }
                It.add(use);
            } while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return It;
    }

    public List<BookEntity> getSoldBooks(String username){
        List<BookEntity> It=new ArrayList<BookEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        BookEntity use = null;
        String selectQuery = "SELECT  * FROM " + SellEntity.TABLE_NAME + " WHERE " + SellEntity.KEY_USERNAME + "=?" + "AND " + SellEntity.KEY_IS_SOLD + " =?";
        Cursor cr=db.rawQuery(selectQuery,new String[]{username, "1"});
        if (cr.moveToFirst()) {
            do {
                use = new BookEntity();
                use.setBook(cr.getString(2));
                use.courseid(cr.getInt(3));
                use.bid = cr.getInt(12);
//                String tmpQuery = "SELECT  * FROM course WHERE courseid =?";
//                Cursor cursor=db.rawQuery(selectQuery,new String[]{Integer.toString(use.courseid)});
//                if (cursor.moveToFirst()) {
//                    use.origprice = cursor.getString(4);
//                    use.author = cursor.getString(6);
//                    use.publisher = cursor.getString(7);
//                    use.pages = cursor.getString(8);
//                    use.image = cursor.getString(9);
//                }
                It.add(use);
            } while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return It;
    }

    public List<BookEntity> getBoughtBooks(String username){
        List<BookEntity> It=new ArrayList<BookEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        BookEntity use = null;
        String selectQuery = "SELECT  * FROM " + SellEntity.TABLE_NAME + " WHERE " + SellEntity.KEY_BUYER + "=?" + "AND " + SellEntity.KEY_IS_SOLD + " =?";
        Cursor cr=db.rawQuery(selectQuery,new String[]{username, "1"});
        if (cr.moveToFirst()) {
            do {
                use = new BookEntity();
                use.setBook(cr.getString(2));
                use.courseid(cr.getInt(3));
                use.bid = cr.getInt(12);
//                String tmpQuery = "SELECT  * FROM course WHERE courseid =?";
//                Cursor cursor=db.rawQuery(selectQuery,new String[]{Integer.toString(use.courseid)});
//                if (cursor.moveToFirst()) {
//                    use.origprice = cursor.getString(4);
//                    use.author = cursor.getString(6);
//                    use.publisher = cursor.getString(7);
//                    use.pages = cursor.getString(8);
//                    use.image = cursor.getString(9);
//                }
                It.add(use);
            } while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return It;
    }



    public List<BookEntity> getBuyingBooks(String username){
        List<BookEntity> It=new ArrayList<BookEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        BookEntity use = null;
        String selectQuery = "SELECT  * FROM " + SellEntity.TABLE_NAME + " WHERE " + SellEntity.KEY_BUYER + "=?"
                + "AND " + SellEntity.KEY_IS_SELLING + " =?" + "AND " + SellEntity.KEY_IS_SOLD + " =?";
        Cursor cr=db.rawQuery(selectQuery,new String[]{username, "0", "0"});
        if (cr.moveToFirst()) {
            do {
                use = new BookEntity();
                use.setBook(cr.getString(2));
                use.courseid(cr.getInt(3));
                use.bid = cr.getInt(12);
//                String tmpQuery = "SELECT  * FROM course WHERE courseid =?";
//                Cursor cursor=db.rawQuery(selectQuery,new String[]{Integer.toString(use.courseid)});
//                if (cursor.moveToFirst()) {
//                    use.origprice = cursor.getString(4);
//                    use.author = cursor.getString(6);
//                    use.publisher = cursor.getString(7);
//                    use.pages = cursor.getString(8);
//                    use.image = cursor.getString(9);
//                }
                It.add(use);
            } while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return It;
    }



    public List<SellBook> getCart(){
        List<SellBook> It = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        SellBook tmp = null;
        String selectQuery = "SELECT  * FROM " + Cart.TABLE_NAME;
        Cursor cr=db.rawQuery(selectQuery, null);
        if (cr.moveToFirst()) {
            do {
                tmp = new SellBook();
                int sid = cr.getInt(1);
                String tmpQuery = "SELECT  * FROM " + SellEntity.TABLE_NAME + " Where " + SellEntity.KEY_ID + " = ?";
                Cursor cr2 = db.rawQuery(tmpQuery,new String[]{Integer.toString(sid)});
                if(cr2.moveToFirst()) {
                    tmp.setData(cr2.getInt(0), cr2.getString(1), cr2.getString(2), cr2.getInt(3), cr2.getString(4),
                            cr2.getInt(5), cr2.getString(6), cr2.getInt(7) ,
                            cr2.getInt(8) , cr2.getString(9),cr2.getString(10),
                            cr2.getInt(11), cr2.getInt(12), cr2.getString(13), cr2.getString(14),
                            cr2.getString(15), cr2.getString(16), cr2.getString(17));
                    It.add(tmp);
                }
            } while (cr.moveToNext());
        }
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
    public void deleteMessages() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("messages", null, null);
        db.close();
    }




    public List search(String query) {
        SQLiteDatabase db = this.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(SellEntity.TABLE_NAME);

        List<SellBook> list = new ArrayList<>();
        SellBook tmp;

        Cursor cursor = builder.query(db, null, SellEntity.KEY_BOOKNAME  + " LIKE ?",
                new String[] {"%"+query+"%"}, null, null, null);

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
                tmp.is_selling =  cursor.getInt(7);
                tmp.is_sold = cursor.getInt(8);
                tmp.add_time = cursor.getString(9);
                tmp.update_time = cursor.getString(10);
                tmp.is_del = cursor.getInt(11);
                tmp.bid = cursor.getInt(12);
                list.add(tmp);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return list;



//        List<SellBook> list = new ArrayList<>();
//        SellBook tmp;
//        String searchQuery = "SELECT * FROM " + SellEntity.TABLE_NAME + " WHERE " + SellEntity.KEY_BOOKNAME;
//        int len = query.length();
//        for (int i = 0; i < len; i++)  {
//            if(i > 0) {
//                searchQuery += " AND " + SellEntity.KEY_BOOKNAME;
//            }
//            searchQuery += " LIKE " + "'%" + query.charAt(i) +"%'";
//        }
//        Cursor cursor = db.rawQuery(searchQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                tmp = new SellBook();
////                tmp.setData();
//                tmp._id = cursor.getInt(0);
//                tmp.username = cursor.getString(1);
//                tmp.bookname = cursor.getString(2);
//                tmp.courseid = cursor.getInt(3);
//                tmp.coursename = cursor.getString(4);
//                tmp.price = cursor.getInt(5);
//                tmp.press = cursor.getString(6);
//                tmp.is_selling =  (cursor.getString(7) == "true" )? true : false;
//                tmp.is_sold = (cursor.getString(8) == "true" )? true : false;
//                tmp.add_time = cursor.getString(9);
//                tmp.update_time = cursor.getString(10);
//                tmp.is_del = (cursor.getString(11) == "true" )? true : false;
//                tmp.bid = cursor.getInt(12);
//                list.add(tmp);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();return list;

    }

    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void deleteTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(User.TABLE_NAME, null, null);
        db.delete(SellEntity.TABLE_NAME, null, null);
        db.delete(BookEntity.TABLE_NAME, null, null);
        db.close();
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
