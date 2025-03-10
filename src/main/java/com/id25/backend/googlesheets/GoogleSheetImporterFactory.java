package com.id25.backend.googlesheets;

import org.springframework.stereotype.*;

@Service
public class GoogleSheetImporterFactory {

    public GoogleSheetImporter getImporter(Long year) {
        Long lookupYear = year == null ? 2024L : year;
        return new GoogleSheetImporter(lookupYear);
    }
}
