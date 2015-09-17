package com.example.zy.stry.util;

import com.example.zy.stry.entity.BookEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {
    public static List<BookEntity> getBookEntitys(String in){
        List<BookEntity> lt =new ArrayList<>();
        try {
            String patternString =
                    "<tr class=\"odd\" onMouseOut=\"this.className='even';\" onMouseOver=\"this.className='evenfocus';\">";
            Pattern pattern = Pattern.compile(patternString,
                    Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
            String[] result = pattern.split(in);
            return parse(result);
        }
        catch (Exception e){
        }
        return lt;
    }
    static List<BookEntity> parse(String[] text) throws IOException {
        BookEntity se ;
        List<BookEntity> lt =new ArrayList<>();
        String patternString =
                "<td align=\"center\">\\s+(.*\\b)\\s+</td>";
        Pattern pattern = Pattern.compile(patternString,
                Pattern.CASE_INSENSITIVE);  //  Pattern.MULTILINE

        for (int i = 1; i < text.length; ++i) {
            Matcher matcher = pattern.matcher(text[i]);
            se = new BookEntity();
            se.isTaking(-1);
            int ca = 0;
            while (matcher.find()) {


                String course = matcher.group(1);
                switch (ca) {
                    case 0:
                        se.courseid=Integer.parseInt(course);;
                        ++ca;
                        break;
                    case 1:
                        se.coursenum=course;
                        ++ca;
                        break;
                    case 2 :
                        se.bookname=course;
                        se.coursename=course;
                        se.setBook(course);
                        ++ca;
                        break;
                    case 3 :
                        se.academic_credit=course;
                        ++ca;
                        break;
                    case 4 :
                        se.course_status=course;
                        ++ca;
                        break;

                }
            }
            lt.add(se);
        }
        return lt;
    }
    /*
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
    */
    public static List<BookEntity> getCourse(String in){
        List<BookEntity> lt =new ArrayList<>();
        try {
            String patternString =
                    "<tr class=\"odd\" onMouseOut=\"this.className='even';\" onMouseOver=\"this.className='evenfocus';\">";
            Pattern pattern = Pattern.compile(patternString,
                    Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
            String[] result = pattern.split(in);
            return parseForTak(result);
        }
        catch (Exception e){
        }
        return lt;
    }
    static List<BookEntity> parseForTak(String[] text) throws IOException {
        BookEntity se ;
        List<BookEntity> lt =new ArrayList<>();
        String patternString =
                "<td rowspan=\"2\" >\\s*&nbsp;\\s*(.+\\b)\\s*</td>";
        Pattern pattern = Pattern.compile(patternString,
                Pattern.CASE_INSENSITIVE);  //  Pattern.MULTILINE

        for (int i = 1; i < text.length; ++i) {
            Matcher matcher = pattern.matcher(text[i]);
            se = new BookEntity();
            se.isTaking(1);
            int ca = 0;
            while (matcher.find()) {
                String course = matcher.group(1);
                switch (ca) {
                    case 0:
                        ++ca;
                        break;
                    case 1:
                        se.courseid=Integer.parseInt(course);
                        ++ca;
                        break;
                    case 2 :
                        se.setBook(course);
                        ++ca;
                        break;
                    case 3 :
                        se.coursenum=course;
                        ++ca;
                        break;
                    case 4 :
                        se.academic_credit=course;
                        ++ca;
                        break;
                    case 5 :
                        se.course_status=course;
                        ++ca;
                        break;
                    case 6 :
                        ++ca;
                        break;
                    case 7 :
                        ++ca;
                        break;

                }
            }
            lt.add(se);
        }
        return lt;
    }
    /*
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
    */
    public static String parserForTLog (String in){
        try {
            String patternErrString =
                    "<td class=\"errorTop\"><strong><font color=\"#990000\">([^0-9]+)</font>";
            Pattern patternErr = Pattern.compile(patternErrString,
                    Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
            Matcher matcherErr = patternErr.matcher(in);
            if (matcherErr.find()) {
                return matcherErr.group(1);

            }
            String patternLogString =
                    "<frame src=\"/menu/s_top.jsp\" name=\"topFrame\" scrolling=\"NO\" noresize frameborder=\"NO\" border=\"0\" framespacing=\"0\">";
            Pattern patternLog = Pattern.compile(patternLogString,
                    Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
            Matcher matcherLog = patternLog.matcher(in);
            if (matcherLog.find()) {
                return "log";

            }
        }
        catch (Exception e){
            //throw e ;
        }
        return "err";
    }
    static void parseForInfo(String text) throws IOException{
        String patternString =
                "<td class=\"fieldName\" width=\"180\">\\s*(.+)\\s*</td>";
        Pattern pattern = Pattern.compile(patternString,
                Pattern.CASE_INSENSITIVE  );  //  Pattern.MULTILINE
        Matcher matcher = pattern.matcher( text);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
                String course = matcher.group(1);
                //if(!isNumeric(course,0)&&!isNumeric(course,1))
                list.add("-----"+course+"++++++\n");
        }
        System.out.println(list);
    }

}
