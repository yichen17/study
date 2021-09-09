package client.demo;

import client.demo.task.NettyTask;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qxc
 */
@SpringBootApplication
@MapperScan(value = "client.demo.dao")
public class ClientApplication implements CommandLineRunner {

	@Autowired
	private NettyTask nettyTask;

	public static void main(String[] args) {
//		SpringApplication.run(ClientApplication.class, args);
		new SpringApplicationBuilder(ClientApplication.class).web(WebApplicationType.SERVLET).run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		new Thread(nettyTask).start();
	}
}
