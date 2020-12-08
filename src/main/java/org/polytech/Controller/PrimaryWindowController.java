package org.polytech.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.polytech.Main;
import org.polytech.Model.Cube;

import java.util.ArrayDeque;
import java.util.ArrayList;

import org.polytech.Utility.Utility;

public class PrimaryWindowController {
    private Main mainApp;

    private final Cube cube = new Cube();
    private ArrayList<Color> colors;
    ArrayList<GridPane> gridPanesList;
    // команды для выполнения
    StringBuilder commandsSb;
    // выполненные команды
    ArrayDeque<String> executedComms;
    // установка состояния (true) или выполнение последовательности команд для решения (false)
    private boolean settingMode;

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
    private TextArea commandsLabel;

    @FXML
    private Group buttonsLeftGroup;

    @FXML
    private Group buttonsRightGroup;

    @FXML
    private void initialize() {

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

        cube.reset();
        executeTurns();

        settingMode = true;

        gridPanesList.forEach(pane ->
            pane.getChildren().forEach(item -> item.setOnMouseClicked(mouseEvent -> {
                if (item instanceof Rectangle && settingMode) {
                    /* Получаем координаты выбранной ячейки (или границы)*/
                    Node source = (Node) mouseEvent.getSource();
                    Integer cI = GridPane.getColumnIndex(source);
                    Integer rI = GridPane.getRowIndex(source);
                    int colI = cI != null ? cI : 0;
                    int rowI = rI != null ? rI : 0;
                    Pair<Integer, Integer> coords = new Pair<>(rowI, colI);

                    Color fill = (Color)((Rectangle)item).getFill();
                    int colorIndex = (colors.indexOf(fill) == 5) ? 0 : colors.indexOf(fill) + 1;

                    //((Rectangle)item).setFill(colors.get(colorIndex));

                    int cubieX;
                    int cubieY;
                    int cubieZ;

                    switch (gridPanesList.indexOf(pane)) {
                        case 0:
                            cubieY = 0;
                            cubieZ = coords.getKey();
                            cubieX = coords.getValue();
                            break;
                        case 1:
                            cubieY = coords.getValue();
                            cubieZ = coords.getKey();
                            cubieX = 2;
                            break;
                        case 2:
                            cubieY = 2;
                            cubieZ = coords.getKey();
                            cubieX = 2 - coords.getValue();
                            break;
                        case 3:
                            cubieY = 2 - coords.getValue();
                            cubieZ = coords.getKey();
                            cubieX = 0;
                            break;
                        case 4:
                            cubieY = 2 - coords.getKey();
                            cubieZ = 0;
                            cubieX = coords.getValue();
                            break;
                        default:
                            cubieY = coords.getKey();
                            cubieZ = 2;
                            cubieX = coords.getValue();
                    }

                    cube.setCubieColor(cubieX, cubieY, cubieZ,
                            Utility.getDirOfSide(gridPanesList.indexOf(pane)), Utility.getColorOfInt(colorIndex));

                    executeTurns();

                    System.out.println(cubieX + " " + cubieY + " " + cubieZ + " "
                            + Utility.getDirOfSide(gridPanesList.indexOf(pane)) + " " + Utility.getColorOfInt(colorIndex));
                }
        })));
    }

    @FXML
    private void handleFrontClockwise() {
        turn("F");
    }

    @FXML
    private void handleFrontCounterclockwise() {
        turn("F'");
    }

    @FXML
    private void handleRightClockwise() {
        turn("R");
    }

    @FXML
    private void handleRightCounterclockwise() {
        turn("R'");
    }

    @FXML
    private void handleBackClockwise() {
        turn("B");
    }

    @FXML
    private void handleBackCounterclockwise() {
        turn("B'");
    }

    @FXML
    private void handleLeftClockwise() {
        turn("L");
    }

    @FXML
    private void handleLeftCounterclockwise() {
        turn("L'");
    }

    @FXML
    private void handleUpClockwise() {
        turn("U");
    }

    @FXML
    private void handleUpCounterclockwise() {
        turn("U'");
    }

    @FXML
    private void handleDownClockwise() {
        turn("D");
    }

    @FXML
    private void handleDownCounterclockwise() {
        turn("D'");
    }

    @FXML
    private String handleShuffle() {
        cube.reset();
        String scramble = cube.randScramble();
        executeTurns();
        return scramble;
    }

    @FXML
    private void handleReset() {
        cube.reset();
        executeTurns();
        commandsLabel.clear();
    }

    @FXML
    private void handleSolve() {
        if (cube.solved()) {
            commandsLabel.setText("Already solved");
            return;
        }

        char[][][] state = cube.getTheState();
        commandsSb = new StringBuilder(cube.solve());
        executedComms = new ArrayDeque<>();

        if (commandsSb.toString().equals("failed")) {
            commandsLabel.setText("IMPOSSIBLE TO SOLVE");
        } else {
            commandsLabel.setText(commandsSb.toString());
            settingMode = false;
            buttonsLeftGroup.setDisable(true);
            buttonsRightGroup.setDisable(false);
        }

        cube.setTheState(state);
        executeTurns();
    }

    @FXML
    private void handleNext() {
        if (commandsSb == null || commandsSb.length() == 0) return;
        int endCommandIndex = commandsSb.indexOf(" ");
        cube.performMoves(commandsSb.substring(0, endCommandIndex));

        executeTurns();
        System.out.println(commandsSb.substring(0, endCommandIndex));

        executedComms.add(commandsSb.substring(0, endCommandIndex));
        commandsSb.delete(0, endCommandIndex+1);
        commandsLabel.setText(commandsSb.toString());
    }

    @FXML
    private void handlePrevious() {
        if (executedComms.size() == 0) return;
        String currCom = executedComms.removeLast();

        cube.performReverseMoves(currCom);
        executeTurns();

        commandsSb.insert(0, currCom + " ");
        commandsLabel.setText(commandsSb.toString());

        int endCommandIndex = commandsSb.indexOf(" ");
        System.out.println(commandsSb.substring(0, endCommandIndex));
    }

    @FXML
    private void handleNew() {
        cube.reset();
        executeTurns();
        settingMode = true;
        buttonsLeftGroup.setDisable(false);
        buttonsRightGroup.setDisable(true);
        commandsLabel.clear();
    }

    @FXML
    private void handleHelp() {
         mainApp.showHelp();
    }

    public void setMainApp(Main MainApp) {
        this.mainApp = MainApp;
    }

    private void turn(String turn) {
        cube.turn(turn);
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
        char[][][] charPaneColors = cube.getTheState();
        ArrayList<ArrayList<Color>> paneColors = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            paneColors.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    paneColors.get(i).add(colors.get(Utility.getColorOfChar(charPaneColors[i][j][k])));
                    //System.out.print(Utility.getColorOfChar(charPaneColors[i][j][k]) + " ");
                }
                //System.out.println('\n');
            }
        }
        return paneColors;
    }

    /**
     * Для тестирования
     */
    private void printStateInConsole() {
        char[][][] cols = cube.getTheState();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    System.out.print(cols[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
