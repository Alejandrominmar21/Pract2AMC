package com.pract2amc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Algoritmos {

    // Variable para contar operaciones (requisito de la práctica para las tablas)
    private static long distanciasCalculadas = 0;

    /**
     * Calcula la distancia euclídea y aumenta el contador de operaciones.
     */
    private static double calcularDistancia(Punto p1, Punto p2) {
        distanciasCalculadas++;
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    // =========================================================================
    // 1. VORAZ EXHAUSTIVO UNIDIRECCIONAL
    // =========================================================================
    public static Camino vorazExhaustivoUnidireccional(ArrayList<Punto> puntos) {
        distanciasCalculadas = 0; // Reset contador
        long tiempoInicio = System.nanoTime();

        int n = puntos.size();
        boolean[] visitados = new boolean[n];
        int[] caminoIndices = new int[n];
        ArrayList<Double> distanciasPasos = new ArrayList<>();
        
        // Empezamos por el primer punto (o uno aleatorio según se prefiera, la práctica suele usar el 0)
        int actualIdx = 0; 
        visitados[actualIdx] = true;
        caminoIndices[0] = actualIdx;
        
        double distanciaTotal = 0;

        for (int i = 1; i < n; i++) {
            int mejorVecino = -1;
            double minDistancia = Double.MAX_VALUE;

            // Búsqueda exhaustiva del vecino más cercano no visitado
            for (int j = 0; j < n; j++) {
                if (!visitados[j]) {
                    double d = calcularDistancia(puntos.get(actualIdx), puntos.get(j));
                    if (d < minDistancia) {
                        minDistancia = d;
                        mejorVecino = j;
                    }
                }
            }

            // Movernos al mejor vecino
            visitados[mejorVecino] = true;
            caminoIndices[i] = mejorVecino;
            distanciasPasos.add(minDistancia);
            distanciaTotal += minDistancia;
            actualIdx = mejorVecino;
        }

        // Cerrar el ciclo (volver al inicio)
        double vuelta = calcularDistancia(puntos.get(actualIdx), puntos.get(caminoIndices[0]));
        distanciaTotal += vuelta;
        distanciasPasos.add(vuelta);

        long tiempoFin = System.nanoTime();
        double tiempoMs = (tiempoFin - tiempoInicio) / 1_000_000.0;

        // Aquí podrías guardar 'distanciasCalculadas' si modificas Camino para aceptarlo
        return new Camino(distanciaTotal, caminoIndices, distanciasPasos, tiempoMs);
    }

    // =========================================================================
    // 2. VORAZ EXHAUSTIVO BIDIRECCIONAL
    // =========================================================================
    public static Camino vorazExhaustivoBidireccional(ArrayList<Punto> puntos) {
        distanciasCalculadas = 0;
        long tiempoInicio = System.nanoTime();

        int n = puntos.size();
        boolean[] visitados = new boolean[n];
        
        // Usamos una Deque (doble cola) lógica mediante un array o lista enlazada.
        // Para simplificar y devolver int[], llenaremos un array desde el centro o usando punteros.
        // Una forma fácil: Mantener una lista de índices ordenados del camino.
        java.util.LinkedList<Integer> caminoLista = new java.util.LinkedList<>();

        int inicio = 0; // Empezamos por el punto 0
        visitados[inicio] = true;
        caminoLista.add(inicio);

        int extremoIzq = inicio;
        int extremoDer = inicio;
        double distanciaTotal = 0;
        ArrayList<Double> distanciasPasos = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            int mejorCandidatoIzq = -1;
            double minDistIzq = Double.MAX_VALUE;
            
            int mejorCandidatoDer = -1;
            double minDistDer = Double.MAX_VALUE;

            // Buscar el más cercano al extremo izquierdo
            for (int j = 0; j < n; j++) {
                if (!visitados[j]) {
                    double d = calcularDistancia(puntos.get(extremoIzq), puntos.get(j));
                    if (d < minDistIzq) {
                        minDistIzq = d;
                        mejorCandidatoIzq = j;
                    }
                }
            }

            // Buscar el más cercano al extremo derecho
            for (int j = 0; j < n; j++) {
                if (!visitados[j]) {
                    double d = calcularDistancia(puntos.get(extremoDer), puntos.get(j));
                    if (d < minDistDer) {
                        minDistDer = d;
                        mejorCandidatoDer = j;
                    }
                }
            }

            // Comparar extremos y decidir
            if (minDistIzq < minDistDer) {
                visitados[mejorCandidatoIzq] = true;
                caminoLista.addFirst(mejorCandidatoIzq);
                extremoIzq = mejorCandidatoIzq;
                distanciaTotal += minDistIzq;
                distanciasPasos.add(minDistIzq);
            } else {
                visitados[mejorCandidatoDer] = true;
                caminoLista.addLast(mejorCandidatoDer);
                extremoDer = mejorCandidatoDer;
                distanciaTotal += minDistDer;
                distanciasPasos.add(minDistDer);
            }
        }

        // Cerrar el ciclo
        double vuelta = calcularDistancia(puntos.get(extremoIzq), puntos.get(extremoDer));
        distanciaTotal += vuelta;
        distanciasPasos.add(vuelta);

        // Convertir LinkedList a int[]
        int[] caminoIndices = caminoLista.stream().mapToInt(i -> i).toArray();

        long tiempoFin = System.nanoTime();
        double tiempoMs = (tiempoFin - tiempoInicio) / 1_000_000.0;

        return new Camino(distanciaTotal, caminoIndices, distanciasPasos, tiempoMs);
    }

    // =========================================================================
    // 3. VORAZ CON PODA UNIDIRECCIONAL
    // =========================================================================
    public static Camino vorazPodaUnidireccional(ArrayList<Punto> puntos) {
        distanciasCalculadas = 0;
        long tiempoInicio = System.nanoTime();
        int n = puntos.size();
        
        // 1. Crear una lista auxiliar de punteros (índices) ordenada por coordenada X
        Integer[] indicesOrdenadosX = new Integer[n];
        for(int i=0; i<n; i++) indicesOrdenadosX[i] = i;
        
        Arrays.sort(indicesOrdenadosX, (a, b) -> Double.compare(puntos.get(a).getX(), puntos.get(b).getX()));

        // Mapa inverso: dado el ID original del punto, saber dónde está en el array ordenado
        int[] posEnOrdenado = new int[n];
        for(int i=0; i<n; i++) posEnOrdenado[indicesOrdenadosX[i]] = i;

        boolean[] visitados = new boolean[n];
        int[] caminoIndices = new int[n];
        ArrayList<Double> distanciasPasos = new ArrayList<>();

        int actualIdx = 0; // Empezamos en el punto 0 original
        visitados[actualIdx] = true;
        caminoIndices[0] = actualIdx;
        double distanciaTotal = 0;

        for (int i = 1; i < n; i++) {
            int mejorVecino = -1;
            double minDistancia = Double.MAX_VALUE;

            // Obtenemos la posición del punto actual en el array ordenado
            int posActual = posEnOrdenado[actualIdx];

            // BARRIDO HACIA ADELANTE Y ATRÁS (PODA)
            // Empezamos a mirar vecinos en el array ordenado a izquierda y derecha
            // Paramos cuando la diferencia en X sea mayor que la minDistancia actual encontrada
            
            // Simulación de barrido "radial" en el array ordenado
            for (int offset = 1; ; offset++) {
                boolean posibleIzquierda = (posActual - offset >= 0);
                boolean posibleDerecha = (posActual + offset < n);
                
                if (!posibleIzquierda && !posibleDerecha) break; // Fin del array

                // Revisar izquierda
                if (posibleIzquierda) {
                    int candidatoIdx = indicesOrdenadosX[posActual - offset];
                    double diffX = Math.abs(puntos.get(actualIdx).getX() - puntos.get(candidatoIdx).getX());
                    
                    // CRITERIO DE PODA: Si la diferencia solo en X ya es mayor que la mejor distancia hallada,
                    // no hace falta seguir mirando más a la izquierda, porque la distancia euclídea será aún mayor.
                    if (diffX < minDistancia) {
                        if (!visitados[candidatoIdx]) {
                            double d = calcularDistancia(puntos.get(actualIdx), puntos.get(candidatoIdx));
                            if (d < minDistancia) {
                                minDistancia = d;
                                mejorVecino = candidatoIdx;
                            }
                        }
                    } else {
                        // Poda efectiva hacia este lado (ya no miramos más a la izquierda)
                        posibleIzquierda = false; 
                    }
                }
                
                // Revisar derecha (misma lógica)
                if (posibleDerecha) {
                    int candidatoIdx = indicesOrdenadosX[posActual + offset];
                    double diffX = Math.abs(puntos.get(actualIdx).getX() - puntos.get(candidatoIdx).getX());
                    
                    if (diffX < minDistancia) {
                        if (!visitados[candidatoIdx]) {
                            double d = calcularDistancia(puntos.get(actualIdx), puntos.get(candidatoIdx));
                            if (d < minDistancia) {
                                minDistancia = d;
                                mejorVecino = candidatoIdx;
                            }
                        }
                    } else {
                        posibleDerecha = false;
                    }
                }
                
                // Si por la diferencia de X ya no podemos mejorar ni por izq ni por der, salimos
                if (minDistancia != Double.MAX_VALUE) {
                     // Comprobamos si podemos abortar el bucle completo
                     double diffXIzq = posibleIzquierda ? Math.abs(puntos.get(actualIdx).getX() - puntos.get(indicesOrdenadosX[posActual - offset]).getX()) : Double.MAX_VALUE;
                     double diffXDer = posibleDerecha ? Math.abs(puntos.get(actualIdx).getX() - puntos.get(indicesOrdenadosX[posActual + offset]).getX()) : Double.MAX_VALUE;
                     
                     if (diffXIzq >= minDistancia && diffXDer >= minDistancia) {
                         break;
                     }
                }
            }

            visitados[mejorVecino] = true;
            caminoIndices[i] = mejorVecino;
            distanciasPasos.add(minDistancia);
            distanciaTotal += minDistancia;
            actualIdx = mejorVecino;
        }

        double vuelta = calcularDistancia(puntos.get(actualIdx), puntos.get(caminoIndices[0]));
        distanciaTotal += vuelta;
        distanciasPasos.add(vuelta);

        long tiempoFin = System.nanoTime();
        double tiempoMs = (tiempoFin - tiempoInicio) / 1_000_000.0;
        
        return new Camino(distanciaTotal, caminoIndices, distanciasPasos, tiempoMs);
    }
    
    // =========================================================================
    // 4. VORAZ CON PODA BIDIRECCIONAL
    // =========================================================================
    
    
    public static Camino vorazPodaBidireccional(ArrayList<Punto> puntos) {
        distanciasCalculadas = 0;
        long tiempoInicio = System.nanoTime();
        int n = puntos.size();
        
        // Estructuras para la poda
        Integer[] indicesOrdenadosX = new Integer[n];
        for(int i=0; i<n; i++) indicesOrdenadosX[i] = i;
        Arrays.sort(indicesOrdenadosX, (a, b) -> Double.compare(puntos.get(a).getX(), puntos.get(b).getX()));

        int[] posEnOrdenado = new int[n];
        for(int i=0; i<n; i++) posEnOrdenado[indicesOrdenadosX[i]] = i;

        // Estructuras del camino
        boolean[] visitados = new boolean[n];
        java.util.LinkedList<Integer> caminoLista = new java.util.LinkedList<>();
        
        int inicio = 0; 
        visitados[inicio] = true;
        caminoLista.add(inicio);

        int extremoIzq = inicio;
        int extremoDer = inicio;
        double distanciaTotal = 0;
        ArrayList<Double> distanciasPasos = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            // Buscamos candidato para extremo Izquierdo con PODA
            ResultadoBusqueda resIzq = buscarConPoda(puntos, extremoIzq, indicesOrdenadosX, posEnOrdenado, visitados);
            
            // Buscamos candidato para extremo Derecho con PODA
            ResultadoBusqueda resDer = buscarConPoda(puntos, extremoDer, indicesOrdenadosX, posEnOrdenado, visitados);

            if (resIzq.distancia < resDer.distancia) {
                visitados[resIzq.indice] = true;
                caminoLista.addFirst(resIzq.indice);
                extremoIzq = resIzq.indice;
                distanciaTotal += resIzq.distancia;
                distanciasPasos.add(resIzq.distancia);
            } else {
                visitados[resDer.indice] = true;
                caminoLista.addLast(resDer.indice);
                extremoDer = resDer.indice;
                distanciaTotal += resDer.distancia;
                distanciasPasos.add(resDer.distancia);
            }
        }

        double vuelta = calcularDistancia(puntos.get(extremoIzq), puntos.get(extremoDer));
        distanciaTotal += vuelta;
        distanciasPasos.add(vuelta);

        int[] caminoIndices = caminoLista.stream().mapToInt(x -> x).toArray();
        long tiempoFin = System.nanoTime();
        double tiempoMs = (tiempoFin - tiempoInicio) / 1_000_000.0;
        
        return new Camino(distanciaTotal, caminoIndices, distanciasPasos, tiempoMs);
    }

    // Clase auxiliar y método para reutilizar la lógica de poda
    private static class ResultadoBusqueda {
        int indice;
        double distancia;
        public ResultadoBusqueda(int indice, double distancia) { this.indice = indice; this.distancia = distancia; }
    }

    private static ResultadoBusqueda buscarConPoda(ArrayList<Punto> puntos, int puntoRefIdx, 
                                                   Integer[] indicesOrdenadosX, int[] posEnOrdenado, 
                                                   boolean[] visitados) {
        int n = puntos.size();
        int posActual = posEnOrdenado[puntoRefIdx];
        double minDistancia = Double.MAX_VALUE;
        int mejorVecino = -1;

        for (int offset = 1; ; offset++) {
            boolean checkIzq = (posActual - offset >= 0);
            boolean checkDer = (posActual + offset < n);
            if (!checkIzq && !checkDer) break;

            if (checkIzq) {
                int cand = indicesOrdenadosX[posActual - offset];
                double diffX = Math.abs(puntos.get(puntoRefIdx).getX() - puntos.get(cand).getX());
                if (diffX < minDistancia) {
                    if (!visitados[cand]) {
                        double d = calcularDistancia(puntos.get(puntoRefIdx), puntos.get(cand));
                        if (d < minDistancia) {
                            minDistancia = d;
                            mejorVecino = cand;
                        }
                    }
                } else checkIzq = false; 
            }

            if (checkDer) {
                int cand = indicesOrdenadosX[posActual + offset];
                double diffX = Math.abs(puntos.get(puntoRefIdx).getX() - puntos.get(cand).getX());
                if (diffX < minDistancia) {
                    if (!visitados[cand]) {
                        double d = calcularDistancia(puntos.get(puntoRefIdx), puntos.get(cand));
                        if (d < minDistancia) {
                            minDistancia = d;
                            mejorVecino = cand;
                        }
                    }
                } else checkDer = false;
            }
            
            // Condición de salida optimizada
            double diffXIzq = (posActual - offset >= 0) ? Math.abs(puntos.get(puntoRefIdx).getX() - puntos.get(indicesOrdenadosX[posActual - offset]).getX()) : Double.MAX_VALUE;
            double diffXDer = (posActual + offset < n) ? Math.abs(puntos.get(puntoRefIdx).getX() - puntos.get(indicesOrdenadosX[posActual + offset]).getX()) : Double.MAX_VALUE;
            
            if (minDistancia != Double.MAX_VALUE && diffXIzq >= minDistancia && diffXDer >= minDistancia) {
                break;
            }
        }
        return new ResultadoBusqueda(mejorVecino, minDistancia);
    }
    
    // Método getter para obtener las estadísticas si lo necesitas fuera
    public static long getDistanciasCalculadas() {
        return distanciasCalculadas;
    }
}
