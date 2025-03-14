package com.id25.backend.formatting;

public class NameFormatter {
    public static String formatName(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ""; // Return empty string if input is null or blank
        }

        StringBuilder formattedName = new StringBuilder();
        boolean capitalizeNext = true; // Capitalize the first letter

        for (char c : input.toLowerCase().toCharArray()) {
            if (capitalizeNext && Character.isLetter(c)) {
                formattedName.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                formattedName.append(c);
            }

            if (c == ' ' || c == '-') { // Capitalize next letter after space or hyphen
                capitalizeNext = true;
            }
        }

        return formattedName.toString();
    }
}

