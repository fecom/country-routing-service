package com.pwc.routing.http;

import com.pwc.routing.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CountryHttpClientTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @Test
    void shouldFetchCountriesSuccessfully() {
        String url = "http://countries.test";

        Country country = new Country("CZE", null);
        mockRestClientResponse(List.of(country));

        CountryHttpClient client = new CountryHttpClient(url, restClient);
        List<Country> result = client.fetch();

        assertThat(result).containsExactly(country);
    }

    @Test
    void shouldThrowExceptionWhenCountriesAreNull() {
        String url = "http://countries.test";

        mockRestClientResponse(null);

        CountryHttpClient client = new CountryHttpClient(url, restClient);

        assertThatThrownBy(client::fetch)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Countries dataset is empty or null");
    }

    @Test
    void shouldThrowExceptionWhenCountriesAreEmpty() {
        String url = "http://countries.test";

        mockRestClientResponse(List.of());

        CountryHttpClient client = new CountryHttpClient(url, restClient);

        assertThatThrownBy(client::fetch)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Countries dataset is empty or null");
    }

    private void mockRestClientResponse(List<Country> expected) {
        doReturn(requestHeadersUriSpec)
            .when(restClient).get();
        doReturn(requestHeadersSpec)
            .when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec)
            .when(requestHeadersSpec).retrieve();
        doReturn(expected)
            .when(responseSpec)
            .body(ArgumentMatchers.<ParameterizedTypeReference<List<Country>>>any());
    }

}
