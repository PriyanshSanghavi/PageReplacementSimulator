package com.example.pagereplacementsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Lru extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar lru_toolbar;
    BottomNavigationView bnavig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru);
        bnavig=(BottomNavigationView)findViewById(R.id.bnavig);
        bnavig.setOnNavigationItemSelectedListener(bottomNavigationMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new LruDetailFragment()).commit();
        lru_toolbar = (Toolbar) findViewById(R.id.lru_toolbar);
        setSupportActionBar(lru_toolbar);
        setTitle("Least Recently Used");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lru_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment=null;
            switch (menuItem.getItemId()){
                case R.id.detail:
                    fragment = new LruDetailFragment();
                    break;
                case R.id.example:
                    fragment = new LruExampleFragment();
                    break;
                case R.id.simulator:
                    fragment = new LruSimulatorFragment();
                    break;

            }
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
                return true;
            }
            return true;
        }
    };
}