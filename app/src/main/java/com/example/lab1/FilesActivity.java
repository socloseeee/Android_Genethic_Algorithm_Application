package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilesActivity extends AppCompatActivity {

    private final static String FILE_NAME = "content.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
    }

    public void saveText(View view){
        FileOutputStream fos = null;

        try{
            EditText textBox = (EditText) findViewById(R.id.saving);
            String text = textBox.getText().toString();

            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);

            fos.write(text.getBytes());
            Toast.makeText(this,"Файл сохранен",Toast.LENGTH_SHORT).show();
        }catch (IOException exception){
            Toast.makeText(this, exception.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {
            try {
                if(fos!=null) fos.close();
            }catch (IOException exception){
                Toast.makeText(this, exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void openText(View view){
        FileInputStream fin = null;
        TextView textView = (TextView) findViewById(R.id.open_text);
        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            textView.setText(text);
        }catch (IOException exception){
            Toast.makeText(this, exception.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {
            try {
                if(fin!=null) fin.close();
            }catch (IOException exception){
                Toast.makeText(this, exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}