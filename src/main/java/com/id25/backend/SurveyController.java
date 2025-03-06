package com.id25.backend;

import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.id25.backend.SurveyLoader.*;

@RestController
@RequestMapping("/api/survey")
@CrossOrigin(origins = {"http://localhost:3000", "https://id25-react.onrender.com/"}) // Tillad adgang fra frontend
public class SurveyController {


    private final List<Survey> surveyData;

    public SurveyController() {
            surveyData = SurveyLoader.loadSurveyData("/app/dummyData.txt");
    }


    @GetMapping("/results")
    public List<Survey> getSurveyResults(
            @RequestParam(required = false) String parti,
            @RequestParam(required = false) String fornavn,
            @RequestParam(required = false) String svar1,
            @RequestParam(required = false) String svar2,
            @RequestParam(required = false) String svar3,
            @RequestParam(required = false) String svar4,
            @RequestParam(required = false) String svar5
    ) {
        return surveyData.stream()
                .filter(s -> parti == null || s.getParti().equalsIgnoreCase(parti))
                .filter(s -> fornavn == null || s.getFornavn().equalsIgnoreCase(fornavn))
                .filter(s -> svar1 == null || s.getSvar1().equalsIgnoreCase(svar1))
                .filter(s -> svar2 == null || s.getSvar2().equalsIgnoreCase(svar2))
                .filter(s -> svar3 == null || s.getSvar3().equalsIgnoreCase(svar3))
                .filter(s -> svar4 == null || s.getSvar4().equalsIgnoreCase(svar4))
                .filter(s -> svar5 == null || s.getSvar5().equalsIgnoreCase(svar5))
                .collect(Collectors.toList());
    }
}
