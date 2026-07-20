package com.pwc.routing.model;

import java.util.List;

public record Country(String cca3, List<String> borders) {
    // Copy the borders list to avoid modifying the original list
    public Country {
        borders = borders == null
            ? List.of()
            : List.copyOf(borders);
    }
}
