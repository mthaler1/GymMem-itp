package com.example.gymmem.Classes;

/**
 * Beschreibung des Programms
 *
 * @author Raphael Tarnoczi
 * @version 2023-05-31
 */
public class CurrentUser {
    private static String currentUserName;

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String currentUserName) {
        CurrentUser.currentUserName = currentUserName;
    }
}
