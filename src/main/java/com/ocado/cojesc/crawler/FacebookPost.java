package com.ocado.cojesc.crawler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FacebookPost {

    private String facebookId;
    private String innerText;
    private String innerHTML;

    public boolean containsKeyWord(String keyWord) {
        return innerText.toLowerCase().contains(keyWord.toLowerCase());
    }

}
