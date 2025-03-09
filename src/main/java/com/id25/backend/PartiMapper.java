package com.id25.backend;

import java.util.HashMap;
import java.util.Map;

public class PartiMapper {

    private static final Map<String, String> partiNavnTilBogstav = new HashMap<>();

    static {
        partiNavnTilBogstav.put("Socialdemokratiet", "A");
        partiNavnTilBogstav.put("Radikale Venstre", "B");
        partiNavnTilBogstav.put("Konservative Folkeparti", "C");
        partiNavnTilBogstav.put("Socialistisk Folkeparti", "F");
        partiNavnTilBogstav.put("Liberal Alliance", "I");
        partiNavnTilBogstav.put("Moderaterne", "M");
        partiNavnTilBogstav.put("Dansk Folkeparti", "O");
        partiNavnTilBogstav.put("Venstre", "V");
        partiNavnTilBogstav.put("Danmarksdemokraterne", "Æ");
        partiNavnTilBogstav.put("Enhedslisten", "Ø");
        partiNavnTilBogstav.put("Alternativet", "Å");
    }

    /**
     * Konverterer et partinavn til dets bogstavsrepræsentation.
     * @param partiNavn Det fulde navn på partiet (f.eks. "Konservative Folkeparti")
     * @return Bogstavsrepræsentationen (f.eks. "C"), eller null hvis ikke fundet.
     */
    public static String getPartiBogstav(String partiNavn) {
        return partiNavnTilBogstav.getOrDefault(partiNavn, partiNavn); // Behold original hvis ikke fundet
    }
}

