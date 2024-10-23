import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class UpdateScreen extends JFrame {
    private UpdateScreenCode usc;
    private JButton btnupdate;
    private ImageIcon img;
    private JLabel imagelabel;
    private JLabel b_phone_no, b_name, b_address,b_city,b_status;
    private JTextField tfphoneno, tfname, tfaddress;

    public UpdateScreen() {
        // Initialize update Screen variable
        usc = new UpdateScreenCode();

        setTitle("Update");
        setLayout(null); // Still using null layout for absolute positioning
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Image icon
        img = new ImageIcon("update.jpg");

        // Image label
        imagelabel = new JLabel(img);
        imagelabel.setBounds(0, 0, 400, 600);

        // Phone number label
        b_phone_no = new JLabel("Enter Phone No");
        b_phone_no.setForeground(Color.BLACK);
        b_phone_no.setFont(new Font("", Font.BOLD, 15));
        b_phone_no.setBounds(420, 100, 150, 30);

        // Phone number text field
        tfphoneno = new JTextField();
        tfphoneno.setBounds(580, 100, 180, 30);

        // Branch name label
        b_name = new JLabel("Enter New Name");
        b_name.setForeground(Color.BLACK);
        b_name.setFont(new Font("", Font.BOLD, 15));
        b_name.setBounds(420, 170, 150, 30);

        // Branch name text field
        tfname = new JTextField();
        tfname.setBounds(580, 170, 180, 30);

        // Address label
        b_address = new JLabel("Enter Address");
        b_address.setForeground(Color.BLACK);
        b_address.setFont(new Font("", Font.BOLD, 15));
        b_address.setBounds(420, 240, 150, 30);

        // Address text field
        tfaddress = new JTextField();
        tfaddress.setBounds(580, 240, 180, 30);

        //city label



        // Update button
        btnupdate = new JButton("Update");
        btnupdate.setBackground(Color.CYAN);
        btnupdate.setFont(new Font("", Font.BOLD, 14));
        btnupdate.setBounds(580, 320, 100, 40);

        // Adding components to frame
        add(imagelabel);
        add(b_phone_no);
        add(tfphoneno);
        add(b_name);
        add(tfname);
        add(b_address);
        add(tfaddress);
        add(btnupdate);

        setVisible(true);

        // Button action listener
        btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (validatePhoneNumber() && validate_empty_Fields() && validate_name_data()) {

                    JOptionPane.showMessageDialog(UpdateScreen.this, "Data Updated Succesfully");

                    dispose();

                } else {

//                    tfphoneno.setText("");
//                    tfaddress.setText("");
//                    tfname.setText("");
                }
            }
        });
    }

    private boolean validatePhoneNumber() {
        String phoneNo = tfphoneno.getText();
        boolean isValidLength = usc.verify_Phone_No_length(phoneNo);
        boolean isValidData = false;

        if (isValidLength) {
            isValidData = usc.verify_Phone_No_Data(phoneNo);
        }

        if (isValidLength && isValidData) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Invalid phone number! Please enter a valid one.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
private boolean validate_empty_Fields(){
        String name_data,add_data,Phone_data;
        name_data=tfname.getText();
        add_data=tfaddress.getText();
        Phone_data=tfphoneno.getText();
        if(name_data.isEmpty() || add_data.isEmpty() || Phone_data.isEmpty()){
         JOptionPane.showMessageDialog(null,"Dont leave Empty Fields");
            return false;
        }
        //JOptionPane.showMessageDialog(null,"Dont Leave Field Empty");
        return true;
}

boolean validate_name_data(){
        String name=tfname.getText();
    String regex = "^[a-zA-Z\\s]+$";
    return Pattern.matches(regex, name);
}

}
