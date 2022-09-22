package com.ocado.cojesc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class CoJescApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoJescApplication.class, args);
	}

}
