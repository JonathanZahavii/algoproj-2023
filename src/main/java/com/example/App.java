package com.example;

public class App {

    public static void main(String[] args) {
        // Board initBoard = new Board(24);
        // initBoard.generateSolvableInXMoves(10);
        Board initBoard = Board.getBoardFromUser();
        Board goalBoard = new Board(15);
        Graph graph = new Graph();
        graph.BFS(initBoard, goalBoard);
    }
}
