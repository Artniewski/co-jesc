package com.ocado.cojesc.validator;

import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacebookPostValidator {
    private final DateValidator dateValidator;
    private final KeyWordsValidator keyWordsValidator;

    public boolean validate(FacebookPost facebookPost, Restaurant restaurant) {
        return dateValidator.checkDate(restaurant.getMenuDuration(), facebookPost.date()) && keyWordsValidator.validateKeyWords(restaurant, facebookPost.innerText());
    }
}
