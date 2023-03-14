package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lab1.R;

public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ImageWork();
    }

    public void ImageWork(){
        ImageView imageView = findViewById(R.id.transition_image);
        final TransitionDrawable transitionDrawable = (TransitionDrawable) imageView.getDrawable();

        Button startTransition = findViewById(R.id.start_transition);
        Button reverseTransition = findViewById(R.id.reverse_transition);
        startTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionDrawable.startTransition(1000);
            }
        });

        reverseTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionDrawable.reverseTransition(1000);
            }
        });
    }
}