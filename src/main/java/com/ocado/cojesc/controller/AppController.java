package com.ocado.cojesc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.restaurant.RestaurantsProvider;
import com.ocado.cojesc.crawler.FacebookPost;
import com.ocado.cojesc.crawler.service.FacebookRestaurantsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final FacebookRestaurantsService service;
    private final RestaurantsProvider restaurantsProvider;

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    @GetMapping("/{id}")
    public List<String> getMenu(@PathVariable int id) {
        List<Restaurant> allRestaurants = restaurantsProvider.getRestaurants();
        return service.getAllPosts(List.of(allRestaurants.get(id))).stream().map(FacebookPost::getInnerHTML).toList();
    }

    @GetMapping("/all")
    public List<String> getAllMenus() {
        List<Restaurant> allRestaurants = restaurantsProvider.getRestaurants();
        return service.getAllPosts(allRestaurants).stream().map(FacebookPost::getInnerHTML).toList();
    }

    @GetMapping("/login")
    public void login() {
        service.login();
    }

}
