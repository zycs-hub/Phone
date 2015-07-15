package com.example.zy.stry.util;

import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;



/**
 * Created by zy on 15/7/8.
 */
public class GetInputStream {
    static String sURL="http://202.118.65.20:8081/";
    static String log="loginAction.do";
    static String pages="gradeLnAllAction.do?type=ln&oper=fainfo";
    static String coursepage="xkAction.do?actionType=6";
    static String responseCookie;//标示Session必须
    public static String  getInputStream(String name,String password, int i) {
        try {
            login(name, password);
            if(i==1) {return View(pages);}
            else {return ViewCourse(coursepage);}


        } catch (Exception e) {
            return e.toString();
        }

    }
    public static void  login(String usr,String pwd) throws IOException
    {
        StringBuilder sbR = new StringBuilder();


        URL url = new URL(sURL+log);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10000);
        connection.setDoInput(true);
        connection.setDoOutput(true);//允许连接提交信息
        connection.setRequestMethod("POST");//网页默认“GET”提交方式

        StringBuffer sb = new StringBuffer();
        sb.append("zjh="+usr);
        sb.append("&mm="+pwd);

        connection.setRequestProperty("Content-Length",
                String.valueOf(sb.toString().length()));

        OutputStream os = connection.getOutputStream();
        os.write(sb.toString().getBytes("GBK"));
        os.close();

        //取Cookie
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GBK"));
        responseCookie = connection.getHeaderField("Set-Cookie");//取到所用的Cookie
        //System.out.println("cookie:" + responseCookie);
        // return responseCookie;



    }


    //返回页面
    private static String View(String page) throws IOException
    {
        StringBuilder sbR = new StringBuilder();


        //打开URL连接
        URL url1 = new URL(sURL+page);
        HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();

        //给服务器送登录后的cookie
        connection1.setRequestProperty("Cookie", responseCookie);
        connection1.setConnectTimeout(10000);

        //读取返回的页面信息到br1
        BufferedReader br1 = new BufferedReader(new InputStreamReader(connection1.getInputStream(),"GBK"));

        //取返回的页面,br1转sbR
        String line1= br1.readLine();
        while (line1 != null) {
            sbR.append(line1+"\n");
            line1 = br1.readLine();
        }
        return sbR.toString();
    }

    private static String ViewCourse(String page) throws IOException
    {
        StringBuilder sbR = new StringBuilder();


        //打开URL连接
        URL url1 = new URL(sURL+page);
        HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();

        //给服务器送登录后的cookie
        connection1.setRequestProperty("Cookie", responseCookie);
        connection1.setConnectTimeout(10000);

        //读取返回的页面信息到br1
        BufferedReader br1 = new BufferedReader(new InputStreamReader(connection1.getInputStream(),"GBK"));

        //取返回的页面,br1转sbR
        String line1= br1.readLine();
        while (line1 != null) {
            sbR.append(line1+"\n");
            line1 = br1.readLine();
        }
        return sbR.toString();
    }
}