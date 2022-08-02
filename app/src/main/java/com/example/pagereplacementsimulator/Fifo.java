package com.example.pagereplacementsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Fifo extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar fifo_toolbar;
    BottomNavigationView bnavig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifo);
        bnavig=(BottomNavigationView)findViewById(R.id.bnavig);
        bnavig.setOnNavigationItemSelectedListener(bottomNavigationMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new FifoDetailFragment()).commit();
        fifo_toolbar = (Toolbar) findViewById(R.id.fifo_toolbar);
        setSupportActionBar(fifo_toolbar);
        setTitle("First In First Out");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fifo_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
                    fragment = new FifoDetailFragment();
                    break;
                case R.id.example:
                    fragment = new FifoExampleFragment();
                    break;
                case R.id.simulator:
                    fragment = new FifoSimulatorFragment();
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