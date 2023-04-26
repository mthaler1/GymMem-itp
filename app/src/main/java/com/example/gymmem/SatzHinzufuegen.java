package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SatzHinzufuegen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satz_hinzufuegen);
        Spinner uebung = findViewById(R.id.uebungen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.uebungen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uebung.setAdapter(adapter);
        Button add = findViewById(R.id.buttonAdd);
        EditText wh = findViewById(R.id.inputWH);
        TextView ausgabe = findViewById(R.id.ausgabe);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getIntent().getStringExtra("name");
                if(TextUtils.isEmpty(wh.getText())) {
                    ausgabe.setText("Bitte trag die Anzahl an Wiederholungen ein!");
                }
                else {
                    Intent i = new Intent(SatzHinzufuegen.this, TrAnsicht.class);
                    i.putExtra("name", name);
                    startActivity(i);
                }
            }
        });
    }
}