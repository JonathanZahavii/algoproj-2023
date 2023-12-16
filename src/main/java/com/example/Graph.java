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
        List<Board> queue = new ArrayList<>();

        queue.addLast(start);
        this.addNode(start);

        while (!queue.isEmpty()) {
            Board node = queue.getFirst();
            queue.removeFirst();
            node.printBoard();
            if (node.equals(goal)) {
                System.out.println("Goal found");
                visited.add(node);
                return visited;
            }

            if (!visited.contains(node)) {
                visited.add(node);
                List<Board> neighbors = node.getNeighbors();
                for (Board neighbor : neighbors) {
                    this.addNode(neighbor);
                    this.addEdge(node, neighbor);
                    if (!visited.contains(neighbor)) {
                        queue.addLast(neighbor);
                    }
                }
            }
        }

        System.out.println("Goal not found");
        return visited;
    }


    
}