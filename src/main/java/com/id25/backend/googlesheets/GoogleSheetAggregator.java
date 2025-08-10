package com.id25.backend.googlesheets;

import com.google.api.services.sheets.v4.*;
import com.google.api.services.sheets.v4.model.*;
import com.id25.backend.dto.*;
import com.id25.backend.formatting.*;

import java.io.*;
import java.security.*;
import java.util.*;

public class GoogleSheetAggregator extends GoogleSheetImporter {

    Set<String> allePartier = Set.of("A","B","C","D","F","I","M","O","R","V","Æ","Ø","Å","U");

    String sheetId;


    private SheetInfo getSheetInfoForParty(String parti) {
        return new SheetInfo(sheetId, parti + "!A:F");
    }

    public GoogleSheetAggregator(String sheetId, Long year) {
        super(year);
        this.sheetId = sheetId;
    }

    @Override
    public List<SurveyDto> importData() throws GeneralSecurityException, IOException {

        Sheets sheetsService = getSheetsService();

        List<List<Object>> kandidater = new ArrayList<>();

        for (String parti : allePartier) {
            var partiSheetInfo = getSheetInfoForParty(parti);

            try {
                List<List<Object>> kandidaterForParti = sheetsService.spreadsheets().values()
                        .get(partiSheetInfo.getSheetId(), partiSheetInfo.getRange())
                        .execute()
                        .getValues();

                kandidater.addAll(kandidaterForParti.subList(1, kandidaterForParti.size()));
            }
            catch (Exception e)
            {
                System.out.println("Failed loading google sheet with id "+ sheetId + " for party " + parti + ": " + e.getMessage());
            }
        }

        SheetInfo surverResultSheetInfo = GoogleSheetsMapper.getSheetInfo(year);

        ValueRange responseSurveyResults = sheetsService.spreadsheets().values()
                .get(surverResultSheetInfo.getSheetId(), surverResultSheetInfo.getRange())
                .execute();

        List<SurveyDto> surveys = new ArrayList<>();
        for (var contact : kandidater){

            if (contact.size() < 5)
                continue;

            String email = getEmail(contact);

            String[] surveyAnswers = getSurveyAnswers(email, responseSurveyResults.getValues());

            SurveyDto dto = new SurveyDto();
            dto.setFornavn(contact.get(3).toString());
            dto.setParti(PartiMapper.getPartiBogstav(contact.get(0).toString()));
            dto.setValg(contact.get(1).toString());
            dto.setStorkreds(contact.get(2).toString());
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

                if ((year == 9999L || year == 8888L) && result.get(0).toString().equals(email)) //for tally har jeg skåret de tomme kolonner væk
                    return new String[]{
                            (String) result.get(1),
                            (String) result.get(2),
                            (String) result.get(3),
                            (String) result.get(4),
                            (String) result.get(5),
                    };

                if (year == 2025L && result.get(1).toString().equals(email))
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
