package com.pwc.routing.graph;

import com.pwc.routing.model.Country;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GraphBuilderTest {

    private final GraphBuilder graphBuilder = new GraphBuilder();

    @Test
    void shouldBuildGraph() {
        CountryGraph graph = graphBuilder.build(List.of(
                new Country("CZE", List.of("AUT", "DEU")),
                new Country("AUT", List.of("CZE", "ITA")),
                new Country("DEU", List.of("CZE")),
                new Country("ITA", List.of("AUT"))
        ));

        assertThat(graph.neighboursOf("CZE"))
                .containsExactlyInAnyOrder("AUT", "DEU");
        assertThat(graph.neighboursOf("AUT"))
                .containsExactlyInAnyOrder("CZE", "ITA");
        assertThat(graph.neighboursOf("ITA"))
                .containsExactly("AUT");
    }

    @Test
    void shouldCreateEmptyGraph() {
        CountryGraph graph = graphBuilder.build(List.of());

        assertThat(graph.neighboursOf("CZE")).isEmpty();
        assertThat(graph.contains("CZE")).isFalse();
    }

    @Test
    void shouldCreateCountryWithoutBorders() {
        CountryGraph graph = graphBuilder.build(List.of(
                new Country("ISL", List.of())
        ));

        assertThat(graph.contains("ISL")).isTrue();
        assertThat(graph.neighboursOf("ISL")).isEmpty();
    }

    @Test
    void shouldRemoveDuplicateBorders() {
        CountryGraph graph = graphBuilder.build(List.of(
                new Country("CZE", List.of("AUT", "AUT", "AUT"))
        ));

        assertThat(graph.neighboursOf("CZE"))
                .containsExactly("AUT");
    }

    @Test
    void shouldHandleNullBorders() {
        CountryGraph graph = graphBuilder.build(List.of(
                new Country("CZE", null)
        ));

        assertThat(graph.contains("CZE")).isTrue();
        assertThat(graph.neighboursOf("CZE")).isEmpty();
    }

    @Test
    void shouldFailForNullCountries() {
        assertThatThrownBy(() -> graphBuilder.build(null))
                .isInstanceOf(NullPointerException.class);
    }

}