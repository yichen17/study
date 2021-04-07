package com.yichen.jvmtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.yichen.jvmtest.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/25 11:16
 */
@RestController
public class TestController {




    @PostMapping("/show")
    public String show(@RequestHeader(value = "appId") String appId){
        System.out.println(appId);
        return "hello";
    }

    @PostMapping("/test")
    public String test(@RequestHeader(value = "appId") String appId,
                       @RequestBody Student student){
        System.out.println(student);
        return "ok";
    }

    @PostMapping("/test1")
    public String test1(
                       @RequestBody Student student){
        System.out.println(student);
        return "ok";
    }

    @PostMapping("/test2")
    public String test2(@RequestHeader(value = "appId") String appId){
        return "ok";
    }

}
