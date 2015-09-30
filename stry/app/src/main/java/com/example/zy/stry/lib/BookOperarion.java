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

    public JSONObject getAllSell(){

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(Config.getHomeUrl());
        // return json
        return json;
    }

    public JSONObject addSellBooks(String username, String books) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("books", books));
        JSONObject json = jsonParser.postJSONFromUrl(Config.getAddSellUrl(), params);
        return json;
    }

    public JSONObject buyBooks(String username, String buyList) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("buyList", buyList));
        JSONObject json = jsonParser.postJSONFromUrl(Config.getBuyUrl(), params);
        return json;
    }

    public JSONObject editBook(String username, String bid, String info) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("bid", bid));
        params.add(new BasicNameValuePair("editInfo", info));
        JSONObject json = jsonParser.postJSONFromUrl(Config.getEditBookUrl(), params);
        return json;
    }
}
