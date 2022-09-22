package com.ocado.cojesc.service;

import com.ocado.cojesc.client.ScraperFeignClient;
import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.restaurant.RestaurantsProvider;
import com.ocado.cojesc.validator.FacebookPostValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacebookRestaurantsService {

    private final FacebookPostValidator facebookPostValidator;

    private final ScraperFeignClient scraperFeignClient;

    private final RestaurantsProvider restaurantsProvider;


    public List<FacebookPost> getAllPosts(List<Restaurant> restaurants) {
        List<FacebookPost> allPosts = new LinkedList<>();
        for (Restaurant restaurant : restaurants) {
            String restaurantId = restaurant.getFacebookId();
            List<String> facebookPostsAsString = scraperFeignClient.getPosts(restaurantId);
            facebookPostsAsString.stream()
                    .map(post -> FacebookPost.parse(restaurantId, post))
                    .filter(facebookPost -> facebookPostValidator.validate(facebookPost, restaurant))
                    .sorted(Comparator.reverseOrder())
                    .findFirst().ifPresent(allPosts::add);
        }
        return allPosts;
    }

}
