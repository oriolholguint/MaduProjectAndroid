package com.example.madu_project;

import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class ProgressAnimation extends Animation
{

    private Respuesta[] respuestas;
    private Button button;
    private RadioButton r1;
    private RadioButton r2;
    private RadioButton r3;
    private RadioButton r4;
    private RadioButton rVer;
    private RadioButton rFals;
    private ProgressBar progressBar;
    private float from;
    private float to;


    public ProgressAnimation(Respuesta[] respuestas,RadioButton r1,RadioButton r2,RadioButton r3,RadioButton r4,RadioButton rVer,RadioButton rFals,Button button, ProgressBar progressBar, float from, float to) {
        super();
        this.respuestas = respuestas;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
        this.rVer = rVer;
        this.rFals =rFals;
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
            desactivarRadioButtons(r1,r2,r3,r4,rVer,rFals);
            VerRespuestasCorrectasIncorrectas(r1,r2,r3,r4,rVer,rFals);

        }



    }

    private void desactivarRadioButtons(RadioButton r1, RadioButton r2, RadioButton r3, RadioButton r4, RadioButton rVer, RadioButton rFals)
    {
        r1.setEnabled(false);
        r2.setEnabled(false);
        r3.setEnabled(false);
        r4.setEnabled(false);
        rVer.setEnabled(false);
        rFals.setEnabled(false);
    }

    private void VerRespuestasCorrectasIncorrectas(RadioButton r1, RadioButton r2, RadioButton r3, RadioButton r4, RadioButton rVer, RadioButton rFals)
    {
        if(respuestas.length == 4)
        {
            if(r1.getText().equals(respuestas[0].getRespuestaDescripcion()) && respuestas[0].isEsCorrecta()){
                r1.setBackgroundResource(R.drawable.bg_respuesta_correcta);
                r1.setTextColor(Color.BLACK);
            } else {
                r1.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

            if(r2.getText().equals(respuestas[1].getRespuestaDescripcion()) && respuestas[1].isEsCorrecta()){
                r2.setBackgroundResource(R.drawable.bg_respuesta_correcta);
                r2.setTextColor(Color.BLACK);
            } else {
                r2.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

            if(r3.getText().equals(respuestas[2].getRespuestaDescripcion()) && respuestas[2].isEsCorrecta()){
                r3.setBackgroundResource(R.drawable.bg_respuesta_correcta);
                r3.setTextColor(Color.BLACK);
            } else {
                r3.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

            if(r4.getText().equals(respuestas[3].getRespuestaDescripcion()) && respuestas[3].isEsCorrecta()){
                r4.setBackgroundResource(R.drawable.bg_respuesta_correcta);
                r4.setTextColor(Color.BLACK);
            } else {
                r4.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }
        } else {

            if(rVer.getText().equals(respuestas[0].getRespuestaDescripcion()) && respuestas[0].isEsCorrecta()){
                rVer.setBackgroundResource(R.drawable.bg_respuesta_correcta);
                rVer.setTextColor(Color.BLACK);
            } else {
                rVer.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

            if(rFals.getText().equals(respuestas[1].getRespuestaDescripcion()) && respuestas[1].isEsCorrecta()){
                rFals.setBackgroundResource(R.drawable.bg_respuesta_correcta);
                rFals.setTextColor(Color.BLACK);
            } else {
                rFals.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }
        }
    }






}
