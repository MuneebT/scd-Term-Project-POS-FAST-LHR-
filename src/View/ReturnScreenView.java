package View;

import Controller.ReturnController;
import Model.ReturnDao;
import Model.Sale;
import View.CustomerElements.RoundedButton;
import View.CustomerElements.RoundedField;
import View.CustomerElements.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReturnScreenView extends JFrame {
    public String invoiceNumber;

    public ReturnScreenView() {
        // Setup the frame
        setTitle("Return Screen");
        setBounds(20, 20, 800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Return Screen");
        titleLabel.setBounds(0, 30, 800, 40); // Centered title
        titleLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        Color customColor = new Color(121, 87, 87); // RGB for #795757
        titleLabel.setForeground(customColor); // Set font color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set background
        ImageIcon bk = new ImageIcon("src/resources/bulb.jpg");
        Image scaledImage2 = bk.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        JLabel backgroundLabel = new JLabel(scaledIcon2);
        backgroundLabel.setBounds(0, 0, 800, 800);

        // Create rounded panel
        JPanel pt1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.dispose();
            }
        };
        pt1.setLayout(null);
        pt1.setBounds(100, 100, 600, 200); // Adjusted size and position
        pt1.setOpaque(false);

        // Background for pt1
        ImageIcon bk1 = new ImageIcon("src/resources/Desktopbg.jpg");
        Image scaledImage1 = bk1.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        JLabel backgroundLabel1 = new JLabel(scaledIcon1);
        backgroundLabel1.setBounds(0, 0, 600, 200);

        // Create rounded label and field for invoice number
        RoundedLabel actionLabelInvoice = new RoundedLabel("Invoice Number", Color.WHITE, 20, 20);
        actionLabelInvoice.setBounds(50, 50, 200, 40);
        actionLabelInvoice.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabelInvoice.setForeground(customColor);

        RoundedField fieldInvoice = new RoundedField(20); // Rounded text field for "Invoice Number"
        fieldInvoice.setBounds(295, 50, 300, 40);

        // Create back button
        RoundedButton backButton = new RoundedButton("Back");
        backButton.setBounds(50, 120, 110, 35);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
            }
        });
        backButton.setBackground(customColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Impact", Font.PLAIN, 16));
        backButton.setToolTipText("Click here to return!");

        // Create submit button
        RoundedButton submitButton = new RoundedButton("Submit");
        submitButton.setBounds(295, 120, 110, 35);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoiceNumber = fieldInvoice.getText();
//                List<Sale> salesList = new ArrayList<>();
//                ReturnController returnController=new ReturnController();
//               // returnController.redirect_get_sales(invoiceNumber);
                new SaleTableView(invoiceNumber);
                // System.out.println("Submitted Invoice Number: " + invoiceNumber);
                dispose(); // Close current window
            }
        });
        submitButton.setBackground(customColor);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Impact", Font.PLAIN, 16));
        submitButton.setToolTipText("Click here to submit!");

        // Create add button
        RoundedButton addButton = new RoundedButton("Add");
        addButton.setBounds(470, 120, 110, 35);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = fieldInvoice.getText();
                System.out.println("Added Invoice Number: " + input);
                JOptionPane.showMessageDialog(ReturnScreenView.this, "Invoice Number '" + input + "' added successfully!");
            }
        });
        addButton.setBackground(customColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Impact", Font.PLAIN, 16));
        addButton.setToolTipText("Click here to add!");

        // Add components to panel
        pt1.add(actionLabelInvoice);
        pt1.add(fieldInvoice);
        pt1.add(backButton);
        pt1.add(submitButton);
        pt1.add(addButton);
        pt1.add(backgroundLabel1);

        mainPanel.add(titleLabel);
        mainPanel.add(pt1);
        mainPanel.add(backgroundLabel);

        mainPanel.revalidate();
        mainPanel.repaint();

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ReturnScreenView();
    }
}