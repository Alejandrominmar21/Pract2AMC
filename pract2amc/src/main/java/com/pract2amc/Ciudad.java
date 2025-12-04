package com.pract2amc;

public class Ciudad {
    int ciudad;
    double distancia;

    public Ciudad() {

    }

    public Ciudad(int ciudad, double distancia) {
        this.ciudad = ciudad;
        this.distancia = distancia;
    }

    public int getCiudad() {
        return ciudad;
    }

    public void setCiudad(int ciudad) {
        this.ciudad = ciudad;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

}