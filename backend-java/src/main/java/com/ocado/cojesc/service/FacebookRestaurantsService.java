package com.ocado.cojesc.service;

import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookRestaurantsService {

    private final CacheAwareFacebookRestaurantService restaurantService;

    public List<FacebookPost> getAllPosts(List<Restaurant> restaurants) {
        log.info("Searching for all lunch menus...");
        List<FacebookPost> allPosts = new LinkedList<>();
        for (Restaurant restaurant : restaurants) {
            log.info("Searching for {} restaurant lunch menu.", restaurant.getName());
            restaurantService.findNewestMenuPost(restaurant).ifPresentOrElse(
                    (p) -> {
                        allPosts.add(p);
                        log.info("Found Lunch menu for {} restaurant.", restaurant.getName());
                    },
                    () -> log.info("Lunch menu not found for {} restaurant", restaurant.getName()));
        }
        log.info("Found lunch menus for restaurants: {}", allPosts.stream().map(FacebookPost::restaurantName).toList());
        return allPosts;
    }
}
