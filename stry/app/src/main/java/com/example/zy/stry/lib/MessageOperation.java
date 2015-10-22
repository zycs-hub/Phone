package com.example.zy.stry.lib;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-10-15.
 */
public class MessageOperation {
    private JSONParser jsonParser;

    public MessageOperation(){
        jsonParser = new JSONParser();
    }

    public class addMsg implements Runnable {
        public JSONObject json;
        List<NameValuePair> params;
        private Function<JSONObject, Void> callBack;

        public addMsg(String message, String sender, String receiver, int year, int moon, int day,
                      int hour, int minute, int isRead,int bid, Function<JSONObject, Void> callBack) {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("bid", Integer.toString(bid)));
            params.add(new BasicNameValuePair("message", message));
            params.add(new BasicNameValuePair("sender", sender));
            params.add(new BasicNameValuePair("receiver", receiver));
            params.add(new BasicNameValuePair("year", Integer.toString(year)));
            params.add(new BasicNameValuePair("moon", Integer.toString(moon)));
            params.add(new BasicNameValuePair("day", Integer.toString(day)));
            params.add(new BasicNameValuePair("hour", Integer.toString(hour)));
            params.add(new BasicNameValuePair("minute", Integer.toString(minute)));
            this.callBack = callBack;
        }

        @Override
        public void run() {
            json = jsonParser.postJSONFromUrl(Config.getAddMsgUrl(), params);
            callBack.apply(json);
        }
    }
}
