package com.example.zy.stry.util;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.zy.stry.entity.UserEntity;
import java.lang.StringBuilder;
import java.lang.StringBuffer;


public class My_DB extends SQLiteOpenHelper {
    public static final String MY_DB_MANE = "my_db";
    public static final String MY_DB_TABLE_1_NAME = "user";
    public static final int MY_DB_VERSION = 1;
    public static final String QUERY_USER_ALL = "SELECT * FROM " + MY_DB_TABLE_1_NAME;


    public My_DB(Context context, String name, CursorFactory factory,
                 int version){
        super(context, name, factory, version);
    }

    @Override
   /* public void onCreate(SQLiteDatabase arg0){
        arg0.execSQL()
                //("create table"
               // +MY_DB_TABLE_1_NAME
              //  +"(book text,);");
    }*/
    public void onCreate(SQLiteDatabase arg0) {
        StringBuffer tableCreate = new StringBuffer();
        tableCreate.append("create table ")
                .append(MY_DB_TABLE_1_NAME)
                .append(" (")
                .append("book text , ")
                .append("isSelected INTEGER")
                .append(")");
        //System.out.println(tableCreate.toString());
        arg0.execSQL(tableCreate.toString());
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0,int arg1,int arg2){
        arg0.execSQL("drop table if it exits "+MY_DB_MANE);
        onCreate(arg0);
    }
    public List<UserEntity> getUserAll(SQLiteDatabase db){
        List<UserEntity> It=new ArrayList<UserEntity>();
        UserEntity use=null;
        Cursor cr=db.rawQuery(QUERY_USER_ALL,null);
        while(cr.moveToNext()){
            use =new UserEntity();
            use.setBook(cr.getString(0));
            use.isSelected(cr.getInt(1));
            It.add(use);
        }
        return It;
    }
    public long addDate(List<UserEntity> be,SQLiteDatabase db){
        long param=0;
        db.beginTransaction();
        if(be!=null)
            for(UserEntity se :be){
                ContentValues cv =new ContentValues();
                cv.put("book",se.getBook());
                cv.put("isSelected" ,se.isSelected());
                param=db.insert(MY_DB_TABLE_1_NAME,null,cv);
            }
        db.setTransactionSuccessful();
        db.endTransaction();
        return param;
    }
}
