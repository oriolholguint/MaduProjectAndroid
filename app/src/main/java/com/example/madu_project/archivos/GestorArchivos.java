package com.example.madu_project.archivos;

import com.example.madu_project.Genero;
import com.example.madu_project.Ranking;
import com.example.madu_project.partida.Partida;
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

        return generos;
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
     * @throws JSONException
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