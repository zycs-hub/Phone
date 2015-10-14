package com.example.zy.stry.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.example.zy.stry.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

import static com.example.zy.stry.MainActivity.*;

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

    public void isOnline() {
        if(netInfo != null && netInfo.isConnectedOrConnecting()) {
            isLinked task = new isLinked(new Function<Boolean, Void>(){

                public Void apply(Boolean para) {
                    MainActivity.hvNetwork = para;
                    return null;
                }
            });
            executorService.submit(task);
        }
    }

    public boolean isRoaming() {
        return netInfo.isRoaming();
    }

    public class isLinked implements Runnable{
        private boolean result;
        private Function<Boolean, Void> callBack;


        public isLinked(Function<Boolean, Void> callBack) {
            this.callBack = callBack;
            result = false;
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
                    callBack.apply(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean getResult() {
            return result;
        }

    }
}
