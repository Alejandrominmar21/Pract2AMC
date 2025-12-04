package com.pract2amc;

import java.util.ArrayList;

public class Camino {
    double distanciaFinal; 
    int[] camino; 
    ArrayList<Double> distancias = new ArrayList<>();
    double tiempo; // Nuevo campo para el tiempo de ejecución (ms)

    public Camino(){}
    
    // Constructor actualizado
    public Camino(double distanciaFinal, int[] camino, ArrayList<Double> distancias, double tiempo) {
        this.distanciaFinal = distanciaFinal;
        this.camino = camino;
        this.distancias = distancias;
        this.tiempo = tiempo;
    }

    public double getDistanciaFinal() { return distanciaFinal; }
    public void setDistanciaFinal(double distanciaFinal) { this.distanciaFinal = distanciaFinal; }

    public int[] getCamino() { return camino; }
    public void setCamino(int[] camino) { this.camino = camino; }

    public ArrayList<Double> getDistancias() { return distancias; }
    public void setDistancias(ArrayList<Double> distancias) { this.distancias = distancias; }
    
    // Nuevos métodos para Tiempo
    public double getTiempo() { return tiempo; }
    public void setTiempo(double tiempo) { this.tiempo = tiempo; }
}