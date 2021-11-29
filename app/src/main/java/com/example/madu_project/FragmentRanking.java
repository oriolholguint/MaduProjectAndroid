package com.example.madu_project;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class FragmentRanking extends Fragment {

    View view;
    //MainActivity ma;
    Genero [] generos;

    public static void sort(ArrayList<Partida> list)
    {

        //list.sort((o1, o2)
                //-> o1.getCustomProperty().compareTo(
                //o2.getCustomProperty()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ranking, container, false);

        //Inicializar variables, Arrays, GridViews,... y metodos
        RecyclerView RankingList = view.findViewById(R.id.ListGeneros);

        for ( Genero g : this.generos) {
            //ArrayList<Partida> p = new ArrayList<>();
            //    sort(g.getPartidas());
            for( Partida p : g.getPartidas()){

            }
            //tl.addTab(tb.setText(g.getNombre()));
            //Toast.makeText(getActivity(), tb.getText(), Toast.LENGTH_SHORT).show();
        }
        Ranking[] rankings = new Ranking[generos.length];
        RankingAdapter adapter = new RankingAdapter(rankings);
        RankingList.setHasFixedSize(true);
        RankingList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        //generos = ma.generos;
        //generos;
        //for ()
        RankingList.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);

        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider));
        RankingList.addItemDecoration(dividerItemDecoration);
        //TabLayout tl = view.findViewById(R.id.tablayoutranking);
        //this.ma = (MainActivity) getActivity();
        //tl.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tl.setTabGravity(TabLayout.GRAVITY_FILL);


        return view;
    }
}