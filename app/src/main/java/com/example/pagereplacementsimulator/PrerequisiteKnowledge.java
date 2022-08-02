package com.example.pagereplacementsimulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PrerequisiteKnowledge extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar pk_toolbar;
    ImageView img_ph,img_vm,img_paging,img_pf;
    TextView txt_ph,txt_vm,txt_paging,txt_pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prerequisite_knowledge);
        pk_toolbar = (Toolbar) findViewById(R.id.pk_toolbar);
        img_paging = findViewById(R.id.img_paging);
        img_ph = findViewById(R.id.img_ph);
        img_vm = findViewById(R.id.img_vm);
        img_pf = findViewById(R.id.img_pf);
        txt_paging = findViewById(R.id.txt_paging);
        txt_ph = findViewById(R.id.txt_ph);
        txt_vm = findViewById(R.id.txt_vm);
        txt_pf = findViewById(R.id.txt_pf);
        setSupportActionBar(pk_toolbar);
        setTitle("Pre-requisite Knowledge");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        pk_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_paging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paging = new Intent(getApplicationContext(),Paging.class);
                startActivity(paging);
            }
        });
        txt_paging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paging = new Intent(getApplicationContext(),Paging.class);
                startActivity(paging);
            }
        });
        img_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ph = new Intent(getApplicationContext(),PageHit.class);
                startActivity(ph);
            }
        });
        txt_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ph = new Intent(getApplicationContext(),PageHit.class);
                startActivity(ph);
            }
        });
        img_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pf = new Intent(getApplicationContext(),PageFault.class);
                startActivity(pf);
            }
        });
        txt_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pf = new Intent(getApplicationContext(),PageFault.class);
                startActivity(pf);
            }
        });
        img_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vm = new Intent(getApplicationContext(),VirtualMemory.class);
                startActivity(vm);
            }
        });
        txt_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vm = new Intent(getApplicationContext(),VirtualMemory.class);
                startActivity(vm);
            }
        });
    }
}