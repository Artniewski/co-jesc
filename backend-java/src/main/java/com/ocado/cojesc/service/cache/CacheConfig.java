package com.ocado.cojesc.service.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CacheConfig {

    public static final String LUNCH_MENU_CACHE = "lunch-menu";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCache lunchMenuCache = buildLunchMenuCache();
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(List.of(lunchMenuCache));
        return manager;
    }

    private CaffeineCache buildLunchMenuCache() {
        return new CaffeineCache(LUNCH_MENU_CACHE, Caffeine.newBuilder().build());
    }
}
