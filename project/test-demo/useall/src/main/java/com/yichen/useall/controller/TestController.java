package com.yichen.useall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/18 9:48
 * @describe  用于测试 gateway模块的处理流程
 */
@RestController
public class TestController {

    @GetMapping("/api-gateway/hello")
    public String testGateway(){
        return "test gateway";
    }

}
