package com.id25.backend.googlesheets;

import java.util.HashMap;
import java.util.Map;

class GoogleSheetMapper {
    private static final Map<Long, SheetInfo> sheetMap = new HashMap<>();

    static {
        sheetMap.put(2024L, new SheetInfo("1N6z_A3eAImleBXdzky1cbLADQtUOg2fFNlR9pOiTGDs", "valg2024!A:H"));
        sheetMap.put(2022L, new SheetInfo("197fjZR2-vYLbyUc_CfmEMT5U_gIqlfuFi2SYeM4rptA", "valg2022!A:H"));
        sheetMap.put(2019L, new SheetInfo("197fjZR2-vYLbyUc_CfmEMT5U_gIqlfuFi2SYeM4rptA", "valg2019!A:D"));
    }

    public static SheetInfo getSheetInfo(Long year) {
        return sheetMap.getOrDefault(year, new SheetInfo("UKENDT_SHEET_ID", "UKENDT_RANGE"));
    }
}

class SheetInfo {
    private final String sheetId;
    private final String range;

    public SheetInfo(String sheetId, String range) {
        this.sheetId = sheetId;
        this.range = range;
    }

    public String getSheetId() {
        return sheetId;
    }

    public String getRange() {
        return range;
    }
}

