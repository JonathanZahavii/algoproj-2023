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
        return this.graph.get(node);
    }

    public Board get(int index) {
        return (Board) this.graph.keySet().toArray()[index];
    }

    public List<Board> BFS(Board start, Board goal) {
        System.out.println("Starting BFS");
        List<Board> visited = new ArrayList<>();
        Queue<Board> queue = new LinkedList<Board>();

        this.addNode(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Board current = queue.poll();
            current.printBoard();

            if (current.equals(goal)) {
                System.out.println("Goal found");
                visited.add(current);
                return visited;
            }

            if (!visited.contains(current)) {
                visited.add(current);
                for (Board neighbor : current.getNeighbors()) {
                    this.addNode(neighbor);
                    this.addEdge(current, neighbor);
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        System.out.println("Goal not found");
        return visited;
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
    public List<Board> AStar(Board start, Board goal) {
        System.out.println("Starting A*");
        Set<Board> visited = new HashSet<>();
        PriorityQueue<Board> queue = new PriorityQueue<>(Comparator.comparingInt(Board::getTotalCost)); // Priority
                                                                                                        // queue for the
                                                                                                        // queue,
                                                                                                        // ordered by
                                                                                                        // the total
                                                                                                        // cost
        Map<Board, Integer> gScore = new HashMap<>(); // Map to store the g-scores (cost from start) for each node
        int count = 0;

        this.addNode(start);
        queue.add(start);
        gScore.put(start, 0);

        while (!queue.isEmpty()) {
            Board current = queue.poll();
            System.out.println("Board " + count);
            current.printBoard();

            if (current.equals(goal)) {
                System.out.println("Goal found");
                return reconstructPath(current);
            }

            visited.add(current);

            for (Board neighbor : current.getNeighbors()) {
                this.addNode(neighbor);
                this.addEdge(current, neighbor);

                int tentativeGScore = gScore.get(current) + 1;

                // Skip if the neighbor is already visited and has a higher g-score
                if (visited.contains(neighbor) && tentativeGScore >= gScore.get(neighbor)) {
                    continue;
                }

                // Update g-score and total cost if a better path is found
                if (!queue.contains(neighbor) || !gScore.containsKey(neighbor)
                        || tentativeGScore < gScore.get(neighbor)) {
                    gScore.put(neighbor, tentativeGScore);

                    int totalCost = tentativeGScore + heuristic(neighbor, goal, Heuristic.MANHATTAN);
                    neighbor.setTotalCost(totalCost);
                    neighbor.setCameFrom(current);

                    if (!queue.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
            count++;
        }

        System.out.println("Goal not found");
        return new ArrayList<>();
    }

    private List<Board> reconstructPath(Board current) {
        List<Board> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.getCameFrom();
        }
        Collections.reverse(path);
        return path;
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