package com.pract2amc;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
        /*comparar4Est.setOnAction(e -> comparar4(stage,false));
        comparar4EstPeor.setOnAction(e -> comparar4(stage,true));
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

}
