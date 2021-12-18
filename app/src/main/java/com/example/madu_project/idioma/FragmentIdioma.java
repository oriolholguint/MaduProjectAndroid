package com.example.madu_project.idioma;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madu_project.Genero;
import com.example.madu_project.MainActivity;
import com.example.madu_project.Pregunta;
import com.example.madu_project.R;
import com.example.madu_project.archivos.GestorArchivos;
import com.example.madu_project.idioma.Idioma;
import com.example.madu_project.idioma.IdiomasAdapter;
import com.example.madu_project.personaje.Personaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class FragmentIdioma extends Fragment
{
    MainActivity activity;
    String path = "/data/data/com.example.madu_project/files/";
    Idioma[] idiomas = getIdiomas();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_idioma, container, false);

        activity = (MainActivity) getActivity();
        RecyclerView listaIdiomas = view.findViewById(R.id.listaIdiomas);

        IdiomasAdapter adapter = new IdiomasAdapter(idiomas);
        listaIdiomas.setHasFixedSize(true);
        listaIdiomas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listaIdiomas.setAdapter(adapter);

        //Por defecto el idioma seleccionado es el espannol
        activity.generos = GestorArchivos.getGeneros(path + idiomas[0].getFilePath());
        cambiarIdioma(idiomas[0].getNombre());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);

        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider_idioma));
        listaIdiomas.addItemDecoration(dividerItemDecoration);

        //Cuando se selecciona un elemento del RecyclerView entra en el evento
        adapter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Creamos un objeto Idioma con los atributos del elemento seleccionado
                try
                {
                    Idioma idioma = idiomas[listaIdiomas.getChildAdapterPosition(view)];

                    //Guardo la posicion del elemento seleccionado en una variable del adapter
                    adapter.selectedPos = listaIdiomas.getChildAdapterPosition(view);
                    //Recargo el adapter
                    adapter.notifyDataSetChanged();

                    activity.generos = GestorArchivos.getGeneros(path + idiomas[adapter.selectedPos].getFilePath());

                    //Cambio el idioma de la tablet el nuevo idioma para usar fichero strings.xml correspondiente
                    cambiarIdioma(idioma.getNombre());

                    //Creo spinner con el idioma de las dificultades correspondientes
                    activity.crearSpinnerDificultad();
                }
                catch (ArrayIndexOutOfBoundsException ex)
                {
                    ex.printStackTrace();
                }

            }
        });

        return view;
    }

    public void cambiarIdioma(String idioma)
    {
        Locale locale = new Locale(idioma);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
    }

    public static Idioma[] getIdiomas()
    {

        return new Idioma[]{
                new Idioma("es", "espana_flag.png", "GenerosEsp.json"),
                new Idioma("en", "reino_unido_flag.png", "GenerosEng.json")};
    }
}