package com.pwc.routing.exception;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String origin, String destination) {
        super("No land route found from " + origin + " to " + destination);
    }
}
