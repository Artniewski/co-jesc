package com.ocado.cojesc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.restaurant.RestaurantsProvider;
import com.ocado.cojesc.service.FacebookRestaurantsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final FacebookRestaurantsService service;
    private final RestaurantsProvider restaurantsProvider;

    @GetMapping("/all")
    public List<String> getAllMenus() {
        List<Restaurant> allRestaurants = restaurantsProvider.getRestaurants();
        return service.getAllPosts(allRestaurants);
    }

}
