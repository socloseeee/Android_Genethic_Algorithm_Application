package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NameFilesActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "Account";
    private static final String PREF_NAМE = "Name";
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_files);
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
    }

    public void saveName(View view) {
// получаем введенное имя
        EditText nameBox = (EditText) findViewById(R.id.nameBox);
        String name = nameBox.getText().toString();
// сохраняем его в настройках
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(PREF_NAМE, name);
        prefEditor.apply();
    }

    public void getName(View view) {
// получаем сохраненное имя
        TextView nameView = (TextView) findViewById(R.id.nameView);
        String name = settings.getString(PREF_NAМE, "n/a");
        nameView.setText(name);
    }
}