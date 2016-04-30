package com.example.cantucky.tcdmx;

/**
 * Created by sebastianchimal on 29/03/16.
 */
public class Articulo {
    private int numeroArticulo;
    private String fraccion;
    private String parrafo;
    private String inciso;
    private String corralon;
    private int puntos;
    private String descripcion;
    private int sansion;

    public Articulo(int numeroArticulo, String fraccion, String parrafo, String inciso, String corralon, int puntos, String descripcion, int sansion) {
        this.numeroArticulo = numeroArticulo;
        this.fraccion = fraccion;
        this.parrafo = parrafo;
        this.inciso = inciso;
        this.corralon = corralon;
        this.puntos = puntos;
        this.descripcion = descripcion;
        this.sansion = sansion;
    }



    public int getNumeroArticulo() {
        return numeroArticulo;
    }

    public void setNumeroArticulo(int numeroArticulo) {
        this.numeroArticulo = numeroArticulo;
    }

    public String getFraccion() {
        return fraccion;
    }

    public void setFraccion(String fraccion) {
        this.fraccion = fraccion;
    }

    public String getParrafo() {
        return parrafo;
    }

    public void setParrafo(String parrafo) {
        this.parrafo = parrafo;
    }

    public String getInciso() {
        return inciso;
    }

    public void setInciso(String inciso) {
        this.inciso = inciso;
    }

    public String getCorralon() {
        return corralon;
    }

    public void setCorralon(String corralon) {
        this.corralon = corralon;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getSansion() {
        return sansion;
    }

    public void setSansion(int sansion) {
        this.sansion = sansion;
    }

    @Override
    public String toString() {
        return "NumeroArticulo: "+numeroArticulo+"\nFraccion :"+fraccion+"\nParrafo: "+parrafo+"\nInciso: "+inciso+"\nCorralon: "+"\nDescripcion: "+descripcion+"\nCorralon :"+corralon+"\nSanción(Puntos): "+sansion;
    }
}
