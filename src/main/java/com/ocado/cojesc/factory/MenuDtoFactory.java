package com.ocado.cojesc.factory;

import com.ocado.cojesc.dto.LunchesDto;
import com.ocado.cojesc.dto.MenuDto;
import org.springframework.stereotype.Component;

@Component
public class MenuDtoFactory {
    public MenuDto from(LunchesDto lunches) {
        return new MenuDto(lunches.restaurantNameToLunch());
    }
}
