package com.ocado.cojesc.services;


import com.ocado.cojesc.config.Restaurant;
import com.ocado.cojesc.factory.LunchesDtoFactory;
import com.ocado.cojesc.html.LunchProvider;
import com.ocado.cojesc.repository.service.MenuSavingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LunchesService implements LunchesProvider {
    private final List<Restaurant> restaurants;
    private final LunchProvider lunchProvider;
    private final LunchesDtoFactory lunchesDtoFactory;
    private final MenuSavingService menuSavingService;

    public LunchesService(List<Restaurant> restaurants, LunchProvider lunchProvider, LunchesDtoFactory lunchesDtoFactory, MenuSavingService menuSavingService) {
        this.restaurants = restaurants;
        this.lunchProvider = lunchProvider;
        this.lunchesDtoFactory = lunchesDtoFactory;
        this.menuSavingService = menuSavingService;
    }

    @Override
    public void saveLunchesToDataBase() {
        restaurants.stream()
                .map(lunchProvider::getLunch)
                .peek(menuSavingService::saveToRepository);
    }
}
