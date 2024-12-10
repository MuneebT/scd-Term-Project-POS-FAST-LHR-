package View;

import View.CustomerElements.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BMOperation4bView extends JFrame {

    private ImageIcon backgroundImage;

    public BMOperation4bView() {
        // Load background image
        backgroundImage = new ImageIcon("src/resources/Splash.png"); // Ensure this image is in your project directory

        // Set up JFrame
        setTitle("User Selection");

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);


        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);


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




        RoundedLabel AddEmployeeLabel = new RoundedLabel(scaledOriginalIcon1,"  Delete Employee", Color.decode("#415a77"), 56, 56);
        RoundedLabel ManageEmployeeLabel = new RoundedLabel(scaledOriginalIcon2,"  Update Employee", Color.decode("#415a77"), 56, 56);


        setupLabel(AddEmployeeLabel, AddEmployeeIcon);
        setupLabel(ManageEmployeeLabel, ManageEmployeeIcon);

        AddEmployeeLabel.setSize(210, 50);
        ManageEmployeeLabel.setSize(210, 50);

        int centerX = (getWidth() - AddEmployeeLabel.getWidth()) / 2;
        int AddEmployeeLabelY = (getHeight() - AddEmployeeLabel.getHeight()) / 3;
        int ManageEmployeeLabelY = AddEmployeeLabelY + 70;

        AddEmployeeLabel.setLocation(centerX, AddEmployeeLabelY);
        ManageEmployeeLabel.setLocation(centerX, ManageEmployeeLabelY);



        AddEmployeeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new EmployeeTableView();
                dispose();
            }
        });

        ManageEmployeeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new EmployeeTableView();

                dispose();
            }
        });



        panel.add(AddEmployeeLabel);
        panel.add(ManageEmployeeLabel);


        add(panel);


        setVisible(true);
    }

    private void setupLabel(RoundedLabel label, ImageIcon icon) {
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        label.setForeground(Color.WHITE); // Set text color


        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));

        label.setHorizontalTextPosition(SwingConstants.RIGHT);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setIconTextGap(10);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BMOperation4bView());
    }
}
