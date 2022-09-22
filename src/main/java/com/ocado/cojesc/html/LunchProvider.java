package com.ocado.cojesc.html;

import com.ocado.cojesc.config.Restaurant;
import com.ocado.cojesc.dto.MenuRecord;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class LunchProvider {
    private final FacebookClient facebookClient;
    private final KeywordLunchExtractor keywordLunchExtractor;

    public LunchProvider(FacebookClient facebookClient, KeywordLunchExtractor keywordLunchExtractor) {
        this.facebookClient = facebookClient;
        this.keywordLunchExtractor = keywordLunchExtractor;
    }

    public MenuRecord getLunch(Restaurant restaurant) {
        String content = keywordLunchExtractor.extract(
                        facebookClient.getPage(restaurant.id()),
                        restaurant.keywords())
                .stream()
                .max(Comparator.comparing(String::length))
                .orElse("No lunch found");

        return new MenuRecord(restaurant.id(), restaurant.name(), "tutaj bedzie data", content);
    }
}
