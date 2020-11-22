package org.polytech.Model;
import java.util.Random;

public class Cube {
    private static class Side {
        int[][] squares;
        int size;
        Side(int size) {
            squares = new int[size][size];
            this.size = size;
        }

        void solveTheSide(int color) {
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    squares[i][j] = color;
        }

        void setTheSide(int[][] side) {
            for (int i = 0; i < size; i++)
                System.arraycopy(side[i], 0, this.squares[i], 0, size);
        }

        void showTheSide() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(squares[i][j] + " ");
                }
                System.out.print('\n');
            }
        }

        int[][] get() {
            return squares;
        }

        void setSquare(int i, int j, int value) {
            squares[i][j] = value;
        }
    }

    private Side[] sides;
    private int size;
    public Cube(int size) {
        if (size < 1) throw new IllegalArgumentException("Dimension must be > 1");
        sides = new Side[6];
        for (int i = 0; i < 6; i++) {
            sides[i] = new Side(size);
            sides[i].solveTheSide(i); // initialize the solved cube
        }
        this.size = size;
    }

    public void showTheStatus() {
        for (int i = 0; i < 6; i++) {
            System.out.println("Side " + i);
            sides[i].showTheSide();
            System.out.print("\n");
        }
    }

    public void setSquare(int sideNumber, int i, int j, int value) {
        sides[sideNumber].setSquare(i, j, value);
    }

    public int[][] getTheSide(int numSide) {
        if (numSide < 0 || numSide > 5) throw new IllegalArgumentException("The wrong number of side");
        return this.sides[numSide].get();
    }

    void setTheSide(int[][] side, int numSide) {
        if (numSide < 0 || numSide > 5) throw new IllegalArgumentException("The wrong side");
        for (int[] row : side) if (side.length != row.length) throw new IllegalArgumentException("Not a cube");
        for (int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if (side[i][j] < 0 || side[i][j] > 5) throw new IllegalArgumentException("The wrong color");
        this.sides[numSide].setTheSide(side);
    }

    void solveTheSide(int color, int numSide) {
        if (numSide < 0 || numSide > 5 || color < 0 || color > 5) throw new IllegalArgumentException("The wrong side/color");
        this.sides[numSide].solveTheSide(color);
    }

    public void reset() {
        for (int i = 0; i < 6; i++) {
            solveTheSide(i, i);
        }
    }

    private void swapTheFaces(int[] faces) {
        Side temp = sides[faces[0]];
        for (int i = 0; i < 3; i++)
            sides[faces[i]] = sides[faces[i+1]];
        sides[faces[3]] = temp;
    }

    private void turnTheFaces(int[] faces, boolean[] directions) {
        for (int i = 0; i < faces.length; i++)
            turnTheFace(faces[i], directions[i]);
    }

    // direction: 0-Up, 1-Down, 2-Left, 3-Right, 4-clockwise, 5-counterclockwise
    void turnTheCube(int direction) {
        switch (direction) {
            case 0:
                swapTheFaces(new int[] {0, 5, 2, 4});
                turnTheFaces(new int[] {5, 5, 2, 2, 1, 3}, new boolean[] {false, false, false, false, false, true});
                break;
            case 1:
                swapTheFaces(new int[] {0, 4, 2, 5});
                turnTheFaces(new int[] {4, 4, 2, 2, 1, 3}, new boolean[] {false, false, false, false, true, false});
                break;
            case 2:
                swapTheFaces(new int[] {0, 1, 2, 3});
                turnTheFaces(new int[] {4, 5}, new boolean[] {false, true});
                break;
            case 3:
                swapTheFaces(new int[] {3, 2, 1, 0});
                turnTheFaces(new int[] {4, 5}, new boolean[] {true, false});
                break;
            case 4:
                swapTheFaces(new int[] {1, 4, 3, 5});
                turnTheFaces(new int[] {1, 4, 3, 5, 0, 2}, new boolean[] {false, false, false, false, false, true});
                break;
            case 5:
                swapTheFaces(new int[] {1, 5, 3, 4});
                turnTheFaces(new int[] {1, 5, 3, 4, 0, 2}, new boolean[] {true, true, true, true, true, false});
                break;
        }
    }

    //direction: 0 - clockwise, 1 - counterclockwise
    private void turnTheFace(int side, boolean direction) {
        int tmp;
        if (!direction) {
            for (int i = 0; i < size / 2; i++)
                for (int j = i; j < size - 1 - i; j++) {
                    tmp = this.sides[side].squares[i][j];
                    this.sides[side].squares[i][j] = this.sides[side].squares[size - j - 1][i];
                    this.sides[side].squares[size - j - 1][i] = this.sides[side].squares[size - i - 1][size - j - 1];
                    this.sides[side].squares[size - i - 1][size - j - 1] = this.sides[side].squares[j][size - i - 1];
                    this.sides[side].squares[j][size - i - 1] = tmp;
                }
        }
        else {
            for (int i = 0; i < size / 2; i++)
                for (int j = i; j < size - 1 - i; j++) {
                    tmp = this.sides[side].squares[i][j];
                    this.sides[side].squares[i][j] = this.sides[side].squares[j][size-1-i];
                    this.sides[side].squares[j][size-1-i] = this.sides[side].squares[size-1-i][size-1-j];
                    this.sides[side].squares[size-1-i][size-1-j] = this.sides[side].squares[size-1-j][i];
                    this.sides[side].squares[size-1-j][i] = tmp;
                }
        }
    }

    private void turnFrontLayerToRight(int layer) {
        int[] temp = new int[size];
        for (int i = 0; i < size; i++)
            temp[i] = sides[3].squares[layer][i]; // создали temp слоя грани 3

        for (int i = 3; i > 0; i--)
            for (int j = 0; j < size; j++)
                sides[i].squares[layer][j] = sides[i - 1].squares[layer][j];

        for (int i = 0; i < size; i++)
            sides[0].squares[layer][i] = temp[i];

        if (layer == 0) turnTheFace(4, true);
        if (layer == size - 1) turnTheFace(5, false);
    }

    private void turnFrontLayerToLeft(int layer) {
        int[] temp = new int[size];
        for (int i = 0; i < size; i++)
            temp[i] = sides[0].squares[layer][i]; // создали temp слоя грани 0

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < size; j++)
                sides[i].squares[layer][j] = sides[i + 1].squares[layer][j];

        for (int i = 0; i < size; i++)
            sides[3].squares[layer][i] = temp[i];

        if (layer == 0) turnTheFace(4, false);
        if (layer == size - 1) turnTheFace(5, true);
    }

    // axis: y-0, x-1, z-2 (origin - back side, the bottom left square; y - front, to you; x - right; z - up)
    // layer: 0 - (size-1), count from origin
    // direction: 0 - clockwise, 1 - counterclockwise; (view against the axis)
    private void turnTheSide(int axis, int layer, int direction) {
        if (layer < 0 || layer >= size || direction < 0 || direction > 1)
            throw new IllegalArgumentException("The wrong option");
        if (axis == 2) {
            if (direction == 1) turnFrontLayerToRight(size - layer - 1);
            else turnFrontLayerToLeft(size - layer - 1);
        }
        else if (axis == 1)  {
            turnTheCube(4); // Clockwise
            if (direction == 0) turnFrontLayerToRight(layer);
            else turnFrontLayerToLeft(layer);
            turnTheCube(5); // CounterClockwise
        }
        else if (axis == 0) {
            turnTheCube(1); // Down
            if (direction == 0) turnFrontLayerToRight(layer);
            else turnFrontLayerToLeft(layer);
            turnTheCube(0); // Up
        }
    }

    // for several rotations at a time
    void turnSides(int axis, int[] layers, int[] directions) {
        if ((layers.length != directions.length) || layers.length == 0)
            throw new IllegalArgumentException("Dimensions must be equal and > 0");
        for (int i = 0; i < layers.length; i++) turnTheSide(axis, layers[i], directions[i]);
    }

    //for one rotation
    public void turnSides(int axis, int layer, int direction) {
        turnTheSide(axis, layer, direction);
    }

    public void setRandomState() {
        int numTurns = 20;
        for (int i = 0; i < numTurns; i++) {
            int layer = new Random().nextInt(size);
            while (layer == 1)
                layer = new Random().nextInt(size);

            turnSides(new Random().nextInt(3), layer, new Random().nextInt(2));

        }
    }
}