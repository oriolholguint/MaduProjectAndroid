package com.example.madu_project.archivos;

import com.example.madu_project.Genero;
import com.example.madu_project.Ranking;
import com.example.madu_project.partida.Partida;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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



    /**
     * Se escribe un fichero JSON
     * @param ranking array con objetos ranking a escribir en JSON
     * @throws JSONException
     */
    public static void writeJson(Ranking[] ranking) throws JSONException
    {
        try
        {
            FileWriter fw = new FileWriter(MATCHES_FILE);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fw.write(gson.toJson(ranking));

            fw.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void guardarPartidaRanking(ArrayList<Ranking> rankings, Partida partida, Genero generoJugado)
    {
        Ranking ranking = null;
        boolean rankingEncontrado = false;
        int counter = 0;

        while(counter < rankings.size() && !rankingEncontrado)
        {
            if(rankings.get(counter).getNombreGenero().equals(generoJugado.getNombre())
            && rankings.get(counter).getDificultad().equals(partida.getDificultad()))
            {
                rankingEncontrado = true;
                ranking = rankings.get(counter);
            }
            else
            {
                counter++;
            }
        }

        if(ranking != null) //Si encuentra el ranking guarda partida
        {
            guardarPartida(ranking, partida);
        }
        else //Si no encuentra el ranking lo crea
        {
            Ranking rankingNuevo = new Ranking(generoJugado.getNombre(), new ArrayList<Partida>(), partida.getDificultad());
            guardarPartida(rankingNuevo, partida);
        }
    }

    public static void guardarPartida(Ranking ranking, Partida partida)
    {
        ArrayList<Partida> partidas = ranking.getpartidas();

        //Guardo la nueva partida en la lista de partidas si supera alguna puntuacion de las guardadas
        boolean partidaGuardada = false;
        int counter = 0;
        while(counter < partidas.size() && !partidaGuardada)
        {
            if(partidas.get(counter).getPuntuacion() < partida.getPuntuacion())
            {
                partidas.add(partida);
                partidaGuardada = true;
            }
            else
            {
                counter++;
            }
        }

        if(partidaGuardada) //Si se ha guardado la partida entra aqui
        {
            //Se ordena el array de partidas con metodo burbuja
            partidas = ordenarBurbuja(partidas);

            //Si el array de partidas es mas grande que el limite se elimina el ultimo
            if(partidas.size() > 10)
            {
                partidas.remove(partidas.size());
            }
        }
    }

    public static ArrayList<Partida> ordenarBurbuja(ArrayList<Partida> partidas)
    {
        Partida partidaAux;
        int i, j;

        for (i = 0; i < partidas.size() - 1; i++)
        {
            for (j = 0; j < partidas.size() - i - 1; j++)
            {
                if (partidas.get(j + 1).getPuntuacion() < partidas.get(j).getPuntuacion())
                {
                    partidaAux = partidas.get(j + 1);
                    partidas.set(j + 1, partidas.get(j));
                    partidas.set(j, partidaAux);
                }
            }
        }

        return partidas;
    }
}