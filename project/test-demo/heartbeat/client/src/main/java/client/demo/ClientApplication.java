package client.demo;

import client.demo.task.NettyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "client.demo.*")
public class ClientApplication implements CommandLineRunner {

	@Autowired
	private NettyTask nettyTask;

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new Thread(nettyTask).start();
	}
}
