package com.example.gymmem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gymmem.Classes.ExerciseType;
import com.example.gymmem.Classes.Training;
import com.example.gymmem.Classes.TrainingSet;
import com.example.gymmem.Classes.TrainingType;
import com.example.gymmem.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrStarten extends AppCompatActivity {

    private String trainingName;
    private Object type;
    private TextView ausgabe;
    private static Training currentTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_starten);
        Spinner kategorien = findViewById(R.id.auswahlkategorie);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.muskelgruppen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        kategorien.setAdapter(adapter);
        EditText name = findViewById(R.id.inputTrainingsname);
        Button weiter = findViewById(R.id.buttonWeiter);
        this.ausgabe = findViewById(R.id.ausgabe);
        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText())) {
                    ausgabe.setText("Bitte einen Namen für das Training vergeben!");
                }
                else {
                    trainingName = name.getText().toString();
                    type = kategorien.getSelectedItem();
                    try {
                        newTraining();
                        Intent i = new Intent(TrStarten.this, TrAnsicht.class);
                        i.putExtra("name",name.getText().toString());
                        startActivity(i);
                    } catch (IllegalArgumentException e) {
                        String msg = e.getMessage();
                        if(msg.contains("Keine gültigen")) {
                            ausgabe.setText(msg);
                        } else if(msg.contains("Name muss mind")) {
                            ausgabe.setText(msg);
                        }
                    }

                };
            }
        });
    }

    private void newTraining() {
            Training training = new Training(trainingName, checkTrainingType(type), new Date());
        
    }

    private TrainingType checkTrainingType(Object type) {
        if(type.toString().equals("Beine")) {
            return TrainingType.LEG;
        }
        if(type.toString().equals("Bauch")) {
            return TrainingType.OTHER;
        }
        if(type.toString().equals("Brust")) {
            return TrainingType.PSH;
        }
        if(type.toString().equals("Rücken")) {
            return TrainingType.PLL;
        }
        if(type.toString().equals("Schultern")) {
            return TrainingType.SHA;
        }
        if(type.toString().equals("Arme")) {
            return TrainingType.SHA;
        }
        return null;
    }

    public static Training getCurrentTraining() {
        return currentTraining;
    }
}