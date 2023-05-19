package com.example.gymmem.Classes;

import com.example.gymmem.Login;

public class Exercise {
    private ExerciseType type;
    private String name;

    private String user;



    public Exercise(String name, ExerciseType type) {
        this.setName(name);
        this.setType(type);
        this.setUser(Login.getCurrentUserName());
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setName(String name) {
        CheckTrue.ungueltigeZeichen(name);
        this.name = name;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public ExerciseType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
