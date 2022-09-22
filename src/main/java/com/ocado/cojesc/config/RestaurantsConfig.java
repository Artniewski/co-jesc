package com.ocado.cojesc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "cojesc")
public class RestaurantsConfig {
    List<Restaurant> restaurants = Collections.emptyList();
}
