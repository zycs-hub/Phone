package com.example.zy.stry.util;

/**
 * Created by zy on 15/7/8.
 */
import android.os.Handler;

import com.example.zy.stry.entity.BookEntity;

import java.lang.String;
import java.util.List;


public class ThreadBooksMessage implements Runnable {
    private Handler han = null;
    private String name, password;
    private String purpose = "";

    public ThreadBooksMessage(Handler han, String name, String password, String purpose ) {
        super();
        this.han = han;
        this.name = name;
        this.password = password;
        this.purpose = purpose;
    }

    @Override
    public void run() {

        /*
        * -1 err unknown
        * 1 log
        * -2 账户名err
        * -3 mm  err
        * */
        String logStatus;
        logStatus = XMLParser.parserForTLog(GetInputStream.login(name, password));
        switch (logStatus){
            case "err" :
                han.sendEmptyMessage(-1);
                break;
            case "log" :
                if (purpose.equals("Override")) {
                    BookGlobla.lts = XMLParser.getBookEntitys(GetInputStream.View("grade", name, password));
                    BookGlobla.lts.addAll(XMLParser.getCourse(GetInputStream.View("curriculum", name, password)));
                    han.sendEmptyMessage(1);
                } else {
                    han.sendEmptyMessage(1);
                }
                break;
            case "你输入的证件号不存在，请您重新输入！" :
                han.sendEmptyMessage(-2);
                break;
            case "您的密码不正确，请您重新输入！":
                han.sendEmptyMessage(-3);
                break;
        }



    }
}