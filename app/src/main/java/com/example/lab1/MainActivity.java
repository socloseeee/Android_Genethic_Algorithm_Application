package com.example.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "Matrix_variables";
    private static final String PREF_m = "m";
    private static final String PREF_n = "n";
    private static final String PREF_T1 = "T1";
    private static final String PREF_T2 = "T2";
    private static final String PREF_z = "z";
    private static final String PREF_k = "k";
    private static final String PREF_Pk = "Pk";
    private static final String PREF_Pm = "Pm";
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
    }

    public void NextActivity(View v) {

        EditText mBox = (EditText) findViewById(R.id.m);
        String m = mBox.getText().toString();

        EditText nBox = (EditText) findViewById(R.id.n);
        String n = nBox.getText().toString();

        EditText T1Box = (EditText) findViewById(R.id.T1);
        String T1 = T1Box.getText().toString();

        EditText T2Box = (EditText) findViewById(R.id.T2);
        String T2 = T2Box.getText().toString();

        EditText zBox = (EditText) findViewById(R.id.z);
        String z = zBox.getText().toString();

        EditText kBox = (EditText) findViewById(R.id.k);
        String k = kBox.getText().toString();

        EditText PkBox = (EditText) findViewById(R.id.Pk);
        String Pk = PkBox.getText().toString();

        EditText PmBox = (EditText) findViewById(R.id.Pm);
        String Pm = PmBox.getText().toString();
// сохраняем его в настройках

        SharedPreferences.Editor prefEditor = settings.edit();
        try {

            byte mByte = Byte.parseByte(m);
            if (mByte < 0 || mByte > 10){
                throw new Exception();
            }

            byte nByte = Byte.parseByte(n);
            if (nByte < 0 || nByte > 8){
                throw new Exception();
            }

            byte T1Byte = Byte.parseByte(T1);
            if (T1Byte < 0 || T1Byte > 100){
                throw new Exception();
            }

            byte T2Byte = Byte.parseByte(T2);
            if (T2Byte < 0 || T2Byte > 100 || T1Byte > T2Byte){
                throw new Exception();
            }

            byte zByte = Byte.parseByte(z);
            if (zByte < 0 || zByte > 100){
                throw new Exception();
            }

            byte kByte = Byte.parseByte(k);
            if (kByte < 0 || kByte > 50){
                throw new Exception();
            }

            byte PkByte = Byte.parseByte(Pk);
            if (PkByte < 0 || PkByte > 100){
                throw new Exception();
            }

            byte PmByte = Byte.parseByte(Pm);
            if (PmByte < 0 || PmByte > 100){
                throw new Exception();
            }

            prefEditor.putInt(PREF_m, mByte);
            prefEditor.putInt(PREF_n, nByte);
            prefEditor.putInt(PREF_T1, T1Byte);
            prefEditor.putInt(PREF_T2, T2Byte);
            prefEditor.putInt(PREF_z, zByte);
            prefEditor.putInt(PREF_k, kByte);
            prefEditor.putInt(PREF_Pk, PkByte);
            prefEditor.putInt(PREF_Pm, PmByte);
            prefEditor.apply();

            Intent intent = new Intent(this, MatrixChoose.class);
            startActivity(intent);
        } catch (Exception e) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context,"Введено неверное значение!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
        }
    }
}
