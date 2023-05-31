package com.example.gymmem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gymmem.Classes.CurrentTraining;
import com.example.gymmem.Classes.CurrentUser;
import com.example.gymmem.Classes.TrainingSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class TrAnsicht extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_ansicht);
        TextView header = findViewById(R.id.transicht);
        header.setText("Training");
        Button satzPlus = findViewById(R.id.satzHinzufugen);
        TextView uebungInfo = findViewById(R.id.uegwh1);
        TextView satz1 = findViewById(R.id.uegwh1);
        TextView satz2 = findViewById(R.id.uegwh2);
        TextView satz3 = findViewById(R.id.uegwh3);

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Training").document(CurrentTraining.getCurrentTraining());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                               @Override
                                               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                   DocumentSnapshot document = task.getResult();
                                                   if (document.exists()) {
                                                       Map<String, Object> training = document.getData();
                                                       ArrayList<String> sets = (ArrayList<String>) training.get("sets");
                                                       if (sets.size() >= 3) {
                                                           satz1.setText(null);
                                                           satz2.setText(null);
                                                           satz3.setText(null);
                                                       } else if (sets.size() == 2) {
                                                           satz1.setText(null);
                                                           satz2.setText(null);
                                                       } else if (sets.size() == 1) {
                                                           satz1.setText(null);
                                                       }
                                                       for (String id : sets) {
                                                           DocumentReference docRef2 = FirebaseFirestore.getInstance().collection("TrainingSet").document(id);
                                                           docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                                                                   DocumentSnapshot document2 = task2.getResult();
                                                                   if (document2.exists()) {
                                                                       Map<String, Object> trSet = document2.getData();
                                                                       if(satz1.getText()==null) {
                                                                           satz1.setText(trSet.get("exercise")+"/"+trSet.get("reps")+"/"+trSet.get("weight")+"kg");
                                                                       }
                                                                       if(satz2.getText()==null) {
                                                                           satz2.setText(trSet.get("exercise")+"/"+trSet.get("reps")+"/"+trSet.get("weight")+"kg");
                                                                       }
                                                                       if(satz3.getText()==null) {
                                                                           satz3.setText(trSet.get("exercise")+"/"+trSet.get("reps")+"/"+trSet.get("weight")+"kg");
                                                                       }
                                                                   }

                                                               }
                                                           });
                                                       }

                                                   }

                                               }


                                           });


        satzPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrAnsicht.this, SatzHinzufuegen.class));
            }
        });

        Button beenden = findViewById(R.id.beenden);
        beenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrAnsicht.this, Startseite.class);
                i.putExtra("name",header.getText().toString());
                startActivity(i);
            }
        });
    }
}