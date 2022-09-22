package com.ocado.cojesc.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DateValidator {

    public boolean checkDate(int menuDuration, LocalDate date) {
        return date.plusDays(menuDuration).isBefore(LocalDate.now());
    }


}
