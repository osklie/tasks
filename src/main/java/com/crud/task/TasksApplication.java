package com.crud.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


//public class TasksApplication extends SpringBootServletInitializer {
@SpringBootApplication
public class TasksApplication {
	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}
//	@Override
//	protected SpringApplicationBuilder configure (SpringApplicationBuilder application) {
//		return application.sources(TasksApplication.class);
//	}
}