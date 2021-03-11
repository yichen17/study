package com.yichen.jedisdemo.jedis.controller;

import com.yichen.jedisdemo.jedis.utils.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/4 17:12
 */
@RestController

public class TestController {
    @Autowired
    private Properties properties;
    @RequestMapping("/show")
    public String show(){
        System.out.println("name: "+properties.getRealName()+"| age: "+properties.getAge());
        return properties.getRealName();
    }
    @RequestMapping("/test/url/path")
    public String path(HttpServletRequest request){
        System.out.println("Path: "+request.getContextPath());
        return request.getContextPath();
    }


}
