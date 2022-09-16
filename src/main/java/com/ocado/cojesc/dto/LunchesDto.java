package com.ocado.cojesc.dto;

import java.util.Map;

public record LunchesDto(Map<String,String> restaurantNameToLunch) {
}
