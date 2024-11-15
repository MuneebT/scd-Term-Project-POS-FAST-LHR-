package View;

import View.CustomerElements.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BMOperation4View extends JFrame {

    private ImageIcon backgroundImage;

    public BMOperation4View() {
        // Load background image
        backgroundImage = new ImageIcon("src/resources/background.jpg"); // Ensure this image is in your project directory

        // Set up JFrame
        setTitle("User Selection");

        setSize(500, 300); // Adjust the frame size for better label placement
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setUndecorated(true);  // Remove title bar and borders

        // Create a JPanel with background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image, covering the entire panel
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // Allow absolute positioning

        // Load separate icons for AddEmployee and ManageEmployee
        ImageIcon AddEmployeeIcon = new ImageIcon("src/resources/employee.png");
        ImageIcon ManageEmployeeIcon = new ImageIcon("src/resources/customer.png");
        Image scaledImage1 = AddEmployeeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledOriginalIcon1 = new ImageIcon(scaledImage1);

        Image scaledImage2 = ManageEmployeeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledOriginalIcon2 = new ImageIcon(scaledImage2);
        Image scaledImage3 = ManageEmployeeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledOriginalIcon3 = new ImageIcon(scaledImage2);
        Image scaledImage4 = ManageEmployeeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledOriginalIcon4 = new ImageIcon(scaledImage2);



        // Create labels with background color
        RoundedLabel AddEmployeeLabel = new RoundedLabel(scaledOriginalIcon1,"  Add Employee", new Color(121, 87, 87), 56, 56);
        RoundedLabel ManageEmployeeLabel = new RoundedLabel(scaledOriginalIcon2,"  Manage Employee", new Color(121, 87, 87), 56, 56);

        // Set different icons, font, and alignment for labels
        setupLabel(AddEmployeeLabel, AddEmployeeIcon);
        setupLabel(ManageEmployeeLabel, ManageEmployeeIcon);

        // Calculate positions for centering the labels
        AddEmployeeLabel.setSize(210, 50); // Fixed label size
        ManageEmployeeLabel.setSize(210, 50);

        int centerX = (getWidth() - AddEmployeeLabel.getWidth()) / 2; // Horizontal center
        int AddEmployeeLabelY = (getHeight() - AddEmployeeLabel.getHeight()) / 3; // One-third down from the top
        int ManageEmployeeLabelY = AddEmployeeLabelY + 70; // Below the AddEmployee label

        AddEmployeeLabel.setLocation(centerX, AddEmployeeLabelY);
        ManageEmployeeLabel.setLocation(centerX, ManageEmployeeLabelY);


        // Add mouse click events to labels
        AddEmployeeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new loginView();
                dispose();
            }
        });

        ManageEmployeeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new loginView();
                dispose();
            }
        });


        // Add labels to the panel
        panel.add(AddEmployeeLabel);
        panel.add(ManageEmployeeLabel);

        // Add panel to the frame
        add(panel);

        // Show the frame
        setVisible(true);
    }

    // Utility method to setup the label with the icon and styling
    private void setupLabel(RoundedLabel label, ImageIcon icon) {
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        label.setForeground(Color.WHITE); // Set text color

        // Scale the icon to fit within the label size
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Adjust 40x40 to your preferred size
        label.setIcon(new ImageIcon(scaledImage)); // Add the scaled icon to the label with the help of yself for the sake of commit

        label.setHorizontalTextPosition(SwingConstants.RIGHT); // Position the text to the right of the icon
        label.setHorizontalAlignment(SwingConstants.LEFT); // Align the label content to the left
        label.setIconTextGap(10); // Add space between the icon and text
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BMOperation4View());
    }
}
