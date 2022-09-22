package com.ocado.cojesc.service;

import com.ocado.cojesc.client.ScraperFeignClient;
import com.ocado.cojesc.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheAwareFacebookRestaurantService {

    private static final String CACHE_NAME = "lunch-menu";

    private final ScraperFeignClient client;

    @Cacheable(cacheNames = { CACHE_NAME }, key = "#restaurant.name", unless = "#result == null or #result.size()==0")
    public List<String> getAllPosts(Restaurant restaurant) {
        log.info("Menu for {} not found in cache. Searching for new menu.", restaurant.getName());
        return client.getPosts(restaurant.getFacebookId());
    }

    @Scheduled(cron = "${cojesc.cache.eviction}")
    @CacheEvict(cacheNames = { CACHE_NAME }, allEntries = true)
    public void clearCache() {
        log.info("{} cache evicted", CACHE_NAME);
    }
}
