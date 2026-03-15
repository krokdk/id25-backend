package com.id25.backend;

import com.id25.backend.dto.*;
import com.id25.backend.googlesheets.*;
import com.id25.backend.matcher.candidateMatcher;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@RestController
@CrossOrigin(origins = {
        "https://id25-react.onrender.com",
        "https://id25-komregvalg.onrender.com",
        "http://localhost:3000/",
        "http://www.intactdenmarktesten.dk",
        "https://www.intactdenmarktesten.dk",
        "http://intactdenmarktesten.dk",
        "https://id25-kandidattest2026-react.onrender.com",})
@RequestMapping("/api/survey")
public class SurveyController {

    private final GoogleSheetImporterFactory googleSheetImporterFactory;
    private DataImporter dataImporter;
    private Map<Long, List<SurveyDto>> cachedData; // Lokalt cachet data
    private Instant lastUpdated;
    private static final Duration CACHE_DURATION = Duration.ofMinutes(2);

    @Autowired
    public SurveyController(GoogleSheetImporterFactory dataImporterFactory) {
        this.googleSheetImporterFactory = dataImporterFactory;
        this.cachedData = new HashMap<>();
        this.lastUpdated = Instant.MIN;
    }

    @GetMapping("/results")
    public List<SurveyDto> getSurveyData(
            @RequestParam(required = false) String parti,
            @RequestParam(required = false) String fornavn,
            @RequestParam(required = false) String storkreds,
            @RequestParam(required = false) String svar1,
            @RequestParam(required = false) String svar2,
            @RequestParam(required = false) String svar3,
            @RequestParam(required = false) String svar4,
            @RequestParam(required = false) String svar5,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) Long year,
            @RequestParam(required = false) String email
    ) throws GeneralSecurityException, IOException {
        if (cachedData.isEmpty() || !cachedData.containsKey(year) || Duration.between(lastUpdated, Instant.now()).compareTo(CACHE_DURATION) > 0) {
            dataImporter = googleSheetImporterFactory.getImporter(year);
            cachedData.put(year, dataImporter.importData()); // Henter friske data
            lastUpdated = Instant.now();
        }
        return cachedData.get(year).stream()
                .filter(s -> parti == null || s.getParti().equalsIgnoreCase(parti))
                .filter(s -> fornavn == null || s.getFornavn().equalsIgnoreCase(fornavn))
                .filter(s -> storkreds == null || s.getStorkreds().equalsIgnoreCase(storkreds))
                .filter(s -> svar1 == null || s.getSvar1().equalsIgnoreCase(svar1))
                .filter(s -> svar2 == null || s.getSvar2().equalsIgnoreCase(svar2))
                .filter(s -> svar3 == null || s.getSvar3().equalsIgnoreCase(svar3))
                .filter(s -> svar4 == null || s.getSvar4().equalsIgnoreCase(svar4))
                .filter(s -> svar5 == null || s.getSvar5().equalsIgnoreCase(svar5))
                // ID132 update to match 2026 questions
                .filter(s -> url == null || s.getUrl().equalsIgnoreCase(url))
                .filter(s -> email == null || s.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }

    @PostMapping("/results/matches")
    public List<MatchResponseDto> getMatches(
            @RequestBody MatchDto match
    ) throws GeneralSecurityException, IOException {
        if (cachedData.isEmpty() || !cachedData.containsKey(2026L) || Duration.between(lastUpdated, Instant.now()).compareTo(CACHE_DURATION) > 0) {
            dataImporter = googleSheetImporterFactory.getImporter(2026L);
            cachedData.put(2026L, dataImporter.importData()); // Henter friske data
            lastUpdated = Instant.now();
        }
        var candidates = cachedData.get(2026L).stream()
                .filter(s -> match.getStorkreds() == null || s.getStorkreds().equalsIgnoreCase(match.getStorkreds()))
                .collect(Collectors.toList());

        return new candidateMatcher().getMatches(Arrays.asList(match.getAnswers()), candidates);

    }
}
