package com.id25.backend;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

class GoogleSheetImporterTest {

    @Mock
    private Sheets sheetsService; // Mocked Sheets API service

    @Mock
    private Sheets.Spreadsheets.Values mockValues; // Mocking Values interface

    @Mock
    private Sheets.Spreadsheets mockSpreadsheets; // Mocking Spreadsheets interface

    @InjectMocks
    private GoogleSheetImporter googleSheetImporter; // Class under test

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Mocking Sheets API behavior
        when(sheetsService.spreadsheets()).thenReturn(mockSpreadsheets);
        when(mockSpreadsheets.values()).thenReturn(mockValues);
    }

    @Test
    void testImportGoogleSheetData() throws IOException, GeneralSecurityException {
        // Sample mock data returned by API
        ValueRange mockValueRange = new ValueRange().setValues(Arrays.asList(
                Arrays.asList("Name", "Age", "City"), // Header row
                Arrays.asList("Alice", "25", "New York"),
                Arrays.asList("Bob", "30", "San Francisco")
        ));

        // Define mock behavior when API is called
        when(mockValues.get(anyString(), anyString())).thenReturn(mock(Sheets.Spreadsheets.Values.Get.class));
        when(mockValues.get(anyString(), anyString()).execute()).thenReturn(mockValueRange);

        // Call the method
        List<Survey> result = googleSheetImporter.importGoogleSheetData();

        // Assertions
        assertNotNull(result);
        assertEquals(3, result.size()); // 3 rows (including header)
        assertEquals(Arrays.asList("Alice", "25", "New York"), result.get(1)); // Verify second row
    }
}
