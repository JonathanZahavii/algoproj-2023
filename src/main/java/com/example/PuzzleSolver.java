package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class PuzzleSolver {
    public static InspectAnswer BFS(Graph graph, Board start, Board goal) {
        double startTime = System.nanoTime();
        List<Board> visited = new ArrayList<>();
        Queue<Board> queue = new LinkedList<Board>();
        boolean isSolved = false;

        graph.addNode(start);
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
                for (Board neighbor : graph.getNeighbors(current)) {
                    graph.addNode(neighbor);
                    graph.addEdge(current, neighbor);
                    neighbor.setCameFrom(current);
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        double runTime = Utillity.getRunTimeInSeconds(startTime);
        Path path = Path.reconstructPath(current);
        return new InspectAnswer(runTime, graph.getNodeCount(), path, isSolved);
    }

    public static InspectAnswer AStar(Graph graph, Board start, Board goal, Heuristic heuristicType) {
        double startTime = System.nanoTime();
        boolean isSolved = false;
        List<Board> visited = new ArrayList<>();
        Map<Board, Integer> gScore = new HashMap<>(); // Map to store the g-scores (cost from start) for each node
        PriorityQueue<Board> queue = new PriorityQueue<>(Comparator.comparingInt(Board::getTotalCost)); // Priority
                                                                                                        // queue for the
                                                                                                        // queue,
                                                                                                        // ordered by
                                                                                                        // the total
                                                                                                        // cost
        graph.addNode(start);
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

            for (Board neighbor : graph.getNeighbors(current)) {
                graph.addNode(neighbor);
                graph.addEdge(current, neighbor);
                int tentativeGScore = gScore.get(current) + 1;

                // Skip if the neighbor is already visited and has a higher g-score
                if (visited.contains(neighbor)
                        && (gScore.get(neighbor) != null && tentativeGScore >= gScore.get(neighbor)))
                    continue;

                // Update g-score and total cost if a better path is found
                if (!queue.contains(neighbor) || !gScore.containsKey(neighbor)
                        || tentativeGScore < gScore.get(neighbor)) {
                    gScore.put(neighbor, tentativeGScore);

                    int totalCost = tentativeGScore + heuristicType.heuristic(neighbor, goal);
                    neighbor.setTotalCost(totalCost);
                    neighbor.setCameFrom(current);

                    if (!queue.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        double runTime = Utillity.getRunTimeInSeconds(startTime);
        Path path = Path.reconstructPath(current);
        return new InspectAnswer(runTime, graph.getNodeCount(), path, isSolved);
    }
}
