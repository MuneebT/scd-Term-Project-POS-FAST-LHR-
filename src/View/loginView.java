package View;

import Controller.LoginController;
import View.CustomerElements.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;

public class loginView extends JFrame {
    LoginController loginController = new LoginController();

    public loginView() {
        // Setup frame
        setTitle("Login Page");
        setBounds(20, 20, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setBounds(249, 20, 300, 40);
        titleLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        Color customColor = Color.decode("#415a77");
        titleLabel.setForeground(customColor);

        ImageIcon originalIcon = new ImageIcon("src/resources/logo1.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        titleLabel.setIcon(scaledIcon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Background Image
        ImageIcon bk = new ImageIcon("src/resources/background1.jpg");
        Image scaledImage2 = bk.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        JLabel backgroundLabel = new JLabel(scaledIcon2);
        backgroundLabel.setBounds(0, 0, 800, 800);

        // Rounded Panel
        JPanel pt1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50);
                g2.setColor(getBackground());
                g2.fill(roundedRectangle);
                g2.dispose();
            }
        };
        pt1.setLayout(null);
        pt1.setBounds(120, 70, 550, 450);
        pt1.setOpaque(false);

        ImageIcon bk1 = new ImageIcon("src/resources/login1.png");
        Image scaledImage1 = bk1.getImage().getScaledInstance(270, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        JLabel backgroundLabel1 = new JLabel(scaledIcon1);
        backgroundLabel1.setBounds(0, 0, 270, 800);

        // User Name Field
        JLabel customerIdLabel = new JLabel("User Name");
        customerIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        customerIdLabel.setForeground(Color.BLACK);
        customerIdLabel.setBounds(439, 150, 100, 30);

        final JTextField customerIdField = new JTextField();
        customerIdField.setBounds(439, 180, 150, 30);
        customerIdField.setForeground(Color.GRAY);
        customerIdField.setText("Enter User Name:");

        // Password Field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(439, 210, 150, 30);

        final JTextField passwordField = new JTextField();
        passwordField.setBounds(439, 240, 150, 32);
        passwordField.setForeground(Color.GRAY);
        passwordField.setText("Enter Password");

        // Designation Dropdown
        JLabel designationlb = new JLabel("Designation:");
        designationlb.setBounds(439, 280, 150, 40);
        designationlb.setFont(new Font("Arial", Font.PLAIN, 16));
        designationlb.setForeground(Color.BLACK);

        String[] designationTypes = {"Super Admin", "Branch Manager", "Data Entry Operator", "Cashier"};
        JComboBox<String> designationTypeComboBox = new JComboBox<>(designationTypes);
        designationTypeComboBox.setBounds(439, 330, 150, 40);
        designationTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        designationTypeComboBox.setForeground(customColor);

        // Branch Field
        JLabel branchLabel = new JLabel("Branch");
        branchLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        branchLabel.setForeground(Color.BLACK);
        branchLabel.setBounds(439, 370, 150, 30);

        final JTextField branchField = new JTextField();
        branchField.setBounds(439, 400, 150, 32);
        branchField.setForeground(Color.GRAY);
        branchField.setText("Enter Branch");

        // Error Label
        final JLabel errorLabel = new JLabel("Wrong Credentials");
        errorLabel.setBounds(413, 120, 350, 30);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        // Focus Listeners
        customerIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (customerIdField.getText().equals("Enter User Name:")) {
                    customerIdField.setText("");
                    customerIdField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (customerIdField.getText().isEmpty()) {
                    customerIdField.setText("Enter User Name:");
                    customerIdField.setForeground(Color.GRAY);
                }
            }
        });

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals("Enter Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().isEmpty()) {
                    passwordField.setText("Enter Password");
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        branchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (branchField.getText().equals("Enter Branch")) {
                    branchField.setText("");
                    branchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (branchField.getText().isEmpty()) {
                    branchField.setText("Enter Branch");
                    branchField.setForeground(Color.GRAY);
                }
            }
        });
        designationTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDesignation = (String) designationTypeComboBox.getSelectedItem();
                if ("Super Admin".equals(selectedDesignation)) {
                    branchField.setEnabled(false); // Disable Branch Field
                    branchField.setText("N/A");   // Set default text
                } else {
                    branchField.setEnabled(true); // Enable Branch Field
                    branchField.setText("");      // Clear the field
                }
            }
        });
        if ("Super Admin".equals(designationTypeComboBox.getSelectedItem())) {
            branchField.setEnabled(false); // Disable if Super Admin is selected by default
            branchField.setText("N/A");
        } else {
            branchField.setEnabled(true); // Enable for other roles
        }

        // Buttons
        RoundedButton submitBtn = new RoundedButton("Submit");
        submitBtn.setBounds(416, 390, 110, 32);
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = customerIdField.getText();
                String password = passwordField.getText();
                String designation = (String) designationTypeComboBox.getSelectedItem();
                String branch = branchField.getText();

                try {
                    if (loginController.redirect_validateUser(userName, password, designation,branch)) {
                        loginController.redirect_set_credientials(userName, password, designation,branch);
                        if (designation.equals("Super Admin")) {
                            new SADashboardView();
                        } else if (designation.equals("Branch Manager")) {
                            new BMDashboardView();
                        } else if (designation.equals("Data Entry Operator")) {
                            new DEODashboardView();
                        } else if (designation.equals("Cashier")) {
                            new CashierDashboard();
                        }
                        dispose();
                    } else {
                        errorLabel.setVisible(true);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        submitBtn.setBackground(customColor);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Impact", Font.PLAIN, 16));
        submitBtn.setVisible(true);

        RoundedButton backBtn = new RoundedButton("Back");
        backBtn.setBounds(295, 390, 110, 32);
        backBtn.addActionListener(e -> {
            dispose();
            new UserSelectionView();
        });

        backBtn.setBackground(customColor);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Impact", Font.PLAIN, 16));
        backBtn.setVisible(true);

        // Add components to the panel
        mainPanel.add(customerIdLabel);
        mainPanel.add(customerIdField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(designationlb);
        mainPanel.add(designationTypeComboBox);
        mainPanel.add(branchLabel);
        mainPanel.add(branchField);
        mainPanel.add(errorLabel);
        pt1.add(backBtn);
        pt1.add(submitBtn);
        pt1.add(backgroundLabel1);
        mainPanel.add(pt1);
        pt1.add(titleLabel);
        mainPanel.add(backgroundLabel);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new loginView();
    }
}