package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class UeErstellen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ue_erstellen);

        Spinner spinnerMuskelgruppen = findViewById(R.id.muskelgruppen);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.muskelgruppen, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerMuskelgruppen.setAdapter(adapter);

    }


}