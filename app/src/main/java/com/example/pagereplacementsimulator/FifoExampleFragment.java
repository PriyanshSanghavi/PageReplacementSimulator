package com.example.pagereplacementsimulator;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class FifoExampleFragment extends Fragment {
    MediaPlayer mpfifo;
    ImageView fifoe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fifo_example, container, false);
        fifoe = v.findViewById(R.id.fifoe);
        fifoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpfifo == null) {
                    mpfifo = MediaPlayer.create(getActivity(), R.raw.fifoe);
                    mpfifo.start();
                }
                else {
                    mpfifo.release();
                    mpfifo = null;
                }
            }
        });
        return v;
    }
}
