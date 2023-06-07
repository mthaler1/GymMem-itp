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

import com.example.gymmem.Classes.CurrentUser;
import com.example.gymmem.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasse um Passwort ändern auszuführen
 * @author Raphael Tarnoczi
 * @version 2023-5-15
 */
public class PasswortAendern extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwort_aendern);
        Button back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PasswortAendern.this, Startseite.class);
                i.putExtra("origin", "settings");
                startActivity(i);
            }
        });
        Button changePassword = findViewById(R.id.changePasswortButton);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText oldPassword = findViewById(R.id.inputOldPassword);
                EditText newPassword = findViewById(R.id.inputNewPassword);
                EditText passwordConfirm = findViewById(R.id.inputPasswordConfirm);
                TextView ausgabe =findViewById(R.id.ausgabe);
                if (TextUtils.isEmpty(oldPassword.getText().toString()) || TextUtils.isEmpty(newPassword.getText().toString()) || TextUtils.isEmpty(passwordConfirm.getText().toString())){
                    ausgabe.setText("Mindestens eine Eingabe ist leer");
                }
                else {
                    if(!passwordConfirm.getText().toString().equals(newPassword.getText().toString())) {
                        ausgabe.setText("Die zwei neuen Passwörter stimmen nicht überein.");
                        return;
                    }
                    if(oldPassword.getText().toString().equals(newPassword.getText().toString())) {
                       ausgabe.setText("Neues Passwort kann nicht das Alte sein.");
                       return;
                    }
                    if(User.checkPasswordStrength(newPassword.getText().toString())<2) {
                        ausgabe.setText("Das eingegebene Passwort ist ungültig.\nMindestens 8 Zeichen und 3 Zeichengruppen und von jeder\nGruppe mindestens zwei Zeichen vorkommen.");
                        return;
                    }
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("User").document(CurrentUser.getCurrentUserName());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                Map<String, Object> userdata = document.getData();
                                int passwordHashed = Integer.parseInt(userdata.get("password").toString());
                                if(oldPassword.getText().toString().hashCode() == passwordHashed) {
                                    Map<String, Object> newUserdata = new HashMap<>();
                                    newUserdata.put("password",newPassword.getText().toString().hashCode());
                                    docRef.update(newUserdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.i("Userdaten geändert", "Der Benutzer hat erfolgreich das Passwort geändert");
                                            Intent i = new Intent(PasswortAendern.this, Startseite.class);
                                            i.putExtra("origin", "settings");
                                            startActivity(i);
                                        }
                                    });
                                }
                                else {
                                    ausgabe.setText("Das alte Passwort ist nicht korrekt.");
                                }
                            }
                        }
                    });

                }
            }
        });
    }
}