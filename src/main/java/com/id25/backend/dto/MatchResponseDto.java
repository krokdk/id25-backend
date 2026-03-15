package com.id25.backend.dto;

public class MatchResponseDto {
    SurveyDto candidate;

    public MatchResponseDto(SurveyDto candidate, Double score) {
        this.score = score;
        this.candidate = candidate;
    }

    public void setCandidate(SurveyDto candidate) {
        this.candidate = candidate;
    }

    public SurveyDto getCandidate() {
        return candidate;
    }

    Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
