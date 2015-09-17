package com.example.zy.stry.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean isRoaming() {
        return netInfo.isRoaming();
    }
}
