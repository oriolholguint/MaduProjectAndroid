package com.example.madu_project;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class FragmentRanking extends Fragment {

    View view;
    MainActivity ma;
    Genero[] generos;
    Genero[] generoAux;
    public static Genero[] generosParaRanking;

    //public static void sort(ArrayList<Partida> list)
    //{

        //list.sort((o1, o2)
                //-> o1.getCustomProperty().compareTo(
                //o2.getCustomProperty()));
    //}
        public static String GetPathImageFromGeneroString(String nombreGenero){

            for (Genero e : generosParaRanking){
                if(e.getNombre().equals(nombreGenero)){
                    return e.getImagenMenu();
                }
            }
            return null;
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ranking, container, false);

        getParentFragmentManager().setFragmentResultListener("generos", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                generos = (Genero[]) result.getSerializable("generos");
                generosParaRanking = generos;
                //ma = getActivity(MainActivity);

                ma = (MainActivity)getActivity();
                generoAux = ma.generosAux;
                //Inicializar variables, Arrays, GridViews,... y metodos
                RecyclerView RankingList = view.findViewById(R.id.RankingList);
                Button btnAtrasRanking = view.findViewById(R.id.btnAtrasRanking);
                btnAtrasRanking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ma.layout = "Menu";
                        FragmentMenu fm = new FragmentMenu();
                        ma.volverAMenu(fm);
                        if(ma.layout.equals("RankingFinal")){
                            ma.stopAudio();
                            ma.mediaPlayer = MediaPlayer.create(ma.getBaseContext(), R.raw.musica_juego_madu);
                            ma.bucleAudio();
                        }

                    }

                });

                Ranking[] rankings = new Ranking[generos.length];
                int cont = 0;
                for ( Genero g : generos) {
                    rankings[cont++] = new Ranking(g.getNombre(),g.getPartidas());
                }
                cont = 0;
                for ( Genero g : generoAux) {
                    rankings[cont++] = new Ranking(g.getNombre(),g.getPartidas());
                }

                RankingAdapter adapter = new RankingAdapter(rankings);
                RankingList.setHasFixedSize(true);
                RankingList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                RankingList.setAdapter(adapter);




            }
        });




        //TabLayout tl = view.findViewById(R.id.tablayoutranking);
        //this.ma = (MainActivity) getActivity();
        //tl.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tl.setTabGravity(TabLayout.GRAVITY_FILL);


        return view;
    }
}