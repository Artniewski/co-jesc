package com.ocado.cojesc.restaurant;

import java.util.List;

import lombok.Data;

@Data
public class Restaurant {
    private String name;
    private String facebookId;
    private String facebookPostClasses;
    private List<String> menuKeyWords;
    private int menuDuration;
}
