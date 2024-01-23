package com.ws.startupProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StartupProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartupProjectApplication.class, args);
	}

}
