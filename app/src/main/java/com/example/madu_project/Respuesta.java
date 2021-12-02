package com.example.madu_project;

public class Respuesta
{
    private String respuestaDescripcion;
    private boolean esCorrecta;

    public Respuesta(String respuestaDescripcion, boolean esCorrecta)
    {

        this.respuestaDescripcion = respuestaDescripcion;
        this.esCorrecta = esCorrecta;
    }

    public String getrespuestaDescripcion() {

        return respuestaDescripcion;
    }

    public void setRespuestaDescripcion(String respuestaDescripcion) {
        this.respuestaDescripcion = respuestaDescripcion;
    }


    public boolean isEsCorrecta() {
        return esCorrecta;
    }
    public void setrespuestaDescripcion(String respuestaDescripcion) {
        this.respuestaDescripcion = respuestaDescripcion;

    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    @Override
    public String toString()
    {
        return "{" +
                "respuestaDescripcion='" + respuestaDescripcion + '\'' +
                ", esCorrecta=" + esCorrecta +
                '}';
    }

}
