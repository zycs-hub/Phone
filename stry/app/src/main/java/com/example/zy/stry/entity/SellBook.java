package com.example.zy.stry.entity;

import java.io.Serializable;

/**
 * Created by wendy on 15-10-15.
 */
public class SellBook implements Serializable {
        public String username, bookname, coursename, press, add_time, update_time, buyer, originprice, author, pages, image;
        public int _id, courseid, price, bid, is_selling, is_sold, is_del;
        public SellBook() {
            setData(-1, "", "", -1, "", -1, "", 0, 0, "", "", 0, -1, "", "", "", "", "");
        }
        public void setData(int id, String _username, String _bookname, int _courseid, String _coursename,
                            int _price, String _press, int _is_selling, int _is_sold, String _add_time,
                            String _update_time, int _is_del, int _bid, String _buyer, String _originprice,
                            String _author, String _pages, String _image ) {
            _id = id;
            username = _username;
            bookname = _bookname;
            courseid = _courseid;
            coursename = _coursename;
            price = _price;
            press = _press;
            is_selling = _is_selling;
            is_sold = _is_sold;
            add_time = _add_time;
            update_time = _update_time;
            is_del = _is_del;
            bid = _bid;
            buyer = _buyer;
            originprice = _originprice;
            author = _author;
            pages = _pages;
            image = _image;
        }
}
