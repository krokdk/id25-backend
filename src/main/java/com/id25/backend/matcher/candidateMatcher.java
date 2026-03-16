package com.id25.backend.matcher;

import com.id25.backend.dto.AnswerDto;
import com.id25.backend.dto.MatchResponseDto;
import com.id25.backend.dto.SurveyDto;

import java.util.*;

public class candidateMatcher {

    private MatchResponseDto scoreCandidate(List<AnswerDto> userAnswers, SurveyDto candidate){

        List<AnswerDto> candidateAnswers = candidate.getAnswers();
        double sum = 0;

        for (int i = 0; i < userAnswers.size(); i++) {

            String uA = userAnswers.get(i).getAnswer();
            String cA = candidateAnswers.get(i).getAnswer();

            String vedIkke = "Ved ikke";

            if (uA.equals(cA)){
                sum += 1;
            } else if (uA.equals(vedIkke)) {
                sum += 0.5;
            }
        }

        Double score = (sum / userAnswers.size() * 100d);
        return new MatchResponseDto(candidate, score);
    }

    public List<MatchResponseDto> getMatches(List<AnswerDto> user, List<SurveyDto> candidates){

        return candidates.stream()
                .filter(x-> !x.getAnswers().get(0).getAnswer().equals("Ikke besvaret"))
                .map(x-> scoreCandidate(user, x))
                .sorted((o1, o2) -> Double.compare(o2.getScore(),o1.getScore()))
                .toList();

    }
}
