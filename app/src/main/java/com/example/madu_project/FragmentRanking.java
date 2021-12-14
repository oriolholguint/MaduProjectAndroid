package com.example.madu_project;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
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
    Button btnAtrasRanking;
    FragmentManager mg;
    FragmentTransaction fragmentTransaction;

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
        btnAtrasRanking = view.findViewById(R.id.btnAtrasRanking);
        ma = (MainActivity)getActivity();

        mg = getFragmentManager();

        getParentFragmentManager().setFragmentResultListener("generos", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                generos = (Genero[]) result.getSerializable("generos");
                generosParaRanking = generos;
                //ma = getActivity(MainActivity);


                generoAux = ma.generosAux;
                //Inicializar variables, Arrays, GridViews,... y metodos
                RecyclerView RankingList = view.findViewById(R.id.RankingList);



                Ranking[] rankings = new Ranking[generos.length];
                int cont = 0;
                for ( Genero g : generos) {
                    //rankings[cont++] = new Ranking(g.getNombre(),g.getPartidas());
                }
                cont = 0;
                for ( Genero g : generoAux) {
                    //rankings[cont++] = new Ranking(g.getNombre(),g.getPartidas());
                }

                RankingAdapter adapter = new RankingAdapter(rankings);
                RankingList.setHasFixedSize(true);
                RankingList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                RankingList.setAdapter(adapter);




            }
        });



        btnAtrasRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ma.layout.equals("RankingFinal")){
                    ma.layout = "Menu";
                    ma.grpPuntuacion.setVisibility(View.INVISIBLE);
                    ma.lbLPuntos.setText("0");
                    ma.stopAudio();
                    ma.mediaPlayer = MediaPlayer.create(ma.getBaseContext(), R.raw.musica_juego_madu);
                    ma.bucleAudio();
                } else {
                    ma.layout = "Menu";
                }
                volverAMenu();
            }

        });


        //TabLayout tl = view.findViewById(R.id.tablayoutranking);
        //this.ma = (MainActivity) getActivity();
        //tl.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tl.setTabGravity(TabLayout.GRAVITY_FILL);


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


}