package com.ocado.cojesc.crawler.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ocado.cojesc.restaurant.Restaurant;
import com.ocado.cojesc.crawler.FacebookPost;

@Component
public class LunchPostValidator {

    public List<FacebookPost> getLunchPosts(Restaurant restaurant, List<FacebookPost> facebookPosts) {
        List<String> menuKeyWords = restaurant.getMenuKeyWords();
        return facebookPosts.stream()
                .filter(post -> menuKeyWords.stream().anyMatch(post::containsKeyWord))
                .toList();
    }

}
