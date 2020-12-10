package org.polytech.Model;

public class Cubie {
    private int x;
    private int y;
    private int z;
    private boolean corner;
    private boolean edge;
    private CubieColor[] colors;

    public Cubie (int xPos, int yPos, int zPos, CubieColor[] nColors, boolean isCorner,
                  boolean isEdge) {
        x = xPos;
        y = yPos;
        z = zPos;
        corner = isCorner;
        edge = isEdge;
        colors = nColors;
    }

    /**
     * Возвращает направление цвета куба
     */
    public char getDirOfColor(char color) {
        for (CubieColor cubieColor : colors) {
            if (cubieColor.getColor() == color)
                return cubieColor.getDir();
        }
        return 'A';
    }

    /**
     * Возвращает цвет в данном направлении
     */
    public char getColorOfDir(char dir) {
        for (CubieColor color : colors) {
            if (color.getDir() == dir)
                return color.getColor();
        }
        return 'A';
    }

    public CubieColor[] getColors() {
        return colors;
    }

    public void setColors(CubieColor[] newColors) {
        colors = newColors;
    }

    /**
     * Устанавливает цвет на данном направлении
     */
    public void setColorOfDir(char dir, char ncolor) {
        for (CubieColor color : colors) {
            if (color.getDir() == dir)
                color.setColor(ncolor);
        }
    }

    public boolean isCornerCubie() {
        return corner;
    }

    public boolean isEdgeCubie() {
        return edge;
    }

    /**
     * Для каждой грани не из второго слоя возвращает вертикальный слой, в котором этот кубик находится
     */
    public char verticalFace(int x, int y) {
        if(edge) {
            if(x == 0) return 'L';
            else if(x == 1) {
                if(y == 0) {
                    return 'F';
                }
                else return 'B';
            }
            else return 'R';
        }
        return 'A';

    }

    public boolean isWhiteCorner() {
        if(corner) {
            return (colors[0].getColor()=='W'|| colors[1].getColor()=='W' || colors[2].getColor()=='W');
        }
        return false;
    }

}
