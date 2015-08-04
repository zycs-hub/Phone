package com.example.zy.stry.util;

/**
 * Created by zy on 15/7/8.
 */
import android.os.Handler;
import java.lang.String;

import java.security.PublicKey;

public class ThreadUsersMessage implements Runnable {
    private Handler han =null;
    private String name,password;
    private int logStatus=-1;
    public ThreadUsersMessage(Handler han,String name,String password){
        super();
        this.han=han;
        this.name=name;
        this.password=password;
    }
    @Override
    public void run(){
        logStatus = GetInputStream.logStatus(name, password);
        if (logStatus==-1){
            han.sendEmptyMessage(-1);
            return;
        }
        else if (logStatus==0){
            han.sendEmptyMessage(0);
            return;
        }
        else if (logStatus==1){
            han.sendEmptyMessage(1);
        }
        else if (logStatus==-2){
            han.sendEmptyMessage(-2);
        }
        else if (logStatus==-3){
            han.sendEmptyMessage(-3);
        }
        UserGlobla.lts= XMLParser.getUserEntitys(GetInputStream.View("grade",name, password));
        CourseGlobla.lts=XMLParser.getCourse(GetInputStream.View("curriculum", name, password));
        if(UserGlobla.lts!=null){
            han.sendEmptyMessage(3);
        }
        else if (CourseGlobla.lts!=null){
            //han.sendEmptyMessage(1);

        }
        else {
           // han.sendEmptyMessage(4);
        }
    }

}