package com.example.madu_project;

import com.example.madu_project.partida.Partida;
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

    public Genero(String nombre, String musicaFondo, String imagenFondo, String imagenMenu,
                  Pregunta[] preguntas, Personaje[] personajes)
    {
        this.nombre = nombre;
        this.musicaFondo = musicaFondo;
        this.imagenFondo = imagenFondo;
        this.imagenMenu = imagenMenu;
        this.preguntas = preguntas;
        this.personajes = personajes;
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

    @Override
    public String toString() {
        return "{" +
                "nombre='" + nombre + '\'' +
                ", musicaFondo='" + musicaFondo + '\'' +
                ", imagenFondo='" + imagenFondo + '\'' +
                ", imagenMenu='" + imagenMenu + '\'' +
                ", preguntas=" + Arrays.toString(preguntas) +
                ", personajes=" + Arrays.toString(personajes) +
                '}';
    }
}
