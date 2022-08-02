package com.example.pagereplacementsimulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ComparisonAlgorithm extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar vm_toolbar;
    EditText nof,rs,lors;
    Button ca_simulator;
    TextView txt1,txt2,txt3,txt4,txt5;
    TextView txt6,txt7,txt8,txt9,txt10,HitMis;
    int hits_of_each_algorithm[]=new int[4];
    boolean b1,b2,b3,b4;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int frames, pointer = 0, hit = 0, fault = 0,ref_len,i=0;
    int buffer[]; //buffer is a queue used to simulate the FIFO algorithm
    int reference[]; //Integer array to store the reference string
    int mem_layout[][]; //2D array to represent memory layout with rows representing frame number and columns representing length of reference string
    static boolean color[][];
    char HitMiss[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_algorithm);
        vm_toolbar = (Toolbar) findViewById(R.id.vm_toolbar);
        setSupportActionBar(vm_toolbar);
        setTitle("Comparision of Algorithm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        vm_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nof = findViewById(R.id.nof);
        rs = findViewById(R.id.rs);
        lors = findViewById(R.id.lors);
        ca_simulator = findViewById(R.id.ca_simulator);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        HitMis = findViewById(R.id.HitMis);
        txt6 = findViewById(R.id.txt6);
        txt7 = findViewById(R.id.txt7);
        txt8 = findViewById(R.id.txt8);
        txt9 = findViewById(R.id.txt9);
        txt10 = findViewById(R.id.txt10);
        ca_simulator.setText("SIMULATOR");
        ca_simulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ca_simulator.getText() == "SIMULATOR") {
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


                        int mem_layout[][]= new int[ref_len][frames];
                        FIFO(frames,ref_len,reference);
                        LIFO(frames,ref_len,reference);
                        LRU(frames,ref_len,reference);
                        OptimalReplacement(frames,ref_len,reference);

                        int best_algo_index=findIndexOfLargestElementInArray(hits_of_each_algorithm);
                        switch(best_algo_index)
                        {
                            case 0:
                                b1=true;
                                txt5.setText("FIFO is the best algorithm for given input");
                                FIFO(frames,ref_len,reference);
                                break;
                            case 1:
                                b2=true;
                                txt5.setText("LIFO is the best algorithm for given input");
                                LIFO(frames,ref_len,reference);
                                break;
                            case 2:
                                b3=true;
                                txt5.setText("LRU is the best algorithm for given input");
                                LRU(frames,ref_len,reference);
                                break;
                            case 3:
                                b4=true;
                                txt5.setText("Optimal Replacement is the best algorithm for given input");
                                OptimalReplacement(frames,ref_len,reference);
                                break;
                        }
                    }

                }
                else if(ca_simulator.getText() == "RESET") {
                    frames=0;
                    pointer = 0;
                    hit = 0;
                    fault = 0;
                    ref_len=0;
                    Arrays.fill(buffer,0);
                    Arrays.fill(reference,0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Arrays.stream(mem_layout).forEach(x->Arrays.fill(x,0));
                    }
                    nof.setText("");
                    nof.setEnabled(true);
                    lors.setText("");
                    lors.setEnabled(true);
                    rs.setText("");
                    rs.setEnabled(true);
                    txt1.setText("");
                    txt2.setText("");
                    txt3.setText("");
                    txt4.setText("");
                    txt5.setText("");
                    txt6.setText("");
                    txt7.setText("");
                    txt8.setText("");
                    txt9.setText("");
                    txt10.setText("");
                    HitMis.setText("");
                    HitMis.setVisibility(View.GONE);
                    txt1.setVisibility(View.GONE);
                    txt2.setVisibility(View.GONE);
                    txt3.setVisibility(View.GONE);
                    txt4.setVisibility(View.GONE);
                    txt5.setVisibility(View.GONE);
                    txt6.setVisibility(View.GONE);
                    txt7.setVisibility(View.GONE);
                    txt8.setVisibility(View.GONE);
                    txt9.setVisibility(View.GONE);
                    txt10.setVisibility(View.GONE);
                    ca_simulator.setText("SIMULATOR");
                }
            }
        });
    }
    public void FIFO(int frames,int ref_len,int reference[])
    {

        int hit=0,fault=0,pointer=0;
        int buffer[]; //buffer is a queue used to simulate the FIFO algorithm
        int mem_layout[][]; //2D array to represent memory layout with rows representing frame number and columns representing length of reference string
        mem_layout = new int[ref_len][frames]; //setting dimensions of mem_layout array
        buffer = new int[frames]; //setting no. of frames in buffer queue
        char HitMiss[]=new char[ref_len];
        for (int j = 0; j < frames; j++)
        {
            buffer[j] = -1; //initialising all the frames to -1
            //-1 signifies frame is empty
        }
        for (int i = 0; i < ref_len; i++)
        {
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
                buffer[pointer] = reference[i]; //Pushes the current page into the queue
                fault++;                        //Increments Page Fault
                HitMiss[i]='M';
                pointer++;                      //Pointer is a variable used to change the index in the queue buffer. It signifies which page is to be replaced according to FIFO algorithm
                if (pointer == frames)
                    pointer = 0;                //if index becomes equal to frame number, we reset the pointer variable.
            }
            for (int j = 0; j < frames; j++)
            {
                mem_layout[i][j] = buffer[j];   //Updating value in main memory
            }
        }
        for (int j = 0 ; j < frames; j ++) {
            for(int i = 0 ; i < ref_len ; i++) {
                color[i][j] = false;
                if(mem_layout[i][j] == reference[i])
                    color[i][j] = true;
            }
        }
        if(b1==true)
        {
            display(frames,ref_len,mem_layout,HitMiss,hit,fault,color);
        }
        else
        {
            txt1.setText("No. of hits in FIFO algorithm: " + hit);
            hits_of_each_algorithm[0] = hit;
        }

    }
    public void LIFO(int frames,int ref_len,int reference[])
    {

        int hit=0,fault=0,pointer=0;
        int buffer[]; //buffer is a queue used to simulate the FIFO algorithm
        int mem_layout[][]; //2D array to represent memory layout with rows representing frame number and columns representing length of reference string
        mem_layout = new int[ref_len][frames]; //setting dimensions of mem_layout array
        buffer = new int[frames]; //setting no. of frames in buffer queue
        char HitMiss[]=new char[ref_len];
        for (int j = 0; j < frames; j++)
        {
            buffer[j] = -1; //initialising all the frames to -1
            //-1 signifies frame is empty
        }
        for (int i = 0; i < ref_len; i++)
        {
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

                fault++;//Increments Page Fault
                //Pointer is a variable used to change the index in the queue buffer. It signifies which page is to be replaced according to FIFO algorithm
                HitMiss[i]='M';
                if (pointer == frames)
                {
                    buffer[frames - 1] = reference[i]; //Pushes the current page into the queue
                }
                else
                {
                    buffer[pointer] = reference[i]; //Pushes the current page into the queue
                    pointer++;
                }//if index becomes equal to frame number, we reset the pointer variable.
            }
            for (int j = 0; j < frames; j++)
            {
                mem_layout[i][j] = buffer[j];   //Updating value in main memory
            }
        }
        for (int j = 0 ; j < frames; j ++) {
            for(int i = 0 ; i < ref_len ; i++) {
                color[i][j] = false;
                if(mem_layout[i][j] == reference[i])
                    color[i][j] = true;
            }
        }
        if(b2==true)
        {
            display(frames,ref_len,mem_layout,HitMiss,hit,fault,color);
        }
        else
        {
            txt2.setText("No. of hits in LIFO algorithm: " + hit);
            hits_of_each_algorithm[1] = hit;
        }

    }
    public void LRU(int frames,int ref_len,int reference[])
    {
        int hit=0,fault=0,pointer=0;
        Boolean isFull = false;
        ArrayList<Integer> stack = new ArrayList<Integer>();
        int buffer[]; //buffer is a queue used to simulate the FIFO algorithm
        int mem_layout[][]; //2D array to represent memory layout with rows representing frame number and columns representing length of reference string
        mem_layout = new int[ref_len][frames]; //setting dimensions of mem_layout array
        buffer = new int[frames]; //setting no. of frames in buffer queue
        char HitMiss[]=new char[ref_len];
        for (int i = 0; i < ref_len; i++)
        {
            if (stack.contains(reference[i])) {            //We keep updating the stack according to how recently a page occurs in the reference string
                stack.remove(stack.indexOf(reference[i])); //If a page is being repeated, we remove it from the stack and then push it back on top of the stack
            }
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
                    int min_loc = ref_len; //min_loc is a variable used to locate the index of the element in buffer array which at the lowest position in the stack
                    for (int j = 0; j < frames; j++) //This part is used to find which element in the buffer array is present at the lowest position in the stack *
                    //Whichever element in the buffer array is lowest in the stack, is replaced by the current page *
                    {
                        if (stack.contains(buffer[j]))
                        {
                            int temp = stack.indexOf(buffer[j]);
                            if (temp < min_loc)
                            {
                                min_loc = temp;
                                pointer = j; //The final pointer value after completing the loop indicates the index of the element in the buffer array which has the lowest position in the stack. *
                            }
                        }
                    }
                }
                buffer[pointer] = reference[i]; //Pushes the page into the buffer array according to the pointer value
                fault++;
                HitMiss[i]='M';                 //Increments Page Fault
                pointer++;                      //Pointer is a variable used to change the index in the queue buffer. It signifies which page is to be replaced
                if (pointer == frames)
                {
                    pointer = 0;                //if index becomes equal to frame number, we reset the pointer variable.
                    isFull = true;              //flag to check if buffer is full or not *
                }
            }
            for (int j = 0; j < frames; j++)
            {
                mem_layout[i][j] = buffer[j];
            }
        }
        for (int j = 0 ; j < frames; j ++) {
            for(int i = 0 ; i < ref_len ; i++) {
                color[i][j] = false;
                if(mem_layout[i][j] == reference[i])
                    color[i][j] = true;
            }
        }
        if(b3==true)
        {
            display(frames,ref_len,mem_layout,HitMiss,hit,fault,color);
        }
        else
        {
            txt3.setText("No. of hits in LRU algorithm: " + hit);
            hits_of_each_algorithm[2] = hit;
        }

    }
    public void OptimalReplacement(int frames,int ref_len,int reference[])
    {
        int hit=0,fault=0,pointer=0;
        Boolean isFull = false;
        int buffer[]; //buffer is a queue used to simulate the FIFO algorithm
        int mem_layout[][]; //2D array to represent memory layout with rows representing frame number and columns representing length of reference string
        mem_layout = new int[ref_len][frames]; //setting dimensions of mem_layout array
        buffer = new int[frames]; //setting no. of frames in buffer queue
        char HitMiss[]=new char[ref_len];
        for(int j = 0; j < frames; j++)
        {
            buffer[j] = -1; //initialising all the frames to -1
            //-1 signifies frame is empty
        }

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
        if(b4==true)
        {
            display(frames,ref_len,mem_layout,HitMiss,hit,fault,color);
        }
        else
        {
            txt4.setText("No. of hits in Optimal Replacement algorithm: " + hit);
            hits_of_each_algorithm[3] = hit;
        }
    }
    static int findIndexOfLargestElementInArray(int a[])
    {
        int n=a.length;
        int index=0;
        int largest=a[0];
        for(int i=0; i<n; i++)
        {
            if(a[i]>largest)
            {
                largest=a[i];
                index=i;
            }

        }
        return index;
    }
    public void display(int frames,int ref_len,int mem_layout[][],char HitMiss[],int hit,int fault,boolean color[][])
    {
        txt6.setText("");
        for (int i = 0; i < frames; i++) {
            txt6.append("\n");
            txt6.append(" | Frame ");
            if((i+1)>9)
                txt6.append((i + 1) + " :\t\t\t");
            else
                txt6.append((i + 1) + "   :\t\t\t");
            for (int j = 0; j < ref_len; j++) {
                if (mem_layout[j][i] > -1)
                    if (color[j][i] == true) {
                        txt6.append("|\t\t");
                        txt6.append(Html.fromHtml("<font color=#028A0F>" + mem_layout[j][i] + "</font>"));
                        txt6.append("\t\t");
                    } else
                        txt6.append("|\t\t" + mem_layout[j][i] + "\t\t");
                else
                    txt6.append("|  \t\t\t\t");
            }
            txt6.append("|");
        }
        HitMis.setVisibility(View.VISIBLE);
        txt1.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.VISIBLE);
        txt3.setVisibility(View.VISIBLE);
        txt4.setVisibility(View.VISIBLE);
        txt5.setVisibility(View.VISIBLE);
        txt6.setVisibility(View.VISIBLE);
        txt7.setVisibility(View.VISIBLE);
        txt8.setVisibility(View.VISIBLE);
        txt9.setVisibility(View.VISIBLE);
        txt10.setVisibility(View.VISIBLE);
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
        txt7.setText(new StringBuffer("The number of Hits: ").append(hit));
        txt8.setText(new StringBuilder("The number of Faults: ").append(fault));
        txt9.setText("Hit Rate: "+String.format("%.2f", hit_rate)+"%");
        txt10.setText("Fault Rate: "+String.format("%.2f", fault_rate)+"%");
        ca_simulator.setText("RESET");
    }
    public Boolean validate()
    {
        String rspatten = "[0-9,]+";
        if(nof.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Number of Frames", Toast.LENGTH_LONG).show();
            return false;
        }
        if(lors.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Length of the Reference String", Toast.LENGTH_LONG).show();
            return false;
        }
        ref_len = Integer.parseInt(lors.getText().toString());
        String a = rs.getText().toString();
        String[] string = a.split(",");
        if(!rs.getText().toString().matches(rspatten) || rs.getText().toString().equals("") || ref_len != string.length)
        {
            Toast.makeText(getApplicationContext(),"Please Enter Valid Reference String", Toast.LENGTH_LONG).show();
            return false;
        }
        for(int i=0;i<ref_len;i++)
        {
            if(Integer.parseInt(string[i])<1)
            {
                Toast.makeText(getApplicationContext(),"Please Enter Positive Value in Reference String",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}