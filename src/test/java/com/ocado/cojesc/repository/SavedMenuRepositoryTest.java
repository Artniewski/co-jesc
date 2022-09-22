package com.ocado.cojesc.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
class SavedMenuRepositoryTest {

    @Autowired
    private SavedMenuRepository repository;

    @Test
    void shouldReadTestDB() {
        Optional<SavedMenu> testRestaurant = repository.findById("test-restaurant-id");

        Assertions.assertThat(repository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(testRestaurant).hasValueSatisfying(
                it -> {
                    Assertions.assertThat(it.getContent()).isEqualTo("This is test menu");
                    Assertions.assertThat(it.getRestaurantName()).isEqualTo("Test Restaurant");
                    Assertions.assertThat(it.getTimePublished()).isEqualTo("6h ago");
                });
    }
}