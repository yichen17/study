package com.yichen.curltest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/11 10:32
 */
@RestController
public class TestController {
    @GetMapping("/getResult")
    public String getResult(HttpServletRequest request){
//        两种获取cookie 的方式
//        System.out.println("cookies: "+request.getHeader("cookie"));
//        Cookie[] cookies = request.getCookies();
//        for(Cookie cookie:cookies){
//            System.out.println("name: "+cookie.getName()+"| value: "+cookie.getValue());
//        }

        return "hello world";
    }
    @PostMapping("/post")
    public String post(HttpServletRequest request){
        System.out.println(request.getParameter("name"));
        return "post result";
    }

}
