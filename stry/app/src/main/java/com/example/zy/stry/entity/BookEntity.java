package com.example.zy.stry.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenyc on 15/6/30.
 */
public class BookEntity implements Serializable {

    public static final String TABLE_NAME = "courses";
    //课是否在上
    private int isTaking = 0;
    //是否有书可以卖
    private int isSelected = 0;

    //public String courseid;
    public String academic_credit=null;
    public String coursenum=null;
    public String course_status=null;

    public String username=null, bookname=null, coursename=null, press=null, add_time=null, update_time=null,author,origprice
            ,price,publisher,pages,damage,image,remarks, buyer;
    public int _id = -1, courseid=0,  bid=0, is_selling=0 , is_sold=0, is_del=0;
    public List<String> message=new ArrayList<>();
    public List<MessageEntity> messageEntities = new ArrayList();


    public BookEntity () {
    }

    public void setData(int id, String _username, String _bookname, int _courseid, String _coursename,
                        int _price, String _press, int _is_selling, int _is_sold, String _add_time,
                        String _update_time, int _is_del, int _bid, String origprice,
                        String author, String pages, String image) {
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
        this.origprice = origprice;
        this.author = author;
        this.pages = pages;
        this.image = image;
        buyer = "";
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
