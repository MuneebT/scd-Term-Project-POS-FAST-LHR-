package View.CustomerElements;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private static final int ARC_WIDTH = 30;
    private static final int ARC_HEIGHT = 30;

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false); // Remove focus painting
        setContentAreaFilled(false); // Remove default background fill
        setBorderPainted(false); // Remove button border
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the button background with rounded corners
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        // Call super to ensure the text is painted
        super.paintComponent(g);

        // Dispose of the graphics context to free resources
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Optionally, you can draw a border for the button here if needed
        // You can also override this method to have no border
    }
}
