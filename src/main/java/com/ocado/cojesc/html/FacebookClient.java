package com.ocado.cojesc.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class FacebookClient {
    public String getPage(String restaurantId) {
        String fbUrl = "https://facebook.com/" + restaurantId;
        try {
            Document inputHtml = Jsoup.connect(fbUrl)
                    .data("query", "Java")
                    .userAgent("Chrome/55.0.2883.91")
                    .get();
            return parseUnicode(inputHtml.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String parseUnicode(String unescaped) {
        Pattern UNICODE_PATTERN = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
        return UNICODE_PATTERN.matcher(unescaped).replaceAll(r -> String.valueOf((char) Integer.parseInt(r.group(1), 16)));
    }
}
