import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class SudokuGameGUI extends JFrame {
    private int gridSize = 6; // Default to 6x6 grid
    private JTextField[][] cells;
    private int[][] puzzle;
    private int[][] solution;
    private JPanel gridPanel;
    private Stack<Change> undoStack;
    private boolean[][] solutionFilledCells;
    private JLabel timerLabel;
    private JLabel bestTimeLabel;
    private JLabel timeComparisonLabel;
    private Timer timer;
    private int secondsElapsed;
    private int bestTime = Integer.MAX_VALUE;

    public SudokuGameGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Sudoku Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        undoStack = new Stack<>();
        solutionFilledCells = new boolean[gridSize][gridSize];

        gridPanel = new JPanel();
        add(gridPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton checkButton = new JButton("Show Solution");
        JButton hintButton = new JButton("Hint");
        JButton gridButton = new JButton("Grid");
        JButton undoButton = new JButton("Undo");
        JButton restartButton = new JButton("Restart");
        JButton clearButton = new JButton("Clear Solution");

        JPanel timerPanel = new JPanel(new GridLayout(3, 1));
        timerPanel.setBackground(new Color(240, 240, 240));
        timerLabel = new JLabel("Time: 00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerPanel.add(timerLabel);

        bestTimeLabel = new JLabel("Best: --:--");
        bestTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerPanel.add(bestTimeLabel);


        add(timerPanel, BorderLayout.NORTH);

        ButtonListeners listeners = new ButtonListeners(this);
        checkButton.addActionListener(listeners.new ShowSolutionListener());
        hintButton.addActionListener(listeners.new HintButtonListener());
        gridButton.addActionListener(listeners.new GridButtonListener());
        undoButton.addActionListener(listeners.new UndoButtonListener());
        restartButton.addActionListener(listeners.new RestartButtonListener());
        clearButton.addActionListener(listeners.new ClearSolutionListener());

        checkButton.setUI(new CustomButtonUI());
        hintButton.setUI(new CustomButtonUI());
        gridButton.setUI(new CustomButtonUI());
        undoButton.setUI(new CustomButtonUI());
        restartButton.setUI(new CustomButtonUI());
        clearButton.setUI(new CustomButtonUI());

        buttonPanel.add(checkButton);
        buttonPanel.add(hintButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(gridButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        PuzzleManager.loadPuzzle(this);
        BoardInitializer.initializeBoard(this);

        initializeTimer();
    }

    private void initializeTimer() {
        secondsElapsed = 0;
        timer = new Timer(1000, e -> {
            secondsElapsed++;
            updateTimerLabel();
            updateTimeComparison();
        });
        timer.start();
    }

    private void updateTimerLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    private void updateTimeComparison() {
        if (bestTime != Integer.MAX_VALUE) {
            int difference = secondsElapsed - bestTime;
            String comparisonText;
            if (difference < 0) {
                comparisonText = String.format("-%02d:%02d", Math.abs(difference) / 60, Math.abs(difference) % 60);
            } else {
                comparisonText = String.format("+%02d:%02d", difference / 60, difference % 60);
            }
            timeComparisonLabel.setText("Comparison: " + comparisonText);
        }
    }

    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    public void resetTimer() {
        stopTimer();
        secondsElapsed = 0;
        updateTimerLabel();
        updateTimeComparison();
        timer.start();
    }

    public void updateBestTime() {
        if (secondsElapsed < bestTime) {
            bestTime = secondsElapsed;
            int minutes = bestTime / 60;
            int seconds = bestTime % 60;
            bestTimeLabel.setText(String.format("Best: %02d:%02d", minutes, seconds));
            updateTimeComparison();
        }
    }

    // Getter and setter methods
    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int size) {
        this.gridSize = size;
        solutionFilledCells = new boolean[size][size];
    }

    public JTextField[][] getCells() {
        return cells;
    }

    public void setCells(JTextField[][] cells) {
        this.cells = cells;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(int[][] puzzle) {
        this.puzzle = puzzle;
    }

    public int[][] getSolution() {
        return solution;
    }

    public void setSolution(int[][] solution) {
        this.solution = solution;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public Stack<Change> getUndoStack() {
        return undoStack;
    }

    public boolean[][] getSolutionFilledCells() {
        return solutionFilledCells;
    }

    public void setSolutionFilledCells(boolean[][] solutionFilledCells) {
        this.solutionFilledCells = solutionFilledCells;
    }

    public int getSecondsElapsed() {
        return secondsElapsed;
    }


    public boolean isPuzzleSolved() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (puzzle[row][col] != solution[row][col]) {
                    return false;
                }
            }
        }
        updateBestTime();
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StyledSudokuGUI(); // Start with the styled GUI
        });
    }
}

