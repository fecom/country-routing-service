package com.pwc.routing.config;

import com.pwc.routing.graph.CountryGraph;
import com.pwc.routing.graph.GraphBuilder;
import com.pwc.routing.loader.CountryLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphConfig {

    @Bean
    CountryGraph countryGraph(CountryLoader countryLoader, GraphBuilder graphBuilder) {
        return graphBuilder.build(countryLoader.load());
    }

}