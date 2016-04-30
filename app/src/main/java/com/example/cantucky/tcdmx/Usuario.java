package com.example.cantucky.tcdmx;

import java.io.Serializable;

/**
 * Created by sebastianchimal on 04/04/16.
 */
public class Usuario implements Serializable{
    private String username;
    private String nombre;
    private String mail;
    private String pass;
    private String pass2;
    private String placa;

    public Usuario(String username, String nombre, String mail, String pass, String pass2, String placa) {
        this.username = username;
        this.nombre = nombre;
        this.mail = mail;
        this.pass = pass;
        this.pass2 = pass2;
        this.placa = placa;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public String getPass2() {
        return pass2;
    }

    public String getPlaca() {
        return placa;
    }
}
