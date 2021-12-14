package com.example.madu_project;

import com.example.madu_project.partida.Partida;

import java.util.ArrayList;

public class Ranking {
    private ArrayList<Partida> partidas;
    private String nombreGenero;
    private String dificultad;

    public Ranking(String nombreGenero,ArrayList<Partida> partidas, String dificultad){
        this.nombreGenero = nombreGenero;
        this.partidas = partidas;
        this.dificultad = dificultad;
    }
    public ArrayList<Partida> getpartidas() {
        return this.partidas;
    }

    public void setpartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }

    public String getNombreGenero() {
        return this.nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
