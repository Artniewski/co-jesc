package com.ocado.cojesc.service.cache;

import com.ocado.cojesc.restaurant.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static com.ocado.cojesc.service.cache.LunchCacheManager.LUNCH_MENU_CACHE;

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
        Cache cache = cacheManager.getCache(LUNCH_MENU_CACHE);
        assert cache != null;
        if (restaurant.getMenuDuration() == 7 && LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY) {
            cache.evictIfPresent(restaurant.getFacebookId());
            log.info("Evicted {} from cache.", restaurant.getFacebookId());
        } else {
            dailyCacheCleaner.clearCache(restaurant);
        }
    }
}
