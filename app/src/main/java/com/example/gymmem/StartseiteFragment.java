package com.example.gymmem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gymmem.Classes.CurrentUser;
import com.example.gymmem.Classes.Training;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class StartseiteFragment extends Fragment {
    private static Training lastTraining, secondLastTraining, thirdLastTraining;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_startseite, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView header = getView().findViewById(R.id.textLabel);
        header.setText("Hallo "+ CurrentUser.getCurrentUserName()+"!");
        Button trainingStarten = getView().findViewById(R.id.buttonTrainingStarten);
        trainingStarten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TrStarten.class));
            }
        });
        //lastThreeTrainings();
        TextView t1 = getView().findViewById(R.id.textTraining1);
        t1.setText("");
        TextView tD1 = getView().findViewById(R.id.datum1);
        tD1.setText("");
        TextView t2 = getView().findViewById(R.id.textTraining2);
        t2.setText("");
        TextView tD2 = getView().findViewById(R.id.datum2);
        tD2.setText("");
        TextView t3 = getView().findViewById(R.id.textTraining3);
        t3.setText("");
        TextView tD3 = getView().findViewById(R.id.datum3);
        tD3.setText("");
        if(lastTraining != null) {
            t1.setText(lastTraining.getName());
            tD1.setText(lastTraining.getDateStart().toString());
            if(secondLastTraining != null) {
                t2.setText(secondLastTraining.getName());
                tD2.setText(secondLastTraining.getDateStart().toString());
                if(thirdLastTraining != null) {
                    t3.setText(thirdLastTraining.getName());
                    tD3.setText(thirdLastTraining.getDateStart().toString());
                }

            }
        }
        else {
            t1.setText("Du hast noch kein Training absolviert.");
        }

    }
    private static void lastThreeTrainings() {
        String currentUserName = CurrentUser.getCurrentUserName();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("User").document(currentUserName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()) {
                    Map<String, Object> userData = document.getData();
                    Map<String, Object> userTrainings  = (Map<String, Object>) userData.get("trainings");
                    CollectionReference colRef = FirebaseFirestore.getInstance().collection("Trainings");
                    //TODO finish converting Object into DocumentReference
                    for(String id: userTrainings.keySet()) {
                        Log.i("Gleich Exception brd", id);
                        DocumentReference tRef = colRef.document(id);
                        tRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot tDocument = task.getResult();
                                if(tDocument.exists()) {
                                    Map<String, Object> trainingData = tDocument.getData();
                                    Date tDateStart = (Date) trainingData.get("dateStart");
                                    String trainingsname = trainingData.get("name").toString();
                                    Training t = new Training(trainingsname, tDateStart);
                                    if(lastTraining != null) {
                                        if (tDateStart.compareTo(lastTraining.getDateStart()) > 0) {
                                            if(secondLastTraining != null) {
                                                if (tDateStart.compareTo(secondLastTraining.getDateStart()) > 0) {
                                                    if(thirdLastTraining != null) {
                                                        if (tDateStart.compareTo(thirdLastTraining.getDateStart()) > 0) {
                                                            return;
                                                        }
                                                        else {
                                                            thirdLastTraining = t;
                                                        }
                                                    }
                                                    else {
                                                        thirdLastTraining = t;
                                                    }

                                                } else {
                                                    thirdLastTraining = secondLastTraining;
                                                    secondLastTraining = t;
                                                }
                                            }
                                            else {
                                                secondLastTraining = t;
                                            }
                                        } else {
                                            thirdLastTraining = secondLastTraining;
                                            secondLastTraining = lastTraining;
                                            lastTraining = t;
                                        }
                                    }
                                    else {
                                        lastTraining = t;
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    public static DocumentReference stringToDocumentRef(String collectionPath, String documentId) {
        CollectionReference collectionRef = FirebaseFirestore.getInstance().collection(collectionPath);
        String documentIDShort = documentId.substring(documentId.lastIndexOf("/")-1,documentId.length());
        DocumentReference docRef = collectionRef.document(documentId);
        return docRef;

    }
}