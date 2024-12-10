package View;

import Controller.OrderController;
import Controller.BranchManagementController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.*;

public class UpdateOrderView extends JFrame {

    // Declare components
    private JLabel lblProductName, lblProductQuantity, lblVendorName, lblBranchID;
    private JTextField txtProductName, txtProductQuantity, txtVendorName;
    private JButton btnUpdate;
    private ImageIcon img;
    private JLabel imagelabel;
    private JComboBox<String> comboBranchID;
    private OrderController oc = new OrderController();
    private BranchManagementController bmc = new BranchManagementController();

    public UpdateOrderView(int p_id, String p_name, int p_quantity, int v_id, String v_name, DefaultTableModel model,int row) {
        // Set the frame title
        setTitle("Update Order");

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

        // Branch ID ComboBox and Label
        lblBranchID = new JLabel("Branch ID:");
        lblBranchID.setBounds(420, 250, 150, 30);
        add(lblBranchID);

        // Fetch branch data from controller
        LinkedList<String> branchData = bmc.redirectConcatenatedData();
        comboBranchID = new JComboBox<>(branchData.toArray(new String[0]));

        // Set the default selected branch ID (optional)
        for (int i = 0; i < branchData.size(); i++) {
            String branchEntry = branchData.get(i);
            // If the branch ID is found, set it as selected
            if (branchEntry.startsWith(String.valueOf(p_id) + "_")) {
                comboBranchID.setSelectedIndex(i);
                break;
            }
        }

        // Wrap the ComboBox in a JScrollPane to handle large data sets
        JScrollPane scrollPane = new JScrollPane(comboBranchID);
        scrollPane.setBounds(580, 250, 150, 30);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);

        // Create and position the Update button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(510, 320, 100, 40); // Button centered horizontally below the fields
        add(btnUpdate);

        // Make the frame visible
        setVisible(true);

        // Button ActionListener to handle update
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateProductQuantity() && isValidName() && isValidInteger()) {
                    String newProductName = txtProductName.getText();
                    int newProductQuantity = Integer.parseInt(txtProductQuantity.getText());
                    String newVendorName = txtVendorName.getText();
                    String selectedBranch = (String) comboBranchID.getSelectedItem();
                    StringTokenizer st = new StringTokenizer(selectedBranch, "_");
                    int branchID = Integer.parseInt(st.nextToken());

                    // Call the controller to update the order
                    oc.redirectOrderUpdateRequest(p_id, newProductName, newProductQuantity, newVendorName, branchID);

                    model.setValueAt(p_id,row,0);
                    model.setValueAt(newProductName,row,1);
                    model.setValueAt(newProductQuantity,row,2);
                    model.setValueAt(newVendorName,row,4);
                    model.setValueAt(branchID,row,5);
                    JOptionPane.showMessageDialog(null, "Data Updated In DB");
                    dispose();
                }
            }
        });
    }

    // Validate Product Quantity (must be non-negative)
    public boolean validateProductQuantity() {
        int quantity = Integer.parseInt(txtProductQuantity.getText());
        if (quantity < 0) {
            JOptionPane.showMessageDialog(null, "Product quantity must be positive.");
            return false;
        }
        return true;
    }

    // Validate that the vendor name contains only alphabetic characters
    public boolean isValidName() {
        String vendorName = txtVendorName.getText();
        String pattern = "^[A-Za-z\\s]+$";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(vendorName);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "Vendor name must only contain letters and spaces.");
            return false;
        }
        return true;
    }

    // Validate that the product quantity is an integer
    public boolean isValidInteger() {
        String str = txtProductQuantity.getText();
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Product quantity must be a valid number.");
            return false;
        }
    }
}
