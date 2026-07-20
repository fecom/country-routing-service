package com.pwc.routing.controller;

import com.pwc.routing.dto.RouteResponse;
import com.pwc.routing.service.RoutingService;

import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/routing")
@Validated
public class RoutingController {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/{origin}/{destination}")
    public RouteResponse getRoute(
        @PathVariable
        @Pattern(regexp = "[A-Z]{3}")
        String origin,
        @PathVariable
        @Pattern(regexp = "[A-Z]{3}")
        String destination
    ) {
        return routingService.findRoute(origin, destination);
    }
}