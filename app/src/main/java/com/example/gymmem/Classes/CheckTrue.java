package com.example.gymmem.Classes;

public class CheckTrue {
    /**
     * Checkt, ob Umlaute im Parameter enthalten sind und zählt diese
     * @param text Text
     * @return Anzahl der Umlaute
     */
    public static int umlaute(String text) {
        int anzahlUmlaute = 0;
        for(int i = 0; i < text.length();i++) {
            if (text.charAt(i) == 'ä' || text.charAt(i) == 'ö' || text.charAt(i) == 'ü' || text.charAt(i) == 'Ä' || text.charAt(i) == 'Ö' || text.charAt(i) == 'Ü') {
                anzahlUmlaute++;
            }
        }
        return anzahlUmlaute;
    }

    /**
     * Checkt, ob ungültige Zeichen enthalten sind und wirft eine Exception
     * @param text Text
     */
    public static void ungueltigeZeichen(String text) {
        boolean gueltig = true;
        int umlaute = umlaute(text);
        for(int i = 0; i < text.length();i++) {
            char buchstabe = text.charAt(i);
            if(umlaute >= 1) {
                continue;
            }
            if(!(buchstabe >= '!' && buchstabe <= '~')) {
                gueltig = false;
            }
        }
        if(!gueltig) {
            throw new IllegalArgumentException("Keine gültigen Zeichen verwendet!");
        }
    }
}
