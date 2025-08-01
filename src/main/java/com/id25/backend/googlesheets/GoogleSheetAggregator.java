package com.id25.backend.googlesheets;

import com.google.api.services.sheets.v4.*;
import com.google.api.services.sheets.v4.model.*;
import com.id25.backend.dto.*;
import com.id25.backend.formatting.*;

import java.io.*;
import java.security.*;
import java.util.*;

public class GoogleSheetAggregator extends GoogleSheetImporter {

    Set<String> allePartier = Set.of("A","B","C","D","F","I","M","O","V","Æ","Ø","Å","U");

    private SheetInfo getSheetInfoForParty(String parti) {

      // denne sheet id burde ikke ligge her i klassen.
        return new SheetInfo("1T-aCGnZ5DTK-sF1lZyqV8wGMtsGCddm1VLBaF4fCjW8", parti + "!A:F");
    }

    public GoogleSheetAggregator(Long year) {
        super(year);
    }

    @Override
    public List<SurveyDto> importData() throws GeneralSecurityException, IOException {

        Sheets sheetsService = getSheetsService();

        List<List<Object>> kandidater = new ArrayList<>();

        for (String parti : allePartier) {
            var partiSheetInfo = getSheetInfoForParty(parti);

            List<List<Object>> kandidaterForParti = sheetsService.spreadsheets().values()
                    .get(partiSheetInfo.getSheetId(), partiSheetInfo.getRange())
                    .execute()
                    .getValues();

            kandidater.addAll(kandidaterForParti.subList(1, kandidaterForParti.size()));
        }

        SheetInfo surverResultSheetInfo = GoogleSheetsMapper.getSheetInfo(year);

        ValueRange responseSurveyResults = sheetsService.spreadsheets().values()
                .get(surverResultSheetInfo.getSheetId(), surverResultSheetInfo.getRange())
                .execute();

        List<SurveyDto> surveys = new ArrayList<>();
        for (var contact : kandidater){

            String email = getEmail(contact);

            String[] surveyAnswers = getSurveyAnswers(email, responseSurveyResults.getValues());

            SurveyDto dto = new SurveyDto();
            dto.setFornavn(contact.get(3).toString());
            dto.setParti(PartiMapper.getPartiBogstav(contact.get(0).toString()));
            dto.setStorkreds(contact.get(1).toString());
            dto.setUrl(getUrl(contact));
            dto.setSvar1(surveyAnswers[0]);
            dto.setSvar2(surveyAnswers[1]);
            dto.setSvar3(surveyAnswers[2]);
            dto.setSvar4(surveyAnswers[3]);
            dto.setSvar5(surveyAnswers[4]);

            surveys.add(dto);
        }

        return surveys;
    }

    private String getUrl(List<Object> contact) {

        if (contact.size() > 5)
            return (String) contact.get(5);

        return "";
    }

    private String[] getSurveyAnswers(String email, List<List<Object>> responseSurveyResults) {

        for (List<Object> result : responseSurveyResults) {

            if (result.get(1) != null) {

                if (year == 9999L && result.get(0).toString().equals(email)) //for tally har jeg skåret de tomme kolonner væk
                    return new String[]{
                            (String) result.get(1),
                            (String) result.get(2),
                            (String) result.get(3),
                            (String) result.get(4),
                            (String) result.get(5),
                    };

                if (result.get(1).toString().equals(email))
                    return new String[]{
                            (String) result.get(4),
                            (String) result.get(5),
                            (String) result.get(6),
                            (String) result.get(7),
                            (String) result.get(8),
                    };
            }
        }

        String ikkeBesvaret = "Ikke besvaret";

        return new String[]{ikkeBesvaret, ikkeBesvaret, ikkeBesvaret, ikkeBesvaret, ikkeBesvaret};
    }

    private String getEmail(List<Object> contact) {
        return (String) contact.get(4);
    }
}
