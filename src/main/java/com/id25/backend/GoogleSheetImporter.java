package com.id25.backend;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.*;
import com.google.api.client.http.*;
import com.google.api.client.json.*;
import com.google.api.client.json.gson.*;
import com.google.api.services.sheets.v4.*;
import com.google.api.services.sheets.v4.model.*;

import java.io.*;
import java.security.*;
import java.util.*;

public class GoogleSheetImporter {

    public GoogleSheetImporter(){
        // DI
    }

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SPREADSHEET_ID = "your-google-sheet-id"; // Replace with your Google Sheet ID
    private static final String RANGE = "Sheet1!A1:B"; // Define the range of cells to fetch

    // Replace with your path to the credentials file
    private static final String CREDENTIALS_FILE_PATH = "google-credentials.json";

    // Public method to fetch and return Google Sheets data as list of Survey objects
    public List<Survey> importGoogleSheetData() throws IOException, GeneralSecurityException {
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
                if (row.size() >= 7) {
                    String parti = row.get(0).toString();  // Assuming column 1 is parti
                    String fornavn = row.get(1).toString();  // Assuming column 2 is fornavn
                    String storkreds = row.get(2).toString();  // Assuming column 3 is storkreds
                    String email = row.get(3).toString();  // Assuming column 4 is email
                    String svar1 = row.get(4).toString();  // Assuming column 5 is svar1
                    String svar2 = row.get(5).toString();  // Assuming column 6 is svar2
                    String svar3 = row.get(6).toString();  // Assuming column 7 is svar3
                    String svar4 = row.get(7).toString();  // Assuming column 8 is svar4
                    String svar5 = row.get(8).toString();  // Assuming column 9 is svar5
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
