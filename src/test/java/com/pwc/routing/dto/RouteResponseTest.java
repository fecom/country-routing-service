package com.pwc.routing.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RouteResponseTest {

    @Test
    void shouldCreateImmutableRouteResponse() {
        var route = new ArrayList<>(List.of("CZE", "AUT", "ITA"));
        var response = new RouteResponse(route);

        route.clear();

        assertThat(response.route()).containsExactly("CZE", "AUT", "ITA");
    }
}