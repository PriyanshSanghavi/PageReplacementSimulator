package com.example.pagereplacementsimulator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PRDetail1 extends Fragment {
    Button btn;
    ImageView img_ps,pra,pralm;
    MediaPlayer mp1,mp2;
    com.uncopt.android.widget.text.justify.JustifiedTextView txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_p_r_detail1, container, false);
        btn = v.findViewById(R.id.btn);
        txt2 = v.findViewById(R.id.txt2);
        txt3 = v.findViewById(R.id.txt3);
        txt4 = v.findViewById(R.id.txt4);
        txt5 = v.findViewById(R.id.txt5);
        txt6 = v.findViewById(R.id.txt6);
        txt7 = v.findViewById(R.id.txt7);
        txt8 = v.findViewById(R.id.txt8);
        txt9 = v.findViewById(R.id.txt9);
        img_ps = v.findViewById(R.id.img_ps);
        pra = v.findViewById(R.id.pra);
        pralm = v.findViewById(R.id.pralm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setVisibility(View.INVISIBLE);
                txt2.setVisibility(View.VISIBLE);
                txt3.setVisibility(View.VISIBLE);
                txt4.setVisibility(View.VISIBLE);
                txt5.setVisibility(View.VISIBLE);
                txt6.setVisibility(View.VISIBLE);
                txt7.setVisibility(View.VISIBLE);
                txt8.setVisibility(View.VISIBLE);
                txt9.setVisibility(View.VISIBLE);
                img_ps.setVisibility(View.VISIBLE);
                pra.setVisibility(View.VISIBLE);
                pralm.setVisibility(View.VISIBLE);
            }
        });
        pra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {if(mp1 == null) {
                mp1 = MediaPlayer.create(getActivity(), R.raw.pra);
                mp1.start();
            }
            else {
                mp1.release();
                mp1 = null;
            }

            }
        });
        pralm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp2 == null) {
                mp2 = MediaPlayer.create(getActivity(), R.raw.pralm);
                mp2.start();
            }
            else {
                mp2.release();
                mp2 = null;
            }
            }
        });
        return v;
    }
}