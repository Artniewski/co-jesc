package com.ocado.cojesc.demo;

import com.ocado.cojesc.client.ScrapedPost;
import com.ocado.cojesc.client.ScraperFeignClient;
import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.validator.FacebookPostValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@ConditionalOnProperty(name = "cojesc.demo.enabled", havingValue = "true")
public class DemoFacebookRestaurantService implements FacebookRestaurantService {

    private final ScraperFeignClient fbClient;
    private final FacebookPostValidator facebookPostValidator;

    public DemoFacebookRestaurantService(ScraperFeignClient scraperFeignClient, FacebookPostValidator facebookPostValidator) {
        this.fbClient = scraperFeignClient;
        this.facebookPostValidator = facebookPostValidator;
    }

    public Optional<FacebookPost> findNewestMenuPost(Restaurant restaurant) {
        log.info("Menu for {} restaurant not found in cache. Loading mock.", restaurant.getName());
        List<ScrapedPost> posts = fbClient.getScrapedMockPosts(restaurant.getFacebookId());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return posts.stream()
                .map(post -> FacebookPost.parse(restaurant.getFacebookId(), restaurant.getName(), post))
                .filter(facebookPost -> facebookPostValidator.validate(facebookPost, restaurant))
                .max(Comparator.naturalOrder());
    }
}
