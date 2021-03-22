package com.yichen.gatewaytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayTestApplication.class, args);
	}


//	  编程式  实现
	@Bean
	public RouteLocator redirectRouteLocator(RouteLocatorBuilder builder){
//		return builder.routes().route("path_router",r->r.path("/get")
//		.uri("http://127.0.0.1:8080/api-gateway/get")).build();
		return builder.routes()
				.route(p -> p
						.path("/")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("https://www.baidu.com/s?ie=UTF-8&wd=66"))
				.build();
	}

}
