package com.example.gymmem.Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class User {
    private String email;
    private String username;
    private String passwort;
    private UUID userID;
    private Map<UUID,Training> = new HashMap<>;

    public User(String email, String username,String passwort) {
        setEmail(email);
        setUsername(username);
        setPasswort(passwort);
        this.userID = UUID.randomUUID();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(checkEmail(email)) {
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Die Email-Adresse ist nicht gültig!");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username==null || username.equals("")) {
            throw new IllegalArgumentException("Der Username ist nicht gültig");
        }
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public UUID getUserID() {
        return userID;
    }


    private boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();

    }

}