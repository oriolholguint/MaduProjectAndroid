package com.example.madu_project.ranking;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.madu_project.FragmentMenu;
import com.example.madu_project.Genero;
import com.example.madu_project.MainActivity;
import com.example.madu_project.R;
import com.example.madu_project.archivos.GestorArchivos;
import com.example.madu_project.partida.Partida;
import com.example.madu_project.partida.PartidaAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class FragmentRanking extends Fragment
{

    View view;
    MainActivity activity;
    Button btnAtrasRanking;
    FragmentManager mg;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ranking, container, false);

        btnAtrasRanking = view.findViewById(R.id.btnAtrasRanking);
        activity = (MainActivity)getActivity();

        //Creo spinner generos
        Spinner spinnerGenero = (Spinner) view.findViewById(R.id.spinnerGenero);
        ArrayList<String> opcionesGenero = obtenerNombreGeneros(activity);

        ArrayAdapter adapterGenero = new ArrayAdapter<String>(activity, R.layout.support_simple_spinner_dropdown_item, opcionesGenero);
        spinnerGenero.setAdapter(adapterGenero);

        //Creo spinner de dificultad
        Spinner spinnerDificultad = (Spinner) view.findViewById(R.id.spinnerDificultad);
        ArrayList<String> opcionesDificultad = activity.llenarSpinnerDificultad();

        ArrayAdapter adapterDificultad = new ArrayAdapter<String>(activity, R.layout.support_simple_spinner_dropdown_item, opcionesDificultad);
        spinnerDificultad.setAdapter(adapterDificultad);

        //Definir spinner por defecto o si se acaba de jugar una partida
        if(activity.partida != null) //Se acaba de jugar una partida - saldra ranking del primer elemento de genero (porque dificultad por defecto es facil)
        {
            boolean generoEncontrado = false;
            int counterGenero = 0;
            while(counterGenero < activity.generos.length && !generoEncontrado)
            {
                String stringSpinnerGenero = (String) spinnerGenero.getItemAtPosition(counterGenero);
                if(stringSpinnerGenero.equals(activity.generos[counterGenero].getNombre()))
                {
                    generoEncontrado = true;
                }
                else
                {
                    counterGenero++;
                }
            }
            spinnerGenero.setSelection(counterGenero);

            boolean dificultadEncontrada = false;
            int counterDificultad = 0;
            while(counterDificultad < spinnerDificultad.getCount() && !dificultadEncontrada)
            {
                String stringSpinnerDificultad = (String) spinnerDificultad.getItemAtPosition(counterDificultad);
                if(stringSpinnerDificultad.equals(activity.partida.getDificultad()))
                {
                    dificultadEncontrada = true;
                }
                else
                {
                    counterDificultad++;
                }
            }
            spinnerDificultad.setSelection(counterDificultad);
        }
        else //No se acaba de jugar una partida - saldra el ranking menu
        {
            spinnerDificultad.setSelection(0);
            spinnerGenero.setSelection(0);
        }

        mg = getFragmentManager();

        ArrayList<Ranking> rankings = GestorArchivos.getRanking();

        Ranking ranking = null;

        if(rankings != null && activity.partida != null && activity.generoSelect != null)
        {
            boolean rankingEncontrado = false;
            int counter = 0;

            while (counter < rankings.size() && !rankingEncontrado)
            {
                if (rankings.get(counter).getDificultad().equals(activity.partida.getDificultad())
                        && rankings.get(counter).getNombreGenero().equals(activity.generoSelect.getNombre()))
                {
                    ranking = rankings.get(counter);
                    rankingEncontrado = true;
                }
                else
                {
                    counter++;
                }
            }

            if(ranking != null)
            {

                RecyclerView listaPartidas = view.findViewById(R.id.listaPartidas);

                PartidaAdapter adapter = new PartidaAdapter(ranking.getpartidas());
                listaPartidas.setHasFixedSize(true);
                listaPartidas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                listaPartidas.setAdapter(adapter);

            }
        }

        btnAtrasRanking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(activity.layout.equals("RankingFinal"))
                {
                    activity.layout = "Menu";
                    activity.grpPuntuacion.setVisibility(View.INVISIBLE);
                    activity.lbLPuntos.setText("0");
                    activity.stopAudio();
                    activity.mediaPlayer = MediaPlayer.create(activity.getBaseContext(), R.raw.musica_juego_madu);
                    activity.partida = null;
                    activity.bucleAudio();
                }
                else
                {
                    activity.layout = "Menu";
                }
                volverAMenu();
            }

        });

        return view;
    }

    public void AnimacionIzquierdaADerecha(FragmentTransaction fragmentTransaction){
        fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right,R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,R.anim.exit_right_to_left);
    }

    public void volverAMenu(){
        fragmentTransaction = mg.beginTransaction();
        AnimacionIzquierdaADerecha(fragmentTransaction);
        FragmentMenu fragmentMenu = new FragmentMenu();
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales, fragmentMenu);
        fragmentTransaction.commit();
    }

    private static ArrayList<String> obtenerNombreGeneros(MainActivity activity)
    {
        ArrayList<String> nombres = new ArrayList<>();

        for(int i = 0; i < activity.generos.length; i++)
        {
            nombres.add(activity.generos[i].getNombre());
        }

        return nombres;
    }
}