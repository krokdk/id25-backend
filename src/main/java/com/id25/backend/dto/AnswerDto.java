package com.id25.backend.dto;

public class AnswerDto {

    private String question;
    private String answer;
    private String comment;

    public AnswerDto() {
    }

    public AnswerDto(String question, String answer, String comment) {
        this.question = question;
        this.answer = answer;
        this.comment = comment;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
