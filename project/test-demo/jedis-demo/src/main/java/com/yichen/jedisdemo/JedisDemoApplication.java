package com.yichen.jedisdemo;

import com.yichen.jedisdemo.jedis.utils.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
public class JedisDemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(JedisDemoApplication.class, args);
	}

}
