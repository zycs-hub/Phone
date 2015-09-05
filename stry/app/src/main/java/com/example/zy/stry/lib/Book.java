package com.example.zy.stry.lib;

import org.json.JSONObject;


/**
 * Created by wendy on 15-9-2.
 */
public class Book {
    private JSONParser jsonParser;

    // constructor
    public Book() {
        jsonParser = new JSONParser();
    }

    public JSONObject getAllSell(){

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(Config.getHomeUrl());
        // return json
        return json;
    }
}