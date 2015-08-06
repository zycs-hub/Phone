package com.example.zy.stry.util;

import com.example.zy.stry.entity.UserEntity;
import com.example.zy.stry.entity.takingCourseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {
    public static List<UserEntity> getUserEntitys(String in){
        List<UserEntity> lt =new ArrayList<UserEntity>();
        List<String> ia=new ArrayList<>();
        UserEntity se =null;
        try {
            String patternString =
                    "<td align=\"center\">\\s+((.*\\b))\\s+</td>";
            Pattern pattern = Pattern.compile(patternString,
                    Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
            Matcher matcher = pattern.matcher(in);
            while (matcher.find()) {
                String course = matcher.group(1);
                if(!isNumeric(course,0)&&!isNumeric(course,1)&&!isNotNess(course)){
                se =new UserEntity();
                se.setBook(course);
                lt.add(se);
                } 
                //ia.add(course);
              //  se=null;
            }
        }
        catch (Exception e){
        }
        return lt;
    }
    public static boolean isNumeric(String str ,int i){
        if (!Character.isDigit(str.charAt(i))){
            return false;
        }
        return true;
    }
    public static boolean isNotNess(String str){
        if(str.subSequence(0, 2).equals("必修")
                ||str.subSequence(0, 2).equals("选修")
                ||str.subSequence(0, 2).equals("任选")
                ||str.subSequence(0, 2).equals("体育")
                ||str.subSequence(0, 2).equals("军训")
                )
            return true;
        return false;
    }
    public static List<takingCourseEntity> getCourse(String in){
        List<takingCourseEntity> lt =new ArrayList<>();
        List<String> ia=new ArrayList<>();
        takingCourseEntity se =null;
        try {
            String patternString =
                    "<td rowspan=\"2\" >\\s*&nbsp;\\s*((.+\\b))\\s*</td>";
            Pattern pattern = Pattern.compile(patternString,
                    Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
            Matcher matcher = pattern.matcher(in);
            while (matcher.find()) {
                String course = matcher.group(1);
                if(!isNumeric(course,0)&&!isNumeric(course,1)&&!isNotNessC(course)){
                    se =new takingCourseEntity();
                    se.setCourse(course);
                    lt.add(se);
                }
                //ia.add(course);
                //  se=null;
            }
        }
        catch (Exception e){
        }
        return lt;
    }
    public static boolean isNotNessC(String str){
        if(str.subSequence(0, 2).equals("必修")
                ||str.subSequence(0, 2).equals("选修")
                ||str.subSequence(0, 2).equals("任选")
                ||str.subSequence(0, 2).equals("正常")
                ||str.subSequence(0, 2).equals("拟选")
                ||str.subSequence(0, 2).equals("选中")
                )
            return true;
        return false;
    }
    public static String parserForTLog (String in){
        try {
            String patternErrString =
                    "<td class=\"errorTop\"><strong><font color=\"#990000\">([^0-9]+)</font>";
            Pattern patternErr = Pattern.compile(patternErrString,
                    Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
            Matcher matcherErr = patternErr.matcher(in);
            while (matcherErr.find()) {
                return matcherErr.group(1);

            }
            String patternLogString =
                    "<frame src=\"/menu/s_top.jsp\" name=\"topFrame\" scrolling=\"NO\" noresize frameborder=\"NO\" border=\"0\" framespacing=\"0\">";
            Pattern patternLog = Pattern.compile(patternLogString,
                    Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
            Matcher matcherLog = patternLog.matcher(in);
            while (matcherLog.find()) {
                return "log";

            }
        }
        catch (Exception e){
        }
        return "err";
    }

}
