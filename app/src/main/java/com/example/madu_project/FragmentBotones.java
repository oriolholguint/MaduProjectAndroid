package com.example.madu_project;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madu_project.idioma.FragmentIdioma;
import com.example.madu_project.introduccion.FragmentTutorial;


public class FragmentBotones extends Fragment
{
    View view;
    Button btnAtras, btnSiguienteCentro, btnSiguiente;
    Group grpBotones;
    MainActivity ActivityMain;
    FragmentManager mg;
    FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_botones, container, false);

        btnAtras = view.findViewById(R.id.btnAtras);
        btnSiguienteCentro = view.findViewById(R.id.btnSiguienteCentro);
        btnSiguiente = view.findViewById(R.id.btnSiguiente);
        grpBotones = view.findViewById(R.id.grpBotones);

        mg = getFragmentManager();


        FragmentIdioma fragmentIdioma = new FragmentIdioma();

        getFragmentManager().beginTransaction().add(R.id.frLContenedorFragments,fragmentIdioma).commit();

        ActivityMain = (MainActivity)getActivity();

        ConstraintLayout constraintLayout = ActivityMain.settingsDialog.findViewById(R.id.dialogSettings);

        btnSiguienteCentro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ActivityMain.layout.equals("Idioma") && ActivityMain.generos != null){
                    ActivityMain.layout = "Introduccion";
                    btnSiguienteCentro.setVisibility(View.INVISIBLE);
                    irATutorial();
                    grpBotones.setVisibility(View.VISIBLE);

                }

            }
        });

        btnSiguienteCentro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnSiguienteCentro.startAnimation(ActivityMain.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnSiguienteCentro.startAnimation(ActivityMain.buttonDown);
                        break;
                }
                return false;
            }
        });


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                if(ActivityMain.layout.equals("Introduccion"))
                {
                    ActivityMain.layout = "Idioma";

                    volverAIdioma();

                    grpBotones.setVisibility(View.INVISIBLE);
                    btnSiguienteCentro.setVisibility(View.VISIBLE);

                } else if(ActivityMain.layout.equals("Login")){
                    ActivityMain.layout = "Introduccion";

                    volverATutorial();

                    btnSiguiente.setVisibility(View.VISIBLE);
                    btnSiguiente.setText("Siguiente");

                }

            }
        });

        btnAtras.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnAtras.startAnimation(ActivityMain.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnAtras.startAnimation(ActivityMain.buttonDown);
                        break;
                }

                return false;
            }
        });


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(ActivityMain.layout.equals("Introduccion"))
                {
                    btnSiguiente.setVisibility(View.INVISIBLE);
                    ActivityMain.layout = "Login";

                    irALogin();

                }
                else if (ActivityMain.layout.equals("Login"))
                {
                    Group grpDificultad = ActivityMain.settingsDialog.findViewById(R.id.grpDificultad);

                    ActivityMain.layout = "Menu";
                    ActivityMain.status = "Juego";

                    irAMenu();

                    ActivityMain.findViewById(R.id.grpDatosUsuario).setVisibility(View.VISIBLE);
                    grpDificultad.setVisibility(View.VISIBLE);



                    btnSiguiente.setVisibility(View.VISIBLE);
                    btnSiguiente.setText("Siguiente");

                }
            }
        });

        btnSiguiente.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnSiguiente.startAnimation(ActivityMain.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnSiguiente.startAnimation(ActivityMain.buttonDown);
                        break;
                }
                return false;
            }
        });



        return view;

    }


    public void volverAIdioma(){
        fragmentTransaction = mg.beginTransaction();
        FragmentIdioma fragmentIdioma = new FragmentIdioma();
        fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right,R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,R.anim.exit_right_to_left);
        fragmentTransaction.replace(R.id.frLContenedorFragments,fragmentIdioma);
        fragmentTransaction.commit();
    }

    public void irATutorial(){
        fragmentTransaction = mg.beginTransaction();
        FragmentTutorial fragmentTutorial = new FragmentTutorial();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,R.anim.exit_left_to_right);
        fragmentTransaction.replace(R.id.frLContenedorFragments,fragmentTutorial);

        fragmentTransaction.commit();
    }

    public void volverATutorial(){
        fragmentTransaction = mg.beginTransaction();
        FragmentTutorial fragmentTutorial = new FragmentTutorial();
        fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right,R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,R.anim.exit_right_to_left);
        fragmentTransaction.replace(R.id.frLContenedorFragments,fragmentTutorial);
        fragmentTransaction.commit();
    }



    public void irALogin(){
        fragmentTransaction = mg.beginTransaction();
        FragmentLogin fragmentLogin = new FragmentLogin();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,R.anim.exit_left_to_right);
        fragmentTransaction.replace(R.id.frLContenedorFragments,fragmentLogin);
        fragmentTransaction.commit();
    }

    public void irAMenu(){
       fragmentTransaction = mg.beginTransaction();
        FragmentMenu fragmentMenu = new FragmentMenu();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,R.anim.exit_left_to_right);
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentMenu);
        fragmentTransaction.commit();
    }


}