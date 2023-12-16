package com.example;

public class App {

    public static void main(String[] args) {
        Board initBoard = new Board(24);
        initBoard.generateSolvableInXMoves(10);
        Board goalBoard = new Board(24);
        Graph graph = new Graph();
        graph.AStar(initBoard, goalBoard);
    }
}
