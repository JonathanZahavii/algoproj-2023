package com.example;

public class App {

    public static void main(String[] args) {
        final int RUNS = 5;
        final int SIZE = 15;
        final int MOVES = 10;
        final Board GOAL_BOARD = new Board(SIZE);

        Game gameOnce = new Game(SIZE, MOVES, GOAL_BOARD, true);
        gameOnce.runOnce("MANHATTAN");

        Game gameMultiple = new Game(RUNS, SIZE, MOVES, GOAL_BOARD);
        gameMultiple.RunMultiple();
    }
}
