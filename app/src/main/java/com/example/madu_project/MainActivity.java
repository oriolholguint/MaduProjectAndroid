package com.example.madu_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.madu_project.idioma.FragmentIdioma;
import com.example.madu_project.partida.Partida;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public String layout = "Idioma";
    public String status = "Pricipal";
    
    public Genero[] generos; //Generos musicales que hay en el juego

    //Dificultades del juego
    public Spinner sprDificultad; //Spinner con las dificultades que hay en el juego
    private ArrayAdapter adapterDificultad; //Se guardan los strings de las dificultades del juego
    public int dificultadMenu; //Facil: 0, Medio: 1, Dificil: 2

    public Partida partida; //Objeto partida que se juega
    public int duracion; //Duracion de la pregunta
    public Jugador jugador; //Objeto jugador que juega al juego
    public Genero generoSelect; //Genero seleccionado para jugar
    public ConstraintLayout clBackgroundApp;

    //Views de la barra de menu superior
    public TextView LblNombreJugador; //Nombre del jugador
    public ImageView imgAvatar; //Avatar del jugador
    public TextView LblEdad; //Edad del jugador
    public Dialog settingsDialog; //Cuadro emergente de opciones
    public TextView lbLPuntos; //Puntos de la partida aparece si esta en una partida

    //Grupos de los views anteriores
    public FrameLayout frmSombra; //Sombra que aparece cuando se esta en el FragmentPreguntas
    public Group grpPuntuacion;
    public Group grpMenu;
    public Group grpDificultad;

    public Animation buttonUp, buttonDown;
    public MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    public ConstraintLayout dialogSettings;



    @Override
    public void onBackPressed() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Bloqueo orientacion de la aplicacion en landscape


        frmSombra = findViewById(R.id.frmSombra);
        dialogSettings = findViewById(R.id.dialogSettings);
        //Bloqueo orientacion de la aplicacion en landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ocultarBarrasDispositivo(); //Ocultar barra de navegacion y barra de notificaciones

        frmSombra = findViewById(R.id.frmSombra);


        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        mediaPlayer = MediaPlayer.create(this, R.raw.musica_juego_madu); //Objeto de la musica del juego
        bucleAudio(); //Inicia la musica del menu
        duracion = 30; //La duracion de cada pregunta es 30 ya que por defecto se juega en facil
        jugador = new Jugador();

        clBackgroundApp = findViewById(R.id.clBackgroundApp);

        buttonUp = AnimationUtils.loadAnimation(this,R.anim.button_up);
        buttonDown = AnimationUtils.loadAnimation(this,R.anim.button_down);

        LblNombreJugador = findViewById(R.id.LblNombreJugador);
        imgAvatar = findViewById(R.id.imgAvatar);
        LblEdad = findViewById(R.id.lblEdad);

        ImageButton imgBtnConfiguracion = findViewById(R.id.imgBtnConfiguracion);
        Group grpDatosUsuario = findViewById(R.id.grpDatosUsuario);

        lbLPuntos = findViewById(R.id.lbLPuntos);
        FragmentManager mgr = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = mgr.beginTransaction();

        final FragmentBotones fragmentBotones = new FragmentBotones();

        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales, fragmentBotones);
        fragmentTransaction.commit();

        settingsDialog = new Dialog(this);

        settingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(R.layout.settings_dialog);
        settingsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        grpPuntuacion = findViewById(R.id.grpPuntuacion);
        grpMenu = settingsDialog.findViewById(R.id.grpMenu);
        grpDificultad = settingsDialog.findViewById(R.id.grpDificultad);

        SeekBar seekBar = settingsDialog.findViewById(R.id.sbrVolumen);
        //seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ImageButton btnSalir = settingsDialog.findViewById(R.id.btnSalir);
        final ImageButton btnMenu = settingsDialog.findViewById(R.id.btnMenu);

        crearSpinnerDificultad();

        final FragmentMenu fragmentMenu = new FragmentMenu();

        sprDificultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = sprDificultad.getSelectedItemPosition();
                dificultadMenu = (int) adapterDificultad.getItemId(index);
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

                if(layout.equals("Preguntas") || layout.equals("Personaje") || layout.equals("Ranking") || layout.equals("RankingFinal")){

                    grpMenu.setVisibility(View.VISIBLE);
                    grpDificultad.setVisibility(View.INVISIBLE);

                } else {
                    grpMenu.setVisibility(View.INVISIBLE);
                }

            }
        });

        settingsDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                ocultarBarrasDispositivo();
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

                if (status.equals("Juego")){
                    if(layout.equals("Preguntas") || layout.equals("Personaje") || layout.equals("RankingFinal")){
                        layout = "Menu";
                        frmSombra.setVisibility(View.INVISIBLE);
                        clBackgroundApp.setBackgroundResource(R.drawable.fondo_menu_madu);
                        frmSombra.setVisibility(View.INVISIBLE);
                        volverAMenu(fragmentMenu);
                        partida = null;
                        grpMenu.setVisibility(View.INVISIBLE);
                        grpDificultad.setVisibility(View.VISIBLE);
                        grpPuntuacion.setVisibility(View.INVISIBLE);
                        lbLPuntos.setText("0");
                        stopAudio();
                        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.musica_juego_madu);
                        bucleAudio();

                    }
                    else {
                        layout = "Menu";
                        frmSombra.setVisibility(View.INVISIBLE);
                        clBackgroundApp.setBackgroundResource(R.drawable.fondo_menu_madu);
                        volverAMenu(fragmentMenu);
                        partida = null;
                        grpMenu.setVisibility(View.INVISIBLE);
                        grpDificultad.setVisibility(View.VISIBLE);
                        grpPuntuacion.setVisibility(View.INVISIBLE);
                        lbLPuntos.setText("0");
                    }
                }
                settingsDialog.cancel();

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
                    if(layout.equals("Preguntas") || layout.equals("Personaje") || layout.equals("RankingFinal")){
                        frmSombra.setVisibility(View.INVISIBLE);
                        clBackgroundApp.setBackgroundResource(R.drawable.fondo_playa_cola);
                        volverAlPrincipio(fragmentBotones);
                        grpDatosUsuario.setVisibility(View.INVISIBLE);
                        frmSombra.setVisibility(View.INVISIBLE);
                        status = "Principal";
                        layout = "Idioma";
                        partida = null;
                        jugador = null;
                        grpDificultad = settingsDialog.findViewById(R.id.grpDificultad);
                        grpDificultad.setVisibility(View.INVISIBLE);
                        grpPuntuacion.setVisibility(View.INVISIBLE);
                        jugador = new Jugador(null,false,0);
                        lbLPuntos.setText("0");
                        stopAudio();
                        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.musica_juego_madu);
                        bucleAudio();
                    } else {
                        clBackgroundApp.setBackgroundResource(R.drawable.fondo_playa_cola);

                        volverAlPrincipio(fragmentBotones);

                        grpDatosUsuario.setVisibility(View.INVISIBLE);
                        status = "Principal";
                        layout = "Idioma";
                        partida = null;
                        jugador = null;
                        grpDificultad = settingsDialog.findViewById(R.id.grpDificultad);
                        grpDificultad.setVisibility(View.INVISIBLE);
                        grpPuntuacion.setVisibility(View.INVISIBLE);
                        jugador = new Jugador(null,false,0);

                        lbLPuntos.setText("0");
                    }

                    //dialogSettings.setBackgroundResource(R.drawable.entrada_settings);

                } else {
                    if (layout.equals("Idioma")) {
                        MainActivity.this.finish();
                        System.exit(0);
                    } else {
                        volverAIdioma();
                        layout = "Idioma";
                        fragmentBotones.grpBotones.setVisibility(View.INVISIBLE);
                        fragmentBotones.btnSiguienteCentro.setVisibility(View.VISIBLE);
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

    @Override
    protected void onPause() {
        super.onPause();
        pauseAudio();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAudio();
        ocultarBarrasDispositivo();
    }

    public void crearSpinnerDificultad()
    {
        sprDificultad = settingsDialog.findViewById(R.id.sprDificultad);
        ArrayList<String> spritems = llenarSpinnerDificultad();
        adapterDificultad = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spritems);
        adapterDificultad.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sprDificultad.setAdapter(adapterDificultad);
    }

    public ArrayList<String> llenarSpinnerDificultad() {
        ArrayList<String> items = new ArrayList<>();

        items.add(getString(R.string.facil));
        items.add(getString(R.string.medio));
        items.add(getString(R.string.dificil));

        return items;
    }

    public void bucleAudio(){
        startAudio();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
               startAudio();
            }

        });
    }

    public void startAudio(){
        mediaPlayer.start();
    }

    public void pauseAudio(){
        mediaPlayer.pause();
    }

    public void stopAudio(){
        mediaPlayer.stop();
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

    public void ocultarBarrasDispositivo()
    {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(Ventana.WINDOW_SETTINGS);
    }

    /**
     * Se obtienen preguntas aleatorias de un array enviado por parametros.
     * @param preguntas array donde obtener preguntas
     * @param cantidadPreguntas cantidad de preguntas aleatorias a obtener
     * @return array con preguntas aleatorias
     */
    public Pregunta[] getPreguntasPartida(Pregunta[] preguntas, int cantidadPreguntas, Jugador jugador)
    {
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
     * Se obtienen preguntas aleatorias de los generos musicales que hay en la aplicacion aleatoriamente.
     * @param cantidadPreguntas cantidad de preguntas aleatorias a obtener
     * @param jugador jugador para saber si es menor o mayor de edad
     * @return array con preguntas aleatorias
     */
    public Pregunta[] getPreguntasMix(int cantidadPreguntas, Jugador jugador)
    {
        Pregunta[] preguntasSeleccionadas = new Pregunta[cantidadPreguntas];
        Genero generoSeleccionado;
        int numeroAleatorio;
        Pregunta pregunta;

        for(int i = 0; i < cantidadPreguntas; i++)
        {
            //Selecciono el genero del cual sacare la pregunta
            generoSeleccionado = generos[obtenerNumeroAleatorio(0, generos.length - 2)];

            boolean preguntaCorrecta = false;

            while (!preguntaCorrecta) //Bucle para elegir pregunta que no se repita y sea para la edad correcta
            {
                //Obtengo un numero aleatorio referente a la cantidad de preguntas del genero seleccionado
                numeroAleatorio = obtenerNumeroAleatorio(0, generoSeleccionado.getPreguntas().length - 1);
                //Guardo la pregunta
                pregunta = generoSeleccionado.getPreguntas()[numeroAleatorio];
                //Inicializo variables para bucle de comprobacion
                int counter = 0;
                boolean preguntaRepetida = false;
                //Compruebo que la pregunta no haya salido
                while (counter < preguntasSeleccionadas.length && !preguntaRepetida)
                {
                    //Si la pregunta ya esta dentro o es para una edad no recomendada
                    if (pregunta == preguntasSeleccionadas[counter] || (!jugador.getEsMayorEdad() && pregunta.isEsMayorEdad()))
                    {
                        preguntaRepetida = true;
                    } else //Si la pregunta no se encuentra dentro del array de preguntasSelecciondas entra aqui
                    {
                        counter++; //Itera el contador para comprobar siguiente numero
                    }
                }

                if (!preguntaRepetida)//Si el numero no esta repetido
                {
                    //Agrego la pregunta al array de preguntas selecciondas
                    preguntasSeleccionadas[i] = pregunta;
                    preguntaCorrecta = true; //Señalo que la respuesta se agrega correctamente para salir del bucle
                }
            }

        }

        return preguntasSeleccionadas;
    }

    /**
     * Devuelve un numero aleatorio dado un minimo y un maximo, ambos incluidos
     * @param min numero minimo a obtener
     * @param max numero maximo a obtener
     * @return numero aleatorio
     */
    public int obtenerNumeroAleatorio(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range + min);
    }
}