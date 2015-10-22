package com.example.zy.stry.entity;

import java.io.Serializable;

/**
 * Created by zy on 15/10/14.
 */
public class MessageEntity implements Serializable {
    public int year;
    public int moon;
    public int day;
    public int hour;
    public int min;
    public int isRead=2;
    public String message;
    public String data;
}
