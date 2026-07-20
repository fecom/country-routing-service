package com.pwc.routing.graph;

import com.pwc.routing.service.RouteFinder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RouteFinderTest {

    @Test
    void shouldFindRouteBetweenCountries() {
        CountryGraph graph = new CountryGraph(Map.of("CZE", Set.of("AUT"),
                "AUT", Set.of("CZE", "ITA"),
                "ITA", Set.of("AUT")));

        RouteFinder finder = new RouteFinder(graph);

        var result = finder.findRoute("CZE", "ITA");

        assertThat(result).isPresent();
        assertThat(result.get()).containsExactly("CZE", "AUT", "ITA");
    }

    @Test
    void shouldReturnEmptyWhenNoRouteExists() {
        CountryGraph graph = new CountryGraph(Map.of("CZE", Set.of(), "ITA", Set.of()));

        RouteFinder finder = new RouteFinder(graph);

        assertThat(finder.findRoute("CZE", "ITA")).isEmpty();
    }

    @Test
    void shouldReturnSameCountryRoute() {
        CountryGraph graph = new CountryGraph(Map.of("CZE", Set.of()));

        RouteFinder finder = new RouteFinder(graph);
        Optional<List<String>> route = finder.findRoute("CZE", "CZE");

        assertThat(route).isPresent();
        assertThat(route.get()).containsExactly("CZE");
    }

    @Test
    void shouldReturnOptionalEmptyWhenGraphNotContainsFromCountry() {
        CountryGraph graph = new CountryGraph(Map.of("ITA", Set.of()));

        RouteFinder finder = new RouteFinder(graph);
        Optional<List<String>> route = finder.findRoute("CZE", "ITA");

        assertThat(route).isEmpty();
    }

    @Test
    void shouldReturnOptionalEmptyWhenGraphNotContainsToCountry() {
        CountryGraph graph = new CountryGraph(Map.of("CZE", Set.of()));

        RouteFinder finder = new RouteFinder(graph);
        Optional<List<String>> route = finder.findRoute("CZE", "ITA");

        assertThat(route).isEmpty();
    }

}