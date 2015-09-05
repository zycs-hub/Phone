package com.example.zy.stry.entity;

/**
 * Created by zy on 15/7/8.
 */
public class BookEntity {
    private String book = null;
    //课是否在上
    private int isTaking = 0;
    //是否有书可以卖
    private int isSelected = 0;

    public String getBook(){
        return book;
    }
    public void setBook(String book){
        this.book=book;
    }
    public int isTaking(){
        return isTaking;
    }
    public void isTaking(int isTaking){
        this.isTaking=isTaking;
    }
    public int isSelected(){
        return isSelected;
    }
    public void isSelected(int isSelected){
        this.isSelected=isSelected;
    }
    @Override
    public String toString(){
        return "BookEntity [book="+book+"]";
    }
}