package View;

import Controller.DataEntryOperatorController;
import Controller.InventoryCntroller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UpdateInventoryView extends JFrame {
    private JButton btnUpdate;
    private ImageIcon img;
    private JLabel imageLabel;
    private JLabel pQuantity, costPrice, salePrice;
    private JTextField tfQuantity, tfPrice, tfSalePrice;
    private InventoryCntroller ic = new InventoryCntroller();

    public UpdateInventoryView(int id, int quantity, int price, int sp, ManageInventoryView.ButtonEditor miv, DefaultTableModel model, int r) {
        setTitle("Update Inventory");
        setLayout(null); // Using null layout for absolute positioning
        setBounds(100, 100, 800, 500); // Adjusted height after removing vendor fields
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Image icon
        img = new ImageIcon("update.jpg");

        // Image label
        imageLabel = new JLabel(img);
        imageLabel.setBounds(0, 0, 400, 500);

        // Quantity label and field
        pQuantity = new JLabel("Enter Quantity");
        pQuantity.setFont(new Font("Arial", Font.BOLD, 15));
        pQuantity.setBounds(420, 50, 150, 30);
        tfQuantity = new JTextField(String.valueOf(quantity));
        tfQuantity.setBounds(580, 50, 180, 30);

        // Cost price label and field
        costPrice = new JLabel("Enter Cost Price");
        costPrice.setFont(new Font("Arial", Font.BOLD, 15));
        costPrice.setBounds(420, 120, 150, 30);
        tfPrice = new JTextField(String.valueOf(price));
        tfPrice.setBounds(580, 120, 180, 30);

        // Sale price label and field
        salePrice = new JLabel("Enter Sale Price");
        salePrice.setFont(new Font("Arial", Font.BOLD, 15));
        salePrice.setBounds(420, 190, 150, 30);
        tfSalePrice = new JTextField(String.valueOf(sp));
        tfSalePrice.setBounds(580, 190, 180, 30);

        // Update button
        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(Color.CYAN);
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 14));
        btnUpdate.setBounds(580, 300, 100, 40);

        btnUpdate.addActionListener(e -> {
            if (validateInputs()) {
                int updatedQuantity = Integer.parseInt(tfQuantity.getText());
                int updatedPrice = Integer.parseInt(tfPrice.getText());
                int updatedSalePrice = Integer.parseInt(tfSalePrice.getText());

                model.setValueAt(updatedQuantity, r, 2);
                model.setValueAt(updatedPrice, r, 4);
                model.setValueAt(updatedSalePrice, r, 5);

                // Send updated values to controller
                ic.redirect_Inventory_update_request(id, updatedQuantity, updatedPrice, updatedSalePrice);
                dispose();
            }
        });

        // Adding components to the frame
        add(imageLabel);
        add(pQuantity);
        add(tfQuantity);
        add(costPrice);
        add(tfPrice);
        add(salePrice);
        add(tfSalePrice);
        add(btnUpdate);

        setVisible(true);
    }

    // Combined input validation
    private boolean validateInputs() {
        return validateNumericField(tfQuantity.getText(), "Quantity") &&
                validateNumericField(tfPrice.getText(), "Cost Price") &&
                validateNumericField(tfSalePrice.getText(), "Sale Price");
    }

    // Numeric validation for quantity, cost price, and sale price
    private boolean validateNumericField(String value, String fieldName) {
        try {
            int number = Integer.parseInt(value);
            if (number <= 0) {
                JOptionPane.showMessageDialog(this, fieldName + " must be greater than zero.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, fieldName + " must be a valid number.");
            return false;
        }
        return true;
    }
}
