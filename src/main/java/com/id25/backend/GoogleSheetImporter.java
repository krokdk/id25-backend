package com.id25.backend;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetImporter {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SPREADSHEET_ID = "17Ys5TtNURFctkaLXRkPeJOpwjqh4WjwqgEbseDy9ps0";//2024 "1N6z_A3eAImleBXdzky1cbLADQtUOg2fFNlR9pOiTGDs";
    private static final String RANGE = "tablepress2!A:H";// 2024 "tablepressview2!A:H"; // Fetch columns A to H

    // ✅ Read credentials from Render environment variable
    private static final String CREDENTIALS_ENV_VAR = "GOOGLE_CREDENTIALS";

    // 🔹 Filsti til credentials i lokal udvikling
    private static final String CREDENTIALS_FILE_PATH = "google-credentials.json";

    public GoogleSheetImporter() {
        // Default Constructor (Dependency Injection)
    }

    public List<Survey> importGoogleSheetData() throws GeneralSecurityException, IOException {
        Sheets sheetsService = getSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        List<List<Object>> values = response.getValues();
        List<Survey> surveys = new ArrayList<>();

        if (values != null && !values.isEmpty()) {
            for (List<Object> row : values) {
                if (row.size() >= 3) {
                    String parti = PartiMapper.getPartiBogstav(row.get(1).toString());  // Assuming column 1 is parti
                    String fornavn = row.get(0).toString();  // Assuming column 2 is fornavn
                    String storkreds = row.get(2).toString();  // Assuming column 3 is storkreds
                    String svar1 = row.size() > 3 ? row.get(3).toString() : "";  // Hvis tom, sæt til ""
                    String svar2 = row.size() > 4 ? row.get(4).toString() : "";
                    String svar3 = row.size() > 5 ? row.get(5).toString() : "";
                    String svar4 = row.size() > 6 ? row.get(6).toString() : "";
                    String svar5 = row.size() > 7 ? row.get(7).toString() : "";

                    // Create Survey object with all fields
                    surveys.add(new Survey(parti, fornavn, storkreds, svar1, svar2, svar3, svar4, svar5));
                }
            }
        }
        return surveys;
    }

    private Sheets getSheetsService() throws IOException, GeneralSecurityException {
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential;

        // 🔹 Tjek om miljøvariablen findes (Render)
        String credentialsJson = System.getenv(CREDENTIALS_ENV_VAR);
        if (credentialsJson != null && !credentialsJson.isEmpty()) {
            System.out.println("🔹 Bruger miljøvariabel til autentificering (Render)");
            credential = GoogleCredential.fromStream(new ByteArrayInputStream(credentialsJson.getBytes()))
                    .createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets.readonly"));
        } else {
            // 🔹 Brug lokal credentials-fil
            System.out.println("🔹 Miljøvariabel ikke fundet. Bruger lokal fil: " + CREDENTIALS_FILE_PATH);
            File credentialsFile = new File(CREDENTIALS_FILE_PATH);
            if (!credentialsFile.exists()) {
                throw new FileNotFoundException("Fejl: google-credentials.json findes ikke! Placer filen i projektets rod.");
            }

            credential = GoogleCredential.fromStream(new FileInputStream(credentialsFile))
                    .createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets.readonly"));
        }

        return new Sheets.Builder(transport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
