package com.example.madu_project;

public class Ranking {
    private Partida[] topRanks;
    private String nombreGenero;

    public Partida[] getTopRanks() {
        return topRanks;
    }

    public void setTopRanks(Partida[] topRanks) {
        this.topRanks = topRanks;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }
}
