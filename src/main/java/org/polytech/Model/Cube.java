package org.polytech.Model;
import org.polytech.Utility.Utility;

public class Cube {

    // Хранит состояние куба в виде 27 маленьких кубиков
    private final Cubie[][][] cubiePos = new Cubie[3][3][3];
    // Для ограничение итераций makeSunFlower
    private int itersMSF = 0;

    /**
     * Стандартное положение куба: зеленый спереди, красный слева.
     * Координата x растет вправо, y - от нас, z - вниз.
     */
    public Cube() {
        cubiePos[0][0][0] = new Cubie(0,0,0,
                new CubieColor[] { new CubieColor('Y','U'), new CubieColor('R','L'),
                        new CubieColor('G','F')}, true, false);
        cubiePos[1][0][0] = new Cubie(1,0,0,
                new CubieColor[] { new CubieColor('Y','U'), new CubieColor('G','F')}, false, true);
        cubiePos[2][0][0] = new Cubie(2,0,0,
                new CubieColor[] { new CubieColor('Y','U'), new CubieColor('G','F'),
                        new CubieColor('O','R')}, true, false);

        cubiePos[0][0][1] = new Cubie(0,0,1,
                new CubieColor[] { new CubieColor('R','L'), new CubieColor('G','F')}, false, true);
        cubiePos[1][0][1] = new Cubie(1,0,1,
                new CubieColor[] { new CubieColor('G','F')}, false, false);
        cubiePos[2][0][1] = new Cubie(2,0,1,
                new CubieColor[] { new CubieColor('G','F'), new CubieColor('O','R')}, false, true);

        cubiePos[0][0][2] = new Cubie(0,0,2,
                new CubieColor[] { new CubieColor('W','D'), new CubieColor('R','L'),
                        new CubieColor('G','F')}, true, false);
        cubiePos[1][0][2] = new Cubie(1,0,2,
                new CubieColor[] { new CubieColor('W','D'), new CubieColor('G','F')}, false, true);
        cubiePos[2][0][2] = new Cubie(2,0,2,
                new CubieColor[] { new CubieColor('W','D'), new CubieColor('G','F'),
                        new CubieColor('O','R')}, true, false);

        cubiePos[0][1][0] = new Cubie(0,1,0,
                new CubieColor[] { new CubieColor('R','L'), new CubieColor('Y','U')}, false, true);
        cubiePos[1][1][0] = new Cubie(1,1,0,
                new CubieColor[] { new CubieColor('Y','U')}, false, false);
        cubiePos[2][1][0] = new Cubie(2,1,0,
                new CubieColor[] { new CubieColor('Y','U'), new CubieColor('O','R')}, false, true);

        cubiePos[0][1][1] = new Cubie(0,1,1,
                new CubieColor[] { new CubieColor('R','L')}, false, false);
        cubiePos[1][1][1] = new Cubie(1,1,1,
                new CubieColor[] { new CubieColor('A','A')}, // Цвета в центре не имеют значения
                false, false);
        cubiePos[2][1][1] = new Cubie(2,1,1,
                new CubieColor[] { new CubieColor('O','R')}, false, false);

        cubiePos[0][1][2] = new Cubie(0,1,2,
                new CubieColor[] { new CubieColor('R','L'), new CubieColor('W','D')}, false, true);
        cubiePos[1][1][2] = new Cubie(1,1,2,
                new CubieColor[] { new CubieColor('W','D')}, false, false);
        cubiePos[2][1][2] = new Cubie(2,1,2,
                new CubieColor[] { new CubieColor('W','D'), new CubieColor('O','R')}, false, true);

        cubiePos[0][2][0] = new Cubie(0,2,0,
                new CubieColor[] { new CubieColor('Y','U'), new CubieColor('R','L'),
                        new CubieColor('B','B')}, true, false);
        cubiePos[1][2][0] = new Cubie(1,2,0,
                new CubieColor[] { new CubieColor('Y','U'), new CubieColor('B','B')}, false, true);
        cubiePos[2][2][0] = new Cubie(2,2,0,
                new CubieColor[] { new CubieColor('Y','U'), new CubieColor('B','B'),
                        new CubieColor('O','R')}, true, false);

        cubiePos[0][2][1] = new Cubie(0,2,1,
                new CubieColor[] { new CubieColor('R','L'), new CubieColor('B','B')}, false, true);
        cubiePos[1][2][1] = new Cubie(1,2,1,
                new CubieColor[] { new CubieColor('B','B')}, false, false);
        cubiePos[2][2][1] = new Cubie(2,2,1,
                new CubieColor[] { new CubieColor('B','B'), new CubieColor('O','R')}, false, true);

        cubiePos[0][2][2] = new Cubie(0,2,2,
                new CubieColor[] { new CubieColor('W','D') , new CubieColor('R','L'),
                        new CubieColor('B','B')}, true, false);
        cubiePos[1][2][2] = new Cubie(1,2,2,
                new CubieColor[] { new CubieColor('W','D') , new CubieColor('B','B')}, false, true);
        cubiePos[2][2][2] = new Cubie(2,2,2,
                new CubieColor[] { new CubieColor('W','D') , new CubieColor('B','B'),
                        new CubieColor('O','R')}, true, false);
    }

    /**
     * turn - команда поворота, первая буква вращающейся стороны (U - up, D - down, ...)
     * команда y означает поворот всего кубика по оси z так, что передняя сторона становится на место левой, правая на
     * место передней и тд.
     */
    public void turn (String turn) {
        char[] preChange;
        char[] postChange;
        Cubie[][] side = new Cubie[3][3];

        switch(turn) {
            case "B":
                preChange = new char[] {'B', 'U', 'R', 'D', 'L'};
                postChange = new char[] {'B', 'L', 'U', 'R', 'D'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[Math.abs(j-2)][2][i];
                    }
                }
                side = rotateSide(side, true, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[Math.abs(j-2)][2][i] = side[i][j];
                    }
                }
                break;

            case "B'":
                preChange = new char[] {'B', 'U', 'R', 'D', 'L'};
                postChange = new char[] {'B', 'R', 'D', 'L', 'U'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[Math.abs(j-2)][2][i];
                    }
                }
                side = rotateSide(side, false, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[Math.abs(j-2)][2][i] = side[i][j];
                    }
                }
                break;

            case "D" :
                preChange = new char[] {'D', 'L', 'B', 'R', 'F'};
                postChange = new char[] {'D', 'F', 'L', 'B', 'R'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[j][i][2];
                    }
                }
                side = rotateSide(side, true, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[j][i][2] = side[i][j];
                    }
                }
                break;

            case "D'" :
                preChange = new char[] {'D', 'F', 'L', 'B', 'R'};
                postChange = new char[] {'D', 'L', 'B', 'R', 'F'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[j][i][2];
                    }
                }
                side = rotateSide(side, false, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[j][i][2] = side[i][j];
                    }
                }
                break;

            case "F":
                preChange = new char[] {'F', 'U', 'R', 'D', 'L'};
                postChange = new char[] {'F', 'R', 'D', 'L', 'U'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[j][0][i];
                    }
                }
                side = rotateSide(side, true, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[j][0][i] = side[i][j];
                    }
                }
                break;

            case "F'":
                preChange = new char[] {'F', 'U', 'R', 'D', 'L'};
                postChange = new char[] {'F', 'L', 'U', 'R', 'D'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[j][0][i];
                    }
                }
                side = rotateSide(side, false, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[j][0][i] = side[i][j];
                    }
                }
                break;

            case "L":
                preChange = new char[] {'L', 'B', 'D', 'F', 'U'};
                postChange = new char[] {'L', 'U', 'B', 'D', 'F'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[0][Math.abs(j-2)][i];
                    }
                }
                side = rotateSide(side, true, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[0][Math.abs(j-2)][i] = side[i][j];
                    }
                }
                break;

            case "L'":
                preChange = new char[] {'L', 'U', 'B', 'D', 'F'};
                postChange = new char[] {'L', 'B', 'D', 'F', 'U'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[0][Math.abs(j-2)][i];
                    }
                }
                side = rotateSide(side, false, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[0][Math.abs(j-2)][i] = side[i][j];
                    }
                }
                break;

            case "R":
                preChange = new char[] {'R', 'U', 'B', 'D', 'F'};
                postChange = new char[] {'R', 'B', 'D', 'F', 'U'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[2][j][i];
                    }
                }
                side = rotateSide(side, true, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[2][j][i] = side[i][j];
                    }
                }
                break;

            case "R'":
                preChange = new char[] {'R', 'B', 'D', 'F', 'U'};
                postChange = new char[] {'R', 'U', 'B', 'D', 'F'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[2][j][i];
                    }
                }
                side = rotateSide(side, false, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[2][j][i] = side[i][j];
                    }
                }
                break;

            case "U" :
                preChange = new char[] {'U', 'F', 'L', 'B', 'R'};
                postChange = new char[] {'U', 'L', 'B', 'R', 'F'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[j][Math.abs(i-2)][0];
                    }
                }
                side = rotateSide(side, true, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[j][Math.abs(i-2)][0] = side[i][j];
                    }
                }
                break;

            case "U'" :
                preChange = new char[] {'U', 'L', 'B', 'R', 'F'};
                postChange = new char[] {'U', 'F', 'L', 'B', 'R'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[j][Math.abs(i-2)][0];
                    }
                }
                side = rotateSide(side, false, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[j][Math.abs(i-2)][0] = side[i][j];
                    }
                }
                break;

            case "E" :
                preChange = new char[] {'L', 'B', 'R', 'F'};
                postChange = new char[] {'F', 'L', 'B', 'R'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[j][i][1];
                    }
                }
                side = rotateSide(side, true, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[j][i][1] = side[i][j];
                    }
                }
                break;

            case "E'" :
                preChange = new char[] {'F', 'L', 'B', 'R'};
                postChange = new char[] {'L', 'B', 'R', 'F'};
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        side[i][j] = cubiePos[j][i][1];
                    }
                }
                side = rotateSide(side, false, preChange, postChange);

                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        cubiePos[j][i][1] = side[i][j];
                    }
                }
                break;

            case "y":
                performMoves("U E' D'");
                break;

            case "y'":
                performMoves("U' E D");
                break;
        }
    }

    /**
     * Поворачивает сторону по часовой стрелке или против.
     */
    private Cubie[][] rotateSide(Cubie[][] orig, boolean clockwise, char[] preChange,
                                 char[] postChange) {
        Cubie[][] rotated = new Cubie[3][3];
        if(clockwise) {
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<3; j++) {
                    rotated[i][j] = orig[j][i];
                }
            }
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<rotated[0].length/2; j++) {
                    Cubie tempCubie = rotated[i][3-j-1];
                    rotated[i][3-j-1] = rotated[i][j];
                    rotated[i][j] = tempCubie;
                }
            }
        }
        else {
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<3; j++) {
                    rotated[i][j] = orig[j][i];
                }
            }
            for(int i = 0; i<rotated[0].length/2; i++) {
                for(int j = 0; j<3; j++) {
                    Cubie tempCubie = rotated[3-i-1][j];
                    rotated[3-i-1][j] = rotated[i][j];
                    rotated[i][j] = tempCubie;
                }
            }
        }
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                CubieColor[] tempColors = rotated[i][j].getColors();
                for (CubieColor tempColor : tempColors) {
                    int index = 6;
                    for (int x = 0; x < preChange.length; x++) {
                        if (tempColor.getDir() == preChange[x]) {
                            index = x;
                        }
                    }
                    if (index < postChange.length)
                        tempColor.setDir(postChange[index]);
                }
                rotated[i][j].setColors(tempColors);
            }
        }
        return rotated;
    }

    /**
     * Выполняет последовательность поворотов.
     */
    public String performMoves(String moves) {
        for(int i = 0; i<moves.length(); i++) {
            if(moves.charAt(i) != ' ') {
                if(i != moves.length()-1) {
                    if(moves.substring(i+1, i+2).compareTo("2") == 0) {
                        turn(moves.substring(i, i+1));
                        turn(moves.substring(i, i+1));
                        i++;
                    }
                    else if(moves.substring(i+1,i+2).compareTo("'") == 0) {
                        turn(moves.substring(i, i+2));
                        i++;
                    }
                    else {
                        turn(moves.substring(i, i+1));
                    }
                }
                else {
                    turn(moves.substring(i, i+1));
                }
            }
        }
        return moves;
    }

    /**
     * Выполняет поледовательность инвертированных поворотов (U -> U'; U' -> U)
     */
    public void performReverseMoves(String moves) {
        for(int i = 0; i < moves.length(); i++) {
            if(moves.charAt(i) != ' ') {
                if(i != moves.length() - 1) {
                    if(moves.substring(i+1, i+2).compareTo("2") == 0) {
                        turn(moves.substring(i, i+1));
                        turn(moves.substring(i, i+1));
                        i++;
                    }
                    else if(moves.substring(i+1,i+2).compareTo("'") == 0) {
                        turn(moves.substring(i, i+1));
                        i++;
                    }
                    else {
                        turn(moves.charAt(i) + "'");
                    }
                }
                else {
                    turn(moves.charAt(i) + "'");
                }
            }
        }
    }

    /**
     * Сокращает последовательность поворотов, удаляя бессмысленные команды или заменяя их более короткими
     * напр. "U U2" -> "U'"
     */
    public String optimizeMoves(String moves) {
        if (moves.equals("failed"))
            return moves;
        for(int i = 0; i<moves.length(); i++) {
            String move = moves.substring(i, i+1);
            if(!move.equals(" ") && !move.equals("'") && !move.equals("2")) {
                if(i <= moves.length()-3) {
                    if(moves.substring(i+1, i+2).compareTo("2") == 0) {
                        if(i <= moves.length()-4 && moves.charAt(i+3) == moves.charAt(i)) {
                            if(i <= moves.length()-5) {
                                if(moves.substring(i+4, i+5).compareTo("2") == 0) {
                                    //"U2 U2"
                                    moves = moves.substring(0, i) + moves.substring(i+5);
                                    i--;
                                } else if(moves.substring(i+4, i+5).compareTo("'") == 0) {
                                    //"U2 U'" -> "U"
                                    moves = moves.substring(0, i) + moves.charAt(i)
                                            + moves.substring(i+5);
                                    i--;
                                } else {
                                    //"U2 U" -> "U'"
                                    moves = moves.substring(0, i) + moves.charAt(i) + "'"
                                            + moves.substring(i+4);
                                    i--;
                                }
                            } else {
                                // "U2 U" -> "U'"
                                moves = moves.substring(0, i) + moves.charAt(i) + "'"
                                        + moves.substring(i+4);
                                i--;
                            }
                        }
                    }
                    else if(moves.substring(i+1,i+2).compareTo("'") == 0) { // Против часовой
                        if(i <= moves.length()-4 && moves.charAt(i+3) == moves.charAt(i)) {
                            if(i <= moves.length()-5) {
                                if(moves.substring(i+4, i+5).compareTo("2") == 0) {
                                    // "U' U2" -> "U"
                                    moves = moves.substring(0, i) + moves.charAt(i)
                                            + moves.substring(i+5);
                                    i--;
                                } else if(moves.substring(i+4, i+5).compareTo("'") == 0) {
                                    // "U' U'" -> "U2"
                                    moves = moves.substring(0, i) + moves.charAt(i) + "2"
                                            + moves.substring(i+5);
                                    i--;
                                } else {
                                    // "U' U"
                                    moves = moves.substring(0, i) + moves.substring(i+4);
                                    i--;
                                }
                            } else {
                                // "U' U"
                                moves = moves.substring(0, i) + moves.substring(i+4);
                                i--;
                            }
                        }
                    }
                    else { // По часовой
                        if(i <= moves.length()-3 && moves.charAt(i+2) == moves.charAt(i)) {
                            if(i <= moves.length()-4) {
                                if(moves.substring(i+3, i+4).compareTo("2") == 0) {
                                    // "U U2" -> "U' "
                                    moves = moves.substring(0, i) + moves.charAt(i) + "'"
                                            + moves.substring(i+4);
                                    i--;
                                } else if(moves.substring(i+3, i+4).compareTo("'") == 0) {
                                    //"U U'"
                                    moves = moves.substring(0, i) + moves.substring(i+4);
                                    i--;
                                } else {
                                    //"U U" -> "U2"
                                    moves = moves.substring(0, i) + moves.charAt(i) + "2"
                                            + moves.substring(i + 3);
                                    i--;
                                }
                            } else {
                                //"U U" -> "U2"
                                moves = moves.substring(0, i) + moves.charAt(i) + "2"
                                        + moves.substring(i + 3);
                                i--;
                            }
                        }

                    }
                }
            // Удаление возможных пробелов
            } else if (move.equals(" ") && i > 0 && i < moves.length()-1 && moves.charAt(i+1) == ' ') {
                moves = moves.substring(0, i) + moves.substring(i+1);
                i-=2;
            }
        }
        return moves;
    }

    /**
     * Генерация рандомного состояния кубика (решаемого)
     */
    public String randScramble() {
        StringBuilder scramble = new StringBuilder();
        char[] possMoves = new char[] {'U', 'D', 'R', 'L', 'F', 'B'}; // Разрешенные повороты
        char prevMove = possMoves[(int)(Math.random()*6)];
        char secondLastMove = possMoves[(int)(Math.random()*6)];

        for(int numMoves = 0; numMoves < 20; ) {
            char move = possMoves[(int)(Math.random()*6)];
            if(move != prevMove && move!= secondLastMove) {
                int rand = (int)(Math.random()*100);
                if(rand < 33) {
                    scramble.append(move).append("2 ");
                } else if(rand < 67) {
                    scramble.append(move).append("' ");
                } else {
                    scramble.append(move).append(" ");
                }
                secondLastMove = prevMove;
                prevMove = move;
                numMoves++;
            }
        }
        scramble(scramble.toString());

        return scramble.toString();
    }

    private void scramble(String scramble) {
        performMoves(scramble);
    }

    /**
     * Сборка белого креста
     */
    public String makeWhiteCross() {
        StringBuilder moves = new StringBuilder();
        int iterations = 0;
        while(numWhiteEdgesOriented() != 0) {
            if (iterations < 100) {
                iterations++;
            } else {
                return "failed";
            }
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][0].isEdgeCubie()) {
                        CubieColor[] tempColors = cubiePos[i][j][0].getColors();
                        if(tempColors[0].getColor() == 'W' || tempColors[1].getColor() == 'W') {
                            for(int k = 0; k<2; k++) {
                                if((tempColors[k].getColor() == 'R' && tempColors[k].getDir() == 'L') ||
                                        (tempColors[k].getColor() == 'G' && tempColors[k].getDir() == 'F') ||
                                        (tempColors[k].getColor() == 'O' && tempColors[k].getDir() == 'R')||
                                        (tempColors[k].getColor() == 'B' && tempColors[k].getDir() == 'B')) {
                                    moves.append(performMoves(cubiePos[i][j][0].verticalFace(i, j) + "2 "));
                                }
                            }
                        }
                    }
                }
            }
            moves.append(performMoves("U "));
        }
        return optimizeMoves(moves.toString());
    }

    /**
     * Сборка белого креста с желтым центром
     */
    public String makeSunflower() {
        StringBuilder moves = new StringBuilder();

        if(numWhiteEdgesOriented() < 5) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][2].isEdgeCubie()) {
                        if(cubiePos[i][j][2].getDirOfColor('W') == 'D') {
                            moves.append(prepareSlot(i, j, 0, 'W'));
                            char turnToMake = cubiePos[i][j][2].verticalFace(i, j);
                            moves.append(performMoves("" + turnToMake + "2 "));
                        }
                    }
                }
            }
        }

        if(numWhiteEdgesOriented() < 5) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][2].isEdgeCubie()) {
                        if(cubiePos[i][j][2].getDirOfColor('W') != 'A' && cubiePos[i][j][2].getDirOfColor('W') != 'D') {
                            char vert = cubiePos[i][j][2].verticalFace(i, j);
                            moves.append(prepareSlot(i, j, 0, 'W'));
                            if(vert == 'F') {
                                moves.append(performMoves("F' U' R "));
                            } else if(vert == 'R') {
                                moves.append(performMoves("R' U' B "));
                            } else if(vert == 'B') {
                                moves.append(performMoves("B' U' L "));
                            } else if(vert == 'L') {
                                moves.append(performMoves("L' U' F "));
                            }
                        }
                    }
                }

            }
        }

        if(numWhiteEdgesOriented() < 5) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][1].isEdgeCubie()) {
                        CubieColor[] tempColors = cubiePos[i][j][1].getColors();
                        for(int k = 0; k<2; k++) {
                            if(tempColors[k].getColor() == 'W') {
                                if(i == 0 && j == 0) {
                                    if(tempColors[k].getDir() == 'L') {
                                        moves.append(prepareSlot(1, 0, 0, 'W')).append(performMoves("F "));
                                    } else {
                                        moves.append(prepareSlot(0, 1, 0, 'W')).append(performMoves("L' "));
                                    }
                                }
                                else if(i == 2 && j == 0) {
                                    if(tempColors[k].getDir() == 'F') {
                                        moves.append(prepareSlot(2, 1, 0, 'W')).append(performMoves("R "));
                                    } else {
                                        moves.append(prepareSlot(1, 0, 0, 'W')).append(performMoves("F' "));
                                    }
                                }
                                else if(i == 2 && j == 2) {
                                    if(tempColors[k].getDir() == 'B') {
                                        moves.append(prepareSlot(2, 1, 0, 'W')).append(performMoves("R' "));
                                    } else {
                                        moves.append(prepareSlot(1, 2, 0, 'W')).append(performMoves("B "));
                                    }
                                }
                                else {
                                    if(tempColors[k].getDir() == 'B') {
                                        moves.append(prepareSlot(0, 1, 0, 'W')).append(performMoves("L "));
                                    } else {
                                        moves.append(prepareSlot(1, 2, 0, 'W')).append(performMoves("B' "));
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        if(numWhiteEdgesOriented() < 5) {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(cubiePos[i][j][0].isEdgeCubie()) {
                        if(cubiePos[i][j][0].getDirOfColor('W') != 'A' && cubiePos[i][j][0].getDirOfColor('W') != 'U') {
                            char vert = cubiePos[i][j][0].verticalFace(i, j);
                            if(vert == 'F') {
                                moves.append(performMoves("F U' R "));
                            } else if(vert == 'R') {
                                moves.append(performMoves("R U' B "));
                            } else if(vert == 'B') {
                                moves.append(performMoves("B U' L "));
                            } else if(vert == 'L') {
                                moves.append(performMoves("L U' F "));
                            }
                        }
                    }
                }

            }
        }

        if(numWhiteEdgesOriented() < 4) {
            if (itersMSF < 10) {
                itersMSF++;
                moves.append(makeSunflower());
            } else
                moves = new StringBuilder("failed");
        }
        itersMSF = 0;
        return optimizeMoves(moves.toString());
    }

    /**
     * Утилита для MSF, позволяет не рушить уже поставленные белые грани
     */
    public String prepareSlot(int x, int y, int z, char color) {
        int numUTurns = 0;
        CubieColor[] tempColor = cubiePos[x][y][z].getColors();
        while((tempColor[0].getColor() == color || tempColor[1].getColor() == color) && numUTurns < 5){
            performMoves("U");
            tempColor = cubiePos[x][y][z].getColors();
            numUTurns++;
        }

        if(numUTurns == 0 || numUTurns == 4) {
            return "";
        }
        else if(numUTurns == 1) {
            return "U ";
        }
        else if (numUTurns == 2) {
            return "U2 ";
        }
        else return "U' ";
    }

    /**
     * Утилита для MSF, возвращает кол-во белых граней в верхнем слое
     */
    public int numWhiteEdgesOriented() {
        int numOriented = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(cubiePos[i][j][0].isEdgeCubie()) {
                    if(cubiePos[i][j][0].getDirOfColor('W') == 'U') {
                        numOriented++;
                    }
                }
            }
        }
        return numOriented;
    }

    /**
     * Завершает сборку нижнего слоя
     */
    public String finishWhiteLayer() {
        StringBuilder moves = new StringBuilder();
        String additionalMoves1 = insertWhiteCorners();
        String additionalMoves2 = insertMisorientedCorners();
        if (additionalMoves1.equals("failed") || additionalMoves2.equals("failed"))
            return "failed";
        moves.append(additionalMoves1);
        moves.append(additionalMoves2);

        int iterations = 0;
        while(whiteCornerInU()) {
            if (iterations < 100) {
                iterations++;
            } else {
                return "failed";
            }

            additionalMoves1 = insertWhiteCorners();
            additionalMoves2 = insertMisorientedCorners();
            if (additionalMoves1.equals("failed") || additionalMoves2.equals("failed"))
                return "failed";
            moves.append(additionalMoves1);
            moves.append(additionalMoves2);
        }
        return optimizeMoves(moves.toString());
    }

    /**
     * Проверяет, есть ли белые углы в верхнем слое
     */
    public boolean whiteCornerInU() {
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(cubiePos[i][j][0].isCornerCubie()) {
                    if(cubiePos[i][j][0].getDirOfColor('W') != 'A')
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Вставляет белые углы на нижний слой
     */
    public String insertWhiteCorners() {
        int iterations2 = 0;
        StringBuilder moves = new StringBuilder();
        for(int y = 0; y<3; y++) {
            for(int x = 0; x<3; x++) {
                if(cubiePos[x][y][0].isCornerCubie() && cubiePos[x][y][0].isWhiteCorner()) {
                    if(x==0) {
                        if(y==0) {
                            moves.append(performMoves("U' "));
                        }
                        else {
                            moves.append(performMoves("U2 "));
                        }
                    }
                    else {
                        if(y==2) {
                            moves.append(performMoves("U "));
                        }
                    }
                    y=0; x=0;

                    int numUTurns = 0;
                    int numyRotations = 0;
                    int iterations = 0;
                    while(!whiteCornerPrepared()) {
                        if(iterations > 5)
                            return "failed";
                        iterations++;

                        performMoves("U y'");
                        numUTurns++;
                        numyRotations++;
                    }
                    uAndyTurns(moves, numUTurns, numyRotations);

                    int numRuruMoves = 0;
                    iterations = 0;
                    while(!cornerInserted(2, 0, 2)) {
                        if(iterations > 7)
                            return "failed";
                        iterations++;

                        performMoves("R U R' U'");
                        numRuruMoves++;
                    }
                    if(numRuruMoves == 5) {
                        moves.append("U R U' R' ");
                    }
                    else {
                        for(int k = 0; k<numRuruMoves; k++) {
                            moves.append("R U R' U' ");
                        }
                    }
                }
                if(iterations2 > 100)
                    return "failed";
                iterations2++;
            }
        }
        return moves.toString();
    }

    /**
     * Утилита для insertWhiteCorners() и insertEdgesInU(), добавляет в moves нужное число U и y поворотов
     */
    private void uAndyTurns(StringBuilder moves, int numUTurns, int yRotations) {
        if(numUTurns == 1) {
            moves.append("U ");
        } else if(numUTurns == 2) {
            moves.append("U2 ");
        } else if(numUTurns == 3) {
            moves.append("U' ");
        }
        if(yRotations == 1) {
            moves.append("y' ");
        } else if(yRotations == 2) {
            moves.append("y2 ");
        } else if(yRotations == 3) {
            moves.append("y ");
        }
    }

    /**
     *  Исправляет положение белых углов, которые уже в нижнем слое.
     */
    public String insertMisorientedCorners() {
        StringBuilder moves = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            moves.append(performMoves("y "));
            if(!cornerInserted(2,0,2)) {
                if(cubiePos[2][0][2].isWhiteCorner()) {
                    if(!cornerInserted(2,0,2)) {
                        moves.append(performMoves("R U R' U' "));
                        String currComm = insertWhiteCorners();
                        if (currComm.equals("failed"))
                            return "failed";
                        moves.append(currComm);
                        i = -1;
                    }
                }
            }
        }
        return moves.toString();
    }

    /**
     * Утилита для insertWhiteCorners(), проверяет, в правильном ли положении находится нужный угол.
     */
    public boolean whiteCornerPrepared() {
        boolean whiteUp = false;

        if(cubiePos[2][0][0].isCornerCubie() && cubiePos[2][0][0].getDirOfColor('W') == 'A') {
            return false;
        }

        if(cubiePos[2][0][0].getDirOfColor('W') == 'U')
            whiteUp = true;

        if(whiteUp) {
            return (cubiePos[2][0][0].getColorOfDir('R') == cubiePos[1][0][1].getColors()[0].getColor() &&
                    cubiePos[2][0][0].getColorOfDir('F') == cubiePos[2][1][1].getColors()[0].getColor()	);
        }
        else {
            return (cubiePos[2][0][0].getColorOfDir('R') == cubiePos[2][1][1].getColors()[0].getColor() ||
                    cubiePos[2][0][0].getColorOfDir('F') == cubiePos[1][0][1].getColors()[0].getColor());
        }
    }

    /**
     * Проверяет, вставлен ли белый угол
     */
    public boolean cornerInserted(int x, int y, int z) {
        if(cubiePos[x][y][z].isCornerCubie()) {
            return (cubiePos[x][y][z].getColorOfDir('D') == cubiePos[1][1][2].getColors()[0].getColor() &&
                    cubiePos[x][y][z].getColorOfDir('F') == cubiePos[1][0][1].getColors()[0].getColor() &&
                    cubiePos[x][y][z].getColorOfDir('R') == cubiePos[2][1][1].getColors()[0].getColor());
        }
        return false;
    }

    /**
     * Сборка второго слоя
     */
    public String insertAllEdges() {
        StringBuilder moves = new StringBuilder();
        moves.append(insertEdgesInU());
        moves.append(insertMisorientedEdges());
        int iterations = 0;
        while(nonYellowEdgesInU()) {
            if (iterations < 100) {
                iterations++;
            } else {
                return "failed";
            }
            moves.append(insertEdgesInU());
            moves.append(insertMisorientedEdges());
        }
        return optimizeMoves(moves.toString());
    }

    /**
     * Утилита для insertAllEdges(), проверяет, есть ли не желтые грани в верхнем слое.
     */
    public boolean nonYellowEdgesInU() {
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(cubiePos[i][j][0].isEdgeCubie()) {
                    if(cubiePos[i][j][0].getDirOfColor('Y') == 'A')
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Вставляет не желтые грани из верхнего слоя во второй в нужные слоты
     */
    public String insertEdgesInU() {
        StringBuilder moves = new StringBuilder();
        int iterations1 = 0;
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(cubiePos[i][j][0].isEdgeCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'A') {
                    if (iterations1 < 100) {
                        iterations1++;
                    } else {
                        return "failed";
                    }

                    if(j==1) {
                        if(i==0) {
                            moves.append(performMoves("U' "));
                        } else {
                            moves.append(performMoves("U "));
                        }
                    }
                    else if(j==2){
                        moves.append(performMoves("U2 "));
                    }

                    int numUTurns = 0;
                    int yRotations = 0;

                    int iterations2 = 0;
                    while(cubiePos[1][0][0].getColorOfDir('F') != cubiePos[1][0][1].getColors()[0].getColor()) {

                        if (iterations2 < 100) {
                            iterations2++;
                        } else {
                            return "failed";
                        }

                        performMoves("U y' "); numUTurns++; yRotations++;
                    }
                    uAndyTurns(moves, numUTurns, yRotations);

                    if(cubiePos[1][0][0].getColorOfDir('U') == cubiePos[0][1][1].getColors()[0].getColor()) {
                        moves.append(performMoves("U' L' U L U F U' F' "));
                    }
                    else if(cubiePos[1][0][0].getColorOfDir('U') == cubiePos[2][1][1].getColors()[0].getColor()){
                        moves.append(performMoves("U R U' R' U' F' U F "));
                    }

                    i = 0; j = 0;
                }
            }
        }
        return moves.toString();
    }

    /**
     * Правильно ориентирует неправильно ориентированные грани второго слоя
     */
    public String insertMisorientedEdges() {
        StringBuilder moves = new StringBuilder();
        for(int i = 0; i<4; i++) {
            moves.append(performMoves("y "));
            if(cubiePos[2][0][1].getDirOfColor('Y') == 'A' &&
                    cubiePos[2][0][1].getColorOfDir('F') != cubiePos[1][0][1].getColors()[0].getColor()) {
                if(cubiePos[2][0][1].getColorOfDir('F') == cubiePos[2][1][1].getColors()[0].getColor() &&
                        cubiePos[2][0][1].getColorOfDir('R') == cubiePos[1][0][1].getColors()[0].getColor()) {
                    moves.append(performMoves("R U R' U2 R U2 R' U F' U' F "));
                }
                else {
                    moves.append(performMoves("R U R' U' F' U' F "));
                    moves.append(insertEdgesInU());
                    i = -1;
                }
            }
        }
        return moves.toString();
    }

    /**
     * Утилита для yellowEdgeOrientation() и makeYellowCross(), возвращает количество правильно ориентированных
     * желтых граней во втором слое
     */
    public int numYellowEdgesOriented(){
        int numOriented = 0;
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(cubiePos[i][j][0].isEdgeCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'U')
                    numOriented++;
            }
        }
        return numOriented;
    }

    /**
     * Утилита для orientLastLayer(), возвращает количество желтых правильно ориентированных углов в верхнем слое
     */
    public int numYellowCornersOriented(){
        int numOriented = 0;
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3; j++) {
                if(cubiePos[i][j][0].isCornerCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'U')
                    numOriented++;
            }
        }
        return numOriented;
    }

    /**
     * Утилита для makeYellowCross(). Возвращает форму правильно ориентированных желтых граней в верхнем слое
     * (точка, угол, прямая или крест)
     */
    public String yellowEdgeOrientation() {
        String status = "";
        int numOriented = numYellowEdgesOriented();

        if(numOriented == 4) {
            status = "Cross";
        }
        else if(numOriented == 0) {
            status = "Dot";
        }
        else if(numOriented == 2) {
            int[] xValues = new int[2];
            int index = 0;
            for(int i = 0; i<3; i++) {
                for(int j = 0; j<3; j++) {
                    if(cubiePos[i][j][0].isEdgeCubie() && cubiePos[i][j][0].getDirOfColor('Y') == 'U') {
                        xValues[index] = i; index++;
                    }
                }
            }
            if(Math.abs(xValues[0]-xValues[1])%2 == 0) {
                status = "Bar";
            } else {
                status = "L";
            }
        }
        return status;
    }

    /**
     * Сборка желтого креста
     */
    public String makeYellowCross() {
        StringBuilder moves = new StringBuilder();
        String status = yellowEdgeOrientation();

        if(status.compareTo("Dot") == 0) {
            moves.append(performMoves("F R U R' U' F' U2 F U R U' R' F' "));
        }
        else if(status.compareTo("L") == 0) {
            while(cubiePos[0][1][0].getDirOfColor('Y') != 'U' || cubiePos[1][2][0].getDirOfColor('Y') != 'U') {
                moves.append(performMoves("U "));
            }
            moves.append(performMoves("F U R U' R' F' "));
        }
        else if(status.compareTo("Bar") == 0) {
            int iterations = 0;
            while(cubiePos[0][1][0].getDirOfColor('Y') != 'U' || cubiePos[2][1][0].getDirOfColor('Y') != 'U') {

                if (iterations < 100) {
                    iterations++;
                } else {
                    return "failed";
                }

                moves.append(performMoves("U "));
            }
            moves.append(performMoves("F R U R' U' F' "));
        }
        return optimizeMoves(moves.toString());
    }

    /**
     * Ориентирует углы в верхнем слое
     */
    public String orientLastLayer() {
        StringBuilder moves = new StringBuilder();
        int numOriented = numYellowCornersOriented();
        int iterations1 = 0;
        while(numOriented != 4) {
            if (iterations1 < 100) {
                iterations1++;
            } else {
                return "failed";
            }

            if(numOriented == 0){
                int iterations = 0;
                while(cubiePos[0][0][0].getDirOfColor('Y') != 'L') {
                    if (iterations < 5) {
                        iterations++;
                    } else {
                        return "failed";
                    }

                    moves.append(performMoves("U "));
                }
                moves.append(performMoves("R U R' U R U2 R' "));
            }
            else if(numOriented == 1){
                int iterations = 0;
                while(cubiePos[0][0][0].getDirOfColor('Y') != 'U') {
                    if (iterations < 5) {
                        iterations++;
                    } else {
                        return "failed";
                    }

                    moves.append(performMoves("U "));
                }
                moves.append(performMoves("R U R' U R U2 R' "));
            }
            else if(numOriented == 2){
                int iterations = 0;
                while(cubiePos[0][0][0].getDirOfColor('Y') != 'F') {
                    if (iterations < 5) {
                        iterations++;
                    } else {
                        return "failed";
                    }

                    moves.append(performMoves("U "));
                }
                moves.append(performMoves("R U R' U R U2 R' "));
            }
            numOriented = numYellowCornersOriented();
        }
        return optimizeMoves(moves.toString());
    }

    /**
     * Финальная часть сборки
     */
    public String permuteLastLayer() {
        StringBuilder moves = new StringBuilder();
        int numHeadlights = 0;
        for(int i = 0; i<4; i++) {
            turn("y");
            if(cubiePos[0][0][0].getColorOfDir('F') == cubiePos[2][0][0].getColorOfDir('F'))
                numHeadlights++;
        }

        if(numHeadlights == 0){
            moves.append(performMoves("R' F R' B2 R F' R' B2 R2 "));
            numHeadlights = 1;
        }
        if(numHeadlights == 1) {

            int iterations = 0;
            while(cubiePos[0][2][0].getColorOfDir('B') != cubiePos[2][2][0].getColorOfDir('B')) {
                moves.append(performMoves("U "));
                if (iterations == 5)
                    return "failed";
                iterations++;
            }
            moves.append(performMoves("R' F R' B2 R F' R' B2 R2 "));

            System.out.println("first done");
        }

        int numSolved = 0;
        for(int i = 0; i<4; i++) {
            turn("y");
            if(cubiePos[0][0][0].getColorOfDir('F') == cubiePos[1][0][0].getColorOfDir('F'))
                numSolved++;
        }
        if (numSolved == 0) {
            moves.append(performMoves("R2 U R U R' U' R' U' R' U R' "));
            numSolved = 1;
        }
        if (numSolved == 1){
            int iterations = 0;
            while (cubiePos[0][2][0].getColorOfDir('B') != cubiePos[1][2][0].getColorOfDir('B')) {
                moves.append(performMoves("U "));

                if (iterations == 5)
                    return "failed";
                iterations++;
            }

            if (cubiePos[1][0][0].getColorOfDir('F') == cubiePos[0][0][0].getColorOfDir('L')) {
                moves.append(performMoves("R2 U R U R' U' R' U' R' U R' "));
            }
            else {
                moves.append(performMoves("R U' R U R U R U' R' U' R2 "));
            }
        }

        System.out.println("second done");

        int iterations = 0;
        while(cubiePos[0][0][0].getColorOfDir('F') != cubiePos[1][0][1].getColors()[0].getColor()) {
            moves.append(performMoves("U "));

            if (iterations == 5)
                return "failed";
            iterations++;
        }

        System.out.println("third done");

        if (!solved())
            moves.append(performMoves("D L2 B2 F2 R2 U' L2 B2 F2 R2"));
        if (!solved())
            moves.append(performMoves("D L2 B2 F2 R2 U' L2 B2 F2 R2"));
        if (!solved())
            moves.append(performMoves("D L2 B2 F2 R2 U' L2 B2 F2 R2"));
        if (!solved())
            moves.append(performMoves("D L2 B2 F2 R2 U' L2 B2 F2 R2"));
        if (!solved())
            return "failed";

        return optimizeMoves(moves.toString());
    }

    public String solve() {
        StringBuilder commandsSb = new StringBuilder();

        String currCommand = optimizeMoves(makeSunflower());
        if (checkIfFailed(currCommand)) return "failed";
        commandsSb.append(currCommand);

        currCommand = optimizeMoves(makeWhiteCross());
        if (checkIfFailed(currCommand)) return "failed";
        commandsSb.append(currCommand);

        currCommand = optimizeMoves(finishWhiteLayer());
        if (checkIfFailed(currCommand)) return "failed";
        commandsSb.append(currCommand);

        currCommand = optimizeMoves(insertAllEdges());
        if (checkIfFailed(currCommand)) return "failed";
        commandsSb.append(currCommand);

        currCommand = optimizeMoves(makeYellowCross());
        if (checkIfFailed(currCommand)) return "failed";
        commandsSb.append(currCommand);

        currCommand = optimizeMoves(orientLastLayer());
        if (checkIfFailed(currCommand)) return "failed";
        commandsSb.append(currCommand);

        currCommand = optimizeMoves(permuteLastLayer());
        if (checkIfFailed(currCommand)) return "failed";
        commandsSb.append(currCommand);

        return optimizeMoves(commandsSb.toString().trim()) + " ";
    }

    private boolean checkIfFailed(String currCommand) {
        return currCommand.equals("failed");
    }

    /**
     * Проверяет, собран ли куб
     */
    public boolean solved() {
        char[][][] colors = getTheState();
        for (int i = 0; i < 6; i++) {
            char currColor = colors[i][0][0];
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (colors[i][j][k] != currColor)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Устанавливает куб в заданное состояние
     */
    public void setTheState(char[][][] colors) {
        for(int z = 0; z < 3; z++) {
            for(int x = 0; x < 3; x++) {
                cubiePos[x][0][z].setColorOfDir('F', colors[0][z][x]);
            }
        }
        for(int z = 0; z < 3; z++) {
            for(int y = 0; y < 3; y++) {
                cubiePos[2][y][z].setColorOfDir('R', colors[1][z][y]);
            }
        }
        for(int z = 0; z < 3; z++) {
            for(int x = 2; x >= 0; x--) {
                cubiePos[x][2][z].setColorOfDir('B', colors[2][z][2-x]);
            }
        }
        for(int z = 0; z < 3; z++) {
            for(int y = 2; y >= 0; y--) {
                cubiePos[0][y][z].setColorOfDir('L', colors[3][z][2-y]);
            }
        }
        for(int y = 2; y >= 0; y--) {
            for(int x = 0; x < 3; x++) {
                cubiePos[x][y][0].setColorOfDir('U', colors[4][2-y][x]);
            }
        }
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                cubiePos[x][y][2].setColorOfDir('D', colors[5][y][x]);
            }
        }
    }

    /**
     * Сбрасывает куб в стандартное собранное состояние
     */
    public void reset() {
        char[][][] colors = new char[6][3][3];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++)
                    colors[i][j][k] = Utility.getColorOfInt(i);
        setTheState(colors);
    }

    /**
     * Устанавливает цвет конкретного кубика в заданном направлении
     */
    public void setCubieColor(int x, int y, int z, char dir, char ncolor) {
        cubiePos[x][y][z].setColorOfDir(dir, ncolor);
    }

    /**
     * Возвращает состояние куба
     */
    public char[][][] getTheState() {
        char[][][] allSets = new char[6][3][3];
        char[][] left = new char[3][3];
        char[][] up = new char[3][3];
        char[][] front = new char[3][3];
        char[][] back = new char[3][3];
        char[][] right = new char[3][3];
        char[][] down = new char[3][3];

        for(int z = 0; z < 3; z++) {
            for(int x = 0; x < 3; x++) {
                front[z][x] = cubiePos[x][0][z].getColorOfDir('F');
            }
        }
        for(int z = 0; z < 3; z++) {
            for(int y = 0; y < 3; y++) {
                right[z][y] = cubiePos[2][y][z].getColorOfDir('R');
            }
        }
        for(int z = 0; z <= 2; z++) {
            for(int x = 2; x >= 0; x--) {
                back[z][2-x] = cubiePos[x][2][z].getColorOfDir('B');
            }
        }
        for(int z = 0; z < 3; z++) {
            for(int y = 2; y >= 0; y--) {
                left[z][Math.abs(y-2)] = cubiePos[0][y][z].getColorOfDir('L');
            }
        }
        for(int y = 2; y >= 0; y--) {
            for(int x = 0; x < 3; x++) {
                up[2-y][x] = cubiePos[x][y][0].getColorOfDir('U');
            }
        }
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                down[y][x] = cubiePos[x][y][2].getColorOfDir('D');
            }
        }

        allSets[0] = front; allSets[1] = right; allSets[2] = back; allSets[3] = left;
        allSets[4] = up; allSets[5] = down;

        return allSets;
    }

}