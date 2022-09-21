package com.ocado.cojesc.crawler.service;

import java.util.List;

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
