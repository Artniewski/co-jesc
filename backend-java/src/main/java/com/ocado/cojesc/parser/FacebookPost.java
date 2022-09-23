package com.ocado.cojesc.parser;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public record FacebookPost(String facebookId, String restaurantName, LocalDate date,
                           String content) implements Comparable<FacebookPost> {
    public static FacebookPost parse(String facebookId, String restaurantName, String facebookPost) {
        String[] facebookPostLines = facebookPost.split("\n");
        try {
            LocalDate date = DateParser.parse(facebookPostLines[1]);
            String content = Arrays.stream(facebookPostLines).skip(2).collect(Collectors.joining("\n"));
            return new FacebookPost(facebookId, restaurantName, date, content);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Could not parse " + facebookPost + " as FacebookPost.");
        }
    }


    @Override
    public int compareTo(FacebookPost o) {
        return this.date.compareTo(o.date);
    }
}
