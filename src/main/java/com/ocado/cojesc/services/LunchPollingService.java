package com.ocado.cojesc.services;

import com.ocado.cojesc.config.Restaurant;
import com.ocado.cojesc.config.RestaurantsConfig;
import com.ocado.cojesc.dto.MenuRecord;
import com.ocado.cojesc.html.LunchProvider;
import com.ocado.cojesc.repository.SavedMenu;
import com.ocado.cojesc.repository.SavedMenuRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
public class LunchPollingService {

    private final SavedMenuRepository menuRepository;
    private final RestaurantsConfig restaurantsConfig;
    private final LunchProvider lunchProvider;

    public LunchPollingService(SavedMenuRepository menuRepository, RestaurantsConfig restaurantsConfig, LunchProvider lunchProvider) {
        this.menuRepository = menuRepository;
        this.restaurantsConfig = restaurantsConfig;
        this.lunchProvider = lunchProvider;
    }

    @Scheduled(cron = "0 0/15 * * * *")
    public void checkForMenus() {
        for (Restaurant restaurant : restaurantsConfig.getRestaurants()) {
            boolean upToDate = menuRepository.findById(restaurant.id())
                    .map(SavedMenu::getTimeDownloaded)
                    .filter(LunchPollingService::isUpToDate)
                    .isPresent();

            if (!upToDate) {
                MenuRecord menuRecord = lunchProvider.getLunch(restaurant);
                menuRepository.save(menuRecord.toSavedMenu());
                //scratch new menu here and save to DB
            }
        }
    }

    private static boolean isUpToDate(LocalDateTime time) {
        return time.isAfter(LocalDateTime.ofInstant(Instant.now().truncatedTo(ChronoUnit.WEEKS), ZoneId.systemDefault()));
    }
}
