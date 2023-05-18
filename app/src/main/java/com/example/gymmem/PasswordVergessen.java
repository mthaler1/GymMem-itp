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

public class PasswordVergessen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_vergessen);
        Button back = findViewById(R.id.buttonBackToLogin);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PasswordVergessen.this, Login.class));
            }
        });
        Button submit = findViewById(R.id.changePasswortButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.inputUsername);
                EditText mail = findViewById(R.id.inputMail);
                EditText newPassword = findViewById(R.id.inputNewPassword);
                EditText passwordConfirm = findViewById(R.id.inputPasswordConfirm);
                TextView ausgabe = findViewById(R.id.ausgabe);
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(mail.getText().toString()) || TextUtils.isEmpty(newPassword.getText().toString()) || TextUtils.isEmpty(passwordConfirm.getText().toString())) {
                    ausgabe.setText("Mindestens eine Eingabe ist leer");
                } else {
                    if (!newPassword.getText().toString().equals(passwordConfirm.getText().toString())) {
                        ausgabe.setText("Die zwei neuen Passwörter stimmen nicht überein.");
                    }
                    else {
                        String usernameString = username.getText().toString();
                        DocumentReference docRef = FirebaseFirestore.getInstance().collection("User").document(usernameString);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Map<String, Object> userdata = document.getData();
                                    String mailUser = userdata.get("email").toString();
                                    int oldPasswordHashed = Integer.parseInt(userdata.get("password").toString());
                                    if (!mailUser.equals(mail.getText().toString())) {
                                        ausgabe.setText("Die E-Mail-Adresse ist nicht korrekt.");

                                    }
                                    if (oldPasswordHashed == newPassword.getText().toString().hashCode()) {
                                        ausgabe.setText("Das alte Passwort kann nicht das Neue sein.");
                                        return;
                                    }

                                    if (User.checkPasswordStrength(newPassword.getText().toString()) < 2) {
                                        ausgabe.setText("Das eingegebene Passwort ist ungültig.\nMindestens 8 Zeichen und 3 Zeichengruppen und von jeder\nGruppe mindestens zwei Zeichen vorkommen.");
                                        return;
                                    }
                                    Map<String, Object> newUserdata = new HashMap<>();
                                    newUserdata.put("password", newPassword.getText().toString().hashCode());
                                    docRef.update(newUserdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.i("Passwort Änderung", "Der Benutzer " + username.getText().toString() + " hat gerade sein Passwort geändert");
                                            startActivity(new Intent(PasswordVergessen.this, Login.class));
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}