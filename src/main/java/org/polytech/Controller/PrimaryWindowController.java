package org.polytech.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.polytech.Model.Cube;
import java.util.ArrayList;

public class PrimaryWindowController {
    private Cube cube = new Cube(3);
    private ArrayList<Color> colors;
    ArrayList<GridPane> gridPanesList;

    @FXML
    private GridPane frontPane;
    @FXML
    private GridPane leftPane;
    @FXML
    private GridPane rightPane;
    @FXML
    private GridPane upPane;
    @FXML
    private GridPane downPane;
    @FXML
    private GridPane backPane;

    @FXML
    private Label commandsLabel;

    @FXML
    private void initialize() {
        //cube.showTheStatus();

        colors = new ArrayList<>();
        colors.add(Color.rgb(0, 255, 21));
        colors.add(Color.rgb(255, 166, 0));
        colors.add(Color.rgb(0, 128, 255));
        colors.add(Color.rgb(255, 21, 0));
        colors.add(Color.rgb(246, 255, 0));
        colors.add(Color.rgb(255, 255, 255));

        gridPanesList = new ArrayList<>();
        gridPanesList.add(frontPane);
        gridPanesList.add(rightPane);
        gridPanesList.add(backPane);
        gridPanesList.add(leftPane);
        gridPanesList.add(upPane);
        gridPanesList.add(downPane);

        //((Rectangle)gridPanesList.get(3).getChildren().get(8)).setFill(colors.get(0));


        gridPanesList.forEach(pane ->
            pane.getChildren().forEach(item -> item.setOnMouseClicked(mouseEvent -> {
                if (item instanceof Rectangle) {
                    /* Получаем координаты выбранной ячейки (или границы)*/
                    Node source = (Node) mouseEvent.getSource();
                    Integer cI = GridPane.getColumnIndex(source);
                    Integer rI = GridPane.getRowIndex(source);
                    int colI = cI != null ? cI : 0;
                    int rowI = rI != null ? rI : 0;
                    Pair<Integer, Integer> coords = new Pair<>(rowI, colI);

                    Color fill = (Color)((Rectangle)item).getFill();
                    int colorIndex = (colors.indexOf(fill) == 5) ? 0 : colors.indexOf(fill) + 1;

                    ((Rectangle)item).setFill(colors.get(colorIndex));
                    cube.setSquare(gridPanesList.indexOf(pane), coords.getKey(), coords.getValue(), colorIndex);
                }
                else
                    commandsLabel.setText("не ректангл");
        })));
    }

    @FXML
    private void handleFrontClockwise() {
        turn(0, 2, 0);
    }

    @FXML
    private void handleFrontCounterclockwise() {
        turn(0, 2, 1);
    }

    @FXML
    private void handleRightClockwise() {
        turn(1, 2, 0);
    }

    @FXML
    private void handleRightCounterclockwise() {
        turn(1, 2, 1);
    }

    @FXML
    private void handleBackClockwise() {
        turn(0, 0, 1);
    }

    @FXML
    private void handleBackCounterclockwise() {
        turn(0, 0, 0);
    }

    @FXML
    private void handleLeftClockwise() {
        turn(1, 0, 1);
    }

    @FXML
    private void handleLeftCounterclockwise() {
        turn(1, 0, 0);
    }

    @FXML
    private void handleUpClockwise() {
        turn(2, 2, 0);
    }

    @FXML
    private void handleUpCounterclockwise() {
        turn(2, 2, 1);
    }

    @FXML
    private void handleDownClockwise() {
        turn(2, 0, 1);
    }

    @FXML
    private void handleDownCounterclockwise() {
        turn(2, 0, 0);
    }

    @FXML
    private void handleShuffle() {
        cube.setRandomState();
        executeTurns();
    }

    @FXML
    private void handleReset() {
        cube.reset();
        executeTurns();
    }

    private void turn(int axis, int layer, int direction) {
        cube.turnSides(axis, layer, direction);
        executeTurns();
    }

    private void executeTurns() {
        ArrayList<ArrayList<Color>> paneColors = arrayOfNeededColors();

        ObservableList<Node> paneCells;
        for (int i = 0; i < 6; i++) {
            paneCells = gridPanesList.get(i).getChildren();
            for (int j = 0; j < 9; j++) {
                ((Rectangle)paneCells.get(j)).setFill(paneColors.get(i).get(j));
            }
        }
    }

    private ArrayList<ArrayList<Color>> arrayOfNeededColors() {
        ArrayList<ArrayList<Color>> paneColors = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            int[][] currentSide = cube.getTheSide(i);
            paneColors.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    paneColors.get(i).add(colors.get(currentSide[j][k]));
                    System.out.print(currentSide[j][k] + " ");
                }
                System.out.println('\n');
            }
        }
        return paneColors;
    }

}
