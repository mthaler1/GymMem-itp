package com.example.gymmem.Classes;

import java.util.UUID;

public class TrainingSet {
    private int reps;
    private double weight;
    private Exercise exercise;
    private UUID id;

    public TrainingSet(double weight, int reps, Exercise exercise) {
        this.setWeight(weight);
        this.setReps(reps);
        this.setExercise(exercise);
        this.id = UUID.randomUUID();
    }

    public void setReps(int reps) {
        this.reps = reps;
        if(reps <= 0) {
            throw new IllegalArgumentException("Anzahl der Reps muss positiv sein");
        }
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        if(weight <= 0) {
            throw new IllegalArgumentException("Anzahl des Gewichts muss positiv sein");
        }
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    public UUID getId() {return this.id;}
    public Exercise getExercise() {return this.exercise;}
}
