package com.ocado.cojesc.controller;

import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.restaurant.RestaurantsProvider;
import com.ocado.cojesc.service.RestaurantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LunchMenuController {

    private final RestaurantsService service;
    private final RestaurantsProvider restaurantsProvider;

    @GetMapping("/{facebookId}/menu")
    public List<FacebookPost> restaurantMenu(@PathVariable("facebookId") String facebbokId) {
        return restaurantsProvider.getRestaurants().stream()
                .filter(p -> p.getFacebookId().equals(facebbokId))
                .flatMap(r -> service.findNewestLunchPost(r).stream())
                .toList();
    }

    @GetMapping("/restaurants")
    public List<Restaurant> allRestaurants() {
        return  restaurantsProvider.getRestaurants();
    }
}
