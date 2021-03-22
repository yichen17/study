package com.yichen.gatewaytest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/18 9:48
 * @describe  用于测试 gateway模块的处理流程
 */
@RestController
public class TestController {

    @RequestMapping("/api-gateway/get")
    public String testGateway(){
        return "test gateway";
    }

}
