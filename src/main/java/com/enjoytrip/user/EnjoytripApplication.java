package com.enjoytrip.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.enjoytrip"})
public class EnjoytripApplication {
	public static void main(String[] args) {
		SpringApplication.run(EnjoytripApplication.class, args);   
	}
}
