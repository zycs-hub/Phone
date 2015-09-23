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
    public int courseid=0,  bid=0;
    public boolean is_selling=false, is_sold=false, is_del=false;






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
    public void isSelected(int isSelected){
        this.isSelected=isSelected;
    }
    public void courseid(int id){
        courseid = id;
    }




}
