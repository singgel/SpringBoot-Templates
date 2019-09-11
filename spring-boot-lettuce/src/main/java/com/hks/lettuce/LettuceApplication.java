package com.hks.lettuce;

import com.hks.lettuce.dao.EmployeeDAO;
import com.hks.lettuce.dao.FamilyDAO;
import com.hks.lettuce.dao.FriendDAO;
import com.hks.lettuce.dao.UserDAO;
import com.hks.lettuce.entity.Person;
import com.hks.lettuce.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LettuceApplication implements CommandLineRunner{

	@Autowired
	AsyncTaskService asyncTaskService;

	public static void main(String[] args) {
		SpringApplication.run(LettuceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) {
			asyncTaskService.executeAsyncTask(i);
		}
	}
}
