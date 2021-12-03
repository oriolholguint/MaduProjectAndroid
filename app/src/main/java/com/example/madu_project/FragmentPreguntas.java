package com.example.madu_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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

import java.security.acl.Group;
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
    MainActivity activity;
    int cont = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_preguntas, container, false);
        activity = (MainActivity) getActivity();
        descPregunta = view.findViewById(R.id.lblDescPregunta);
        clbotonesRrespuestas = view.findViewById(R.id.clbotonesRrespuestas);
        progressBar = view.findViewById(R.id.prbarDificultadTiempo);
        grp2Respuestas = view.findViewById(R.id.grp2Respuestas);
        grp4Respuestas = view.findViewById(R.id.grp4Respuestas);

        btnResp1 = view.findViewById(R.id.btnResp1);
        btnResp2 = view.findViewById(R.id.btnResp2);
        btnResp3 = view.findViewById(R.id.btnResp3);
        btnResp4 = view.findViewById(R.id.btnResp4);


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

                Respuesta [] respuestas = preguntas[cont].getRespuestas();

                llenarRespuestas(respuestas,grp2Respuestas,grp4Respuestas);

                if(progressBar.getProgress() == 1000){
                    btnSiguietePregunta.setEnabled(true);
                }

            }
        });


        btnSiguietePregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont++;
                btnSiguietePregunta.setEnabled(false);
                descPregunta.setText(preguntas[cont].getPreguntaDescripcion());



                progressAnimation(btnSiguietePregunta, progressBar, activity.duracion);

                Respuesta [] respuestas = preguntas[cont].getRespuestas();

                llenarRespuestas(respuestas,grp2Respuestas,grp4Respuestas);
                restablecerRadioButons(btnResp1,btnResp2,btnResp3,btnResp4);


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
            btnResp1.setText(resp[0].getRespuestaDescripcion());
            btnResp2.setText(resp[1].getRespuestaDescripcion());
        }

    }


    private void restablecerRadioButons(RadioButton r1, RadioButton r2, RadioButton r3, RadioButton r4){
        r1.setChecked(false);
        r2.setChecked(false);
        r3.setChecked(false);
        r4.setChecked(false);
    }





}

