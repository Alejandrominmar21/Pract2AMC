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
        Button estudiarEstrategia = new Button("Unidireccional vs Bidireccional (.tsp aleatorio)");
        Button btnSalir = new Button("Salir");

        // Acciones al hacer clic

        comparar4Est.setOnAction(e -> comparar4(stage));
         //comparar2Est.setOnAction(e -> compararDos(stage,false));
        comprobarEstrategias.setOnAction(e -> compararEstrategias(stage));
          /* estudiarEstrategia.setOnAction(e -> stage.setScene(estudiarEstrategia(stage,
         * puntos)));
         */

        btnSalir.setOnAction(e -> stage.close());

        // Organizar botones en un layout vertical
        VBox menu = new VBox(15, comparar4Est, comparar2Est, comprobarEstrategias, estudiarEstrategia, btnSalir);
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

}
