package com.pwc.routing.loader;

import com.pwc.routing.http.CountryHttpClient;
import com.pwc.routing.model.Country;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CountryWebLoaderTest {

    @Test
    void shouldLoadOnlyValidCountries() {

        CountryHttpClient client = mock(CountryHttpClient.class);
        when(client.fetch()).thenReturn(List.of(new Country("CZE", List.of("AUT")), new Country(null, List.of("XXX"))));

        CountryWebLoader loader = new CountryWebLoader(client);
        var result = loader.load();

        assertThat(result).containsExactly(new Country("CZE", List.of("AUT")));
        verify(client).fetch();
    }

}
