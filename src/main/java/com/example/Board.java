package com.example;

import java.util.*;

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
        int oldRow = 0, oldCol = 0;
        boolean found = false;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] == 0) {
                    oldRow = i;
                    oldCol = j;
                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }

        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] dir : dirs) {
            int newRow = oldRow + dir[0];
            int newCol = oldCol + dir[1];
            if (newRow >= 0 && newRow < this.board.length && newCol >= 0 && newCol < this.board[0].length) {
                this.swap(oldRow, oldCol, newRow, newCol);
                neighbors.add(new Board(this.board));
                this.swap(oldRow, oldCol, newRow, newCol);
            }
        }

        return neighbors;
    }

    private void swap(int oldRow, int oldCol, int newRow, int newCol) {
        int temp = board[oldRow][oldCol];
        this.board[oldRow][oldCol] = this.board[newRow][newCol];
        this.board[newRow][newCol] = temp;
    }

    public void generateSolvableInXMoves(int moves) {
        int emptyRow = this.size - 1;
        int emptyCol = this.size - 1;
        Random random = new Random();
        int lastMove = -1;

        for (int i = 0; i < moves; i++) {
            int direction = random.nextInt(4); // 0: up, 1: right, 2: down, 3: left
            boolean moveMade = false;

            while (!moveMade) {
                // don't undo the last move
                if ((lastMove == 0 && direction == 2) || (lastMove == 2 && direction == 0)
                        || (lastMove == 1 && direction == 3) || (lastMove == 3 && direction == 1)) {
                    direction = random.nextInt(4);
                    continue;
                }

                switch (direction) {
                    case 0: // up
                        if (emptyRow < this.size - 1) {
                            this.swap(emptyRow, emptyCol, emptyRow + 1, emptyCol);
                            emptyRow++;
                            moveMade = true;
                        }
                        break;
                    case 1: // right
                        if (emptyCol > 0) {
                            this.swap(emptyRow, emptyCol, emptyRow, emptyCol - 1);
                            emptyCol--;
                            moveMade = true;
                        }
                        break;
                    case 2: // down
                        if (emptyRow > 0) {
                            this.swap(emptyRow, emptyCol, emptyRow - 1, emptyCol);
                            emptyRow--;
                            moveMade = true;
                        }
                        break;
                    case 3: // left
                        if (emptyCol < this.size - 1) {
                            this.swap(emptyRow, emptyCol, emptyRow, emptyCol + 1);
                            emptyCol++;
                            moveMade = true;
                        }
                        break;
                }

                if (moveMade) {
                    lastMove = direction;
                } else {
                    direction = random.nextInt(4);
                }
            }
        }
    }

    public void printBoard() {
        for (int[] row : this.board) {
            for (int tile : row) {
                System.out.print(tile + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------");
    }

    private static int[][] insertBoard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the size of the board:");
        int length = scanner.nextInt();
        int size = (int) Math.sqrt(length + 1);
        int[][] board = new int[size][size];
        System.out.println("Enter the elements of the board:");
        for (int i = 0; i < size; i++) {
            System.out.println("Row #" + (i + 1));
            for (int j = 0; j < size; j++) {
                System.out.println("Col #" + (j + 1));
                board[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
        return board;
    }

    private static boolean isSolvable(int[][] board) {
        int[] flatBoard = Arrays.stream(board).flatMapToInt(Arrays::stream).toArray();
        int inversions = 0;

        for (int i = 0; i < flatBoard.length - 1; i++) {
            for (int j = i + 1; j < flatBoard.length; j++) {
                if (flatBoard[i] > flatBoard[j]) {
                    inversions++;
                }
            }
        }

        int zeroIndex = Arrays.asList(Arrays.stream(flatBoard).boxed().toArray(Integer[]::new)).indexOf(0);
        if (zeroIndex == -1)
            throw new IllegalArgumentException("The board is not solvable: no empty tile");

        int blankRowFromBottom = (flatBoard.length - zeroIndex) / board.length;
        if ((board.length % 2 == 0 && blankRowFromBottom % 2 != inversions % 2) || inversions % 2 == 0) {
            return true;
        } else {
            throw new IllegalArgumentException("The board is not solvable");
        }
    }

    public static Board getBoardFromUser() {
        int[][] board = insertBoard();
        isSolvable(board);
        return new Board(board);
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getSize() {
        return this.size;
    }

    public int getTotalCost() {
        return this.totalCost;
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