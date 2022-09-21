package com.ocado.cojesc.temp;

import java.time.LocalDate;
import java.util.Arrays;

public final class DateParser {
    private DateParser() {
    }

    private static final String[] MONTHS = {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    public static LocalDate parse(String facebookDate) {
        if (isDateFormat(facebookDate)) {
            return parseDateFormat(facebookDate);
        } else {
            return parseHumanFormat(facebookDate);
        }
    }

    private static boolean isDateFormat(String facebookDate) {
        return Arrays.stream(MONTHS).anyMatch(facebookDate::contains);
    }

    private static LocalDate parseDateFormat(String facebookDate) { // 12 September at 11:23, 12 June, 12 June 2021
        String[] split = facebookDate.split(" ");
        int day = Integer.parseInt(split[0]);
        int month = Arrays.asList(MONTHS).indexOf(split[1]) + 1;
        int year;
        if (split.length > 2 && split[2].length() == 4) year = Integer.parseInt(split[2]);
        else year = LocalDate.now().getYear();

        return LocalDate.of(year, month, day);
    }

    private static boolean isHumanFormat(String facebookDate) {
        return !isDateFormat(facebookDate);
    }

    private static LocalDate parseHumanFormat(String facebookDate) {
        // nothing here yet
        // TODO: implement
        return LocalDate.of(2010, 10, 10);
    }
}
