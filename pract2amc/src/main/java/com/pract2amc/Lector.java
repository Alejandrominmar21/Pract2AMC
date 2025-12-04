package com.pract2amc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase para leer archivos de formato TSP (Traveling Salesman Problem).
 * Lee coordenadas de puntos desde un archivo con formato específico.
 */
public class Lector {
    /** Archivo TSP a leer */
    File myObj;

    /**
     * Constructor que inicializa el lector con un archivo.
     * 
     * @param obj Archivo TSP a leer
     */
    public Lector(File obj) {
        myObj = obj;
    }

    /**
     * Lee los puntos del archivo TSP y los devuelve como una lista.
     * El archivo debe tener un formato específico con una sección
     * "NODE_COORD_SECTION"
     * seguida de líneas con el formato: ID X Y
     * 
     * @return Lista de puntos leídos del archivo
     */
    public ArrayList<Punto> LeePuntos() {
        ArrayList<Punto> puntos = new ArrayList<>();
        try (Scanner myReader = new Scanner(myObj)) {
            boolean numeros = false;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("NODE_COORD_SECTION")) {
                    numeros = true;
                    continue;
                } else if ("EOF".equals(data)) {
                    // System.out.println("FINAL");
                    numeros = false;
                }

                if (numeros == true) {
                    String[] partes = data.trim().split("\\s+"); // separa por espacios
                    // int id = Integer.parseInt(partes[0]);
                    double x = Double.parseDouble(partes[1].replace(',', '.'));
                    double y = Double.parseDouble(partes[2].replace(',', '.'));

                    System.out.println(" X: " + x + ", Y: " + y);
                    puntos.add(new Punto(x, y));

                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        return puntos;
    }
}
