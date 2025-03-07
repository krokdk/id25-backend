package com.id25.backend;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetImporter {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SPREADSHEET_ID = "1B0V2vMr8baXp6PftSYF7FBAFUJGORFB1eZaMBJvviBY";
    private static final String RANGE = "valg2024!A:I"; // Fetch columns A to I

    // ✅ Read credentials from Render environment variable
    private static final String CREDENTIALS_ENV_VAR = "GOOGLE_CREDENTIALS";

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
                if (row.size() >= 4) {
                    String parti = row.get(0).toString();  // Assuming column 1 is parti
                    String fornavn = row.get(1).toString();  // Assuming column 2 is fornavn
                    String email = row.get(2).toString();  // Assuming column 4 is email
                    String storkreds = row.get(3).toString();  // Assuming column 3 is storkreds
                    String svar1 = row.size() > 4 ? row.get(4).toString() : "";  // Hvis tom, sæt til ""
                    String svar2 = row.size() > 5 ? row.get(5).toString() : "";
                    String svar3 = row.size() > 6 ? row.get(6).toString() : "";
                    String svar4 = row.size() > 7 ? row.get(7).toString() : "";
                    String svar5 = row.size() > 8 ? row.get(8).toString() : "";

                    // Create Survey object with all fields
                    surveys.add(new Survey(parti, fornavn, storkreds, email, svar1, svar2, svar3, svar4, svar5));
                }
            }
        }
        return surveys;
    }

    private Sheets getSheetsService() throws IOException, GeneralSecurityException {
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();

        // ✅ Get credentials from environment variable
        String credentialsJson = System.getenv(CREDENTIALS_ENV_VAR);
        if (credentialsJson == null || credentialsJson.isEmpty()) {
            throw new IOException("Error: GOOGLE_CREDENTIALS environment variable is missing.");
        }

        GoogleCredential credential = GoogleCredential.fromStream(new ByteArrayInputStream(credentialsJson.getBytes()))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets.readonly"));

        return new Sheets.Builder(transport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
