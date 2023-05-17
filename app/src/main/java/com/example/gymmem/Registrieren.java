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

import com.example.gymmem.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registrieren extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren);
        EditText mail = findViewById(R.id.inputMail);
        EditText name= findViewById(R.id.inputName);
        EditText password = findViewById(R.id.inputPassword);
        Button registrieren = findViewById(R.id.registrierenButton);
        Button anmelden = findViewById(R.id.loginButton);
        TextView ausgabe = findViewById(R.id.ausgabe);
        registrieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(mail.getText())) {
                    ausgabe.setText("Mindestens eine Eingabe ist leer!");
                }
                else {
                    String usernameString = name.getText().toString();
                    String mailString = mail.getText().toString();
                    String passwordString = password.getText().toString();
                    saveUser(usernameString,mailString,passwordString,ausgabe);
                }

            }
        });
        anmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registrieren.this, Login.class));
            }
        });

    }


    private void saveUser(String mailString,String usernameString, String passwordString,TextView ausgabe) {
        try {
            User u = new User(mailString,usernameString,passwordString);

            Map<String, Object> user = new HashMap<>();
            user.put("name",u.getUsername());
            user.put("password",u.getPasswort().hashCode());
            user.put("email",u.getEmail());
            user.put("id",u.getUserID().toString());
            user.put("trainings",u.getTrainings());
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("User").document(u.getUsername());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        ausgabe.setText("Ein Account unter diesem Namen existiert bereits.");
                    }
                    else {
                        docRef.set(user);
                        Log.wtf("Dokument angelegt","Ein neues Dokument für den User "+u.getUsername()+" wurde erstellt.");
                        startActivity(new Intent(Registrieren.this, Startseite.class));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ausgabe.setText("Es ist ein Fehler aufgetreten.");
                    Log.wtf("Registrieren","Es ist ein Fehler bei der Registrierung aufgetreten.");
                }
            });
        }
        catch(IllegalArgumentException e) {
            String msg = e.getMessage();
            if(msg.contains("Email-Adresse")){
                ausgabe.setText("Die eingegebene Email-Adresse ist ungültig.");
            }
            else if(msg.contains("Username")) {
                ausgabe.setText("Der eingegebene Benutzername ist ungültig.");
            }
            else if(msg.contains("Passwort") ||msg.contains("Zeichen")) {
                ausgabe.setText("Das eingegebene Passwort ist ungültig.\nMindestens 8 Zeichen und 2 Zeichengruppen.");
            }
            else {
                ausgabe.setText("Fehler! Probiere es erneut.");
            }
        }
    }
}
