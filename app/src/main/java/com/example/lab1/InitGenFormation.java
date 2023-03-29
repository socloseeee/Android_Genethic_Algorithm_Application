package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InitGenFormation extends AppCompatActivity {

    private static final String PREFS_methods_FILE = "Methods_choose";

    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_gen_formation);
        settings = getSharedPreferences(PREFS_methods_FILE, MODE_PRIVATE);
    }

    public void specificMethod(View v) {
        prefEditor = settings.edit();
        Button text = (Button) findViewById(R.id.specific_method);
        prefEditor.putString("method", text.getText().toString());
        prefEditor.apply();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == -1){
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context,"Выберите метод!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
        }
        else{
            settings = getSharedPreferences("Chosen_method", MODE_PRIVATE);
            prefEditor = settings.edit();

            RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
            prefEditor.putString("Chosen_method", radioButton.getText().toString());
            prefEditor.apply();

            Intent intent = new Intent(this, GenesBoundFormation.class);
            startActivity(intent);
        }
    }

    public void allMethods(View v) {
        prefEditor = settings.edit();
        Button text = (Button) findViewById(R.id.all_methods);
        prefEditor.putString("method", text.getText().toString());
        prefEditor.apply();
        Intent intent = new Intent(this, GenesBoundFormation.class);
        startActivity(intent);
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, MatrixRepeats.class);
        startActivity(intent);
    }
}