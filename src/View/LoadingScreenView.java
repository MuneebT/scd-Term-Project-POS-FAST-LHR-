package View;

import javax.swing.*;
import java.awt.*;

public class LoadingScreenView extends JFrame {
    private JProgressBar pbload;
    private JLabel msg;

    public LoadingScreenView() {
        // Set frame properties
        setTitle("Loading");
        setResizable(false);
        setVisible(true);
        setBounds(100, 100, 500, 300);  // Adjusted frame size (height reduced for better fit)
        setLayout(null);  // Use null layout manager

        // Label message
        msg = new JLabel("Processing Your Request", SwingConstants.CENTER);
        msg.setForeground(Color.BLACK);
        msg.setFont(new Font("Arial", Font.BOLD, 15));
        msg.setBounds(100, 50, 300, 30);  // Positioning label slightly above the progress bar

        // Progress bar
        pbload = new JProgressBar(0, 100);
        pbload.setValue(0);
        pbload.setForeground(Color.BLACK);
        pbload.setStringPainted(true);
        pbload.setBounds(100, 100, 300, 30);  // Position and size of the progress bar

        // Add components to the frame
        add(msg);
        add(pbload);

        // Create a new thread to simulate loading progress
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                pbload.setValue(i);
                try {
                    Thread.sleep(50);  // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Optionally, hide the loading screen or proceed after the progress reaches 100%
            // You could call dispose() or change the view to a new one here
        }).start();
        dispose();
    }

    public static void main(String[] args) {
        new LoadingScreenView();
    }


}
