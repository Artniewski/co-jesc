package com.ocado.cojesc.restaurant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "cojesc")
public class RestaurantsProvider {
    List<Restaurant> restaurants = Collections.emptyList();

    public Restaurant findByFacebookId(String fbId) {
        return restaurants.stream()
                .filter(r -> r.getFacebookId().equals(fbId))
                .findFirst()
                .orElseThrow();
    }
}
