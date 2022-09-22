package com.ocado.cojesc.repository.service;

import com.ocado.cojesc.dto.MenuRecord;
import com.ocado.cojesc.repository.SavedMenu;
import com.ocado.cojesc.repository.SavedMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuSavingService {

    @Autowired
    private SavedMenuRepository repository;

    public void saveToRepository(MenuRecord menu) {
        if (repository.findById(menu.getId()).isEmpty()) {
            repository.save(menu.toSavedMenu());
        } else if (isContentEqual(menu)) {
            repository.save(menu.toSavedMenu());
        }
    }

    private boolean isContentEqual(MenuRecord menu) {
        SavedMenu savedMenu = repository.findById(menu.getId()).get();
        return !savedMenu.getContent().equals(menu.getContent());
    }
}
