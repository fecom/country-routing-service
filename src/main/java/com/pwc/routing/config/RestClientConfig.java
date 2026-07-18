package com.pwc.routing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient restClient() {
        return RestClient.builder().configureMessageConverters(converters -> converters.addCustomConverter(jacksonConverter())).build();
    }

    private JacksonJsonHttpMessageConverter jacksonConverter() {
        JacksonJsonHttpMessageConverter converter = new JacksonJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        return converter;
    }

}