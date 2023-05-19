package com.example.CateringApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CateringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CateringAppApplication.class, args);
	}

}
