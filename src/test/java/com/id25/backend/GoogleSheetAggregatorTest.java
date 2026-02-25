package com.id25.backend;

import com.id25.backend.dto.*;
import com.id25.backend.googlesheets.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.security.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleSheetAggregatorTest {

    String sheetId2026 = GoogleSheetImporterFactory.folketingsvalg2026;
    String sheetId9999 = GoogleSheetImporterFactory.kommunalvalg2025;
    String sheetId8888 = GoogleSheetImporterFactory.regionsrådsvalg2025;

    @Test
    public void hulIgennem2026() {
        GoogleSheetAggregator importer = new GoogleSheetAggregator2026(sheetId2026);
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
    public void hulIgennem() {
        GoogleSheetAggregator importer = new GoogleSheetAggregator(sheetId2026, 2026L);
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
        GoogleSheetAggregator importer = new GoogleSheetAggregator(sheetId2026,2026L);
        try {
            List<SurveyDto> surveys = importer.importData();

            var sigurd= surveys
                    .stream()
                    .filter(x->"Stuffi Rok".equalsIgnoreCase(x.getFornavn()))
                    .findFirst();

            assertTrue(sigurd.isPresent());

            SurveyDto survey = sigurd.get();
            assertEquals( "Stuffi Rok",survey.getFornavn());
            assertEquals("UKENDT",survey.getParti());
            assertEquals("https://id25-react.onrender.com/", survey.getUrl());
            assertEquals("Amar",survey.getStorkreds());
            assertEquals("nej", survey.getSvar1());
            assertEquals("skide godt!", survey.getSvar5());

        } catch (IOException | GeneralSecurityException e) {
            fail("Fejl ved import af Google Sheet-data: " + e.getMessage());
        }
    }

    @Test
    public void contactUdenSurveySvar() {
        GoogleSheetAggregator importer = new GoogleSheetAggregator(sheetId2026, 2026L);
        try {
            List<SurveyDto> surveys = importer.importData();

            var suzette= surveys
                    .stream()
                    .filter(x->"Suzette Frovin".equalsIgnoreCase(x.getFornavn()))
                    .findFirst();

            assertTrue(suzette.isPresent());

            SurveyDto survey = suzette.get();
            assertEquals( "Suzette Frovin",survey.getFornavn());
            assertEquals("F",survey.getParti());
            assertEquals("https://sf.dk/politiker/suzette-frovin", survey.getUrl());
            assertEquals("",survey.getStorkreds());
            assertEquals("Fyn",survey.getValg());
            assertEquals("Ikke besvaret", survey.getSvar1());
            assertEquals("Ikke besvaret", survey.getSvar5());

        } catch (IOException | GeneralSecurityException e) {
            fail("Fejl ved import af Google Sheet-data: " + e.getMessage());
        }
    }

    @Test
    public void svarFraTallySurveyHvorKunEmailKendes() {
        GoogleSheetAggregator importer = new GoogleSheetAggregator(sheetId9999, 9999L);
        try {
            List<SurveyDto> surveys = importer.importData();

            var kandidat= surveys
                    .stream()
                    .filter(x->"Stuffi Rok".equalsIgnoreCase(x.getFornavn()))
                    .findFirst();

            assertTrue(kandidat.isPresent());

            SurveyDto survey = kandidat.get();
            assertEquals( "Stuffi Rok",survey.getFornavn());
            assertEquals("UKENDT",survey.getParti());
            assertEquals("https://id25-react.onrender.com/", survey.getUrl());

            assertEquals("Nej", survey.getSvar1());
            assertEquals("tally ho!", survey.getSvar4());

        } catch (IOException | GeneralSecurityException e) {
            fail("Fejl ved import af Google Sheet-data: " + e.getMessage());
        }
    }

    @Test
    public void svarFraTallyForRegionsrådsvalg() {
        GoogleSheetAggregator importer = new GoogleSheetAggregator(sheetId8888, 8888L);
        try {
            List<SurveyDto> surveys = importer.importData();

            var kandidat= surveys
                    .stream()
                    .filter(x->"Stuffi Rok".equalsIgnoreCase(x.getFornavn()))
                    .findFirst();

            assertTrue(kandidat.isPresent());

            SurveyDto survey = kandidat.get();
            assertEquals( "Stuffi Rok",survey.getFornavn());
            assertEquals("UKENDT",survey.getParti());
            assertEquals("https://id25-react.onrender.com/", survey.getUrl());

            assertEquals("Nej", survey.getSvar1());
            assertEquals("Skide godt!", survey.getSvar4());

        } catch (IOException | GeneralSecurityException e) {
            fail("Fejl ved import af Google Sheet-data: " + e.getMessage());
        }
    }
}
