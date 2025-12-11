package com.pract2amc;

/**
     * Clase auxiliar para la tabla de comparación Uni vs Bi.
     * Almacena los resultados agregados (victorias y tiempos medios) por talla.
     */
    public class FilaEstadistica {
        private final String estrategia;
        private final int[] victorias;
        private final double[] tiempos;

        public FilaEstadistica(String estrategia, int[] victorias, double[] tiempos) {
            this.estrategia = estrategia;
            this.victorias = victorias;
            this.tiempos = tiempos;
        }

        public String getEstrategia() { return estrategia; }

        // Métodos de acceso por índice para las columnas dinámicas
        public Integer getVictoriasAt(int index) {
            if (victorias != null && index >= 0 && index < victorias.length) {
                return victorias[index];
            }
            return 0;
        }

        public Double getTiempoAt(int index) {
            if (tiempos != null && index >= 0 && index < tiempos.length) {
                return tiempos[index];
            }
            return 0.0;
        }
    }