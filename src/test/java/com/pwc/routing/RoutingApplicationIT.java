package com.pwc.routing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RoutingApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFindLandRoute() throws Exception {
        mockMvc
            .perform(get("/routing/CZE/ITA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.route").isArray())
            .andExpect(jsonPath("$.route[0]").value("CZE"))
            .andExpect(jsonPath("$.route[1]").value("AUT"))
            .andExpect(jsonPath("$.route[2]").value("ITA"));
    }

    @Test
    void shouldReturn400WhenNoRouteExists() throws Exception {
        mockMvc
            .perform(get("/routing/CZE/USA"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("No land route found from CZE to USA"));
    }

}