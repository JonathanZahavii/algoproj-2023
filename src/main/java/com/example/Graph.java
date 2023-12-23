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
}