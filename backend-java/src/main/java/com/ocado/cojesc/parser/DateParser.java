package com.ocado.cojesc.parser;

import java.time.LocalDate;
import java.time.LocalTime;
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
            return parseRelativeFormat(facebookDate);
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

    private static boolean isRelativeFormat(String facebookDate) {
        return !isDateFormat(facebookDate);
    }

    private static LocalDate parseRelativeFormat(String facebookDate) {
        String[] split = facebookDate.split(" ");
        if (split.length != 2) throw new IllegalArgumentException("Could not parse " + facebookDate + " as LocalDate.");

        int number = Integer.parseInt(split[0]);
        String unit = split[1];

        return switch (unit) {
            case "d" -> LocalDate.now().minusDays(number);
            case "h" -> LocalTime.now().getHour() > number ?
                    LocalDate.now() : LocalDate.now().minusDays(1);
            case "m" -> LocalTime.now().getHour() > 0 || LocalTime.now().getMinute() > number ?
                    LocalDate.now() : LocalDate.now().minusDays(1);
            case "s" ->
                    LocalTime.now().getHour() > 0 || LocalTime.now().getMinute() > 0 || LocalTime.now().getSecond() > number ?
                            LocalDate.now() : LocalDate.now().minusDays(1);
            default -> throw new IllegalArgumentException("Could not parse " + facebookDate + " as LocalDate.");
        };
    }
}
