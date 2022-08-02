package com.example.pagereplacementsimulator;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class LifoExampleFragment extends Fragment {
    MediaPlayer mp;
    ImageView lifoe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lifo_example, container, false);
        lifoe = v.findViewById(R.id.lifoe);
        lifoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp == null) {
                    mp = MediaPlayer.create(getActivity(), R.raw.lifoe);
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
