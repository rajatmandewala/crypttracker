package com.rajat.cryp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrypApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrypApplication.class, args);
	}

}
