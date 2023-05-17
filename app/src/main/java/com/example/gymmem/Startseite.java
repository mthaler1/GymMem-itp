package com.example.gymmem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class Startseite extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Fragment startseiteFragment = new StartseiteFragment();
    Fragment einstellungenFragment = new EinstellungenFragment();
    Fragment ueErstellenFragment = new UeErstellenFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        String msg = getIntent().getStringExtra("origin");
        if(msg != null && msg.equals("settings")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.alles, einstellungenFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.settings);

        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.alles, startseiteFragment).commit();
        }
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.alles, startseiteFragment).commit();
                    return true;
                }
                if (item.getItemId() == R.id.add) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.alles, ueErstellenFragment).commit();
                    return true;
                }
                if (item.getItemId() == R.id.settings) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.alles, einstellungenFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
