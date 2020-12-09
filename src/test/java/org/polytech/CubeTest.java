package org.polytech;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.polytech.Model.*;



class CubeTest {
    private Cube cube1;
    private Cube cube2;

    @Test
    void setTheStateTest() {
        cube1 = new Cube();
        cube2 = new Cube();
        cube1.setTheState(new char[][][] {
                {{'G', 'G', 'G'}, {'G', 'G', 'G'}, {'G', 'G', 'G'}},
                {{'O', 'O', 'O'}, {'O', 'O', 'O'}, {'O', 'O', 'O'}},
                {{'B', 'B', 'B'}, {'B', 'B', 'B'}, {'B', 'B', 'B'}},
                {{'R', 'R', 'R'}, {'R', 'R', 'R'}, {'R', 'R', 'R'}},
                {{'Y', 'Y', 'Y'}, {'Y', 'Y', 'Y'}, {'Y', 'Y', 'Y'}},
                {{'W', 'W', 'W'}, {'W', 'W', 'W'}, {'W', 'W', 'W'}}
        });
        assertArrayEquals(cube1.getTheState(), cube2.getTheState());
    }

    @Test
    void performMovesTest() {
        cube1 = new Cube();
        cube2 = new Cube();
        cube1.performMoves("R U R' U'");
        cube2.setTheState(new char[][][] {
                {{'G', 'G', 'W'}, {'G', 'G', 'Y'}, {'G', 'G', 'G'}},
                {{'O', 'O', 'Y'}, {'B', 'O', 'O'}, {'Y', 'O', 'O'}},
                {{'B', 'O', 'O'}, {'B', 'B', 'B'}, {'B', 'B', 'B'}},
                {{'B', 'R', 'R'}, {'R', 'R', 'R'}, {'R', 'R', 'R'}},
                {{'Y', 'Y', 'R'}, {'Y', 'Y', 'G'}, {'Y', 'Y', 'G'}},
                {{'W', 'W', 'O'}, {'W', 'W', 'W'}, {'W', 'W', 'W'}}
        });
        assertArrayEquals(cube1.getTheState(), cube2.getTheState());

        cube1.reset();
        cube2.reset();
        cube1.performMoves("L D L' D'");
        cube2.setTheState(new char[][][] {
                {{'G', 'G', 'G'}, {'W', 'G', 'G'}, {'Y', 'G', 'G'}},
                {{'O', 'O', 'O'}, {'O', 'O', 'O'}, {'O', 'O', 'B'}},
                {{'B', 'B', 'B'}, {'B', 'B', 'B'}, {'R', 'R', 'B'}},
                {{'R', 'R', 'W'}, {'R', 'R', 'B'}, {'W', 'R', 'R'}},
                {{'Y', 'Y', 'Y'}, {'Y', 'Y', 'Y'}, {'R', 'Y', 'Y'}},
                {{'G', 'W', 'W'}, {'G', 'W', 'W'}, {'O', 'W', 'W'}}
        });
        assertArrayEquals(cube1.getTheState(), cube2.getTheState());

        cube1.reset();
        cube2.reset();
        cube1.performMoves("B F B' F'");
        assertArrayEquals(cube1.getTheState(), cube2.getTheState());
    }

    @Test
    void solvedTest() {
        cube1 = new Cube();
        assertTrue(cube1.solved());
        cube1.performMoves("U");
        assertFalse(cube1.solved());

    }

    @Test
    void solveTest() {
        cube1 = new Cube();
        for (int i = 0; i < 1000; i++) {
            cube1.performMoves(cube1.randScramble());
            cube1.solve();
            assertTrue(cube1.solved());
            cube1.reset();
        }
    }
}
