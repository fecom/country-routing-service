package com.pwc.routing.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CountryTest {

    @Test
    void shouldCreateImmutableCountry() {
        //Given ~ Arrange ~ Set up the test environment
        var borders = new ArrayList<>(List.of("AUT", "ITA"));
        var country = new Country("CZE", borders);

        //When ~ Act ~ Execute the code to test
        borders.add("DEU");

        //Then ~ Assert ~ Verify the results - expected behavior
        assertThat(country.borders()).containsExactly("AUT", "ITA");
    }
}