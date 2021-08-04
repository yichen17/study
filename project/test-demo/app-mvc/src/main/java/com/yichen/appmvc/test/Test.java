package com.yichen.appmvc.test;

import com.alibaba.fastjson.JSON;
import com.yichen.appmvc.model.Person;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/7/28 15:44
 * @describe 测试 fastjon能否识别  驼峰和下划线
 */
public class Test {
    public static void main(String[] args) {
        String data="{\"customer_id\":\"10\",\"app_id\":\"7421\"}";
        Person person= JSON.parseObject(data,Person.class);
        System.out.println(person);

//        String a=null;
//        System.out.println(Integer.parseInt(a));

        System.out.println("aa".equals(null));
        Map<String,String> map=new HashMap<>();
        map.remove("aa");

    }
}
