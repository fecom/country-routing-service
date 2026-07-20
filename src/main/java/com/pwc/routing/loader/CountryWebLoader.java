package com.pwc.routing.loader;

import com.pwc.routing.http.CountryHttpClient;
import com.pwc.routing.model.Country;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(
        name = "app.routing.countries.data.source",
        havingValue = "web"
)
public class CountryWebLoader implements CountryLoader {

    private final CountryHttpClient countryHttpClient;

    public CountryWebLoader(CountryHttpClient countryHttpClient) {
        this.countryHttpClient = countryHttpClient;
    }

    @Override
    public List<Country> load() {
        return countryHttpClient.fetchCountries()
                .stream()
                .filter(country -> country.cca3() != null)
                .toList();
    }
}