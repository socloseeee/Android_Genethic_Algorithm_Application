package com.example.lab1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;



class GThread extends Thread {

    public String JSON;
    GThread(String JSON){
        super();
        this.JSON = JSON;
    }

    public void run(){

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("scripts");

        pyobj.callAttr("main", JSON);
    }
}

class KThread extends Thread {

    public TextView tv;
    public ProgressBar progressBar;
    public GThread AnotherThread;
    public int repeat;
    public String isCreateWay;


    KThread(TextView tv, GThread AnotherThread, ProgressBar progressBar, int repeat, String isCreateWay) {
        super();
        this.tv = tv;
        this.AnotherThread = AnotherThread;
        this.progressBar = progressBar;
        this.repeat = repeat;
        this.isCreateWay = isCreateWay;
    }

    @SuppressLint("SetTextI18n")
    public void run() {

        int i = 0;
        int number = 0;
        int val = 18000;
        if (!Objects.equals(isCreateWay, "Все методы")) {
            val = val / 4;
        }
        progressBar.setMax((int) (val * repeat * 1.9 * 100));
        while (AnotherThread.isAlive()) {
            i++;
            number = (int) (i / (val * repeat * 1.9));
            progressBar.setProgress(i);
            if (number < 90) {
                tv.setText((int) number + "%");
            }
        }
        while (progressBar.getProgress() < (int) (val * repeat * 1.9 * 100)) {
            i += 3;
            number = (int) ((i) / (val * repeat * 1.9));
            tv.setText((int) number + "%");
            progressBar.setProgress((int) (i));
        }
    }
}


public class GenethicAlgorythmScript extends AppCompatActivity {

    SharedPreferences settings;

    String finalMyString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genethic_algorythm_script);

        settings = getSharedPreferences("Matrix_variables", MODE_PRIVATE);
        Map all_var = settings.getAll();
        Object m = all_var.get("m");
        Object n = all_var.get("n");
        Object T1 = all_var.get("T1");
        Object T2 = all_var.get("T2");
        Object z = all_var.get("z");
        Object k = all_var.get("k");
        Object Pk = all_var.get("Pk");
        Object Pm = all_var.get("Pm");

        settings = getSharedPreferences("matrix", MODE_PRIVATE);
        String Matrix = settings.getString("Matrix", "");
        all_var.put("Matrix", Matrix);

        settings = getSharedPreferences("repeats", MODE_PRIVATE);
        int repeat = settings.getInt("repeats", 0);
        all_var.put("repeat", repeat);

        settings = getSharedPreferences("Methods_choose", MODE_PRIVATE);
        String isCreateWay = settings.getString("method", "");
        all_var.put("isCreateWay", isCreateWay);

        settings = getSharedPreferences("Chosen_method", MODE_PRIVATE);
        String createWay = settings.getString("Chosen_method", "");
        all_var.put("createWay", createWay);

        settings = getSharedPreferences("processor_bounds_formation", MODE_PRIVATE);
        String bounds = settings.getString("Chosen_method", "");
        all_var.put("bounds", bounds);


        // Все значения переменных
        StringJoiner joiner = new StringJoiner("\n");
        for (Object item : all_var.entrySet()) {
            joiner.add("\t\"" + item.toString().replace("=", "\": \"") + "\",");
        }
        String myString = "{\n" + joiner.toString();
        myString = myString.substring(0, myString.length() - 2) + "\"\n}";

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        TextView tv = (TextView) findViewById(R.id.textView3);

        Button btn = (Button) findViewById(R.id.button4);

        Button json_btn = (Button) findViewById(R.id.button6);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String finalMyString = myString;
        if (progressBar.getProgress() == 100){
            btn.setVisibility(Button.VISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(Button.INVISIBLE);
                progressBar.setVisibility(ProgressBar.VISIBLE);

                GThread script = new GThread(finalMyString);
                KThread output = new KThread(tv, script, progressBar, (Integer) repeat, isCreateWay);

                output.start();
                script.start();
            }
        });

        json_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, finalMyString, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0,0);
                toast.show();
            }
        });

    }

    public void NextActivity(View v) {
        Intent intent = new Intent(this, ShowHystograms.class);
        startActivity(intent);
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, GenesBoundFormation.class);
        startActivity(intent);
    }
}