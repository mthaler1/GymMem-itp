package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TrStarten extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_starten);
        Spinner spinnerKategorien = findViewById(R.id.auswahlkategorie);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.muskelgruppen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerKategorien.setAdapter(adapter);
        EditText name = findViewById(R.id.inputTrainingsname);
        Button weiter = findViewById(R.id.buttonWeiter);
        TextView ausgabe = findViewById(R.id.ausgabe);
        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText())) {
                    ausgabe.setText("Bitte einen Namen für das Training vergeben!");
                }
                else {
                    Intent i = new Intent(TrStarten.this, TrAnsicht.class);
                    i.putExtra("name",name.getText().toString());
                    startActivity(i);
                };
            }
        });
    }
}