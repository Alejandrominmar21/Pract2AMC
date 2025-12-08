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

    /*
     * static void setRoot(String fxml) throws IOException {
     * setRoot(fxml,stage.getTitle());
     * }
     * 
     * static void setRoot(String fxml, String title) throws IOException {
     * Scene scene = new Scene(loadFXML(fxml));
     * stage.setTitle(title);
     * stage.setScene(scene);
     * stage.show();
     * }
     * 
     * private static Parent loadFXML(String fxml) throws IOException {
     * FXMLLoader fxmlLoader = new
     * FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));
     * return fxmlLoader.load();
     * }
     */

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
        comparar2Est.setOnAction(e -> compararDos(stage));
        comprobarEstrategias.setOnAction(e -> compararEstrategias(stage));
        uniVsBi.setOnAction(e ->compararUniVsBi(stage));
       // uniVsBi.setOnAction(e -> stage.setScene(compararUniVsBi(stage)));

        btnSalir.setOnAction(e -> stage.close());

        // Organizar botones en un layout vertical
        VBox menu = new VBox(15, comparar4Est, comparar2Est, comprobarEstrategias, uniVsBi, btnSalir);
        menu.setAlignment(Pos.CENTER);

        return new Scene(menu, 1200, 800);
    }

    public void comparar4(Stage stage) {
        /*
         * Lector lector = new Lector(new File("pract2amc/datasets/berlin52.tsp"));
         * ArrayList<Punto> puntos = lector.LeePuntos();
         * int [] camino= {0,1,2,3,4,5};
         * ArrayList<Double> distancias= new ArrayList<>();
         * Camino prueba= new Camino(1,camino, distancias , 0);
         * crearGrafica(puntos, prueba, stage);
         */

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

    public void compararDos(Stage stage) {//TODO Cambiar para que funcione como en la 1
       int[] Tallas = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };
        Camino ResultadosVorazExhaustivoUnidireccional[] = new Camino[Tallas.length];
        Camino ResultadosVorazExhaustivoBidireccional[] = new Camino[Tallas.length];
        
        for (int i = 0; i < Tallas.length; i++) {
            GeneradorTSP.crearArchivoTSP(Tallas[i], false);
            File myObj = new File("dataset" + Tallas[i] + ".tsp");
            Lector prueba = new Lector(myObj);
            ArrayList<Punto> puntosDataset = prueba.LeePuntos();

            ResultadosVorazExhaustivoUnidireccional[i] = Algoritmos.vorazExhaustivoUnidireccional(puntosDataset);
            ResultadosVorazExhaustivoBidireccional[i] = Algoritmos.vorazExhaustivoBidireccional(puntosDataset);


        }
        compararDosEstrategias(stage, Tallas,"dada",ResultadosVorazExhaustivoUnidireccional,"dasd", ResultadosVorazExhaustivoBidireccional);

    }
     public void compararUniVsBi(Stage stage) {//TODO Cambiar para que funcione como en la 1
       int[] Tallas = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };
        Camino ResultadosVorazExhaustivoUnidireccional[] = new Camino[Tallas.length];
        Camino ResultadosVorazExhaustivoBidireccional[] = new Camino[Tallas.length];
        
        for (int i = 0; i < Tallas.length; i++) {
            GeneradorTSP.crearArchivoTSP(Tallas[i], false);
            File myObj = new File("dataset" + Tallas[i] + ".tsp");
            Lector prueba = new Lector(myObj);
            ArrayList<Punto> puntosDataset = prueba.LeePuntos();

            ResultadosVorazExhaustivoUnidireccional[i] = Algoritmos.vorazExhaustivoUnidireccional(puntosDataset);
            ResultadosVorazExhaustivoBidireccional[i] = Algoritmos.vorazExhaustivoBidireccional(puntosDataset);


        }
        mostrarComparacionUniVsBi(stage, Tallas,ResultadosVorazExhaustivoUnidireccional, ResultadosVorazExhaustivoBidireccional);

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

    // --- MÉTODO PARA VISUALIZAR LA COMPARACIÓN DE 2 ESTRATEGIAS ---
    /**
     * Muestra una tabla comparativa para dos estrategias específicas a través de múltiples tallas.
     * Crea columnas anidadas (Tiempo y Calculadas) para cada Talla.
     *
     * @param stage Ventana actual.
     * @param tallas Array de tallas ejecutadas (ej: {100, 200, 500}).
     * @param nombreEst1 Nombre de la primera estrategia.
     * @param res1 Resultados (Caminos) de la primera estrategia.
     * @param nombreEst2 Nombre de la segunda estrategia.
     * @param res2 Resultados (Caminos) de la segunda estrategia.
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
        if (res1 != null) datos.add(new FilaComparacion(nombreEst1, res1));
        if (res2 != null) datos.add(new FilaComparacion(nombreEst2, res2));

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

        public String getEstrategia() { return estrategia; }

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
     * Muestra la tabla comparativa calculando victorias y tiempos a partir de los objetos Camino.
     * * @param stage Ventana actual.
     * @param tallas Array con las tallas evaluadas.
     * @param uni Array de objetos Camino con los resultados de la estrategia Unidireccional.
     * @param bi Array de objetos Camino con los resultados de la estrategia Bidireccional.
     */
    public void mostrarComparacionUniVsBi(Stage stage, int[] tallas, 
                                          Camino[] uni, Camino[] bi) {

        Label titulo = new Label("Comparación: Unidireccional vs Bidireccional");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<FilaEstadistica> tabla = new TableView<>();

        // 1. Preparar los datos a partir de los arrays de Camino
        int n = (tallas != null) ? tallas.length : 0;
        
        int[] winsUni = new int[n];
        double[] timeUni = new double[n];
        
        int[] winsBi = new int[n];
        double[] timeBi = new double[n];

        // Procesar lógica de comparación (quién gana)
        if (tallas != null && uni != null && bi != null) {
            for (int i = 0; i < n; i++) {
                // Verificar que existan datos para esa posición
                if (i < uni.length && uni[i] != null && i < bi.length && bi[i] != null) {
                    
                    // Extraer tiempos
                    timeUni[i] = uni[i].getTiempo();
                    timeBi[i] = bi[i].getTiempo();

                    // Comparar distancias para asignar victoria (con margen de error para doubles)
                    double distUni = uni[i].getDistanciaFinal();
                    double distBi = bi[i].getDistanciaFinal();
                    double epsilon = 0.000001;

                    if (distUni < distBi - epsilon) {
                        winsUni[i] = 1; // Gana Uni
                        winsBi[i] = 0;
                    } else if (distBi < distUni - epsilon) {
                        winsUni[i] = 0;
                        winsBi[i] = 1; // Gana Bi
                    } else {
                        // Empate (opcional: ambos 0 o ambos 1, aquí ponemos 0 a victorias estrictas)
                        winsUni[i] = 0;
                        winsBi[i] = 0;
                    }
                }
            }
        }

        // 2. Definir Columnas de la Tabla
        // Columna Fija: Estrategia
        TableColumn<FilaEstadistica, String> colEstrategia = new TableColumn<>("Estrategia");
        colEstrategia.setCellValueFactory(new PropertyValueFactory<>("estrategia"));
        colEstrategia.setMinWidth(200);
        tabla.getColumns().add(colEstrategia);

        // Columnas Dinámicas por Talla
        if (tallas != null) {
            for (int i = 0; i < tallas.length; i++) {
                final int index = i; 
                TableColumn<FilaEstadistica, String> colTalla = new TableColumn<>("Talla " + tallas[i]);

                // Sub-columna: Victorias (1 o 0)
                TableColumn<FilaEstadistica, String> colVictorias = new TableColumn<>("Gana");
                colVictorias.setCellValueFactory(cell -> 
                    new SimpleStringProperty(String.valueOf(cell.getValue().getVictoriasAt(index))));
                colVictorias.setMinWidth(60);
                colVictorias.setStyle("-fx-alignment: CENTER;");

                // Sub-columna: Tiempo
                TableColumn<FilaEstadistica, String> colTiempo = new TableColumn<>("Tiempo (ms)");
                colTiempo.setCellValueFactory(cell -> 
                    new SimpleStringProperty(String.format("%.4f", cell.getValue().getTiempoAt(index))));
                colTiempo.setMinWidth(100);
                colTiempo.setStyle("-fx-alignment: CENTER-RIGHT;");

                colTalla.getColumns().addAll(colVictorias, colTiempo);
                tabla.getColumns().add(colTalla);
            }
        }

        // 3. Crear las filas y asignar a la tabla
        ObservableList<FilaEstadistica> datos = FXCollections.observableArrayList(
            new FilaEstadistica("Unidireccional", winsUni, timeUni),
            new FilaEstadistica("Bidireccional", winsBi, timeBi)
        );

        tabla.setItems(datos);

        // Botón Volver
        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, titulo, tabla, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 1200, 800));
    }

}
