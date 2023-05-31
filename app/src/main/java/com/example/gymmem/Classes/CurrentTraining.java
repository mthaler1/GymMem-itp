package com.example.gymmem.Classes;

import java.util.UUID;

public class CurrentTraining {
    private static String id;

    public static String getCurrentTraining() {
        return id;
    }

    public static void setCurrentTraining(String id) {
        CurrentTraining.id = id;
    }
}
