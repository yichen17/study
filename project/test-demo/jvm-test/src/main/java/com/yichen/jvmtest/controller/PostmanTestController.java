package com.yichen.jvmtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.yichen.jvmtest.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/28 14:06
 */
@RestController
public class PostmanTestController {
    @PostMapping("/postman")
    public JSONObject postman(String name,String age, String sex){
        System.out.println(name+"|"+age+"|"+sex);
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("name",name);
        map.put("age",age);
        map.put("sex",sex);
        return new JSONObject(map);
    }

    @PostMapping("/postman1")
    public String postman1(String name,HttpServletRequest request){
        System.out.println(name);
        return name;
    }

    @PostMapping("/postman2")
    public String postman2(HttpServletRequest request){
        String name=request.getParameter("name");
        System.out.println(name);
        return name;
    }

    @PostMapping("/postman3")
    public String postman3(@RequestBody String name){
        System.out.println(name);
        return name;
    }


    @PostMapping("/testConstruct")
    public String testConstruct(@RequestBody Student student){
        return "testConstruct:  "+JSONObject.toJSONString(student);
    }


    @GetMapping("/get")
    public String get(){
        return "get";
    }
}
