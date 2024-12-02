import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardInitializer {
    public static void initializeBoard(SudokuGameGUI gui) {
        JPanel gridPanel = gui.getGridPanel();
        int gridSize = gui.getGridSize();
        int[][] puzzle = gui.getPuzzle();
        int[][] solution = gui.getSolution();

        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(gridSize, gridSize, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gridPanel.setBackground(new Color(4, 156, 156, 223));

        JTextField[][] cells = new JTextField[gridSize][gridSize];
        int blockSize = (int) Math.sqrt(gridSize);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                final int finalRow = row; // Make row final
                final int finalCol = col; // Make col final
                cells[row][col] = new JTextField() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        if (!isOpaque()) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(getBackground());
                            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                            g2.dispose();
                        }
                        // Draw thick lines for the third row and column
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setColor(Color.BLACK);
                        g2.setStroke(new BasicStroke(4)); // Set the stroke to thick

                        // Check if this is the third row (index 2)
                        if (finalRow == 2) {
                            // Draw horizontal thick line
                            g2.drawLine(0, getHeight(), getWidth(), getHeight());
                        }

                        // Check if this is the third column (index 2)
                        if (finalCol == 2) {
                            // Draw vertical thick line
                            g2.drawLine(getWidth(), 0, getWidth(), getHeight());
                        }

                        g2.dispose();

                        super.paintComponent(g);
                    }
                };
                cells[row][col].setOpaque(false);
                cells[row][col].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 30));

                if (puzzle[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(puzzle[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(new Color(50, 205, 205, 255));
                    cells[row][col].setForeground(Color.BLACK);
                } else {
                    cells[row][col].setBackground(Color.WHITE);
                    cells[row][col].setForeground(Color.BLACK);
                    cells[row][col].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            JTextField cell = (JTextField) e.getSource();
                            String text = cell.getText();
                            int rowIndex = -1, colIndex = -1;
                            for (int r = 0; r < gridSize; r++) {
                                for (int c = 0; c < gridSize; c++) {
                                    if (cells[r][c] == cell) {
                                        rowIndex = r;
                                        colIndex = c;
                                        break;
                                    }
                                }
                            }
                            if (rowIndex != -1 && colIndex != -1 && !text.isEmpty()) {
                                int userAnswer = 0;
                                if (Character.isDigit(text.charAt(0))) {
                                    userAnswer = Integer.parseInt(text);
                                    if (puzzle[rowIndex][colIndex] != userAnswer) {
                                        gui.getUndoStack().push(new Change(rowIndex, colIndex, puzzle[rowIndex][colIndex]));
                                        puzzle[rowIndex][colIndex] = userAnswer;
                                    }
                                    if (userAnswer == solution[rowIndex][colIndex]) {
                                        cell.setForeground(new Color(0, 0, 255));
                                    } else {
                                        cell.setForeground(new Color(255, 0, 0));
                                    }
                                    if (gui.isPuzzleSolved()) {
                                        gui.stopTimer();
                                        int timeElapsed = gui.getSecondsElapsed();
                                        SwingUtilities.invokeLater(() -> {
                                            JOptionPane.showMessageDialog(gui,
                                                    "Congratulations! You've solved the puzzle!\n" +
                                                            "Time taken: " + formatTime(timeElapsed),
                                                    "Victory",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                        });
                                    }
                                }
                            }
                        }
                    });
                }
                gridPanel.add(cells[row][col]);
            }
        }
        gui.setCells(cells);
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private static String formatTime(int seconds) {



        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}