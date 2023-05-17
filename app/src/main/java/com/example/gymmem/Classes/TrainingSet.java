package com.example.gymmem.Classes;

public class TrainingSet {
    private int reps;
    private double weight;
    private Exercise exercise;

    public TrainingSet(int weight, int reps) {
        this.setWeight(weight);
        this.setReps(reps);
        this.setExercise(exercise);
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
