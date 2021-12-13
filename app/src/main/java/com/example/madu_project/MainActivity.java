package com.example.madu_project;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;


import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.madu_project.idioma.FragmentIdioma;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public String layout = "Idioma";
    public String status = "Pricipal";
    public Genero[] generos;
    public Genero[] generosAux;
    public Dialog settingsDialog;
    public int duracion;
    private ArrayAdapter mAdapter;
    public Partida partida;
    public Jugador jugador;
    public Genero generoSelect;
    public ConstraintLayout clBackgroundApp;
    public TextView LblNombreJugador;
    public ImageView imgAvatar;
    public TextView LblEdad;
    public Animation buttonUp, buttonDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        duracion = 30;
        partida = null;
        jugador = new Jugador(null,false,0);

        clBackgroundApp = findViewById(R.id.clBackgroundApp);

        buttonUp = AnimationUtils.loadAnimation(this,R.anim.button_up);
        buttonDown = AnimationUtils.loadAnimation(this,R.anim.button_down);

        LblNombreJugador = findViewById(R.id.LblNombreJugador);
        imgAvatar = findViewById(R.id.imgAvatar);
        LblEdad = findViewById(R.id.lblEdad);

        ImageButton imgBtnConfiguracion = findViewById(R.id.imgBtnConfiguracion);
        androidx.constraintlayout.widget.Group grpDatosUsuario = findViewById(R.id.grpDatosUsuario);

        TextView lbLPuntos = findViewById(R.id.lbLPuntos);
        androidx.fragment.app.FragmentManager mgr = getSupportFragmentManager();
        final androidx.fragment.app.FragmentTransaction fragmentTransaction = mgr.beginTransaction();

        final FragmentBotones fragmentBotones = new FragmentBotones();

        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales, fragmentBotones);
        fragmentTransaction.commit();

        settingsDialog = new Dialog(this);
        settingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(R.layout.settings_dialog);

        androidx.constraintlayout.widget.Group grpPuntuacion = findViewById(R.id.grpPuntuacion);
        androidx.constraintlayout.widget.Group grpMenu = settingsDialog.findViewById(R.id.grpMenu);
        androidx.constraintlayout.widget.Group grpDificultad = settingsDialog.findViewById(R.id.grpDificultad);


        final ImageButton btnSalir = settingsDialog.findViewById(R.id.btnSalir);
        final ImageButton btnMenu = settingsDialog.findViewById(R.id.btnMenu);


        final Spinner sprDificultad = (Spinner) settingsDialog.findViewById(R.id.sprDificultad);
        ArrayList<String> spritems = llenarSpinner();


        mAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spritems);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sprDificultad.setAdapter(mAdapter);

        final FragmentMenu fragmentMenu = new FragmentMenu();

        sprDificultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // duracion = sprDificultad.getSelectedItemPosition() * 5 + 20;
                int index = sprDificultad.getSelectedItemPosition();

                duracion = 30 - (5 * index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        imgBtnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.show();


                if(layout.equals("Preguntas") || layout.equals("Ranking")){


                    grpMenu.setVisibility(View.VISIBLE);
                    grpDificultad.setVisibility(View.INVISIBLE);

                } else {
                    grpMenu.setVisibility(View.INVISIBLE);
                }

            }
        });

        imgBtnConfiguracion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        imgBtnConfiguracion.startAnimation(buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        imgBtnConfiguracion.startAnimation(buttonDown);
                        break;
                }
                return false;
            }
        });


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(layout.equals("Preguntas") || layout.equals("Ranking")){

                    layout = "Menu";
                    clBackgroundApp.setBackgroundResource(R.drawable.fondojuego);
                    volverAMenu(fragmentMenu);

                    partida = null;
                    grpMenu.setVisibility(View.INVISIBLE);
                    grpDificultad.setVisibility(View.VISIBLE);
                    grpPuntuacion.setVisibility(View.INVISIBLE);
                    lbLPuntos.setText("0");

                    settingsDialog.cancel();
                }

            }
        });

        btnMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnMenu.startAnimation(buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnMenu.startAnimation(buttonDown);
                        break;
                }
                return false;
            }
        });


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(status.equals("Juego")){
                    clBackgroundApp.setBackgroundResource(R.drawable.fondojuego);

                    volverAlPrincipio(fragmentBotones);

                    grpDatosUsuario.setVisibility(View.INVISIBLE);
                    status = "Principal";
                    layout = "Idioma";
                    generos = null;
                    partida = null;
                    jugador = null;
                    androidx.constraintlayout.widget.Group grpDificultad = settingsDialog.findViewById(R.id.grpDificultad);
                    grpDificultad.setVisibility(View.INVISIBLE);
                    grpPuntuacion.setVisibility(View.INVISIBLE);
                    jugador = new Jugador(null,false,0);
                    lbLPuntos.setText("0");



                } else {
                    if (layout.equals("Idioma")) {
                        MainActivity.this.finish();
                        System.exit(0);
                    } else {
                        volverAIdioma();
                        layout = "Idioma";
                        fragmentBotones.grpBotones.setVisibility(View.INVISIBLE);
                        fragmentBotones.btnSiguienteCentro.setVisibility(View.VISIBLE);
                        generos = null;
                    }


                }


                settingsDialog.cancel();


            }
        });


        btnSalir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnSalir.startAnimation(buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnSalir.startAnimation(buttonDown);
                        break;
                }
                return false;
            }
        });


    }


    public ArrayList<String> llenarSpinner() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Facil");
        items.add("Medio");
        items.add("Dificil");

        return items;

    }

    public void AnimacionIzquierdaADerecha(FragmentTransaction fragmentTransaction){
        fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right,R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,R.anim.exit_right_to_left);
    }


    public void volverAMenu(FragmentMenu fragmentMenu){

        FragmentManager mg = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mg.beginTransaction();
        AnimacionIzquierdaADerecha(fragmentTransaction);
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales, fragmentMenu);
        fragmentTransaction.commit();
    }

    public void volverAlPrincipio(FragmentBotones fragmentBotones){
        FragmentManager mg = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mg.beginTransaction();
        AnimacionIzquierdaADerecha(fragmentTransaction);
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales, fragmentBotones);
        fragmentTransaction.commit();
    }

    public void volverAIdioma(){
        FragmentManager mg = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mg.beginTransaction();
        AnimacionIzquierdaADerecha(fragmentTransaction);
        FragmentIdioma fragmentIdioma = new FragmentIdioma();
        fragmentTransaction.replace(R.id.frLContenedorFragments, fragmentIdioma);
        fragmentTransaction.commit();
    }


    /**
     * Se obtienen preguntas aleatorias de un array enviado por parametros.
     *
     * @param preguntas         array donde obtener preguntas
     * @param cantidadPreguntas cantidad de preguntas aleatorias a obtener
     * @return array con preguntas aleatorias
     */
    public static Pregunta[] getPreguntasPartida(Pregunta[] preguntas, int cantidadPreguntas, Jugador jugador) {
        Pregunta[] preguntasSeleccionadas = new Pregunta[cantidadPreguntas]; //Preguntas que saldran en la partida
        ArrayList<Integer> numerosAleatorios = new ArrayList<>(); //Numeros aleatorios para no repetirlos
        int numeroAleatorio = 0;

        for (int i = 0; i < cantidadPreguntas; i++) //Bucle con la cantidad de preguntas necesarias
        {
            boolean preguntaCorrecta = false;

            while (!preguntaCorrecta) //Bucle para elegir pregunta que no se repita
            {
                numeroAleatorio = obtenerNumeroAleatorio(0, preguntas.length - 1);
                int counter = 0;
                boolean numeroRepetido = false;
                //Si el num no esta repetido y aun quedan por revisar mas numeros entra aqui
                while (!numeroRepetido && counter < numerosAleatorios.size()) {
                    //Si el num aleatorio esta repetido o el jugador es menor de edad y la pregunta es para mayor de edad
                    if (numeroAleatorio == numerosAleatorios.get(counter) || (!jugador.getEsMayorEdad() && preguntas[numeroAleatorio].isEsMayorEdad())) {
                        numeroRepetido = true;
                    } else //Si el num aleatorio no esta repetido
                    {
                        counter++; //Itera el contador para comprobar siguiente numero
                    }
                }

                if (!numeroRepetido)//Si el numero no esta repetido
                {
                    //Agrego el numero aleatorio al array de los numeros que ya han salido
                    numerosAleatorios.add(numeroAleatorio);
                    //Agrego la pregunta al array de preguntas selecciondas
                    preguntasSeleccionadas[i] = preguntas[numeroAleatorio];
                    preguntaCorrecta = true; //Señalo que la respuesta se agrega correctamente para salir del bucle
                }
            }
        }

        return preguntasSeleccionadas;
    }

    /**
     * Devuelve un numero aleatorio dado un minimo y un maximo, ambos incluidos
     *
     * @param min numero minimo a obtener
     * @param max numero maximo a obtener
     * @return numero aleatorio
     */
    public static int obtenerNumeroAleatorio(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range + min);
    }
}
