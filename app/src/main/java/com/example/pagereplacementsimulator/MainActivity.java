package com.example.pagereplacementsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 3000;
    Animation topAnim,bottomAnim;
    ImageView logo;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo = findViewById(R.id.logo);
        tv1 = findViewById(R.id.tv1);
        logo.setAnimation(topAnim);
        tv1.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent PRD = new Intent(getApplication(),PRDetail.class);
                startActivity(PRD);
                finish();
            }
        },TIME_OUT);
    }
}