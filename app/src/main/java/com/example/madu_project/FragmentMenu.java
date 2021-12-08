package com.example.madu_project;


import android.app.AlertDialog;
import android.app.Dialog;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.madu_project.introduccion.FragmentTutorial;


public class FragmentMenu extends Fragment {

   View view;
   MainActivity activity;
   Genero [] generos;
   RecyclerView listGeneros;
   FrameLayout frLinformacion;
   ConstraintLayout frmMenu;
   ViewPager vpGeneros;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu, container, false);

        activity = (MainActivity) getActivity();

        generos = activity.generos;
        frLinformacion = view.findViewById(R.id.frLinformacion);
        frmMenu = view.findViewById(R.id.frmMenu);
        //vpGeneros = view.findViewById(R.id.vpGeneros);

        FragmentTutorial fragmentTutorial = new FragmentTutorial();
        getFragmentManager().beginTransaction().add(R.id.frLinformacion,fragmentTutorial).commit();


        listGeneros = view.findViewById(R.id.ListGeneros);
        GeneroAdapter generoAdapter = new GeneroAdapter(generos);

        //GeneroAdapterCardView generoAdapterCardView = new GeneroAdapterCardView(getContext(),generos);
        //vpGeneros.setAdapter(generoAdapterCardView);


        listGeneros.setHasFixedSize(true);
        listGeneros.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        listGeneros.setAdapter(generoAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);

        //RecyclerView.ItemDecoration dividerItemDecorator = new DividerItemDecorator(getActivity());
        //listGeneros.addItemDecoration(dividerItemDecorator);

        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider));
        listGeneros.addItemDecoration(dividerItemDecoration);

        ImageButton imgBtnRanking = view.findViewById(R.id.imgBtnRanking);
        ImageButton imgBtnInformacion = view.findViewById(R.id.imgBtnInformacion);

        /*
        vpGeneros.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        activity.clBackgroundApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frLinformacion.setVisibility(View.INVISIBLE);
            }
        });


        imgBtnRanking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                activity.layout = "Ranking";

                Genero[] genero = generos;
                Bundle bundle = new Bundle();
                bundle.putSerializable("generos", genero);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                FragmentRanking fragmentRanking = new FragmentRanking();
                fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentRanking);
                fragmentTransaction.commit();


            }

        });


        imgBtnInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                frLinformacion.setVisibility(View.VISIBLE);
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