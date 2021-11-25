package com.example.madu_project.archivos;

import com.example.madu_project.Genero;
import com.example.madu_project.idioma.Idioma;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GestorArchivos
{
    /**
     * Se obtienen los generos del fichero json en idioma espannol.
     * @return Array con los generos en espannol.
     */
    public static Genero[] getGenero(String filePath)
    {
        Genero[] generos = null;

        try
        {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            Gson gson = new Gson();

            generos = gson.fromJson(br, Genero[].class);

            br.close();
            fr.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return generos;
    }

    public static Idioma[] getIdiomas(String filePath)
    {
        Idioma[] idiomas = null;

        try
        {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            Gson gson = new Gson();

            idiomas = gson.fromJson(br, Idioma[].class);

            br.close();
            fr.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return idiomas;
    }

}
