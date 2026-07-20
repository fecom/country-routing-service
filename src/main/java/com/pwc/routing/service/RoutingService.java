package com.pwc.routing.service;

import com.pwc.routing.dto.RouteResponse;
import com.pwc.routing.exception.RouteNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoutingService {

    private final RouteFinder routeFinder;

    public RoutingService(RouteFinder routeFinder) {
        this.routeFinder = routeFinder;
    }

    public RouteResponse findRoute(String origin, String destination) {
        return routeFinder
            .findRoute(origin, destination)
            .map(RouteResponse::new)
            .orElseThrow(() -> new RouteNotFoundException(origin, destination));
    }

}
