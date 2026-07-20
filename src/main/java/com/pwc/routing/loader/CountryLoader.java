package com.pwc.routing.loader;

import com.pwc.routing.model.Country;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CountryLoader {
    List<Country> load();
}
