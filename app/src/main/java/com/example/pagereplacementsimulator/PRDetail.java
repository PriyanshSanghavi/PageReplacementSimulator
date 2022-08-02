package com.example.pagereplacementsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class PRDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    SpringDotsIndicator dotsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_r_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        dotsIndicator = (SpringDotsIndicator) findViewById(R.id.dots_indicator);
        pager = findViewById(R.id.am_view1);
        List<Fragment> list= new ArrayList<>();
        list.add(new PRDetail1());
        list.add(new PRDetail2());
        list.add(new PRDetail3());

        pager.setClipToPadding(false);
        pagerAdapter = new SlidePager(getSupportFragmentManager(),list);
        pager.setAdapter(pagerAdapter);
        dotsIndicator.setViewPager(pager);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()) {
            case R.id.home:
                Intent home = new Intent(getApplication(),PRDetail.class);
                startActivity(home);
                break;
            case R.id.pknowleged :
                Intent pk = new Intent(getApplication(),PrerequisiteKnowledge.class);
                startActivity(pk);
                break;
            case R.id.fifo:
                Intent fifo = new Intent(getApplication(),Fifo.class);
                startActivity(fifo);
                break;
            case R.id.lifo:
                Intent lifo = new Intent(getApplication(),Lifo.class);
                startActivity(lifo);
                break;
            case R.id.opr:
                Intent opr = new Intent(getApplication(),Opr.class);
                startActivity(opr);
                break;
            case R.id.lru:
                Intent lru = new Intent(getApplication(),Lru.class);
                startActivity(lru);
                break;
            case R.id.ca:
                Intent ca = new Intent(getApplication(),ComparisonAlgorithm.class);
                startActivity(ca);
                break;
            case R.id.help:
                Intent help = new Intent(getApplication(),Help.class);
                startActivity(help);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}