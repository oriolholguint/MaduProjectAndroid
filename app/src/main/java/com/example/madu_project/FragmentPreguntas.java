package com.example.madu_project;


import android.content.res.Resources;
import android.graphics.Color;

import android.graphics.drawable.BitmapDrawable;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;


import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.madu_project.partida.Partida;
import com.example.madu_project.personaje.FragmentPersonaje;

import java.util.Calendar;
import java.util.Date;

public class FragmentPreguntas extends Fragment {

    View view;
    TextView descPregunta;
    Pregunta[] preguntas;
    Respuesta [] respuestas;
    Genero genero;
    ImageView imgPregunta;
    ProgressBar progressBar;
    Button btnSiguietePregunta;
    androidx.constraintlayout.widget.Group grp2Respuestas;
    androidx.constraintlayout.widget.Group grp4Respuestas;
    androidx.constraintlayout.widget.ConstraintLayout clFondoPreguntaGenero;
    androidx.constraintlayout.widget.ConstraintLayout clbotonesRrespuestas;
    androidx.constraintlayout.widget.ConstraintLayout.LayoutParams posicionConstraintBarraRespuestas;
    RadioButton btnResp1;
    RadioButton btnResp2;
    RadioButton btnResp3;
    RadioButton btnResp4;
    RadioButton btnRespVerdadero;
    RadioButton btnRespFalso;
    MainActivity activity;
    TextView lbLPuntos;
    TextView lblItem;
    private int cont = 0;
    private int puntuacion = 0;
    private int pixeles;
    private boolean ultimaPregunta = false;
    Date currentTime = null;
    int dificultad = 0;
    BitmapDrawable fondoGeneroUrl;
    MediaPlayer media;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_preguntas, container, false);
        activity = (MainActivity) getActivity();

        lbLPuntos = activity.findViewById(R.id.lbLPuntos);

        descPregunta = view.findViewById(R.id.lblDescPregunta);
        imgPregunta = view.findViewById(R.id.imgPregunta);


        clFondoPreguntaGenero = view.findViewById(R.id.clFondoPreguntaGenero);
        clbotonesRrespuestas = view.findViewById(R.id.clbotonesRrespuestas);
        posicionConstraintBarraRespuestas = (ConstraintLayout.LayoutParams) clbotonesRrespuestas.getLayoutParams();
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


        btnSiguietePregunta = (Button) view.findViewById(R.id.btnSiguietePregunta);


        pixeles = convertirDpPixeles();

        getParentFragmentManager().setFragmentResultListener("genero", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull  String requestKey, @NonNull Bundle result) {
                genero = (Genero) result.getSerializable("genero");
                musicaGenero();


                descPregunta.setText(genero.getNombre());

                //FUNCIONA METODO OBTENER PREGUNTAS, CAMBIAR CANTIDAD PREGUNTAS PARA PRUEBAS
                preguntas = activity.getPreguntasPartida(genero.getPreguntas(),10,activity.jugador);



                fondoGeneroUrl = new BitmapDrawable("/data/data/com.example.madu_project/files/images/"+genero.getImagenFondo());
                activity.clBackgroundApp.setBackground(fondoGeneroUrl);

                descPregunta.setText(preguntas[cont].getPreguntaDescripcion());

                MoverConstraintLayoutBarraRespuestas(preguntas[cont].getImagen());
                progressBar.setScaleY(2f);

                respuestas = preguntas[cont].getRespuestas();

                llenarRespuestas(respuestas,grp2Respuestas,grp4Respuestas);

                progressAnimation(respuestas,btnResp1,btnResp2,btnResp3,btnResp4,btnRespVerdadero,btnRespFalso,btnSiguietePregunta,progressBar, activity.duracion);



            }
        });





        btnResp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResp2.setChecked(false);
                btnResp3.setChecked(false);
                btnResp4.setChecked(false);

                Verificarultimapregunta();
                VerificarRespuesta(btnResp1.getText().toString(),btnResp1.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
                btnSiguietePregunta.setEnabled(true);
                btnSiguietePregunta.setVisibility(View.VISIBLE);

            }
        });

        btnResp1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnResp1.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnResp1.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });

        btnResp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResp1.setChecked(false);
                btnResp3.setChecked(false);
                btnResp4.setChecked(false);
                Verificarultimapregunta();
                VerificarRespuesta(btnResp2.getText().toString(),btnResp2.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
                btnSiguietePregunta.setEnabled(true);
                btnSiguietePregunta.setVisibility(View.VISIBLE);
            }
        });

        btnResp2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnResp2.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnResp2.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });

        btnResp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResp1.setChecked(false);
                btnResp2.setChecked(false);
                btnResp4.setChecked(false);
                Verificarultimapregunta();
                VerificarRespuesta(btnResp3.getText().toString(),btnResp3.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
                btnSiguietePregunta.setEnabled(true);
                btnSiguietePregunta.setVisibility(View.VISIBLE);
            }
        });

        btnResp3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnResp3.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnResp3.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });

        btnResp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResp1.setChecked(false);
                btnResp2.setChecked(false);
                btnResp3.setChecked(false);
                Verificarultimapregunta();
                VerificarRespuesta(btnResp4.getText().toString(),btnResp4.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
                btnSiguietePregunta.setEnabled(true);
                btnSiguietePregunta.setVisibility(View.VISIBLE);
            }
        });

        btnResp4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnResp4.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnResp4.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });

        btnRespVerdadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRespFalso.setChecked(false);
                Verificarultimapregunta();
                VerificarRespuesta(btnRespVerdadero.getText().toString(),btnRespVerdadero.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                desactivarRadioButtons();
                btnSiguietePregunta.setEnabled(true);
                btnSiguietePregunta.setVisibility(View.VISIBLE);
            }
        });

        btnRespVerdadero.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnRespVerdadero.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnRespVerdadero.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });

        btnRespFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRespVerdadero.setChecked(false);
                Verificarultimapregunta();
                VerificarRespuesta(btnRespFalso.getText().toString(),btnRespFalso.isChecked());
                VerRespuestasCorrectasIncorrectas(btnResp1,btnResp2,btnResp3,btnResp4, btnRespVerdadero, btnRespFalso);
                btnSiguietePregunta.setEnabled(true);
                btnSiguietePregunta.setVisibility(View.VISIBLE);

            }
        });

        btnRespFalso.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnRespFalso.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnRespFalso.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });


        btnSiguietePregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ultimaPregunta) {
                    IrAfragmentPersonaje();
                } else {
                    cont++;
                    btnSiguietePregunta.setEnabled(false);
                    btnSiguietePregunta.setVisibility(View.INVISIBLE);
                    descPregunta.setText(preguntas[cont].getPreguntaDescripcion());

                    MoverConstraintLayoutBarraRespuestas(preguntas[cont].getImagen());

                    respuestas = preguntas[cont].getRespuestas();
                    llenarRespuestas(respuestas,grp2Respuestas,grp4Respuestas);
                    progressAnimation(respuestas,btnResp1,btnResp2,btnResp3,btnResp4,btnRespVerdadero,btnRespFalso,btnSiguietePregunta,progressBar, activity.duracion);
                    restablecerRadioButons();


                }

            }
        });

        btnSiguietePregunta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnSiguietePregunta.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnSiguietePregunta.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });


        return view;
    }


    private void musicaGenero(){
        activity.mediaPlayer.stop();
        activity.mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse("/data/data/com.example.madu_project/files/sound/" + genero.getMusicaFondo()));
        //activity.mediaPlayer.start();
        bucleAudio();

    }

    public void bucleAudio(){
        activity.mediaPlayer.start();
        activity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                activity.mediaPlayer.start();
            }

        });
    }


    private void progressAnimation(Respuesta [] resps,RadioButton r1, RadioButton r2,RadioButton r3,RadioButton r4,RadioButton rVer,RadioButton rFals,Button button,ProgressBar progressBar,int duracion)
    {
        ProgressAnimation animation = new ProgressAnimation(resps,r1,r2,r3,r4,rVer,rFals,button,progressBar,0f,1000f);
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
        btnResp1.setTextColor(Color.WHITE);
        btnResp2.setTextColor(Color.WHITE);
        btnResp3.setTextColor(Color.WHITE);
        btnResp4.setTextColor(Color.WHITE);
        btnRespVerdadero.setTextColor(Color.WHITE);
        btnRespFalso.setTextColor(Color.WHITE);


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

        if(correcto){
            int valorDificultad = 0;
            String[][] dif = {{"30","1"},{"25","2"},{"20","3"}};
            for (String[] e : dif){
                if (e[0].equals(String.valueOf(activity.duracion))) valorDificultad = Integer.parseInt(e[1]);
            }
            puntuacion += (valorDificultad*25) + 50 + (100 - (progressBar.getProgress()/10));//dificultad + tiempo
            lbLPuntos.setText(Integer.toString(puntuacion));
        }


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


    public int convertirDpPixeles(){
        int pixeles;
        float dip = 172f;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );

        pixeles = (int)px;

        return pixeles;
    }

    public void MoverConstraintLayoutBarraRespuestas(String imagen){


        imgPregunta.setBackground(null);

        if(imagen != null ){
            BitmapDrawable menuGeneroUrl = new BitmapDrawable("/data/data/com.example.madu_project/files/images/"+ imagen);
            imgPregunta.setBackground(menuGeneroUrl);
            imgPregunta.setVisibility(View.VISIBLE);
            posicionConstraintBarraRespuestas.setMargins(posicionConstraintBarraRespuestas.leftMargin,450,posicionConstraintBarraRespuestas.rightMargin,posicionConstraintBarraRespuestas.bottomMargin);
            clbotonesRrespuestas.setLayoutParams(posicionConstraintBarraRespuestas);
        } else {
            imgPregunta.setVisibility(View.INVISIBLE);
            posicionConstraintBarraRespuestas.setMargins(posicionConstraintBarraRespuestas.leftMargin,pixeles,posicionConstraintBarraRespuestas.rightMargin,posicionConstraintBarraRespuestas.bottomMargin);
            clbotonesRrespuestas.setLayoutParams(posicionConstraintBarraRespuestas);
        }
    }


    public void Verificarultimapregunta(){
        if (cont ==  preguntas.length -1){
            ultimaPregunta = true;
        }
    }

    public void IrAfragmentPersonaje()
    {
        if(activity.duracion == 30){
            dificultad = 0;
        } else if (activity.duracion == 25){
            dificultad = 1;
        } else if (activity.duracion == 20){
            dificultad = 2;
        }

        activity.layout = "Personaje";
        currentTime = Calendar.getInstance().getTime();

        activity.partida = new Partida(puntuacion,dificultad,currentTime, activity.jugador);

        FragmentManager mg = getFragmentManager();
        FragmentTransaction fragmentTransaction = mg.beginTransaction();

        FragmentPersonaje fragmentPersonaje = new FragmentPersonaje();
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentPersonaje);
        fragmentTransaction.commit();
    }



}

