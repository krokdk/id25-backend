package com.id25.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final List<Survey> surveyData = List.of(
            new Survey("A", "Mette", "Nordjylland", "mette@a.dk", "ja", "nej", "ja", "nej", "Ingen kommentar"),
            new Survey("B", "Lars", "Midtjylland", "lars@b.dk", "nej", "ja", "nej", "ja", "Spændende"),
            new Survey("Ø", "Pelle", "Storkøbenhavn", "pelle@ø.dk", "ja", "nej", "ja", "nej", "kommentar")
    );

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
