package com.example.zy.stry.entity;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by wendy on 15-9-5.
 */
public class SellEntity {
    public SellEntity() {
    }

    /* Inner class that defines the table contents */
    public static abstract class Sell implements BaseColumns {
        public static final String TABLE_NAME = "sell";
        //Table Columns names
        public static final String KEY_ID = "id_";
        public static final String KEY_USERNAME = "username";
        public static final String KEY_BOOKNAME = "bookname";
        public static final String KEY_COURSEID = "courseid";
        public static final String KEY_COURSENAME = "coursename";
        public static final String KEY_PRICE = "price";
        public static final String KEY_PRESSS = "press";
        public static final String KEY_IS_SELLING = "is_selling";
        public static final String KEY_IS_SOLD = "is_sold";
        public static final String KEY_ADD_TIME = "add_time";
        public static final String KEY_UPDATE_TIME = "update_time";
        public static final String KEY_IS_DEL = "is_del";
        public static final String KEY_BID = "bid";
    }

    public static class SellBook implements Serializable {
        public String username, bookname, coursename, press, add_time, update_time;
        public int _id, courseid, price, bid;
        public boolean is_selling, is_sold, is_del;
        public SellBook() {
            setData(-1, "", "", -1, "", -1, "", false, false, "", "", false, -1);
        }
        public void setData(int id, String _username, String _bookname, int _courseid, String _coursename,
                        int _price, String _press, boolean _is_selling, boolean _is_sold, String _add_time,
                        String _update_time, boolean _is_del, int _bid) {
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
        }
    }
}
