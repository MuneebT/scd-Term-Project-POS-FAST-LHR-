package View;

import Controller.LoginController;
import Model.LoggedEmp;
import View.CustomerElements.RoundedButton;
import View.CustomerElements.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;

public class DEODashboardView extends JFrame {
    LoggedEmp loggedEmp=LoggedEmp.getInstance();
    public DEODashboardView() {
        // Setup second frame

        setTitle("Data Entry Operations");
        setBounds(20, 20, 1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        headerPanel.setBounds(0, 0, 1400, 63); // Full width header
        headerPanel.setBackground(new Color(20, 33, 61)); // Customize background color
        //  JLabel headerTitle = new JLabel("Dashboard");
        // headerTitle.setFont(new Font("Impact", Font.PLAIN, 30));
        //  headerTitle.setForeground(Color.WHITE);
        //  headerPanel.add(headerTitle);

        JLabel headerlabel0 = new JLabel("Data Entry Operations");
        JLabel headerlabel1=new JLabel("Search");
        JLabel headerlabel2=new JLabel("About Us");
        JLabel headerlabel3=new JLabel("Log out");
        headerlabel1.setFont(new Font("Impact", Font.PLAIN, 24));
        headerlabel2.setFont(new Font("Impact", Font.PLAIN, 24));
        headerlabel3.setFont(new Font("Impact", Font.PLAIN, 24));

        headerlabel0.setBounds(0, 30, 1400, 40); // Centered title
        headerlabel0.setFont(new Font("Impact", Font.PLAIN, 24));
        Color customColor = new Color(121, 87, 87); // RGB for #795757
        headerlabel0.setForeground(customColor); // Set font color
        headerlabel1.setForeground(customColor);
        headerlabel2.setForeground(customColor);
        headerlabel3.setForeground(customColor);



        // Load and scale the icon image
        ImageIcon originalIcon = new ImageIcon("src/resources/bulb-icon.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        headerlabel0.setIcon(scaledIcon);
        headerlabel0.setHorizontalTextPosition(SwingConstants.RIGHT);
        headerlabel0.setHorizontalAlignment(SwingConstants.CENTER);


        // Set background
        ImageIcon bk = new ImageIcon("src/resources/bulb.jpg");
        Image scaledImage2 = bk.getImage().getScaledInstance(1400, 700, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        JLabel backgroundLabel = new JLabel(scaledIcon2);
        backgroundLabel.setBounds(0, 0, 1400, 700);


        // Create rounded panel
        JPanel pt1 = new JPanel(new GridLayout(3,3,20,20)) {
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
        pt1.setBounds(10, 60, 1400, 590); // Adjusted size and position
        pt1.setBackground(new Color(255, 255, 255));
        ImageIcon originalIcon1 = new ImageIcon("src/resources/click7.png");
        Image scaledImage1 = originalIcon1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Larger size for better visibility
        ImageIcon scaledOriginalIcon1 = new ImageIcon(scaledImage1);

        // Create rounded labels
        RoundedLabel titleLabel1 = new RoundedLabel(scaledOriginalIcon1,"Click here to Manage Vendors", new Color(34, 34, 59), 50, 50);
        //   titleLabel1.setBounds(70, 50, 250, 250);
        titleLabel1.setFont(new Font("Arial", Font.PLAIN, 18));
        titleLabel1.setForeground(customColor); // Set the font color
        //vendor inventory order
        RoundedLabel titleLabel1_1 = new RoundedLabel("  Manage Vendors", new Color(34, 34, 59), 50, 50);
        titleLabel1_1.setBounds(9,0,280,40);
        titleLabel1_1.setFont(new Font("Impact", Font.PLAIN, 18));
        titleLabel1_1.setForeground(customColor);
        titleLabel1.add(titleLabel1_1);
        //  titleLabel1.setBackground(new Color(216, 210, 194));

        titleLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                try {
                    new ManageVendorView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
             //   JOptionPane.showMessageDialog(null, "Managing");
                //new EmployeeOperation3_1();
            }
        });


        RoundedLabel titleLabel2 = new RoundedLabel(scaledOriginalIcon1,"Click here to Manage Inventory", new Color(34, 34, 59), 50, 50);
        // titleLabel2.setBounds(650, 50, 250, 250);
        titleLabel2.setFont(new Font("Arial", Font.PLAIN, 18));
        titleLabel2.setForeground(customColor);


        RoundedLabel titleLabel2_1 = new RoundedLabel("  Manage Inventory", new Color(34, 34, 59), 50, 50);
        titleLabel2_1.setBounds(9,0,280,40);
        titleLabel2_1.setFont(new Font("Impact", Font.PLAIN, 18));
        titleLabel2_1.setForeground(customColor);
        titleLabel2.add(titleLabel2_1);

        titleLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new ManageInventoryView();
            }
        });



        RoundedLabel titleLabel3 = new RoundedLabel(scaledOriginalIcon1,"Click here to Manage Orders", new Color(34, 34, 59), 50, 50);
        //  titleLabel3.setBounds(70, 330, 250, 250);
        titleLabel3.setFont(new Font("Arial", Font.PLAIN, 18));
        titleLabel3.setForeground(customColor); // Set the font color
        RoundedLabel titleLabel3_1 = new RoundedLabel("  Manage Orders", new Color(34, 34, 59), 50, 50);
        titleLabel3_1.setBounds(9,0,280,40);
        titleLabel3_1.setFont(new Font("Impact", Font.PLAIN, 18));
        titleLabel3_1.setForeground(customColor);
        titleLabel3.add(titleLabel3_1);

        titleLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new ManageOrderView();
            }
        });




        RoundedLabel titleLabel4 = new RoundedLabel(scaledOriginalIcon1,"Click here to Change Password", new Color(34, 34, 59), 50, 50);
        //  titleLabel3.setBounds(70, 330, 250, 250);
        titleLabel4.setFont(new Font("Arial", Font.PLAIN, 18));
        titleLabel4.setForeground(customColor); // Set the font color
        RoundedLabel titleLabel4_1 = new RoundedLabel("  Change Password", new Color(34, 34, 59), 50, 50);
        titleLabel4_1.setBounds(9,0,280,40);
        titleLabel4_1.setFont(new Font("Impact", Font.PLAIN, 18));
        titleLabel4_1.setForeground(customColor);
        titleLabel4.add(titleLabel4_1);

        titleLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showChangePasswordDialog();

            }
        });



        ImageIcon scaledOriginalIcon2 = new ImageIcon(scaledImage2);


        // Back button
        RoundedButton openFirstPageButton = new RoundedButton("Back");
        openFirstPageButton.setBounds(1200, 490, 110, 40);
        openFirstPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //  new EmployeeDesktop();
            }
        });

        // Adjusted position for the first card
        titleLabel1.setBounds(50, 50, 300, 200); // Adjust position and size

        // Adjusted position for the second card
        titleLabel2.setBounds(400, 50, 300, 200);

        // Adjusted position for the third card
        titleLabel3.setBounds(750, 50, 300, 200);
        titleLabel4.setBounds(50, 300, 300, 200);






        openFirstPageButton.setBackground(customColor);
        openFirstPageButton.setForeground(Color.WHITE);
        openFirstPageButton.setFont(new Font("Impact", Font.PLAIN, 16));
        openFirstPageButton.setToolTipText("Click here to return!");

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBounds(0, 650, 1400, 50); // Full width footer at the bottom
        footerPanel.setBackground(new Color(20, 33, 61)); // Customize footer background color
        JLabel footerLabel = new JLabel("Footer Section © 2024");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);

        // Add components to panels
//        mainPanel.add(titleLabel1);
//        mainPanel.add(titleLabel2);
//        mainPanel.add(titleLabel3);
//        mainPanel.add(titleLabel4);
//        mainPanel.add(titleLabel5);
        pt1.add(titleLabel1);
        pt1.add(titleLabel2);
        pt1.add(titleLabel3);
        pt1.add(titleLabel4);
        footerPanel.add(openFirstPageButton);

        // Add components to the main panel
        mainPanel.add(headerPanel); // Add header
        headerPanel.add(headerlabel0);
        headerPanel.add(headerlabel1);
        headerPanel.add(headerlabel2);
        headerPanel.add(headerlabel3);
        mainPanel.add(pt1); // Add rounded panel
        mainPanel.add(footerPanel); // Add footer
        mainPanel.add(backgroundLabel); // Full background

        // Revalidate and repaint to ensure proper component visibility
        mainPanel.revalidate();
        mainPanel.repaint();

        // Add main panel to the frame
        add(mainPanel);
        setVisible(true);


        int isFirstTimePasswordChange=loggedEmp.getFirstTimePasswordChange();
        boolean isFirstTimeLogin=false;
        if(isFirstTimePasswordChange==0)
        {
            isFirstTimeLogin=true;
        }
        if(isFirstTimeLogin)
        {
            showChangePasswordDialog();
        }
    }
    // Helper method to create action labels
    private JLabel createActionLabel(String text, int y, Color color) {
        JLabel label = new JLabel(text);
        label.setBounds(50, y, 400, 40); // Position for alignment
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setForeground(color);
        return label;
    }


    private void showChangePasswordDialog()
    {

        JDialog passwordDialog = new JDialog(this, "Change Password", true);
        passwordDialog.setLayout(new GridBagLayout());
        passwordDialog.setSize(400, 300);
        passwordDialog.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel currentPasswordLabel = new JLabel("Current Password:");
        JPasswordField currentPasswordField = new JPasswordField(15);

        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField(15);

        JButton submitButton = new JButton("Submit");

        // Add components to the dialog
        gbc.gridx = 0;
        gbc.gridy = 0;
        passwordDialog.add(currentPasswordLabel, gbc);

        gbc.gridx = 1;
        passwordDialog.add(currentPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        passwordDialog.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        passwordDialog.add(newPasswordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        passwordDialog.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoggedEmp loggedEmp=LoggedEmp.getInstance();


                int isFirstTimePasswordChange=loggedEmp.getFirstTimePasswordChange();
                boolean isFirstTimeLogin=false;
                if(isFirstTimePasswordChange==0)
                {
                    isFirstTimeLogin=true;
                }

                String currentPassword = new String(currentPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());

                String retrievedCurrPass=loggedEmp.getPassword();

                // Validate passwords
                if (currentPassword.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(passwordDialog, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (currentPassword.equals(newPassword)) {
                    JOptionPane.showMessageDialog(passwordDialog, "New password must be different from the current password!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if (!currentPassword.equals(retrievedCurrPass)) {
                    JOptionPane.showMessageDialog(passwordDialog, "The Password you provided is not current Password", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    boolean errorExists=false;
                    // Process password change logic here
                    LoginController AuthService=new LoginController();
                    try {
                        AuthService.updatePassword(currentPassword,newPassword);
                    } catch (SQLException ex) {
                        errorExists=true;
                        JOptionPane.showMessageDialog(passwordDialog, "Password could not be changed", "Success", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    if(!errorExists)
                    {
                        JOptionPane.showMessageDialog(passwordDialog, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        isFirstTimeLogin = false; // Mark the user as logged in

                    }
                    passwordDialog.dispose();

                }
            }
        });

        passwordDialog.setVisible(true);
    }

    public static void main(String[] args) {
        new DEODashboardView();
    }
}

