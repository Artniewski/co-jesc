package com.ocado.cojesc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CoJescApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoJescApplication.class, args);
	}

}