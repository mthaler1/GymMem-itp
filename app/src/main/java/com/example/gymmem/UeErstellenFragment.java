package com.example.gymmem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
                if(TextUtils.isEmpty(input.getText())){
                    ausgabe.setText("Lege einen Name für die Übung fest!");
                }
                else {
                    Log.i("Eingabe", input.getText().toString());
                    String exerciseName = input.getText().toString();
                    Exercise exercise = new Exercise(exerciseName, ExerciseType.ABS);
                    Map<String, Object> exercises = new HashMap<>();
                    exercises.put("name",exerciseName);
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("Exercise").document(exercise.getName());

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                ausgabe.setText("Eine Übung unter diesem Namen existiert bereits.");
                            } else {
                                docRef.set(exercises);
                                Log.i("Exercise angelegt",exercise.getName());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new IllegalArgumentException();
                        }
                    });

                    ausgabe.setText(null);
                    input.setText(null);
                }
            }
        });
    }
}