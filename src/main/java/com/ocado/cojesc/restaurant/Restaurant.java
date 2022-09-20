package com.ocado.cojesc.restaurant;

public record Restaurant(
        String name,
        String facebookId,
        Menu menu,
        Characteristics characteristics
) {

}
