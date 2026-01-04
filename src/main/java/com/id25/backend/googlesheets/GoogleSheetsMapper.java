package com.id25.backend.googlesheets;

import java.util.HashMap;
import java.util.Map;

class GoogleSheetsMapper {
    private static final Map<Long, SheetInfo> sheetMap = new HashMap<>();

    static {
        sheetMap.put(9999L, new SheetInfo("1AAeZoCS3K1sFiJqaeuYDktUL-xf2omBXIxs-_ZzfcRg", "kommunalvalg2025!A:F"));
        sheetMap.put(8888L, new SheetInfo("1AAeZoCS3K1sFiJqaeuYDktUL-xf2omBXIxs-_ZzfcRg", "regionsrådsvalg2025!A:F"));
        sheetMap.put(2025L, new SheetInfo("1AAeZoCS3K1sFiJqaeuYDktUL-xf2omBXIxs-_ZzfcRg", "svar!A:I"));
        sheetMap.put(2024L, new SheetInfo("1N6z_A3eAImleBXdzky1cbLADQtUOg2fFNlR9pOiTGDs", "valg2024!A:J"));
        sheetMap.put(2022L, new SheetInfo("197fjZR2-vYLbyUc_CfmEMT5U_gIqlfuFi2SYeM4rptA", "valg2022!A:J"));
        sheetMap.put(2021L, new SheetInfo("197fjZR2-vYLbyUc_CfmEMT5U_gIqlfuFi2SYeM4rptA", "afstemning2021!A:D"));
        sheetMap.put(2019L, new SheetInfo("197fjZR2-vYLbyUc_CfmEMT5U_gIqlfuFi2SYeM4rptA", "valg2019!A:D"));
    }

    public static SheetInfo getSheetInfo(Long year) {
        return sheetMap.getOrDefault(year, new SheetInfo("UKENDT_SHEET_ID", "UKENDT_RANGE"));
    }
}

