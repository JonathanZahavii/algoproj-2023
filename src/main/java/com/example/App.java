package com.example;

public class App {

    public static void main(String[] args) {
        Board initBoard = new Board(15);
        initBoard.generateSolvableInXMoves(10);
        // Board initBoard = Board.getBoardFromUser();
        Board goalBoard = new Board(15);
        Graph graph = new Graph();
        InspectAnswer inspectAnswer = graph.BFS(initBoard, goalBoard);
        inspectAnswer.print();
    }
}
