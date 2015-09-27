package com.example.zy.stry.entity;

import android.provider.BaseColumns;

/**
 * Created by wendy on 15-9-21.
 */
public class CartEntity {
    public CartEntity() {
    }

    /* Inner class that defines the table contents */
    public static abstract class Cart implements BaseColumns {
        public static final String TABLE_NAME = "cart";
        //Table Columns names
        public static final String KEY_ID = "id_";
        public static final String KEY_SELLID = "sell_id";
//        public static final String KEY_BOOKNAME = "bookname";
//        public static final String KEY_COURSEID = "courseid";
//        public static final String KEY_COURSENAME = "coursename";
//        public static final String KEY_PRICE = "price";
//        public static final String KEY_PRESSS = "press";
//        public static final String KEY_IS_SELLING = "is_selling";
//        public static final String KEY_IS_SOLD = "is_sold";
        public static final String KEY_ADD_TIME = "add_time";
//        public static final String KEY_UPDATE_TIME = "update_time";
//        public static final String KEY_IS_DEL = "is_del";
//        public static final String KEY_BID = "bid";
    }
}
