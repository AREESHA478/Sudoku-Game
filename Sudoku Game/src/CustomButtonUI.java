import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomButtonUI extends BasicButtonUI {
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set preferred size for consistent button dimensions
        button.setPreferredSize(new Dimension(120, 35));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create gradient colors
        Color startColor = new Color(0, 150, 200);
        Color endColor = new Color(0, 100, 150);

        if (button.getModel().isPressed()) {
            // Darker colors when pressed
            startColor = startColor.darker();
            endColor = endColor.darker();
        }

        // Create gradient paint
        GradientPaint gradient = new GradientPaint(
                0, 0, startColor,
                0, button.getHeight(), endColor
        );
        g2.setPaint(gradient);

        // Draw rounded rectangle background
        RoundRectangle2D.Float rect = new RoundRectangle2D.Float(
                0, 0,
                button.getWidth() - 1,
                button.getHeight() - 1,
                20, 20
        );
        g2.fill(rect);

        // Add subtle highlight at top for 3D effect
        g2.setPaint(new Color(255, 255, 255, 50));
        g2.fillRect(0, 0, button.getWidth(), button.getHeight() / 3);

        g2.dispose();

        // Paint the text
        super.paint(g, c);
    }
}

