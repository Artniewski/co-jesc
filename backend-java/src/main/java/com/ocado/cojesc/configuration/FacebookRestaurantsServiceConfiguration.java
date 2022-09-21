package com.ocado.cojesc.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ocado.cojesc.crawler.service.FacebookRestaurantsService;

@Configuration
@EnableConfigurationProperties(FacebookProperties.class)
public class FacebookRestaurantsServiceConfiguration {

    @Bean
    FacebookRestaurantsService service(LunchPostValidator lunchPostValidator,
                                       FacebookProperties properties) {
        return new FacebookRestaurantsService(lunchPostValidator, properties);
    }
}
