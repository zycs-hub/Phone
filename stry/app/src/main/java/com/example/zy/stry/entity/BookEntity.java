package com.example.zy.stry.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chenyc on 15/6/30.
 */
public class BookEntity implements Serializable {

    public static final String TABLE_NAME = "courses";
    private String book = null;
    //课是否在上
    private int isTaking = 0;
    //是否有书可以卖
    private int isSelected = 0;

    //public String courseid;
    public String academic_credit=null;
    public String coursenum=null;
    public String course_status=null;

    public String username=null, bookname=null, coursename=null, press=null, add_time=null, update_time=null,author,origprice
            ,price,publisher,pages,damage,image,remarks;
    public int _id = -1, courseid=0,  bid=0;
    public boolean is_selling=false, is_sold=false, is_del=false;
    public List<String> message=new ArrayList<>();
    public List<Message> messages= new ArrayList();


    public BookEntity () {
    }

    public void setData(int id, String _username, String _bookname, int _courseid, String _coursename,
                        int _price, String _press, boolean _is_selling, boolean _is_sold, String _add_time,
                        String _update_time, boolean _is_del, int _bid) {
        _id = id;
        username = _username;
        bookname = _bookname;
        courseid = _courseid;
        coursename = _coursename;
        price = Integer.toString(_price);
        press = _press;
        is_selling = _is_selling;
        is_sold = _is_sold;
        add_time = _add_time;
        update_time = _update_time;
        is_del = _is_del;
        bid = _bid;
    }

    public String getBook(){
        return book;
    }
    public void setBook(String book){
        this.book=book;
    }
    public int isTaking(){
        return isTaking;
    }
    public void isTaking(int isTaking){
        this.isTaking=isTaking;
    }
    public int isSelected(){
        return isSelected;
    }
    public void setSelected(int Selected){
        this.isSelected = Selected;
    }
    public void courseid(int id){
        courseid = id;
    }

    public void toggleChecked() {
        isSelected = isSelected^1;
    }

    public String getSellid()
    {
        return Integer.toString(_id);
    }


}
