package com.id25.backend;

import java.util.*;

public class PartiMapper {
    private static final Map<String, List<String>> bogstavTilNavne = new HashMap<>();
    private static final Map<String, String> navnTilBogstav = new HashMap<>(); // Lookup-mappe

    static {
        bogstavTilNavne.put("A", Arrays.asList("A","Socialdemokratiet", "Socialdemokraterne"));
        bogstavTilNavne.put("B", Arrays.asList("B","Radikale Venstre", "Radikale"));
        bogstavTilNavne.put("C", Arrays.asList("C","Konservative Folkeparti", "Konservative"));
        bogstavTilNavne.put("F", Arrays.asList("F","Socialistisk Folkeparti", "SF"));
        bogstavTilNavne.put("H", Arrays.asList("H", "Borgernes Parti"));
        bogstavTilNavne.put("I", Arrays.asList("I", "Liberal Alliance"));
        bogstavTilNavne.put("M", Arrays.asList("M", "Moderaterne"));
        bogstavTilNavne.put("O", Arrays.asList("O", "Dansk Folkeparti", "DF"));
        bogstavTilNavne.put("V", Arrays.asList("V", "Venstre"));
        bogstavTilNavne.put("Æ", Arrays.asList("Æ", "Danmarksdemokraterne "));
        bogstavTilNavne.put("Ø", Arrays.asList("Ø", "Enhedslisten"));
        bogstavTilNavne.put("Å", Arrays.asList("Å", "Alternativet"));

        // Opret lookup-mappe automatisk
        for (Map.Entry<String, List<String>> entry : bogstavTilNavne.entrySet()) {
            String bogstav = entry.getKey();
            for (String navn : entry.getValue()) {
                navnTilBogstav.put(navn, bogstav);
            }
        }
    }

    static String getPartiBogstav(String partiNavn) {
        return navnTilBogstav.getOrDefault(partiNavn, "UKENDT");
    }
}
