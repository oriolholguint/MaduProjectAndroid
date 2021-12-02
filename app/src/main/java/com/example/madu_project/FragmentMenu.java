package com.example.madu_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.madu_project.introduccion.FragmentTutorial;

import java.util.ArrayList;
import java.util.Arrays;


public class FragmentMenu extends Fragment {

   View view;
   MainActivity activity;
   Genero [] generos;
   Pregunta[] preguntas;
   RecyclerView listGeneros;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu, container, false);

        activity = (MainActivity) getActivity();

        generos = activity.generos;

        listGeneros = view.findViewById(R.id.ListGeneros);
        GeneroAdapter generoAdapter = new GeneroAdapter(generos);

        listGeneros.setHasFixedSize(true);
        listGeneros.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        listGeneros.setAdapter(generoAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);

        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider));
        listGeneros.addItemDecoration(dividerItemDecoration);

        ImageButton imgBtnRanking = view.findViewById(R.id.imgBtnRanking);
        imgBtnRanking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                activity.layout = "Ranking";
                Genero[] genero = generos;
                Bundle bundle = new Bundle();
                bundle.putSerializable("generos",genero);


        adapter.setOnClickListener(new View.OnClickListener() {
                getParentFragmentManager().setFragmentResult("generos",bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                FragmentRanking fragmentRanking = new FragmentRanking();
                fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentRanking);
                fragmentTransaction.commit();
            }

        });


        generoAdapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                activity.layout = "Preguntas";
                Genero genero = generos[listGeneros.getChildAdapterPosition(view)];


                Bundle bundle = new Bundle();
                bundle.putSerializable("genero",genero);
                getParentFragmentManager().setFragmentResult("genero",bundle);

                FragmentManager mg = getFragmentManager();
                FragmentTransaction fragmentTransaction = mg.beginTransaction();

                FragmentPreguntas fragmentPreguntas = new FragmentPreguntas();
                fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentPreguntas);
                fragmentTransaction.commit();



            }
        });



        return view;
    }
}