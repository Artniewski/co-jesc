package com.ocado.cojesc.parser;

import com.ocado.cojesc.client.ScrapedPost;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public record FacebookPost(String facebookId, String restaurantName, LocalDate date,
                           String innerText, String innerHtml) implements Comparable<FacebookPost> {
    public static FacebookPost parse(String facebookId, String restaurantName, ScrapedPost post) {
        String[] facebookPostLines = post.getInnerText().split("\n");
        try {
            LocalDate date = DateParser.parse(facebookPostLines[1]);
            String innerText = Arrays.stream(facebookPostLines).skip(2).collect(Collectors.joining("\n"));
            return new FacebookPost(facebookId, restaurantName, date, innerText, post.getInnerHtml());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Could not parse " + post + " as FacebookPost.");
        }
    }

    @Override
    public int compareTo(FacebookPost o) {
        return this.date.compareTo(o.date);
    }
}
