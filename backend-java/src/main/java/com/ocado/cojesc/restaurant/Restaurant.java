package com.ocado.cojesc.restaurant;

import lombok.Data;

import java.util.List;

@Data
public class Restaurant {
    private String name;
    private String facebookId;
    private List<String> menuKeyWords;
    private int menuDuration;
}
