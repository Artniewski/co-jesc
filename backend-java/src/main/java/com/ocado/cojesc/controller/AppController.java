package com.ocado.cojesc.controller;

import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.restaurant.RestaurantsProvider;
import com.ocado.cojesc.service.FacebookRestaurantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final FacebookRestaurantsService service;
    private final RestaurantsProvider restaurantsProvider;

    @GetMapping("/all")
    public List<FacebookPost> getAllMenus() throws ExecutionException, InterruptedException {
        List<Restaurant> allRestaurants = restaurantsProvider.getRestaurants();
        return service.getAllPosts(allRestaurants);
    }

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
