package server.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import server.demo.task.NettyTask;

@SpringBootApplication
@ComponentScan(value = "server.demo.*")
public class ServerApplication implements CommandLineRunner
{

	@Autowired
	private NettyTask nettyTask;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		new Thread(nettyTask).start();
	}
}
