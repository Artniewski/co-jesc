package com.ocado.cojesc.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DateValidator {

    public boolean checkDate(int menuDuration, LocalDate date) {
        if (menuDuration == 7) {
            return date.isAfter(LocalDate.now().with(DayOfWeek.MONDAY)) || date.isEqual(LocalDate.now().with(DayOfWeek.MONDAY));
        } else if (menuDuration == 1) {
            return date.isEqual(LocalDate.now());
        }
        return date.plusDays(menuDuration).isAfter(LocalDate.now());
    }
}
