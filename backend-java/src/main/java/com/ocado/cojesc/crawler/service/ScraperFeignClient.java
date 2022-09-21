package com.ocado.cojesc.crawler.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "scraper")
public class ScraperFeignClient {

    @RequestMapping(value = "/mock/menu",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getPosts(@RequestBody RestaurantDto restaurantDto) {
        return null;
    }
}
