package com.example.zy.stry.entity;

import android.provider.BaseColumns;

/**
 * Created by wendy on 15-9-5.
 */
public class UserEntity {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public UserEntity() {
        }

        /* Inner class that defines the table contents */
        public static abstract class User implements BaseColumns {
            public static final String TABLE_NAME = "users";
            //Table Columns names
            public static final String KEY_UID = "uid";
            public static final String KEY_NAME = "username";
            public static final String KEY_PASS = "password";
        }
}
