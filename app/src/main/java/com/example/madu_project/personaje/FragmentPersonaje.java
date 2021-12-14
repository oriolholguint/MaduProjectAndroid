package com.example.madu_project.personaje;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

public class FragmentPersonaje extends Fragment
{
    private final String IMAGE_PATH = "/data/data/com.example.madu_project/files/images/";
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

        btnIrRanking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                btnIrRanking.setEnabled(false);

                FragmentManager mg = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction;

                fragmentTransaction = mg.beginTransaction();
                FragmentRanking fragmentRanking = new FragmentRanking();
                fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentRanking);
                fragmentTransaction.commit();
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
                activity.mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.polynomial1m);
                activity.startAudio();
                activity.settingsDialog.cancel();

            }
        });

        btnIrMenu.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Drawable drawable = btnIrMenu.getBackground();

                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        drawable.setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
                        btnIrMenu.setBackground(drawable);
                        break;
                    case MotionEvent.ACTION_UP:
                        drawable.clearColorFilter();
                        btnIrMenu.setBackground(drawable);
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
}