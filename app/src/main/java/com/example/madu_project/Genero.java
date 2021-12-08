package com.example.madu_project;

import com.example.madu_project.personaje.Personaje;

import java.io.Serializable;
import java.util.Arrays;

public class Genero implements Serializable
{
    private String nombre;
    private String musicaFondo;
    private String imagenFondo;
    private String imagenMenu;
    private Pregunta[] preguntas;
    private Personaje[] personajes;
    private Partida[] partidas;

    public Genero(String nombre, String musicaFondo, String imagenFondo, String imagenMenu,
                  Pregunta[] preguntas, Personaje[] personajes, Partida[] partidas)
    {
        this.nombre = nombre;
        this.musicaFondo = musicaFondo;
        this.imagenFondo = imagenFondo;
        this.imagenMenu = imagenMenu;
        this.preguntas = preguntas;
        this.personajes = personajes;
        this.partidas = partidas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMusicaFondo() {
        return musicaFondo;
    }

    public String getImagenFondo() {
        return imagenFondo;
    }

    public String getImagenMenu() {
        return imagenMenu;
    }

    public Pregunta[] getPreguntas() {
        return preguntas;
    }


    public Personaje[] getPersonajes() {
        return personajes;
    }

    public Partida[] getPartidas() {
        return partidas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMusicaFondo(String musicaFondo) {
        this.musicaFondo = musicaFondo;
    }

    public void setImagenFondo(String imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    public void setImagenMenu(String imagenMenu) {
        this.imagenMenu = imagenMenu;
    }

    public void setPreguntas(Pregunta[] preguntas) {
        this.preguntas = preguntas;
    }

    public void setPersonajes(Personaje[] personajes) {
        this.personajes = personajes;
    }

    public void setPartidas( Partida[]  partidas) {
        this.partidas = partidas;
    }

    @Override
    public String toString() {
        return "{" +
                "nombre='" + nombre + '\'' +
                ", musicaFondo='" + musicaFondo + '\'' +
                ", imagenFondo='" + imagenFondo + '\'' +
                ", imagenMenu='" + imagenMenu + '\'' +
                ", preguntas=" + Arrays.toString(preguntas) +
                ", personajes=" + Arrays.toString(personajes) +
                ", partidas=" + Arrays.toString(partidas) +
                '}';
    }
}
