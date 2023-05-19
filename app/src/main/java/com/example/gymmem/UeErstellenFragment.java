package com.example.gymmem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gymmem.Classes.Exercise;
import com.example.gymmem.Classes.ExerciseType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UeErstellenFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ue_erstellen, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner kategorie = getView().findViewById(R.id.muskelgruppen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.muskelgruppen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategorie.setAdapter(adapter);
        EditText input = getView().findViewById(R.id.UebungEingabe);
        Button add = getView().findViewById(R.id.buttonAdd);
        TextView ausgabe = getView().findViewById(R.id.ausgabe);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(TextUtils.isEmpty(input.getText())){
                        ausgabe.setText("Lege einen Namen für die Übung fest!");
                    }
                    else {
                        Log.i("Eingabe", input.getText().toString());
                        String exerciseName = input.getText().toString();
                        ExerciseType exerciseType = checkExerciseType(kategorie.getSelectedItem());
                        Exercise exercise = new Exercise(exerciseName, exerciseType);
                        Map<String, Object> exercises = new HashMap<>();
                        exercises.put("name",exercise.getName());
                        exercises.put("type",exercise.getType());
                        exercises.put("user",exercise.getUser());
                        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Exercise").document(exercise.getName());

                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()) {
                                    ausgabe.setText("Eine Übung unter diesem Namen existiert bereits.");
                                } else {
                                    docRef.set(exercises);
                                    Log.i("Exercise angelegt",exercise.getName() + exercise.getType());
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                ausgabe.setText("Übung konnte nicht hinzugefügt werden");
                            }
                        });

                        ausgabe.setText(null);
                        input.setText(null);
                    }
                } catch(IllegalArgumentException e) {
                    String msg = e.getMessage();
                    if(msg.contains("Zeichen")) {
                        ausgabe.setText("Keine gültigen Zeichen verwendet");
                    }
                }

            }
        });
    }

    private ExerciseType checkExerciseType(Object type) {
        if(type.toString().equals("Beine")) {
            return ExerciseType.LEGS;
        }
        if(type.toString().equals("Bauch")) {
            return ExerciseType.ABS;
        }
        if(type.toString().equals("Brust")) {
            return ExerciseType.CHEST;
        }
        if(type.toString().equals("Rücken")) {
            return ExerciseType.BACK;
        }
        if(type.toString().equals("Schultern")) {
            return ExerciseType.SHOULDERS;
        }
        if(type.toString().equals("Arme")) {
            return ExerciseType.ARMS;
        }
        return null;
    }

}