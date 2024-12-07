package View;

import javax.swing.*;
import java.awt.*;

public class splashView extends JFrame {

    private JProgressBar progressBar;
    private ImageIcon backgroundImage;

    public splashView() {
        // Load background image
        backgroundImage = new ImageIcon("src/resources/Splash.png"); // Ensure this image is in your project directory

        // Set up JFrame
        setTitle("Splash Screen");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);  // Remove title bar and borders

        // Create JPanel with background image and progress bar
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // Set layout to null for absolute positioning


        // Add "Welcome to" label
        JLabel welcomeLabel = new JLabel("Welcome to");
        welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 24)); // Set font and size
        welcomeLabel.setForeground(Color.decode("#415a77")); // Set text color (e.g., white)//#415a77
        welcomeLabel.setBounds(120, 50, 200, 30); // Adjust position (x, y, width, height)
        welcomeLabel.setOpaque(false); // Transparent background
        panel.add(welcomeLabel);

        // Add "QuickBill" label with an icon
        JLabel titleLabel = new JLabel("QuickBill");
        titleLabel.setFont(new Font("Impact", Font.PLAIN, 32)); // Set a larger, bold font
        titleLabel.setForeground(Color.decode("#415a77")); // Set custom color for the text
        titleLabel.setBounds(125, 90, 200, 40); // Adjust position and size

        // Load and scale the icon image
        ImageIcon originalIcon = new ImageIcon("src/resources/logo2.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Larger size for better visibility
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        titleLabel.setIcon(scaledIcon); // Add the icon to the label
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT); // Position the text to the left of the icon
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the entire label
        titleLabel.setIconTextGap(10); // Add some space between the text and the icon
        panel.add(titleLabel);  // Create a progress bar




        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setString(null); // Remove any text from the progress bar
        progressBar.setStringPainted(false); // Do not display the percentage
        progressBar.setForeground(Color.decode("#fff0f3")); // Set progress bar color
        progressBar.setBackground(Color.BLACK); // Set background color
        progressBar.setBorderPainted(false);

        // Set progress bar size and position
        progressBar.setBounds(0, 296, 500, 9); // x, y, width, height
        panel.add(progressBar);

        // Add loading message under progress bar
        JLabel loadingLabel = new JLabel("Loading, please wait...");
        loadingLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font and size
        loadingLabel.setForeground(Color.decode("#415a77")); // Set text color
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingLabel.setBounds(100, 270, 300, 20); // Position it right under the progress bar
        panel.add(loadingLabel);

        // Add panel to the frame
        add(panel);

        // Show the frame
        setVisible(true);

        // Simulate progress
        simulateProgress();
    }

    // Simulating progress
    private void simulateProgress() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(35); // Simulate work
                    progressBar.setValue(i);
                }
                return null;
            }

            @Override
            protected void done() {
                new UserSelectionView(); // Proceed to the next screen
                dispose();
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new splashView());
    }
}
