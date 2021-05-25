package com.yichen.test;

import java.util.Date;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/25 16:02
 * @describe  测试时间戳与具体时间的转换    分钟，秒数一致，但是小时数差8，
 */
public class TestTimestamp {
    public static void main(String[] args) {
        long now=System.currentTimeMillis();
        now=now/1000;
        Date date=new Date();
        System.out.println(date.toString());
        long second=now%60;
        now=now/60;
        long minute=now%60;
        now=now/60;
        //  小时不一致，是因为时间戳 默认计算地点在本初子母线，即 格林威治时间（GMT）。而我目前计算机时间，是按照东八区计算的，比它早8个小时。即比它大8
        long hour=now%24;
        System.out.println("second:"+second+"minute:"+minute+"hour:"+hour);
    }
}
