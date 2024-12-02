import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class ButtonListeners {
    private SudokuGameGUI gui;

    public ButtonListeners(SudokuGameGUI gui) {
        this.gui = gui;
    }

    public class ShowSolutionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField[][] cells = gui.getCells();
            int[][] puzzle = gui.getPuzzle();
            int[][] solution = gui.getSolution();
            int gridSize = gui.getGridSize();

            boolean[][] solutionFilledCells = gui.getSolutionFilledCells();

            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    if (puzzle[row][col] == 0) {
                        cells[row][col].setText(String.valueOf(solution[row][col]));
                        cells[row][col].setEditable(false);
                        // Removed the line that changes the background color
                        cells[row][col].setForeground(Color.BLUE);
                        solutionFilledCells[row][col] = true; // Mark cell as filled by "Show Solution"
                    }
                }
            }
            gui.stopTimer(); // Stop the timer when showing solution
            int timeElapsed = gui.getSecondsElapsed();
            JOptionPane.showMessageDialog(gui, "Here is the solution!");
        }


        private String formatTime(int seconds) {
            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;
            return String.format("%02d:%02d", minutes, remainingSeconds);
        }
    }



    public class ClearSolutionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField[][] cells = gui.getCells();
            int gridSize = gui.getGridSize();

            boolean[][] solutionFilledCells = gui.getSolutionFilledCells();

            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    if (solutionFilledCells[row][col]) {
                        cells[row][col].setText("");
                        cells[row][col].setEditable(true);
                        cells[row][col].setBackground(Color.WHITE);
                        cells[row][col].setForeground(Color.BLACK);
                        solutionFilledCells[row][col] = false; // Clear the tracking for this cell
                    }
                }
            }
            JOptionPane.showMessageDialog(gui, "Solution cells have been cleared!");
        }
    }



    public class HintButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField[][] cells = gui.getCells();
            int[][] puzzle = gui.getPuzzle();
            int[][] solution = gui.getSolution();
            int gridSize = gui.getGridSize();

            ArrayList<int[]> emptyCells = new ArrayList<>();
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    if (puzzle[row][col] == 0 && cells[row][col].isEditable()) {
                        emptyCells.add(new int[]{row, col});
                    }
                }
            }
            if (!emptyCells.isEmpty()) {
                Random rand = new Random();
                int[] cell = emptyCells.get(rand.nextInt(emptyCells.size()));
                int row = cell[0], col = cell[1];
                cells[row][col].setText(String.valueOf(solution[row][col]));
                cells[row][col].setEditable(false);
                cells[row][col].setForeground(Color.BLUE);
            } else {
                JOptionPane.showMessageDialog(gui, "No hints available!");
            }
        }
    }

    public class GridButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] options = {"3x3", "4x4", "5x5", "6x6"};
            String choice = (String) JOptionPane.showInputDialog(gui, "Select Grid Size", "Grid Size",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice != null) {
                switch (choice) {
                    case "3x3":
                        gui.setGridSize(3);
                        break;
                    case "4x4":
                        gui.setGridSize(4);
                        break;
                    case "5x5":
                        gui.setGridSize(5);
                        break;
                    case "6x6":
                        gui.setGridSize(6);
                        break;
                }
                PuzzleManager.loadPuzzle(gui);
                BoardInitializer.initializeBoard(gui);

                gui.setSolutionFilledCells(new boolean[gui.getGridSize()][gui.getGridSize()]);

            }
        }
    }

    public class UndoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gui.getUndoStack().isEmpty()) {
                Change lastChange = gui.getUndoStack().pop();
                int row = lastChange.getRow();
                int col = lastChange.getCol();
                int previousValue = lastChange.getPreviousValue();

                gui.getPuzzle()[row][col] = previousValue;
                gui.getCells()[row][col].setText(previousValue == 0 ? "" : String.valueOf(previousValue));
            } else {
                JOptionPane.showMessageDialog(gui, "No actions to undo!");
            }
        }
    }

    public class RestartButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PuzzleManager.loadPuzzle(gui);
            BoardInitializer.initializeBoard(gui);
            gui.setSolutionFilledCells(new boolean[gui.getGridSize()][gui.getGridSize()]);
            gui.resetTimer(); // Reset the timer when restarting
        }
    }
}