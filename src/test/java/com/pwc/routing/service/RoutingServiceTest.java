package com.pwc.routing.service;

import com.pwc.routing.exception.RouteNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoutingServiceTest {

    @Test
    void shouldReturnRoute() {
        RouteFinder finder = mock(RouteFinder.class);
        when(finder.findRoute("CZE", "ITA")).thenReturn(Optional.of(List.of("CZE", "AUT", "ITA")));
        RoutingService service = new RoutingService(finder);

        var response = service.findRoute("CZE", "ITA");

        assertThat(response.route()).containsExactly("CZE", "AUT", "ITA");
        verify(finder).findRoute("CZE", "ITA");
    }


    @Test
    void shouldThrowWhenRouteDoesNotExist() {
        RouteFinder finder = mock(RouteFinder.class);
        when(finder.findRoute("CZE", "USA")).thenReturn(Optional.empty());

        RoutingService service = new RoutingService(finder);

        assertThatThrownBy(() -> service.findRoute("CZE", "USA")).isInstanceOf(RouteNotFoundException.class);
    }
}