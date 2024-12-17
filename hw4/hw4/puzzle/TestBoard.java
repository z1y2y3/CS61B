package hw4.puzzle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBoard {
    @Test
    public void verifyImmutability() {
        int r = 2;
        int c = 2;
        int[][] x = new int[r][c];
        int cnt = 0;
        for (int i = 0; i < r; i += 1) {
            for (int j = 0; j < c; j += 1) {
                x[i][j] = cnt;
                cnt += 1;
            }
        }
        Board b = new Board(x);
        assertEquals("Your Board class is not being initialized with the right values.", 0, b.tileAt(0, 0));
        assertEquals("Your Board class is not being initialized with the right values.", 1, b.tileAt(0, 1));
        assertEquals("Your Board class is not being initialized with the right values.", 2, b.tileAt(1, 0));
        assertEquals("Your Board class is not being initialized with the right values.", 3, b.tileAt(1, 1));

        x[1][1] = 1000;
        assertEquals("Your Board class is mutable and you should be making a copy of the values in the passed tiles array. Please see the FAQ!", 3, b.tileAt(1, 1));
    }

    @Test
    public void twoEstimate() {
        int[][] initial = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        int[][] goal = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board b = new Board(initial);

        assertEquals("Hamming estimate", 5, b.hamming());
        assertEquals("Manhattan estimate", 10, b.manhattan());

        int[][] initial2 = {
                {1, 2},
                {3, 0}
        };
        int[][] goal2 = {
                {1, 2},
                {3, 0}
        };
        Board b2 = new Board(initial2);

        assertEquals("Hamming estimate", 0, b2.hamming());
        assertEquals("Manhattan estimate", 0, b2.manhattan());
    }


} 
