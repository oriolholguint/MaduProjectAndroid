package com.example.madu_project.archivos;

import com.example.madu_project.Genero;
import com.example.madu_project.idioma.Idioma;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorArchivos
{
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
     * @param generos array con los objetos Genero para escribir en JSON
     * @param filePath ruta donde se guardara el fichero JSON
     * @throws JSONException
     */
    public static void writeJson(Genero[] generos, String filePath) throws JSONException
    {
        try
        {
            FileWriter fw = new FileWriter(filePath);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fw.write(gson.toJson(generos));

            fw.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
}