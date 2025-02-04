package com.team4.taskmanager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import com.team4.taskmanager.controller.RestController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class TaskmanagerApplicationTests {
	
	@Autowired
	private RestController restcontroller;

	@Test
	void contextLoads() {
	}

}
