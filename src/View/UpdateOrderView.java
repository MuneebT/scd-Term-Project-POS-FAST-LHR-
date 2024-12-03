package View;

import Controller.DataEntryOperatorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.*;
public class UpdateOrderView extends JFrame {

    // Declare components
    private JLabel lblProductName, lblProductQuantity, lblVendorName;
    private JTextField txtProductName, txtProductQuantity, txtVendorName;
    private JButton btnUpdate;
    private ImageIcon img;
    private JLabel imagelabel;
    private DataEntryOperatorController deoc = new DataEntryOperatorController();

    public UpdateOrderView(int p_id, String p_name, int p_quantity, int v_id, String v_name) {
        // Set the frame title
        setTitle("Add Order");

        // Set layout to null for absolute positioning
        setLayout(null);

        // Set the frame bounds (position and size)
        setBounds(100, 100, 800, 600);

        // Make the window non-resizable
        setResizable(false);

        // Set the background color to white
        getContentPane().setBackground(Color.WHITE);

        // Set default close operation
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Load image for background
        img = new ImageIcon("update.jpg");

        // Set image label with the background image
        imagelabel = new JLabel(img);
        imagelabel.setBounds(0, 0, 400, 600); // Set bounds for the image label
        add(imagelabel);

        // Initialize and position the components
        lblProductName = new JLabel("Product Name:");
        lblProductName.setBounds(420, 100, 150, 30);
        add(lblProductName);

        txtProductName = new JTextField();
        txtProductName.setBounds(580, 100, 150, 30);
        txtProductName.setText(p_name);
        add(txtProductName);

        lblProductQuantity = new JLabel("Product Quantity:");
        lblProductQuantity.setBounds(420, 150, 150, 30);
        add(lblProductQuantity);

        txtProductQuantity = new JTextField();
        txtProductQuantity.setBounds(580, 150, 150, 30);
        txtProductQuantity.setText(String.valueOf(p_quantity));
        add(txtProductQuantity);

        lblVendorName = new JLabel("Vendor Name:");
        lblVendorName.setBounds(420, 200, 150, 30);
        add(lblVendorName);

        txtVendorName = new JTextField();
        txtVendorName.setBounds(580, 200, 150, 30);
        txtVendorName.setText(v_name);
        add(txtVendorName);

        // Create and position the Update button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(510, 270, 100, 40); // Button centered horizontally below the fields
        add(btnUpdate);

        // Make the frame visible
        setVisible(true);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateproductquantity() && isValidName() && isValidInteger()) {
                    String newProductname = txtProductName.getText();
                    int newProductQuantity = Integer.parseInt(txtProductQuantity.getText());
                    String newVendorname = txtVendorName.getText();
                    deoc.redirectOrderUpdateRequest(p_id, newProductname, newProductQuantity, newVendorname);
                    JOptionPane.showMessageDialog(null, "Data Updated In DB");
                    dispose();
                }
            }
        });
    }

    public boolean validateproductquantity() {
        int quantity = Integer.parseInt(txtProductQuantity.getText());
        if (quantity < 0) {
            return false;
        }
        return true;
    }

    public boolean isValidName() {
        String vendorname = txtVendorName.getText();

        String pattern = "^[A-Za-z\\s]+$";


        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(vendorname);


        return matcher.matches();
    }

    public boolean isValidInteger() {
        String str = txtProductQuantity.getText();
        try {

            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"DOnt Enter Characters");
            return false;
        }

    }
}