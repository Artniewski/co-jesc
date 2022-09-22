package com.ocado.cojesc.factory;

import com.ocado.cojesc.dto.LunchesDto;
import com.ocado.cojesc.dto.MenuRecord;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LunchesDtoFactory {
    public LunchesDto from(Map<String, MenuRecord> lunches) {
        return new LunchesDto(lunches);
    }
}
