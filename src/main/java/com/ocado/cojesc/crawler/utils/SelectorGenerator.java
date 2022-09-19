package com.ocado.cojesc.crawler.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SelectorGenerator {

    public static String generateSelectorFromClasses(String classes) {
        if (classes.isBlank()) return classes;

        return ".".concat(classes.replace(' ', '.'));
    }
}
