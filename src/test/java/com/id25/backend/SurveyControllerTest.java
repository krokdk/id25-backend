package com.id25.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SurveyController.class) // Only loads the controller layer
class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnSurveyResults() throws Exception {
        mockMvc.perform(get("/api/survey/results"))
                .andExpect(status().isOk()) // Check HTTP 200 status
                .andExpect(jsonPath("$.length()").value(3)) // Expecting a list with 1 element
                .andExpect(jsonPath("$[2].parti").value("Ø")) // Validate first survey object
                .andExpect(jsonPath("$[2].fornavn").value("Pelle"))
                .andExpect(jsonPath("$[2].storkreds").value("Storkøbenhavn"))
                .andExpect(jsonPath("$[2].email").value("pelle@ø.dk"))
                .andExpect(jsonPath("$[2].svar1").value("ja"))
                .andExpect(jsonPath("$[2].svar2").value("nej"))
                .andExpect(jsonPath("$[2].svar3").value("ja"))
                .andExpect(jsonPath("$[2].svar4").value("nej"))
                .andExpect(jsonPath("$[2].svar5").value("kommentar"));
    }
}

