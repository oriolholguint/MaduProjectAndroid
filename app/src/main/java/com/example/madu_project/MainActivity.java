package com.example.madu_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
{

    public String layout = "Idioma";
    public String status = "Pricipal";
    public Genero [] generos;
    public int duracion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ImageButton imgBtnConfiguracion = findViewById(R.id.imgBtnConfiguracion);

        androidx.fragment.app.FragmentManager mgr = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction = mgr.beginTransaction();

        FragmentBotones fragmentBotones = new FragmentBotones();

        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentBotones);
        fragmentTransaction.commit();


        final Dialog settingsDialog = new Dialog(this);
        settingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(R.layout.settings_dialog);


        //
        final ImageButton btnSalir = settingsDialog.findViewById(R.id.btnSalir);


        imgBtnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.show();

            }
        });


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




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
