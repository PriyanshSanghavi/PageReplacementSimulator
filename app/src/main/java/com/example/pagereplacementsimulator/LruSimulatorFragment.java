package com.example.pagereplacementsimulator;

import android.graphics.Color;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class LruSimulatorFragment extends Fragment //Space Complexity:O(ref_len*frames) //Time Complexity:O(ref_len * frames)
{
    EditText nof,rs,lors;
    Button lru_simulator;
    TextView txt1,txt2,txt3,txt4,txt,HitMis;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int frames, pointer = 0, hit = 0, fault = 0, ref_len;
    Boolean isFull = false;
    int buffer[]; //buffer is an array which denotes a particular column in the mem_layout
    ArrayList<Integer> stack = new ArrayList<Integer>();
    int reference[]; //Integer array to store the reference string
    int mem_layout[][]; //2D array to represent memory layout with rows representing frame number and columns representing length of reference string
    boolean color[][];
    char HitMiss[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lru_simulator, container, false);
        nof = v.findViewById(R.id.nof);
        rs = v.findViewById(R.id.rs);
        lors = v.findViewById(R.id.lors);
        lru_simulator = v.findViewById(R.id.lru_simulator);
        txt = v.findViewById(R.id.txt);
        HitMis = v.findViewById(R.id.HitMis);
        txt1 = v.findViewById(R.id.txt1);
        txt2 = v.findViewById(R.id.txt2);
        txt3 = v.findViewById(R.id.txt3);
        txt4 = v.findViewById(R.id.txt4);
        lru_simulator.setText("SIMULATOR");
        lru_simulator.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(lru_simulator.getText() == "SIMULATOR") {
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
                        buffer = new int[frames]; //setting no. of frames in buffer array
                        for (int j = 0; j < frames; j++)
                            buffer[j] = -1; //initialising all the frames to -1
                            //-1 signifies frame is empty
                        String a = rs.getText().toString();
                        String[] string = a.split(",");
                        for (int i = 0; i < string.length; i++)
                            reference[i] = Integer.valueOf(string[i]);
                        //Time Complexity:O(ref_len * frames)
                        for (int i = 0; i < ref_len; i++)
                        {
                            if (stack.contains(reference[i]))              //We keep updating the stack according to how recently a page occurs in the reference string
                                stack.remove(stack.indexOf(reference[i])); //If a page is being repeated, we remove it from the stack and then push it back on the top of the stack
                            stack.add(reference[i]);
                            int search = -1; //flag to detect page fault
                            for (int j = 0; j < frames; j++)
                            {
                                if (buffer[j] == reference[i]) //checking if page is already inside main memory
                                {
                                    search = j; //changing value of flag variable if page is present in main memory
                                    hit++; //incrementing page hit count
                                    HitMiss[i]='H';
                                    break;
                                }
                            }
                            if (search == -1) // if page is not inside main memory
                            {
                                if (isFull == true) //checking if buffer is full or not
                                {
                                    int min_loc = ref_len; //min_loc is a variabe used to locate the index of the element in buffer array which is at the lowest position in the stack
                                    for (int j = 0; j < frames; j++) //This for loop is used to find which element in the buffer array is present at the lowest position in the stack
                                    {                                //Whichever element in the buffer array is lowest in the stack, is replaced by the current page.
                                        if (stack.contains(buffer[j]))
                                        {
                                            int temp = stack.indexOf(buffer[j]);
                                            if (temp < min_loc)
                                            {
                                                min_loc = temp;
                                                pointer = j; //The final pointer value after completing the loop indicates the index of the element
                                                             // in the buffer array which has the lowest position in the stack
                                            }
                                        }
                                    }
                                }
                                buffer[pointer] = reference[i];//Pushes the current page into the queue
                                HitMiss[i]='M';
                                fault++;                        //Increments Page Fault
                                pointer++;                      //Pointer is a variable used to change the index in the queue buffer. It signifies which page is to be replaced
                                if (pointer == frames)
                                {
                                    pointer = 0;                //if index becomes equal to frame number, we reset the pointer variable.
                                    isFull = true;              //flag to check if buffer is full or not
                                }
                            }
                            for (int j = 0; j < frames; j++)
                                mem_layout[i][j] = buffer[j];
                        }
                        for (int j = 0 ; j < frames; j ++) {
                            for(int i = 0 ; i < ref_len ; i++) {
                                color[i][j] = false;
                                if(mem_layout[i][j] == reference[i])
                                    color[i][j] = true;
                            }
                        }
                        for (int i = 0; i < frames; i++) {
                            txt.append("\n");
                            txt.append(" | Frame ");
                            if((i+1)>9)
                                txt.append((i + 1) + " :\t\t\t");
                            else
                                txt.append((i + 1) + "   :\t\t\t");
                            for (int j = 0; j < ref_len; j++) {
                                if (mem_layout[j][i] == -1) {
                                    txt.append("|  \t\t\t\t");
                                } else {
                                    if (color[j][i] == true) {
                                        txt.append("|\t\t");
                                        txt.append(Html.fromHtml("<font color=#028A0F>" + mem_layout[j][i] + "</font>"));
                                        txt.append("\t\t");
                                    } else
                                        txt.append("|\t\t" + mem_layout[j][i] + "\t\t");
                                }
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
                        lru_simulator.setText("RESET");
                    }
                }
                else if(lru_simulator.getText() == "RESET") {
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
                    lru_simulator.setText("SIMULATOR");
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