package com.example;

public class App {

    public static void main(String[] args) {
        /*
         * Game constants
         */
        final int RUNS = 1;
        final int SIZE = 15;
        final Board GOAL_BOARD = new Board(SIZE);

        /*
         * Get board from user
         */
        // Board initBoard = Board.getBoardFromUser();

        /*
         * Generate random board
         */
        // Board initBoard = new Board(SIZE);
        // initBoard.generateSolvableInXMoves(10);
        // Graph graph = new Graph();
        // InspectAnswer inspectAnswer = (PuzzleSolver.BFS(graph, initBoard, GOAL_BOARD));
        // inspectAnswer.getPath().print();

        /*
         * Run game multiple times
         */
        InspectAnswer inspectAnswerBFS = new InspectAnswer();
        InspectAnswer inspectAnswerDIJ = new InspectAnswer();
        InspectAnswer inspectAnswerMAN = new InspectAnswer();
        InspectAnswer inspectAnswerNAD = new InspectAnswer();

        for (int i = 0; i < RUNS; i++) {
            Board initBoard = new Board(SIZE);
            initBoard.generateSolvableInXMoves(10);

            Board initBoardBFS = new Board(initBoard.getBoard());
            Graph graphBFS = new Graph();
            inspectAnswerBFS.add(PuzzleSolver.BFS(graphBFS, initBoardBFS, GOAL_BOARD));

            Board initBoardDIJ = new Board(initBoard.getBoard());
            Graph graphDIJ = new Graph();
            inspectAnswerDIJ.add(PuzzleSolver.AStar(graphDIJ, initBoardDIJ, GOAL_BOARD,
                    Heuristic.DIJAKSTRA));

            Board initBoardMAN = new Board(initBoard.getBoard());
            Graph graphMAN = new Graph();
            inspectAnswerMAN.add(PuzzleSolver.AStar(graphMAN, initBoardMAN, GOAL_BOARD,
                    Heuristic.MANHATTAN));

            Board initBoardNAD = new Board(initBoard.getBoard());
            Graph graphNAD = new Graph();
            inspectAnswerNAD.add(PuzzleSolver.AStar(graphNAD, initBoardNAD, GOAL_BOARD,
                    Heuristic.NONADMISSIBLE));
        }

        inspectAnswerBFS.divide(RUNS);
        inspectAnswerDIJ.divide(RUNS);
        inspectAnswerMAN.divide(RUNS);
        inspectAnswerNAD.divide(RUNS);

        System.out.println("BFS");
        inspectAnswerBFS.print();
        System.out.println("DIJ");
        inspectAnswerDIJ.print();
        System.out.println("MAN");
        inspectAnswerMAN.print();
        System.out.println("NAD");
        inspectAnswerNAD.print();
    }
}
