package com.ocado.cojesc.crawler.service;

import java.util.List;

import com.ocado.cojesc.FacebookProperties;
import com.ocado.cojesc.crawler.FacebookPost;
import com.ocado.cojesc.crawler.validator.LunchPostValidator;
import com.ocado.cojesc.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FacebookRestaurantsService {

    private final LunchPostValidator lunchPostValidator;
    private final FacebookProperties fbProperties;
    private final ScraperFeignClient scraperClient;

    public List<FacebookPost> getAllPosts(List<Restaurant> restaurants) {
        restaurants.stream().map(restaurant ->
                scraperClient.getPosts(restaurant)
        ).toList();
        return null;
    }

}
