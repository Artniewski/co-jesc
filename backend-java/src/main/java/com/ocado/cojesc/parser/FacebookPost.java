package com.ocado.cojesc.temp;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public record FacebookPost(String facebookId, String restaurantName, String date, String content) {
    public static FacebookPost parse(String facebookId, String facebookPost) {
        String[] facebookPostLines = facebookPost.split("\n");
        try {
            String restaurantName = facebookPostLines[0];
            String date = facebookPostLines[1];
            String content = Arrays.stream(facebookPostLines).skip(2).collect(Collectors.joining("\n"));
            return new FacebookPost(facebookId, restaurantName, date, content);
        } catch (NullPointerException e) {
            throw new RuntimeException("Could not parse " + facebookPost + " as FacebookPost.");
        }
    }
}
