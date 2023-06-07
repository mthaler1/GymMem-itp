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
import java.util.UUID;

/**
 * Klasse um Mail ändern auszuführen
 * @author Raphael Tarnoczi
 * @version 2023-5-15
 */
public class MailAendern extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_aendern);
        Button back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MailAendern.this, Startseite.class);
                i.putExtra("origin", "settings");
                startActivity(i);
            }
        });
        Button submit = findViewById(R.id.changeMailButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText oldMail = findViewById(R.id.inputOldMail);
                EditText newMail = findViewById(R.id.inputNewMail);
                EditText password = findViewById(R.id.inputPassword2);
                TextView ausgabe = findViewById(R.id.ausgabe);
                if(TextUtils.isEmpty(oldMail.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(newMail.getText())) {
                    ausgabe.setText("Mindestens eine Eingabe ist leer!");
                }
                else {
                    if (!User.checkEmail(newMail.getText().toString())) {
                        ausgabe.setText("Ungültige E-Mail-Addresse eingegeben");
                        return;
                    }
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("User").document(CurrentUser.getCurrentUserName());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> userData = document.getData();
                                String oldMailUser = userData.get("email").toString();
                                if(oldMailUser.equals(newMail.getText().toString())){
                                    ausgabe.setText("Die eingegebenen E-Mail-Adressen sind ident.");
                                    return;
                                }
                                int passwordHashed = Integer.parseInt(userData.get("password").toString());
                                if (!oldMailUser.equals(oldMail.getText().toString())) {
                                    ausgabe.setText("Der eingegebene E-Mail-Adresse gehört\nnicht zu diesem Account.");
                                    return;
                                }
                                if (passwordHashed != password.getText().toString().hashCode()) {
                                    ausgabe.setText("Das eingegebene Passwort ist falsch.");
                                    return;
                                }
                                Map<String, Object> newUserdata = new HashMap<>();
                                newUserdata.put("email", newMail.getText().toString());
                                docRef.update(newUserdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.i("Userdaten verändert", "Der User hat erfolgreich seine E-Mail geändert");
                                        Intent i = new Intent(MailAendern.this, Startseite.class);
                                        i.putExtra("origin", "settings");
                                        startActivity(i);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}