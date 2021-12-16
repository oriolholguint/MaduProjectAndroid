package com.example.madu_project.partida;

import com.example.madu_project.Jugador;

import java.io.Serializable;
import java.util.Date;

public class Partida implements Serializable
{
    private Integer puntuacion;
    private int dificultad; //Facil: 0, Medio:1, Dificil:2
    private Date fecha;
    private Jugador jugador;

    public Partida(int puntuacion, int dificultad, Date fecha, Jugador jugador)
    {
        this.puntuacion = puntuacion;
        this.dificultad = dificultad;
        this.fecha = fecha;
        this.jugador = jugador;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public int getDificultad() {
        return dificultad;
    }

    public Date getFecha() {
        return fecha;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    public boolean check(){
        return !(this.dificultad == 0 || this.fecha == null || this.jugador == null || this.puntuacion == null);
    }

    @Override
    public String toString()
    {
        return "{" +
                "puntuacion=" + puntuacion +
                ", dificultad='" + dificultad + '\'' +
                ", fecha=" + fecha +
                ", jugador=" + jugador +
                '}';
    }
}
