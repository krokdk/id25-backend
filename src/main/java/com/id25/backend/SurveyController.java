package com.id25.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final GoogleSheetImporter googleSheetImporter;
    private List<Survey> cachedData; // Lokalt cachet data
    private Instant lastUpdated;
    private static final Duration CACHE_DURATION = Duration.ofHours(1); // Cache i 10 minutter

    @Autowired
    public SurveyController(GoogleSheetImporter googleSheetImporter) {
        this.googleSheetImporter = googleSheetImporter;
        this.cachedData = new ArrayList<Survey>();
        this.lastUpdated = Instant.MIN;
    }

    @GetMapping("/results")
    public List<Survey> getSurveyData(
            @RequestParam(required = false) String parti,
            @RequestParam(required = false) String fornavn,
            @RequestParam(required = false) String storkreds,
            @RequestParam(required = false) String svar1,
            @RequestParam(required = false) String svar2,
            @RequestParam(required = false) String svar3,
            @RequestParam(required = false) String svar4,
            @RequestParam(required = false) String svar5
    ) throws GeneralSecurityException, IOException {
        // Tjekker om cachen er forældet
        if (cachedData.isEmpty() || Duration.between(lastUpdated, Instant.now()).compareTo(CACHE_DURATION) > 0) {
            cachedData = googleSheetImporter.importGoogleSheetData(); // Henter friske data
            lastUpdated = Instant.now();
        }
        return cachedData.stream()
                .filter(s -> parti == null || s.getParti().equalsIgnoreCase(parti))
                .filter(s -> fornavn == null || s.getFornavn().equalsIgnoreCase(fornavn))
                .filter(s -> storkreds == null || s.getStorkreds().equalsIgnoreCase(fornavn))
                .filter(s -> svar1 == null || s.getSvar1().equalsIgnoreCase(svar1))
                .filter(s -> svar2 == null || s.getSvar2().equalsIgnoreCase(svar2))
                .filter(s -> svar3 == null || s.getSvar3().equalsIgnoreCase(svar3))
                .filter(s -> svar4 == null || s.getSvar4().equalsIgnoreCase(svar4))
                .filter(s -> svar5 == null || s.getSvar5().equalsIgnoreCase(svar5))
                .collect(Collectors.toList());
    }

    @GetMapping("/refresh")
    public String refreshCache() throws GeneralSecurityException, IOException {
        cachedData = googleSheetImporter.importGoogleSheetData(); // Henter friske data manuelt
        lastUpdated = Instant.now();
        return "Cache opdateret!";
    }
}
