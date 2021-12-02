package com.example.madu_project;

public class Ranking {
    private Partida[] partidasRank;
    private String nombreGenero;

    public Ranking(String nombreGenero,Partida[] ranks){
        this.nombreGenero = nombreGenero;
        this.partidasRank = ranks;
    }
    public Partida[] getPartidasRank() {
        return this.partidasRank;
    }

    public void setPartidasRank(Partida[] partidasRank) {
        this.partidasRank = partidasRank;
    }

    public String getNombreGenero() {
        return this.nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }
}
