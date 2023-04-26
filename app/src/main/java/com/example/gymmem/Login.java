package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

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
                    startActivity(new Intent(Login.this,Startseite.class));
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
