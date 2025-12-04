package com.pract2amc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainApp extends Application {
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage=s;
        stage.setScene(crearMenu(stage));
        stage.setTitle("Análisis de Algoritmos");
        stage.show();
    }

    /*static void setRoot(String fxml) throws IOException {
        setRoot(fxml,stage.getTitle());
    }

    static void setRoot(String fxml, String title) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }*/


    public static void main(String[] args) {
        launch(args);
    }

     private Scene crearMenu(Stage stage) {
        //File myObj = new File("berlin52.tsp");

        //Lector prueba = new Lector(myObj);
        //ArrayList<Punto> puntos = prueba.LeePuntos();

        Button comparar4Est = new Button("Comparar todas las estrategias (.tsp aleatorio)");
        Button comparar4EstPeor = new Button("Comparar todas las estrategias en caso peor(.tsp aleatorio)");
        Button comparar2Est = new Button("Comparar dos estrategias (.tsp aleatorio)");
        Button comparar2EstPeor = new Button("Comparar dos estrategias en caso peor(.tsp aleatorio)");
        Button comprobarEstrategias = new Button("Comprobar todas las estrategias (dataset cargado)");
        Button estudiarEstrategia = new Button("Estudiar una estrategia (dataset cargado)");
        Button btnSalir = new Button("Salir");

        // Acciones al hacer clic
        comparar4Est.setOnAction(e -> prueba_grafica(stage));
        /*comparar4EstPeor.setOnAction(e -> comparar4(stage,true));
        comparar2Est.setOnAction(e -> compararDos(stage,false));
        comparar2EstPeor.setOnAction(e -> compararDos(stage,true));
        comprobarEstrategias.setOnAction(e -> compararEstrategias(stage, puntos));
        estudiarEstrategia.setOnAction(e -> stage.setScene(estudiarEstrategia(stage, puntos)));*/

        btnSalir.setOnAction(e -> stage.close());

        // Organizar botones en un layout vertical
        VBox menu = new VBox(15, comparar4Est,comparar4EstPeor, comparar2Est,comparar2EstPeor, comprobarEstrategias, estudiarEstrategia, btnSalir);
        menu.setAlignment(Pos.CENTER);

        return new Scene(menu, 1200, 800);
    }

    public void prueba_grafica(Stage stage){
        Lector lector = new Lector(new File("berlin52.tsp"));
        ArrayList<Punto> puntos = lector.LeePuntos();
        int [] camino= {0,1,2,3,4,5};
        ArrayList<Double> distancias= new ArrayList<>();
        Camino prueba= new Camino(1,camino, distancias);
        crearGrafica(puntos, prueba, stage);
    }
    /**
     /* * Crea una gráfica que muestra todos los puntos y resalta el par de puntos más cercanos.
     * 
     * @param puntosDataset Lista de todos los puntos a mostrar
     * @param solucion Par de puntos más cercanos a resaltar
     * @param stage Ventana donde se mostrará la gráfica
     
    public void crearGrafica(ArrayList<Punto> puntosDataset, ParPuntos solucion, Stage stage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setCreateSymbols(true); // mostrar puntos

        XYChart.Series<Number, Number> puntos = new XYChart.Series<>();
        for (Punto punto : puntosDataset) {
            puntos.getData().add(new XYChart.Data<>(punto.getX(), punto.getY()));
        }
        chart.getData().add(puntos);
        // Serie que será la línea entre los dos puntos más cercanos

        Punto p1 = solucion.getP1();
        Punto p2 = solucion.getP2();

        XYChart.Series<Number, Number> linea = new XYChart.Series<>();
        linea.getData().add(new XYChart.Data<>(p1.getX(), p1.getY()));
        linea.getData().add(new XYChart.Data<>(p2.getX(), p2.getY()));
        chart.getData().add(linea);

        Scene scene = new Scene(chart, 1200, 800);
        stage.setScene(scene);

        // Estilo: ocultar la línea de la serie de puntos y colorear la serie de la
        // línea
        Platform.runLater(() -> {
            // primera serie = puntos -> ocultar trazo (solo símbolos)
            chart.lookupAll(".series0.chart-series-line").forEach(n -> n.setStyle("-fx-stroke: transparent;"));
            // segunda serie = línea -> hacerla roja y gruesa
            chart.lookupAll(".series1.chart-series-line")
                    .forEach(n -> n.setStyle("-fx-stroke: red; -fx-stroke-width: 2;"));
        });

    }
 */
/**
     * Crea una gráfica que muestra todos los puntos y dibuja el camino (ruta) completo.
     * * @param puntosDataset Lista de todos los puntos disponibles (referencia para los índices).
     * @param solucion Objeto Camino que contiene los índices de la ruta a trazar.
     * @param stage Ventana donde se mostrará la gráfica.
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

        // Estilo CSS mediante Platform.runLater para asegurar que los nodos ya existen en el Scene Graph
        Platform.runLater(() -> {
            // Serie 0 (Nodos): Ocultar líneas, mostrar solo símbolos (puntos dispersos)
            chart.lookupAll(".series0.chart-series-line").forEach(n -> n.setStyle("-fx-stroke: transparent;"));
            
            // Serie 1 (Camino): Línea roja y gruesa para resaltar la ruta. 
            // Opcional: Ocultar símbolos de la serie 1 si se superponen demasiado, o dejarlos.
            chart.lookupAll(".series1.chart-series-line")
                    .forEach(n -> n.setStyle("-fx-stroke: red; -fx-stroke-width: 2;"));
        });
    }
}
