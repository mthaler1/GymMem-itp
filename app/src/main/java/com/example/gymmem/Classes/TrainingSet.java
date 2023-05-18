package com.example.gymmem.Classes;

public class TrainingSet {
    private int reps;
    private double weight;
    private Exercise exercise;

    public TrainingSet(int weight, int reps, Exercise exercise) {
        this.setWeight(weight);
        this.setReps(reps);
        this.setExercise(exercise);
    }

    public void setReps(int reps) {
        if(reps < 0) {
            throw new IllegalArgumentException("Anzahl der Reps muss positiv sein");
        }
        this.reps = reps;
    }

    public void setWeight(double weight) {
        if(weight < 0) {
            throw new IllegalArgumentException("Anzahl des Gewichts muss positiv sein");
        }
        this.weight = weight;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
