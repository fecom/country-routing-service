package com.pwc.routing.controller;

import com.pwc.routing.dto.RouteResponse;
import com.pwc.routing.exception.RouteNotFoundException;
import com.pwc.routing.service.RoutingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RoutingController.class)
class RoutingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoutingService routingService;

    @Test
    void shouldReturnRoute() throws Exception {
        when(routingService.findRoute("CZE", "ITA")).thenReturn(new RouteResponse(java.util.List.of("CZE", "AUT", "ITA")));

        mockMvc
            .perform(get("/routing/CZE/ITA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.route[0]").value("CZE"))
            .andExpect(jsonPath("$.route[2]").value("ITA"));
    }


   @Test
    void shouldRejectInvalidCountryCode() throws Exception {
        mockMvc.perform(get("/routing/cz/ITA"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenRouteDoesNotExist() throws Exception {
        when(routingService.findRoute("CZE", "USA"))
            .thenThrow(new RouteNotFoundException("CZE", "USA"));
        mockMvc.perform(get("/routing/CZE/USA"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("No land route found from CZE to USA"));
    }

}