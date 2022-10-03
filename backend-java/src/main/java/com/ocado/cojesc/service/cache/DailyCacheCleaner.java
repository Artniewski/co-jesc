package com.ocado.cojesc.service.cache;

import com.ocado.cojesc.restaurant.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DailyCacheCleaner {

    private final CacheManager cacheManager;

    public DailyCacheCleaner(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void clearCache(Restaurant restaurant) {
        Cache cache = cacheManager.getCache(LunchCacheManager.CACHE_NAME);
        assert cache != null;

        if (restaurant.getMenuDuration() == 1) {
            cache.evictIfPresent(restaurant.getFacebookId());
            log.info("Evicted {} from cache.", restaurant.getFacebookId());
        }
    }
}
