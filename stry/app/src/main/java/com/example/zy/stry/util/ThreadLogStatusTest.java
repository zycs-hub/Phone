package com.example.zy.stry.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.zy.stry.entity.BookEntity;

import java.util.List;

/**
 * Created by zy on 15/8/4.
 */
public class ThreadLogStatusTest implements Runnable{
    private Handler hanStatus =null;
    //private int logStatus=-1;
    My_DB db=null;
    SQLiteDatabase job =null;
    Context context;

    /*
    LogStatusGlobla
    * 0 默认
    * 1 登录
    * 2 登录有网
    * 3 验证成功
    * 4 绑定教务处
    * -1 未登录
    * -2 登录无网
    * -4 未绑定教务处
    */
    public ThreadLogStatusTest(Handler hanStatus,Context context) {
        super();
        this.hanStatus = hanStatus;
        this.context=context;
        }
    @Override
    public void run(){
        //hanStatus.sendEmptyMessage(0);
        if (isLog()/*isLog()登录*/) {
            hanStatus.sendEmptyMessage(1);
            if (isWeb()/*有网*/) {
                hanStatus.sendEmptyMessage(2);
                if (true/*验证*/) {
                    hanStatus.sendEmptyMessage(3);
                    if (false/*绑定教务处*/) {
                        hanStatus.sendEmptyMessage(4);
                    }
                    else /*未绑定教务处*/{
                        hanStatus.sendEmptyMessage(-4);
                    }
                }
                else/*验证失败，要求严格,网络邻接不通畅不算*/{
                    hanStatus.sendEmptyMessage(-1);
                }
            }
            else/*登录无网*/{
                hanStatus.sendEmptyMessage(-2);
            }
        }
        else /*未登录*/{
            hanStatus.sendEmptyMessage(-1);
        }

    }

    private boolean isWeb(){
        String logStatus;
        logStatus = XMLParser.parserForTLog(GetInputStream.login("1", "1"));
        switch (logStatus){
            case "err" :
                hanStatus.sendEmptyMessage(-1);
                return false;
            case "你输入的证件号不存在，请您重新输入！" :
                hanStatus.sendEmptyMessage(-2);
                return true;
            default:
                return false;
        }

    }



     private boolean isLog(){
         db=new My_DB(context,My_DB.MY_DB_MANE,null,My_DB.MY_DB_VERSION);///may err //really err
         job=db.getReadableDatabase();
         List<BookEntity> lt= db.getUserAll(job);
         return !lt.isEmpty();
     }



}
