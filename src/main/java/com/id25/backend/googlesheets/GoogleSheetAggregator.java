package com.id25.backend.googlesheets;

import com.google.api.services.sheets.v4.*;
import com.google.api.services.sheets.v4.model.*;
import com.id25.backend.dto.*;

import java.io.*;
import java.security.*;
import java.util.*;


// kald denne fra en test
public class GoogleSheetAggregator extends GoogleSheetImporter {
//https://docs.google.com/spreadsheets/d/1T-aCGnZ5DTK-sF1lZyqV8wGMtsGCddm1VLBaF4fCjW8/edit?usp=sharing
    SheetInfo contactSheetInfo = new SheetInfo("1T-aCGnZ5DTK-sF1lZyqV8wGMtsGCddm1VLBaF4fCjW8", "F!A:F");

    private SheetInfo getSheetInfoForParty(String parti) {
        return new SheetInfo("1T-aCGnZ5DTK-sF1lZyqV8wGMtsGCddm1VLBaF4fCjW8", parti + "!A:F");
    }

    //https://docs.google.com/spreadsheets/d/1AAeZoCS3K1sFiJqaeuYDktUL-xf2omBXIxs-_ZzfcRg/edit?usp=sharing
    SheetInfo surverResultSheetInfo = new SheetInfo("1AAeZoCS3K1sFiJqaeuYDktUL-xf2omBXIxs-_ZzfcRg", "svar!A:I");

    public GoogleSheetAggregator(Long year) {
        super(year);
    }


    @Override
    public List<SurveyDto> importData() throws GeneralSecurityException, IOException {

        Sheets sheetsService = getSheetsService();


        var allePartier = Set.of("A","B","C","D","F","I","M","O","V","Æ","Ø","Å");


        List<List<Object>> kandidater = new ArrayList<>();

        for (String parti : allePartier) {
            var partiSheetInfo = getSheetInfoForParty(parti);

            List<List<Object>> kandidaterForParti = sheetsService.spreadsheets().values()
                    .get(partiSheetInfo.getSheetId(), partiSheetInfo.getRange())
                    .execute()
                    .getValues();

            kandidater.addAll(kandidaterForParti.subList(1,kandidaterForParti.size()));
        }

        ValueRange responseSurveyResults = sheetsService.spreadsheets().values()
                .get(surverResultSheetInfo.getSheetId(), surverResultSheetInfo.getRange())
                .execute();


        List<SurveyDto> surveys = new ArrayList<>();
        for (var contact : kandidater){

            String email = getEmail(contact);

            String[] surveyAnswers = getSurveyAnswers(email, responseSurveyResults.getValues());


            SurveyDto foo = new SurveyDto();
            foo.setFornavn(contact.get(3).toString());
            foo.setParti(contact.get(0).toString());
            foo.setStorkreds(contact.get(1).toString());
            foo.setSvar1(surveyAnswers[0]);
            foo.setSvar2(surveyAnswers[1]);
            foo.setSvar3(surveyAnswers[2]);
            foo.setSvar4(surveyAnswers[3]);
            foo.setSvar5(surveyAnswers[4]);

            surveys.add(foo);
        }


        return surveys;
    }

    private String[] getSurveyAnswers(String email, List<List<Object>> responseSurveyResults) {

        for (List<Object> result : responseSurveyResults) {

            if (result.get(1) != null && result.get(1).toString().equals(email)) {

                return new String[]{
                        (String) result.get(4),
                        (String) result.get(5),
                        (String) result.get(6),
                        (String) result.get(7),
                        (String) result.get(8),
                };
            }
        }
        return new String[]{"", "", "", "", ""};
    }

    private String getEmail(List<Object> contact) {
        return (String) contact.get(4);
    }
}
