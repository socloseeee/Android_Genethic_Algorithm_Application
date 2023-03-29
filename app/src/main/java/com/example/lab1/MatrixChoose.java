package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;


public class MatrixChoose extends AppCompatActivity {

//    public static final String txtData = "123445";
    private static final String PREFS_FILE_VARIABLES = "Matrix_variables";

    private static final String PREF_m = "m";
    private static final String PREF_n = "n";
    private static final String PREF_T1 = "T1";
    private static final String PREF_T2 = "T2";

    private static final String PREF_FILE_MATRIX = "matrix";
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_choose);
    }


    public void goBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveMatrix(View view) {
        settings = getSharedPreferences(PREFS_FILE_VARIABLES, MODE_PRIVATE);
// получаем введенные переменные
        int m = settings.getInt(PREF_m, 0);
        int n = settings.getInt(PREF_n, 0);
        int T1 = settings.getInt(PREF_T1, 0);
        int T2 = settings.getInt(PREF_T2, 0);
        settings = getSharedPreferences(PREF_FILE_MATRIX, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        int[][] mas = new int[m][n];

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                mas[i][j] = T1 + (int) (Math.random() * (T2 - T1) + 1);
//                prefEditor.putInt("MyArray"+Integer.toString(i)+Integer.toString(j), mas[i][j]);
            }
        }
        prefEditor.putString("Matrix", Arrays.deepToString(mas));
        prefEditor.apply();

        Intent intent = new Intent(this, MatrixRepeats.class);
        startActivity(intent);
    }

    public void getMatrix(View view) {
// получаем сохраненное имя
        TextView nameView = (TextView) findViewById(R.id.nameView);
//        String name = settings.getString(PREF_NAМE, "n/a");
//        nameView.setText(name);
        Intent intent = new Intent(this, MatrixRepeats.class);
        startActivity(intent);
    }

}