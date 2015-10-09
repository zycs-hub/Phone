package com.example.zy.stry.lib;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wendy on 15-9-2.
 */
public class BookOperarion {
    private JSONParser jsonParser;

    // constructor
    public BookOperarion() {
        jsonParser = new JSONParser();
    }

    public class getAllSell implements Runnable {
        public JSONObject json;
        public getAllSell(){
        }

        @Override
        public void run() {
            json = jsonParser.getJSONFromUrl(Config.getHomeUrl());
        }

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

        public buyBooks(String username, String buyList) {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("buyList", buyList));
        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.getBuyUrl(), params);
        }

    }

    public class editBook implements Runnable {
        public JSONObject json;
        List<NameValuePair> params;

        public editBook(String username, String bid, String info) {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("bid", bid));
            params.add(new BasicNameValuePair("editInfo", info));
        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.getEditBookUrl(), params);
        }
    }

}
