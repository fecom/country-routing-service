package com.pwc.routing.http;

import com.pwc.routing.model.Country;

import java.util.List;

public interface CountryHttpClient {
    List<Country> fetchCountries();
}
