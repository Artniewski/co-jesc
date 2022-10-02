package com.ocado.cojesc.parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class DateParser {
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

    private static Pattern RELATIVE_FB_DATE_REGEX = Pattern.compile("[0-9]{0,2}([hdms])");

    private DateParser() {
    }

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

    private static LocalDate parseDateFormat(String facebookData) { // 12 September at 11:23, 12 June, 12 June 2021
        String[] split = facebookData.split(" ");
        int dayPosition = !isDaySwitchedWithMonth(split) ? 0 : 1;
        int monthPosition = dayPosition ^ 1;
        int day = Integer.parseInt(split[dayPosition]);
        int month = Arrays.asList(MONTHS).indexOf(split[monthPosition]) + 1;
        int year;
        if (split.length > 2 && split[2].length() == 4) year = Integer.parseInt(split[2]);
        else year = LocalDate.now().getYear();

        return LocalDate.of(year, month, day);
    }

    private static boolean isDaySwitchedWithMonth (String[] facebookData) {
        List<String> months = Arrays.stream(MONTHS).map(String::toLowerCase).toList();
        return (months.contains(facebookData[0].toLowerCase()));
    }

    private static boolean isRelativeFormat(String facebookDate) {
        return !isDateFormat(facebookDate);
    }

    private static LocalDate parseRelativeFormat(String facebookDate) {
        String[] split = facebookDate.split(" ");
        if (split.length != 2) {
            split = trySplitRelativeDateFormat(facebookDate);
        }

        int number = Integer.parseInt(split[0]);
        String unit = split[1];

        return switch (unit) {
            case "d" -> LocalDate.now().minusDays(number + 1);
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

    private static String[] trySplitRelativeDateFormat(String facebookDate) {
        String[] split = facebookDate.split(" ");
        if (split.length == 2) {
            return split;
        }

        if (RELATIVE_FB_DATE_REGEX.matcher(facebookDate).matches()) {
            String time = facebookDate.substring(0, facebookDate.length() - 1);
            String unit = facebookDate.substring(facebookDate.length() - 1);
            return new String[] {time, unit};
        }

        throw new IllegalArgumentException("Could not parse " + facebookDate + " as LocalDate.");
    }
}
