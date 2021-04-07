package com.yichen.webfluxdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/7 14:25
 * @describe  RESTful(Representational State Transfer，表述性状态转移)    实现webFlux
 */
@RestController
public class HelloController {
 
    @GetMapping("/hello1")
    public String hello1() {
        return "Hello World!";
    }
    @GetMapping("/hello2")
    public Mono<String> hello2() {
        return Mono.just("Hello World!");
    }
    
}
