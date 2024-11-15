package View.CustomerElements;

import javax.swing.*;
import java.awt.*;

public class RoundedLabel extends JLabel {
    private Color backgroundColor;
    private int arcWidth;
    private int arcHeight;
    private ImageIcon icon;

    // Constructor for text-based label
    public RoundedLabel(String text, Color bgColor, int arcWidth, int arcHeight) {
        super(text);
        this.backgroundColor = bgColor;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.icon = null; // No icon set
        setOpaque(false); // Make sure we don't use the default background rendering
    }

    // Overloaded constructor for icon and text-based label
    public RoundedLabel(ImageIcon icon, String text, Color bgColor, int arcWidth, int arcHeight) {
        super(text); // Sets the label text
        this.icon = icon;
        this.backgroundColor = bgColor;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setOpaque(false); // Make sure we don't use the default background rendering
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded rectangle with background color
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        // Draw icon if set, then text after a gap
        int textX = 0;
        if (icon != null) {
            // Calculate position to center the icon vertically and align it to the left
            int iconY = (getHeight() - icon.getIconHeight()) / 2;
            int iconX = 10; // Add some padding from the left edge
            icon.paintIcon(this, g2, iconX, iconY);
            textX = iconX + icon.getIconWidth() + 5; // Set text position with a gap after the icon
        }

        // Draw text if present
        String labelText = getText();
        if (labelText != null) {
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics();
            int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2; // Center text vertically
            g2.drawString(labelText, textX, textY);
        }

        g2.dispose();
    }
}
