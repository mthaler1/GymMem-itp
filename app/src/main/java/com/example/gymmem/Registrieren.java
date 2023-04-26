package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                    if(password.length() > 8) {
                        ausgabe.setText("Passwort muss mindestens 8 Zeichen lang sein.");
                    }
                    else {
                        Intent i = new Intent(Registrieren.this,Startseite.class);
                        i.putExtra("name", name.getText().toString());
                        startActivity(i);
                    }
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
}
