package com.example;

public class App {

    public static void main(String[] args) {
        Board initBoard = new Board(24);
        initBoard.generateSolvableInXMoves(20);
        // Board initBoard = Board.getBoardFromUser();
        Board goalBoard = new Board(24);
        Graph graph = new Graph();
        PuzzleSolver solver = new PuzzleSolver();
        InspectAnswer inspectAnswer = solver.BFS(graph, initBoard, goalBoard);
        // InspectAnswer inspectAnswer = solver.AStar(graph, initBoard, goalBoard, Heuristic.MANHATTAN);
        inspectAnswer.print();
    }
}
