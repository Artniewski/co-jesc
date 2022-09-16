package com.ocado.cojesc.services;


import com.ocado.cojesc.config.Restaurant;
import com.ocado.cojesc.dto.LunchesDto;
import com.ocado.cojesc.factory.LunchesDtoFactory;
import com.ocado.cojesc.html.LunchProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LunchesService implements LunchesProvider {
    private final List<Restaurant> restaurants;
    private final LunchProvider lunchProvider;
    private final LunchesDtoFactory lunchesDtoFactory;

    public LunchesService(List<Restaurant> restaurants, LunchProvider lunchProvider, LunchesDtoFactory lunchesDtoFactory) {
        this.restaurants = restaurants;
        this.lunchProvider = lunchProvider;
        this.lunchesDtoFactory = lunchesDtoFactory;
    }

    @Override
    public LunchesDto getLunches() {
        return lunchesDtoFactory.from(restaurants.stream()
                .collect(Collectors.toMap(Restaurant::name, lunchProvider::getLunch)));
    }
}
