package com.example.pagereplacementsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class VirtualMemory extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar vm_toolbar;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_memory);
        vm_toolbar = (Toolbar) findViewById(R.id.vm_toolbar);
        setSupportActionBar(vm_toolbar);
        setTitle("Virtual Memory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        vm_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.play :
                if(mp == null) {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.vm);
                    mp.start();
                }
                else {
                    mp.release();
                    mp = null;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}