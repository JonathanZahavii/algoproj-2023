package com.example;

public class InspectAnswer {
    private double runTime;
    private int nodesExplored;
    private int moves;
    private boolean isSolved;

    public InspectAnswer(double runTime, int nodesExplored, int moves, boolean isSolved) {
        this.runTime = runTime;
        this.nodesExplored = nodesExplored;
        this.moves = moves;
        this.isSolved = true;
    }

    public double getRunTime() {
        return runTime;
    }

    public int getNodesExplored() {
        return nodesExplored;
    }

    public int getMoves() {
        return moves;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void print() {
        System.out.println("Runtime: " + runTime + " seconds");
        System.out.println("Nodes explored: " + nodesExplored);
        System.out.println("Moves: " + moves);
        System.out.println("Solved: " + isSolved);
    }
}