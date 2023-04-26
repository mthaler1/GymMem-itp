package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SatzHinzufuegen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satz_hinzufuegen);
        Spinner uebung = findViewById(R.id.uebungen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.uebungen, R.layout.activity_satz_hinzufuegen);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uebung.setAdapter(adapter);
    }
}