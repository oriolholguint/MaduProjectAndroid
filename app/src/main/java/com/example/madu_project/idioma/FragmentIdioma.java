package com.example.madu_project.idioma;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.madu_project.R;
import com.example.madu_project.archivos.GestorArchivos;
import com.example.madu_project.idioma.Idioma;
import com.example.madu_project.idioma.IdiomasAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class FragmentIdioma extends Fragment
{
    public static final String GENEROS_ESP = "GenerosEsp.json";
    public static final String GENEROS_ENG = "GenerosEng.json";

    MainActivity activity;
    String path = "/data/data/com.example.madu_project/files/";
    Idioma[] idiomas = GestorArchivos.getIdiomas("/data/data/com.example.madu_project/files/idiomas.json");

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
        activity.generos = GestorArchivos.getGeneros(path + GENEROS_ESP);
        activity.generosAux = GestorArchivos.getGeneros(path + GENEROS_ENG);

        //Cuando se selecciona un elemento del RecyclerView entra en el evento
        adapter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Creamos un objeto Idioma con los atributos del elemento seleccionado
                Idioma idioma = idiomas[listaIdiomas.getChildAdapterPosition(view)];

                //Guardo la posicion del elemento seleccionado en una variable del adapter
                adapter.selectedPos = listaIdiomas.getChildAdapterPosition(view);
                //Recargo el adapter
                adapter.notifyDataSetChanged();

                /*Dependiendo del idioma seleccionado se enviara como array de generos principal un idioma
                u otro y como array aux otros, en el caso que se añado un idioma nuevo se tendra que
                annadir un else if con el idioma nuevo*/
                if(idiomas[adapter.selectedPos].getFilePath().equals(GENEROS_ESP))
                {
                    activity.generos = GestorArchivos.getGeneros(path + GENEROS_ESP);
                    activity.generosAux = GestorArchivos.getGeneros(path + GENEROS_ENG);
                }
                else if(idiomas[adapter.selectedPos].getFilePath().equals(GENEROS_ENG))
                {

                    activity.generos = GestorArchivos.getGeneros(path + GENEROS_ENG);
                    activity.generosAux = GestorArchivos.getGeneros(path + GENEROS_ESP);
                }

                //Cambio el idioma de la tablet el nuevo idioma para usar fichero strings.xml correspondiente
                cambiarIdioma(idioma.getNombre());
            }
        });

        return view;
    }

    public void cambiarIdioma(String idioma)
    {
        String nuevoLenguaje = idioma;
        Locale locale = new Locale(nuevoLenguaje);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
    }
    
}