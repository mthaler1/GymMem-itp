package com.example.gymmem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.gymmem.Classes.Exercise;
import com.example.gymmem.Classes.ExerciseType;
import com.example.gymmem.Classes.Training;
import com.example.gymmem.Classes.TrainingSet;
import com.example.gymmem.Classes.TrainingType;
import com.example.gymmem.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SaveDummies extends AppCompatActivity {
    private static User u1, u2;
    private static Exercise e1,e2,e3,e4,e5,e6;
    private static TrainingSet ts1,ts2,ts3,ts4,ts5,ts6;
    private static Training t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_dummies);
        addExerciseToSets();
        addSetsToTraining();
        addTrainingToUser();
        saveExercises();
        saveSets();
        saveTrainings();
        saveUser();
        startActivity(new Intent(SaveDummies.this, Login.class));
    }
    private static void addExerciseToSets() {
        e1 = new Exercise("Übung1", ExerciseType.LEGS);
        ts1 = new TrainingSet(15, 30, e1);
        e2 = new Exercise("Übung2", ExerciseType.CHEST);
        ts2 = new TrainingSet(10, 25, e2);
        e3 = new Exercise("Übung3", ExerciseType.SHOULDERS);
        ts3 = new TrainingSet(5, 20, e3);
        e4 = new Exercise("Übung4", ExerciseType.ABS);
        ts4 = new TrainingSet(7.5, 15, e4);
        e5 = new Exercise("Übung5", ExerciseType.BACK);
        ts5 = new TrainingSet(12.5, 10, e5);
        e6 = new Exercise("Übung6", ExerciseType.OTHER);
        ts6 = new TrainingSet(17.5, 5, e6);
    }
    private static void addSetsToTraining() {
        t1 = new Training("Training1", TrainingType.PLL, new Date(2023,5,18,23,0));
        t1.setDateEnd(new Date(2023,5,19,0,0));
        t1.addSet(ts1);
        t1.addSet(ts2);
        t2 = new Training("Training2", TrainingType.CBK, new Date(2023,5,18,22,0));
        t2.setDateEnd(new Date(2023,5,18,23,0));
        t2.addSet(ts3);
        t2.addSet(ts4);
        t3 = new Training("Training3", TrainingType.SHA, new Date(2023,5,18,21,0));
        t3.setDateEnd(new Date(2023,5,18,22,0));
        t3.addSet(ts5);
        t3.addSet(ts6);
        t4 = new Training("Training4", TrainingType.WBY, new Date(2023,5,18,20,0));
        t4.setDateEnd(new Date(2023,5,18,21,0));
        t4.addSet(ts2);
        t4.addSet(ts4);

    }
    private static void addTrainingToUser() {
        u1 = new User("testuser1@tgm.ac.at", "User1", "Passwort123/ITP");
        u1.addTraining(t1);
        u1.addTraining(t2);
        u2 = new User("testuser2@tgm.ac.at", "User2", "Passwort123/ITP2");
        u2.addTraining(t3);
        u2.addTraining(t4);

    }
    private static void saveExercises() {
        CollectionReference colRef = FirebaseFirestore.getInstance().collection("Exercise");
        List<Exercise> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
        list.add(e5);
        list.add(e6);
        for(Exercise e: list) {
            DocumentReference docRef = colRef.document(e.getName());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if(!document.exists()) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",e.getName());
                        map.put("type",e.getType().toString());
                        docRef.set(map);
                        Log.i("Exercies saved", "Folgendes Exercise wurde gespeichert: "+e.getName());
                    }
                    else {
                        Log.i("Exercise already saved", "Folgendes Exercise ist bereits abgespeichert "+e.getName());
                    }
                }
            });
        }
    }
    private static void saveSets() {
        CollectionReference colRef = FirebaseFirestore.getInstance().collection("TrainingSet");
        List<TrainingSet> list = new ArrayList<>();
        list.add(ts1);
        list.add(ts2);
        list.add(ts3);
        list.add(ts4);
        list.add(ts5);
        list.add(ts6);
        for(TrainingSet ts: list) {
            DocumentReference docRef = colRef.document(ts.getId().toString());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if(!document.exists()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("weight",ts.getWeight());
                        map.put("reps",ts.getReps());
                        DocumentReference eRef = FirebaseFirestore.getInstance().collection("Exercise").document(ts.getExercise().getName());
                        map.put("exercise",eRef);
                        docRef.set(map);
                        Log.i("Set saved", "Folgendes TrainingSet wurde gespeichert: "+ts.getId());
                    }
                    else {
                        Log.i("Set already saved", "Folgendes Exercise ist bereits abgespeichert "+ts.getId());
                    }
                }
            });
        }
    }
    private static void saveTrainings() {
        CollectionReference colRef = FirebaseFirestore.getInstance().collection("Training");
        List<Training> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        for(Training t: list) {
            DocumentReference docRef = colRef.document(t.getId().toString());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if(!document.exists()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name",t.getName());
                        map.put("id",t.getId().toString());
                        map.put("dateStart",t.getDateStart());
                        map.put("dateEnd",t.getDateEnd());
                        map.put("type",t.getType());
                        List<TrainingSet> listSets = t.getSets();
                        List<DocumentReference> sets = new ArrayList<>();
                        for(TrainingSet ts: listSets) {
                            DocumentReference tsRef= FirebaseFirestore.getInstance().collection("TrainingSet").document(ts.getId().toString());
                            sets.add(tsRef);
                        }
                        map.put("sets", sets);
                        docRef.set(map);
                        Log.i("Training saved", "Folgendes Training wurde gespeichert: "+t.getId().toString());
                    }
                    else {

                        Log.i("Training already saved", "Folgendes Training ist bereits gespeichert: "+t.getId().toString());
                    }
                }
            });
        }

    }
    private static void saveUser() {
        CollectionReference colRef = FirebaseFirestore.getInstance().collection("User");
        List<User> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        for(User u: list) {
            DocumentReference docRef = colRef.document(u.getUsername());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if(!document.exists()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name",u.getUsername());
                        map.put("email",u.getEmail());
                        map.put("id",u.getUserID().toString());
                        map.put("password",u.getPasswort().hashCode());
                        Map<String, Object> trainings = new HashMap();
                        Map<UUID, Training> trainingsMap = u.getTrainings();
                        for(UUID id: trainingsMap.keySet()) {
                            DocumentReference tRef = FirebaseFirestore.getInstance().collection("Training").document(id.toString());
                            trainings.put(id.toString(), tRef);
                        }
                        map.put("trainings",trainings);
                        docRef.set(map);
                        Log.i("User saved", "Folgender User wurde gespeichert: "+u.getUsername());
                    }
                    else {
                        Log.i("User already saved", "Folgender User ist bereits gespeichert: "+u.getUsername());
                    }
                }
            });
        }

    }
}
