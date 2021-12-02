package com.example.madu_project;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressAnimation extends Animation
{

    private Button button;
    private ProgressBar progressBar;
    private float from;
    private float to;


    public ProgressAnimation(Button button, ProgressBar progressBar, float from, float to) {
        super();
        this.button = button;
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);


        if (value == to){
            button.setEnabled(true);
        }



    }

}
