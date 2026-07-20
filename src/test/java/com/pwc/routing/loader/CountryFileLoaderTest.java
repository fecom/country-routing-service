package com.pwc.routing.loader;

import com.pwc.routing.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CountryFileLoaderTest {

    private static final String PATH = "classpath:countries.json";

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private Resource resource;

    @Mock
    private ObjectMapper objectMapper;

    private CountryFileLoader loader;

    @BeforeEach
    void setUp() {
        loader = new CountryFileLoader(
            PATH,
            resourceLoader,
            objectMapper
        );
    }

    @Test
    void shouldLoadCountries() throws Exception {
        InputStream inputStream =
            new ByteArrayInputStream("[]".getBytes());
        List<Country> expected = List.of(
            new Country("CZE", List.of("AUT")),
            new Country("AUT", List.of("CZE"))
        );
        given(resourceLoader.getResource(PATH))
            .willReturn(resource);
        given(resource.getInputStream())
            .willReturn(inputStream);
        given(objectMapper.readValue(
            any(InputStream.class),
            any(TypeReference.class)))
            .willReturn(expected);

        List<Country> result = loader.load();

        assertThat(result)
            .containsExactlyElementsOf(expected);
        then(resourceLoader)
            .should()
            .getResource(PATH);
        then(resource)
            .should()
            .getInputStream();
        then(objectMapper)
            .should()
            .readValue(
                any(InputStream.class),
                any(TypeReference.class));
        then(resourceLoader)
            .shouldHaveNoMoreInteractions();
        then(resource)
            .shouldHaveNoMoreInteractions();
        then(objectMapper)
            .shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenResourceCannotBeOpened() throws Exception {
        given(resourceLoader.getResource(PATH))
            .willReturn(resource);
        given(resource.getInputStream())
            .willThrow(new IOException("boom"));

        assertThatThrownBy(loader::load)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Cannot load countries from resource")
            .cause()
            .isInstanceOf(IOException.class);
        then(resourceLoader)
            .should()
            .getResource(PATH);
        then(resource)
            .should()
            .getInputStream();
        then(objectMapper)
            .shouldHaveNoInteractions();
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenObjectMapperFails() throws Exception {

        InputStream inputStream = new ByteArrayInputStream("[]".getBytes());
        given(resourceLoader.getResource(PATH))
            .willReturn(resource);
        given(resource.getInputStream())
            .willReturn(inputStream);
        given(objectMapper.readValue(
            any(InputStream.class),
            any(TypeReference.class)))
            .willThrow(new RuntimeException("invalid json"));

        assertThatThrownBy(loader::load)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Cannot load countries from resource")
            .cause()
            .isInstanceOf(RuntimeException.class);
        then(resourceLoader)
            .should()
            .getResource(PATH);
        then(resource)
            .should()
            .getInputStream();
        then(objectMapper)
            .should()
            .readValue(
                any(InputStream.class),
                any(TypeReference.class)
            );
        then(resourceLoader)
            .shouldHaveNoMoreInteractions();
        then(resource)
            .shouldHaveNoMoreInteractions();
        then(objectMapper)
            .shouldHaveNoMoreInteractions();
    }

}