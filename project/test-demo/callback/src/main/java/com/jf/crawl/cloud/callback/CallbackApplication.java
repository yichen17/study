package com.jf.crawl.cloud.callback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liumg
 * @version 创建时间：2018/12/29.
 */
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication  
public class CallbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallbackApplication.class, args);
    }
}

