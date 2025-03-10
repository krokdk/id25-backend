package com.id25.backend;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@RestController
@CrossOrigin(origins = {"https://id25-react.onrender.com", "http://localhost:3000/"})
@RequestMapping("/api/survey")
public class SurveyController {

    private final GoogleSheetImporterFactory googleSheetImporterFactory;
    private GoogleSheetImporter googleSheetImporter;
        private Map<Long, List<Survey>> cachedData; // Lokalt cachet data
    private Instant lastUpdated;
    private static final Duration CACHE_DURATION = Duration.ofHours(1); // Cache i 10 minutter

    @Autowired
    public SurveyController(GoogleSheetImporterFactory googleSheetImporterFactory) {
        this.googleSheetImporterFactory = googleSheetImporterFactory;
        this.cachedData = new HashMap<>();
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
            @RequestParam(required = false) String svar5,
            @RequestParam(required = false) Long year
    ) throws GeneralSecurityException, IOException {
        if (cachedData.isEmpty() || !cachedData.containsKey(year) || Duration.between(lastUpdated, Instant.now()).compareTo(CACHE_DURATION) > 0) {
            googleSheetImporter = googleSheetImporterFactory.getImporter(year);
            cachedData.put(year, googleSheetImporter.importGoogleSheetData()); // Henter friske data
            lastUpdated = Instant.now();
        }
        return cachedData.get(year).stream()
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

        for (var cachedSheetData : cachedData.values()) cachedSheetData = googleSheetImporter.importGoogleSheetData();

        lastUpdated = Instant.now();
        return "Cache opdateret!";
    }
}
