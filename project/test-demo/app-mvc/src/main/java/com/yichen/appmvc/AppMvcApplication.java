package com.yichen.appmvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ServletComponentScan(value = "com.yichen")
@Component("com.yichen")
public class AppMvcApplication {

	public static void main(String[] args) {
		// springboot 启动是需要设置 启动环境，指定工作目录为当前的项目位置，否则会导致无法找到 jsp
		// 如果是通过maven springboot:run 启动项目的话， 如果项目扫描的位置存在 main() 函数可能会导致互斥
		new SpringApplicationBuilder(AppMvcApplication.class).web(true).run(args);
	}

}
