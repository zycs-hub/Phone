package com.example.zy.stry.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;


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
        private JSONObject json;
        final private List<NameValuePair> params;
        private Function<JSONObject, Void> callBack;

        /**
         * function make Login Request
         * @param username
         * @param password
         * @param callBack */
        public loginUser(String username, String password,  Function<JSONObject, Void> callBack) {
            // Building Parameters
            this.callBack = callBack;
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            json = null;
        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.getLogUrl(), params);
            callBack.apply(json);

        }

        public JSONObject getJson(){
            return json;
        }

    }

    public class registerUser implements Runnable {
        private JSONObject json;
        private List<NameValuePair> params;
        private Function<JSONObject, Void> callBack;
        /**
         * function make Login Request
         * @param username
         * @param password
         * */
        public registerUser(String username, String password, Function<JSONObject, Void> callBack){
            // Building Parameters
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            this.callBack = callBack;
        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.REGIST_URL, params);
            callBack.apply(json);
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
