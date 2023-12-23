package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    List<Board> path;
    int moves;

    public Path(){
        this.path = new ArrayList<>();
        this.moves = 0;
    }

    public List<Board> getPath(){
        return path;
    }

    public int getMoves(){
        return path.size() - 1;
    }

    public static Path reconstructPath(Board current) {
        Path path = new Path();
        while (current != null) {
            path.getPath().add(current);
            current = current.getCameFrom();
        }
        Collections.reverse(path.getPath());
        return path;
    }
}



