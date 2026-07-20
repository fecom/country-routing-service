package com.pwc.routing.config;

import com.pwc.routing.graph.CountryGraph;
import com.pwc.routing.graph.GraphBuilder;
import com.pwc.routing.loader.CountryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphConfig {

    private static final Logger log = LoggerFactory.getLogger(GraphConfig.class);

    @Bean
    CountryGraph countryGraph(CountryLoader countryLoader, GraphBuilder graphBuilder) {
        var countries = countryLoader.load();
        log.info("Loaded {} countries", countries.size());
        return graphBuilder.build(countries);
    }

}