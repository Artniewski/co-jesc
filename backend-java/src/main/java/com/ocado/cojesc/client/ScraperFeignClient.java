package com.ocado.cojesc.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "scraper")
public interface ScraperFeignClient {

    @RequestMapping(value = "/{facebookId}/menu",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<String> getPosts(@PathVariable String facebookId);
}
