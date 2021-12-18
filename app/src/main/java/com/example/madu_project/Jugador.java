package com.example.madu_project;

public class Jugador
{
    private String nombre;
    private Boolean esMayorEdad;
    private int avatar;//int porque es el drawable


    public Jugador(String nombre, Boolean esMayorEdad, int avatar) {
        this.nombre = nombre;
        this.esMayorEdad = esMayorEdad;
        this.avatar = avatar;
    }

    public Jugador() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEsMayorEdad() {
        return esMayorEdad;
    }

    public void setEsMayorEdad(Boolean esMayorEdad) {
        this.esMayorEdad = esMayorEdad;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "{" +
                "nombre='" + nombre + '\'' +
                ", esMayorEdad=" + esMayorEdad +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}