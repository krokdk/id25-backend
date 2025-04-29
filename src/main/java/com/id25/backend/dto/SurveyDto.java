package com.id25.backend.dto;

public class SurveyDto {
    private String parti;
    private String fornavn;
    private String storkreds;
    private String email;
    private String svar1;
    private String svar2;
    private String svar3;
    private String svar4;
    private String svar5;

    // Constructor with all fields
    public SurveyDto(String parti, String fornavn, String storkreds, String svar1, String svar2, String svar3, String svar4, String svar5) {
        this.parti = parti;
        this.fornavn = fornavn;
        this.storkreds = storkreds;
        this.svar1 = svar1;
        this.svar2 = svar2;
        this.svar3 = svar3;
        this.svar4 = svar4;
        this.svar5 = svar5;
    }

    // Getters and Setters
    public String getParti() {
        return parti;
    }

    public void setParti(String parti) {
        this.parti = parti;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getStorkreds() {
        return storkreds;
    }

    public void setStorkreds(String storkreds) {
        this.storkreds = storkreds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSvar1() {
        return svar1;
    }

    public void setSvar1(String svar1) {
        this.svar1 = svar1;
    }

    public String getSvar2() {
        return svar2;
    }

    public void setSvar2(String svar2) {
        this.svar2 = svar2;
    }

    public String getSvar3() {
        return svar3;
    }

    public void setSvar3(String svar3) {
        this.svar3 = svar3;
    }

    public String getSvar4() {
        return svar4;
    }

    public void setSvar4(String svar4) {
        this.svar4 = svar4;
    }

    public String getSvar5() {
        return svar5;
    }

    public void setSvar5(String svar5) {
        this.svar5 = svar5;
    }

    // Updated toString() method
    @Override
    public String toString() {
        return "Survey{" +
                "parti='" + parti + '\'' +
                ", fornavn='" + fornavn + '\'' +
                ", storkreds='" + storkreds + '\'' +
                ", email='" + email + '\'' +
                ", svar1='" + svar1 + '\'' +
                ", svar2='" + svar2 + '\'' +
                ", svar3='" + svar3 + '\'' +
                ", svar4='" + svar4 + '\'' +
                ", svar5='" + svar5 + '\'' +
                '}';
    }
}
