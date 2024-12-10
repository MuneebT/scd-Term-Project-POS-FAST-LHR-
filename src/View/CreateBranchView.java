package View;

import Controller.BranchManagementController;
import Model.BranchDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class CreateBranchView extends JFrame {
    private JButton btnCreate, btnBack;
    private JScrollPane scrollPane;
    private String[] citynames;
    private ImageIcon img;
    private JLabel imagelabel;
    private JLabel b_phone_no, b_name, b_address, b_city, b_status;
    private JTextField tfphoneno, tfname, tfaddress;
    private JComboBox<String> cb_status;
    private JComboBox<String> cb_cityname;

    private BranchManagementController bmc = new BranchManagementController();

    public CreateBranchView() {
        setTitle("Create Branch");
        setLayout(null); // Still using null layout for absolute positioning
        setBounds(100, 100, 800, 600);
        setBackground(Color.white);
        setResizable(false);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Image icon
        img = new ImageIcon("update.jpg");

        // Image label
        imagelabel = new JLabel(img);
        imagelabel.setBounds(0, 0, 400, 600);

        // Phone number label
        b_phone_no = new JLabel("Enter Phone No");
        b_phone_no.setForeground(Color.decode("#415a77"));
        b_phone_no.setFont(new Font("", Font.BOLD, 15));
        b_phone_no.setBounds(420, 170, 150, 30);

        // Phone number text field
        tfphoneno = new JTextField();
        tfphoneno.setBounds(580, 170, 180, 30);

        // Branch name label
        b_name = new JLabel("Enter Branch Name");
        b_name.setForeground(Color.decode("#415a77"));
        b_name.setFont(new Font("", Font.BOLD, 15));
        b_name.setBounds(420, 100, 150, 30);

        // Branch name text field
        tfname = new JTextField();
        tfname.setBounds(580, 100, 180, 30);

        // Address label
        b_address = new JLabel("Enter Address");
        b_address.setForeground(Color.decode("#415a77"));
        b_address.setFont(new Font("", Font.BOLD, 15));
        b_address.setBounds(420, 240, 150, 30);

        // Address text field
        tfaddress = new JTextField();
        tfaddress.setBounds(580, 240, 180, 30);

        // Branch status label
        b_status = new JLabel("Set status");
        b_status.setForeground(Color.decode("#415a77"));
        b_status.setFont(new Font("", Font.BOLD, 15));
        b_status.setBounds(420, 310, 150, 30);

        // Status combo box
        String[] branchstatus = {"Active", "InActive"};
        cb_status = new JComboBox<>(branchstatus);
        cb_status.setBounds(580, 310, 150, 30);

        // City label
        b_city = new JLabel("Select city");
        b_city.setForeground(Color.decode("#415a77"));
        b_city.setFont(new Font("", Font.BOLD, 15));
        b_city.setBounds(420, 380, 150, 30);

        // City combo box
        LinkedList<String> list_citynames = bmc.return_list_of_city_names();
        copy_data(list_citynames);
        cb_cityname = new JComboBox<>(citynames);
        cb_cityname.setBounds(580, 380, 150, 30);

        // Back button
        btnBack = new JButton("Back");
        btnBack.setForeground(Color.white);
        btnBack.setBackground(Color.decode("#415a77"));
        btnBack.setFont(new Font("", Font.BOLD, 14));
        btnBack.setBounds(400, 450, 150, 40);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BranchManagementView();
                dispose();
            }
        });

        // Create button
        btnCreate = new JButton("Create Branch");
        btnCreate.setForeground(Color.white);
        btnCreate.setBackground(Color.decode("#415a77"));
        btnCreate.setFont(new Font("", Font.BOLD, 14));
        btnCreate.setBounds(580, 450, 150, 40);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validatePhoneNumber() && validate_empty_Fields() && validate_name_data() && validate_is_status_combobox_empty() && validate_is_cityname_combobox_empty()) {
                    BranchDAO branchDAO = new BranchDAO();
                    branchDAO.createBranch(tfname.getText(), cb_cityname.getSelectedItem().toString(), cb_status.getSelectedItem().toString(), tfaddress.getText(), tfphoneno.getText());
                    JOptionPane.showMessageDialog(CreateBranchView.this, "Branch Added Successfully");

                    tfphoneno.setText("");
                    tfaddress.setText("");
                    tfname.setText("");
                }
            }
        });

        // Adding components to frame
        add(imagelabel);
        add(b_phone_no);
        add(tfphoneno);
        add(b_name);
        add(tfname);
        add(b_address);
        add(tfaddress);
        add(b_city);
        add(b_status);
        add(cb_status);
        add(cb_cityname);
        add(btnBack);
        add(btnCreate);

        setVisible(true);
    }

    // Copying data from citynames list to array
    private void copy_data(LinkedList<String> data) {
        citynames = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            citynames[i] = data.get(i);
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNo = tfphoneno.getText();
        boolean isValidLength = verify_Phone_No_length(phoneNo);
        boolean isValidData = false;

        if (isValidLength) {
            isValidData = verify_Phone_No_Data(phoneNo);
        }

        if (isValidLength && isValidData) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Invalid phone number! Please enter a valid one.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean verify_Phone_No_length(String phone_no) {
        return phone_no.length() == 11;
    }

    private boolean verify_Phone_No_Data(String phone_no) {
        String regex = "^[0-9]+$";
        return phone_no.matches(regex);
    }

    private boolean validate_empty_Fields() {
        return !tfname.getText().isEmpty() && !tfaddress.getText().isEmpty() && !tfphoneno.getText().isEmpty();
    }

    private boolean validate_name_data() {
        String name = tfname.getText();
        String regex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(regex, name);
    }

    private boolean validate_is_status_combobox_empty() {
        return cb_status.getSelectedItem() != null;
    }

    private boolean validate_is_cityname_combobox_empty() {
        return cb_cityname.getSelectedItem() != null;
    }

    public static void main(String[] args) {
        new CreateBranchView();
    }
}
