package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MatrixRepeats extends AppCompatActivity {

    private static final String PREFS_m_FILE = "Matrix_variables";
    private static final String PREFS_FILE = "matrix";

    private static final String PREFS_REPEATS_FILE = "repeats";
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeats);
        show_matrix();
    }

    public void show_matrix(){
        settings = getSharedPreferences(PREFS_m_FILE, MODE_PRIVATE);
        int m = settings.getInt("m", 0);

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        String str = settings.getString("Matrix", "n/a");
        str = str.substring(2, str.length() - 2);
        String[] massives = str.split("], \\[");
        TextView matrix = (TextView) findViewById(R.id.matrix);
        for (int i = 0; i < m; i++){
            massives[i] = massives[i].replaceAll("[^0-9 ]", " ");
        }
        matrix.setText(String.join("\n", massives));
    }

    public void set_repeats() throws Exception {
        EditText repeats = (EditText) findViewById(R.id.repeats);
        settings = getSharedPreferences(PREFS_REPEATS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        int repeats_val = Integer.parseInt(String.valueOf(repeats.getText()));
        if (repeats_val < 1 || repeats_val > 100){
            throw new Exception();
        }
        else {
            prefEditor.putInt("repeats", repeats_val);
            prefEditor.apply();
        }
    }

    public void NextActivity(View v) {
        try{
            set_repeats();
            Intent intent = new Intent(this, InitGenFormation.class);
            startActivity(intent);
        } catch (Exception e) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context,"Значение должно быть от 1 до 100!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
        }
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, MatrixChoose.class);
        startActivity(intent);
    }
}