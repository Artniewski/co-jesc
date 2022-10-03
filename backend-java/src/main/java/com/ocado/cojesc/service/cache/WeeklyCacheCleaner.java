package com.ocado.cojesc.service.cache;

import com.ocado.cojesc.restaurant.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import static com.ocado.cojesc.service.cache.LunchCacheManager.CACHE_NAME;

@Slf4j
@Component
public class WeeklyCacheCleaner {

    private final CacheManager cacheManager;
    private final DailyCacheCleaner dailyCacheCleaner;

    public WeeklyCacheCleaner(CacheManager cacheManager, DailyCacheCleaner dailyCacheCleaner) {
        this.cacheManager = cacheManager;
        this.dailyCacheCleaner = dailyCacheCleaner;
    }

    public void clearCache(Restaurant restaurant) {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        assert cache != null;
        if (restaurant.getMenuDuration() == 7) {
            cache.evictIfPresent(restaurant.getFacebookId());
            log.info("Evicted {} from cache.", restaurant.getFacebookId());
        } else {
            dailyCacheCleaner.clearCache(restaurant);
        }
    }
}
