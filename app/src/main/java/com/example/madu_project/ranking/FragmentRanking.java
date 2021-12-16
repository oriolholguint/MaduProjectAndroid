package com.example.madu_project.ranking;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.madu_project.FragmentMenu;
import com.example.madu_project.MainActivity;
import com.example.madu_project.R;
import com.example.madu_project.archivos.GestorArchivos;
import com.example.madu_project.partida.PartidaAdapter;

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

        activity = (MainActivity)getActivity();
        TextView rankingError = view.findViewById(R.id.rankingError); //Mensaje de aviso si no hay ranking
        btnAtrasRanking = view.findViewById(R.id.btnAtrasRanking); //Boton volver al menu
        RecyclerView listaPartidas = view.findViewById(R.id.listaPartidas); //RecyclerView con la lista de partidas

        //Obtengo los rankings del fichero partidas.json
        ArrayList<Ranking> rankings = GestorArchivos.getRanking();

        //Creo spinner generos y su adapter
        Spinner spinnerGenero = view.findViewById(R.id.spinnerGenero);
        ArrayList<String> opcionesGenero = obtenerNombreGeneros(activity);

        ArrayAdapter adapterGenero = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item, opcionesGenero);
        spinnerGenero.setAdapter(adapterGenero);

        //Creo spinner de dificultad y su adapter
        Spinner spinnerDificultad = view.findViewById(R.id.spinnerDificultad);
        ArrayList<String> opcionesDificultad = activity.llenarSpinnerDificultad();

        ArrayAdapter adapterDificultad = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item, opcionesDificultad);
        spinnerDificultad.setAdapter(adapterDificultad);

        //Pongo la dificultad del menu en el spinner (cambiar para que se haga automaticamente segun idioma)
        switch (activity.dificultadMenu)
        {
            case 0:
                spinnerDificultad.setSelection(0);
                break;
            case 1:
                spinnerDificultad.setSelection(1);
                break;
            case 2:
                spinnerDificultad.setSelection(2);
                break;
        }

        //Definir spinner por defecto o si se acaba de jugar una partida
        if(activity.partida != null) //Se acaba de jugar una partida - saldra ranking del primer elemento de genero (porque dificultad por defecto es facil)
        {
            //Obtengo la posicion del adapter generos para ponerlo en el spinner
            int posicionGenero = adapterGenero.getPosition(activity.generoSelect.getNombre());
            spinnerGenero.setSelection(posicionGenero); //Selecciono en el spinner el genero de la partida jugada

            //Obtengo la posicion del adapter dificultad para ponerlo en el spinner
            int posicionDificultad = adapterDificultad.getPosition(activity.partida.getDificultad());
            spinnerDificultad.setSelection(posicionDificultad); //Selecciono en el spinner el genero se la partida jugada

            //Al haber modificado la seleccion del spinner saltaran los evento setOnItemSelectedListener
        }

        mg = getFragmentManager();

        spinnerDificultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                int dificultad = (int) spinnerDificultad.getSelectedItemId();
                String genero = (String) spinnerGenero.getSelectedItem();

                Ranking ranking = obtenerRanking(rankings, genero, dificultad);

                if(ranking != null)
                {
                    rankingError.setVisibility(View.INVISIBLE);
                    listaPartidas.setVisibility(View.VISIBLE);
                    PartidaAdapter adapter = new PartidaAdapter(ranking.getpartidas());
                    listaPartidas.setHasFixedSize(true);
                    listaPartidas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    listaPartidas.setAdapter(adapter);
                }
                else
                {
                    listaPartidas.setVisibility(View.INVISIBLE);
                    rankingError.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        spinnerGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                int dificultad = (int) spinnerDificultad.getSelectedItemId();
                String genero = (String) spinnerGenero.getSelectedItem();

                Ranking ranking = obtenerRanking(rankings, genero, dificultad);

                if(ranking != null)
                {
                    rankingError.setVisibility(View.INVISIBLE);
                    listaPartidas.setVisibility(View.VISIBLE);
                    PartidaAdapter adapter = new PartidaAdapter(ranking.getpartidas());
                    listaPartidas.setHasFixedSize(true);
                    listaPartidas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    listaPartidas.setAdapter(adapter);
                }
                else
                {
                    listaPartidas.setVisibility(View.INVISIBLE);
                    rankingError.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

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

    private static Ranking obtenerRanking(ArrayList<Ranking> rankings, String genero, int dificultad)
    {
        Ranking ranking = null;
        boolean rankingEncontrado = false;
        int counter = 0;

        while (counter < rankings.size() && !rankingEncontrado)
        {
            if (rankings.get(counter).getDificultad() == dificultad
                    && rankings.get(counter).getNombreGenero().equals(genero))
            {
                ranking = rankings.get(counter);
                rankingEncontrado = true;
            }
            else
            {
                counter++;
            }
        }

        return ranking;
    }

}