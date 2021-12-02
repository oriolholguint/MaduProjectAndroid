package com.example.madu_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentPersonaje extends Fragment
{
    private final static int PUNTUACION_MAX = 1000;

    MainActivity activity = (MainActivity) getActivity();

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_personaje, container, false);

        int puntuacionPartidaJugador = 0;

        TextView  nombrePersonaje   = view.findViewById(R.id.nombrePersonaje);
        ImageView imagenPersonaje   = view.findViewById(R.id.imagenPersonaje);
        TextView  puntuacionPartida = view.findViewById(R.id.puntuacionPartida);

        Genero generoJugado = activity.generos[activity.generoJugado];
        Personaje personajePartida = personajeElegido(generoJugado, puntuacionPartidaJugador);

        nombrePersonaje.setText(personajePartida.getNombre());
        Bitmap bMap = BitmapFactory.decodeFile("/data/data/com.example.madu_project/files/images/" + personajePartida.getImagen());
        imagenPersonaje.setImageBitmap(bMap);
        puntuacionPartida.setText(puntuacionPartidaJugador);

        return view;
    }

    public Personaje personajeElegido(Genero genero, int puntuacionPartida)
    {
        Personaje personaje = null;
        int posicionJugador = 0;

        if(puntuacionPartida >= PUNTUACION_MAX)
        {
            posicionJugador = 1;
        }
        else if(puntuacionPartida > PUNTUACION_MAX * 0.666f)
        {
            posicionJugador = 2;
        }
        else if(puntuacionPartida > PUNTUACION_MAX * 0.333f)
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
}