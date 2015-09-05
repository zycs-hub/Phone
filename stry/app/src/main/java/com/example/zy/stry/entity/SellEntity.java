package com.example.zy.stry.entity;

import android.provider.BaseColumns;

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

    public static class SellBook {
        public String username, bookname, coursename, press, add_time, update_time;
        public int courseid, price, bid;
        public byte[] is_selling, is_sold, is_del;
        public SellBook() {
        }
    }
}
