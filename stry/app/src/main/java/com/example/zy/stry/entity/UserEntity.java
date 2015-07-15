package com.example.zy.stry.entity;

/**
 * Created by zy on 15/7/8.
 */
public class UserEntity {
    private String book = null;
    public String getBook(){
        return book;
    }
    public void setBook(String book){
        this.book=book;
    }
    @Override
    public String toString(){
        return "BookEntity [book="+book+"]";
    }
}
