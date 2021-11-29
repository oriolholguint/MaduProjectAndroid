package com.example.madu_project;

public class Jugador
{
    private String nombre;
    private Boolean esMayorEdad;
    private String avatar;


    public Jugador(String nombre, Boolean esMayorEdad, String avatar) {
        this.nombre = nombre;
        this.esMayorEdad = esMayorEdad;
        this.avatar = avatar;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", esMayorEdad=" + esMayorEdad +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}