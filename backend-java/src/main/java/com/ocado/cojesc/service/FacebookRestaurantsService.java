package com.ocado.cojesc.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ocado.cojesc.client.ScraperFeignClient;
import com.ocado.cojesc.restaurant.Restaurant;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacebookRestaurantsService {

    private final ScraperFeignClient client;

    public List<String> getAllPosts(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(Restaurant::getFacebookId)
                .map(client::getPosts)
                .flatMap(Collection::stream)
                .toList();
    }

}
