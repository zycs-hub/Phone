package com.example.zy.stry.lib;


/**
 * Created by wendy on 15-7-1.
 */
public class Config {
    //设置所有Http访问的URL
    private static  String HOME_URL = "http://transbook.sinaapp.com/phone/homePage";
    private static String LOG_URL = "http://transbook.sinaapp.com/phone/login/";
    public static String REGIST_URL = "http://transbook.sinaapp.com/phone/register/";
    private static String ADD_SELL_URL = "http://transbook.sinaapp.com/phone/addSaleBook";
    private static String BUY_URL = "http://transbook.sinaapp.com/phone/buy/";
//    private static String BUY_URL = "http://192.168.12.24:8080/phone/buy/";
    private static String EDIT_BOOK_URL = "http://transbook.sinaapp.com/phone/editBook/";
//    private static String EDIT_BOOK_URL = "http://192.168.13.223:8080/phone/editBook/";
    private static String EDIT_SELL_URL = "http://transbook.sinaapp.com/phone/editSell/";
    private static String ADD_MSG_URL = "http://transbook.sinaapp.com/phone/addMsg/";


    public static String LOGIN_INFO_ERROR = "用户名或密码不正确";
    public static String KEY_SUCCESS = "success";
    public static String LOAD_ERROR = "提交失败";


    public static String getHomeUrl() {
        return HOME_URL;
    }
    public static String getLogUrl() {
        return LOG_URL;
    }
    public static  String getAddSellUrl() {
        return ADD_SELL_URL;
    }
    public static String getBuyUrl() {
        return BUY_URL;
    }
    public static String getEditBookUrl() {
        return EDIT_BOOK_URL;
    }
    public static String getEditSellUrl() {
        return  EDIT_SELL_URL;
    }
    public static String getAddMsgUrl() {
        return ADD_MSG_URL;
    }
}
