package com.id25.backend.googlesheets;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.id25.backend.dto.AnswerDto;
import com.id25.backend.dto.SurveyDto;
import com.id25.backend.formatting.NameFormatter;
import com.id25.backend.formatting.PartiMapper;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GoogleSheetAggregator2026 extends GoogleSheetAggregator {


    private SheetInfo getSheetInfoForParty() {
        return new SheetInfo(sheetId, "kandidater" + "!A:K");
    }

    public GoogleSheetAggregator2026(String sheetId) {
        super(sheetId, 2026L);
    }

    @Override
    public List<SurveyDto> importData() throws GeneralSecurityException, IOException {

        Sheets sheetsService = getSheetsService();

        List<List<Object>> kandidater = new ArrayList<>();


        var partiSheetInfo = getSheetInfoForParty();

        try {
            List<List<Object>> kandidaterForParti = sheetsService.spreadsheets().values()
                    .get(partiSheetInfo.getSheetId(), partiSheetInfo.getRange())
                    .execute()
                    .getValues();

            kandidater.addAll(kandidaterForParti.subList(1, kandidaterForParti.size()));
        } catch (Exception e) {
            System.out.println("Failed loading google sheet with id " + sheetId + " : " + e.getMessage());
        }

        SheetInfo surverResultSheetInfo = GoogleSheetsMapper.getSheetInfo(year);

        ValueRange responseSurveyResults = sheetsService.spreadsheets().values()
                .get(surverResultSheetInfo.getSheetId(), surverResultSheetInfo.getRange())
                .execute();

        List<SurveyDto> surveys = new ArrayList<>();
        for (var contact : kandidater) {

            //0: UID	1: Parti	2: Storkreds	3: Kreds	4: Navn	5: email	6: hjemmeside
//0: TrackingID	1: Navn	2: Parti	3: Kreds	4: Mail 	5: Telefon	6:Fornavn	7: Mellemnavn 8: Efternavn	9:BilledeURL	10:Hjemmeside

            if (contact.size() < 6)
                continue;

            String uid = contact.get(0).toString();
            String email = contact.get(4).toString();

            SurveyDto dto = new SurveyDto();
            dto.setFornavn(NameFormatter.formatName(contact.get(1).toString()));
            dto.setParti(PartiMapper.getPartiBogstav(contact.get(2).toString()));
            dto.setValg("Folketingsvalg 2026");
            dto.setStorkreds(contact.get(3).toString());
            if (contact.size() > 10) {
                dto.setUrl(contact.get(10).toString());
            }
            dto.setEmail(email);

            var answers = getSurveyAnswersRaw(responseSurveyResults, uid);

            dto.setAnswers(getSurveyAnswers(answers));

            dto.setComment(getComment(answers));
            surveys.add(dto);
        }

        return surveys;
    }

    private String getComment(List<Object> answers) {

        if (!answers.isEmpty() && answers.stream().count() > 3) {
            return answers.get(4).toString();
        }
        return "";
    }

    private List<Object> getSurveyAnswersRaw(ValueRange responseSurveyResults, String uid) {
        for (List<Object> result : responseSurveyResults.getValues()) {

            if (result.get(0) != null) {
                if (result.get(0).toString().equals(uid)) {
                    return result;
                }
            }
        }
        return new ArrayList<>();
    }

    private List<AnswerDto> getSurveyAnswers(List<Object> result) {
        List<AnswerDto> answers = new ArrayList<>();

        for (int i = 3; i < 17; i++) {
            if (!result.isEmpty() && result.get(0) != null) {

                // håndter at komment til sidst kan være tom
                var answer = (String) result.get(2 * i-1);
                var comment = result.size()< i ? (String) result.get(2 * i) : "";
                answers.add(new AnswerDto("Spm" + (i-2), answer, comment));
            } else {
                answers.add(new AnswerDto("Spm" + (i), ikkeBesvaret, ""));
            }
        }
        return answers;
    }
}
