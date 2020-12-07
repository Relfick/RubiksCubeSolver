package org.polytech.Model;

public class CubieColor {
    private char color;
    private char dir; // Направление цвета по сторонам

    public CubieColor(char ncolor, char ndir) {
        color = ncolor;
        dir = ndir;
    }

    public char getColor() {
        return color;
    }

    public char getDir() {
        return dir;
    }

    public void setDir(char ndir) {
        dir = ndir;
    }

    public void setColor(char ncolor) {
        color = ncolor;
    }

}
