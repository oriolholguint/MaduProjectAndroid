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
import com.example.madu_project.ranking.FragmentRanking;
import com.example.madu_project.Genero;
import com.example.madu_project.MainActivity;
import com.example.madu_project.R;
import com.example.madu_project.ranking.Ranking;
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

        TextView nombreJugador          = view.findViewById(R.id.nombreJugador);
        ImageView avatarJugadorPartida  = view.findViewById(R.id.avatarJugadorPartida);
        TextView dificultadPartida      = view.findViewById(R.id.dificultadPartida);
        TextView  puntuacionPartida     = view.findViewById(R.id.puntuacionPartida);
        TextView  nombrePersonaje       = view.findViewById(R.id.nombrePersonaje);
        ImageView imagenPersonaje       = view.findViewById(R.id.imagenPersonaje);
        Button btnIrRanking             = view.findViewById(R.id.btnIrRanking);
        Button btnIrMenu                = view.findViewById(R.id.btnIrMenu);

        Personaje personaje = personajeElegido(activity.generoSelect, activity.partida.getPuntuacion());

        nombreJugador.setText(activity.partida.getJugador().getNombre());

        avatarJugadorPartida.setImageResource(activity.partida.getJugador().getAvatar());

        switch (activity.partida.getDificultad())
        {
            case 0:
                dificultadPartida.setText(R.string.facil);
                break;
            case 1:
                dificultadPartida.setText(R.string.medio);
                break;
            case 2:
                dificultadPartida.setText(R.string.dificil);
                break;
        }

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
                FragmentTransaction fragmentTransaction = mg.beginTransaction();

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
                    case MotionEvent.ACTION_UP:
                        btnIrRanking.startAnimation(activity.buttonDown);
                        break;
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
                activity.clBackgroundApp.setBackgroundResource(R.drawable.fondo_menu_madu);
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
                    case MotionEvent.ACTION_UP:
                        btnIrMenu.startAnimation(activity.buttonDown);
                        break;
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

        if(puntuacionPartida >= 1200)
        {
            posicionJugador = 1;
        }
        else if(puntuacionPartida > 700)
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
                        && rankings.get(counter).getDificultad() == partida.getDificultad())
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
            Ranking rankingEditado = guardarPartida(ranking, partida);
            rankings.remove(ranking);
            rankings.add(rankingEditado);
            GestorArchivos.writeJson(rankings);
        }
        else //Si no encuentra el ranking lo crea y guarda la partida
        {
            Ranking rankingNuevo = new Ranking(generoJugado.getNombre(), new ArrayList<>(), partida.getDificultad());
            rankingNuevo.getpartidas().add(partida);
            rankings.add(rankingNuevo);
            GestorArchivos.writeJson(rankings);
        }
    }

    public static Ranking guardarPartida(Ranking ranking, Partida partida)
    {
        ArrayList<Partida> partidas = ranking.getpartidas();

        //Guardo la nueva partida en la lista de partidas si supera o iguala alguna puntuacion de las guardadas
        boolean partidaGuardada = false;
        int counter = 0;

        while(counter < partidas.size() && !partidaGuardada)
        {
            if(partidas.get(counter).getPuntuacion() <= partida.getPuntuacion())
            {
                partidaGuardada = true;
                partidas = meterPartida(partidas, partida, counter);
                eliminarPartidas(partidas);
                ranking.setPartidas(partidas);
            }
            else
            {
                counter++;
            }
        }

        if(!partidaGuardada && counter < MAX_PARTIDAS)
        {
            partidas.add(partida);
            ranking.setPartidas(partidas);
        }

        return ranking;
    }

    public static void eliminarPartidas(ArrayList<Partida> partidas)
    {
        if(partidas.size() > MAX_PARTIDAS)
        {
            for(int i = MAX_PARTIDAS; i < partidas.size(); i++)
            {
                partidas.remove(i);
            }
        }
    }

    private static ArrayList<Partida> meterPartida(ArrayList<Partida> partidas, Partida partidaNueva,int posicion)
    {
        ArrayList<Partida> partidasNuevo = new ArrayList<>();

        for(int i = 0; i < posicion; i++)
        {
            partidasNuevo.add(partidas.get(i));
        }

        partidasNuevo.add(partidaNueva);

        if(posicion < partidas.size())
        {
            for(int i = posicion; i < partidas.size(); i++)
            {
                partidasNuevo.add(partidas.get(i));
            }
        }

        return partidasNuevo;
    }

    /*public static ArrayList<Partida> ordenarBurbuja(ArrayList<Partida> partidas)
    {
        Partida partidaAux;
        int i, j;

        for (i = 0; i < partidas.size() - 1; i++)
        {
            for (j = 0; j < partidas.size() - i - 1; j++)
            {
                //Verificar direccion de orden si ascendente o descendente
                if (partidas.get(j + 1).getPuntuacion() > partidas.get(j).getPuntuacion())
                {
                    partidaAux = partidas.get(j + 1);
                    partidas.set(j + 1, partidas.get(j));
                    partidas.set(j, partidaAux);
                }
            }
        }

        return partidas;
    }*/
}