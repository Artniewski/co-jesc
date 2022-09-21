package com.ocado.cojesc.restaurant;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "cojesc")
public class RestaurantsProvider {
    List<Restaurant> restaurants = Collections.emptyList();
}
