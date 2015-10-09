package com.example.zy.stry.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.zy.stry.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by wendy on 15-9-16.
 */
public class NetWorkChecker {
    ConnectivityManager cm;
    NetworkInfo netInfo;
    public NetWorkChecker(Context ctx) {
        cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
    }

    public boolean isOnline() {

        if(netInfo != null && netInfo.isConnectedOrConnecting()) {
            isLinked task = new isLinked();
            MainActivity.executorService.submit(task);
            boolean result = task.result;
            return result;
        }
        return false;
    }

    public boolean isRoaming() {
        return netInfo.isRoaming();
    }

    public class isLinked implements Runnable{
        boolean result =false;

        public isLinked() {
        }

        @Override
        public void run() {
            HttpClient client = new DefaultHttpClient();
            StringBuilder builder = new StringBuilder();
// HttpGet连接对象使用get方式请求

            HttpGet myget = new HttpGet("http://www.baidu.com/");
            try {
// HttpResponse对象，连接成功后的一个响应对象
                HttpResponse response = client.execute(myget);
// 返回值为200，即为服务器成功响应了请求
                if (response.getStatusLine().getStatusCode() == 200) {
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
