package com.ocado.cojesc.service;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ocado.cojesc.client.ScraperFeignClient;
import com.ocado.cojesc.restaurant.Restaurant;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookRestaurantsService {

    private final CacheAwareFacebookRestaurantService restaurantService;

    public List<String> getAllPosts(List<Restaurant> restaurants) {
        log.info("Searching for all menus.");
        return restaurants.stream()
                .map(restaurantService::getAllPosts)
                .flatMap(Collection::stream)
                .toList();
    }
}
