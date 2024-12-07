package View;

import View.CustomerElements.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserSelectionView extends JFrame {

    private ImageIcon backgroundImage;

    public UserSelectionView() {
        // Load background image
        backgroundImage = new ImageIcon("src/resources/Splash.png"); // Ensure this image is in your project directory

        // Set up JFrame
        setTitle("User Selection");

        setSize(500, 400); // Adjust the frame size for better label placement
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

        // Load separate icons for SuperAdmin and Manager
        ImageIcon SuperAdminIcon = new ImageIcon("src/resources/employee.png");
        ImageIcon ManagerIcon = new ImageIcon("src/resources/customer.png");
        Image scaledImage1 = SuperAdminIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledOriginalIcon1 = new ImageIcon(scaledImage1);

        Image scaledImage2 = ManagerIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledOriginalIcon2 = new ImageIcon(scaledImage2);
        Image scaledImage3 = ManagerIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledOriginalIcon3 = new ImageIcon(scaledImage2);
        Image scaledImage4 = ManagerIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledOriginalIcon4 = new ImageIcon(scaledImage2);



        // Create labels with background color
        RoundedLabel SuperAdminLabel = new RoundedLabel(scaledOriginalIcon1,"  SuperAdmin", Color.decode("#415a77"), 56, 56);
        RoundedLabel ManagerLabel = new RoundedLabel(scaledOriginalIcon2,"  ManagerLabel", Color.decode("#415a77"), 56, 56);
        RoundedLabel DataEntryOperatorLabel = new RoundedLabel(scaledOriginalIcon2,"  Data Entry Operator", Color.decode("#415a77"), 56, 56);
        RoundedLabel CashierLabel = new RoundedLabel(scaledOriginalIcon2,"  Cashier", Color.decode("#415a77"), 56, 56);

        // Set different icons, font, and alignment for labels
        setupLabel(SuperAdminLabel, SuperAdminIcon);
        setupLabel(ManagerLabel, ManagerIcon);
        setupLabel(DataEntryOperatorLabel,ManagerIcon);
        setupLabel(CashierLabel,ManagerIcon);

        // Calculate positions for centering the labels
        SuperAdminLabel.setSize(210, 50); // Fixed label size
        ManagerLabel.setSize(210, 50);
        DataEntryOperatorLabel.setSize(210,50);
        CashierLabel.setSize(210,50);

        int centerX = (getWidth() - SuperAdminLabel.getWidth()) / 2; // Horizontal center
        int SuperAdminLabelY = (getHeight() - SuperAdminLabel.getHeight()) / 4; // One-third down from the top
        int ManagerLabelY = SuperAdminLabelY + 70; // Below the SuperAdmin label
        int DataEntryOperatorLabelY=ManagerLabelY+70;
        int CashierLabelY=DataEntryOperatorLabelY+70;


        SuperAdminLabel.setLocation(centerX, SuperAdminLabelY);
        ManagerLabel.setLocation(centerX, ManagerLabelY);
        DataEntryOperatorLabel.setLocation(centerX,DataEntryOperatorLabelY);
        CashierLabel.setLocation(centerX,CashierLabelY);


        // Add mouse click events to labels
        SuperAdminLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new loginView();
                dispose();
            }
        });

        ManagerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new loginView();
                dispose();
            }
        });
        DataEntryOperatorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new loginView();
                dispose();
            }
        });
        CashierLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new loginView();
                dispose();
            }
        });


        // Add labels to the panel
        panel.add(SuperAdminLabel);
        panel.add(ManagerLabel);
        panel.add(DataEntryOperatorLabel);
        panel.add(CashierLabel);

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
        label.setIcon(new ImageIcon(scaledImage)); // Add the scaled icon to the label

        label.setHorizontalTextPosition(SwingConstants.RIGHT); // Position the text to the right of the icon
        label.setHorizontalAlignment(SwingConstants.LEFT); // Align the label content to the left
        label.setIconTextGap(10); // Add space between the icon and text
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserSelectionView());
    }
}
