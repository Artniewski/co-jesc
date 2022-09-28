package com.ocado.cojesc.service;

import com.ocado.cojesc.demo.FacebookRestaurantService;
import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantsService {

    private final FacebookRestaurantService restaurantService;

    public Optional<FacebookPost> findNewestLunchPost(Restaurant restaurant) {
        log.info("Searching for {} restaurant lunch menu.", restaurant.getName());
        Optional<FacebookPost> menu = restaurantService.findNewestMenuPost(restaurant);
        menu.ifPresentOrElse(
                (p) -> log.info("Lunch menu for {} restaurant found.", restaurant.getName()),
                () -> log.info("Lunch menu for {} restaurant not found.", restaurant.getName())
        );
        return menu;
    }
}
