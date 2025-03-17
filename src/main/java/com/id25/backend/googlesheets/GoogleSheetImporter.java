package com.id25.backend.googlesheets;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.*;
import com.google.api.client.http.*;
import com.google.api.client.json.*;
import com.google.api.client.json.gson.*;
import com.google.api.services.sheets.v4.*;
import com.google.api.services.sheets.v4.model.*;
import com.id25.backend.*;
import com.id25.backend.formatting.*;

import java.io.*;
import java.security.*;
import java.util.*;

public class GoogleSheetImporter {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    // ✅ Read credentials from Render environment variable
    private static final String CREDENTIALS_ENV_VAR = "GOOGLE_CREDENTIALS";

    // 🔹 Filsti til credentials i lokal udvikling
    private static final String CREDENTIALS_FILE_PATH = "google-credentials.json";
    private final Long year;

    public GoogleSheetImporter(Long year) {

        this.year = year;
    }

    public List<Survey> importGoogleSheetData() throws GeneralSecurityException, IOException {
        SheetInfo sheetInfo =GoogleSheetMapper.getSheetInfo(year);

        Sheets sheetsService = getSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(sheetInfo.getSheetId(), sheetInfo.getRange())
                .execute();

        List<List<Object>> values = response.getValues();
        List<Survey> surveys = new ArrayList<>();

        if (values != null && !values.isEmpty()) {
            for (List<Object> row : values) {
                if (row.size() >= 3) {
                    String parti = PartiMapper.getPartiBogstav(row.get(1).toString());  // Assuming column 1 is parti
                    String fornavn = NameFormatter.formatName(row.get(0).toString());  // Assuming column 2 is fornavn
                    String storkreds = row.get(2).toString();  // Assuming column 3 is storkreds
                    String svar1 = row.size() > 3 ? row.get(3).toString() : "";
                    String svar2 = row.size() > 4 ? row.get(4).toString() : "";
                    String svar3 = row.size() > 5 ? row.get(5).toString() : "";
                    String svar4 = row.size() > 6 ? row.get(6).toString() : "";
                    String svar5 = row.size() > 7 ? row.get(7).toString() : "";

                    // Create Survey object with all fields
                    if (year == 2019L){
                        if (svar1 == "")
                            continue;

                        surveys.add(new Survey(parti, fornavn, storkreds, "", svar1, "", "", ""));
                    } else if (year == 2020L) {
                        if (svar1 == "")
                            continue;

                        surveys.add(new Survey(parti, fornavn, "", "", svar1, "", "", ""));

                    } else {
                        surveys.add(new Survey(parti, fornavn, storkreds, replaceEmptyString(svar1), replaceEmptyString(svar2), replaceEmptyString(svar3), replaceEmptyString(svar4), svar5));
                    }
                }
            }
        }
        return surveys;
    }

    private String replaceEmptyString(String input){
        if (input == "")
            return "Ikke besvaret";

        return input;
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
