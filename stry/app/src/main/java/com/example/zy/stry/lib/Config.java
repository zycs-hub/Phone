package com.example.zy.stry.lib;

import java.security.PublicKey;

/**
 * Created by wendy on 15-7-1.
 */
public class Config {
    //设置登入和注册的URL
    private static String LOG_URL = "http://bookt.sinaapp.com/phone/login/";
    public static String REGIST_URL = "http://bookt.sinaapp.com/phone/register/";
    public static String LOGIN_INFO_ERROR = "用户名或密码不正确";

    public static String getLogUrl() {
        return LOG_URL;
    }

}
