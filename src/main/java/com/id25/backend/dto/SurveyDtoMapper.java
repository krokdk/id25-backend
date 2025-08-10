package com.id25.backend.dto;

import com.id25.backend.domainModel.*;

import java.util.*;

public class SurveyDtoMapper {

    public static SurveyDto mapToDto(Kandidat kandidat, Integer year){

        var surveyResult =kandidat.SurveyResults.get(year);

        return new SurveyDto(
                surveyResult.surveyData.Parti,
                kandidat.Name,
                surveyResult.surveyData.Storkreds,
                surveyResult.surveyData.Valg,
                surveyResult.results.get(0),
                surveyResult.results.get(1),
                surveyResult.results.get(2),
                surveyResult.results.get(3),
                surveyResult.results.get(4)
        );
    }

}
