package com.id25.backend.googlesheets;

import com.id25.backend.*;
import org.springframework.stereotype.*;

@Service
public class GoogleSheetImporterFactory {

    public DataImporter getImporter(Long year) {
        Long lookupYear = year == null ? 2024L : year;

        if (year == 2025L)
            return new GoogleSheetAggregator(0L);

        return new GoogleSheetImporter(lookupYear);
    }
}
