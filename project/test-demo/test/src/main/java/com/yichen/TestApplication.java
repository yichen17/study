package com.yichen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/23 10:52
 * @describe
 */
@SpringBootApplication
@MapperScan("com.yichen")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
