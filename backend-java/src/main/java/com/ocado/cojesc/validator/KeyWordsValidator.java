package com.ocado.cojesc.validator;

import com.ocado.cojesc.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyWordsValidator {

    private double requiredPercentOfKeyWordsInPost = 60;

    public boolean validateKeyWords(Restaurant restaurant, String content) {
        int numberOfWordsInMenu = countNumberOfKeyWordsInPost(restaurant);
        if (numberOfWordsInMenu != 0) {
            return percentOfKeyWordInPost(numberOfWordsInMenu, restaurant.getMenuKeyWords().size()) >= requiredPercentOfKeyWordsInPost;
        }
        return false;
    }

    private int countNumberOfKeyWordsInPost(Restaurant restaurant) {
        int numberOfKeyWordsInPost = 0;
        for (String keyWord : restaurant.getMenuKeyWords()) {
            if (restaurant.getFacebookPostClasses().toLowerCase().contains(keyWord)) {
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
