package com.ocado.cojesc.dto;

import com.ocado.cojesc.repository.SavedMenu;

import java.time.LocalDateTime;
import java.util.Objects;

public class MenuRecord {
    private String id;
    private String restaurantName;
    private String publicationTime;
    private String content;

    public MenuRecord(String id, String restaurantName, String publicationTime, String content) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.publicationTime = publicationTime;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(String publicationTime) {
        this.publicationTime = publicationTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuRecord menuRecord = (MenuRecord) o;
        return Objects.equals(id, menuRecord.id) && Objects.equals(restaurantName, menuRecord.restaurantName) && Objects.equals(publicationTime, menuRecord.publicationTime) && Objects.equals(content, menuRecord.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantName, publicationTime, content);
    }

    public SavedMenu toSavedMenu() {    //todo local date time to uproszczenie
        return new SavedMenu(this.id, this.restaurantName, this.publicationTime, LocalDateTime.now(), this.content);
    }
}
