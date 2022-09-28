package com.ocado.cojesc.demo;

import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;

import java.util.Optional;

public interface FacebookRestaurantService {
    Optional<FacebookPost> findNewestMenuPost(Restaurant restaurant);
}
