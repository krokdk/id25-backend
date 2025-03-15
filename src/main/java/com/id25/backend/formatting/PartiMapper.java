package com.id25.backend.formatting;

import java.util.*;

public class PartiMapper {
    private static final Map<String, List<String>> bogstavTilNavne = new HashMap<>();
    private static final Map<String, String> navnTilBogstav = new HashMap<>(); // Lookup-mappe

    static {
        bogstavTilNavne.put("A", Arrays.asList("A", "Socialdemokratiet", "Socialdemokraterne", "S"));
        bogstavTilNavne.put("B", Arrays.asList("B", "Radikale Venstre", "Radikale", "RV"));
        bogstavTilNavne.put("C", Arrays.asList("C", "Konservative Folkeparti", "Konservative", "KF"));
        bogstavTilNavne.put("D", Arrays.asList("D", "Nye Borgerlige", "NB"));
        bogstavTilNavne.put("F", Arrays.asList("F", "Socialistisk Folkeparti", "SF"));
        bogstavTilNavne.put("H", Arrays.asList("H", "Borgernes Parti"));
        bogstavTilNavne.put("I", Arrays.asList("I", "Liberal Alliance", "LA"));
        bogstavTilNavne.put("K", Arrays.asList("K", "Kristendemokratern", "KD"));
        bogstavTilNavne.put("M", Arrays.asList("M", "Moderaterne"));
        bogstavTilNavne.put("O", Arrays.asList("O", "Dansk Folkeparti", "DF"));
        bogstavTilNavne.put("V", Arrays.asList("V", "Venstre"));
        bogstavTilNavne.put("Æ", Arrays.asList("Æ", "Danmarksdemokraterne"));
        bogstavTilNavne.put("Ø", Arrays.asList("Ø", "Enhedslisten", "EL"));
        bogstavTilNavne.put("Å", Arrays.asList("Å", "Alternativet", "ALT"));
        bogstavTilNavne.put("NA", Arrays.asList("Nordatlantiske mandater", "JF", "SIU", "SP", "IA"));
        bogstavTilNavne.put("LØ", Arrays.asList("UFG", "Uden for folketingsgrupperne"));


        // Opret lookup-mappe automatisk
        for (Map.Entry<String, List<String>> entry : bogstavTilNavne.entrySet()) {
            String bogstav = entry.getKey();
            for (String navn : entry.getValue()) {
                navnTilBogstav.put(navn, bogstav);
            }
        }
    }

    public static String getPartiBogstav(String partiNavn) {
        return navnTilBogstav.getOrDefault(partiNavn, "UKENDT");
    }
}
