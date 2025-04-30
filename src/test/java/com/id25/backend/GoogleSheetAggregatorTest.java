package com.id25.backend;

import com.id25.backend.dto.*;
import com.id25.backend.googlesheets.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.security.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleSheetAggregatorTest {

    @Test
    public void hulIgennem() {
        GoogleSheetAggregator importer = new GoogleSheetAggregator(0L);
        try {
            List<SurveyDto> surveys = importer.importData();

            // 🔹 Sikrer at vi har fået data tilbage
            assertNotNull(surveys, "Liste må ikke være null");
            assertFalse(surveys.isEmpty(), "Liste må ikke være tom");

        } catch (IOException | GeneralSecurityException e) {
            fail("Fejl ved import af Google Sheet-data: " + e.getMessage());
        }
    }

    @Test
    public void contactMedSurveySvar() {
        GoogleSheetAggregator importer = new GoogleSheetAggregator(0L);
        try {
            List<SurveyDto> surveys = importer.importData();

            var sigurd= surveys
                    .stream()
                    .filter(x->"Sigurd Agersnap".equalsIgnoreCase(x.getFornavn()))
                    .findFirst();

            assertTrue(sigurd.isPresent());

            SurveyDto survey = sigurd.get();
            assertEquals( "Sigurd Agersnap",survey.getFornavn());
            assertEquals("Socialistisk Folkeparti",survey.getParti());
            assertEquals("https://sf.dk/politiker/sigurd-agersnap", survey.getUrl());
            assertEquals("Københavns omegn",survey.getStorkreds());
            assertEquals("nej", survey.getSvar1());
            assertEquals("skide godt!", survey.getSvar5());

        } catch (IOException | GeneralSecurityException e) {
            fail("Fejl ved import af Google Sheet-data: " + e.getMessage());
        }
    }

    @Test
    public void contactUdenSurveySvar() {
        GoogleSheetAggregator importer = new GoogleSheetAggregator(0L);
        try {
            List<SurveyDto> surveys = importer.importData();

            var suzette= surveys
                    .stream()
                    .filter(x->"Suzette Frovin".equalsIgnoreCase(x.getFornavn()))
                    .findFirst();

            assertTrue(suzette.isPresent());

            SurveyDto survey = suzette.get();
            assertEquals( "Suzette Frovin",survey.getFornavn());
            assertEquals("Socialistisk Folkeparti",survey.getParti());
            assertEquals("https://sf.dk/politiker/suzette-frovin", survey.getUrl());
            assertEquals("Fyn",survey.getStorkreds());
            assertEquals("", survey.getSvar1());
            assertEquals("", survey.getSvar5());

        } catch (IOException | GeneralSecurityException e) {
            fail("Fejl ved import af Google Sheet-data: " + e.getMessage());
        }
    }
}
