package com.yichen.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/15 14:48
 */
@RestController
public class TestController {
    @PostMapping("/show")
    public String showResultFromCallBack(@RequestParam Map<Object,Object> map){
        StringBuilder builder=new StringBuilder();
        for(Object key:map.keySet()){
            builder.append(key+"  ");
            builder.append(map.get(key));
            builder.append("\n");
        }
        System.out.println("接收到回调调用，内容如下\n"+builder.toString());
        return builder.toString();
    }

    @GetMapping("/api-gateway/hello")
    public String testGateway(){
        return "test gateway";
    }

}
