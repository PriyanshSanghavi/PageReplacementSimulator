package com.example.pagereplacementsimulator;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PRDetail3 extends Fragment {
    ImageView img_ph,img_vm,img_paging,img_pf;
    TextView txt_ph,txt_vm,txt_paging,txt_pf;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_p_r_detail3, container, false);
        img_paging = v.findViewById(R.id.img_paging);
        img_ph = v.findViewById(R.id.img_ph);
        img_vm = v.findViewById(R.id.img_vm);
        img_pf = v.findViewById(R.id.img_pf);
        txt_paging = v.findViewById(R.id.txt_paging);
        txt_ph = v.findViewById(R.id.txt_ph);
        txt_vm = v.findViewById(R.id.txt_vm);
        txt_pf = v.findViewById(R.id.txt_pf);
        img_paging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paging = new Intent(getActivity(),Paging.class);
                startActivity(paging);
            }
        });
        txt_paging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paging = new Intent(getActivity(),Paging.class);
                startActivity(paging);
            }
        });
        img_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ph = new Intent(getActivity(),PageHit.class);
                startActivity(ph);
            }
        });
        txt_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ph = new Intent(getActivity(),PageHit.class);
                startActivity(ph);
            }
        });
        img_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pf = new Intent(getActivity(),PageFault.class);
                startActivity(pf);
            }
        });
        txt_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pf = new Intent(getActivity(),PageFault.class);
                startActivity(pf);
            }
        });
        img_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vm = new Intent(getActivity(),VirtualMemory.class);
                startActivity(vm);
            }
        });
        txt_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vm = new Intent(getActivity(),VirtualMemory.class);
                startActivity(vm);
            }
        });
        return v;
    }
}