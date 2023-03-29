package com.example.lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowImage extends AppCompatActivity {

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        settings = getSharedPreferences("byte_img", MODE_PRIVATE);
        String val = settings.getString("byte_img", "n/a");
        ImageView img = (ImageView) findViewById(R.id.text_view);
        byte[] decodedBytes = Base64.decode(val, 0);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        img.setImageBitmap(bmp);
    }

    public void goBack(View v) {
        // super.onBackPressed();
        Intent intent = new Intent(this, ShowHystograms.class);
        startActivity(intent);
    }

}