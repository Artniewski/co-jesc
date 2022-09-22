package com.ocado.cojesc.dto;

import java.util.Map;

public record MenuDto(Map<String, MenuRecord> restaurantNameToLunch) {
}
