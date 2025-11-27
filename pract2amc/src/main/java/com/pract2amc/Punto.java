
package com.pract2amc;


/**
 * Representa un punto en un plano 2D con coordenadas (x,y).
 * Esta clase proporciona métodos para manipular y calcular distancias entre
 * puntos.
 * 
 * @author usuario
 */
public class Punto {
    double x;
    double y;

    /**
     * Constructor que crea un nuevo punto con las coordenadas especificadas.
     * 
     * @param x Coordenada X del punto
     * @param y Coordenada Y del punto
     */
    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {

        return "{" + x + " , " + y + "}";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calcula la distancia euclidiana entre este punto y otro punto dado.
     * 
     * @param p El punto con el que calcular la distancia
     * @return La distancia euclidiana entre los dos puntos
     */
    public double distancia(Punto p) {
        return Math.sqrt(Math.pow(this.getX() - p.getX(), 2) + Math.pow(this.getY() - p.getY(), 2));
    }

}
