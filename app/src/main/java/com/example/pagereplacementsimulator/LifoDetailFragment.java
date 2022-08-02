package com.example.pagereplacementsimulator;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class LifoDetailFragment extends Fragment {
    MediaPlayer mp;
    ImageView lifo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lifo_detail, container, false);
        lifo = v.findViewById(R.id.lifo);
        lifo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp == null) {
                    mp = MediaPlayer.create(getActivity(), R.raw.lifo);
                    mp.start();
                }
                else {
                    mp.release();
                    mp = null;
                }
            }
        });
        return v;
    }
}
