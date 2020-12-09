package org.polytech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.polytech.Controller.PrimaryWindowController;

import java.io.IOException;

/**
 * Программа - решатель кубика рубика
 * Вращение каждой из 6 граней кубика обозначено отдельной буквой:
 * F (Front) - передняя
 * B (Back) - задняя
 * R (Right) - правая
 * L (Left) - левая
 * U (Up) - верхняя
 * D (Down) - нижняя
 * Каждое из этих действий вращает указанную грань по часовой
 * стрелке. Если у буквы есть апостроф ( например, U' ), это значит, что
 * вращение происходит против часовой стрелки.
 *
 * Также используется обозначение y, при котором весь кубик поворачивается
 * так, что правая грань становится передней, передняя левой, левая задней, а
 * задняя правой. Верхняя и нижняя местами не меняются. y' - то же, но в
 * противоположную сторону
 */
public class Main extends Application {
    private Stage primaryStage;

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
            System.out.println(getClass().getResource(""));
            AnchorPane primaryWindow = loader.load();
            PrimaryWindowController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(primaryWindow);
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
