package com.example;

import java.util.*;

public class Graph {
    private Map<Board, List<Board>> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addNode(Board node) {
        if (!this.graph.containsKey(node)) {
            this.graph.put(node, new ArrayList<Board>());
        }
    }

    public void addEdge(Board source, Board destination) {
        if (!this.graph.containsKey(source) || !this.graph.containsKey(destination)) {
            throw new IllegalArgumentException("Invalid source or destination node.");
        }
        this.graph.get(source).add(destination);
        this.graph.get(destination).add(source);
    }

    public List<Board> getNeighbors(Board node) {
        List<Board> neighbors = this.graph.get(node);
        if (neighbors.size() < 4) {
            neighbors = node.getNeighbors();
        }
        return neighbors;
    }

    public int getNodeCount() {
        return this.graph.keySet().size();
    }

    public double getRunTimeInSeconds(long startTime) {
        long endTime = System.nanoTime();
        long runTime = endTime - startTime;
        double runTimeInSeconds = runTime / 1_000_000_000.0;
        return runTimeInSeconds;
    }

    public InspectAnswer BFS(Board start, Board goal) {
        long startTime = System.nanoTime();
        List<Board> visited = new ArrayList<>();
        Queue<Board> queue = new LinkedList<Board>();
        boolean isSolved = false;

        this.addNode(start);
        queue.add(start);
        Board current = start;

        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current.equals(goal)) {
                visited.add(current);
                isSolved = true;
                break;
            }

            if (!visited.contains(current)) {
                visited.add(current);
                for (Board neighbor : current.getNeighbors()) { // FIXME: Not getting neighbors from graph
                    this.addNode(neighbor);
                    this.addEdge(current, neighbor);
                    neighbor.setCameFrom(current);
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        double runTime = getRunTimeInSeconds(startTime);
        Path path = Path.reconstructPath(current);
        return new InspectAnswer(runTime, this.getNodeCount(), path.getMoves(), isSolved);
    }

    /**
     * Performs the A* search algorithm to find the shortest path from the start
     * board to the goal board.
     *
     * @param start The initial state of the puzzle.
     * @param goal  The goal state of the puzzle.
     * @return A list of board states representing the shortest path from start
     *         to goal.
     */
    public InspectAnswer AStar(Board start, Board goal, Heuristic heuristicType) {
        long startTime = System.nanoTime();
        boolean isSolved = false;
        List<Board> visited = new ArrayList<>();
        Map<Board, Integer> gScore = new HashMap<>(); // Map to store the g-scores (cost from start) for each node
        PriorityQueue<Board> queue = new PriorityQueue<>(Comparator.comparingInt(Board::getTotalCost)); // Priority
                                                                                                        // queue for the
                                                                                                        // queue,
                                                                                                        // ordered by
                                                                                                        // the total
                                                                                                        // cost
        this.addNode(start);
        queue.add(start);
        gScore.put(start, 0);
        Board current = start;

        while (!queue.isEmpty()) {
            current = queue.poll();
            visited.add(current);

            if (current.equals(goal)) {
                isSolved = true;
                break;
            }

            for (Board neighbor : current.getNeighbors()) { // FIXME: Not getting neighbors from graph
                this.addNode(neighbor);
                this.addEdge(current, neighbor);
                int tentativeGScore = gScore.get(current) + 1;

                // Skip if the neighbor is already visited and has a higher g-score
                if (visited.contains(neighbor) && tentativeGScore >= gScore.get(neighbor))
                    continue;

                // Update g-score and total cost if a better path is found
                if (!queue.contains(neighbor) || !gScore.containsKey(neighbor)
                        || tentativeGScore < gScore.get(neighbor)) {
                    gScore.put(neighbor, tentativeGScore);

                    int totalCost = tentativeGScore + heuristic(neighbor, goal, heuristicType);
                    neighbor.setTotalCost(totalCost);
                    neighbor.setCameFrom(current);

                    if (!queue.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        double runTime = getRunTimeInSeconds(startTime);
        Path path = Path.reconstructPath(current);
        return new InspectAnswer(runTime, this.getNodeCount(), path.getMoves(), isSolved);
    }

    private int heuristicManhattan(Board current, Board goal) {
        int[][] currentBoard = current.getBoard();
        int[][] goalBoard = goal.getBoard();
        int manhattanDistance = 0;

        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                int value = currentBoard[i][j];
                if (value != 0) {
                    for (int x = 0; x < goalBoard.length; x++) {
                        for (int y = 0; y < goalBoard[x].length; y++) {
                            if (goalBoard[x][y] == value) {
                                manhattanDistance += Math.abs(i - x) + Math.abs(j - y);
                            }
                        }
                    }
                }
            }
        }

        return manhattanDistance;
    }

    private int heuristicDijaksra(Board current, Board goal) {
        return 0;
    }

    private int heuristicNonAdmissible(Board current, Board goal) {
        // Non-admissible heuristic always returns a constant value of 5
        return 5;
    }

    private int heuristic(Board current, Board goal, Heuristic type) {
        switch (type) {
            case MANHATTAN:
                return heuristicManhattan(current, goal);
            case DIJAKSTRA:
                return heuristicDijaksra(current, goal);
            case NONADMISSIBLE:
                return heuristicNonAdmissible(current, goal);
            default:
                return 0;
        }

    }
}