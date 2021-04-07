package com.yichen.useall;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.yichen.useall.dao")
@EnableDiscoveryClient
//@NacosPropertySource(dataId = "useall.properties",groupId = "com.yichen")
public class UseallApplication {



	public static void main(String[] args) {
		SpringApplication.run(UseallApplication.class, args);
	}

}
