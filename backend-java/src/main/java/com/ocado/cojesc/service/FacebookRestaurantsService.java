package com.ocado.cojesc.service;

import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookRestaurantsService {

    private final CacheAwareFacebookRestaurantService restaurantService;

    @Deprecated
    public List<FacebookPost> getAllPosts(List<Restaurant> restaurants) throws ExecutionException, InterruptedException {
        log.info("Searching for all lunch menus...");
        List<Future<Optional<FacebookPost>>> futures = new LinkedList<>();
        for (Restaurant restaurant : restaurants) {
            log.info("Searching for {} restaurant lunch menu.", restaurant.getName());
            futures.add(restaurantService.findNewestMenuPost(restaurant));
        }

        List<FacebookPost> allPosts = new LinkedList<>();
        for (Future<Optional<FacebookPost>> f : futures) {
            var facebookPost = f.get();
            facebookPost.ifPresent(
                    (p) -> {
                        allPosts.add(p);
                        log.info("Found Lunch menu for {} restaurant.", p.restaurantName());
                    });
        }

        log.info("Found lunch menus for restaurants: {}", allPosts.stream().map(FacebookPost::restaurantName).toList());
        return allPosts;
    }

    public Optional<FacebookPost> findNewestLunchPost(Restaurant restaurant) {
        log.info("Searching for {} restaurant lunch menu.", restaurant.getName());
        Optional<FacebookPost> menu = restaurantService.findNewestMenuPost2(restaurant);
        menu.ifPresentOrElse(
                (p) -> log.info("Lunch menu for {} restaurant found.", restaurant.getName()),
                () -> log.info("Lunch menu for {} restaurant not found.", restaurant.getName())
        );
        return menu;
    }
}
