package com.example.zy.stry.lib;

import android.content.Context;
import android.content.SharedPreferences;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-7-6.
 */
public class User {
    private JSONParser jsonParser;
    SharedPreferences.Editor prefEditor;
    public static final String PREFS_NAME = "MyPrefs";

    // constructor
    public User(){
        jsonParser = new JSONParser();
    }


    public class loginUser implements Runnable{
        public JSONObject json;
        private List<NameValuePair> params;
        /**
         * function make Login Request
         * @param username
         * @param password
         * */
        public loginUser(String username, String password) {
            // Building Parameters
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.getLogUrl(), params);
        }
    }

    public class registerUser implements Runnable {
        public JSONObject json;
        private List<NameValuePair> params;
        /**
         * function make Login Request
         * @param username
         * @param password
         * */
        public registerUser(String username, String password){
            // Building Parameters
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));

        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.REGIST_URL, params);
        }
    }


    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getUserCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }

    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.deleteUser();
        prefEditor = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE).edit();
        prefEditor.clear();
        prefEditor.commit();
        return true;
    }
}
