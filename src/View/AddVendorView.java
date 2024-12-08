package View;

import Connection.InternetConnectionChecker;
import Controller.VendorManagementController;
import View.CustomerElements.RoundedButton;
import View.CustomerElements.RoundedField;
import View.CustomerElements.RoundedLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class AddVendorView extends JFrame {

    private RoundedField  nameField, contactPersonField, phoneField, addressField, cityField,
            stateProvinceField, countryField, emailField;
    VendorManagementController vendorManagementController = new VendorManagementController();

    private InternetConnectionChecker iccc=new InternetConnectionChecker();
    public AddVendorView() {
        // Frame setup
        setTitle("Add Vendor ");
        setBounds(20, 20, 800, 700); // Adjust height for additional fields
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("ADD Vendor");
        titleLabel.setBounds(0, 10, 800, 40);
        titleLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        Color customColor = Color.decode("#415a77");
        titleLabel.setForeground(Color.decode("#fff0f3"));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Background image
        ImageIcon bk = new ImageIcon("src/resources/background1.jpg");
        Image scaledImage = bk.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH); // Adjust size for added fields
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 800);

        // Rounded panel setup
        JPanel formPanel = new JPanel() {
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
        formPanel.setLayout(null);
        formPanel.setBounds(100, 50, 600, 600); // Adjust for additional fields
        formPanel.setOpaque(false);

        // Adjusted starting y-position for attributes

        // Vendor Name label and field
        RoundedLabel nameLabel = new RoundedLabel("Name", Color.WHITE, 20, 20);
        nameLabel.setBounds(50, 100, 200, 40); // Start a bit lower
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        nameLabel.setForeground(customColor);

        nameField = new RoundedField(20);
        nameField.setBounds(295, 100, 300, 40);

        // Contact Person label and field
        RoundedLabel contactPersonLabel = new RoundedLabel("Contact Person", Color.WHITE, 20, 20);
        contactPersonLabel.setBounds(50, 150, 200, 40); // Start a bit lower
        contactPersonLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        contactPersonLabel.setForeground(customColor);

        contactPersonField = new RoundedField(20);
        contactPersonField.setBounds(295, 150, 300, 40);

        // Phone label and field
        RoundedLabel phoneLabel = new RoundedLabel("Phone", Color.WHITE, 20, 20);
        phoneLabel.setBounds(50, 200, 200, 40); // Start a bit lower
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        phoneLabel.setForeground(customColor);

        phoneField = new RoundedField(20);
        phoneField.setBounds(295, 200, 300, 40);

        // Address label and field
        RoundedLabel addressLabel = new RoundedLabel("Address", Color.WHITE, 20, 20);
        addressLabel.setBounds(50, 250, 200, 40); // Start a bit lower
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        addressLabel.setForeground(customColor);

        addressField = new RoundedField(20);
        addressField.setBounds(295, 250, 300, 40);

        // City label and field
        RoundedLabel cityLabel = new RoundedLabel("City", Color.WHITE, 20, 20);
        cityLabel.setBounds(50, 300, 200, 40); // Start a bit lower
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        cityLabel.setForeground(customColor);

        cityField = new RoundedField(20);
        cityField.setBounds(295, 300, 300, 40);

        // State/Province label and field
        RoundedLabel stateProvinceLabel = new RoundedLabel("State/Province", Color.WHITE, 20, 20);
        stateProvinceLabel.setBounds(50, 350, 200, 40); // Start a bit lower
        stateProvinceLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        stateProvinceLabel.setForeground(customColor);

        stateProvinceField = new RoundedField(20);
        stateProvinceField.setBounds(295, 350, 300, 40);

        // Country label and field
        RoundedLabel countryLabel = new RoundedLabel("Country", Color.WHITE, 20, 20);
        countryLabel.setBounds(50, 400, 200, 40); // Start a bit lower
        countryLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        countryLabel.setForeground(customColor);

        countryField = new RoundedField(20);
        countryField.setBounds(295, 400, 300, 40);

        // Email label and field (new field)
        RoundedLabel emailLabel = new RoundedLabel("Email", Color.WHITE, 20, 20);
        emailLabel.setBounds(50, 450, 200, 40); // Start a bit lower
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        emailLabel.setForeground(customColor);

        emailField = new RoundedField(20);
        emailField.setBounds(295, 450, 300, 40);

        // Insert button
        RoundedButton updateButton = new RoundedButton("Insert");
        updateButton.setBounds(470, 500, 110, 35); // Adjust button placement
        updateButton.addActionListener(e -> {
            handleUpdate(
                    nameField.getText(),
                    contactPersonField.getText(),
                    phoneField.getText(),
                    emailField.getText(), // Use the emailField value
                    addressField.getText(),
                    cityField.getText(),
                    stateProvinceField.getText(),
                    countryField.getText()
            );
        });
        updateButton.setBackground(customColor);
        updateButton.setForeground(Color.WHITE);

        // Add components to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(contactPersonLabel);
        formPanel.add(contactPersonField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(addressLabel);
        formPanel.add(addressField);
        formPanel.add(cityLabel);
        formPanel.add(cityField);
        formPanel.add(stateProvinceLabel);
        formPanel.add(stateProvinceField);
        formPanel.add(countryLabel);
        formPanel.add(countryField);
        formPanel.add(emailLabel); // Add email label
        formPanel.add(emailField); // Add email field
        formPanel.add(updateButton);

        // Add panels to the main panel
        mainPanel.add(titleLabel);
        mainPanel.add(formPanel);
        mainPanel.add(backgroundLabel);

        add(mainPanel);
        setVisible(true);
    }

    public void handleUpdate(String name, String contactPerson, String phone, String email,
                             String address, String city, String stateProvince, String country) {
        boolean isconnected= iccc.startChecking();

        if(isconnected){
        if (vendorManagementController.redirect_insert_Vendors(name, contactPerson, phone, email, address, city, stateProvince, country)) {
            JOptionPane.showMessageDialog(this, "Vendor updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Vendor update failed.");
        }
        }
        else{

            //==================stored vendordata in temp file================

            storeVendordataintempfile(name, contactPerson, phone, email, address, city, stateProvince, country);
            writevendoridandnameforconcatenation(vendorManagementController.returnlistofvendorids(),vendorManagementController.returnlistofvendornames());
        }
    }

    public void storeVendordataintempfile(String name,String contactPerson,String phone, String email,
                                          String address,String city,String stateProvince,String country){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("addvendor.txt",true));
            String data=name+","+ contactPerson+","+ phone+","+ email+","+ address+","+ city+","+ stateProvince+","+country;
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
    public void writevendoridandnameforconcatenation(LinkedList<Integer> id,LinkedList<String> name){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("concatenatedvendordata.txt",true));
            for(int i=0;i<id.size();i++){
                String data=id.get(i)+"_"+name.get(i);
                bw.write(data);
                bw.newLine();
            }
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
        new AddVendorView();
    }
}
