package com.example.madu_project.personaje;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madu_project.FragmentMenu;
import com.example.madu_project.FragmentRanking;
import com.example.madu_project.Genero;
import com.example.madu_project.MainActivity;
import com.example.madu_project.R;
import com.example.madu_project.Ranking;
import com.example.madu_project.archivos.GestorArchivos;
import com.example.madu_project.partida.Partida;

import java.util.ArrayList;

public class FragmentPersonaje extends Fragment
{
    private final static String IMAGE_PATH = "/data/data/com.example.madu_project/files/images/";
    private final static int MAX_PARTIDAS = 10;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MainActivity activity = (MainActivity) getActivity();

        view = inflater.inflate(R.layout.fragment_personaje, container, false);

        TextView  nombrePersonaje   = view.findViewById(R.id.nombrePersonaje);
        ImageView imagenPersonaje   = view.findViewById(R.id.imagenPersonaje);
        TextView  puntuacionPartida = view.findViewById(R.id.puntuacionPartida);
        Button btnIrRanking         = view.findViewById(R.id.btnIrRanking);
        Button btnIrMenu            = view.findViewById(R.id.btnIrMenu);

        Personaje personaje = personajeElegido(activity.generoSelect, activity.partida.getPuntuacion());

        nombrePersonaje.setText(personaje.getNombre());

        Bitmap bMap = BitmapFactory.decodeFile(IMAGE_PATH + personaje.getImagen());
        imagenPersonaje.setImageBitmap(bMap);
        puntuacionPartida.setText(getString(R.string.info_puntuacion) + " " + (activity.partida.getPuntuacion()));

        ArrayList<Ranking> rankings = GestorArchivos.getRanking();
        guardarPartidaRanking(rankings, activity.partida, activity.generoSelect);

        btnIrRanking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnIrRanking.setEnabled(false);
                activity.layout = "RankingFinal";
                activity.clBackgroundApp.setBackgroundResource(R.drawable.fondojuego);
                FragmentManager mg = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction;

                fragmentTransaction = mg.beginTransaction();
                FragmentRanking fragmentRanking = new FragmentRanking();
                fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentRanking);
                fragmentTransaction.commit();
            }
        });

        btnIrRanking.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btnIrRanking.startAnimation(activity.buttonUp);
                        break;
                    /*case MotionEvent.ACTION_UP:
                        btnIrRanking.startAnimation(activity.buttonUp);
                        break;*/
                }

                return false;
            }
        });

        btnIrMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnIrMenu.setEnabled(false);
                FragmentMenu fragmentMenu = new FragmentMenu();

                activity.layout = "Menu";
                activity.clBackgroundApp.setBackgroundResource(R.drawable.fondojuego);
                activity.volverAMenu(fragmentMenu);
                activity.grpMenu.setVisibility(View.INVISIBLE);
                activity.grpDificultad.setVisibility(View.VISIBLE);
                activity.grpPuntuacion.setVisibility(View.INVISIBLE);
                activity.lbLPuntos.setText("0");
                activity.partida = null;
                activity.stopAudio();
                activity.mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.musica_juego_madu);
                activity.bucleAudio();
                activity.settingsDialog.cancel();

            }
        });

        btnIrMenu.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        btnIrMenu.startAnimation(activity.buttonUp);
                        break;
                    /*case MotionEvent.ACTION_UP:
                        btnIrMenu.startAnimation(activity.buttonUp);
                        break;*/
                }

                return false;
            }
        });

        return view;
    }

    public Personaje personajeElegido(Genero genero, int puntuacionPartida)
    {
        Personaje personaje = null;
        int posicionJugador = 0;

        if(puntuacionPartida >= 1000)
        {
            posicionJugador = 1;
        }
        else if(puntuacionPartida > 500)
        {
            posicionJugador = 2;
        }
        else if(puntuacionPartida >= 0)
        {
            posicionJugador = 3;
        }

        boolean personajeEncontrado = false;
        int counter = 0;
        while(counter < genero.getPersonajes().length && !personajeEncontrado)
        {
            if(genero.getPersonajes()[counter].getPosicionRanking() == posicionJugador)
            {
                personajeEncontrado = true;
                personaje = genero.getPersonajes()[counter];
            }
            else
            {
                counter++;
            }
        }

        return personaje;
    }

    public static void guardarPartidaRanking(ArrayList<Ranking> rankings, Partida partida, Genero generoJugado)
    {
        Ranking ranking = null;

        if(rankings != null)
        {
            boolean rankingEncontrado = false;
            int counter = 0;

            while (counter < rankings.size() && !rankingEncontrado)
            {
                if (rankings.get(counter).getNombreGenero().equals(generoJugado.getNombre())
                        && rankings.get(counter).getDificultad().equals(partida.getDificultad()))
                {
                    rankingEncontrado = true;
                    ranking = rankings.get(counter);
                }
                else
                {
                    counter++;
                }
            }
        }
        else
        {
            rankings = new ArrayList<>();
        }

        if(ranking != null) //Si encuentra el ranking guarda partida
        {
            guardarPartida(ranking, partida);
            GestorArchivos.writeJson(rankings);
        }
        else //Si no encuentra el ranking lo crea
        {
            Ranking rankingNuevo = new Ranking(generoJugado.getNombre(), new ArrayList<Partida>(), partida.getDificultad());
            rankingNuevo.getpartidas().add(partida);
            rankings.add(rankingNuevo);
            GestorArchivos.writeJson(rankings);
        }
    }

    public static Ranking guardarPartida(Ranking ranking, Partida partida)
    {
        ArrayList<Partida> partidas = ranking.getpartidas();

        //Guardo la nueva partida en la lista de partidas si supera alguna puntuacion de las guardadas
        boolean partidaGuardada = false;
        int counter = 0;

        if(partidas.size() != MAX_PARTIDAS - 1)
        {
            partidas.add(partida);
            partidaGuardada = true;
        }
        else
        {
            while(counter < partidas.size() && !partidaGuardada)
            {
                if(partidas.get(counter).getPuntuacion() < partida.getPuntuacion())
                {
                    partidas.add(partida);
                    partidaGuardada = true;
                }
                else
                {
                    counter++;
                }
            }
        }

        if(partidaGuardada) //Si se ha guardado la partida entra aqui
        {
            //Se ordena el array de partidas con metodo burbuja
            partidas = ordenarBurbuja(partidas);

            //Si el array de partidas es mas grande que el limite se eliminan las partidas que sobrepasan el maximo
            //Esto se hace asi por si se cambia el numero de partidas que aparecen en el ranking
            eliminarPartidas(partidas);
        }
        else
        {
            eliminarPartidas(partidas);
        }

        ranking.setPartidas(partidas);

        return ranking;
    }

    public static ArrayList<Partida> ordenarBurbuja(ArrayList<Partida> partidas)
    {
        Partida partidaAux;
        int i, j;

        for (i = 0; i < partidas.size() - 1; i++)
        {
            for (j = 0; j < partidas.size() - i - 1; j++)
            {
                if (partidas.get(j + 1).getPuntuacion() < partidas.get(j).getPuntuacion())
                {
                    partidaAux = partidas.get(j + 1);
                    partidas.set(j + 1, partidas.get(j));
                    partidas.set(j, partidaAux);
                }
            }
        }

        return partidas;
    }

    public static void eliminarPartidas(ArrayList<Partida> partidas)
    {
        if(partidas.size() > MAX_PARTIDAS - 1)
        {
            for(int i = MAX_PARTIDAS - 1; i < partidas.size(); i++)
            {
                partidas.remove(i);
            }
        }
    }
}