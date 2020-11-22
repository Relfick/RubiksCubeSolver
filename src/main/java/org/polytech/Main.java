package org.polytech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.polytech.Model.Cube;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private AnchorPane farmField;
    //private Cube cube = new Cube(3);

    public Main() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Кубик Ребика");

        showFarmFields();
    }

    public void showFarmFields() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/PrimaryWindow.fxml"));
            farmField = loader.load();

            Scene scene = new Scene(farmField);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
