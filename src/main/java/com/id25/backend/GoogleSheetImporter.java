package com.id25.backend;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.*;
import com.google.api.client.http.*;
import com.google.api.client.json.*;
import com.google.api.client.json.gson.*;
import com.google.api.services.sheets.v4.*;
import com.google.api.services.sheets.v4.model.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.security.*;
import java.util.*;

@Service
public class GoogleSheetImporter {

    public GoogleSheetImporter(){
        // DI
    }

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SPREADSHEET_ID = "1B0V2vMr8baXp6PftSYF7FBAFUJGORFB1eZaMBJvviBY";
    private static final String RANGE = "valg2024!A:I"; // Define the range of cells to fetch

    // Replace with your path to the credentials file
    private static final String CREDENTIALS_FILE_PATH = "/app/google-credentials.json";

    // Public method to fetch and return Google Sheets data as list of Survey objects
    public List<Survey> importGoogleSheetData() throws GeneralSecurityException, IOException {
        // Initialize Sheets API service
        Sheets sheetsService = getSheetsService();

        // Fetch data from the sheet
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

    // Helper method to set up the Sheets API service
    private Sheets getSheetsService() throws IOException, GeneralSecurityException {
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));

        return new Sheets.Builder(transport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
