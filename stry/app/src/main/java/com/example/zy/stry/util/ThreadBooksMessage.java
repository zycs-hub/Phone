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

    public ThreadBooksMessage(Handler han, String name, String password, String purpose) {
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
        int logStatus = GetInputStream.logStatus(name, password);
        if (logStatus == -1) {
            han.sendEmptyMessage(-1);
        }
        else if (logStatus == 1) {
            if (purpose.equals("Override")) {
                List<BookEntity> t;
                t = XMLParser.getBookEntitys(GetInputStream.View("grade", name, password));
                for(BookEntity book : XMLParser.getCourse(GetInputStream.View("curriculum", name, password)))
                    t.add(book);
                BookGlobla.lts = t;
                han.sendEmptyMessage(1);
            } else {
                han.sendEmptyMessage(1);
            }
        }
        else if (logStatus == -2) {
            han.sendEmptyMessage(-2);
        }
        else if (logStatus == -3) {
            han.sendEmptyMessage(-3);
        }

    }
}