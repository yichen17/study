package com.yichen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/30 10:21
 * @describe  用于测试非 默认路径下，是否可以根据初始 url 在cas 认证之后重定向
 *          现如今无法执行，貌似要springboot项目才可以
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "/WEB-INF/test.jsp";
    }
    @RequestMapping("/test1")
    @ResponseBody
    public String test1(){
        return "test1";
    }
}
