package com.example.madu_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;


import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.madu_project.idioma.FragmentIdioma;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{

    public String layout = "Idioma";
    public String status = "Pricipal";
    public Genero [] generos;
    public Genero [] generosAux;
    public Dialog settingsDialog;
    public int duracion;
    private ArrayAdapter mAdapter;
    public Partida partida;
    public Jugador jugador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        duracion = 30;
        partida = null;
        jugador = new Jugador("jugador1",true,3);
        ImageButton imgBtnConfiguracion = findViewById(R.id.imgBtnConfiguracion);
        androidx.constraintlayout.widget.Group grpDatosUsuario = findViewById(R.id.grpDatosUsuario);

        TextView lbLPuntos = findViewById(R.id.lbLPuntos);
        androidx.fragment.app.FragmentManager mgr = getSupportFragmentManager();
        final androidx.fragment.app.FragmentTransaction fragmentTransaction = mgr.beginTransaction();

        final FragmentBotones fragmentBotones = new FragmentBotones();

        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentBotones);
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


        mAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,spritems);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sprDificultad.setAdapter(mAdapter);

        final FragmentMenu fragmentMenu = new FragmentMenu();

        sprDificultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // duracion = sprDificultad.getSelectedItemPosition() * 5 + 20;
                int index = sprDificultad.getSelectedItemPosition();

                if(index == 0){
                    duracion = 30;
                }
                else if(index == 1)
                {
                    duracion = 25;
                }
                else if(index == 2)
                {
                    duracion = 20;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        imgBtnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.show();

                if(layout.equals("Preguntas")){

                    grpMenu.setVisibility(View.VISIBLE);
                    grpDificultad.setVisibility(View.INVISIBLE);

                } else {
                    grpMenu.setVisibility(View.INVISIBLE);
                }

            }
        });



        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout.equals("Preguntas")){
                    layout = "Menu";
                    FragmentManager mg = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = mg.beginTransaction();

                    fragmentTransaction2.replace(R.id.ContenedorFragmentsPricipales,fragmentMenu);
                    fragmentTransaction2.commit();
                    partida = null;
                    grpMenu.setVisibility(View.INVISIBLE);
                    grpDificultad.setVisibility(View.VISIBLE);
                    grpPuntuacion.setVisibility(View.INVISIBLE);
                    lbLPuntos.setText("0");

                    settingsDialog.cancel();
                }

            }
        });


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(status.equals("Juego")){
                    FragmentManager mg = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = mg.beginTransaction();

                    fragmentTransaction2.replace(R.id.ContenedorFragmentsPricipales,fragmentBotones);
                    fragmentTransaction2.commit();
                    grpDatosUsuario.setVisibility(View.INVISIBLE);
                    status = "Principal";
                    layout = "Idioma";
                    generos = null;
                    partida = null;
                    jugador = null;
                    androidx.constraintlayout.widget.Group grpDificultad = settingsDialog.findViewById(R.id.grpDificultad);
                    grpDificultad.setVisibility(View.INVISIBLE);
                    grpPuntuacion.setVisibility(View.INVISIBLE);
                    lbLPuntos.setText("0");


                } else
                {
                    if(layout.equals("Idioma")){
                        MainActivity.this.finish(); System.exit(0);
                    }

                    FragmentManager mg = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = mg.beginTransaction();

                    FragmentIdioma fragmentIdioma = new FragmentIdioma();
                    fragmentTransaction2.replace(R.id.frLContenedorFragments,fragmentIdioma);
                    fragmentTransaction2.commit();
                    layout = "Idioma";
                    fragmentBotones.grpBotones.setVisibility(View.INVISIBLE);
                    fragmentBotones.btnSiguienteCentro.setVisibility(View.VISIBLE);
                    generos = null;
                }



                settingsDialog.cancel();


            }
        });




    }




    public ArrayList<String> llenarSpinner ()
    {
        ArrayList<String> items = new ArrayList<>();
        items.add("Facil");
        items.add("Medio");
        items.add("Dificil");

        return items;

    }

    /**
     * Se obtienen preguntas aleatorias de un array enviado por parametros.
     * @param preguntas array donde obtener preguntas
     * @param cantidadPreguntas cantidad de preguntas aleatorias a obtener
     * @return array con preguntas aleatorias
     */
    public static ArrayList<Pregunta> getPreguntasPartida(Pregunta[] preguntas,int cantidadPreguntas, Jugador jugador)
    {
        ArrayList<Pregunta> preguntasSeleccionadas = new ArrayList<>(); //Preguntas que saldran en la partida
        ArrayList<Integer> numerosAleatorios = new ArrayList<>(); //Numeros aleatorios para no repetirlos
        int numeroAleatorio = 0;

        for(int i = 0; i < cantidadPreguntas; i++) //Bucle con la cantidad de preguntas necesarias
        {
            boolean preguntaCorrecta = false;

            while(!preguntaCorrecta) //Bucle para elegir pregunta que no se repita
            {
                numeroAleatorio = obtenerNumeroAleatorio(0, preguntas.length - 1);
                int counter = 0;
                boolean numeroRepetido = false;
                //Si el num no esta repetido y aun quedan por revisar mas numeros entra aqui
                while(!numeroRepetido && counter < numerosAleatorios.size())
                {
                    //Si el num aleatorio esta repetido o el jugador es menor de edad y la pregunta es para mayor de edad
                    if(numeroAleatorio == numerosAleatorios.get(counter) || (!jugador.getEsMayorEdad() && preguntas[numeroAleatorio].isEsMayorEdad()))
                    {
                        numeroRepetido = true;
                    }
                    else //Si el num aleatorio no esta repetido
                    {
                        counter++; //Itera el contador para comprobar siguiente numero
                    }
                }

                if(!numeroRepetido)//Si el numero no esta repetido
                {
                    //Agrego el numero aleatorio al array de los numeros que ya han salido
                    numerosAleatorios.add(numeroAleatorio);
                    //Agrego la pregunta al array de preguntas selecciondas
                    preguntasSeleccionadas.add(preguntas[numeroAleatorio]);
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
    public static int obtenerNumeroAleatorio(int min, int max)
    {
        int range = (max -min) + 1;
        return (int) (Math.random() * range + min);
    }

}