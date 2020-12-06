package org.polytech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.polytech.Controller.PrimaryWindowController;
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

        showMainWindow();
    }

    public void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/PrimaryWindow.fxml"));
            farmField = loader.load();
            PrimaryWindowController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(farmField);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHelp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/HelpWindow.fxml"));
            AnchorPane storage = loader.load();
            Stage helpStage = new Stage();
            helpStage.setTitle("Справка");
            helpStage.initModality(Modality.WINDOW_MODAL);
            helpStage.initOwner(primaryStage);
            Scene scene = new Scene(storage);
            helpStage.setScene(scene);

            helpStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
