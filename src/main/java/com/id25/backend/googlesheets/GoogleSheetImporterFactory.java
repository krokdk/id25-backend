package com.id25.backend.googlesheets;

import com.id25.backend.*;
import org.springframework.stereotype.*;

@Service
public class GoogleSheetImporterFactory {

    public static String folketingsvalg2025 = "1T-aCGnZ5DTK-sF1lZyqV8wGMtsGCddm1VLBaF4fCjW8";
    public static String kommunalvalg2025 = "1NOIUhBAF9SZOqG8BQ35tj9u_MK4Rw9Ntadxh-mAD6R4";

    public DataImporter getImporter(Long year) {
        Long lookupYear = year == null ? 2024L : year;

        if (lookupYear == 2025L)
            return new GoogleSheetAggregator(folketingsvalg2025, 2025L);

        if (lookupYear == 9999L)
            return new GoogleSheetAggregator(kommunalvalg2025, 9999L);

        return new GoogleSheetImporter(lookupYear);
    }
}
