package com.pract2amc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage = s;
        stage.setScene(crearMenu(stage));
        stage.setTitle("Análisis de Algoritmos");
        stage.show();
    }

 
    public static void main(String[] args) {
        launch(args);
    }

    private Scene crearMenu(Stage stage) {
        // File myObj = new File("berlin52.tsp");

        // Lector prueba = new Lector(myObj);
        // ArrayList<Punto> puntos = prueba.LeePuntos();

        Button comparar4Est = new Button("Comparar todas las estrategias (.tsp aleatorio)");
        Button comparar2Est = new Button("Comparar dos estrategias (.tsp aleatorio)");
        Button comprobarEstrategias = new Button("Comprobar todas las estrategias (dataset cargado)");
        Button uniVsBi = new Button("Unidireccional vs Bidireccional (.tsp aleatorio)");
        Button btnSalir = new Button("Salir");

        // Acciones al hacer clic

        comparar4Est.setOnAction(e -> comparar4(stage));
        comparar2Est.setOnAction(e -> menuSeleccionCompararDos(stage));
        comprobarEstrategias.setOnAction(e -> compararEstrategias(stage));
        uniVsBi.setOnAction(e -> menuSeleccionUniVsBi(stage));
        // En crearMenu(Stage stage) ...
// Suponiendo que el botón es 'comparar4Est' o crea uno nuevo 'btnGrafica'
Button btnGrafica = new Button("Ver Gráfica de Algoritmo");
btnGrafica.setOnAction(e -> menuSeleccionGrafica(stage));

// Añade btnGrafica a tu VBox del menú
        // uniVsBi.setOnAction(e -> stage.setScene(compararUniVsBi(stage)));

        btnSalir.setOnAction(e -> stage.close());

        // Organizar botones en un layout vertical
        VBox menu = new VBox(15, comparar4Est, comparar2Est, comprobarEstrategias, uniVsBi,btnGrafica, btnSalir);
        menu.setAlignment(Pos.CENTER);

        return new Scene(menu, 1200, 800);
    }

    public void comparar4(Stage stage) {
      
        int[] Tallas = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };
        Camino ResultadosVorazExhaustivoUnidireccional[] = new Camino[Tallas.length];
        Camino ResultadosVorazExhaustivoBidireccional[] = new Camino[Tallas.length];
        Camino ResultadosVorazPodaUnidireccional[] = new Camino[Tallas.length];
        Camino ResultadosVorazPodaBidireccional[] = new Camino[Tallas.length];

        for (int i = 0; i < Tallas.length; i++) {
            GeneradorTSP.crearArchivoTSP(Tallas[i], false);
            File myObj = new File("dataset" + Tallas[i] + ".tsp");
            Lector prueba = new Lector(myObj);
            ArrayList<Punto> puntosDataset = prueba.LeePuntos();

            ResultadosVorazExhaustivoUnidireccional[i] = Algoritmos.vorazExhaustivoUnidireccional(puntosDataset);
            ResultadosVorazExhaustivoBidireccional[i] = Algoritmos.vorazExhaustivoBidireccional(puntosDataset);
            ResultadosVorazPodaUnidireccional[i] = Algoritmos.vorazPodaUnidireccional(puntosDataset);
            ResultadosVorazPodaBidireccional[i] = Algoritmos.vorazPodaBidireccional(puntosDataset);

        }
        compararTiemposPorTalla(stage, Tallas,
                ResultadosVorazExhaustivoUnidireccional, ResultadosVorazExhaustivoBidireccional,
                ResultadosVorazPodaUnidireccional, ResultadosVorazPodaBidireccional);
    }

    public void compararEstrategias(Stage stage) {
        Lector lector = new Lector(new File("pract2amc/datasets/berlin52.tsp"));
        ArrayList<Punto> puntosDataset = lector.LeePuntos();

        imprimirCompararEstrategias(stage,
                Algoritmos.vorazExhaustivoUnidireccional(puntosDataset),
                Algoritmos.vorazExhaustivoBidireccional(puntosDataset),
                Algoritmos.vorazPodaUnidireccional(puntosDataset),
                Algoritmos.vorazPodaBidireccional(puntosDataset));

    }

    public void compararDos(Stage stage, String alg1, String alg2) {
        int[] Tallas = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };
        Camino Resultados1[] = new Camino[Tallas.length];
        Camino Resultados2[] = new Camino[Tallas.length];

        for (int i = 0; i < Tallas.length; i++) {
            GeneradorTSP.crearArchivoTSP(Tallas[i], false);
            File myObj = new File("dataset" + Tallas[i] + ".tsp");
            Lector prueba = new Lector(myObj);
            ArrayList<Punto> puntosDataset = prueba.LeePuntos();

            switch (alg1) {
                case "Voraz Exhaustivo Unidireccional":
                    Resultados1[i] = Algoritmos.vorazExhaustivoUnidireccional(puntosDataset);
                    break;
                case "Voraz Exhaustivo Bidireccional":
                    Resultados1[i] = Algoritmos.vorazExhaustivoBidireccional(puntosDataset);
                    break;
                case "Voraz Poda Unidireccional":
                    Resultados1[i] = Algoritmos.vorazPodaUnidireccional(puntosDataset);
                    break;
                case "Voraz Poda Bidireccional":
                    Resultados1[i] = Algoritmos.vorazPodaBidireccional(puntosDataset);
                    break;
                default:
                    // return null;
            }
            switch (alg2) {
                case "Voraz Exhaustivo Unidireccional":
                    Resultados2[i] = Algoritmos.vorazExhaustivoUnidireccional(puntosDataset);
                    break;
                case "Voraz Exhaustivo Bidireccional":
                    Resultados2[i] = Algoritmos.vorazExhaustivoBidireccional(puntosDataset);
                    break;
                case "Voraz Poda Unidireccional":
                    Resultados2[i] = Algoritmos.vorazPodaUnidireccional(puntosDataset);
                    break;
                case "Voraz Poda Bidireccional":
                    Resultados2[i] = Algoritmos.vorazPodaBidireccional(puntosDataset);
                    break;
                default:
                    // return null;
            }
            // ResultadosVorazExhaustivoUnidireccional[i] =
            // Algoritmos.vorazExhaustivoUnidireccional(puntosDataset);
            // ResultadosVorazExhaustivoBidireccional[i] =
            // Algoritmos.vorazExhaustivoBidireccional(puntosDataset);

        }
        compararDosEstrategias(stage, Tallas, alg1, Resultados1, alg2, Resultados2);

    }

    public void compararUniVsBi(Stage stage,String tipoBase, int repeticiones) {
        int[] tallas;
        if (tipoBase.contains("Poda")) {
            tallas = new int[]{500, 1000, 2000, 3000, 4000}; // Poda aguanta más
        }else {
            tallas = new int[]{100, 200, 300, 400, 500}; // Exhaustivo es lento, tallas menores
        }

        // Arrays para guardar los acumulados
        int[] winsUni = new int[tallas.length];
        double[] avgTimeUni = new double[tallas.length];
        
        int[] winsBi = new int[tallas.length];
        double[] avgTimeBi = new double[tallas.length];

        try {
            for (int i = 0; i < tallas.length; i++) {
                int n = tallas[i];
                double sumaTiempoUni = 0;
                double sumaTiempoBi = 0;

                for (int r = 0; r < repeticiones; r++) {
                    // 1. Generar Dataset Aleatorio Temporal
                    GeneradorTSP.crearArchivoTSP(n, false);
                    File file = new File("dataset" + n + ".tsp");
                    Lector lector = new Lector(file);
                    ArrayList<Punto> puntos = lector.LeePuntos();

                    // 2. Ejecutar Algoritmos según la selección
                    Camino resUni, resBi;

                    if (tipoBase.equals("Voraz Exhaustivo")) {
                        resUni = Algoritmos.vorazExhaustivoUnidireccional(puntos);
                        resBi = Algoritmos.vorazExhaustivoBidireccional(puntos);
                    } else { // "Voraz con Poda"
                        resUni = Algoritmos.vorazPodaUnidireccional(puntos);
                        resBi = Algoritmos.vorazPodaBidireccional(puntos);
                    }

                    // 3. Acumular Tiempos
                    sumaTiempoUni += resUni.getTiempo();
                    sumaTiempoBi += resBi.getTiempo();

                    // 4. Determinar Victoria (quién tiene MENOR distancia)
                    double distUni = resUni.getDistanciaFinal();
                    double distBi = resBi.getDistanciaFinal();
                    
                    // Margen de error para double
                    if (distUni < distBi - 0.0000001) {
                        winsUni[i]++;
                    } else if (distBi < distUni - 0.0000001) {
                        winsBi[i]++;
                    }
                    // Si son iguales, no suma a nadie (empate)
                }

                // Calcular medias para esta talla
                avgTimeUni[i] = sumaTiempoUni / repeticiones;
                avgTimeBi[i] = sumaTiempoBi / repeticiones;
            }

            // Llamar a la tabla de visualización (pasamos los arrays de primitivos calculados)
            mostrarComparacionUniVsBi(stage, tallas, winsUni, avgTimeUni, winsBi, avgTimeBi);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error en simulación: " + ex.getMessage());
        }
    }

    /**
     * Crea una gráfica que muestra todos los puntos y dibuja el camino (ruta)
     * completo.
     * * @param puntosDataset Lista de todos los puntos disponibles (referencia para
     * los índices).
     * 
     * @param solucion Objeto Camino que contiene los índices de la ruta a trazar.
     * @param stage    Ventana donde se mostrará la gráfica.
     */
    public void crearGrafica(ArrayList<Punto> puntosDataset, Camino solucion, Stage stage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        // Se definen etiquetas si es necesario, o se dejan vacías para limpieza visual
        xAxis.setLabel("Eje X");
        yAxis.setLabel("Eje Y");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Visualización del Camino TSP");
        chart.setCreateSymbols(true); // Mostrar los puntos (nodos)

        // Serie 0: Todos los puntos del dataset (nube de puntos)
        XYChart.Series<Number, Number> puntosSeries = new XYChart.Series<>();
        puntosSeries.setName("Nodos");
        for (Punto punto : puntosDataset) {
            puntosSeries.getData().add(new XYChart.Data<>(punto.getX(), punto.getY()));
        }

        // Serie 1: El camino solución (línea que conecta los puntos en orden)
        XYChart.Series<Number, Number> caminoSeries = new XYChart.Series<>();
        caminoSeries.setName("Ruta Solución");

        int[] indicesCamino = solucion.getCamino();

        if (indicesCamino != null) {
            for (int index : indicesCamino) {
                // Validación de límites para evitar IndexOutOfBoundsException
                if (index >= 0 && index < puntosDataset.size()) {
                    Punto p = puntosDataset.get(index);
                    caminoSeries.getData().add(new XYChart.Data<>(p.getX(), p.getY()));
                }
            }
        }

        chart.getData().add(puntosSeries);
        chart.getData().add(caminoSeries);

        Scene scene = new Scene(chart, 1200, 800);
        stage.setScene(scene);

        // Estilo CSS mediante Platform.runLater para asegurar que los nodos ya existen
        // en el Scene Graph
        Platform.runLater(() -> {
            // Serie 0 (Nodos): Ocultar líneas, mostrar solo símbolos (puntos dispersos)
            chart.lookupAll(".series0.chart-series-line").forEach(n -> n.setStyle("-fx-stroke: transparent;"));

            // Serie 1 (Camino): Línea roja y gruesa para resaltar la ruta.
            // Opcional: Ocultar símbolos de la serie 1 si se superponen demasiado, o
            // dejarlos.
            chart.lookupAll(".series1.chart-series-line")
                    .forEach(n -> n.setStyle("-fx-stroke: red; -fx-stroke-width: 2;"));
        });
    }

    /**
     * Clase para la tabla de UN solo dataset (Método 1)
     * Muestra métricas detalladas: Calculadas, Solución, Tiempo.
     */
    public static class FilaDetalle {
        private final String estrategia;
        private final long calculadas;
        private final double solucion;
        private final double tiempo;

        public FilaDetalle(String estrategia, Camino c) {
            this.estrategia = estrategia;
            // Se valida null para evitar errores si el algoritmo no se ejecutó
            if (c != null) {
                this.calculadas = c.getCalculadas();
                this.solucion = c.getDistanciaFinal();
                this.tiempo = c.getTiempo();
            } else {
                this.calculadas = 0;
                this.solucion = 0;
                this.tiempo = 0;
            }
        }

        public String getEstrategia() {
            return estrategia;
        }

        public long getCalculadas() {
            return calculadas;
        }

        public double getSolucion() {
            return solucion;
        }

        public double getTiempo() {
            return tiempo;
        }
    }

    /**
     * Clase para la tabla de MÚLTIPLES tallas (Método 2)
     * Guarda el nombre del algoritmo y un array con los tiempos por talla.
     */
    public static class FilaTiempos {
        private final String estrategia;
        private final double[] tiempos; // Tiempos correspondientes a cada talla en orden

        public FilaTiempos(String estrategia, Camino[] resultados) {
            this.estrategia = estrategia;
            this.tiempos = new double[resultados.length];
            for (int i = 0; i < resultados.length; i++) {
                if (resultados[i] != null) {
                    this.tiempos[i] = resultados[i].getTiempo();
                } else {
                    this.tiempos[i] = -1; // O valor indicativo de error/no ejecutado
                }
            }
        }

        public String getEstrategia() {
            return estrategia;
        }

        // Método para obtener el tiempo según el índice de la columna (talla)
        public Double getTiempoAt(int index) {
            if (index >= 0 && index < tiempos.length) {
                return tiempos[index];
            }
            return null;
        }
    }

    /**
     * Carga un único dataset y muestra columnas {estrategia, calculadas,
     * solucion, tiempo}.
     */
    public void imprimirCompararEstrategias(Stage stage,
            Camino resExhaustivoUni,
            Camino resExhaustivoBi,
            Camino resPodaUni,
            Camino resPodaBi) {

        Label titulo = new Label("Comparativa Detallada (Dataset Único)");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<FilaDetalle> tabla = new TableView<>();

        // 1. Columna Estrategia
        TableColumn<FilaDetalle, String> colEstrategia = new TableColumn<>("Estrategia");
        colEstrategia.setCellValueFactory(new PropertyValueFactory<>("estrategia"));
        colEstrategia.setMinWidth(250);

        // 2. Columna Calculadas (Operaciones)
        TableColumn<FilaDetalle, Long> colCalculadas = new TableColumn<>("Calculadas");
        colCalculadas.setCellValueFactory(new PropertyValueFactory<>("calculadas"));
        colCalculadas.setMinWidth(150);

        // 3. Columna Solución (Distancia)
        TableColumn<FilaDetalle, String> colSolucion = new TableColumn<>("Solución");
        colSolucion.setCellValueFactory(
                cell -> new SimpleStringProperty(String.format("%.4f", cell.getValue().getSolucion())));
        colSolucion.setMinWidth(150);

        // 4. Columna Tiempo
        TableColumn<FilaDetalle, String> colTiempo = new TableColumn<>("Tiempo (ms)");
        colTiempo.setCellValueFactory(
                cell -> new SimpleStringProperty(String.format("%.6f", cell.getValue().getTiempo())));
        colTiempo.setMinWidth(150);

        tabla.getColumns().addAll(colEstrategia, colCalculadas, colSolucion, colTiempo);

        // Añadir las 4 filas (una por algoritmo)
        ObservableList<FilaDetalle> datos = FXCollections.observableArrayList(
                new FilaDetalle("Voraz Exhaustivo Unidireccional", resExhaustivoUni),
                new FilaDetalle("Voraz Exhaustivo Bidireccional", resExhaustivoBi),
                new FilaDetalle("Voraz Poda Unidireccional", resPodaUni),
                new FilaDetalle("Voraz Poda Bidireccional", resPodaBi));

        tabla.setItems(datos);

        // Botón y Layout
        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, titulo, tabla, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 1000, 600));
    }

    /**
     * Usa diferentes tallas y muestra el TIEMPO para cada talla en
     * columnas dinámicas.
     * 
     * @param tallas           Array con las tallas ejecutadas (ej: {100, 200,
     *                         500}).
     * @param resultadosArrays Arrays de caminos, donde cada array tiene el mismo
     *                         tamaño que 'tallas'.
     */
    public void compararTiemposPorTalla(Stage stage, int[] tallas,
            Camino[] resExhaustivoUni,
            Camino[] resExhaustivoBi,
            Camino[] resPodaUni,
            Camino[] resPodaBi) {

        Label titulo = new Label("Comparativa de Tiempos por Talla (ms)");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<FilaTiempos> tabla = new TableView<>();

        // 1. Columna Fija: Estrategia
        TableColumn<FilaTiempos, String> colEstrategia = new TableColumn<>("Estrategia");
        colEstrategia.setCellValueFactory(new PropertyValueFactory<>("estrategia"));
        colEstrategia.setMinWidth(250);
        tabla.getColumns().add(colEstrategia);

        // 2. Columnas Dinámicas: Una por cada Talla
        if (tallas != null) {
            for (int i = 0; i < tallas.length; i++) {
                final int indiceColumna = i; // Necesario para usar dentro de la lambda
                TableColumn<FilaTiempos, String> colTalla = new TableColumn<>("Talla " + tallas[i]);

                // Factoría de celda personalizada: busca en el array de tiempos de la fila
                colTalla.setCellValueFactory(cellData -> {
                    Double tiempo = cellData.getValue().getTiempoAt(indiceColumna);
                    if (tiempo != null) {
                        return new SimpleStringProperty(String.format("%.4f", tiempo));
                    }
                    return new SimpleStringProperty("-");
                });

                colTalla.setMinWidth(100);
                tabla.getColumns().add(colTalla);
            }
        }

        // Añadir las filas (una por algoritmo, conteniendo todos sus tiempos)
        ObservableList<FilaTiempos> datos = FXCollections.observableArrayList(
                new FilaTiempos("Voraz Exhaustivo Unidireccional", resExhaustivoUni),
                new FilaTiempos("Voraz Exhaustivo Bidireccional", resExhaustivoBi),
                new FilaTiempos("Voraz Poda Unidireccional", resPodaUni),
                new FilaTiempos("Voraz Poda Bidireccional", resPodaBi));

        tabla.setItems(datos);

        // Botón y Layout
        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, titulo, tabla, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 1200, 800));
    }

    

    // --- MÉTODO PARA VISUALIZAR LA COMPARACIÓN DE 2 ESTRATEGIAS ---
    /**
     * Muestra una tabla comparativa para dos estrategias específicas a través de
     * múltiples tallas.
     * Crea columnas anidadas (Tiempo y Calculadas) para cada Talla.
     *
     * @param stage      Ventana actual.
     * @param tallas     Array de tallas ejecutadas (ej: {100, 200, 500}).
     * @param nombreEst1 Nombre de la primera estrategia.
     * @param res1       Resultados (Caminos) de la primera estrategia.
     * @param nombreEst2 Nombre de la segunda estrategia.
     * @param res2       Resultados (Caminos) de la segunda estrategia.
     */
    public void compararDosEstrategias(Stage stage, int[] tallas,
            String nombreEst1, Camino[] res1,
            String nombreEst2, Camino[] res2) {

        Label titulo = new Label("Comparación: " + nombreEst1 + " vs " + nombreEst2);
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<FilaComparacion> tabla = new TableView<>();

        // 1. Columna Fija: Nombre de la Estrategia
        TableColumn<FilaComparacion, String> colEstrategia = new TableColumn<>("Estrategia");
        colEstrategia.setCellValueFactory(new PropertyValueFactory<>("estrategia"));
        colEstrategia.setMinWidth(200);
        tabla.getColumns().add(colEstrategia);

        // 2. Columnas Dinámicas por Talla (Anidadas)
        if (tallas != null) {
            for (int i = 0; i < tallas.length; i++) {
                final int index = i; // Variable final para usar en las lambdas

                // Columna Padre: Talla X
                TableColumn<FilaComparacion, String> colTalla = new TableColumn<>("Talla " + tallas[i]);

                // Sub-columna 1: Tiempo
                TableColumn<FilaComparacion, String> colTiempo = new TableColumn<>("Tiempo (ms)");
                colTiempo.setCellValueFactory(cell -> {
                    Double t = cell.getValue().getTiempoAt(index);
                    return new SimpleStringProperty(t != null ? String.format("%.4f", t) : "-");
                });
                colTiempo.setMinWidth(90);

                // Sub-columna 2: Calculadas
                TableColumn<FilaComparacion, String> colCalculadas = new TableColumn<>("Calculadas");
                colCalculadas.setCellValueFactory(cell -> {
                    Long c = cell.getValue().getCalculadasAt(index);
                    return new SimpleStringProperty(c != null ? String.valueOf(c) : "-");
                });
                colCalculadas.setMinWidth(90);

                // Añadir sub-columnas a la columna padre
                colTalla.getColumns().addAll(colTiempo, colCalculadas);

                // Añadir columna padre a la tabla
                tabla.getColumns().add(colTalla);
            }
        }

        // Crear los datos de las filas
        ObservableList<FilaComparacion> datos = FXCollections.observableArrayList();
        if (res1 != null)
            datos.add(new FilaComparacion(nombreEst1, res1));
        if (res2 != null)
            datos.add(new FilaComparacion(nombreEst2, res2));

        tabla.setItems(datos);

        // Botón Volver
        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, titulo, tabla, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 1200, 800));
    }

    // --- CLASE AUXILIAR PARA ESTADÍSTICAS ---
    public static class FilaEstadistica {
        private final String estrategia;
        private final int[] victorias;
        private final double[] tiempos;

        public FilaEstadistica(String estrategia, int[] victorias, double[] tiempos) {
            this.estrategia = estrategia;
            this.victorias = victorias;
            this.tiempos = tiempos;
        }

        public String getEstrategia() {
            return estrategia;
        }

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

    /**
     * Muestra la tabla con los resultados empíricos de la comparación.
     * * @param stage Ventana actual.
     * @param tallas Array con las tallas ejecutadas (ej: {500, 1000, ...}).
     * @param winsUni Array con el conteo de victorias de Unidireccional por talla.
     * @param timeUni Array con el tiempo medio de Unidireccional por talla.
     * @param winsBi Array con el conteo de victorias de Bidireccional por talla.
     * @param timeBi Array con el tiempo medio de Bidireccional por talla.
     */
    public void mostrarComparacionUniVsBi(Stage stage, int[] tallas, 
                                          int[] winsUni, double[] timeUni, 
                                          int[] winsBi, double[] timeBi) {

        Label titulo = new Label("Comparación Empírica: Unidireccional vs Bidireccional");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<FilaEstadistica> tabla = new TableView<>();

        // 1. Columna Fija: Estrategia
        TableColumn<FilaEstadistica, String> colEstrategia = new TableColumn<>("Estrategia");
        colEstrategia.setCellValueFactory(new PropertyValueFactory<>("estrategia"));
        colEstrategia.setMinWidth(200);
        tabla.getColumns().add(colEstrategia);

        // 2. Columnas Dinámicas por Talla (Anidadas)
        if (tallas != null) {
            for (int i = 0; i < tallas.length; i++) {
                final int index = i; // Variable final necesaria para la lambda
                
                // Columna Padre: Talla X
                TableColumn<FilaEstadistica, String> colTalla = new TableColumn<>("Talla " + tallas[i]);

                // Sub-columna 1: Victorias
                TableColumn<FilaEstadistica, String> colVictorias = new TableColumn<>("Victorias");
                colVictorias.setCellValueFactory(cell -> 
                    new SimpleStringProperty(String.valueOf(cell.getValue().getVictoriasAt(index))));
                colVictorias.setMinWidth(80);
                colVictorias.setStyle("-fx-alignment: CENTER;"); // Centrar texto

                // Sub-columna 2: Tiempo Medio
                TableColumn<FilaEstadistica, String> colTiempo = new TableColumn<>("Tiempo (ms)");
                colTiempo.setCellValueFactory(cell -> 
                    new SimpleStringProperty(String.format("%.4f", cell.getValue().getTiempoAt(index))));
                colTiempo.setMinWidth(100);
                colTiempo.setStyle("-fx-alignment: CENTER-RIGHT;"); // Alinear a la derecha

                // Añadir sub-columnas a la columna padre
                colTalla.getColumns().addAll(colVictorias, colTiempo);
                
                // Añadir columna padre a la tabla
                tabla.getColumns().add(colTalla);
            }
        }

        // 3. Crear datos y asignarlos a la tabla
        ObservableList<FilaEstadistica> datos = FXCollections.observableArrayList(
            new FilaEstadistica("Unidireccional", winsUni, timeUni),
            new FilaEstadistica("Bidireccional", winsBi, timeBi)
        );

        tabla.setItems(datos);

        // 4. Botón Volver y Layout
        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, titulo, tabla, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Ajustar tamaño de escena según contenido
        stage.setScene(new Scene(layout, 1200, 800));
    }
    /**
     * Muestra un menú para seleccionar dos algoritmos a comparar y las tallas de
     * ejecución.
     */
    public void menuSeleccionCompararDos(Stage stage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Label title = new Label("Configurar Comparación de 2 Estrategias");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Opciones disponibles
        ObservableList<String> opciones = FXCollections.observableArrayList(
                "Voraz Exhaustivo Unidireccional",
                "Voraz Exhaustivo Bidireccional",
                "Voraz Poda Unidireccional",
                "Voraz Poda Bidireccional");

        // Selectores
        Label lblEst1 = new Label("Estrategia 1:");
        ComboBox<String> combo1 = new ComboBox<>(opciones);
        combo1.getSelectionModel().select(0); // Seleccionar primero por defecto

        Label lblEst2 = new Label("Estrategia 2:");
        ComboBox<String> combo2 = new ComboBox<>(opciones);
        combo2.getSelectionModel().select(1); // Seleccionar segundo por defecto

        // Botón de Acción
        Button btnEjecutar = new Button("Ejecutar Comparación");
        btnEjecutar.setStyle("-fx-font-size: 14px; -fx-base: #b6e7c9;");

        Label lblStatus = new Label(""); // Para mostrar mensajes de carga

        btnEjecutar.setOnAction(e -> {
            String sel1 = combo1.getValue();
            String sel2 = combo2.getValue();

            if (sel1 == null || sel2 == null) {
                lblStatus.setText("Por favor selecciona ambas estrategias.");
                return;
            }

            lblStatus.setText("Calculando... Por favor espere.");

            // Usamos Platform.runLater para que la UI se actualice antes de bloquearse con
            // el cálculo
            Platform.runLater(() -> {
                compararDos(stage, sel1, sel2);
            });
        });

        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> stage.setScene(crearMenu(stage)));

        layout.getChildren().addAll(title, lblEst1, combo1, lblEst2, combo2, btnEjecutar, lblStatus, btnVolver);
        stage.setScene(new Scene(layout, 600, 400));
    }

    /**
     * Menú para configurar la comparación empírica (Victorias) entre Uni y Bi.
     */
    public void menuSeleccionUniVsBi(Stage stage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Label title = new Label("Comparación Empírica: Unidireccional vs Bidireccional");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // 1. Selector de Tipo de Algoritmo (Exhaustivo o Poda)
        Label lblTipo = new Label("Seleccione la variante a comparar:");
        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll("Voraz Exhaustivo", "Voraz con Poda");
        comboTipo.getSelectionModel().select(1); // Poda por defecto (es más rápido)

        // 2. Input para número de repeticiones
        Label lblRepeticiones = new Label("Número de repeticiones por talla:");
        javafx.scene.control.TextField txtRepeticiones = new javafx.scene.control.TextField("100");
        txtRepeticiones.setMaxWidth(100);

        // 3. Botón de Ejecución
        Button btnEjecutar = new Button("Iniciar Simulación");
        btnEjecutar.setStyle("-fx-base: #b6e7c9; -fx-font-weight: bold;");
        
        Label lblStatus = new Label(""); // Feedback visual

        btnEjecutar.setOnAction(e -> {
            String tipo = comboTipo.getValue();
            String repStr = txtRepeticiones.getText();

            if (tipo == null || repStr.isEmpty()) {
                lblStatus.setText("Datos incompletos.");
                return;
            }

            try {
                int repeticiones = Integer.parseInt(repStr);
                lblStatus.setText("Simulando... Esto puede tardar.");

                // Usamos Platform.runLater para permitir que la UI se renderice antes de bloquear
                Platform.runLater(() -> {
                    compararUniVsBi(stage, tipo, repeticiones);
                });

            } catch (NumberFormatException ex) {
                lblStatus.setText("El número de repeticiones debe ser un entero.");
            }
        });

        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> stage.setScene(crearMenu(stage)));

        layout.getChildren().addAll(title, lblTipo, comboTipo, lblRepeticiones, txtRepeticiones, btnEjecutar, lblStatus, btnVolver);
        stage.setScene(new Scene(layout, 600, 500));
    }
    // --- CLASE AUXILIAR PARA COMPARACIÓN DE 2 ESTRATEGIAS ---
    // Esta clase almacena arrays de tiempos y calculadas para poder acceder por índice de talla
    public static class FilaComparacion {
        private final String estrategia;
        private final double[] tiempos;
        private final long[] calculadas;

        public FilaComparacion(String estrategia, Camino[] resultados) {
            this.estrategia = estrategia;
            int n = (resultados != null) ? resultados.length : 0;
            this.tiempos = new double[n];
            this.calculadas = new long[n];

            if (resultados != null) {
                for (int i = 0; i < n; i++) {
                    if (resultados[i] != null) {
                        this.tiempos[i] = resultados[i].getTiempo();
                        this.calculadas[i] = resultados[i].getCalculadas();
                    } else {
                        // Valores centinela en caso de error o nulo
                        this.tiempos[i] = 0.0;
                        this.calculadas[i] = 0;
                    }
                }
            }
        }

        public String getEstrategia() { return estrategia; }

        public Double getTiempoAt(int index) {
            if (tiempos != null && index >= 0 && index < tiempos.length) {
                return tiempos[index];
            }
            return null;
        }

        public Long getCalculadasAt(int index) {
            if (calculadas != null && index >= 0 && index < calculadas.length) {
                return calculadas[index];
            }
            return null;
        }
    }
    /**
     * Menú para configurar y lanzar la visualización gráfica de un algoritmo.
     */
    public void menuSeleccionGrafica(Stage stage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Label title = new Label("Visualización de Rutas (Gráfica)");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 1. Selector de Algoritmo
        Label lblAlg = new Label("Selecciona el algoritmo a visualizar:");
        ComboBox<String> comboAlg = new ComboBox<>();
        comboAlg.getItems().addAll(
            "Voraz Exhaustivo Unidireccional",
            "Voraz Exhaustivo Bidireccional",
            "Voraz Poda Unidireccional",
            "Voraz Poda Bidireccional"
        );
        comboAlg.getSelectionModel().select(0);

        // 2. Selector de Archivo (Opcional, por defecto berlin52)
        Label lblFile = new Label("Dataset: berlin52.tsp (Por defecto)");
        Button btnCargar = new Button("Cargar otro archivo .tsp");
        // Variable para guardar el archivo seleccionado (usamos un array de 1 elemento para poder modificarlo dentro de la lambda)
        final File[] archivoSeleccionado = { new File("berlin52.tsp") };
        
        btnCargar.setOnAction(e -> {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Abrir archivo TSP");
            fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("TSP Files", "*.tsp"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                archivoSeleccionado[0] = file;
                lblFile.setText("Dataset: " + file.getName());
            }
        });

        // 3. Botón para dibujar
        Button btnDibujar = new Button("Generar Gráfica");
        btnDibujar.setStyle("-fx-base: #b6e7c9; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label lblStatus = new Label("");

        btnDibujar.setOnAction(e -> {
            String algName = comboAlg.getValue();
            File file = archivoSeleccionado[0];

            if (!file.exists()) {
                lblStatus.setText("Error: No se encuentra el archivo " + file.getName());
                return;
            }

            try {
                // Leer puntos
                Lector lector = new Lector(file);
                ArrayList<Punto> puntos = lector.LeePuntos();
                
                // Ejecutar algoritmo seleccionado
                Camino resultado = null;
                switch (algName) {
                    case "Voraz Exhaustivo Unidireccional":
                        resultado = Algoritmos.vorazExhaustivoUnidireccional(puntos);
                        break;
                    case "Voraz Exhaustivo Bidireccional":
                        resultado = Algoritmos.vorazExhaustivoBidireccional(puntos);
                        break;
                    case "Voraz Poda Unidireccional":
                        resultado = Algoritmos.vorazPodaUnidireccional(puntos);
                        break;
                    case "Voraz Poda Bidireccional":
                        resultado = Algoritmos.vorazPodaBidireccional(puntos);
                        break;
                }

                if (resultado != null) {
                    // Llamar a tu método existente crearGrafica
                    crearGrafica(puntos, resultado, stage);
                    
                    // IMPORTANTE: Como crearGrafica reemplaza la escena y no tiene botón "Volver",
                    // sugerencia: Modifica crearGrafica para añadir un botón o usa la lógica de abajo
                    // para inyectar un botón de vuelta en la gráfica si es posible.
                    agregarBotonVolverAGrafica(stage); 
                }

            } catch (Exception ex) {
                lblStatus.setText("Error al procesar: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        Button btnVolver = new Button("Volver al Menú Principal");
        btnVolver.setOnAction(e -> stage.setScene(crearMenu(stage)));

        layout.getChildren().addAll(title, lblAlg, comboAlg, lblFile, btnCargar, btnDibujar, lblStatus, btnVolver);
        stage.setScene(new Scene(layout, 600, 500));
    }

    // Método auxiliar para añadir un botón "Volver" sobre la gráfica
    // Llama a esto justo después de llamar a crearGrafica()
    private void agregarBotonVolverAGrafica(Stage stage) {
        Scene sceneGrafica = stage.getScene();
        if (sceneGrafica != null && sceneGrafica.getRoot() instanceof javafx.scene.Parent) {
            // Intentamos envolver el contenido actual (Chart) en un VBox con el botón
            javafx.scene.Node chartNode = sceneGrafica.getRoot();
            
            Button btnVolver = new Button("Volver a Selección");
            btnVolver.setOnAction(e -> menuSeleccionGrafica(stage));
            
            VBox newRoot = new VBox(10, chartNode, btnVolver);
            newRoot.setAlignment(Pos.CENTER);
            newRoot.setPadding(new Insets(10));
            
            stage.setScene(new Scene(newRoot, 1200, 850));
        }
    }
}
