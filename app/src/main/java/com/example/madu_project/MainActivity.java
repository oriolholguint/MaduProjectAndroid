package com.example.madu_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
{

    public String layout = "Idioma";
    public String status = "Pricipal";
    public Genero [] generos;
    public Dialog settingsDialog;
    public int duracion;
    private ArrayAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        duracion = 30;
        ImageButton imgBtnConfiguracion = findViewById(R.id.imgBtnConfiguracion);
        androidx.constraintlayout.widget.Group grpDatosUsuario = findViewById(R.id.grpDatosUsuario);

        androidx.fragment.app.FragmentManager mgr = getSupportFragmentManager();
        final androidx.fragment.app.FragmentTransaction fragmentTransaction = mgr.beginTransaction();

        final FragmentBotones fragmentBotones = new FragmentBotones();

        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentBotones);
        fragmentTransaction.commit();

        settingsDialog = new Dialog(this);
        settingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(R.layout.settings_dialog);

        androidx.constraintlayout.widget.Group grpMenu = settingsDialog.findViewById(R.id.grpMenu);
        androidx.constraintlayout.widget.Group grpDificultad = settingsDialog.findViewById(R.id.grpDificultad);


        final ImageButton btnSalir = settingsDialog.findViewById(R.id.btnSalir);
        final ImageButton btnMenu = settingsDialog.findViewById(R.id.btnMenu);


        final Spinner sprDificultad = (Spinner) settingsDialog.findViewById(R.id.sprDificultad);
        ArrayList<String> spritems = llenarSpinner();


        mAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,spritems);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sprDificultad.setAdapter(mAdapter);

        final FragmentMenu fragmentMenu = new FragmentMenu();

        sprDificultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // duracion = sprDificultad.getSelectedItemPosition() * 5 + 20;
                int index = sprDificultad.getSelectedItemPosition();

                if(index == 0){
                    duracion = 30;
                }
                else if(index == 1)
                {
                    duracion = 25;
                }
                else if(index == 2)
                {
                    duracion = 20;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        imgBtnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.show();

                if(layout.equals("Preguntas")){

                    grpMenu.setVisibility(View.VISIBLE);
                    grpDificultad.setVisibility(View.INVISIBLE);

                } else {
                    grpMenu.setVisibility(View.INVISIBLE);
                }

            }
        });



        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout.equals("Preguntas")){
                    layout = "Menu";
                    FragmentManager mg = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = mg.beginTransaction();

                    fragmentTransaction2.replace(R.id.ContenedorFragmentsPricipales,fragmentMenu);
                    fragmentTransaction2.commit();

                    grpMenu.setVisibility(View.INVISIBLE);
                    grpDificultad.setVisibility(View.VISIBLE);
                    settingsDialog.cancel();
                }
            }
        });


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(status.equals("Juego")){
                    FragmentManager mg = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = mg.beginTransaction();

                    fragmentTransaction2.replace(R.id.ContenedorFragmentsPricipales,fragmentBotones);
                    fragmentTransaction2.commit();
                    grpDatosUsuario.setVisibility(View.INVISIBLE);
                    status = "Principal";
                    layout = "Idioma";
                    generos = null;
                    androidx.constraintlayout.widget.Group grpDificultad = settingsDialog.findViewById(R.id.grpDificultad);
                    grpDificultad.setVisibility(View.INVISIBLE);


                } else
                {
                    if(layout.equals("Idioma")){
                        MainActivity.this.finish(); System.exit(0);
                    }

                    FragmentManager mg = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction2 = mg.beginTransaction();

                    FragmentIdioma fragmentIdioma = new FragmentIdioma();
                    fragmentTransaction2.replace(R.id.frLContenedorFragments,fragmentIdioma);
                    fragmentTransaction2.commit();
                    layout = "Idioma";
                    fragmentBotones.grpBotones.setVisibility(View.INVISIBLE);
                    fragmentBotones.btnSiguienteCentro.setVisibility(View.VISIBLE);
                    generos = null;
                }



                settingsDialog.cancel();

            }
        });




    }


    public ArrayList<String> llenarSpinner ()
    {
        ArrayList<String> items = new ArrayList<>();
        items.add("Facil");
        items.add("Medio");
        items.add("Dificil");

        return items;

    }


}
