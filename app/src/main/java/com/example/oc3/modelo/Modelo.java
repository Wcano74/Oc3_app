package com.example.oc3.modelo;

import com.google.firebase.database.GenericTypeIndicator;

public class Modelo {

    private String telefono;
    private String rack;
    private String tarjeta;
    private String puerto;

    public Modelo() {
    }

    public Modelo(String telefono, String rack, String tarjeta, String puerto) {
        this.telefono = telefono;
        this.rack = rack;
        this.tarjeta = tarjeta;
        this.puerto = puerto;
    }

    public String getTelefono() {
        return rack;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }
}


