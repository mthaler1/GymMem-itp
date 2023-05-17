package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UeErstellen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ue_erstellen);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_add);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), Startseite.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            if(item.getItemId() == R.id.bottom_add) {
                return true;
            }
            if(item.getItemId() == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), Einstellungen.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        Spinner spinnerMuskelgruppen = findViewById(R.id.muskelgruppen);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.muskelgruppen, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerMuskelgruppen.setAdapter(adapter);

    }


}