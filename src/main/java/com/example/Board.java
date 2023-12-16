package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private int[][] board;
    private int size;

    private int totalCost;
    private Board cameFrom;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] != ((Board) o).board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param blocks 2D array representing the initial state of the board.
     */
    public Board(int[][] board) {
        this.board = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = Arrays.copyOf(board[i], board[i].length);
        }
        this.size = board.length;
    }

    /**
     * Creates a board of size n x n with the numbers 1 through n^2 - 1 in order
     * and a 0 in the bottom right corner.
     *
     * @param length the length of one side of the board
     *               length = size^2 - 1
     *               e.g. size = 4, length = 15
     */
    public Board(int length) {
        this.size = (int) Math.sqrt(length + 1);
        this.board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = i * size + j + 1;
            }
        }
        this.board[size - 1][size - 1] = 0; // empty slot
    }

    public List<Board> getNeighbors() {
        List<Board> neighbors = new ArrayList<>();
        int i0 = 0, j0 = 0;
        boolean found = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] dir : dirs) {
            int newI = i0 + dir[0];
            int newJ = j0 + dir[1];
            if (newI >= 0 && newI < board.length && newJ >= 0 && newJ < board[0].length) {
                swap(i0, j0, newI, newJ);
                neighbors.add(new Board(board));
                swap(i0, j0, newI, newJ);
            }
        }

        return neighbors;
    }

    private void swap(int i0, int j0, int i, int j) {
        int temp = board[i0][j0];
        board[i0][j0] = board[i][j];
        board[i][j] = temp;
    }

    // TODO: Check if works
    public void generateSolvableInXMoves(int x) {
        int emptyRow = size - 1;
        int emptyCol = size - 1;
        Random random = new Random();

        for (int i = 0; i < x; i++) {
            int direction = random.nextInt(4); // 0: up, 1: right, 2: down, 3: left
            boolean moveMade = false;

            while (!moveMade) {
                switch (direction) {
                    case 0: // up
                        if (emptyRow < size - 1) {
                            swap(emptyRow, emptyCol, emptyRow + 1, emptyCol);
                            emptyRow++;
                            moveMade = true;
                        }
                        break;
                    case 1: // right
                        if (emptyCol > 0) {
                            swap(emptyRow, emptyCol, emptyRow, emptyCol - 1);
                            emptyCol--;
                            moveMade = true;
                        }
                        break;
                    case 2: // down
                        if (emptyRow > 0) {
                            swap(emptyRow, emptyCol, emptyRow - 1, emptyCol);
                            emptyRow--;
                            moveMade = true;
                        }
                        break;
                    case 3: // left
                        if (emptyCol < size - 1) {
                            swap(emptyRow, emptyCol, emptyRow, emptyCol + 1);
                            emptyCol++;
                            moveMade = true;
                        }
                        break;
                }

                if (!moveMade) {
                    direction = random.nextInt(4);
                }
            }
        }
    }

    public void printBoard() {
        for (int[] row : board) {
            for (int tile : row) {
                System.out.print(tile + " ");
            }
            System.out.println();
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }
    
    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Board getCameFrom() {
        return this.cameFrom;
    }

    public void setCameFrom(Board cameFrom) {
        this.cameFrom = cameFrom;
    }

}
