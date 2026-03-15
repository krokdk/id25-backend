package com.id25.backend.dto;

import java.util.List;

public class MatchDto {
    String storkreds;

    public String getStorkreds() {
        return storkreds;
    }

    public void setStorkreds(String storkreds) {
        this.storkreds = storkreds;
    }

    AnswerDto[] answers;

    public void setAnswers(AnswerDto[] answers) {
        this.answers = answers;
    }

    public AnswerDto[] getAnswers() {
        return answers;
    }
}
