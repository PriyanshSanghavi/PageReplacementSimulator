package com.example.pagereplacementsimulator;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PRDetail2 extends Fragment {
    ImageView fifo,lifo,lru,opr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_p_r_detail2, container, false);
        fifo=(ImageView) v.findViewById(R.id.fifo);
        lifo=(ImageView)v.findViewById(R.id.lifo);
        opr=(ImageView)v.findViewById(R.id.opr);
        lru=(ImageView)v.findViewById(R.id.lru);
        fifo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fifo = new Intent(getActivity(),Fifo.class);
                startActivity(fifo);
            }
        });
        lifo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lifo = new Intent(getActivity(),Lifo.class);
                startActivity(lifo);

            }
        });
        opr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opr = new Intent(getActivity(),Opr.class);
                startActivity(opr);

            }
        });
        lru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lru = new Intent(getActivity(),Lru.class);
                startActivity(lru);
            }
        });
        return v;
    }
}