package com.ocado.cojesc.service;

import com.ocado.cojesc.client.ScrapedPost;
import com.ocado.cojesc.client.ScraperFeignClient;
import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.restaurant.RestaurantsProvider;
import com.ocado.cojesc.validator.FacebookPostValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class CacheAwareFacebookRestaurantService {

    private static final String CACHE_NAME = "lunch-menu";

    private final ScraperFeignClient fbClient;
    private final FacebookPostValidator facebookPostValidator;
    private final ExecutorService executorService;

    public CacheAwareFacebookRestaurantService(ScraperFeignClient scraperFeignClient, FacebookPostValidator facebookPostValidator, RestaurantsProvider restaurantsProvider) {
        this.fbClient = scraperFeignClient;
        this.facebookPostValidator = facebookPostValidator;
        this.executorService = Executors.newFixedThreadPool(restaurantsProvider.getRestaurants().size());
    }

    @Cacheable(cacheNames = {CACHE_NAME}, key = "#restaurant.name", unless = "#result.get == null")
    public Future<Optional<FacebookPost>> findNewestMenuPost(Restaurant restaurant) {
        return executorService.submit(() -> {
            log.info("Menu for {} restaurant not found in cache. Scraping from FB.", restaurant.getName());

            //TODO: replace mock with real call
//            List<ScrapedPost> posts = fbClient.getScrapedPosts(restaurant.getFacebookId());
            List<ScrapedPost> posts = fbClient.getScrapedMockPosts(restaurant.getFacebookId());

            return posts.stream()
                    .map(post -> FacebookPost.parse(restaurant.getFacebookId(), restaurant.getName(), post))
                    .filter(facebookPost -> facebookPostValidator.validate(facebookPost, restaurant))
                    .max(Comparator.naturalOrder());
        });
    }

    @Scheduled(cron = "${cojesc.cache.eviction}")
    @CacheEvict(cacheNames = {CACHE_NAME}, allEntries = true)
    public void clearCache() {
        log.info("{} cache evicted", CACHE_NAME);
    }

}
