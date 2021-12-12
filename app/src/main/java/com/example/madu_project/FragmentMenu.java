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
import android.view.MotionEvent;
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
   ImageButton imgBtnRanking, imgBtnInformacion;


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

        imgBtnRanking = view.findViewById(R.id.imgBtnRanking);
        imgBtnInformacion = view.findViewById(R.id.imgBtnInformacion);



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
                bundle.putSerializable("generos",genero);


                getParentFragmentManager().setFragmentResult("generos",bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                FragmentRanking fragmentRanking = new FragmentRanking();
                fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentRanking);
                fragmentTransaction.commit();
            }

        });

        imgBtnRanking.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        imgBtnRanking.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        imgBtnRanking.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });


        imgBtnInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                frLinformacion.setVisibility(View.VISIBLE);
            }
        });

        imgBtnInformacion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        imgBtnInformacion.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        imgBtnInformacion.startAnimation(activity.buttonDown);
                        break;
                }
                return false;
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
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,R.anim.exit_left_to_right);
                fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentPreguntas);
                fragmentTransaction.commit();


            }
        });

        generoAdapter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        generoAdapter.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        view.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });


        return view;
    }




}
