package com.pract2amc;

import java.util.ArrayList;

public class Camino {
    double distanciaFinal; 
    int[] camino; 
    ArrayList<Double> distancias = new ArrayList<>();

    public Camino(){}
    
    public Camino(double distanciaFinal, int[] camino, ArrayList<Double> distancias) {
        this.distanciaFinal = distanciaFinal;
        this.camino = camino;
        this.distancias=distancias;
    }

    public double getDistanciaFinal() {
        return distanciaFinal;
    }

    public void setDistanciaFinal(double distanciaFinal) {
        this.distanciaFinal = distanciaFinal;
    }

    public int[] getCamino() {
        return camino;
    }

    public void setCamino(int[] camino) {
        this.camino = camino;
    }

    public ArrayList<Double> getDistancias() {
        return distancias;
    }

    public void setDistancias(ArrayList<Double> distancias) {
        this.distancias = distancias;
    }  
}