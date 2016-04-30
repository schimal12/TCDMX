package com.example.cantucky.tcdmx;

/**
 * Created by sebastianchimal on 14/03/16.
 */
public class Carro {
    private String marca;
    private String modelo;
    private String submarca;
    private String resultado;
    public Carro(String marca, String modelo, String submarca,String resultado){
        this.marca = marca;
        this.modelo = modelo;
        this.submarca = submarca;
        this.resultado = resultado;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSubmarca() {
        return submarca;
    }

    public String getResultado() { return resultado;};
}
