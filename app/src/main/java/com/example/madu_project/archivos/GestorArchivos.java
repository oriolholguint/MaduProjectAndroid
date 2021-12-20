package com.example.madu_project.archivos;

import com.example.madu_project.Genero;
import com.example.madu_project.Pregunta;
import com.example.madu_project.personaje.FragmentPersonaje;
import com.example.madu_project.personaje.Personaje;
import com.example.madu_project.ranking.Ranking;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GestorArchivos
{

    private final static String MATCHES_FILE = "/data/data/com.example.madu_project/partidas.json";

    /**
     * Se obtienen los generos del fichero json en idioma espannol.
     * @return Array con los generos en espannol.
     */
    public static Genero[] getGeneros(String filePath)
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

        Genero generoMixto = new Genero("Mix", "musica_rap.mp3", "fondo_mix.jpg",
                "fondo_menu_mix.jpg", new Pregunta[FragmentPersonaje.MAX_PARTIDAS], new Personaje[3]);

        Genero[] generosExtra = new Genero[generos.length + 1];

        for(int i = 0; i < generos.length; i++)
        {
            generosExtra[i] = generos[i];
        }

        generosExtra[generos.length] = generoMixto;

        return generosExtra;
    }

    public static ArrayList<Ranking> getRanking()
    {
        Ranking[] rankings = null;

        try
        {
            FileReader fr = new FileReader(MATCHES_FILE);
            BufferedReader br = new BufferedReader(fr);

            Gson gson = new Gson();

            rankings = gson.fromJson(br, Ranking[].class);

            br.close();
            fr.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        if(rankings != null)
        {
            return new ArrayList<Ranking>(Arrays.asList(rankings));
        }
        else
        {
            return null;
        }

    }

    /**
     * Se escribe un fichero JSON
     * @param ranking array con objetos ranking a escribir en JSON
     */
    public static void writeJson(ArrayList<Ranking> ranking)
    {
        Ranking[] rankingArray = new Ranking[ranking.size()];

        for(int i = 0; i < rankingArray.length; i++)
        {
            rankingArray[i] = ranking.get(i);
        }

        try
        {
            File fichero = new File(MATCHES_FILE);
            fichero.delete();

            FileWriter fw = new FileWriter(MATCHES_FILE);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fw.write(gson.toJson(rankingArray));

            fw.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
}