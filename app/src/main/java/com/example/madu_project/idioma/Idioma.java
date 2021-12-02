package com.example.madu_project.idioma;

public class Idioma
{
    private String nombre;
    private String imageButton;
    private String filePath;

    public Idioma(String nombre, String imageButton, String filePath)
    {
        this.nombre = nombre;
        this.imageButton = imageButton;
        this.filePath = filePath;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImageButton() {
        return imageButton;
    }

    public void setImageButton(String imageButton) {
        this.imageButton = imageButton;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Idioma{" +
                "nombre='" + nombre + '\'' +
                ", imageButton='" + imageButton + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
