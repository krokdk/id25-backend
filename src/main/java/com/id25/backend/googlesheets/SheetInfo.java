package com.id25.backend.googlesheets;

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
