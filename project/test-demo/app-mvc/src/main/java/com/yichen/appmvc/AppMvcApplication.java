package com.yichen.appmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Servle	tComponentScan("com.yichen")
@ComponentScan(value = "com.yichen")
public class AppMvcApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppMvcApplication.class).web(true).run(args);
	}

}
