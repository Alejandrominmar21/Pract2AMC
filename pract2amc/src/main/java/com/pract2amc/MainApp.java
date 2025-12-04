package com.pract2amc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
        Camino prueba= new Camino(1,camino, distancias , 0);
        crearGrafica(puntos, prueba, stage);
    }
    
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
    /**
     * Clase auxiliar para representar una fila en la tabla.
     * Puedes ponerla dentro de MainApp o como clase externa.
     */
    public static class FilaResultados {
        private final String estrategia;
        private final double solucion;
        private final double tiempo;
        private final int talla; // Opcional, por si hay varias tallas

        public FilaResultados(String estrategia, double solucion, double tiempo, int talla) {
            this.estrategia = estrategia;
            this.solucion = solucion;
            this.tiempo = tiempo;
            this.talla = talla;
        }

        public String getEstrategia() { return estrategia; }
        public double getSolucion() { return solucion; }
        public double getTiempo() { return tiempo; }
        public int getTalla() { return talla; }
    }

   
    private void compararTodasLasStrategias(Stage stage, int[] Tallas,//TODO REVISSAR
                                Camino[] exhaustivo, Camino[] poda, 
                                Camino[] dyv, Camino[] dyvMejorado) {

        Label titulo = new Label("Comparativa de Estrategias");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<FilaResultados> tabla = new TableView<>();

        // Columna 1: Estrategia
        TableColumn<FilaResultados, String> colEstrategia = new TableColumn<>("Estrategia");
        colEstrategia.setCellValueFactory(new PropertyValueFactory<>("estrategia"));
        // Ancho sugerido
        colEstrategia.setMinWidth(200);

        // Columna 2: Solución Calculada (Distancia)
        TableColumn<FilaResultados, String> colSolucion = new TableColumn<>("Solución Calculada");
        colSolucion.setCellValueFactory(cell -> 
            new SimpleStringProperty(String.format("%.4f", cell.getValue().getSolucion())));

        // Columna 3: Tiempo (ms)
        TableColumn<FilaResultados, String> colTiempo = new TableColumn<>("Tiempo (ms)");
        colTiempo.setCellValueFactory(cell -> 
            new SimpleStringProperty(String.format("%.6f", cell.getValue().getTiempo())));
            
     
        TableColumn<FilaResultados, Integer> colTalla = new TableColumn<>("Talla");
        colTalla.setCellValueFactory(new PropertyValueFactory<>("talla"));

        tabla.getColumns().addAll(colEstrategia, colSolucion, colTiempo, colTalla);

        // Rellenar datos
        // Recorremos los arrays y creamos una fila por cada estrategia y talla
        if (Tallas != null) {
            for (int i = 0; i < Tallas.length; i++) {
                int t = Tallas[i];
                // Asegúrate de que los arrays no sean nulos y tengan datos en la posición i
                if (exhaustivo != null && i < exhaustivo.length) 
                    tabla.getItems().add(new FilaResultados("Exhaustivo", exhaustivo[i].getDistanciaFinal(), exhaustivo[i].getTiempo(), t));
                
                if (poda != null && i < poda.length) 
                    tabla.getItems().add(new FilaResultados("Exhaustivo con Poda", poda[i].getDistanciaFinal(), poda[i].getTiempo(), t));
                
                if (dyv != null && i < dyv.length) 
                    tabla.getItems().add(new FilaResultados("Divide y Vencerás", dyv[i].getDistanciaFinal(), dyv[i].getTiempo(), t));
                
                if (dyvMejorado != null && i < dyvMejorado.length) 
                    tabla.getItems().add(new FilaResultados("DyV Mejorado", dyvMejorado[i].getDistanciaFinal(), dyvMejorado[i].getTiempo(), t));
            }
        }

        Button volverBtn = new Button("Volver al menú");
        volverBtn.setOnAction(e -> stage.setScene(crearMenu(stage)));

        VBox layout = new VBox(15, titulo, tabla, volverBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 1200, 800));
    }
}
