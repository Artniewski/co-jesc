package com.ocado.cojesc.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "SAVED_MENUS", schema = "COJESC")
@AllArgsConstructor
@NoArgsConstructor
public class SavedMenu {

    @Id
    @EqualsAndHashCode.Include
    private String restaurantId;
    private String restaurantName;
    private String timePublished;
    private LocalDateTime timeDownloaded;
    private String content;
}
