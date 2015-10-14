package com.example.zy.stry.entity;

import java.io.Serializable;

/**
 * Created by zy on 15/10/14.
 */
public class Message implements Serializable {
    public String year;
    public String moon;
    public String day;
    public String hour;
    public String min;
    public int isRead=2;
    public String message;
    public String data;
}
