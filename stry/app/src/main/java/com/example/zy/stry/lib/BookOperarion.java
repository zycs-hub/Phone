package com.example.zy.stry.lib;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


/**
 * Created by wendy on 15-9-2.
 */
public class BookOperarion {
    private JSONParser jsonParser;

    // constructor
    public BookOperarion() {
        jsonParser = new JSONParser();
    }

//    public class getAllSell implements Runnable {
//        private JSONObject json = null;
//        public getAllSell(){
//        }
//
//        @Override
//        public void run() {
//            json = jsonParser.getJSONFromUrl(Config.getHomeUrl());
//        }
//
//        public JSONObject getJSON() {
//            return json;
//        }
//
//    }
    public JSONObject getAllSell(){

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(Config.getHomeUrl());
        // return json
        return json;
    }



    public class addSellBooks implements Runnable {
        public JSONObject json;
        List<NameValuePair> params;

        public addSellBooks(String username, String books) {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("books", books));
        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.getAddSellUrl(), params);
        }

    }

    public class buyBooks implements Runnable {
        public JSONObject json;
        List<NameValuePair> params;
        private Function<JSONObject, Void> callBack;


        public buyBooks(String username, String buyList, String address, String comment, Function<JSONObject, Void> callBack) {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("buyList", buyList));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("comment", comment));
            this.callBack = callBack;

        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.getBuyUrl(), params);
            callBack.apply(json);
        }

    }

    public class editBook implements Runnable {
        public JSONObject json;
        List<NameValuePair> params;
        private Function<JSONObject, Void> callBack;

        public editBook(String username, String bid, String info, Function<JSONObject, Void> callBack) {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("bid", bid));
            params.add(new BasicNameValuePair("editInfo", info));
            this.callBack = callBack;
        }

        @Override
        public void run() {

            json = jsonParser.postJSONFromUrl(Config.getEditBookUrl(), params);
            callBack.apply(json);
        }
    }

}
