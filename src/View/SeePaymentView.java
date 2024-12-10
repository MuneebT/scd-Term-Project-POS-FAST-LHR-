package View;

import Controller.PayController;
import View.CustomerElements.RoundedButton;
import View.CustomerElements.RoundedField;
import View.CustomerElements.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class SeePaymentView extends JFrame {
    public String invoiceNumber;
    boolean status=false;
    PayController payController=new PayController();
    public SeePaymentView(String name, String password, String designation, String branch,BigDecimal pay) {
        // Setup the frame
        setTitle("See Payment");
        setBounds(20, 20, 800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel titleLabel = new JLabel("See Payment");
        titleLabel.setBounds(0, 30, 800, 40); // Centered title
        titleLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        Color customColor = Color.decode("#415a77"); // RGB for #795757
        titleLabel.setForeground((Color.decode("#fff0f3"))); // Set font color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon originalIcon = new ImageIcon("src/resources/logo2.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        titleLabel.setIcon(scaledIcon);
        titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set background
        ImageIcon bk = new ImageIcon("src/resources/background1.jpg");
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
        ImageIcon bk1 = new ImageIcon("src/resources/Deskt opbg.jpg");
        Image scaledImage1 = bk1.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        JLabel backgroundLabel1 = new JLabel(scaledIcon1);
        backgroundLabel1.setBounds(0, 0, 600, 200);

        // Create rounded label and field for invoice number
        RoundedLabel actionLabelInvoice = new RoundedLabel("Payment", Color.WHITE, 20, 20);
        actionLabelInvoice.setBounds(50, 50, 200, 40);
        actionLabelInvoice.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabelInvoice.setForeground(customColor);

        RoundedField fieldInvoice = new RoundedField(20); // Rounded text field for "Invoice Number"
        fieldInvoice.setEditable(false);
        fieldInvoice.setText(String.valueOf(pay));
        fieldInvoice.setBounds(295, 50, 300, 40);

        // Create back button
        RoundedButton backButton = new RoundedButton("Back");
        backButton.setBounds(80, 120, 110, 35);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BMDashboardView();
                dispose(); // Close current window
            }
        });
        backButton.setBackground(customColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Impact", Font.PLAIN, 16));
        backButton.setToolTipText("Click here to return!");

        // Create submit button
        RoundedButton submitButton = new RoundedButton("Paid");
        submitButton.setBounds(405, 120, 110, 35);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setting status

                if(payController.redirect_updateStatus(name, password, designation, branch))
                {
                    JOptionPane.showMessageDialog(null,"Paid\n");
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Not Paid\n");
                }


                // System.out.println("Submitted Invoice Number: " + invoiceNumber);
                dispose(); // Close current window

            }
        });
        submitButton.setBackground(customColor);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Impact", Font.PLAIN, 16));
        submitButton.setToolTipText("Click here to submit!");

        // Add components to panel
        pt1.add(actionLabelInvoice);
        pt1.add(fieldInvoice);
        pt1.add(backButton);
        pt1.add(submitButton);
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
    }
}
