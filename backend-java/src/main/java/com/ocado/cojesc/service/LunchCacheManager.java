package com.ocado.cojesc.service;

import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.restaurant.RestaurantsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LunchCacheManager {

    public static final String CACHE_NAME = "lunch-menu";

    private final CacheManager cacheManager;
    private final RestaurantsProvider restaurantsProvider;

    public LunchCacheManager(CacheManager cacheManager, RestaurantsProvider restaurantsProvider) {
        this.cacheManager = cacheManager;
        this.restaurantsProvider = restaurantsProvider;
    }

    @Scheduled(cron = "${cojesc.cache.eviction.nulls}")
    public void clearNullValuesFromCache() {
        restaurantsProvider.getRestaurants().stream()
                .map(Restaurant::getFacebookId)
                .forEach(fbid -> {
                    Cache.ValueWrapper wrapper = cacheManager.getCache(CACHE_NAME).get(fbid);
                    if (wrapper != null && wrapper.get() == null) {
                        cacheManager.getCache(CACHE_NAME).evict(fbid);
                        log.info("{} evicted from {} cache. It was temporarily cached with null value.", fbid, CACHE_NAME);
                    }
                });
    }

    @Scheduled(cron = "${cojesc.cache.eviction.menus}")
    @CacheEvict(cacheNames = {LunchCacheManager.CACHE_NAME}, allEntries = true)
    public void clearCache() {
        log.info("{} cache evicted", CACHE_NAME);
    }
}
