package com.ocado.cojesc.html;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeywordLunchExtractor {
    public List<String> extract(String html, List<String> keywords){
        return keywords.stream()
                .map(keyword -> extractByKeyword(html, keyword))
                .toList();
    }

    private String extractByKeyword(String html, String keyword) {
        int index = html.indexOf(keyword);
        StringBuilder result = new StringBuilder();
        while (html.charAt(index) != '"') {
            result.append(html.charAt(index));
            index++;
        }
        return result.toString();
    }
}
