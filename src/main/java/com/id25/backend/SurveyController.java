package com.id25.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    @GetMapping("/results")
    public Map<String, Integer> getSurveyResults() {
        return Map.of("yes", 55, "no", 35, "undecided", 10);
    }
}
