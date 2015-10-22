package com.example.zy.stry.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.example.zy.stry.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


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
            return true;
        }
        return false;
    }

    public boolean isRoaming() {
        return netInfo.isRoaming();
    }

}
