package com.example.zy.stry.entity;

/**
 * Created by zy on 15/8/6.
 */
public class takingCourseEntity {
    private String course = null;
    public String getCourse(){
        return course;
    }
    public void setCourse(String book){
        this.course=book;
    }
    @Override 
    public String toString(){
        return "takingCourse [book="+course+"]";
    }
}
