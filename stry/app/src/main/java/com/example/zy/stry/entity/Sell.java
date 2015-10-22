package com.example.zy.stry.entity;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by wendy on 15-9-5.
 */
public class Sell {

        public Sell() {
        }

    /* Inner class that defines the table contents */
        public static abstract class SellEntity implements BaseColumns {
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
            public static final String KEY_BUYER = "buyer";
            public static final String KEY_ORINGIN_PRICE = "originprice";
            public static final String KEY_AUTHOR = "author";
            public static final String KEY_PAGES = "pages";
            public static final String KEY_IMG = "image";

    }


    }
