package com.example.pagereplacementsimulator;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class OprSimulatorFragment extends Fragment {    //Time Complexity: O(ref_len * ref_len * frames)
                                                        //Space Complexity: O(ref_len * frames)
    EditText nof,rs,lors;
    Button opr_simulator;
    TextView txt1,txt2,txt3,txt4,txt,HitMis;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int frames, pointer = 0, hit = 0, fault = 0, ref_len;
    Boolean isFull = false;
    int buffer[]; //buffer is an array which denotes a particular column in the mem_layout
    ArrayList<Integer> stack = new ArrayList<Integer>();
    int reference[]; //Integer array to store the reference string
    int mem_layout[][]; //2D array to represent memory layout with rows representing frame number and columns representing length of reference string
    boolean color[][];
    char HitMiss[]; //character array to store hit/miss. H means Hit, M means miss.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_opr_simulator, container, false);
        nof = v.findViewById(R.id.nof);
        rs = v.findViewById(R.id.rs);


        lors = v.findViewById(R.id.lors);
        opr_simulator = v.findViewById(R.id.opr_simulator);
        txt = v.findViewById(R.id.txt);
        HitMis = v.findViewById(R.id.HitMis);
        txt1 = v.findViewById(R.id.txt1);
        txt2 = v.findViewById(R.id.txt2);
        txt3 = v.findViewById(R.id.txt3);
        txt4 = v.findViewById(R.id.txt4);
        opr_simulator.setText("SIMULATOR");
        opr_simulator.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(opr_simulator.getText() == "SIMULATOR") {
                    if (validate()) {
                        nof.setEnabled(false);
                        lors.setEnabled(false);
                        rs.setEnabled(false);
                        frames = Integer.parseInt(nof.getText().toString());
                        ref_len = Integer.parseInt(lors.getText().toString());
                        reference = new int[ref_len]; //setting length of reference array
                        HitMiss= new char[ref_len]; //Array to store Hit or Miss
                        mem_layout = new int[ref_len][frames]; //setting dimensions of mem_layout array
                        color = new boolean[ref_len][frames];
                        buffer = new int[frames]; //setting no. of frames in buffer queue
                        for (int j = 0; j < frames; j++)
                            buffer[j] = -1; //initialising all the frames in buffer queue to -1
                        //-1 signifies frame is empty
                        String a = rs.getText().toString();
                        String[] string = a.split(",");
                        for (int i = 0; i < string.length; i++)
                            reference[i] = Integer.valueOf(string[i]);
                        //Time Complexity:O(ref_len * ref_len * frames)
                        for(int i = 0; i < ref_len; i++)
                        {
                            int search = -1; //flag to detect page fault
                            for(int j = 0; j < frames; j++)
                            {
                                if(buffer[j] == reference[i]) //checking if page is already inside main memory
                                {
                                    search = j; //changing value of flag variable if page is present in main memory
                                    hit++; //incrementing page hit count
                                    HitMiss[i]='H'; //updating H inside HitMiss array
                                    break;
                                }
                            }
                            // code to update the stack checking its capacity
                            if(search == -1) // if page is not inside main memory
                            {
                                if(isFull) //checking if buffer is full or not
                                {
                                    int index[] = new int[frames];
                                    boolean index_flag[] = new boolean[frames];
                                    for(int j = i + 1; j < ref_len; j++)
                                    {
                                        for(int k = 0; k < frames; k++)
                                        {
                                            if((reference[j] == buffer[k]) && (index_flag[k] == false)) //(reference[j] == buffer[k]) this checks if any element in buffer[] occurs later on in the reference string
                                            {                                                           //(index_flag[k] == false)) this checks if the element in buffer has already been marked as true
                                                index[k] = j; //if the element in buffer[] occurs later in the reference string then we save its index
                                                index_flag[k] = true; //if the element in buffer[] occurs later in the reference string then we set its flag as true
                                                break;
                                            }
                                        }
                                    }
                                    //At the end of this code, the index[] array will contain the values(indexes) at which the elements in the buffer[] array occur for the first time in reference string
                                    //If a certain element in buffer[] array does not repeat again in the reference string, then we set its index=0 i.e. default value
                                    //In this we check which element in the buffer[] array occurs the latest in the reference string i.e. element with the highest index value in index[] array
                                    int max = index[0];
                                    pointer = 0;
                                    if(max == 0)  //If max=0 it means that the particular element does not repeat again in the reference string. Hence we will replace this element only.
                                        max = Integer.MAX_VALUE; //Thus we set max to some arbitrary value greater than the length of reference string so that no value in index array can be greater than max.
                                    for(int j = 0; j < frames; j++)
                                    {
                                        if(index[j] == 0) //If we encounter 0 in the index array, it means that element does not repeat again in reference string. Thus we will replace this element only.
                                        {
                                            index[j] = Integer.MAX_VALUE;
                                        }
                                        if(index[j] > max) //Condition to check which element in index[] array has largest value.
                                        {
                                            max = index[j];
                                            pointer = j;
                                        }
                                    }
                                }
                                buffer[pointer] = reference[i]; //Pushes the page into the buffer array according to the pointer value
                                fault++; //Increments Page Fault
                                HitMiss[i]='M'; //updating M inside HitMiss array
                                if(!isFull)
                                {
                                    pointer++;
                                    if(pointer == frames)
                                    {
                                        pointer = 0;      //if index becomes equal to frame number, we reset the pointer variable.
                                        isFull = true;    //flag to check if buffer is full or not
                                    }
                                }
                            }
                            for(int j = 0; j < frames; j++)
                                mem_layout[i][j] = buffer[j];
                        }
                        for (int j = 0 ; j < frames; j ++) {
                            for(int i = 0 ; i < ref_len ; i++) {
                                color[i][j] = false;
                                if(mem_layout[i][j] == reference[i])
                                    color[i][j] = true;
                            }
                        }
                        // code to display the memory layout
                        for (int i = 0; i < frames; i++)
                        {
                            txt.append("\n");
                            txt.append(" | Frame ");
                            if((i+1)>9)
                                txt.append((i + 1) + " :\t\t\t");
                            else
                                txt.append((i + 1) + "   :\t\t\t");
                            for (int j = 0; j < ref_len; j++)
                            {
                               if(mem_layout[j][i] >-1)
                                   if (color[j][i] == true) {
                                       txt.append("|\t\t");
                                       txt.append(Html.fromHtml("<font color=#028A0F>" + mem_layout[j][i] + "</font>"));
                                       txt.append("\t\t");
                                   } else
                                       txt.append("|\t\t" + mem_layout[j][i] + "\t\t");
                               else
                                   txt.append("|  \t\t\t\t");
                            }
                            txt.append("|");
                        }
                        HitMis.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.VISIBLE);
                        txt1.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.VISIBLE);
                        txt3.setVisibility(View.VISIBLE);
                        txt4.setVisibility(View.VISIBLE);
                        HitMis.append("                          ");
                        for(int i=0;i<ref_len;i++)
                        {
                            if(HitMiss[i] == 'H') {
                                HitMis.append("  ");
                                HitMis.append(Html.fromHtml("<font color=#028A0F>" + HitMiss[i] +"</font>"));
                                HitMis.append("   ");
                            }
                            else
                            {
                                HitMis.append("  ");
                                HitMis.append(Html.fromHtml("<font color=red>" + HitMiss[i] +"</font>"));
                                HitMis.append("  ");
                            }
                        }
                        double fault_rate=(double)fault*100/ref_len;
                        double hit_rate=(double)hit*100/ref_len;
                        txt1.setText(new StringBuffer("The number of Hits: ").append(hit));
                        txt2.setText(new StringBuilder("The number of Faults: ").append(fault));
                        txt3.setText("Hit Rate: "+String.format("%.2f", hit_rate)+"%");
                        txt4.setText("Fault Rate: "+String.format("%.2f", fault_rate)+"%");
                        opr_simulator.setText("RESET");
                    }
                }
                else if(opr_simulator.getText() == "RESET") {
                    frames=0;
                    pointer = 0;
                    hit = 0;
                    fault = 0;
                    ref_len=0;
                    Arrays.fill(buffer,0);
                    Arrays.fill(reference,0);
                    Arrays.stream(mem_layout).forEach(x->Arrays.fill(x,0));
                    nof.setText("");
                    nof.setEnabled(true);
                    lors.setText("");
                    lors.setEnabled(true);
                    rs.setText("");
                    rs.setEnabled(true);
                    HitMis.setText("");
                    txt.setText("");
                    txt1.setText("");
                    txt2.setText("");
                    txt3.setText("");
                    txt4.setText("");
                    HitMis.setVisibility(View.GONE);
                    txt.setVisibility(View.GONE);
                    txt1.setVisibility(View.GONE);
                    txt2.setVisibility(View.GONE);
                    txt3.setVisibility(View.GONE);
                    txt4.setVisibility(View.GONE);
                    opr_simulator.setText("SIMULATOR");
                }
            }
        });
        return v;
    }
    public Boolean validate()
    {
        String rspatten = "[0-9,]+";
        String nofpatten = "[1-9,]+";
        if(!nof.getText().toString().matches(nofpatten) || nof.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please Enter Valid Number of Frames", Toast.LENGTH_LONG).show();
            return false;
        }
        if(lors.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please Enter Valid Length of the Reference String", Toast.LENGTH_LONG).show();
            return false;
        }
        ref_len = Integer.parseInt(lors.getText().toString());
        String a = rs.getText().toString();
        String[] string = a.split(",");
        if(!rs.getText().toString().matches(rspatten) || rs.getText().toString().equals("") || ref_len != string.length)
        {
            Toast.makeText(getActivity(),"Please Enter Valid Reference String", Toast.LENGTH_LONG).show();
            return false;
        }
        for(int i=0 ; i<ref_len; i++)
        {
            if(Integer.parseInt(string[i])<1)
            {
                Toast.makeText(getActivity(),"Please Enter positive value in Reference String",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}