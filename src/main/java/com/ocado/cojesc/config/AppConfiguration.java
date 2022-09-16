package com.ocado.cojesc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfiguration {

    @Bean
    public List<Restaurant> getRestaurants(){
        return List.of(new Restaurant("podlatarniami","Pod Latarniami",List.of("Poniedziałek")));
    }
}
