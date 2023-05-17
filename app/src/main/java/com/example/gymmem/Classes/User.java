package com.example.gymmem.Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class User {
    private String email;
    private String username;
    private String passwort;

    private UUID userID;
    private Map<UUID, Training> trainings = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && username.equals(user.username) && passwort.equals(user.passwort) && trainings.equals(user.trainings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, passwort, trainings);
    }

    public User(String email, String username, String passwort) {
        setEmail(email);
        setUsername(username);
        setPasswort(passwort);
        this.userID = UUID.randomUUID();
    }

    public void setTrainings(HashMap<UUID, Training> trainings) {
        this.trainings = trainings;
    }

    public Map getTrainings() {
        return this.trainings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (checkEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Die Email-Adresse ist nicht gültig!");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        CheckTrue.ungueltigeZeichen(username);
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("Der Username ist nicht gültig");
        }
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        int pwStaerke = checkPasswordStrength(passwort);
        if(pwStaerke<2) {
            throw new IllegalArgumentException("Das Passwort muss mindestens 8 Zeichen lang sein und aus mindestens " +
                    "drei Zeichengruppen bestehen");

        }
        this.passwort = passwort;
    }

    public UUID getUserID() {
        return userID;
    }


    private static boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();

    }

    public static int checkPasswordStrength(String pw) {
        ungueltigeZeichen(pw);
        int strength = 0;
        if(pw.length() < 8) {
            return strength;
        }
        if(pw.length() <= 15) {
            strength += 1;
        }
        if(pw.length() >= 16) {
            strength += 2;
        }
        int anzahlZeiGr = 0;
        boolean abzug = false;
        int zeichengruppe = zeichengruppe(pw, 97,122);
        if(zeichengruppe >= 1) {
            anzahlZeiGr += 1;
        }
        zeichengruppe = zeichengruppe(pw, 65,90);
        if(zeichengruppe >= 1) {
            anzahlZeiGr += 1;
            if(zeichengruppe == 1) {
                abzug = true;
            }
        }
        zeichengruppe = zeichengruppe(pw, 48,57);
        if(zeichengruppe >= 1) {
            anzahlZeiGr += 1;
            if(zeichengruppe == 1) {
                abzug = true;
            }
        }
        zeichengruppe = zeichengruppe(pw, 33,47);
        if(zeichengruppe >= 1) {
            anzahlZeiGr += 1;
            if(zeichengruppe == 1) {
                abzug = true;
            }
        }
        if(umlaute(pw) >= 1) {
            anzahlZeiGr += 1;
            if(umlaute(pw) == 1) {
                abzug = true;
            }
        }
        if(anzahlZeiGr >= 3) {
            strength += 1;
        }
        if(anzahlZeiGr >= 4) {
            strength += 1;
        }
        if(anzahlZeiGr >= 5) {
            strength += 1;
        }
        if(abzug) {
            strength--;
        }
        return strength;
    }

    public static int zeichengruppe(String pw, int char1, int char2) {
        int anzahlZ = 0;
        for(int i = 0; i < pw.length();i++) {
            char buchstabe = pw.charAt(i);
            if(buchstabe >= char1 && buchstabe <= char2) {
                anzahlZ += 1;
            }
        }
        return anzahlZ;
    }

    public static int umlaute(String pw) {
        int anzahlUmlaute = 0;
        for(int i = 0; i < pw.length();i++) {
            if (pw.charAt(i) == 'ä' || pw.charAt(i) == 'ö' || pw.charAt(i) == 'ü' || pw.charAt(i) == 'Ä' || pw.charAt(i) == 'Ö' || pw.charAt(i) == 'Ü') {
                anzahlUmlaute++;
            }
        }
        return anzahlUmlaute;
    }

    public static void ungueltigeZeichen(String pw) {
        boolean gueltig = true;
        int umlaute = umlaute(pw);
        for(int i = 0; i < pw.length();i++) {
            char buchstabe = pw.charAt(i);
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