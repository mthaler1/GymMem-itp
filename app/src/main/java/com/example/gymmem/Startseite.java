package com.example.gymmem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Startseite extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    StartseiteFragment startseiteFragment = new StartseiteFragment();
    EinstellungenFragment einstellungenFragment = new EinstellungenFragment();
    UeErstellenFragment ueErstellenFragment = new UeErstellenFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.alles,startseiteFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.alles, startseiteFragment).commit();
                    return true;
                }
                if(item.getItemId() == R.id.add) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.alles, ueErstellenFragment).commit();
                    return true;
                }
                if(item.getItemId() == R.id.settings) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.alles, einstellungenFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
