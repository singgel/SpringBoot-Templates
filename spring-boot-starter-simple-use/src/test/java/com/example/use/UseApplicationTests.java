package com.example.use;

import com.hks.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UseApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private ExampleService exampleService;

	@Test
	public void testStarter() {
		System.out.println(exampleService.wrap("hello"));
	}

}
