package com.yichen;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/23 14:44
 */
public class Test {
    public static void main(String[] args) {
        String name="严先生";
        // 该方法将字符串指定的char 字符转为unicode 码
//        java 实际 存储 char  也是以 unicode 方式存储的
        int code=name.codePointAt(0);
        System.out.println(code);
//        System.out.println(code=='y');

        System.out.println(Long.MIN_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(-Long.MAX_VALUE);

        JSONObject object=JSONObject.parseObject("{\"name\":\"yichen\"}");
        System.out.println(object.get("name"));

        System.out.println(TimeZone.getTimeZone("GMT-11:00"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=sdf.format(new Date());
        System.out.println(date);

    }
}
