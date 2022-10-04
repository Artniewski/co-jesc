package com.ocado.cojesc.service;

import com.ocado.cojesc.client.ScrapedPost;
import com.ocado.cojesc.client.ScraperFeignClient;
import com.ocado.cojesc.demo.FacebookRestaurantService;
import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.service.cache.LunchCacheManager;
import com.ocado.cojesc.validator.FacebookPostValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@ConditionalOnProperty(name = "cojesc.demo.enabled", havingValue = "false")
public class CacheAwareFacebookRestaurantService implements FacebookRestaurantService {

    private final ScraperFeignClient fbClient;
    private final FacebookPostValidator facebookPostValidator;

    public CacheAwareFacebookRestaurantService(ScraperFeignClient scraperFeignClient, FacebookPostValidator facebookPostValidator) {
        this.fbClient = scraperFeignClient;
        this.facebookPostValidator = facebookPostValidator;
    }

    @Cacheable(cacheNames = {LunchCacheManager.LUNCH_MENU_CACHE}, key = "#restaurant.facebookId")
    public Optional<FacebookPost> findNewestMenuPost(Restaurant restaurant) {
        log.info("Menu for {} restaurant not found in cache. Scraping from FB.", restaurant.getName());
        List<ScrapedPost> posts = fbClient.getScrapedPosts(restaurant.getFacebookId());

        return posts.stream()
                .map(post -> FacebookPost.parse(restaurant.getFacebookId(), restaurant.getName(), post))
                .filter(facebookPost -> facebookPostValidator.validate(facebookPost, restaurant))
                .max(Comparator.naturalOrder());
    }
}
