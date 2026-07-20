package com.pwc.routing.http;


import com.pwc.routing.model.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CountryHttpClient implements HttpClient<Country> {

    private final String countriesUrl;
    private final RestClient restClient;

    public CountryHttpClient(
            @Value("${app.routing.countries.data.path}")
            String countriesUrl,
            RestClient restClient
    ) {
        this.countriesUrl = countriesUrl;
        this.restClient = restClient;
    }


    @Override
    public List<Country> fetch() {
        List<Country> countries = restClient
            .get()
            .uri(countriesUrl)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });

        if (countries == null || countries.isEmpty()) {
            throw new IllegalStateException("Countries dataset is empty or null");
        }

        return countries;
    }

}
