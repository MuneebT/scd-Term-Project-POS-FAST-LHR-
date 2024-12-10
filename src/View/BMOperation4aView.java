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

public class BMOperation4aView extends JFrame {
    public String name;
    public String salary;
    public String empNo;
    public String email;
    public String branchCode;
    public String designation;
    public String password;
    EmployeeManagementController employeeManagementController = new EmployeeManagementController();
private InternetConnectionChecker icc=new InternetConnectionChecker();
    public BMOperation4aView() {
        // Setup second frame
        setTitle("Employee Desktop");
        setBounds(20, 20, 800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Employee Desktop");
        titleLabel.setBounds(0, 30, 800, 40); // Centered title
        titleLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        Color customColor = Color.decode("#415a77"); // RGB for #795757
        titleLabel.setForeground(Color.decode("#fff0f3")); // Set font color

        // Load and scale the icon image
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
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50);
                g2.setColor(getBackground());
                g2.fill(roundedRectangle);
                g2.dispose();
            }
        };
        pt1.setLayout(null);
        pt1.setBounds(100, 100, 600, 460); // Adjusted size and position (shrink to account for removed fields)
        pt1.setOpaque(false);

        // Background for pt1
        ImageIcon bk1 = new ImageIcon("src/resour ces/2.jpg");
        Image scaledImage1 = bk1.getImage().getScaledInstance(600, 460, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        JLabel backgroundLabel1 = new JLabel(scaledIcon1);
        backgroundLabel1.setBounds(0, 0, 600, 460);

        // Create rounded labels and fields
        RoundedLabel actionLabel1 = new RoundedLabel("Name", Color.WHITE, 20, 20);
        actionLabel1.setBounds(50, 50, 200, 40);
        actionLabel1.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabel1.setForeground(customColor);

        RoundedField field1 = new RoundedField(20); // Rounded text field for "Name"
        field1.setBounds(295, 50, 300, 40);

        // Add password label and field
        RoundedLabel actionLabelPassword = new RoundedLabel("Password", Color.WHITE, 20, 20);
        actionLabelPassword.setBounds(50, 100, 200, 40); // Positioned below "Name"
        actionLabelPassword.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabelPassword.setForeground(customColor);

        RoundedField passwordField = new RoundedField(20); // Password field for "Password"
        passwordField.setBounds(295, 100, 300, 40);

        RoundedLabel actionLabel2 = new RoundedLabel("#Emp No", Color.WHITE, 20, 20);
        actionLabel2.setBounds(50, 150, 200, 40);
        actionLabel2.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabel2.setForeground(customColor);

        RoundedField field2 = new RoundedField(20); // Rounded text field for "Employee Number"
        field2.setBounds(295, 150, 300, 40);
        field2.setEditable(false); // Make it read-only so users can't change it
        field2.setText(generateUUIDEmployeeNumber()); // Set initial employee number

        RoundedLabel actionLabel3 = new RoundedLabel("Email", Color.WHITE, 20, 20);
        actionLabel3.setBounds(50, 200, 200, 40);
        actionLabel3.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabel3.setForeground(customColor);

        RoundedField field3 = new RoundedField(20); // Rounded text field for "Email"
        field3.setBounds(295, 200, 300, 40);

        RoundedLabel actionLabel5 = new RoundedLabel("Branch Code", Color.WHITE, 20, 20);
        actionLabel5.setBounds(50, 250, 200, 40);
        actionLabel5.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabel5.setForeground(customColor);

        RoundedField field5 = new RoundedField(20); // Rounded text field for "Branch Code"
        field5.setBounds(295, 250, 300, 40);

        RoundedLabel actionLabel6 = new RoundedLabel("Salary", Color.WHITE, 20, 20);
        actionLabel6.setBounds(50, 300, 200, 40);
        actionLabel6.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabel6.setForeground(customColor);

        RoundedField field6 = new RoundedField(20); // Rounded text field for "Salary"
        field6.setBounds(295, 300, 300, 40);

        RoundedLabel actionLabel7 = new RoundedLabel("Designation", Color.WHITE, 20, 20);
        actionLabel7.setBounds(50, 350, 200, 40);
        actionLabel7.setFont(new Font("Arial", Font.PLAIN, 24));
        actionLabel7.setForeground(customColor);

        String[] jobTitles = {"Data Entry Operator", "Cashier"};
        JComboBox<String> jobComboBox = new JComboBox<>(jobTitles);
        jobComboBox.setBounds(295, 350, 300, 40);

        RoundedButton backButton = new RoundedButton("Back");
        backButton.setBounds(50, 400, 110, 35);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BMDashboardView();
            }
        });
        backButton.setBackground(customColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Impact", Font.PLAIN, 16));
        backButton.setToolTipText("Click here to return!");

        RoundedButton submit = new RoundedButton("Submit");
        submit.setBounds(470, 400, 110, 35);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = field1.getText();
                empNo = field2.getText();
                email = field3.getText();
                branchCode = field5.getText();
                salary = field6.getText();
                designation = (String) jobComboBox.getSelectedItem();

                password = passwordField.getText();
                System.out.println(salary);
                boolean isconnected=icc.startChecking();
                if(isconnected) {
                    employeeManagementController.redirect_employee_insertion(name, empNo, email, branchCode, salary, designation, password);
                    dispose();
                }
                else{
                    storebmoperationsintempfile(name, empNo, email, branchCode, salary, designation, password);
                }


            }
        });
        submit.setBackground(customColor);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Impact", Font.PLAIN, 16));
        submit.setToolTipText("Click here to submit!");

        pt1.add(actionLabel1);
        pt1.add(field1);
        pt1.add(actionLabelPassword);
        pt1.add(passwordField);
        pt1.add(actionLabel2);
        pt1.add(field2);
        pt1.add(actionLabel3);
        pt1.add(field3);
        pt1.add(actionLabel5);
        pt1.add(field5);
        pt1.add(actionLabel6);
        pt1.add(field6);
        pt1.add(actionLabel7);
        pt1.add(jobComboBox);
        pt1.add(backButton);
        pt1.add(submit);
        pt1.add(backgroundLabel1);

        mainPanel.add(titleLabel);
        mainPanel.add(pt1);
        mainPanel.add(backgroundLabel);

        mainPanel.revalidate();
        mainPanel.repaint();

        add(mainPanel);
        setVisible(true);
    }

    public String generateUUIDEmployeeNumber() {
        return "EMP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void storebmoperationsintempfile(String name,String empNo,String email,String branchCode,String salary,String designation,String password){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("bmoperations4a.txt",true));
            String data=name+","+ empNo+","+ email+","+ branchCode+","+ salary+","+ designation+","+ password;
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
        new BMOperation4aView();
    }

}
