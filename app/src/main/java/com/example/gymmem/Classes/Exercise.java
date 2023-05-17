package com.example.gymmem.Classes;

public class Exercise {
    private ExerciseType type;
    private String name;

    public Exercise(String name, ExerciseType type) {
        this.setName(name);
        this.setType(type);
    }

    public void setName(String name) {
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
