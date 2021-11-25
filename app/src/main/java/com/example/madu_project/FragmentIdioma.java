package com.example.madu_project;

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
import android.widget.Toast;

import com.example.madu_project.archivos.GestorArchivos;
import com.example.madu_project.idioma.Idioma;
import com.example.madu_project.idioma.IdiomasAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class FragmentIdioma extends Fragment
{
    MainActivity activity;
    String path = "/data/data/com.example.madu_project/files/";
    Idioma[] idiomas = GestorArchivos.getIdiomas("/data/data/com.example.madu_project/files/idiomas.json");

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_idioma, container, false);

        RecyclerView listaIdiomas = view.findViewById(R.id.listaIdiomas);
        IdiomasAdapter adapter = new IdiomasAdapter(idiomas);
        listaIdiomas.setHasFixedSize(true);
        listaIdiomas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listaIdiomas.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Idioma idioma = idiomas[listaIdiomas.getChildAdapterPosition(view)];

                //Añado fondo al idioma seleccionado
                adapter.selectedPos = listaIdiomas.getChildAdapterPosition(view);
                adapter.notifyDataSetChanged();

                //Obtengo el array de generos en el idioma seleccionado
                Genero[] generoSeleccionado = GestorArchivos.getGenero(path + idioma.getFilePath());
                if(generoSeleccionado != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setMessage(idioma.getNombre())
                            .setTitle(idioma.getNombre());

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                //Añado a la tablet el nuevo idioma para usar fichero strings.xml correspondiente
                String nuevoLenguaje = idioma.getNombre();
                Locale locale = new Locale(nuevoLenguaje);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;

                //activity.generos = generoSeleccionado;
            }
        });

        return view;
    }





}