package com.ocado.cojesc.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class DateValidator {

    public boolean checkDate(int menuDuration, LocalDate date) {
        if (menuDuration == 7) {
            return LocalDate.ofInstant(Instant.now().truncatedTo(ChronoUnit.WEEKS), ZoneId.systemDefault()).isAfter(LocalDate.now());
        } else if (menuDuration == 1) {
            return LocalDate.ofInstant(Instant.now().truncatedTo(ChronoUnit.DAYS), ZoneId.systemDefault()).isAfter(LocalDate.now());
        }
        return date.plusDays(menuDuration).isAfter(LocalDate.now());
    }
}
