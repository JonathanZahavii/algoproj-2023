package com.example;

public class InspectAnswer {
    private double runTime;
    private int nodesExplored;
    private Path path;
    private int moves;
    private boolean isSolved;

    public InspectAnswer(double runTime, int nodesExplored, Path path, boolean isSolved) {
        this.runTime = runTime;
        this.nodesExplored = nodesExplored;
        this.path = path;
        this.moves = path.getMoves();
        this.isSolved = true;
    }


    public InspectAnswer() {
        this.runTime = 0.0;
        this.nodesExplored = 0;
        this.moves = 0;
        this.isSolved = true;
    }

    public double getRunTime() {
        return runTime;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

    public int getNodesExplored() {
        return nodesExplored;
    }

    public void setNodesExplored(int nodesExplored) {
        this.nodesExplored = nodesExplored;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public Path getPath() {
        return path;
    }

    public void print() {
        System.out.printf("Runtime: " + runTime + " seconds");
        System.out.println("");
        System.out.println("Nodes explored: " + nodesExplored);
        System.out.println("Moves: " + moves);
        System.out.println("Solved: " + isSolved);
    }


    public void add(InspectAnswer inspectAnswer) {
        this.runTime += inspectAnswer.getRunTime();
        this.nodesExplored += inspectAnswer.getNodesExplored();
        this.moves += inspectAnswer.getMoves();
        this.isSolved = this.isSolved && inspectAnswer.isSolved();
    }

    public void divide(int n) {
        this.runTime /= n;
        this.nodesExplored /= n;
        this.moves /= n;
    }
}