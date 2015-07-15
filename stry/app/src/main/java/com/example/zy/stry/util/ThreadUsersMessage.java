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
    public ThreadUsersMessage(Handler han,String name,String password){
        super();
        this.han=han;
        this.name=name;
        this.password=password;
    }
    @Override
    public void run(){
       UserGlobla.lts= PullForXml.getUserEntitys(GetInputStream.getInputStream(name, password, 1));
        CourseGlobla.lts=PullForXml.getCourse(GetInputStream.getInputStream(name,password,0));
        if(UserGlobla.lts!=null){
            han.sendEmptyMessage(3);
        }
        else if (CourseGlobla.lts!=null){
            han.sendEmptyMessage(1);

        }
        else {
            han.sendEmptyMessage(4);
        }
    }

}