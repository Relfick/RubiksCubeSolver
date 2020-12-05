package org.polytech.Utility;

public class Utility {
    public static char getDirOfSide(int sideIndex) {
        switch (sideIndex) {
            case 0: return 'F';
            case 1: return 'R';
            case 2: return 'B';
            case 3: return 'L';
            case 4: return 'U';
        }
        return 'D';
    }

    public static char getColorOfInt(int col) {
        switch(col) {
            case 0: return 'G';
            case 1: return 'O';
            case 2: return 'B';
            case 3: return 'R';
            case 4: return 'Y';
        }
        return 'W';
    }

    public static int getColorOfChar(char col) {
        switch(col) {
            case 'G': return 0;
            case 'O': return 1;
            case 'B': return 2;
            case 'R': return 3;
            case 'Y': return 4;
        }
        return 5;
    }
}
