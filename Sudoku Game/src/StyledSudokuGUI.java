import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StyledSudokuGUI {

    // Main frame and components
    private JFrame frame;
    private JButton newGameButton;

    public StyledSudokuGUI() {
        // Initialize the frame
        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        // Set custom layout and background
        frame.setContentPane(new CustomBackground());
        frame.setLayout(new BorderLayout());

        // Add panel with heading and buttons
        JPanel mainPanel = createMainPanel();
        frame.add(mainPanel, BorderLayout.CENTER);

        // Set frame visibility
        frame.setVisible(true);
    }

    // Create main panel with heading and buttons
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false); // Transparent to show the background
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for proper positioning

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 20, 0); // Add spacing between components
        gbc.gridx = 0;

        // Add heading
        JLabel heading = createHeading();
        gbc.gridy = 0; // Row 0 for heading
        panel.add(heading, gbc);

        // Add "New Game" button
        newGameButton = createStyledButton("Start Game");
        gbc.gridy = 1; // Row 1 for New Game button
        panel.add(newGameButton, gbc);

        // Add action listener to New Game button
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                SwingUtilities.invokeLater(() -> new SudokuGameGUI().setVisible(true));
            }
        });

        return panel;
    }

    // Create heading JLabel with custom font and styling
    private JLabel createHeading() {
        JLabel heading = new JLabel("Let's Play Sudoku", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36)); // Custom font
        heading.setForeground(Color.BLACK); // Change the heading color to black
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add padding
        return heading;
    }

    // Create a styled button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 150, 200), 0, getHeight(), new Color(0, 100, 150));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); // Rounded rectangle

                // Draw border
                g2d.setColor(new Color(0, 100, 150));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);

                // Draw text
                g2d.setFont(getFont());
                g2d.setColor(Color.WHITE);
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getHeight();
                g2d.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight / 2) / 2);

                super.paintComponent(g2d);
            }

            @Override
            public void paintBorder(Graphics g) {
                // Do nothing (border is custom drawn)
            }
        };

        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(150, 60)); // Button size
        button.setContentAreaFilled(false); // Remove default button fill
        button.setFocusPainted(false); // Remove focus outline
        return button;
    }

    // Custom JPanel for background painting
    private class CustomBackground extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Draw gradient background
            GradientPaint gradient = new GradientPaint(0, 0, new Color(176, 224, 230),
                    getWidth(), getHeight(), new Color(240, 248, 255));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Add decorative elements
            g2d.setColor(new Color(255, 250, 250, 150)); // Soft white for overlay
            g2d.fillOval(-50, -50, 300, 300); // Top-left decoration
            g2d.fillOval(getWidth() - 250, getHeight() - 250, 300, 300); // Bottom-right decoration
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StyledSudokuGUI::new);
    }
}

