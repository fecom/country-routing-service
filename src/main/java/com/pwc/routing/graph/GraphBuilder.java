package com.pwc.routing.graph;

import com.pwc.routing.model.Country;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
public class GraphBuilder {

    public CountryGraph build(List<Country> countries) {
        var graph = new HashMap<String, HashSet<String>>();
        for (Country country : countries) {
            graph.put(
                country.cca3(),
                new HashSet<>(country.borders())
            );
        }
        return new CountryGraph(Map.copyOf(graph));
    }
}