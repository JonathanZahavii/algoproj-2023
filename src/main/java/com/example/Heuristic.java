package com.example;

public enum Heuristic {
    MANHATTAN {
        @Override
        public int heuristic(Board current, Board goal) {
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
    },
    DIJAKSTRA {
        @Override
        public int heuristic(Board current, Board goal) {
            return 0;
        }
    },
    NONADMISSIBLE {
        @Override
        public int heuristic(Board current, Board goal) {
            // Non-admissible heuristic always returns a constant value of 5
            return 5;
        }
    };

    public abstract int heuristic(Board current, Board goal);
}