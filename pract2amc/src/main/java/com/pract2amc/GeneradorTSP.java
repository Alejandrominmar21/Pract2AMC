package com.pract2amc;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase para generar archivos de formato TSP (Traveling Salesman Problem) con
 * puntos aleatorios.
 * Permite crear conjuntos de datos de prueba con diferentes distribuciones de
 * puntos.
 */
public class GeneradorTSP {

    /**
     * Crea un archivo TSP con n puntos aleatorios.
     * El archivo generado sigue el formato estándar TSP con coordenadas EUC_2D.
     * 
     * @param n      Número de puntos a generar
     * @param mismax Si es true, genera puntos con una distribución específica donde
     *               x=1 y y varía según una fórmula especial.
     *               Si es false, genera puntos con coordenadas aleatorias más
     *               uniformemente distribuidas.
     */
    public static void crearArchivoTSP(int n, boolean mismax) {
        Random rand = new Random();
        DecimalFormat df = new DecimalFormat("0.0000000000");
        String nombreArchivo = "dataset" + n + ".tsp";

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("NAME: " + nombreArchivo + "\n");
            writer.write("TYPE: TSP\n");
            writer.write("COMMENT: " + n + " random points\n");
            writer.write("DIMENSION: " + n + "\n");
            writer.write("EDGE_WEIGHT_TYPE: EUC_2D\n");
            writer.write("NODE_COORD_SECTION\n");

            for (int i = 0; i < n; i++) {
                double x, y;

                if (mismax) {
                    double aux1 = rand.nextInt(1000) + 7;
                    y = aux1 / ((double) i + 1 + i * 0.100);
                    int num = rand.nextInt(3);
                    y += ((i % 500) - num * (rand.nextInt(100)));
                    x = 1;
                } else {
                    int num = rand.nextInt(4000) + 1;
                    int den = rand.nextInt(11) + 7;
                    x = num / ((double) den + 0.37);
                    y = (rand.nextInt(4000) + 1) / ((double) (rand.nextInt(11) + 7) + 0.37);
                }

                writer.write((i + 1) + " " + df.format(x) + " " + df.format(y) + "\n");
            }

            writer.write("EOF\n");
            System.out.println("Archivo generado correctamente: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
    /*
     * 
     * Crea un archivo TSP a partir de una lista de puntos dada.
     * El archivo generado sigue el formato estándar TSP con coordenadas EUC_2D.
     * @param puntos   Lista de puntos a incluir en el archivo TSP
     * @param Algoritmo Nombre del algoritmo utilizado, que se incluye en el nombre
     *                  del archivo  
     * 
     */
    public static void crearArchivoTspDeAlgoritmos(ArrayList<Punto> puntos, String Algoritmo) {
        DecimalFormat df = new DecimalFormat("0.0000000000");
        String nombreArchivo = "dataset" + Algoritmo + ".tsp";
        int n = puntos.size();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("NAME: " + nombreArchivo + "\n");
            writer.write("TYPE: TSP\n");
            writer.write("COMMENT: " + n + " \n");
            writer.write("DIMENSION: " + n + "\n");
            writer.write("EDGE_WEIGHT_TYPE: EUC_2D\n");
            writer.write("NODE_COORD_SECTION\n");

            for (int i = 0; i < n; i++) {
                writer.write((i + 1) + " " + df.format(puntos.get(i).getX()) + " " + df.format(puntos.get(i).getY()) + "\n");
            }

            writer.write("EOF\n");
            System.out.println("Archivo generado correctamente: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    /**
     * Genera el fichero de solución con el formato específico de la Práctica 2.
     * @param rutaFichero Nombre o ruta del archivo a guardar (ej: "solucion_voraz.txt")
     * @param camino Objeto Camino con la solución calculada
     * @param numCiudades Número total de ciudades (DIMENSION)
     */
    public static void guardarSolucion(String rutaFichero, Camino camino, int numCiudades) {
        try (FileWriter writer = new FileWriter(rutaFichero)) {
            writer.write("NAME : " + rutaFichero + "\n");
            writer.write("TYPE : TOUR\n");
            writer.write("DIMENSION : " + numCiudades + "\n");

            // Usamos replace para asegurar el punto decimal si fuera necesario
            writer.write("SOLUTION: " + String.format("%.2f", camino.getDistanciaFinal()).replace(',', '.') + "\n");
            
            writer.write("TOUR_SECTION\n");

            int[] indices = camino.getCamino();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < indices.length; i++) {
                sb.append(indices[i] + 1); // +1 para pasar de índice 0 a etiqueta 1
                if (i < indices.length - 1) {
                    sb.append(",");
                }
            }
            // Cerrar el ciclo visualmente añadiendo el primero al final
            sb.append("," + (indices[0] + 1)); 
            writer.write(sb.toString() + "\n");

            ArrayList<Double> distanciasPasos = camino.getDistancias();
            
            for (int i = 0; i < distanciasPasos.size(); i++) {
                // Nodos (ajustando índice base 0 a base 1)
                int u = indices[i] + 1;
                int v = (i == indices.length - 1) ? indices[0] + 1 : indices[i + 1] + 1;
                
                double coste = distanciasPasos.get(i);
                
                // Formateamos el coste. Si prefieres sin decimales cuando es entero, 
                // puedes usar DecimalFormat, aquí mantengo %.2f por precisión.
                String costeStr = String.format("%.2f", coste).replace(',', '.');

                if (costeStr.endsWith(".00")) {
                     costeStr = costeStr.substring(0, costeStr.length() - 3);
                }

                writer.write(costeStr + " - " + u + "," + v + "\n");
            }

            writer.write("EOF\n");
            System.out.println("Fichero de solución guardado correctamente: " + rutaFichero);

        } catch (IOException e) {
            System.err.println("Error al guardar la solución: " + e.getMessage());
        }
    }
}
