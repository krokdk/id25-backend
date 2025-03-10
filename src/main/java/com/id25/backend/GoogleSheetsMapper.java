package com.id25.backend;

import java.util.HashMap;
import java.util.Map;

class GoogleSheetMapper {
    private static final Map<Long, SheetInfo> sheetMap = new HashMap<>();

    static {

        //
        sheetMap.put(2024L, new SheetInfo("1N6z_A3eAImleBXdzky1cbLADQtUOg2fFNlR9pOiTGDs", "tablepressview2!A:H"));
        sheetMap.put(2023L, new SheetInfo("17Ys5TtNURFctkaLXRkPeJOpwjqh4WjwqgEbseDy9ps0", "tablepress2!A:H"));
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

