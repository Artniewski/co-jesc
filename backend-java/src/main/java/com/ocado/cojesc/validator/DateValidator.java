package com.ocado.cojesc.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class DateValidator {

    public boolean checkDate(int menuDuration, LocalDate date) {
        return date.plusDays(menuDuration).isAfter(LocalDate.now());
    }
}
