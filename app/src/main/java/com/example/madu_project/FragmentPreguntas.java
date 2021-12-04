package com.example.madu_project;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FragmentPreguntas extends Fragment {

    View view;
    TextView descPregunta;
    Pregunta[] preguntas;
    Respuesta [] respuestas;
    Genero genero;
    ProgressBar progressBar;
    Button btnSiguietePregunta;
    androidx.constraintlayout.widget.Group grp2Respuestas;
    androidx.constraintlayout.widget.Group grp4Respuestas;
    androidx.constraintlayout.widget.ConstraintLayout clbotonesRrespuestas;
    RadioButton btnResp1;
    RadioButton btnResp2;
    RadioButton btnResp3;
    RadioButton btnResp4;
    RadioButton btnRespVerdadero;
    RadioButton btnRespFalso;
    Boolean correcta = false;
    MainActivity activity;
    TextView lbLPuntos;
    private int cont = 0;
    private int puntuacion = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_preguntas, container, false);
        activity = (MainActivity) getActivity();

        lbLPuntos = activity.findViewById(R.id.lbLPuntos);

        descPregunta = view.findViewById(R.id.lblDescPregunta);
        clbotonesRrespuestas = view.findViewById(R.id.clbotonesRrespuestas);
        progressBar = view.findViewById(R.id.prbarDificultadTiempo);
        grp2Respuestas = view.findViewById(R.id.grp2Respuestas);
        grp4Respuestas = view.findViewById(R.id.grp4Respuestas);
        Group grpPuntuacion = activity.findViewById(R.id.grpPuntuacion);
        grpPuntuacion.setVisibility(View.VISIBLE);

        btnResp1 = view.findViewById(R.id.btnResp1);
        btnResp2 = view.findViewById(R.id.btnResp2);
        btnResp3 = view.findViewById(R.id.btnResp3);
        btnResp4 = view.findViewById(R.id.btnResp4);



        btnRespVerdadero = view.findViewById(R.id.btnRespVerdadero);
        btnRespFalso = view.findViewById(R.id.btnRespFalso);


        btnSiguietePregunta = view.findViewById(R.id.btnSiguietePregunta);

        getParentFragmentManager().setFragmentResultListener("genero", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull  String requestKey, @NonNull Bundle result) {
                genero = (Genero) result.getSerializable("genero");
                descPregunta.setText(genero.getNombre());

                preguntas = genero.getPreguntas();

                descPregunta.setText(preguntas[cont].getPreguntaDescripcion());

                progressBar.setScaleY(2f);
                progressAnimation(btnSiguietePregunta,progressBar, activity.duracion);

                respuestas = preguntas[cont].getRespuestas();

                llenarRespuestas(respuestas,grp2Respuestas,grp4Respuestas);

                if(progressBar.getProgress() == 1000){
                    btnSiguietePregunta.setEnabled(true);
                }

            }
        });

        btnResp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                btnResp2.setChecked(false);
                btnResp3.setChecked(false);
                btnResp4.setChecked(false);

                VerificarRespuesta(btnResp1.getText().toString(),btnResp1.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();

            }
        });

        btnResp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResp1.setChecked(false);
                btnResp3.setChecked(false);
                btnResp4.setChecked(false);

                VerificarRespuesta(btnResp2.getText().toString(),btnResp2.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
            }
        });


        btnResp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResp1.setChecked(false);
                btnResp2.setChecked(false);
                btnResp4.setChecked(false);

                VerificarRespuesta(btnResp3.getText().toString(),btnResp3.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);

                desactivarRadioButtons();
            }
        });

        btnResp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResp1.setChecked(false);
                btnResp2.setChecked(false);
                btnResp3.setChecked(false);

                VerificarRespuesta(btnResp4.getText().toString(),btnResp4.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
            }
        });

        btnRespVerdadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRespFalso.setChecked(false);

                VerificarRespuesta(btnRespVerdadero.getText().toString(),btnRespVerdadero.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
            }
        });

        btnRespFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRespVerdadero.setChecked(false);
                VerificarRespuesta(btnRespFalso.getText().toString(),btnRespFalso.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
            }
        });


        btnSiguietePregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont++;
                btnSiguietePregunta.setEnabled(false);
                descPregunta.setText(preguntas[cont].getPreguntaDescripcion());


                progressAnimation(btnSiguietePregunta, progressBar, activity.duracion);

                respuestas = preguntas[cont].getRespuestas();

                llenarRespuestas(respuestas,grp2Respuestas,grp4Respuestas);
                restablecerRadioButons();


                if (cont ==  preguntas.length -1){
                    /*
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("genero",genero);
                    getParentFragmentManager().setFragmentResult("genero",bundle);

                    FragmentManager mg = getFragmentManager();
                    FragmentTransaction fragmentTransaction = mg.beginTransaction();

                    FragmentPersonaje fragmentPersonaje = new FragmentPersonaje();
                    fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentPersonaje);
                    fragmentTransaction.commit();

                     */
                }





            }
        });


        return view;
    }


    private void progressAnimation(Button button, ProgressBar progressBar,int duracion)
    {
        ProgressAnimation animation = new ProgressAnimation(button,progressBar,0f,1000f);
        int milisegundos = duracion * 1000;
        animation.setDuration(milisegundos);
        progressBar.startAnimation(animation);
    }

    private void llenarRespuestas(Respuesta[] resp, androidx.constraintlayout.widget.Group grpRes2, androidx.constraintlayout.widget.Group grpRes4)
    {

        if(resp.length == 4){
            grpRes2.setVisibility(View.INVISIBLE);
            grpRes4.setVisibility(View.VISIBLE);
            btnResp1.setText(resp[0].getRespuestaDescripcion());
            btnResp2.setText(resp[1].getRespuestaDescripcion());
            btnResp3.setText(resp[2].getRespuestaDescripcion());
            btnResp4.setText(resp[3].getRespuestaDescripcion());
        } else {
            grpRes2.setVisibility(View.VISIBLE);
            grpRes4.setVisibility(View.INVISIBLE);
            btnRespVerdadero.setText(resp[0].getRespuestaDescripcion());
            btnRespFalso.setText(resp[1].getRespuestaDescripcion());
        }

    }


    private void restablecerRadioButons(){
        btnResp1.setChecked(false);
        btnResp2.setChecked(false);
        btnResp3.setChecked(false);
        btnResp4.setChecked(false);
        btnRespVerdadero.setChecked(false);
        btnRespFalso.setChecked(false);

        btnResp1.setEnabled(true);
        btnResp2.setEnabled(true);
        btnResp3.setEnabled(true);
        btnResp4.setEnabled(true);
        btnRespVerdadero.setEnabled(true);
        btnRespFalso.setEnabled(true);

        btnResp1.setBackgroundResource(R.drawable.bg_radiobutton1);
        btnResp2.setBackgroundResource(R.drawable.bg_radiobutton2);
        btnResp3.setBackgroundResource(R.drawable.bg_radiobutton3);
        btnResp4.setBackgroundResource(R.drawable.bg_radiobutton4);
        btnRespVerdadero.setBackgroundResource(R.drawable.bg_radiobutton2);
        btnRespFalso.setBackgroundResource(R.drawable.bg_radiobutton1);
    }

    private void desactivarRadioButtons()
    {
        btnResp1.setEnabled(false);
        btnResp2.setEnabled(false);
        btnResp3.setEnabled(false);
        btnResp4.setEnabled(false);
        btnRespVerdadero.setEnabled(false);
        btnRespFalso.setEnabled(false);
    }





    private void VerificarRespuesta(String respuesta, Boolean esCorrecta)
    {
        boolean correcto = false;

        for(int i = 0; i < respuestas.length;i++)
        {
            if(respuesta.equals(respuestas[i].getRespuestaDescripcion()) && esCorrecta == respuestas[i].isEsCorrecta()){
                correcto = true;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(correcto){

            builder.setMessage("correcto")
                    .setTitle(respuesta);

            AlertDialog dialog = builder.create();
            dialog.show();

            puntuacion += 100;
            lbLPuntos.setText(Integer.toString(puntuacion));

        }


    }


    private void VerRespuestasCorrectasIncorrectas(RadioButton r1, RadioButton r2, RadioButton r3, RadioButton r4, RadioButton rVer, RadioButton rFals)
    {
        if(respuestas.length == 4)
        {
            if(r1.getText().equals(respuestas[0].getRespuestaDescripcion()) && respuestas[0].isEsCorrecta()){
                r1.setBackgroundResource(R.drawable.bg_respuesta_correcta);
            } else {
                r1.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

            if(r2.getText().equals(respuestas[1].getRespuestaDescripcion()) && respuestas[1].isEsCorrecta()){
                r2.setBackgroundResource(R.drawable.bg_respuesta_correcta);
            } else {
                r2.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

            if(r3.getText().equals(respuestas[2].getRespuestaDescripcion()) && respuestas[2].isEsCorrecta()){
                r3.setBackgroundResource(R.drawable.bg_respuesta_correcta);
            } else {
                r3.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

            if(r4.getText().equals(respuestas[3].getRespuestaDescripcion()) && respuestas[3].isEsCorrecta()){
                r4.setBackgroundResource(R.drawable.bg_respuesta_correcta);
            } else {
                r4.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }
        } else {

            if(rVer.getText().equals(respuestas[0].getRespuestaDescripcion()) && respuestas[0].isEsCorrecta()){
                rVer.setBackgroundResource(R.drawable.bg_respuesta_correcta);
            } else {
                rVer.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

            if(rFals.getText().equals(respuestas[1].getRespuestaDescripcion()) && respuestas[1].isEsCorrecta()){
                rFals.setBackgroundResource(R.drawable.bg_respuesta_correcta);
            } else {
                rFals.setBackgroundResource(R.drawable.bg_respuesta_incorrecta);
            }

        }




    }


}

