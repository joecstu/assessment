package com.kbtg.bootcamp.posttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PostTestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(PostTestApplication.class, args);
	}

}
