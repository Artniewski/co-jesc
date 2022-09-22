package com.ocado.cojesc.validator;

import com.ocado.cojesc.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyWordsValidator {

    private double requiredPercentOfKeyWordsInPost = 60;

    public boolean validateKeyWords(Restaurant restaurant, String content) {
        int numberOfWordsInMenu = countNumberOfKeyWordsInPost(restaurant, content);
        if (numberOfWordsInMenu != 0) {
            return percentOfKeyWordInPost(numberOfWordsInMenu, restaurant.getMenuKeyWords().size()) >= requiredPercentOfKeyWordsInPost;
        }
        return false;
    }

    private int countNumberOfKeyWordsInPost(Restaurant restaurant, String content) {
        int numberOfKeyWordsInPost = 0;
        for (String keyWord : restaurant.getMenuKeyWords()) {
            if (content.toLowerCase().contains(keyWord.toLowerCase())) {
                numberOfKeyWordsInPost++;
            }
        }
        return numberOfKeyWordsInPost;
    }

    private double percentOfKeyWordInPost(int numberOfWords, int maxNumberOfWords) {
        double doubleNumberOfWords = numberOfWords;
        double doubleMaxNumberOfWords = maxNumberOfWords;
        return doubleNumberOfWords / doubleMaxNumberOfWords * 100.0;
    }

}
