package com.example.madu_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madu_project.personaje.FragmentPersonaje;

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
    Button btnResp1;
    Button btnResp2;
    Button btnResp3;
    Button btnResp4;
    MainActivity activity;
    int cont = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_preguntas, container, false);
        activity = (MainActivity) getActivity();
        descPregunta = view.findViewById(R.id.lblDescPregunta);
        progressBar = view.findViewById(R.id.prbarDificultadTiempo);
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

                btnResp1.setText(respuestas[0].getRespuestaDescripcion());
                btnResp2.setText(respuestas[1].getRespuestaDescripcion());
                btnResp3.setText(respuestas[2].getRespuestaDescripcion());
                btnResp4.setText(respuestas[3].getRespuestaDescripcion());



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

                btnResp1.setText(respuestas[0].getRespuestaDescripcion());
                btnResp2.setText(respuestas[1].getRespuestaDescripcion());
                btnResp3.setText(respuestas[2].getRespuestaDescripcion());
                btnResp4.setText(respuestas[3].getRespuestaDescripcion());



                if (cont ==  preguntas.length -1){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("genero",genero);
                    getParentFragmentManager().setFragmentResult("genero",bundle);

                    FragmentManager mg = getFragmentManager();
                    FragmentTransaction fragmentTransaction = mg.beginTransaction();

                    FragmentPersonaje fragmentPersonaje = new FragmentPersonaje();
                    fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentPersonaje);
                    fragmentTransaction.commit();
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









}

