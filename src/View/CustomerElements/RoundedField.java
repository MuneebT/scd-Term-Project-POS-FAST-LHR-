package View.CustomerElements;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedField extends JTextField {
    private static final int ARC_WIDTH = 30;
    private static final int ARC_HEIGHT = 30;

    public RoundedField(int columns) {
        super(columns);
        setOpaque(false); // Makes sure the background is drawn by the component itself
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the text field background with rounded corners
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        // Set the clipping area to ensure the text stays within rounded bounds
        g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT));

        // Call the parent method to draw the text correctly
        super.paintComponent(g2d);

        // Dispose of the graphics context to free resources
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Optionally, customize the border drawing here
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw a border around the text field with rounded corners
        g2d.setColor(getForeground());
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT);

        g2d.dispose();
    }

    @Override
    public Insets getInsets() {
        // Adjust insets to ensure proper padding for the text inside the rounded area
        return new Insets(5, 15, 5, 15);
    }
}
