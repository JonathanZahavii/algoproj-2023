package com.example;

public class App {

    public static void main(String[] args) {

        // Board initBoard = Board.getBoardFromUser();
        final int RUNS = 50;
        final int SIZE = 15;

        Board goalBoard = new Board(SIZE);

        InspectAnswer inspectAnswerBFS = new InspectAnswer();
        InspectAnswer inspectAnswerDIJ = new InspectAnswer();
        InspectAnswer inspectAnswerMAN = new InspectAnswer();
        InspectAnswer inspectAnswerNAD = new InspectAnswer();

        for (int i = 0; i < RUNS; i++) {
            Board initBoard = new Board(SIZE);
            initBoard.generateSolvableInXMoves(10);

            Board initBoardBFS = new Board(initBoard.getBoard());
            Graph graphBFS = new Graph();
            inspectAnswerBFS.add(PuzzleSolver.BFS(graphBFS, initBoardBFS, goalBoard));

            Board initBoardDIJ = new Board(initBoard.getBoard());
            Graph graphDIJ = new Graph();
            inspectAnswerDIJ.add(PuzzleSolver.AStar(graphDIJ, initBoardDIJ, goalBoard,
            Heuristic.DIJAKSTRA));

            Board initBoardMAN = new Board(initBoard.getBoard());
            Graph graphMAN = new Graph();
            inspectAnswerMAN.add(PuzzleSolver.AStar(graphMAN, initBoardMAN, goalBoard,
                    Heuristic.MANHATTAN));

            Board initBoardNAD = new Board(initBoard.getBoard());
            Graph graphNAD = new Graph();
            inspectAnswerNAD.add(PuzzleSolver.AStar(graphNAD, initBoardNAD, goalBoard,
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
