package com.example.madu_project;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.madu_project.introduccion.FragmentTutorial;
import com.example.madu_project.ranking.FragmentRanking;


public class FragmentMenu extends Fragment {

    View view;
    MainActivity activity;
    Genero [] generos;
    RecyclerView listGeneros;
    FrameLayout frLinformacion, frLgrupo;
    ConstraintLayout frmMenu;
    ImageButton imgBtnRanking, imgBtnGrupo, imgBtnInformacion;
    FragmentManager mg;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu, container, false);

        activity = (MainActivity) getActivity();

        mg = getFragmentManager();

        generos = activity.generos;
        frLinformacion = view.findViewById(R.id.frLinformacion);
        frLgrupo = view.findViewById(R.id.frLgrupo);
        frmMenu = view.findViewById(R.id.frmMenu);





        listGeneros = view.findViewById(R.id.ListGeneros);
        GeneroAdapter generoAdapter = new GeneroAdapter(generos);


        listGeneros.setHasFixedSize(true);
        listGeneros.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        listGeneros.setAdapter(generoAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);

        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider));
        listGeneros.addItemDecoration(dividerItemDecoration);

        imgBtnRanking = view.findViewById(R.id.imgBtnRanking);
        imgBtnGrupo = view.findViewById(R.id.imgBtnGrupo);
        imgBtnInformacion = view.findViewById(R.id.imgBtnInformacion);



        activity.clBackgroundApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frLinformacion.setVisibility(View.INVISIBLE);
                frLgrupo.setVisibility(View.INVISIBLE);
            }
        });


        imgBtnRanking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                activity.layout = "Ranking";
                /*
                Genero[] genero = generos;
                Bundle bundle = new Bundle();
                bundle.putSerializable("generos",genero);

                getParentFragmentManager().setFragmentResult("generos",bundle);


                 */
                irARanking();
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


        imgBtnGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentGrupo fragmentGrupo = new FragmentGrupo();

                getFragmentManager().beginTransaction().add(R.id.frLgrupo,fragmentGrupo).commit();
                frLgrupo.setVisibility(View.VISIBLE);
            }
        });

        imgBtnGrupo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        imgBtnGrupo.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        imgBtnGrupo.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });

        imgBtnInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTutorial fragmentTutorial = new FragmentTutorial();
                getFragmentManager().beginTransaction().add(R.id.frLinformacion,fragmentTutorial).commit();
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
                activity.frmSombra.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                bundle.putSerializable("genero",genero);
                getParentFragmentManager().setFragmentResult("genero",bundle);
                //Igualo el genero seleccionado a la variable del main para utilizarlo en el personaje
                activity.generoSelect = genero;
                activity.pauseAudio();
                irAPreguntas();


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


    public void AnimacionDerechaAIzquierda(){
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,R.anim.exit_left_to_right);
    }


    public void irARanking(){

        fragmentTransaction = mg.beginTransaction();
        FragmentRanking fragmentRanking = new FragmentRanking();
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentRanking);
        fragmentTransaction.commit();
    }

    public void irAPreguntas(){

        fragmentTransaction = mg.beginTransaction();

        FragmentPreguntas fragmentPreguntas = new FragmentPreguntas();
        AnimacionDerechaAIzquierda();
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentPreguntas);
        fragmentTransaction.commit();
    }


}
