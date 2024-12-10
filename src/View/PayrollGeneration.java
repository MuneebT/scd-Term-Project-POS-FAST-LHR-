package View;

import Controller.PayController;
import View.CustomerElements.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.math.BigDecimal;
import java.sql.SQLException;

    class PayrollGeneration extends JFrame {
       // LoginController loginController = new LoginController();
        PayController payController=new PayController();

        public PayrollGeneration(String BMbranch) {
            // Setup frame
            setTitle("Payroll Generation");
            setBounds(20, 20, 800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(null);

            // Title Label
            JLabel titleLabel = new JLabel("Payroll Generation");
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

            ImageIcon bk1 = new ImageIcon("src/resources/money1.jpg");
            Image scaledImage1 = bk1.getImage().getScaledInstance(290, 800, Image.SCALE_SMOOTH);
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
            JLabel passwordLabel = new JLabel("Employee ID");
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            passwordLabel.setForeground(Color.BLACK);
            passwordLabel.setBounds(439, 210, 150, 30);

            final JTextField passwordField = new JTextField();
            passwordField.setBounds(439, 240, 150, 32);
            passwordField.setForeground(Color.GRAY);
            passwordField.setText("Enter Employee ID");

            // Designation Dropdown
            JLabel designationlb = new JLabel("Designation:");
            designationlb.setBounds(439, 280, 150, 40);
            designationlb.setFont(new Font("Arial", Font.PLAIN, 16));
            designationlb.setForeground(Color.BLACK);

            String[] designationTypes = {"Branch Manager", "Data Entry Operator", "Cashier"};
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
            branchField.setText(BMbranch);
            branchField.setEditable(false);

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
                    if (passwordField.getText().equals("Enter Emplopyee ID")) {
                        passwordField.setText("");
                        passwordField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (passwordField.getText().isEmpty()) {
                        passwordField.setText("Enter Employee ID");
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


            // Buttons
            RoundedButton submitBtn = new RoundedButton("Submit");
            submitBtn.setBounds(416, 390, 110, 32);
            submitBtn.addActionListener(e -> {
                String userName = customerIdField.getText();
                String password = passwordField.getText();
                String designation = (String) designationTypeComboBox.getSelectedItem();
                String branch = branchField.getText();


                    try {
                        if (payController.redirect_validateUser(userName, password, designation, branch)) {
                            if(!payController.redirect_getStatus(userName,password,designation,branch))
                            {
                                BigDecimal pay = payController.redirect_getPay(userName, password, designation, branch);
                               SeePaymentView seePaymentView= new SeePaymentView(userName, password, designation, branch,pay);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Already Paid");
                            }
                            dispose();
                        } else {
                            errorLabel.setVisible(true);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

            });

            submitBtn.setBackground(customColor);
            submitBtn.setForeground(Color.WHITE);
            submitBtn.setFont(new Font("Impact", Font.PLAIN, 16));

            RoundedButton backBtn = new RoundedButton("Back");
            backBtn.setBounds(295, 390, 110, 32);
            backBtn.addActionListener(e -> {
                dispose();
                new UserSelectionView();
            });

            backBtn.setBackground(customColor);
            backBtn.setForeground(Color.WHITE);
            backBtn.setFont(new Font("Impact", Font.PLAIN, 16));

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
            mainPanel.add(pt1);
            pt1.add(backBtn);
            pt1.add(submitBtn);
            pt1.setComponentZOrder(backBtn, 0);
            pt1.setComponentZOrder(submitBtn, 0);
            pt1.add(backgroundLabel1);
            mainPanel.add(pt1);
            pt1.add(titleLabel);
            mainPanel.add(backgroundLabel);

            add(mainPanel);
            setVisible(true);
        }
//
//        void storeLoginCredentials(String username, String password, String designation) {
//            BufferedWriter bw = null;
//            String data = username + "," + password + "," + designation;
//            try {
//                bw = new BufferedWriter(new FileWriter("Login.txt", true));
//                bw.write(data);
//                bw.newLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bw != null) {
//                        bw.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        public static void main(String[] args) {
            new PayrollGeneration("1");
        }
    }
