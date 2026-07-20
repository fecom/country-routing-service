package com.pwc.routing.service;

import com.pwc.routing.graph.CountryGraph;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

@Service
public class RouteFinder {

    private final CountryGraph graph;

    public RouteFinder(CountryGraph graph) {
        this.graph = graph;
    }

    public Optional<List<String>> findRoute(String origin, String destination) {
        if (!graph.contains(origin) || !graph.contains(destination)) {
            return Optional.empty();
        }
        if (origin.equals(destination)) {
            return Optional.of(List.of(origin));
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();
        queue.add(origin);
        visited.add(origin);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbour : graph.neighboursOf(current)) {
                if (visited.contains(neighbour)) {
                    continue;
                }
                visited.add(neighbour);
                previous.put(neighbour, current);
                if (neighbour.equals(destination)) {
                    return Optional.of(buildPath(previous, destination));
                }
                queue.add(neighbour);
            }
        }
        return Optional.empty();
    }

    private List<String> buildPath(Map<String, String> previous, String destination) {
        LinkedList<String> path = new LinkedList<>();
        String current = destination;
        while (current != null) {
            path.addFirst(current);
            current = previous.get(current);
        }
        return List.copyOf(path);
    }

}