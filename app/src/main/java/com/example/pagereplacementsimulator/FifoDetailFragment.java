package com.example.pagereplacementsimulator;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class FifoDetailFragment extends Fragment {
    MediaPlayer mpfifoe;
    ImageView fifo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fifo_detail, container, false);
        fifo = v.findViewById(R.id.fifo);
        fifo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpfifoe == null) {
                    mpfifoe = MediaPlayer.create(getActivity(), R.raw.fifo);
                    mpfifoe.start();
                }
                else {
                    mpfifoe.release();
                    mpfifoe = null;
                }
            }
        });
        return v;
    }
}