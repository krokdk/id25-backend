package com.id25.backend;

import com.id25.backend.googlesheets.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GoogleSheetImporterTest {

    @Test
    void testImportGoogleSheetData() {
        GoogleSheetImporter importer = new GoogleSheetImporter(2024L);

        try {
            List<Survey> surveys = importer.importGoogleSheetData();

            // 🔹 Sikrer at vi har fået data tilbage
            assertNotNull(surveys, "Liste må ikke være null");
            assertFalse(surveys.isEmpty(), "Liste må ikke være tom");

            // 🔹 Validerer en tilfældig værdi
            Survey firstSurvey = surveys.get(0);
            assertNotNull(firstSurvey.getFornavn(), "Fornavn må ikke være null");
            assertNotNull(firstSurvey.getParti(), "Parti må ikke være null");
            assertNotNull(firstSurvey.getEmail(), "Parti må ikke være null");
            assertNotNull(firstSurvey.getStorkreds(), "Parti må ikke være null");

        } catch (IOException | GeneralSecurityException e) {
            fail("Fejl ved import af Google Sheet-data: " + e.getMessage());
        }
    }
}
