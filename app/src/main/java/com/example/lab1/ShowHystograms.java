package com.example.lab1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


class ScriptThread extends Thread {

    public Thread setImage;
    ScriptThread(Thread setImage){
        super();
        this.setImage = setImage;
    }

    public void run(){

        Python py = Python.getInstance();
        final PyObject pyobj1 = py.getModule("visualizing_histogram");
        pyobj1.callAttr("main");

        final PyObject pyobj2 = py.getModule("summary_results");
        pyobj2.callAttr("main");

        setImage.start();
    }
}

class ProgressBarThread extends Thread {

    public ScriptThread scriptThread;
    public Thread setImage;
    public ProgressBar progressBar;
    public TextView progressTv;

    ProgressBarThread(ScriptThread scriptThread, Thread setImage, ProgressBar progressBar, TextView progressTv) {
        super();
        this.scriptThread = scriptThread;
        this.setImage = setImage;
        this.progressBar = progressBar;
        this.progressTv = progressTv;
    }

    @SuppressLint("SetTextI18n")
    public void run() {

        int i = 0;
        int number = 0;
        int val = 14000;
        progressBar.setMax(val * 100);
        while (scriptThread.isAlive() || setImage.isAlive()) {
            i++;
            number = (int) (i / val);
            if (number < 95) {
                progressBar.setProgress(i);
                progressTv.setText(number + "%");
            }
        }
        while (progressBar.getProgress() < (int) (val * 100)) {
            i += 3;
            number = (int) (i / val);
            progressTv.setText((int) number + "%");
            progressBar.setProgress(i);
        }
        progressTv.setText((int) 100 + "%");
    }
}


public class ShowHystograms extends AppCompatActivity {

    SharedPreferences settings;

    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hystograms);

        ImageView[] masIV = new ImageView[]{
                findViewById(R.id.image_view1), findViewById(R.id.image_view2),
                findViewById(R.id.image_view3), findViewById(R.id.image_view4),
                findViewById(R.id.image_view5), findViewById(R.id.image_view6),
                findViewById(R.id.image_view7), findViewById(R.id.image_view8),
                findViewById(R.id.image_view9), findViewById(R.id.image_view10),
                findViewById(R.id.image_view11), findViewById(R.id.image_view12),
                findViewById(R.id.image_view13), findViewById(R.id.image_view14),
                findViewById(R.id.image_view15), findViewById(R.id.image_view16),
                findViewById(R.id.image_view17), findViewById(R.id.image_view18),
                findViewById(R.id.image_view19), findViewById(R.id.image_view20),
                findViewById(R.id.image_view21), findViewById(R.id.image_view22)
        };

        masIV[0].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_50r+50d.png")));
        masIV[1].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_25r+75d.png")));
        masIV[2].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_75r+25d.png")));
        masIV[3].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_50pz+50b.png")));
        masIV[4].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_all.png")));
        masIV[5].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_50r+50d.png")));
        masIV[6].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_25r+75d.png")));
        masIV[7].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_75r+25d.png")));
        masIV[8].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_50pz+50b.png")));
        masIV[9].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_all.png")));
        masIV[10].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_50r+50d.png")));
        masIV[11].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_25r+75d.png")));
        masIV[12].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_75r+25d.png")));
        masIV[13].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_50pz+50b.png")));
        masIV[14].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_all.png")));
        masIV[15].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_50r+50d.png")));
        masIV[16].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_25r+75d.png")));
        masIV[17].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_75r+25d.png")));
        masIV[18].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_50pz+50b.png")));
        masIV[19].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_all.png")));
        masIV[20].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/all_bounds_results.png")));
        masIV[21].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/all_methods_results.png")));

        AlertDialog.Builder aboutDialog = new AlertDialog.Builder(
                ShowHystograms.this)
                .setMessage("Сгенерировать новые диаграммы?")
                .setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        process(masIV);
                    }
                });

        aboutDialog.create();
        aboutDialog.show();


    }

    @SuppressLint("SdCardPath")
    public void process(ImageView[] masIV){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView progressTv = (TextView) findViewById(R.id.textView15);

        Thread setImage = new Thread() {
            @SuppressLint("SdCardPath")
            @Override
            public void run() {
                runOnUiThread(() -> {
                    masIV[0].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_50r+50d.png")));
                    masIV[1].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_25r+75d.png")));
                    masIV[2].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_75r+25d.png")));
                    masIV[3].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_50pz+50b.png")));
                    masIV[4].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_all.png")));
                    masIV[5].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_50r+50d.png")));
                    masIV[6].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_25r+75d.png")));
                    masIV[7].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_75r+25d.png")));
                    masIV[8].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_50pz+50b.png")));
                    masIV[9].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_all.png")));
                    masIV[10].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_50r+50d.png")));
                    masIV[11].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_25r+75d.png")));
                    masIV[12].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_75r+25d.png")));
                    masIV[13].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_50pz+50b.png")));
                    masIV[14].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_all.png")));
                    masIV[15].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_50r+50d.png")));
                    masIV[16].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_25r+75d.png")));
                    masIV[17].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_75r+25d.png")));
                    masIV[18].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_50pz+50b.png")));
                    masIV[19].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_all.png")));
                    masIV[20].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/all_bounds_results.png")));
                    masIV[21].setImageURI(Uri.fromFile(new File("/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/all_methods_results.png")));
                });
            }
        };
        ScriptThread script = new ScriptThread(setImage);
        ProgressBarThread output = new ProgressBarThread(script, setImage, progressBar, progressTv);

        script.start();
        output.start();
        //setImage.start();
    }

    @SuppressLint("SdCardPath")
    public void showPic(View v){

        settings = getSharedPreferences("byte_img", MODE_PRIVATE);

        Map<Integer, String> dict = new HashMap<Integer, String>();
        dict.put(R.id.image_view1, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_50r+50d.png");
        dict.put(R.id.image_view2, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_25r+75d.png");
        dict.put(R.id.image_view3, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_75r+25d.png");
        dict.put(R.id.image_view4, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_50pz+50b.png");
        dict.put(R.id.image_view5, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/left_bound/Result_all.png");
        dict.put(R.id.image_view6, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_50r+50d.png");
        dict.put(R.id.image_view7, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_25r+75d.png");
        dict.put(R.id.image_view8, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_75r+25d.png");
        dict.put(R.id.image_view9, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_50pz+50b.png");
        dict.put(R.id.image_view10, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/right_bound/Result_all.png");
        dict.put(R.id.image_view11, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_50r+50d.png");
        dict.put(R.id.image_view12, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_25r+75d.png");
        dict.put(R.id.image_view13, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_75r+25d.png");
        dict.put(R.id.image_view14, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_50pz+50b.png");
        dict.put(R.id.image_view15, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/central_bound/Result_all.png");
        dict.put(R.id.image_view16, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_50r+50d.png");
        dict.put(R.id.image_view17, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_25r+75d.png");
        dict.put(R.id.image_view18, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_75r+25d.png");
        dict.put(R.id.image_view19, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_50pz+50b.png");
        dict.put(R.id.image_view20, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/histograms/random_bound/Result_all.png");
        dict.put(R.id.image_view21, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/all_bounds_results.png");
        dict.put(R.id.image_view22, "/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/all_methods_results.png");
        SharedPreferences.Editor editor = settings.edit();

        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(dict.get(v.getId()));
        Log.println(Log.WARN, "2", String.valueOf(bitmap));
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        editor.putString("byte_img", temp);
        editor.apply();
        Intent intent = new Intent(this, ShowImage.class);
        startActivity(intent);
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, GenethicAlgorythmScript.class);
        startActivity(intent);
    }
}