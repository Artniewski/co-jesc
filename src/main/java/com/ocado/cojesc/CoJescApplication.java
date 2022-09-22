package com.ocado.cojesc;

import com.ocado.cojesc.config.RestaurantsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(RestaurantsConfig.class)
@EnableScheduling
public class CoJescApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoJescApplication.class, args);
	}

}
