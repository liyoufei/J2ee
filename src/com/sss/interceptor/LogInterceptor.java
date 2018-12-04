package com.sss.interceptor;


import com.sss.utility.LogUtility;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.LinkedHashMap;

public class LogInterceptor {

    //ThreadLocal对象能保证不同线程都保存有自己的数据副本，保证同一时间的多次调用不会读取脏数据
    ThreadLocal<LinkedHashMap<String,String>> data = new ThreadLocal<>();


    public void preAction(String actionName){
        data.set(new LinkedHashMap<String, String>());
        LinkedHashMap<String,String> map = data.get();
        map.put("name",actionName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("s-time",simpleDateFormat.format(new Date()));
        data.set(map);

    }

    public void afterAction(String result){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LinkedHashMap<String,String> map = data.get();
        map.put("e-time",simpleDateFormat.format(new Date()));
        map.put("result",result);
        data.set(map);

        //调用基本工具类将数据存入xml文件中，无需多次IO操作，提升效率
        LogUtility.saveLog(data.get());
        data.set(new LinkedHashMap<String, String>());
    }


}