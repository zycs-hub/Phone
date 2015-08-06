package com.example.zy.stry.util;

/**
 * Created by zy on 15/8/4.
 */
public class LogStatusGlobla {
    private static int Log=-1;
    public static void setLogStatus(int log){
        Log=log;
    }
    public static int getLogStatus(){
        return Log;
    }
}
