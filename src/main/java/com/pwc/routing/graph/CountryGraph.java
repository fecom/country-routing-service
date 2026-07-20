package com.pwc.routing.graph;

import java.util.Map;
import java.util.Set;

public final class CountryGraph {

    private final Map<String, Set<String>> neighbours;

    public CountryGraph(Map<String, Set<String>> neighbours) {
        this.neighbours = neighbours;
    }

    public Set<String> neighboursOf(String country) {
        return neighbours.getOrDefault(country, Set.of());
    }

    public boolean contains(String country) {
        return neighbours.containsKey(country);
    }
}