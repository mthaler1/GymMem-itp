package com.example.gymmem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gymmem.Classes.Exercise;
import com.example.gymmem.Classes.TrainingSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class SatzHinzufuegen extends AppCompatActivity {

    private static TrainingSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satz_hinzufuegen);
        Spinner uebung = findViewById(R.id.uebungen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.uebungen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uebung.setAdapter(adapter);
        Button add = findViewById(R.id.buttonAdd);
        EditText wh = findViewById(R.id.inputWH);
        TextView ausgabe = findViewById(R.id.ausgabe);

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Exercise");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if(querySnapshot != null) {
                        List<String> spinnerElements = new ArrayList<>();
                        int i = 0;
                        for(QueryDocumentSnapshot document: querySnapshot) {

                            String exerciseUser = (String) document.get("user").toString();


                            if(exerciseUser.equals(Login.getCurrentUserName()) || exerciseUser.equals("PUBLIC")) {


                                spinnerElements.add((String) document.get("name").toString());


                            }
                        }
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(SatzHinzufuegen.this, android.R.layout.simple_spinner_item, spinnerElements);

                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        uebung.setAdapter(adapter2);

                    }
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText gewicht = findViewById(R.id.inputGewicht);
                String name = getIntent().getStringExtra("name");


                if(TextUtils.isEmpty(wh.getText()) || TextUtils.isEmpty(gewicht.getText())) {
                    ausgabe.setText("Bitte trag die Anzahl an Wiederholungen oder das Gewicht ein!");
                }
                else {

                    String setExerciseName = uebung.getSelectedItem().toString();


                    int reps = Integer.parseInt(wh.getText().toString());
                    int weight = Integer.parseInt(gewicht.getText().toString());

                    String exerciseName = uebung.getSelectedItem().toString();

                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("Exercise").document(exerciseName);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            if(doc.exists()) {
                                Exercise exercise = new Exercise(exerciseName,UeErstellenFragment.checkExerciseType(doc.getString("type")));
                                set.setExercise(exercise);
                            }
                        }
                    });
                    set.setReps(reps);
                    set.setWeight(weight);

                    Intent i = new Intent(SatzHinzufuegen.this, TrAnsicht.class);
                    startActivity(i);
                }
            }
        });
    }
}