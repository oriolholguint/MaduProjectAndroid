package com.example.madu_project;

public class Ranking {
    private Partida[] topRanks;
    private String nombreGenero;

    public Ranking(String nombreGenero,Partida[] ranks){
        this.nombreGenero = nombreGenero;
        this.topRanks = ranks;
    }
    public Partida[] getTopRanks() {
        return this.topRanks;
    }

    public void setTopRanks(Partida[] topRanks) {
        this.topRanks = topRanks;
    }

    public String getNombreGenero() {
        return this.nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }
}
