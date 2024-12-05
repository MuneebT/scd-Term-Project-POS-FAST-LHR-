package View;

import Controller.DataEntryOperatorController;
import Controller.InventoryCntroller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddInventoryView extends JFrame {
    private JButton btnAdd;
    private ImageIcon img;
    private JLabel imagelabel;
    private JLabel p_quantity, costprice, saleprice, p_name, p_category;
    private JTextField tfquantity, tfprice, tfsaleprice, tfname, tfcategory;
    private InventoryCntroller ic = new InventoryCntroller();

    public AddInventoryView() {
        setTitle("Add Inventory");
        setLayout(null); // Absolute positioning
        setBounds(100, 100, 800, 600); // Adjusted height after removing vendor fields
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Image icon
        img = new ImageIcon("update.jpg");

        // Image label
        imagelabel = new JLabel(img);
        imagelabel.setBounds(0, 0, 400, 600);

        // Product Name
        p_name = new JLabel("Enter Name");
        p_name.setFont(new Font("Arial", Font.BOLD, 15));
        p_name.setBounds(420, 30, 150, 30);

        tfname = new JTextField();
        tfname.setBounds(580, 30, 180, 30);

        // Quantity
        p_quantity = new JLabel("Enter Quantity");
        p_quantity.setFont(new Font("Arial", Font.BOLD, 15));
        p_quantity.setBounds(420, 80, 150, 30);

        tfquantity = new JTextField();
        tfquantity.setBounds(580, 80, 180, 30);

        // Category
        p_category = new JLabel("Enter Category");
        p_category.setFont(new Font("Arial", Font.BOLD, 15));
        p_category.setBounds(420, 130, 150, 30);

        tfcategory = new JTextField();
        tfcategory.setBounds(580, 130, 180, 30);

        // Cost Price
        costprice = new JLabel("Enter Cost Price");
        costprice.setFont(new Font("Arial", Font.BOLD, 15));
        costprice.setBounds(420, 180, 150, 30);

        tfprice = new JTextField();
        tfprice.setBounds(580, 180, 180, 30);

        // Sale Price
        saleprice = new JLabel("Enter Sale Price");
        saleprice.setFont(new Font("Arial", Font.BOLD, 15));
        saleprice.setBounds(420, 230, 150, 30);

        tfsaleprice = new JTextField();
        tfsaleprice.setBounds(580, 230, 180, 30);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBackground(Color.CYAN);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBounds(580, 300, 100, 40);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    String name = tfname.getText();
                    int quantity = Integer.parseInt(tfquantity.getText());
                    String category = tfcategory.getText();
                    int costPrice = Integer.parseInt(tfprice.getText());
                    int salePrice = Integer.parseInt(tfsaleprice.getText());

                    // Updated to exclude vendor parameters
                    ic.redirect_Inventory_Insert_request(name, quantity, category, costPrice, salePrice);
                    dispose();
                }
            }
        });

        // Adding components to the frame
        add(imagelabel);
        add(p_name);
        add(tfname);
        add(p_quantity);
        add(tfquantity);
        add(p_category);
        add(tfcategory);
        add(costprice);
        add(tfprice);
        add(saleprice);
        add(tfsaleprice);
        add(btnAdd);

        setVisible(true);
    }

    // Validate Inputs
    private boolean validateInputs() {
        if (!namedoesNotContainInteger(tfname.getText())) {
            JOptionPane.showMessageDialog(null, "Product name cannot contain numbers");
            return false;
        }
        if (!namedoesNotContainInteger(tfcategory.getText())) {
            JOptionPane.showMessageDialog(null, "Category name cannot contain numbers");
            return false;
        }
        if (!isPositiveInteger(tfquantity.getText(), "Quantity")) return false;
        if (!isPositiveInteger(tfprice.getText(), "Cost Price")) return false;
        if (!isPositiveInteger(tfsaleprice.getText(), "Sale Price")) return false;

        return true;
    }

    // Validate non-numeric input
    private boolean namedoesNotContainInteger(String input) {
        return !input.matches(".*\\d.*");
    }

    // Validate positive integer input
    private boolean isPositiveInteger(String input, String fieldName) {
        try {
            int value = Integer.parseInt(input);
            if (value <= 0) {
                JOptionPane.showMessageDialog(null, fieldName + " must be a positive number");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " must be a valid number");
            return false;
        }
        return true;
    }
}
