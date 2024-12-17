package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board implements WorldState {

    private int[][] board;

    private int N;

    /*
        Constructs a board from an N-by-N array of tiles where
        tiles[i][j] = tile at row i, column j
    */
    public Board(int[][] tiles) {
        int index = 0;
        board = new int[tiles.length][tiles.length];
        for (int[] tile : tiles) {
            board[index] = Arrays.copyOf(tile, tile.length);
            index++;
        }
        N = tiles.length;
    }

    /*
        Returns value of tile at row i, column j (or 0 if blank)
        The tileAt() method should throw a java.lang.IndexOutOfBoundsException
        unless both i and j are between 0 and N − 1.
     * */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    /*
        Returns the board size N
     * */
    public int size() {
        return N;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int N = size();
        int x = -1;
        int y = -1;

        // look for black's position in the current board
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        //  复制current board到newBoard
        int[][] newBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newBoard[i][j] = tileAt(i, j);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Math.abs(x - i) == 1 && Math.abs(y - j) == 0 || Math.abs(x - i) == 0 && Math.abs(y - j) == 1) {
                    // 交换空白方块和相邻方块
                    newBoard[x][y] = newBoard[i][j];
                    newBoard[i][j] = 0;

                    // 创建新的邻居并加入队列
                    Board neighbor = new Board(newBoard);
                    neighbors.enqueue(neighbor);

                    newBoard[i][j] = newBoard[x][y];
                    newBoard[x][y] = 0;
                }
            }
        }
        return neighbors;
    }

    private int getGoal(int i, int j) {
        if (i == N - 1 && j == N - 1) {
            return 0;
        }
        return i * N + (j + 1);
    }

    /*
        Hamming estimate
     * */
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == 0) {
                    continue;
                }
                if (tileAt(i, j) != getGoal(i, j)) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    private int goalPositionThenSum(int x, int i, int j) {
        if (x == 0) {
            return 0;
        }
        int i_ = (x - 1) / N;
        int j_ = (x - 1) % N;
        return Math.abs(i - i_) + Math.abs(j - j_);
    }

    /*
        Manhattan estimate
     * */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += goalPositionThenSum(tileAt(i, j), i, j);
            }
        }
        return sum;
    }

    /*
        Estimated distance to goal. This method should
        simply return the results of manhattan() when submitted to
        Gradescope.
     * */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /*
        Returns true if this board's tile values are the same
        position as y's
     * */
    public boolean equals(Object y) {
        // 1. 判断是否为同一个对象
        if (this == y) return true;

        // 2. 判断类型是否为 int[][]
        if (y == null || getClass() != y.getClass()) return false;

        return Arrays.deepEquals(board, ((Board) y).board);
    }

    /*
        Returns the string representation of the board. This
        method is provided in the skeleton
     * */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
