package com.yichen.rabbitmqcase;

import com.yichen.rabbitmqcase.producer.Producer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RabbitmqCaseApplicationTests {

	@Autowired
	Producer creater;

	@Test
	public void creatertest(){
		creater.create();
	}

}
