package com.ocado.cojesc.controller;

import com.ocado.cojesc.factory.MenuDtoFactory;
import com.ocado.cojesc.services.LunchesProvider;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    private final LunchesProvider lunchesProvider;

    private final MenuDtoFactory menuDtoFactory;

    public MenuController(LunchesProvider lunchesProvider, MenuDtoFactory menuDtoFactory) {
        this.lunchesProvider = lunchesProvider;
        this.menuDtoFactory = menuDtoFactory;
    }

    //todo make this return result from Db not jsoup
//    @GetMapping("/menu")
//    public MenuDto getMenus() {
//        return menuDtoFactory.from(lunchesProvider.getLunches());
//    }
}
