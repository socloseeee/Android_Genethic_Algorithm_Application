package com.example.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GenesBoundFormation extends AppCompatActivity {

    private static final String PREFS_bounds_FILE = "processor_bounds_formation";

    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genes_bound_formation);
    }


    public void NextActivity(View v) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId == -1){
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context,"Выберите метод!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
        }
        else{
            settings = getSharedPreferences(PREFS_bounds_FILE, MODE_PRIVATE);
            prefEditor = settings.edit();

            RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
            prefEditor.putString("Chosen_method", radioButton.getText().toString());
            prefEditor.apply();
            Intent intent = new Intent(this, GenethicAlgorythmScript.class);
            startActivity(intent);
        }
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, InitGenFormation.class);
        startActivity(intent);
    }
}