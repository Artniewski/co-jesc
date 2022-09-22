package com.ocado.cojesc.validator;

import com.ocado.cojesc.parser.FacebookPost;
import com.ocado.cojesc.restaurant.Restaurant;

public class FacebookPostValidator {
    //todo sprawdz kiedy wywala sie na parsowaniu day i niech to bedzie obsluzone
    //todo a co jesli jest wiecj postow niz jeden
    private DateValidator dateValidator;

    private KeyWordsValidator keyWordsValidator;

    public boolean validate(FacebookPost facebookPost, Restaurant restaurant) {
        return dateValidator.checkDate(restaurant.getMenuDuration(), facebookPost.date()) && keyWordsValidator.validateKeyWords(restaurant, facebookPost.content());
    }
}
