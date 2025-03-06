package com.id25.backend;

import java.io.*;
import java.util.*;

public class SurveyLoader {

    public static List<Survey> loadSurveyData(String filename) {
        List<Survey> surveyList = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                // Format: "O", "Lars", "Nordjylland", "lars@ø.dk", "nej", "nej", "ja", "nej", "Spændende"
                line = line.trim();
                if (line.startsWith("\"") && line.endsWith("\"")) {
                    // Remove surrounding quotes and split the line by "\", \""
                    String[] parts = line.substring(1, line.length() - 1).split("\", \"");

                    if (parts.length == 9) {
                        String firstEntry = parts[0];
                        String firstName = parts[1];
                        String region = parts[2];
                        String email = parts[3];
                        String answer1 = parts[4];
                        String answer2 = parts[5];
                        String answer3 = parts[6];
                        String answer4 = parts[7];
                        String sentence = parts[8];

                        Survey survey = new Survey(firstEntry, firstName, region, email, answer1, answer2, answer3, answer4, sentence);
                        surveyList.add(survey);
                    }
                }
            }
        } catch (IOException e) {
            // Log error (you could also print the stack trace or handle the error differently)
            System.err.println("Error reading file: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing reader: " + e.getMessage());
            }
        }

        return surveyList;
    }

//    public static void main(String[] args) {
//        List<Survey> surveys = loadSurveyData("survey_data.txt");
//        // You can now use the `surveys` list as needed
//        surveys.forEach(survey -> {
//            System.out.println(survey.getFornavn() + " - " + survey.getParti());
//        });
//    }
}
