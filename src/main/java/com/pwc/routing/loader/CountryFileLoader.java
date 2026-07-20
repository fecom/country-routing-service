package com.pwc.routing.loader;

import com.pwc.routing.model.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@ConditionalOnProperty(
        name = "app.routing.countries.data.source",
        havingValue = "file",
        matchIfMissing = true
)
public class CountryFileLoader implements CountryLoader {

    private final String path;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public CountryFileLoader(
        @Value("${app.routing.countries.data.path}")
        String path,
        ResourceLoader resourceLoader,
        ObjectMapper objectMapper
    ) {
        this.path = path;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Country> load() {
        try (var inputStream = resourceLoader.getResource(path).getInputStream()) {
            List<Country> countries = objectMapper.readValue(inputStream, new TypeReference<>() {});
            if (countries.isEmpty()) {
                throw new IllegalStateException("Countries dataset is empty");
            }
            return countries;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load countries from resource", e);
        }
    }

}