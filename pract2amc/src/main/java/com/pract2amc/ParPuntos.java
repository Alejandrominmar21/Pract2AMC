/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pract2amc;


/**
 * Representa un par de puntos en el plano 2D.
 * Esta clase se utiliza principalmente para almacenar los dos puntos más
 * cercanos
 * encontrados por los algoritmos de búsqueda.
 * 
 * @author usuario
 */
public class ParPuntos {
    private Punto p1;
    private Punto p2;

    /**
     * Constructor que crea un nuevo par de puntos.
     * 
     * @param p1 Primer punto del par
     * @param p2 Segundo punto del par
     */
    public ParPuntos(Punto p1, Punto p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setP1(Punto p1) {
        this.p1 = p1;
    }

    public void setP2(Punto p2) {
        this.p2 = p2;
    }

    public Punto getP1() {
        return p1;
    }

    public Punto getP2() {
        return p2;
    }

    @Override
    public String toString() {
        return "ParPuntos{" + "p1=" + p1 + ", p2=" + p2 + '}';
    }

}
