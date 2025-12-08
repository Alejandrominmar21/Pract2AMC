package com.pract2amc;

import java.util.ArrayList;

public class Camino {
    double distanciaFinal;
    int[] camino;
    ArrayList<Double> distancias = new ArrayList<>();
    double tiempo;
    long calculadas; // Nuevo campo

    public Camino() {}

    // Constructor actualizado
    public Camino(double distanciaFinal, int[] camino, ArrayList<Double> distancias, double tiempo, long calculadas) {
        this.distanciaFinal = distanciaFinal;
        this.camino = camino;
        this.distancias = distancias;
        this.tiempo = tiempo;
        this.calculadas = calculadas;
    }

    public double getDistanciaFinal() { return distanciaFinal; }
    public void setDistanciaFinal(double distanciaFinal) { this.distanciaFinal = distanciaFinal; }

    public int[] getCamino() { return camino; }
    public void setCamino(int[] camino) { this.camino = camino; }

    public ArrayList<Double> getDistancias() { return distancias; }
    public void setDistancias(ArrayList<Double> distancias) { this.distancias = distancias; }

    public double getTiempo() { return tiempo; }
    public void setTiempo(double tiempo) { this.tiempo = tiempo; }

    public long getCalculadas() { return calculadas; }
    public void setCalculadas(long calculadas) { this.calculadas = calculadas; }
}