package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrAnsicht extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_ansicht);
        TextView header = findViewById(R.id.transicht);
        header.setText("Training");
        Button satzPlus = findViewById(R.id.satzHinzufugen);
        TextView uebungInfo = findViewById(R.id.uegwh1);
        satzPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrAnsicht.this, SatzHinzufuegen.class));
            }
        });
        String uebung, gewicht, wh;
        if (getIntent().getStringExtra("uebung") != null) {
            uebung = getIntent().getStringExtra("uebung");
            gewicht = getIntent().getStringExtra("gewicht");
            wh = getIntent().getStringExtra("wh");
            uebungInfo.setText(uebung+"/"+gewicht+"kg/"+wh);
        }
        Button beenden = findViewById(R.id.beenden);
        beenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrAnsicht.this, Startseite.class);
                i.putExtra("name",header.getText().toString());
                startActivity(i);
            }
        });
    }
}