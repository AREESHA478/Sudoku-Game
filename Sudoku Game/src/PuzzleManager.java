public class PuzzleManager {
    public static void loadPuzzle(SudokuGameGUI gui) {
        int gridSize = gui.getGridSize();
        int[][] puzzle, solution;

        if (gridSize == 3) {
            puzzle = new int[][]{
                    {5, 0, 0},
                    {0, 7, 0},
                    {0, 0, 2}
            };
            solution = new int[][]{
                    {5, 3, 4},
                    {6, 7, 8},
                    {9, 1, 2}
            };
        } else if (gridSize == 4) {
            puzzle = new int[][]{
                    {1, 0, 0, 4},
                    {0, 2, 0, 0},
                    {0, 0, 3, 0},
                    {3, 0, 0, 2}
            };
            solution = new int[][]{
                    {1, 3, 2, 4},
                    {4, 2, 1, 3},
                    {2, 4, 3, 1},
                    {3, 1, 4, 2}
            };
        } else if (gridSize == 5) {
            puzzle = new int[][]{
                    {0, 4, 0, 0, 5},
                    {0, 0, 3, 2, 0},
                    {5, 0, 0, 0, 0},
                    {0, 2, 0, 0, 0},
                    {3, 0, 0, 5, 0}
            };
            solution = new int[][]{
                    {2, 4, 1, 3, 5},
                    {1, 5, 3, 2, 4},
                    {5, 3, 2, 4, 1},
                    {4, 2, 5, 1, 3},
                    {3, 1, 4, 5, 2}
            };
        } else {
            puzzle = new int[][]{
                    {0, 1, 0, 0, 4, 6},
                    {0, 0, 6, 3, 0, 1},
                    {6, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 3},
                    {5, 0, 4, 1, 0, 0},
                    {3, 4, 0, 0, 1, 0}
            };
            solution = new int[][]{
                    {2, 1, 3, 5, 4, 6},
                    {4, 5, 6, 3, 2, 1},
                    {6, 3, 1, 2, 5, 4},
                    {1, 2, 5, 4, 6, 3},
                    {5, 6, 4, 1, 3, 2},
                    {3, 4, 2, 6, 1, 5}
            };
        }

        gui.setPuzzle(puzzle);
        gui.setSolution(solution);
    }
}
