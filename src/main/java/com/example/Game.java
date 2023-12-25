package com.example;

public class Game {
    int runs = 5;
    int size = 15;
    int moves = 10;
    Board goalBoard;
    boolean isFromUser;

    public Game(int runs, int size, int moves, Board goalBoard) {
        this.runs = runs;
        this.size = size;
        this.moves = moves;
        this.goalBoard = goalBoard;
    }

    public Game(int size, int moves, Board goalBoard, boolean isFromUser) {
        this.size = size;
        this.moves = moves;
        this.goalBoard = goalBoard;
        this.isFromUser = isFromUser;
    }

    public void runOnce(String algorithm) {
        Board initBoard;
        if (this.isFromUser) {
            initBoard = Board.getBoardFromUser();
        } else {
            initBoard = new Board(this.size);
            initBoard.generateSolvableInXMoves(this.moves);
        }

        Graph graph = new Graph();
        InspectAnswer inspectAnswer;

        switch (algorithm) {
            case "BFS":
                inspectAnswer = PuzzleSolver.BFS(graph, initBoard, this.goalBoard);
                break;
            case "DIJAKSTRA":
                inspectAnswer = PuzzleSolver.AStar(graph, initBoard, this.goalBoard, Heuristic.DIJAKSTRA);
                break;
            case "MANHATTAN":
                inspectAnswer = PuzzleSolver.AStar(graph, initBoard, this.goalBoard, Heuristic.MANHATTAN);
                break;
            case "NADMISSIBLE":
                inspectAnswer = PuzzleSolver.AStar(graph, initBoard, this.goalBoard, Heuristic.NONADMISSIBLE);
                break;
            default:
                inspectAnswer = PuzzleSolver.BFS(graph, initBoard, this.goalBoard);
                break;
        }

        inspectAnswer.print();
        // inspectAnswer.getPath().print(); // Print path
    }

    public void runMultiple() {
        // Init answer objects
        InspectAnswer inspectAnswerBFS = new InspectAnswer();
        InspectAnswer inspectAnswerDIJ = new InspectAnswer();
        InspectAnswer inspectAnswerMAN = new InspectAnswer();
        InspectAnswer inspectAnswerNAD = new InspectAnswer();

        for (int i = 0; i < this.runs; i++) {
            // Generate random board
            Board initBoard = new Board(this.size);
            initBoard.generateSolvableInXMoves(this.moves);

            Board initBoardBFS = new Board(initBoard.getBoard());
            Graph graphBFS = new Graph();
            inspectAnswerBFS.add(PuzzleSolver.BFS(graphBFS, initBoardBFS, this.goalBoard));

            Board initBoardDIJ = new Board(initBoard.getBoard());
            Graph graphDIJ = new Graph();
            inspectAnswerDIJ.add(PuzzleSolver.AStar(graphDIJ, initBoardDIJ, this.goalBoard,
                    Heuristic.DIJAKSTRA));

            Board initBoardMAN = new Board(initBoard.getBoard());
            Graph graphMAN = new Graph();
            inspectAnswerMAN.add(PuzzleSolver.AStar(graphMAN, initBoardMAN, this.goalBoard,
                    Heuristic.MANHATTAN));

            Board initBoardNAD = new Board(initBoard.getBoard());
            Graph graphNAD = new Graph();
            inspectAnswerNAD.add(PuzzleSolver.AStar(graphNAD, initBoardNAD, this.goalBoard,
                    Heuristic.NONADMISSIBLE));
        }

        inspectAnswerBFS.divide(this.runs);
        inspectAnswerDIJ.divide(this.runs);
        inspectAnswerMAN.divide(this.runs);
        inspectAnswerNAD.divide(this.runs);

        System.out.println("BFS");
        inspectAnswerBFS.print();
        System.out.println("DIJAKSTRA");
        inspectAnswerDIJ.print();
        System.out.println("MANHATTAN");
        inspectAnswerMAN.print();
        System.out.println("NONADMISSIBLE");
        inspectAnswerNAD.print();
    }
}
