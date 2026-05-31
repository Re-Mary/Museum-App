package com.museum.app.core;

import com.museum.app.PostgresTestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportTestcontainers(PostgresTestcontainersConfiguration.class)
class MuseumObjectApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createObjectAndConditionReport() throws Exception {
        String objectJson = """
                {
                  "title": "Kaiser Relief",
                  "inventarNumber": "SKU-00077",
                  "assignSystemNumber": true
                }
                """;

        MvcResult objectResult = mockMvc.perform(post("/api/v1/objects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.systemNumber").exists())
                .andReturn();

        String objectId = com.jayway.jsonpath.JsonPath.read(
                objectResult.getResponse().getContentAsString(), "$.id");

        String reportJson = """
                {
                  "reportDate": "2026-05-29",
                  "conditionCode": "good",
                  "summary": "Initial condition assessment"
                }
                """;

        mockMvc.perform(post("/api/v1/objects/" + objectId + "/condition-reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reportJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.conditionCode").value("good"));

        mockMvc.perform(get("/api/v1/objects/" + objectId + "/condition-reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].summary").value("Initial condition assessment"));
    }
}
