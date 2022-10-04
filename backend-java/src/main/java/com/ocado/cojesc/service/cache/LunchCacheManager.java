package com.ocado.cojesc.service.cache;

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

    public static final String LUNCH_MENU_CACHE = "lunch-menu";

    private final CacheManager cacheManager;
    private final RestaurantsProvider restaurantsProvider;
    private final WeeklyCacheCleaner cacheCleaner;

    public LunchCacheManager(CacheManager cacheManager, RestaurantsProvider restaurantsProvider, WeeklyCacheCleaner cacheCleaner) {
        this.cacheManager = cacheManager;
        this.restaurantsProvider = restaurantsProvider;
        this.cacheCleaner = cacheCleaner;
    }

    @Scheduled(cron = "${cojesc.cache.eviction.nulls}")
    public void clearNullValuesFromCache() {
        Cache cache = cacheManager.getCache(LUNCH_MENU_CACHE);
        assert cache != null;
        restaurantsProvider.getRestaurants().stream()
                .map(Restaurant::getFacebookId)
                .forEach(fbid -> {
                    Cache.ValueWrapper wrapper = cache.get(fbid);
                    if (wrapper != null && wrapper.get() == null) {
                        cache.evictIfPresent(fbid);
                        log.info("{} evicted from {} cache. It was temporarily cached with null value.", fbid, LUNCH_MENU_CACHE);
                    }
                });
    }

    @Scheduled(cron = "${cojesc.cache.eviction.menus}")
    @CacheEvict(cacheNames = {LunchCacheManager.LUNCH_MENU_CACHE}, allEntries = true)
    public void clearCache() {
        restaurantsProvider.getRestaurants()
                .forEach(cacheCleaner::clearCache);
    }
}
