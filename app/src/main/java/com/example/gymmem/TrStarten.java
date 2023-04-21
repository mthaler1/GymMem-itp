package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TrStarten extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_starten);
        Spinner spinnerKategorien = findViewById(R.id.auswahlkategorie);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.muskelgruppen, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerKategorien.setAdapter(adapter);
    }
}