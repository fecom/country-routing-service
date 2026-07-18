package com.pwc.routing.http;


import com.pwc.routing.model.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class DefaultCountryHttpClient implements CountryHttpClient {

    private final String countriesUrl;
    private final RestClient restClient;

    public DefaultCountryHttpClient(
            @Value("${app.routing.countries.data.path}")
            String countriesUrl,
            RestClient restClient
    ) {
        this.countriesUrl = countriesUrl;
        this.restClient = restClient;
    }


    @Override
    public List<Country> fetchCountries() {
        Country[] countries = restClient
                .get()
                .uri(countriesUrl)
                .retrieve()
                .body(Country[].class);
        if (countries == null) {
            return List.of();
        }
        return Arrays.asList(countries);
    }
}
