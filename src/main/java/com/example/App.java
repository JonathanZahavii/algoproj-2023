package com.example;

public class App {

    public static void main(String[] args) {
        // Board initBoard = new Board(15);
        // initBoard.generateSolvableInXMoves(10);
        Board initBoard = Board.getBoardFromUser();
        Board goalBoard = new Board(15);
        Graph graph = new Graph();
        PuzzleSolver solver = new PuzzleSolver();
        InspectAnswer inspectAnswer = solver.BFS(graph, initBoard, goalBoard);
        // InspectAnswer inspectAnswer = solver.AStar(graph, initBoard, goalBoard, Heuristic.MANHATTAN);
        inspectAnswer.print();
    }
}
