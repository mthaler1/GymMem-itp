package com.example.gymmem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import java.util.Map;

public class Login extends AppCompatActivity {
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button anmelden = findViewById(R.id.loginButton);
        Button registrieren = findViewById(R.id.registrierenButton);
        EditText name = findViewById(R.id.inputName);
        EditText password = findViewById(R.id.inputPassword);
        TextView ausgabe = findViewById(R.id.ausgabe);
        anmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(password.getText())) {
                    ausgabe.setText("Mindestens eine Eingabe ist leer!");
                }
                else {
                    String nameString = name.getText().toString();
                    String passwordString = password.getText().toString();
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("User").document(nameString);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                Map<String, Object> user =document.getData();
                                int passwordDatabase = Integer.parseInt(user.get("password").toString());
                                if (passwordString.hashCode() == passwordDatabase) {
                                    Log.i("ANGEMELDET!", "Der Benutzer "+nameString+" wurde angemeldet!");
                                    startActivity(new Intent(Login.this, Startseite.class));
                                } else {
                                    ausgabe.setText("Das eingegebene Passwort oder Benutzername ungültig.");
                                    Log.i("Passworteingabe", "Passwort ist ungültig");
                                }
                            }
                            else {
                                ausgabe.setText("Es wurde kein Benutzer unter diesem Namen gefunden!");
                                Log.i("Anmeldung","Benutzer nicht gefunden!");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ausgabe.setText("Es wurde kein Benutzer unter diesem Namen gefunden!");
                            Log.i("Anmeldung","Benutzer nicht gefunden!");
                        }
                    });

                }
            }
        });
        registrieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registrieren.class));
            }
        });
    }
}
