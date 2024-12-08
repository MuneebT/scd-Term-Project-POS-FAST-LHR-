package View;

import Connection.InternetConnectionChecker;
import Controller.EmployeeManagementController;
import View.CustomerElements.RoundedButton;
import View.CustomerElements.RoundedField;
import View.CustomerElements.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class AddBMView extends JFrame {
    public String name;
    public String empNo;
    public String password;
    public String email;
    public String branchCode;
    public String salary;
    public String designation;
private InternetConnectionChecker icc=new InternetConnectionChecker();
    EmployeeManagementController employeeManagementController = new EmployeeManagementController();

    public AddBMView() {
        // Frame setup
        setTitle("Add Branch Manager - METRO POS");
        setBounds(20, 20, 800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Add Branch Manager");
        titleLabel.setBounds(0, 30, 800, 40);
        titleLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        Color customColor = Color.decode("#415a77");
        titleLabel.setForeground(Color.decode("#fff0f3"));//61a5c2
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Background image
        ImageIcon bk = new ImageIcon("src/resources/background1.jpg");
        Image scaledImage = bk.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 800);

        // Rounded panel setup
        // Rounded panel setup with background image
        JPanel formPanel = new JPanel() {
            private ImageIcon backgroundImage = new ImageIcon("src/resources/bulb.jpg");

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Scale the image to fit the panel
                Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                g2.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), null);


                // Rounded rectangle overlay
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50);
                g2.setColor(new Color(255, 255, 255, 150)); // Semi-transparent white overlay
                g2.fill(roundedRectangle);

                g2.dispose();
            }
        };
        formPanel.setLayout(null);
        formPanel.setBounds(100, 100, 600, 500);
        formPanel.setOpaque(false);



        // Labels and fields
        RoundedLabel nameLabel = new RoundedLabel("Name", Color.WHITE, 20, 20);
        nameLabel.setBounds(50, 50, 200, 40);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        nameLabel.setForeground(customColor);

        RoundedField nameField = new RoundedField(20);
        nameField.setBounds(295, 50, 300, 40);

        RoundedLabel empNoLabel = new RoundedLabel("Emp No", Color.WHITE, 20, 20);
        empNoLabel.setBounds(50, 100, 200, 40);
        empNoLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        empNoLabel.setForeground(customColor);

        RoundedField empNoField = new RoundedField(20);
        empNoField.setBounds(295, 100, 300, 40);
        empNoField.setEditable(false);
        empNoField.setText(generateUUIDEmployeeNumber());

        RoundedLabel passwordLabel = new RoundedLabel("Password", Color.WHITE, 20, 20);
        passwordLabel.setBounds(50, 150, 200, 40);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordLabel.setForeground(customColor);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(295, 150, 300, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));

        RoundedLabel emailLabel = new RoundedLabel("Email", Color.WHITE, 20, 20);
        emailLabel.setBounds(50, 200, 200, 40);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        emailLabel.setForeground(customColor);

        RoundedField emailField = new RoundedField(20);
        emailField.setBounds(295, 200, 300, 40);

        RoundedLabel branchCodeLabel = new RoundedLabel("Branch Code", Color.WHITE, 20, 20);
        branchCodeLabel.setBounds(50, 250, 200, 40);
        branchCodeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        branchCodeLabel.setForeground(customColor);

        RoundedField branchCodeField = new RoundedField(20);
        branchCodeField.setBounds(295, 250, 300, 40);

        RoundedLabel salaryLabel = new RoundedLabel("Salary", Color.WHITE, 20, 20);
        salaryLabel.setBounds(50, 300, 200, 40);
        salaryLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        salaryLabel.setForeground(customColor);

        RoundedField salaryField = new RoundedField(20);
        salaryField.setBounds(295, 300, 300, 40);

        // Back button
        RoundedButton backButton = new RoundedButton("Back");
        backButton.setBounds(50, 420, 110, 35);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SADashboardView screen=new SADashboardView();
                screen.setVisible(true);
            }
        });
        backButton.setBackground(customColor);
        backButton.setForeground(Color.WHITE);

        // Submit button
        RoundedButton submitButton = new RoundedButton("Submit");
        submitButton.setBounds(470, 420, 110, 35);
        submitButton.addActionListener(e -> {
            boolean isconnected=icc.startChecking();
            if(isconnected) {
                name = nameField.getText();
                empNo = empNoField.getText();
                password = new String(passwordField.getPassword());
                email = emailField.getText();
                branchCode = branchCodeField.getText();
                salary = salaryField.getText();

                employeeManagementController.redirect_employee_insertion(name, empNo, email, branchCode, salary, "Branch Manager", password);

                dispose();
            }
            else{
                storedataintempfile(name, empNo, email, branchCode, salary, "Branch Manager", password);
            }



        });
        submitButton.setBackground(customColor);
        submitButton.setForeground(Color.WHITE);

        // Add components to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(empNoLabel);
        formPanel.add(empNoField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(branchCodeLabel);
        formPanel.add(branchCodeField);
        formPanel.add(salaryLabel);
        formPanel.add(salaryField);
        formPanel.add(backButton);
        formPanel.add(submitButton);

        // Add panels to the main panel
        mainPanel.add(titleLabel);
        mainPanel.add(formPanel);
        mainPanel.add(backgroundLabel);

        add(mainPanel);
        setVisible(true);
    }

    // Generate unique employee number
    public String generateUUIDEmployeeNumber() {
        return "EMP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void storedataintempfile(String name,String empNo,String email,String branchCode,String salary,String designation,String password){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("AddBM.txt",true));
            String data=name+","+ empNo+","+ email+"," + branchCode+","+ salary+","+ "Branch Manager"+","+ password;
            bw.write(data);
            bw.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new AddBMView();
    }
}
